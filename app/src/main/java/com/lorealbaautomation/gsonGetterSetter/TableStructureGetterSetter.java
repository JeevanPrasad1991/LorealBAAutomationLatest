
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TableStructureGetterSetter {
    @SerializedName("Table_Structure")
    @Expose
    private List<TableStructure> tableStructure = null;

    public List<TableStructure> getTableStructure() {
        return tableStructure;
    }

    @SerializedName("Product_Master")
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

    @SerializedName("Mapping_Visibility")
    @Expose
    private List<MappingVisibility> mappingVisibility = null;

    public List<MappingVisibility> getMappingVisibility() {
        return mappingVisibility;
    }

    @SerializedName("Master_Posm")
    @Expose
    private List<MasterPosm> masterPosm = null;

    public List<MasterPosm> getMasterPosm() {
        return masterPosm;
    }
}
