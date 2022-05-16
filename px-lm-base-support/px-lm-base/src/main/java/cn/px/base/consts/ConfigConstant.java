package cn.px.base.consts;

/**
 * 配置的常量
 *
 * @author fengshuonan
 * @date 2019-06-24-12:51
 */
public interface ConfigConstant {

    /**
     * 系统常量的前缀标识
     */
    String SYSTEM_CONSTANT_PREFIX = "GUNS_";


    public class XssFilterUrlsConstants {
        public static String[] urls = {
                "/notice/add",
                "/notice/update",
                "/excelExportDeploy/editItem",
                "/announcement/add",
                "/announcement/update",
                "/excelExportDeploy/addItem",
                "/shopgoods/update/save"
        };
    }
}
