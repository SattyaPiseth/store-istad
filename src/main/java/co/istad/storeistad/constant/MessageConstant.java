package co.istad.storeistad.constant;

/**
 * @author Sombath
 * create at 23/1/24 3:48 PM
 */
public class MessageConstant {

    public static final String SUCCESSFULLY = "Successfully";
    public static final String ALL = "ALL";

    public static class SYSTEM{
        public final static String INTERNAL_SERVER_ERROR = "Internal server error";
        public final static String BAD_REQUEST = "Bad request";
        public final static String NOT_FOUND = "Not found";
        public final static String METHOD_NOT_ALLOWED = "Method not allowed";
        public final static String UNSUPPORTED_MEDIA_TYPE = "Unsupported media type";
        public final static String MISSING_SERVLET_REQUEST_PARAMETER = "Missing servlet request parameter";
        public final static String REQUEST_BODY_NOT_READABLE = "Request body not readable";
        public final static String REQUEST_BODY_NOT_VALID = "Request body not valid";
        public final static String REQUEST_BODY_NOT_SUPPORTED = "Request body not supported";
        public final static String REQUEST_METHOD_NOT_SUPPORTED = "Request method not supported";
        public final static String TYPE_MISMATCH = "Type mismatch";
        public final static String SIZE_LIMIT_EXCEEDED = "Size limit exceeded";
        public final static String ACCESS_DENIED = "Access denied";
        public final static String INVALID_BEARER_TOKEN = "Invalid bearer token";
        public final static String INVALID_REFRESH_TOKEN = "Invalid refresh token";
        public final static String INVALID_ACCESS_TOKEN = "Invalid access token";
        public final static String INVALID_GRANT = "Invalid grant";
        public final static String INVALID_SCOPE = "Invalid scope";
        public final static String INVALID_CLIENT = "Invalid client";
        public final static String INVALID_REQUEST = "Invalid request";
        public final static String INVALID_TOKEN = "Invalid token";
        public final static String INVALID_VERIFICATION_CODE = "Invalid verification code";
        public final static String INVALID_RESET_TOKEN = "Invalid reset token";
        public final static String INVALID_VERIFY_TOKEN = "Invalid verify token";
        public final static String INVALID_RESET_PASSWORD_TOKEN = "Invalid reset password token";
        public final static String INVALID_VERIFY_CODE = "Invalid verify code";
        public final static String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";
        public final static String INVALID_EMAIL = "Invalid email";
        public final static String INVALID_PASSWORD = "Invalid password";
        public final static String INVALID_USERNAME = "Invalid username";
        public final static String INVALID_ROLE = "Invalid role";
        public final static String INVALID_PERMISSION = "Invalid permission";
        public final static String INVALID_CATEGORY = "Invalid category";
        public final static String INVALID_PRODUCT = "Invalid product";
        public final static String INVALID_VARIATION = "Invalid variation";
        public final static String INVALID_VARIATION_OPTION = "Invalid variation option";
    }

    public static class AUTH {
        public final static String ACCOUNT_LOCKED = "Account locked";
        public final static String INCORRECT_USERNAME_OR_PASSWORD = "Incorrect Username or password";
        public final static String ACCOUNT_DEACTIVATE = "Account have been deactivated";
        public final static String REGISTER_SUCCESSFULLY = "Register successfully";
        public final static String EMAIL_NOT_FOUND = "Email not found";
        public final static String EMAIL_EXIST = "Email already exist";
        public final static String USERNAME_EXIST = "Username already exist";
        public final static String PASSWORD_NOT_MATCH = "Password not match";
        public final static String PASSWORD_SAME = "New password is the same as old password";
        public final static String RESET_PASSWORD_SUCCESSFULLY = "Reset password successfully";
        public final static String CHANGE_PASSWORD_SUCCESSFULLY = "Change password successfully";
        public final static String EMAIL_ALREADY_VERIFIED = "Email already verified";
        public final static String PASSWORD_RESET_TOKEN_ALREADY_SENT= "Password reset token already sent";
        public final static String VERIFICATION_CODE_RESENT = "Verification code resent";
        public final static String RESET_PASSWORD_FAILED = "Reset password failed";
        public final static String EXPIRED_TOKEN = "Expired token";
        public final static String INVALID_TOKEN = "Invalid token";
        public final static String INVALID_REFRESH_TOKEN = "Invalid refresh token";
        public final static String TOKEN_NOT_FOUND = "Token not found";
        public final static String TOKEN_EXPIRED = "Token expired";
        public final static String VERIFY_CODE_NOT_FOUND = "Verify code not found";
        public final static String VERIFY_CODE_NOT_MATCH = "Verify code not match";
        public final static String BAD_CREDENTIALS = "Bad credentials";
        public final static String VERIFY_TOKEN_NOT_FOUND = "Verify token not found";
        public final static String VERIFY_TOKEN_NOT_MATCH = "Verify token not match";
        public final static String RESET_TOKEN_NOT_FOUND = "Reset token not found";
        public final static String RESET_TOKEN_NOT_MATCH = "Reset token not match";
        public final static String USERNAME_OR_EMAIL_ALREADY_EXISTS = "Username or email already exists";
        public final static String EMAIL_VERIFIED = "Email verified";
        public final static String RESET_PASSWORD_EMAIL = "Reset password email";
    }

    public static class ROLE {
        public final static String ADMIN = "ADMIN";
        public final static String USER = "USER";
        public static final String ROLE_CREATED_SUCCESSFULLY = "Role has been created";
        public static final String ROLE_NOT_FOUND = "Role could not be found";
        public static final String ROLE_DELETED_SUCCESSFULLY = "Role has been deleted";
        public static final String ROLE_UPDATED_SUCCESSFULLY = "Role has been updated";
        public static final String ROLE_ID_NOT_FOUND = "Role ID could not be found";
        public static final String ROLE_ALREADY_EXISTS = "Role already exists";
    }

    public static class USER{
        public final static String USER_CREATED_SUCCESSFULLY = "User has been created";
        public final static String USER_NOT_FOUND = "User could not be found";
        public final static String USER_DELETED_SUCCESSFULLY = "User has been deleted";
        public final static String USER_UPDATED_SUCCESSFULLY = "User has been updated";
        public final static String USERNAME_OR_EMAIL_ALREADY_EXISTS = "Username or email already exists";
    }

    public static class PERMISSION{
        public final static String PERMISSION_CREATED_SUCCESSFULLY = "Permission has been created";
        public final static String PERMISSION_NOT_FOUND = "Permission could not be found";
        public final static String PERMISSION_DELETED_SUCCESSFULLY = "Permission has been deleted";
        public final static String PERMISSION_UPDATED_SUCCESSFULLY = "Permission has been updated";
    }

    public static class PRODUCT{
        public final static String PRODUCT_RESTORED_SUCCESSFULLY = "Product restored successfully";
        public final static String PRODUCT_ALREADY_EXISTS = "Product already exists";
        public final static String PRODUCT_CREATED_SUCCESSFULLY = "Product has been created";
        public final static String PRODUCT_NOT_FOUND = "Product could not be found";
        public final static String PRODUCT_DELETED_SUCCESSFULLY = "Product has been deleted";
        public final static String PRODUCT_UPDATED_SUCCESSFULLY = "Product has been updated";
    }

    public static class VARIATION{
        public final static String VARIATION_CREATED_SUCCESSFULLY = "Variation has been created";
        public final static String VARIATION_NOT_FOUND = "Variation could not be found";
        public final static String VARIATION_DELETED_SUCCESSFULLY = "Variation has been deleted";
        public final static String VARIATION_UPDATED_SUCCESSFULLY = "Variation has been updated";
    }

    public static class VARIATION_OPTION{
        public final static String VARIATION_OPTION_CREATED_SUCCESSFULLY = "Variation option has been created";
        public final static String VARIATION_OPTION_NOT_FOUND = "Variation option could not be found";
        public final static String VARIATION_OPTION_DELETED_SUCCESSFULLY = "Variation option has been deleted";
        public final static String VARIATION_OPTION_UPDATED_SUCCESSFULLY = "Variation option has been updated";

        public final static String VARIATION_OPTION_REQUIRED = "Variation option required";
        public final static String VARIATION_OPTION_ALREADY_USED = "Variation option already used";
    }

    public static class PRODUCT_ITEM{
        public final static String PRODUCT_ID_REQUIRED = "Product id required";
        public final static String PRODUCT_ITEM_CREATED_SUCCESSFULLY = "Product item has been created";
        public final static String PRODUCT_ITEM_NOT_FOUND = "Product item could not be found";
        public final static String FAILED_TO_GET_PRODUCT_ITEM = "Failed to get product item";
        public final static String PRODUCT_ITEM_DELETED_SUCCESSFULLY = "Product item has been deleted";
        public final static String PRODUCT_ITEM_UPDATED_SUCCESSFULLY = "Product item has been updated";
        public final static String PRODUCT_ITEM_ALREADY_EXISTS = "Product item already exists";
    }
    public static class CATEGORY{
        public final static String CATEGORY_CANNOT_BE_PARENT_OF_ITSELF = "Category cannot be parent of itself";
        public final static String CATEGORY_RESTORED_SUCCESSFULLY = "Category restored successfully";
        public final static String CATEGORY_PARENT_ID_CANNOT_BE_ZERO = "Category parent id cannot be zero";
        public final static String CATEGORY_CREATED_SUCCESSFULLY = "Category has been created";
        public final static String CATEGORY_NOT_FOUND = "Category could not be found";
        public final static String CATEGORY_DELETED_SUCCESSFULLY = "Category has been deleted";
        public final static String CATEGORY_UPDATED_SUCCESSFULLY = "Category has been updated";
        public final static String CATEGORY_ALREADY_EXISTS = "Category already exists";
    }

    public static class FILE{
       public final static String FILE_EMPTY = "File is empty";
         public final static String FILE_NOT_FOUND = "File not found";
    }
}
