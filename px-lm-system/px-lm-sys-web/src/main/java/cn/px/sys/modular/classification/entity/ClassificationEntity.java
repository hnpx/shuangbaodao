package cn.px.sys.modular.classification.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 商家分类管理(Classification)
 *
 * @author
 * @since 2020-08-27 15:21:58
 */
@TableName("classification")
public class ClassificationEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 102669153789463276L;
    /**
     * 分类名称
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