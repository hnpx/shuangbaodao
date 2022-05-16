package cn.px.base.exception;

import cn.px.base.support.http.HttpCode;

/**
 * @author PXHLT
 * @version 2019年5月20日 下午3:19:19
 */
@SuppressWarnings("serial")
public class BusinessException extends BaseException {
	public BusinessException() {
	}

	public BusinessException(Throwable ex) {
		super(ex);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable ex) {
		super(message, ex);
	}

	@Override
    protected HttpCode getCode() {
		return HttpCode.CONFLICT;
	}
}