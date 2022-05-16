package cn.px.sys.modular.system.vo;

import cn.px.sys.modular.system.entity.Role;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 角色实体类
 */
@Data
@ToString
public class RoleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 提示
     */
    @TableField("description")
    private String description;

    public RoleVO() {
    }

    public RoleVO(Role role) {
        this.description = role.getDescription();
        this.name = role.getName();
        this.roleId = role.getRoleId();
    }
}
