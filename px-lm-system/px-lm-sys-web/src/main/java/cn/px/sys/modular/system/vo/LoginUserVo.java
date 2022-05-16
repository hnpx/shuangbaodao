package cn.px.sys.modular.system.vo;

import lombok.Data;

/**
 * 用户信息
 */
@Data
public class LoginUserVo {
    private String code;
    private String encryptedData;
    private String iv;
//TODO 后期还有其他扩展信息
}
