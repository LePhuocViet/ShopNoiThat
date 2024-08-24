package NoiThatGroup.Home.Enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ACCOUNT_NOT_ACTIVE(400,"Account not active"),
    USERNAME_IS_EXIST(400,"Username is exist"),
    USERNAME_OR_PASSWORD_WRONG(400,"Username or Password is wrong"),
    EMAIL_IS_EXIST(400,"Username is exist"),
    ROLE_IS_EXIST(400,"Role is exist"),
    PASSWORD_NOT_MATCH(400,"Password not match"),
    USERNAME_INVALID(400,"Username needs to be between 5 and 18 characters."),
    PASSWORD_INVALID(400,"Password needs to be between 5 and 18 characters."),
    PHONENUMBER_INVALID(400,"Phone needs to be 10 characters"),
    CATEGORY_IS_EXIST(400,"Category is exits"),

    UNAUTHORIZED(401, "Unauthorized"),

    FORBIDDEN(403, "Forbidden"),

    CODE_EXPIRED(404,"Code expired"),
    EMAIL_NOT_FOUND(404,"Email not found"),
    ACCOUNT_NOT_FOUND(404,"Account not found"),
    USER_NOT_FOUND(404,"Account not found"),
    ROLE_NOT_FOUND(404,"Role not found"),
    CATEGORY_NOT_FOUND(400,"Category not found"),
    ITEM_NOT_FOUND(404,"Item not found")




    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
