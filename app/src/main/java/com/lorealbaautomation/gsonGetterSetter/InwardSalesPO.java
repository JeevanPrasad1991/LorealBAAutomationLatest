
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InwardSalesPO {

    @Expose
    private Integer storeId;
    @Expose
    private String storeCode;
    @Expose
    private Integer counterId;
    @Expose
    private String counterCode;
    @Expose
    private Integer productId;
    @Expose
    private String productCode;
    @Expose
    private String grnRefNo;
    @Expose
    private Integer qTY;
    @Expose
    private Integer mRP;
    @Expose
    private String uOM;
    @Expose
    private String grnDate;
    @Expose
    private String grnType;
    @Expose
    private String productName;

    public String getProductName() {
        return productName;
    }

    private int stock=0;
    private int reasonId=0;
    private String Reason="";

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Integer getCounterId() {
        return counterId;
    }

    public void setCounterId(Integer counterId) {
        this.counterId = counterId;
    }

    public String getCounterCode() {
        return counterCode;
    }

    public void setCounterCode(String counterCode) {
        this.counterCode = counterCode;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getGrnRefNo() {
        return grnRefNo;
    }

    public void setGrnRefNo(String grnRefNo) {
        this.grnRefNo = grnRefNo;
    }

    public Integer getQTY() {
        return qTY;
    }

    public void setQTY(Integer qTY) {
        this.qTY = qTY;
    }

    public Integer getMRP() {
        return mRP;
    }

    public void setMRP(Integer mRP) {
        this.mRP = mRP;
    }

    public String getUOM() {
        return uOM;
    }

    public void setUOM(String uOM) {
        this.uOM = uOM;
    }

    public String getGrnDate() {
        return grnDate;
    }

    public void setGrnDate(String grnDate) {
        this.grnDate = grnDate;
    }

    public String getGrnType() {
        return grnType;
    }

    public void setGrnType(String grnType) {
        this.grnType = grnType;
    }
    String accept_flag="";

    public String getAccept_flag() {
        return accept_flag;
    }

    public void setAccept_flag(String accept_flag) {
        this.accept_flag = accept_flag;
    }

    private int resion_id=0;
    private String resion="";


    public int getResion_id() {
        return resion_id;
    }

    public void setResion_id(int resion_id) {
        this.resion_id = resion_id;
    }

    public String getResion() {
        return resion;
    }

    public void setResion(String resion) {
        this.resion = resion;
    }
}
