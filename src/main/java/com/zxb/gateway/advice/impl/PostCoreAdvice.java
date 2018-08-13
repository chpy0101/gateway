package com.zxb.gateway.advice.impl;

import com.zxb.gateway.GatewayRequest;
import com.zxb.gateway.advice.CoreAdvice;
import com.zxb.gateway.util.HttpHelper;
import com.zxb.gateway.util.JsonMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class PostCoreAdvice implements CoreAdvice {

    @Override
    public String proxyTransRequest(String proxyUrl, GatewayRequest body, HttpServletRequest servlet) throws Exception {

        String contentType = servlet.getContentType();
        proxyUrl += ("?" + servlet.getQueryString() == null ? "" : servlet.getQueryString());

        //有前置处理器时
        if (body.getMapParams() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry entry : body.getMapParams().entrySet()) {
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(entry.getValue());
                stringBuilder.append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            //表单类型
            if (contentType.equals("application/x-www-form-urlencoded")) {
                return HttpHelper.doPost(proxyUrl, contentType, stringBuilder.toString().getBytes(utf8));
            }
            //json格式
            else {
                return HttpHelper.doPost(proxyUrl + stringBuilder.toString(), contentType, body.getJsonContentByte());
            }
        }
        //json无改动直接透传。避免序列化最优性能
        if (body.getJsonContentByte() != null) {
            return HttpHelper.doPost(proxyUrl, contentType, body.getJsonContentByte());
        }
        return "";
    }

}
