package co.istad.storeistad.service.user;


import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.db.entity.UserEntity;
import co.istad.storeistad.model.request.auth.ChangePasswordRQ;
import co.istad.storeistad.model.request.auth.RegisterRQ;
import co.istad.storeistad.model.request.user.CreateUserRQ;
import co.istad.storeistad.model.request.user.UpdateProfileRQ;
import co.istad.storeistad.model.request.user.UpdateRoleToUserRQ;
import co.istad.storeistad.model.response.user.UserProfileRS;

/**
 * @author Sattya
 * create at 1/29/2024 10:18 PM
 */
public interface UserService {
    /**
     * Create new user for registration
     * @param request of Request data from client
     */
    StructureRS createNewUser(CreateUserRQ request);

    void registerUser(RegisterRQ request);
    /**
     * reset password service
     * @param request of request data from client
     * @return message for client
     */
    StructureRS changePassword(ChangePasswordRQ request);

    /**
     * Check if valid old password for user
     * @param user of UserEntity
     * @param oldPassword of UserEntity
     * @return boolean value for client
     */
    boolean checkIfValidOldPassword(UserEntity user, String oldPassword);

    /**
     * Change user password service
     * @param user of UserEntity
     * @param newPassword of UserEntity
     */
    void changeUserPassword(UserEntity user, String newPassword);

    /**
     * Retrieve current logged in user
     *
     * @return UserProfileRS
     */
    UserProfileRS myProfile();

    /**
     * retrieve resource user by uuid from database
     * @param uuid of User
     * @return UserProfileRS --> ErrorResponse
     */
    StructureRS getUserByUuid(String uuid);

    /**
     * Delete user by uuid
     * @param uuid of User
     */
    StructureRS deleteByUuid(String uuid);

    /**
     *  create password reset token for user
     * @param user of UserEntity
     * @param token of UserEntity expired token for user to reset password (15 min)
     */
    void createPasswordResetTokenForUser(UserEntity user, String token);


    /**
     * Update own profile
     * @param request of Request data from client
     * @return message for client
     */
    StructureRS updateOwnProfile(UpdateProfileRQ request);

    /**
     * This method is used to update status(enable or disable)
     * for delete a user(Soft-Delete) by UUID
     * default of 'isDeleted' = false
     * @param uuid      of User
     * @param isDeleted of User
     */
    StructureRS updateIsDeletedByUuid(String uuid, Boolean isDeleted);

    /**
     * This method is used to retrieve all users
     * from database
     * @param response is the request data from client for pagination and filter
     * @return StructureRS
     */
    StructureRS findAllUsers(BaseListingRQ response);
    /**
     * This method is used to update role to user
     *
     * @param uuid    of User
     * @param request of Request data from client
     * @return StructureRS
     */
    StructureRS updateRoleToUser(String uuid, UpdateRoleToUserRQ request);

    // user access based on permission (custom permission)
//    StructureRS customizeUserAccess(String uuid, CustomizePermissionRQ request);

}
