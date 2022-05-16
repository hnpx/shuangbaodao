package cn.px.base.exception;


import cn.px.base.support.http.HttpCode;

/**
 * @author PXHLT
 * @version 2019年5月20日 下午3:19:19
 */
@SuppressWarnings("serial")
public class DataParseException extends BaseException {

	public DataParseException() {
	}

	public DataParseException(Throwable ex) {
		super(ex);
	}

	public DataParseException(String message) {
		super(message);
	}

	public DataParseException(String message, Throwable ex) {
		super(message, ex);
	}

	@Override
    protected HttpCode getCode() {
		return HttpCode.INTERNAL_SERVER_ERROR;
	}

}
