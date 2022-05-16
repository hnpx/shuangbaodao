package cn.px.sys.modular.project.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 项目评价(ProjectComment)
 *
 * @author
 * @since 2020-09-04 09:06:03
 */
@TableName("project_comment")
public class ProjectCommentEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -59296338193104004L;
    /**
     * 项目id
     */
    @TableField("project_id")
    private Long projectId;
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
    /**
     * 评价图片
     */
    @TableField("img")
    private String img;


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}