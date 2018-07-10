package com.zxb.gateway;

import java.util.Map;

public class GatewayRequest {

    private Map<String,String> mapParams;
    private byte[] jsonContentByte;

    public Map<String, String> getMapParams() {
        return mapParams;
    }

    public void setMapParams(Map<String, String> mapParams) {
        this.mapParams = mapParams;
    }

    public byte[] getJsonContentByte() {
        return jsonContentByte;
    }

    public void setJsonContentByte(byte[] jsonContentByte) {
        this.jsonContentByte = jsonContentByte;
    }
}
