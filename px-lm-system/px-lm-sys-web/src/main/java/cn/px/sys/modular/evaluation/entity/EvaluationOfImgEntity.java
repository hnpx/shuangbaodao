package cn.px.sys.modular.evaluation.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (EvaluationOfImg)
 *
 * @author
 * @since 2020-09-16 09:53:37
 */
@TableName("evaluation_of_img")
public class EvaluationOfImgEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -65219042546007782L;
    /**
     * 图片地址
     */
    @TableField("img_url")
    private String imgUrl;


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}