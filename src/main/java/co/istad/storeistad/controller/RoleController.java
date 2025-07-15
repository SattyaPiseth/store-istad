package co.istad.storeistad.controller;


import co.istad.storeistad.base.BaseController;
import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.model.request.role.RoleRQ;
import co.istad.storeistad.model.request.role.UpdateRolePermissionRQ;
import co.istad.storeistad.model.request.role.UpdateRoleRQ;
import co.istad.storeistad.service.role.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sattya
 * create at 1/29/2024 3:49 PM
 */
@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController extends BaseController {
    private final RoleService roleService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public StructureRS createRole(@RequestBody @Validated RoleRQ roleRQ){
        roleService.createRole(roleRQ);
        return new StructureRS(HttpStatus.CREATED, MessageConstant.ROLE.ROLE_CREATED_SUCCESSFULLY);
    }
    @GetMapping
    public StructureRS getAllRoles(BaseListingRQ response){
        return roleService.getAllRoles(response);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{roleId}")
    public StructureRS deleteRole(@PathVariable Long roleId){
        roleService.deleteRole(roleId);
        return new StructureRS(HttpStatus.OK, MessageConstant.ROLE.ROLE_DELETED_SUCCESSFULLY);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{roleId}")
    public StructureRS updateRole(@PathVariable Long roleId, @RequestBody UpdateRoleRQ request){
        roleService.updateRole(roleId, request);
        return new StructureRS(HttpStatus.OK, MessageConstant.ROLE.ROLE_UPDATED_SUCCESSFULLY);
    }
    @PutMapping("update/permission")
    public ResponseEntity<StructureRS> updatePermission(@RequestBody @Valid UpdateRolePermissionRQ updateRolePermissionRQ){
        return response(roleService.updatePermission(updateRolePermissionRQ));
    }
}
