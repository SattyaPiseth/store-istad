package co.istad.storeistad.service.user;


import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.BaseService;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.db.entity.PasswordResetToken;
import co.istad.storeistad.db.entity.RoleEntity;
import co.istad.storeistad.db.entity.UserEntity;
import co.istad.storeistad.db.repository.PasswordResetTokenRepository;
import co.istad.storeistad.db.repository.RoleRepository;
import co.istad.storeistad.db.repository.UserRepository;
import co.istad.storeistad.exception.httpstatus.BadRequestException;
import co.istad.storeistad.exception.httpstatus.InternalServerError;
import co.istad.storeistad.exception.httpstatus.NotFoundException;
import co.istad.storeistad.mapper.UserEntityMapper;
import co.istad.storeistad.model.projection.user.UserEntityInfo;
import co.istad.storeistad.model.projection.user.UserProfileByUuid;
import co.istad.storeistad.model.request.auth.ChangePasswordRQ;
import co.istad.storeistad.model.request.auth.RegisterRQ;
import co.istad.storeistad.model.request.user.CreateUserRQ;
import co.istad.storeistad.model.request.user.UpdateProfileRQ;
import co.istad.storeistad.model.request.user.UpdateRoleToUserRQ;
import co.istad.storeistad.model.response.user.UserProfileRS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Sattya
 * create at 1/29/2024 10:19 PM
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends BaseService implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public StructureRS createNewUser(CreateUserRQ request){
        // Check if user already exists in the database
        if (userRepository.existsByUsernameOrEmailAndIsDeletedFalse(request.getUsername(), request.getEmail())) {
            throw new InternalServerError(MessageConstant.AUTH.USERNAME_OR_EMAIL_ALREADY_EXISTS);
        }
        UserEntity userEntity = userEntityMapper.fromCreateUserRQ(request);
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setStatus(true);
        userEntity.setIsDeleted(false);
        userEntity.setDeletedAt(null);
//        userEntity.setIsVerified(false);

        // Validate and assign role
        Long roleId = request.getRoleIds().stream().findFirst().orElseThrow(() -> new NotFoundException(MessageConstant.ROLE.ROLE_NOT_FOUND));
        Optional<RoleEntity> roleEntity = roleRepository.findById(roleId);
        userEntity.setRoleEntity(roleEntity.orElseThrow(() -> new NotFoundException(MessageConstant.ROLE.ROLE_NOT_FOUND)));

//        userEntity.setRoleEntity(roleRepository.getReferenceById(2L));

        // Save user within the transaction
        userRepository.save(userEntity);

        return response(HttpStatus.CREATED, MessageConstant.USER.USER_CREATED_SUCCESSFULLY);
    }
    @Transactional
    @Override
    public void registerUser(RegisterRQ request) {
        if (userRepository.existsByUsernameOrEmailAndIsDeletedFalse(request.getUsername(), request.getEmail())) {
            throw new InternalServerError(MessageConstant.AUTH.USERNAME_OR_EMAIL_ALREADY_EXISTS);
        }
        UserEntity userEntity = userEntityMapper.fromRegisterRQ(request);
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setStatus(false);
        userEntity.setIsDeleted(false);
        userEntity.setDeletedAt(null);

        userEntity.setRoleEntity(roleRepository.getReferenceById(3L));

        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public StructureRS changePassword(ChangePasswordRQ request) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByUsernameAndIsDeletedFalseAndStatusTrue(authenticatedUsername)
                .orElseThrow(() -> new BadRequestException(MessageConstant.AUTH.INVALID_TOKEN));

        if (!checkIfValidOldPassword(userEntity, request.getOldPassword())) {
            throw new InternalServerError(MessageConstant.AUTH.PASSWORD_NOT_MATCH);
        }

        // Check if old password and new password are the same
        if (passwordEncoder.matches(request.getNewPassword(), userEntity.getPassword())) {
            throw new InternalServerError(MessageConstant.AUTH.PASSWORD_SAME);
        }

        changeUserPassword(userEntity, request.getNewPassword());
        return response(HttpStatus.OK, MessageConstant.AUTH.CHANGE_PASSWORD_SUCCESSFULLY);
    }

    @Override
    public boolean checkIfValidOldPassword(UserEntity user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public void changeUserPassword(UserEntity user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        new StructureRS(HttpStatus.OK, MessageConstant.AUTH.CHANGE_PASSWORD_SUCCESSFULLY);
    }

    @Override
    public UserProfileRS myProfile() {

        UserEntity userEntity = userRepository.findByUsernameAndIsDeletedFalseAndStatusTrue(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() ->
                new BadRequestException(MessageConstant.AUTH.INVALID_TOKEN));
        return userEntityMapper.toUserProfileRS(userEntity);
    }

    @Override
    public StructureRS getUserByUuid(String uuid) {
        UserProfileByUuid userProfileByUuid = userRepository.findByUuidFetchRolePermission(uuid);
        if (userProfileByUuid == null) throw new NotFoundException(MessageConstant.USER.USER_NOT_FOUND);
        return response(userProfileByUuid);
    }

    @Transactional
    @Override
    public StructureRS deleteByUuid(String uuid) {
        UserEntity userEntity = userRepository.findByUuidAndStatusTrueAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException(MessageConstant.USER.USER_NOT_FOUND));

        userEntity.setIsDeleted(true);
        userEntity.setStatus(false);
        userEntity.setDeletedAt(Instant.now());
        userRepository.save(userEntity);

        return response(HttpStatus.OK, MessageConstant.USER.USER_DELETED_SUCCESSFULLY);
    }

    @Override
    public void createPasswordResetTokenForUser(UserEntity user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(Date.from(Instant.now().plusSeconds(900)));
        passwordResetTokenRepository.save(passwordResetToken);
    }
    @Transactional
    @Override
    public StructureRS updateOwnProfile(UpdateProfileRQ profileRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            log.info("Email : {}", Optional.ofNullable(jwt.getClaim("email")));

            // Check if the email or username already exists
            if (userRepository.existsByEmailOrUsernameAndIsDeletedFalse(profileRequest.getEmail(), profileRequest.getUsername())) {
                throw new InternalServerError(MessageConstant.USER.USERNAME_OR_EMAIL_ALREADY_EXISTS);
            }
            UserEntity userEntity = userRepository.findByUsernameAndIsDeletedFalseAndStatusTrue(authentication.getName())
                    .orElseThrow(() -> new BadRequestException(MessageConstant.AUTH.INVALID_TOKEN));

            // Update user profile based on profileRequest
            userEntityMapper.fromUpdateProfileRQ(userEntity, profileRequest);
            userRepository.save(userEntity);
        }
        return response(HttpStatus.OK, MessageConstant.USER.USER_UPDATED_SUCCESSFULLY);
    }
    @Transactional
    @Override
    public StructureRS updateIsDeletedByUuid(String uuid, Boolean isDeleted) {
        boolean isFound = userRepository.existsByUuid(uuid);

        if (isFound) {
            if (isDeleted.equals(true)){
                userRepository.updateIsDeletedAndDeletedAtByUuidAndStatusTrue(true,Instant.now(),uuid);
                return response(HttpStatus.OK, MessageConstant.USER.USER_DELETED_SUCCESSFULLY);
            }
            else {
                userRepository.updateIsDeletedAndUpdatedAtByUuid(isDeleted,Instant.now(),uuid);
                return response(HttpStatus.OK, MessageConstant.USER.USER_UPDATED_SUCCESSFULLY);
            }
        } else {
            return response(HttpStatus.NOT_FOUND, MessageConstant.USER.USER_NOT_FOUND);
        }
    }

    @Override
    public StructureRS findAllUsers(BaseListingRQ response) {
        Page<UserEntityInfo> userEntityInfoPage = userRepository.findByQuery(response.getQuery(), response.getPageable(response.getSort(), response.getOrder()));
        return response(userEntityInfoPage.getContent(), userEntityInfoPage);
    }
    @Transactional
    @Override
    public StructureRS updateRoleToUser(String uuid, UpdateRoleToUserRQ request) {
        UserEntity userEntity = userRepository.findByUuidAndStatusTrueAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException(MessageConstant.USER.USER_NOT_FOUND));
        log.info("Updating role to user with uuid {}",uuid);

        Set<RoleEntity> roles = request.getRoleId().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new NotFoundException(MessageConstant.ROLE.ROLE_NOT_FOUND)))
                .collect(Collectors.toSet());

        userEntity.setRoleEntity(roles.stream().findFirst().orElseThrow(() -> new NotFoundException(MessageConstant.ROLE.ROLE_NOT_FOUND)));
        userRepository.save(userEntity);

        return response(HttpStatus.OK, MessageConstant.USER.USER_UPDATED_SUCCESSFULLY);
    }

}
