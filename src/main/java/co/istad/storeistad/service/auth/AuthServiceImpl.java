package co.istad.storeistad.service.auth;

import co.istad.storeistad.base.BaseService;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.db.entity.PasswordResetToken;
import co.istad.storeistad.db.entity.UserEntity;
import co.istad.storeistad.db.repository.AuthRepository;
import co.istad.storeistad.db.repository.PasswordResetTokenRepository;
import co.istad.storeistad.exception.httpstatus.BadRequestException;
import co.istad.storeistad.exception.httpstatus.InternalServerError;
import co.istad.storeistad.exception.httpstatus.NotFoundException;
import co.istad.storeistad.mail.Mail;
import co.istad.storeistad.mail.MailService;
import co.istad.storeistad.model.request.auth.ForgotPasswordRQ;
import co.istad.storeistad.model.request.auth.LoginRQ;
import co.istad.storeistad.model.request.auth.RegisterRQ;
import co.istad.storeistad.model.request.auth.ResetPasswordRQ;
import co.istad.storeistad.model.request.mail.VerifyRQ;
import co.istad.storeistad.security.UserPrincipal;
import co.istad.storeistad.service.user.UserService;
import co.istad.storeistad.utils.RandomUtil;
import co.istad.storeistad.utils.TokenUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Sattya
 * create at 26/1/24 2:08 AM
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl extends BaseService implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MailService mailService;
    private final AuthRepository authRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${spring.mail.username}")
    private String adminMail;

    @Value("${app.base-uri}")
    private String appBaseUri;

    @Value("${app.api-version}")
    private String apiVersion;

    @Transactional
    @Override
    public StructureRS login(LoginRQ request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        try{
            authenticationManager.authenticate(authenticationToken);
        }catch (LockedException exception){
            throw new BadRequestException(MessageConstant.AUTH.ACCOUNT_DEACTIVATE);
        }

        Authentication auth = authenticationManager.authenticate(authenticationToken);
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        Map<String, Object> respond = new HashMap<>();

        respond.put("user", userPrincipal);
        respond.put("token", tokenUtils.generateToken(userPrincipal));

        return response(respond);
    }
    @Transactional
    @Override
    public StructureRS register(RegisterRQ request) throws MessagingException {

        userService.registerUser(request);

        // Generate verification code and token
//        String verificationCode = RandomUtil.generateCode();
        String verificationToken = UUID.randomUUID().toString();

        // Store verification details in the database
//        authRepository.updateVerifiedCode(request.getUsername(),verificationCode);
        authRepository.updateVerifiedTokenAndDeletedAtByUsername(verificationToken,request.getUsername());

        // Prepare and send verification email
        String verificationLink = appBaseUri + apiVersion +"auth/verify?token=" + verificationToken;


//        // First email verification code 6 digits
//        Mail<String> verificationCodeMail = createVerificationEmail(request.getEmail(),verificationCode,"auth/verify-mail");
//        log.info("Verification code: {}", verificationCode);
//        mailService.sendMail(verificationCodeMail);

        // Second email verification link
        Mail<String> verificationLinkMail = createVerificationEmail(request.getEmail(),verificationLink,"auth/verify-token-mail");
        mailService.sendMail(verificationLinkMail);

        return response(HttpStatus.valueOf(201),MessageConstant.AUTH.REGISTER_SUCCESSFULLY, verificationLink);
    }

    @Transactional
    @Override
    public void verifyRQ(VerifyRQ request) {
        UserEntity userEntity = authRepository.findByUsernameAndVerifiedCodeAndIsDeletedFalse(request.getEmail(),request.getVerifiedCode())
                .orElseThrow(() -> new NotFoundException(MessageConstant.AUTH.VERIFY_CODE_NOT_FOUND));

//        userEntity.setIsVerified(true);
        userEntity.setStatus(true);
        userEntity.setVerifiedCode(null);
        userEntity.setVerifiedToken(null);
        authRepository.save(userEntity);
    }
    @Transactional
    @Override
    public void verifyUser(String token) {
        // Find the user with the matching token
        UserEntity userEntity = authRepository.findByVerifiedTokenAndIsDeletedFalse(token)
                .orElseThrow(() -> new NotFoundException(MessageConstant.AUTH.VERIFY_TOKEN_NOT_FOUND));
        if (!userEntity.getStatus()) {
//            userEntity.setIsVerified(true);
            userEntity.setVerifiedCode(null);
            userEntity.setVerifiedToken(null);
            userEntity.setDeletedAt(null);
            userEntity.setStatus(true);
            authRepository.save(userEntity);
        }
    }

    @Transactional
    @Override
    public StructureRS forgotPassword(ForgotPasswordRQ request) throws MessagingException {
        // Check if a password reset token has already been sent for this email
        if (passwordResetTokenRepository.existsByUserEmail(request.getEmail())) {
            throw new InternalServerError(MessageConstant.AUTH.PASSWORD_RESET_TOKEN_ALREADY_SENT);
        }

        // Find the user with the matching email
        UserEntity userEntity = authRepository.findByEmailAndIsDeletedFalseAndStatusTrue(request.getEmail())
                .orElseThrow(() -> new NotFoundException(MessageConstant.AUTH.EMAIL_NOT_FOUND));

        // Generate a unique token
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(userEntity, token);

        // Create the reset link
        String changePasswordLink = appBaseUri + apiVersion + "auth/change-password?token=" + token;

        // Send the password reset email
        Mail<?> resetMail = createPasswordResetEmail(userEntity.getEmail(), changePasswordLink);
        mailService.sendMail(resetMail);
        log.info("Password reset email sent: {}", resetMail);

        // Return the response
        return response(HttpStatus.CREATED, MessageConstant.SUCCESSFULLY,
                Map.of("link", changePasswordLink, "change-password-token", token,
                        "message","Please check email and change-password..! This token is valid for 15 minutes."));
    }

    @Transactional
    @Override
    public StructureRS changePassword(String token, ResetPasswordRQ request) {
        Optional<UserEntity> user = Optional.ofNullable(getUserByPasswordResetToken(token));
        if (user.isPresent()) {
            userService.changeUserPassword(user.get(), request.getNewPassword());
            passwordResetTokenRepository.deleteByToken(token);
            return new StructureRS(HttpStatus.OK, MessageConstant.AUTH.CHANGE_PASSWORD_SUCCESSFULLY);
        } else {
            return new StructureRS(HttpStatus.BAD_REQUEST, MessageConstant.AUTH.TOKEN_EXPIRED);
        }
    }

    @Transactional
    @Override
    public boolean validatePasswordResetToken(String token) {
        final PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(
                () -> new BadRequestException(MessageConstant.AUTH.INVALID_TOKEN)
        );
        return isTokenFound(passwordResetToken) && (!isTokenExpired(passwordResetToken));
    }

    @Override
    public UserEntity getUserByPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token).orElseThrow(
                () -> new BadRequestException(MessageConstant.AUTH.INVALID_TOKEN)
        ).getUser();
    }
    @Transactional
    @Override
    public StructureRS resendVerificationCodeViaEmail(String email) throws MessagingException {
        String verificationCode = RandomUtil.generateCode();

//        // Check if email exists, is not deleted, and is not verified
//        UserEntity userEntity = authRepository.findByEmailAndIsDeletedFalseAndStatusFalse(email)
//                .orElseThrow(() -> new NotFoundException(MessageConstant.AUTH.EMAIL_NOT_FOUND));

        if (!authRepository.existsByEmailAndIsDeletedFalseAndStatusFalse(email)) {
            throw new BadRequestException(MessageConstant.AUTH.EMAIL_NOT_FOUND);
        }

        authRepository.updateVerifiedCodeByEmailAndIsDeletedFalseAndStatusFalse(verificationCode, email);

        String verificationLink = appBaseUri + apiVersion + "auth/verify-code";
        Mail<String> verificationCodeMail = createVerificationEmail(email, verificationCode, "auth/verify-mail");
        log.info("Verification code: {}", verificationCode);
        mailService.sendMail(verificationCodeMail);

        return response(HttpStatus.OK, MessageConstant.AUTH.VERIFICATION_CODE_RESENT, Map.of("link",verificationLink,"6 digits",verificationCode));
    }

    private boolean isTokenFound(PasswordResetToken passwordResetToken) {
        return passwordResetToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passwordResetToken) {
        final Calendar calendar = Calendar.getInstance();
        return passwordResetToken.getExpiryDate().before(calendar.getTime());
    }

    private Mail<String> createPasswordResetEmail(String recipient, String resetLink) {
        Mail<String> mail = new Mail<>();
        mail.setSubject("Reset Password");
        mail.setSender(recipient);  //destination -> recipient is the email address of the receiver
        mail.setReceiver(adminMail); //source -> adminMail is the email address of the sender
        mail.setTemplate("auth/forgot-password-mail");
        mail.setMetaData(resetLink);
        return mail;
    }


    private Mail<String> createVerificationEmail(String recipient, String metaData,String template) {
        Mail<String> mail = new Mail<>();
        mail.setSubject("Email Verification");
        mail.setSender(adminMail);  //source -> adminMail is the email address of the sender
        mail.setReceiver(recipient);  //destination -> recipient is the email address of the receiver
        mail.setTemplate(template);
        mail.setMetaData(metaData);
        return mail;
    }
}
