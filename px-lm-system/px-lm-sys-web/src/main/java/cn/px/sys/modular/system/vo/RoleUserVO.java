package cn.px.sys.modular.system.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 角色及角色下的用户实体类
 */
@Data
@ToString
public class RoleUserVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色
     */
    RoleVO role;
    /**
     * 该角色下的用户
     */
    List<UserVO> users;
}
