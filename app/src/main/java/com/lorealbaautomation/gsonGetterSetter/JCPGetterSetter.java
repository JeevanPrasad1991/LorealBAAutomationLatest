
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JCPGetterSetter {
    @SerializedName("Mapping_JourneyPlan")
    @Expose
    private List<JourneyPlan> journeyPlan = null;

    public List<JourneyPlan> getJourneyPlan() {
        return journeyPlan;
    }


    @SerializedName("BA_List")
    @Expose
    private List<BaListGetterSetter> bAList = null;

    public List<BaListGetterSetter> getBAList() {
        return bAList;
    }

    @SerializedName("Mapping_CounterGroup_Brand")
    @Expose
    private List<MappingCounterGroupBrand> mappingCounterGroupBrand = null;

    public List<MappingCounterGroupBrand> getMappingCounterGroupBrand() {
        return mappingCounterGroupBrand;
    }

}
