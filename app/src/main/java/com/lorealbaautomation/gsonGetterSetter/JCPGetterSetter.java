
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JCPGetterSetter {

    @Expose
    private List<JourneyPlan> journeyPlan = null;

    public List<JourneyPlan> getJourneyPlan() {
        return journeyPlan;
    }

    @Expose
    private List<ProfileDetail> profileDetail = null;

    public List<ProfileDetail> getProfileDetail() {
        return profileDetail;
    }
}
