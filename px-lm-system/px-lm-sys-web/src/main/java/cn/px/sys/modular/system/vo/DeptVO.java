package cn.px.sys.modular.system.vo;

import cn.px.sys.modular.system.entity.Dept;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 部门实体类
 */
@Data
@ToString
public class DeptVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Long deptId;


    /**
     * 简称
     */
    @NotBlank
    private String simpleName;

    /**
     * 全称
     */
    @NotBlank
    private String fullName;

    /**
     * 描述
     */
    private String description;

    public DeptVO() {
    }

    public DeptVO(Dept dept) {
        this.deptId = dept.getDeptId();
        this.description = dept.getDescription();
        this.fullName = dept.getFullName();
        this.simpleName = dept.getSimpleName();
    }
}
