package com.zxb.gateway.advice.impl;

import com.zxb.gateway.advice.CoreAdvice;
import com.zxb.gateway.util.HttpHelper;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class GetCoreAdvice implements CoreAdvice {

    @Override
    public String proxyTransRequest(String proxyUrl, Object body, HttpServletRequest servlet) throws Exception {
        Map<String, String> param = (Map) body;
        if (param == null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry item : param.entrySet()) {
                sb.append(item.getKey());
                sb.append("=");
                sb.append(item.getValue());
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            proxyUrl = proxyUrl + "?" + sb.toString();
        }
        return HttpHelper.doGet(proxyUrl);
    }
}
