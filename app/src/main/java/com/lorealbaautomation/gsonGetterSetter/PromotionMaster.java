package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromotionMaster {
    @Expose
    private Integer promotionId;
    @Expose
    private String promotionName;
    @Expose
    private Integer promotionTypeId;
    @Expose
    private String promotionType;
    @Expose
    private Integer counterGroupId;
    @Expose
    private String counterGroupName;
    @Expose
    private Integer productClusterId;
    @Expose
    private String productCluster;
    @Expose
    private String startDate;
    @Expose
    private String imageName;
    @Expose
    private String endDate;
    @Expose
    private String url;

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public Integer getPromotionTypeId() {
        return promotionTypeId;
    }

    public void setPromotionTypeId(Integer promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public Integer getCounterGroupId() {
        return counterGroupId;
    }

    public void setCounterGroupId(Integer counterGroupId) {
        this.counterGroupId = counterGroupId;
    }

    public String getCounterGroupName() {
        return counterGroupName;
    }

    public void setCounterGroupName(String counterGroupName) {
        this.counterGroupName = counterGroupName;
    }

    public Integer getProductClusterId() {
        return productClusterId;
    }

    public void setProductClusterId(Integer productClusterId) {
        this.productClusterId = productClusterId;
    }

    public String getProductCluster() {
        return productCluster;
    }

    public void setProductCluster(String productCluster) {
        this.productCluster = productCluster;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String promotion_exists_state ="";
    String promotion_img ="";
    String promotion_qr_data ="";

    public String getPromotion_exists_state() {
        return promotion_exists_state;
    }

    public void setPromotion_exists_state(String promotion_exists_state) {
        this.promotion_exists_state = promotion_exists_state;
    }

    public String getPromotion_img() {
        return promotion_img;
    }

    public void setPromotion_img(String promotion_img) {
        this.promotion_img = promotion_img;
    }

    public String getPromotion_qr_data() {
        return promotion_qr_data;
    }

    public void setPromotion_qr_data(String promotion_qr_data) {
        this.promotion_qr_data = promotion_qr_data;
    }

    public String getPromotion_currect_ans() {
        return promotion_currect_ans;
    }

    public void setPromotion_currect_ans(String promotion_currect_ans) {
        this.promotion_currect_ans = promotion_currect_ans;
    }

    String promotion_currect_ans ="";

    public String getPromotion_currect_ans_Id() {
        return promotion_currect_ans_Id;
    }

    public void setPromotion_currect_ans_Id(String promotion_currect_ans_Id) {
        this.promotion_currect_ans_Id = promotion_currect_ans_Id;
    }

    String promotion_currect_ans_Id ="";

}
