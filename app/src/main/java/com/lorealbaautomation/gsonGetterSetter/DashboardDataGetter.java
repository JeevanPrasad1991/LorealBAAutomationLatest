package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardDataGetter {
    @Expose
    private Integer id;
    @Expose
    private String label;
    @Expose
    private Double value;
    @Expose
    private Double percentage;
    @Expose
    private String url;
    @Expose
    private String imageName;
    @Expose
    private Integer detailShow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getDetailShow() {
        return detailShow;
    }

    public void setDetailShow(Integer detailShow) {
        this.detailShow = detailShow;
    }

}
