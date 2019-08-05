
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockDataGetterSetter {

    @Expose
    private List<StockDatum> stockData = null;

    public List<StockDatum> getStockData() {
        return stockData;
    }

    public void setStockData(List<StockDatum> stockData) {
        this.stockData = stockData;
    }

}
