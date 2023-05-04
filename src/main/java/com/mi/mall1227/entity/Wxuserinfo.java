package com.mi.mall1227.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @TableName t_wxuserinfo
 */
@TableName(value = "t_wxuserinfo")
@Data
public class Wxuserinfo implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String openid;

    /**
     *
     */
    private String nickname;

    /**
     *
     */
    private String avatarurl;

    /**
     *
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date registerdate;

    /**
     *
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date lastlogindate;


    @TableField(select = false, exist = false)
    private String code; // 微信用户code 前端传来的


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Wxuserinfo other = (Wxuserinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
                && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
                && (this.getAvatarurl() == null ? other.getAvatarurl() == null : this.getAvatarurl().equals(other.getAvatarurl()))
                && (this.getRegisterdate() == null ? other.getRegisterdate() == null : this.getRegisterdate().equals(other.getRegisterdate()))
                && (this.getLastlogindate() == null ? other.getLastlogindate() == null : this.getLastlogindate().equals(other.getLastlogindate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getAvatarurl() == null) ? 0 : getAvatarurl().hashCode());
        result = prime * result + ((getRegisterdate() == null) ? 0 : getRegisterdate().hashCode());
        result = prime * result + ((getLastlogindate() == null) ? 0 : getLastlogindate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", openid=").append(openid);
        sb.append(", nickname=").append(nickname);
        sb.append(", avatarurl=").append(avatarurl);
        sb.append(", registerdate=").append(registerdate);
        sb.append(", lastlogindate=").append(lastlogindate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}