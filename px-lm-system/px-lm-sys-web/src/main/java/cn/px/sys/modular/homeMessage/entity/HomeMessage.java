package cn.px.sys.modular.homeMessage.entity;


import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("home_message")
public class HomeMessage extends BaseModel implements Serializable {

    private static final long serialVersionUID = 112223642036968361L;

    @TableField("title")
    private String title;

    @TableField("img")
    private String img;
}
