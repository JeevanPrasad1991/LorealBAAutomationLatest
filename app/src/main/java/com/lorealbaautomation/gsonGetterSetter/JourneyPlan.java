
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JourneyPlan {
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;
    @SerializedName("CounterId")
    @Expose
    private Integer counterId;
    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("ChainId")
    @Expose
    private Integer chainId;
    @SerializedName("ChainName")
    @Expose
    private String chainName;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Landmark")
    @Expose
    private String landmark;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("CounterName")
    @Expose
    private String counterName;
    @SerializedName("IMEI")
    @Expose
    private String iMEI;
    @SerializedName("ChannelId")
    @Expose
    private Integer channelId;
    @SerializedName("ChannelName")
    @Expose
    private String channelName;
    @SerializedName("StoreTypeId")
    @Expose
    private Integer storeTypeId;
    @SerializedName("StoreTypeName")
    @Expose
    private String storeTypeName;
    @SerializedName("ClassId")
    @Expose
    private Integer classId;
    @SerializedName("ClassName")
    @Expose
    private String className;
    @SerializedName("CounterGroupId")
    @Expose
    private Integer counterGroupId;
    @SerializedName("CounterUploadStatus")
    @Expose
    private String counterUploadStatus;
    @SerializedName("BAUploadStatus")
    @Expose
    private String bAUploadStatus;
    @SerializedName("MID")
    @Expose
    private Integer mID;
    @SerializedName("BID")
    @Expose
    private Integer bID;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getCounterId() {
        return counterId;
    }

    public void setCounterId(Integer counterId) {
        this.counterId = counterId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getIMEI() {
        return iMEI;
    }

    public void setIMEI(String iMEI) {
        this.iMEI = iMEI;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public String getStoreTypeName() {
        return storeTypeName;
    }

    public void setStoreTypeName(String storeTypeName) {
        this.storeTypeName = storeTypeName;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getCounterGroupId() {
        return counterGroupId;
    }

    public void setCounterGroupId(Integer counterGroupId) {
        this.counterGroupId = counterGroupId;
    }

    public String getCounterUploadStatus() {
        return counterUploadStatus;
    }

    public void setCounterUploadStatus(String counterUploadStatus) {
        this.counterUploadStatus = counterUploadStatus;
    }

    public String getBAUploadStatus() {
        return bAUploadStatus;
    }

    public void setBAUploadStatus(String bAUploadStatus) {
        this.bAUploadStatus = bAUploadStatus;
    }

    public Integer getMID() {
        return mID;
    }

    public void setMID(Integer mID) {
        this.mID = mID;
    }

    public Integer getBID() {
        return bID;
    }

    public void setBID(Integer bID) {
        this.bID = bID;
    }

}
