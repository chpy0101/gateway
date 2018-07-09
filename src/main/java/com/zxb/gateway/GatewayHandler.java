package com.zxb.gateway;

import com.zxb.gateway.advice.BehindAdvice;
import com.zxb.gateway.advice.CoreAdvice;
import com.zxb.gateway.advice.PreAdvice;
import com.zxb.gateway.config.ActionPlugins;
import com.zxb.gateway.filter.GatewayFilter;
import com.zxb.gateway.util.JsonMapper;

import javax.servlet.http.HttpServletRequest;
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
        Object reqBody = getRequestBody(servletRequest);
        //前置处理器
        PreAdvice preAdvice = actionPlugins.getPreAdvice();
        if (preAdvice != null) {
            reqBody = preAdvice.advice(reqBody);
        }
        //核心处理器
        CoreAdvice coreAdvice = actionPlugins.getCoreAdvice();
        if (coreAdvice == null)
            return null;
        Object resBody = coreAdvice.proxyTransRequest(actionPlugins.getProxyUrl(), reqBody, servletRequest);
        //后置处理器
        BehindAdvice behindAdvice = actionPlugins.getBehindAdvice();
        if (behindAdvice != null) {
            resBody = behindAdvice.advice(resBody);
        }
        return JsonMapper.instance().readValue(resBody.toString(), Object.class);
    }

    private Object getRequestBody(HttpServletRequest request) {
        Object reqBody;
        reqBody = request.getParameterMap();
        if (((Map) reqBody).size() < 1 && request.getContentLength() > 0) {
            byte[] jsonByte = new byte[request.getContentLength()];
            try {
                request.getInputStream().read(jsonByte);
                reqBody = jsonByte;
            } catch (Exception ex) {
            }
        }
        return reqBody;
    }
}
