
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JourneyPlanSearchGetterSetter {

    @Expose
    private List<JourneyPlanSearch> journeyPlanSearch = null;

    public List<JourneyPlanSearch> getJourneyPlanSearch() {
        return journeyPlanSearch;
    }

    public void setJourneyPlanSearch(List<JourneyPlanSearch> journeyPlanSearch) {
        this.journeyPlanSearch = journeyPlanSearch;
    }

}
