
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NonWorkingReasonGetterSetter {

    @Expose
    private List<NonWorkingReason> nonWorkingReason = null;

    public List<NonWorkingReason> getNonWorkingReason() {
        return nonWorkingReason;
    }

    @Expose
    private List<DashboardDataGetter> dashboardData = null;

    public List<DashboardDataGetter> getDashboardData() {
        return dashboardData;
    }

    @Expose
    private List<PromotionMaster> promotionMaster = null;

    public List<PromotionMaster> getPromotionMaster() {
        return promotionMaster;
    }

    @Expose
    private List<NonPromotionReason> nonPromotionReason = null;

    public List<NonPromotionReason> getNonPromotionReason() {
        return nonPromotionReason;
    }
    @Expose
    private List<ConsumerSalesHistory> consumerSalesHistory = null;

    public List<ConsumerSalesHistory> getConsumerSalesHistory() {
        return consumerSalesHistory;
    }

    @Expose
    private List<AttendanceHistory> attendanceHistory = null;

    public List<AttendanceHistory> getAttendanceHistory() {
        return attendanceHistory;
    }
    @Expose
    private List<SalesPerformance> salesPerformance = null;

    public List<SalesPerformance> getSalesPerformance() {
        return salesPerformance;
    }

    @Expose
    private List<DashboardAchivementDetail> dashboardAchivementDetail = null;

    public List<DashboardAchivementDetail> getDashboardAchivementDetail() {
        return dashboardAchivementDetail;
    }

    @Expose
    private List<CustomerVisited> customerVisited = null;

    public List<CustomerVisited> getCustomerVisited() {
        return customerVisited;
    }


    @Expose
    private List<CustomerVisitedCurMonth> customerVisitedCurMonth = null;

    public List<CustomerVisitedCurMonth> getCustomerVisitedCurMonth() {
        return customerVisitedCurMonth;
    }

    public void setCustomerVisitedCurMonth(List<CustomerVisitedCurMonth> customerVisitedCurMonth) {
        this.customerVisitedCurMonth = customerVisitedCurMonth;
    }

}
