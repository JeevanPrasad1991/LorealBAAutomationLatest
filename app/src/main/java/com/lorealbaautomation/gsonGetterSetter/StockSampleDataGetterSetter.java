
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockSampleDataGetterSetter {

    @Expose
    private List<StockSampleDatum> stockSampleData = null;

    public List<StockSampleDatum> getStockSampleData() {
        return stockSampleData;
    }

    public void setStockSampleData(List<StockSampleDatum> stockSampleData) {
        this.stockSampleData = stockSampleData;
    }

}
