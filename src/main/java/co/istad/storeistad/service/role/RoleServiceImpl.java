package co.istad.storeistad.service.role;


import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.BaseService;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.db.entity.PermissionEntity;
import co.istad.storeistad.db.entity.RoleEntity;
import co.istad.storeistad.db.repository.PermissionRepository;
import co.istad.storeistad.db.repository.RoleRepository;
import co.istad.storeistad.exception.httpstatus.NotFoundException;
import co.istad.storeistad.mapper.RoleEntityMapper;
import co.istad.storeistad.model.projection.RoleEntityInfo;
import co.istad.storeistad.model.request.permission.PermissionRQ;
import co.istad.storeistad.model.request.role.RoleRQ;
import co.istad.storeistad.model.request.role.UpdateRolePermissionRQ;
import co.istad.storeistad.model.request.role.UpdateRoleRQ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author Sattya
 * create at 1/29/2024 3:37 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl extends BaseService implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleEntityMapper roleMapper;
    @Transactional
    @Override
    public void createRole(RoleRQ roleRQ) {
        // Check for existing role name
        if (roleRepository.existsByNameAndCodeAllIgnoreCase(roleRQ.getName(),roleRQ.getCode())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, MessageConstant.ROLE.ROLE_ALREADY_EXISTS);
        }
        RoleEntity role = new RoleEntity();
        role.setName(roleRQ.getName());
        role.setCode(roleRQ.getCode());
        roleRepository.save(role);
    }
    @Transactional
    @Override
    public void deleteRole(Long roleId) {
        // check for existing role
        boolean roleExists = roleRepository.existsById(roleId);
        if (!roleExists){
            throw new NotFoundException(MessageConstant.ROLE.ROLE_NOT_FOUND);
        }
        // delete the role , potentially logging the action
        roleRepository.deleteById(roleId);
        log.info("Role with ID {} deleted successfully.",roleId);
    }
    @Transactional
    @Override
    public void updateRole(Long roleId, UpdateRoleRQ request) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(MessageConstant.ROLE.ROLE_NOT_FOUND));
        log.info("Updating role with ID {}", roleId);

        roleMapper.fromRoleRQ(request, role);

        roleRepository.save(role);
        log.info("Role with ID {} updated successfully.", roleId);
    }

    @Override
    public StructureRS getAllRoles(BaseListingRQ response) {
        Page<RoleEntityInfo> roleEntityInfoPage = roleRepository.findByNameStartsWithAndCode(response.getQuery(), response.getPageable(response.getSort(),response.getOrder()));
        return response(roleEntityInfoPage.getContent(),roleEntityInfoPage);
    }

    @Transactional
    @Override
    public StructureRS updatePermission(UpdateRolePermissionRQ updateRolePermissionRQ) {
        if (!roleRepository.existsById(updateRolePermissionRQ.getRoleId()))
            throw new NotFoundException(MessageConstant.ROLE.ROLE_ID_NOT_FOUND);

        RoleEntity roleEntity = roleRepository.findByIdFetchPermission(updateRolePermissionRQ.getRoleId());

        List<PermissionEntity> permissionEntities = permissionRepository.findByIdIn(updateRolePermissionRQ.getPermissions().stream().map(PermissionRQ::getPermissionId).collect(Collectors.toList()));

        for (PermissionRQ permissionRQ : updateRolePermissionRQ.getPermissions()) {

            Optional<PermissionEntity> optionalPermissionEntity = permissionEntities.stream().filter(it -> it.getId().equals(permissionRQ.getPermissionId())).findFirst();

            if (optionalPermissionEntity.isPresent()) {
                PermissionEntity permissionEntity = optionalPermissionEntity.get();

                if (permissionRQ.getStatus()) {
                    if (!roleEntity.getPermissionEntities().contains(permissionEntity))
                        roleEntity.getPermissionEntities().add(permissionEntity);
                } else
                    roleEntity.getPermissionEntities().remove(permissionEntity);

            }

        }
        roleRepository.save(roleEntity);

        return response();
    }
}
