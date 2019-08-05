
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockTesterDataGetterSetter {

    @Expose
    private List<StockTesterDatum> stockTesterData = null;

    public List<StockTesterDatum> getStockTesterData() {
        return stockTesterData;
    }

    public void setStockTesterData(List<StockTesterDatum> stockTesterData) {
        this.stockTesterData = stockTesterData;
    }

}
