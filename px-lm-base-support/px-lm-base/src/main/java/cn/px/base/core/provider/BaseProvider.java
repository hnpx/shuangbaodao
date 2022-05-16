package cn.px.base.core.provider;

/**
 * @author PXHLT
 * @version 2019年5月20日 下午3:19:19
 */
public interface BaseProvider {
	Parameter execute(Parameter parameter);
	
	Object execute(String service, String method, Object... parameters);
}
