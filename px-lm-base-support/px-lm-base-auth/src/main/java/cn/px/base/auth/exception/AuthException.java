package cn.px.base.auth.exception;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;
import lombok.Data;

/**
 * 认证失败（账号密码错误，账号被冻结，token过期等）
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class AuthException extends RuntimeException {

    private Integer httpCode;
    private String errorMessage;

    public AuthException() {
        super("认证失败！");
        this.httpCode = 500;
        this.errorMessage = "认证失败！";

}
    public AuthException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.httpCode = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

    public Integer getCode(){
        return this.httpCode;
    }

}
