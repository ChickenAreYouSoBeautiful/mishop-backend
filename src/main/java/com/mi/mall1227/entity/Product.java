package com.mi.mall1227.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @TableName t_product
 */
@TableName(value = "t_product")
@Data
public class Product implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Integer price;

    /**
     *
     */
    private Integer stock;

    /**
     *
     */
    private String propic;

    /**
     *
     */
    private Boolean ishot;

    /**
     *
     */
    private Boolean isswiper;

    /**
     *
     */
    private String swiperpic;

    /**
     *
     */
    private Integer swipersort;

    /**
     *
     */
    private Integer typeid;

    /**
     *
     */
    private Date hotdatetime;

    /**
     *
     */
    private String productintroimgs;

    /**
     *
     */
    private String productparaimgs;

    /**
     *
     */
    private String description;

    //该商品的轮播图属性集合
    @TableField(select = false)
    private List<ProductSwiperImage> productSwiperImageList;


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
        Product other = (Product) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getStock() == null ? other.getStock() == null : this.getStock().equals(other.getStock()))
                && (this.getPropic() == null ? other.getPropic() == null : this.getPropic().equals(other.getPropic()))
                && (this.getIshot() == null ? other.getIshot() == null : this.getIshot().equals(other.getIshot()))
                && (this.getIsswiper() == null ? other.getIsswiper() == null : this.getIsswiper().equals(other.getIsswiper()))
                && (this.getSwiperpic() == null ? other.getSwiperpic() == null : this.getSwiperpic().equals(other.getSwiperpic()))
                && (this.getSwipersort() == null ? other.getSwipersort() == null : this.getSwipersort().equals(other.getSwipersort()))
                && (this.getTypeid() == null ? other.getTypeid() == null : this.getTypeid().equals(other.getTypeid()))
                && (this.getHotdatetime() == null ? other.getHotdatetime() == null : this.getHotdatetime().equals(other.getHotdatetime()))
                && (this.getProductintroimgs() == null ? other.getProductintroimgs() == null : this.getProductintroimgs().equals(other.getProductintroimgs()))
                && (this.getProductparaimgs() == null ? other.getProductparaimgs() == null : this.getProductparaimgs().equals(other.getProductparaimgs()))
                && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getStock() == null) ? 0 : getStock().hashCode());
        result = prime * result + ((getPropic() == null) ? 0 : getPropic().hashCode());
        result = prime * result + ((getIshot() == null) ? 0 : getIshot().hashCode());
        result = prime * result + ((getIsswiper() == null) ? 0 : getIsswiper().hashCode());
        result = prime * result + ((getSwiperpic() == null) ? 0 : getSwiperpic().hashCode());
        result = prime * result + ((getSwipersort() == null) ? 0 : getSwipersort().hashCode());
        result = prime * result + ((getTypeid() == null) ? 0 : getTypeid().hashCode());
        result = prime * result + ((getHotdatetime() == null) ? 0 : getHotdatetime().hashCode());
        result = prime * result + ((getProductintroimgs() == null) ? 0 : getProductintroimgs().hashCode());
        result = prime * result + ((getProductparaimgs() == null) ? 0 : getProductparaimgs().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", price=").append(price);
        sb.append(", stock=").append(stock);
        sb.append(", propic=").append(propic);
        sb.append(", ishot=").append(ishot);
        sb.append(", isswiper=").append(isswiper);
        sb.append(", swiperpic=").append(swiperpic);
        sb.append(", swipersort=").append(swipersort);
        sb.append(", typeid=").append(typeid);
        sb.append(", hotdatetime=").append(hotdatetime);
        sb.append(", productintroimgs=").append(productintroimgs);
        sb.append(", productparaimgs=").append(productparaimgs);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}