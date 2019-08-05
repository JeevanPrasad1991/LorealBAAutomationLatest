
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TableStructureGetterSetter {

    @Expose
    private List<TableStructure> tableStructure = null;

    public List<TableStructure> getTableStructure() {
        return tableStructure;
    }

    @Expose
    private List<ProductMaster> productMaster = null;

    public List<ProductMaster> getProductMaster() {
        return productMaster;
    }

    @Expose
    private List<InwardSalesPO> inwardSalesPO = null;

    public List<InwardSalesPO> getInwardSalesPO() {
        return inwardSalesPO;
    }

    public void setInwardSalesPO(List<InwardSalesPO> inwardSalesPO) {
        this.inwardSalesPO = inwardSalesPO;
    }


}
