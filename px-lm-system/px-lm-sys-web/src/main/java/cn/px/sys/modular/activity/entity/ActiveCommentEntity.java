package cn.px.sys.modular.activity.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 活动评价(ActiveComment)
 *
 * @author
 * @since 2020-08-31 11:09:21
 */
@TableName("active_comment")
public class ActiveCommentEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 659987264544366560L;
    /**
     * 活动id
     */
    @TableField("active_id")
    private Long activeId;
    /**
     * 评价用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 评价内容
     */
    @TableField("content")
    private String content;
    @TableField("img")
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getActiveId() {
        return activeId;
    }

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}