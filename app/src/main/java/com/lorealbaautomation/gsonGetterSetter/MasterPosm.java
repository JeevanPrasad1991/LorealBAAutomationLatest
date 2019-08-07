package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterPosm {

    @SerializedName("PosmId")
    @Expose
    private Integer posmId;
    @SerializedName("Posm")
    @Expose
    private String posm;
    @SerializedName("PosmTypeId")
    @Expose
    private Integer posmTypeId;
    @SerializedName("RefImage")
    @Expose
    private String refImage;

    public Integer getPosmId() {
        return posmId;
    }

    public void setPosmId(Integer posmId) {
        this.posmId = posmId;
    }

    public String getPosm() {
        return posm;
    }

    public void setPosm(String posm) {
        this.posm = posm;
    }

    public Integer getPosmTypeId() {
        return posmTypeId;
    }

    public void setPosmTypeId(Integer posmTypeId) {
        this.posmTypeId = posmTypeId;
    }

    public String getRefImage() {
        return refImage;
    }

    public void setRefImage(String refImage) {
        this.refImage = refImage;
    }

}