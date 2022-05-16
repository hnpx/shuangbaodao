package cn.px.sys.modular.member.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 用户信息管理(Member)
 *
 * @author
 * @since 2020-09-03 14:02:37
 */
@TableName("member")
public class MemberEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 834220079750421119L;
    /**
     * 用户名字
     */
    @TableField("name")
    private String name;
    /**
     * 所属社区
     */
    @TableField("belonging_community")
    private Long belongingCommunity;
    /**
     * 所属小区
     */
    @TableField("belonging_home")
    private Long belongingHome;
    /**
     * 详细地址
     */
    @TableField("address")
    private String address;
    /**
     * 所属单位
     */
    @TableField("belonging_unit")
    private Long belongingUnit;
    /**
     * 编号
     */
    @TableField("numbering")
    private String numbering;
    /**
     * 头像
     */
    @TableField("avatar")
    private Object avatar;
    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    /**
     * 积分
     */
    @TableField("integral")
    private Integer integral;
    /**
     * 身份证号
     */
    @TableField("id_number")
    private String idNumber;
    /**
     * 是否绑定
     */
    @TableField("is_bind")
    private Integer isBind;
    /**
     * openid
     */
    @TableField("openid")
    private String openid;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    @TableField("expertise")
    private Long expertise;

    public Long getExpertise() {
        return expertise;
    }

    public void setExpertise(Long expertise) {
        this.expertise = expertise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBelongingCommunity() {
        return belongingCommunity;
    }

    public void setBelongingCommunity(Long belongingCommunity) {
        this.belongingCommunity = belongingCommunity;
    }

    public Long getBelongingHome() {
        return belongingHome;
    }

    public void setBelongingHome(Long belongingHome) {
        this.belongingHome = belongingHome;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBelongingUnit() {
        return belongingUnit;
    }

    public void setBelongingUnit(Long belongingUnit) {
        this.belongingUnit = belongingUnit;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getIsBind() {
        return isBind;
    }

    public void setIsBind(Integer isBind) {
        this.isBind = isBind;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}