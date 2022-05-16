package cn.px.sys.modular.system.vo;

import cn.px.sys.modular.system.entity.User;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
@ToString
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long userId;

    /**
     * 头像
     */
    private String avatar;


    /**
     * 名字
     */
    private String name;

    /**
     * 性别(字典)
     */
    private String sex;


    /**
     * 角色id(多个逗号隔开)
     */
    private String roleId;

    /**
     * 部门id(多个逗号隔开)
     */
    private Long deptId;

    private String phone;

    public UserVO() {
    }

    public UserVO(User user) {
        this.userId = user.getUserId();
        this.avatar = user.getAvatar();
        this.name = user.getName();
        this.deptId = user.getDeptId();
        this.roleId = user.getRoleId();
        this.sex = user.getSex();
        this.phone=user.getPhone();
    }
}
