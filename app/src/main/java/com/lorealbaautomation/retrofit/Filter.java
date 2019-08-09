package com.lorealbaautomation.retrofit;

interface Filter<ProductMaster,String> {
    public boolean isMatched(ProductMaster object, String text);
}