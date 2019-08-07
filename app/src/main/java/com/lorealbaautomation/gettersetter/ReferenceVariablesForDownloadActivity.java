package com.lorealbaautomation.gettersetter;


import com.lorealbaautomation.gsonGetterSetter.JCPGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.NonStockReasonGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.NonWorkingReasonGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.StockDataGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.StockPwpGwpDataGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.StockSampleDataGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.StockTesterDataGetterSetter;
import com.lorealbaautomation.gsonGetterSetter.TableStructureGetterSetter;

/**
 * Created by jeevanp on 15-12-2017.
 */

public class ReferenceVariablesForDownloadActivity {
    protected TableStructureGetterSetter tableStructureObj,product_masterObject,inwardSales_POObject,mapping_visibilityObject,posmmasterObject;
    protected JCPGetterSetter jcpObject,baListObject,mappingCounterGroupBrandObject;
    protected NonWorkingReasonGetterSetter nonWorkingObj,dashboardObject,promotionMasterObject,nonpromotionReason,cst_sales_histryObject,
            attendance_historyObject,salesperformanceObject,dashboard_value_achievementObject,customer_visited_count_object,customer_visited_curmonth_object;
    protected NonStockReasonGetterSetter reasonObj;
    protected StockDataGetterSetter stockDataObj;
    protected StockPwpGwpDataGetterSetter stockPowObj;
    protected StockTesterDataGetterSetter stockTesterObj;
    protected StockSampleDataGetterSetter stockSampleObj;
}
