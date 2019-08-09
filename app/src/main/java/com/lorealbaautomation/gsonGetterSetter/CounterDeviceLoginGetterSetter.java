
package com.lorealbaautomation.gsonGetterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CounterDeviceLoginGetterSetter {

    @SerializedName("CounterDeviceLogin")
    @Expose
    private List<CounterDeviceLogin> counterDeviceLogin = null;

    public List<CounterDeviceLogin> getCounterDeviceLogin() {
        return counterDeviceLogin;
    }

    public void setCounterDeviceLogin(List<CounterDeviceLogin> counterDeviceLogin) {
        this.counterDeviceLogin = counterDeviceLogin;
    }


}
