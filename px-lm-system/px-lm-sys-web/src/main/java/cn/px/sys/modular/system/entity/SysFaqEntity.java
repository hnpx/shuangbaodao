package cn.px.sys.modular.system.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.px.base.core.BaseModel;

/**
 * (SysFaq)
 *
 * @author makejava
 * @since 2020-06-09 14:36:36
 */
@TableName("sys_faq")
public class SysFaqEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -74647963005133938L;
                                             @TableField("name")
    private String name;
                 @TableField("answer")
    private Object answer;
    
                                                                                
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
                    
    public Object getAnswer() {
        return answer;
    }

    public void setAnswer(Object answer) {
        this.answer = answer;
    }
    
}