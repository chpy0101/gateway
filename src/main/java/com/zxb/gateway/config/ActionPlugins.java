package com.zxb.gateway.config;

import com.zxb.gateway.advice.BehindAdvice;
import com.zxb.gateway.advice.CoreAdvice;
import com.zxb.gateway.advice.PreAdvice;
import com.zxb.gateway.filter.GatewayFilter;

import java.util.List;

public class ActionPlugins {

    private String proxyUrl;
    private List<? extends GatewayFilter> filterAdvice;
    private PreAdvice preAdvice;
    private CoreAdvice coreAdvice;
    private BehindAdvice behindAdvice;

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    public List<? extends GatewayFilter> getFilterAdvice() {
        return filterAdvice;
    }

    public void setFilterAdvice(List<? extends GatewayFilter> filterAdvice) {
        this.filterAdvice = filterAdvice;
    }

    public PreAdvice getPreAdvice() {
        return preAdvice;
    }

    public void setPreAdvice(PreAdvice preAdvice) {
        this.preAdvice = preAdvice;
    }

    public CoreAdvice getCoreAdvice() {
        return coreAdvice;
    }

    public void setCoreAdvice(CoreAdvice coreAdvice) {
        this.coreAdvice = coreAdvice;
    }

    public BehindAdvice getBehindAdvice() {
        return behindAdvice;
    }

    public void setBehindAdvice(BehindAdvice behindAdvice) {
        this.behindAdvice = behindAdvice;
    }
}
