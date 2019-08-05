
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockPwpGwpDataGetterSetter {
    @Expose
    private List<StockPwpGwpDatum> stockPwpGwpData = null;

    public List<StockPwpGwpDatum> getStockPwpGwpData() {
        return stockPwpGwpData;
    }

    public void setStockPwpGwpData(List<StockPwpGwpDatum> stockPwpGwpData) {
        this.stockPwpGwpData = stockPwpGwpData;
    }

}
