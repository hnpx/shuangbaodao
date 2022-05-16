package cn.px.sys.modular.project.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (ProjectClass)
 *
 * @author
 * @since 2020-09-02 20:19:41
 */
@TableName("project_class")
public class ProjectClassEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -87276322721488168L;
    /**
     * 项目分类名称
     */
    @TableField("name")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}