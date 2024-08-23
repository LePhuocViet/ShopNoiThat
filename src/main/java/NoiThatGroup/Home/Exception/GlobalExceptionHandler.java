package NoiThatGroup.Home.Exception;

import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponses> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.badRequest().body( ApiResponses.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponses> handlingMethodArgument(MethodArgumentNotValidException exception){
        String errorkey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(errorkey);
        return ResponseEntity.badRequest().body(ApiResponses.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                .build());
    }

}
