package com.zxb.gateway.config;

import java.io.Serializable;
import java.util.List;

/*
配置实体
 */
public class GatewayConfig implements Serializable {

    private String actionName;
    private String proxyUrl;
    private String requestType;
    private List<String> filterClassName;
    private String preAdviceClassName;
    private String coreAdviceName;
    private String behindAdviceClassName;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public List<String> getFilterClassName() {
        return filterClassName;
    }

    public void setFilterClassName(List<String> filterClassName) {
        this.filterClassName = filterClassName;
    }

    public String getPreAdviceClassName() {
        return preAdviceClassName;
    }

    public void setPreAdviceClassName(String preAdviceClassName) {
        this.preAdviceClassName = preAdviceClassName;
    }

    public String getCoreAdviceName() {
        return coreAdviceName;
    }

    public void setCoreAdviceName(String coreAdviceName) {
        this.coreAdviceName = coreAdviceName;
    }

    public String getBehindAdviceClassName() {
        return behindAdviceClassName;
    }

    public void setBehindAdviceClassName(String behindAdviceClassName) {
        this.behindAdviceClassName = behindAdviceClassName;
    }
}
