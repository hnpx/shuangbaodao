/**
 * 
 */
package cn.px.base.exception;

import cn.px.base.support.http.HttpCode;

/**
 * 
 * @author PXHLT
 * @version 2018年3月24日 下午9:30:10
 */
@SuppressWarnings("serial")
public class InstanceException extends BaseException {
    public InstanceException() {
        super();
    }

    public InstanceException(String message){
        super(message);
    }
    public InstanceException(Throwable t) {
        super(t);
    }

    @Override
    protected HttpCode getCode() {
        return HttpCode.INTERNAL_SERVER_ERROR;
    }
}
