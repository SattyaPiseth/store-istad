package co.istad.storeistad.mapper;


import co.istad.storeistad.db.entity.RoleEntity;
import co.istad.storeistad.model.request.role.UpdateRoleRQ;
import org.mapstruct.*;

/**
 * @author Sattya
 * create at 2/5/2024 10:19 PM
 */
@Mapper(componentModel = "spring")
public interface RoleEntityMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromRoleRQ(UpdateRoleRQ updateRoleRQ, @MappingTarget RoleEntity roleEntity);
}
