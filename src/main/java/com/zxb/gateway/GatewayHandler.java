package com.zxb.gateway;

import com.zxb.gateway.advice.BehindAdvice;
import com.zxb.gateway.advice.CoreAdvice;
import com.zxb.gateway.advice.PreAdvice;
import com.zxb.gateway.config.ActionPlugins;
import com.zxb.gateway.filter.GatewayFilter;
import com.zxb.gateway.util.JsonMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GatewayHandler {

    private ActionPlugins actionPlugins;

    private GatewayHandler() {
    }

    public GatewayHandler(ActionPlugins actionPlugins) {
        this.actionPlugins = actionPlugins;
    }

    public Object handler(HttpServletRequest servletRequest) throws Exception {
        //自定义过滤器
        List<? extends GatewayFilter> filters = actionPlugins.getFilterAdvice();
        if (filters != null) {
            for (GatewayFilter filter : filters)
                if (!filter.filter(servletRequest))
                    return null;
        }
        //请求实体
        GatewayRequest reqBody = getRequestBody(servletRequest);
        //前置处理器
        PreAdvice preAdvice = actionPlugins.getPreAdvice();
        if (preAdvice != null) {
            reqBody = preAdvice.advice(reqBody, servletRequest);
        }
        //核心处理器
        CoreAdvice coreAdvice = actionPlugins.getCoreAdvice();
        if (coreAdvice == null)
            return null;
        String resBody = coreAdvice.proxyTransRequest(actionPlugins.getProxyUrl(), reqBody, servletRequest);
        //后置处理器
        BehindAdvice behindAdvice = actionPlugins.getBehindAdvice();

        return behindAdvice != null ?
                behindAdvice.advice(resBody) :
                JsonMapper.instance().readValue(resBody.toString(), Object.class);
    }

    private GatewayRequest getRequestBody(HttpServletRequest request) {
        GatewayRequest reqBody = new GatewayRequest();
        //键对值
        Enumeration<String> keys = request.getParameterNames();
        Map<String, String> params = new HashMap<>();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            params.put(key, request.getParameter(key));
        }
        reqBody.setMapParams(params);
        //流数据模块
        if (request.getContentLength() > 0) {
            byte[] jsonByte = new byte[request.getContentLength()];
            try {
                request.getInputStream().read(jsonByte);
                reqBody.setJsonContentByte(jsonByte);
            } catch (Exception ex) {
            }
        }
        return reqBody;
    }
}
