package co.istad.storeistad.mapper;

import co.istad.storeistad.db.entity.UserEntity;
import co.istad.storeistad.model.request.auth.RegisterRQ;
import co.istad.storeistad.model.request.user.CreateUserRQ;
import co.istad.storeistad.model.request.user.UpdateProfileRQ;
import co.istad.storeistad.model.response.user.UserProfileRS;
import org.mapstruct.*;

/**
 * @author Sattya
 * create at 1/30/2024 11:49 AM
 */
@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity fromCreateUserRQ(CreateUserRQ registerRQ);
    UserEntity fromRegisterRQ(RegisterRQ registerRQ);
    CreateUserRQ toRegisterRQ(UserEntity userEntity);

    @Mapping(target = "roleName", source = "roleEntity.name")
    @Mapping(target = "roleCode", source = "roleEntity.code")
    UserProfileRS toUserProfileRS(UserEntity userEntity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateProfileRQ(@MappingTarget UserEntity userEntity, UpdateProfileRQ request);
}
