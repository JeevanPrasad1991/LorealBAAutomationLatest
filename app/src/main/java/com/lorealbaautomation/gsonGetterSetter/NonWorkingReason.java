
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonWorkingReason {

    @Expose
    private Integer reasonId;
    @Expose
    private String reason;
    @Expose
    private Boolean entryAllow;
    @Expose
    private Boolean imageAllow;
    @Expose
    private Boolean gPSMandatory;

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getEntryAllow() {
        return entryAllow;
    }

    public void setEntryAllow(Boolean entryAllow) {
        this.entryAllow = entryAllow;
    }

    public Boolean getImageAllow() {
        return imageAllow;
    }

    public void setImageAllow(Boolean imageAllow) {
        this.imageAllow = imageAllow;
    }

    public Boolean getGPSMandatory() {
        return gPSMandatory;
    }

    public void setGPSMandatory(Boolean gPSMandatory) {
        this.gPSMandatory = gPSMandatory;
    }
}
