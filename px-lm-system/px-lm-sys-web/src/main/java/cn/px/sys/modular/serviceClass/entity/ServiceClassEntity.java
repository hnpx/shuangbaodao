package cn.px.sys.modular.serviceClass.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (ServiceClass)
 *
 * @author
 * @since 2020-08-27 17:49:53
 */
@TableName("service_class")
public class ServiceClassEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -12458497164525332L;
    /**
     * 服务分类名称
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