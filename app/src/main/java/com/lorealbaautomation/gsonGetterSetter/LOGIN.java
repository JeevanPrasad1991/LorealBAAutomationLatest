
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LOGIN {

    @SerializedName("CounterId")
    @Expose
    private Integer counterId;
    @SerializedName("VisitDate")
    @Expose
    private String visitDate;
    @SerializedName("AppVersion")
    @Expose
    private Integer appVersion;
    @SerializedName("AppPath")
    @Expose
    private String appPath;

    public Integer getCounterId() {
        return counterId;
    }

    public void setCounterId(Integer counterId) {
        this.counterId = counterId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public Integer getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Integer appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

}
