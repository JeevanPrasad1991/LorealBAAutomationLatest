package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaListGetterSetter {
    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("DesignationId")
    @Expose
    private Integer designationId;
    @SerializedName("DesignationName")
    @Expose
    private String designationName;
    @SerializedName("VisitDate")
    @Expose
    private String visitDate;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Integer designationId) {
        this.designationId = designationId;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

}
