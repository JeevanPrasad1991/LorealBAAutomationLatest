package com.lorealbaautomation.gsonGetterSetter;

import java.io.Serializable;

public class InvoiceGetterSetter implements Serializable {
    String product;

    public boolean isPos_sale_flag() {
        return pos_sale_flag;
    }

    public void setPos_sale_flag(boolean pos_sale_flag) {
        this.pos_sale_flag = pos_sale_flag;
    }

    boolean pos_sale_flag = false;

    public String getScan_ean_code_or_enterd_ean_code() {
        return scan_ean_code_or_enterd_ean_code;
    }

    public void setScan_ean_code_or_enterd_ean_code(String scan_ean_code_or_enterd_ean_code) {
        this.scan_ean_code_or_enterd_ean_code = scan_ean_code_or_enterd_ean_code;
    }

    String scan_ean_code_or_enterd_ean_code;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(String product_Id) {
        this.product_Id = product_Id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_rate() {
        return product_rate;
    }

    public void setProduct_rate(String product_rate) {
        this.product_rate = product_rate;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    String product_Id;
    String quantity;
    String product_rate;
    String total_amount;
    String customer_name;
    String mobile_no;
    String store_name;
    String percheasing_tym;

    public String getPercheasing_tym() {
        return percheasing_tym;
    }

    public void setPercheasing_tym(String percheasing_tym) {
        this.percheasing_tym = percheasing_tym;
    }


    public int getReccept_count() {
        return reccept_count;
    }

    public void setReccept_count(int reccept_count) {
        this.reccept_count = reccept_count;
    }

    int reccept_count;

    public String getCst_count() {
        return cst_count;
    }

    public void setCst_count(String cst_count) {
        this.cst_count = cst_count;
    }

    String cst_count = "0";

    public String getCustomer_gender() {
        return customer_gender;
    }

    public void setCustomer_gender(String customer_gender) {
        this.customer_gender = customer_gender;
    }

    String customer_gender;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    String store_address;

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    String visit_date;
    String cst_feed_back_value = "";

    public String getCst_feed_back_value() {
        return cst_feed_back_value;
    }

    public void setCst_feed_back_value(String cst_feed_back_value) {
        this.cst_feed_back_value = cst_feed_back_value;
    }

    public String getFeed_back_type() {
        return feed_back_type;
    }

    public void setFeed_back_type(String feed_back_type) {
        this.feed_back_type = feed_back_type;
    }

    String feed_back_type = "";

    private Integer mtdSales;

    private String counterName;
    private Double target;

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    private Double discount=0.0;

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public Integer getMtdSales() {
        return mtdSales;
    }

    public void setMtdSales(Integer mtdSales) {
        this.mtdSales = mtdSales;
    }

    public Double getActual_price_per_product() {
        return actual_price_per_product;
    }

    public void setActual_price_per_product(Double actual_price_per_product) {
        this.actual_price_per_product = actual_price_per_product;
    }

    Double actual_price_per_product=0.0;

    public Double getDiscounted_value() {
        return discounted_value;
    }

    public void setDiscounted_value(Double discounted_value) {
        this.discounted_value = discounted_value;
    }

    Double discounted_value=0.0;
    private Double mrp;
    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private Double discountValue;

    private Double totalAmount;

}
