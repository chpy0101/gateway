package com.zxb.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zxb.gateway.advice.BehindAdvice;
import com.zxb.gateway.advice.CoreAdvice;
import com.zxb.gateway.advice.PreAdvice;
import com.zxb.gateway.advice.impl.GetCoreAdvice;
import com.zxb.gateway.advice.impl.PostCoreAdvice;
import com.zxb.gateway.config.ActionPlugins;
import com.zxb.gateway.config.GatewayConfig;
import com.zxb.gateway.util.JsonMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum GatewayFactory {

    Instance;

    GatewayFactory() {
        createActionMap();
    }

    private static Map<String, ActionPlugins> actionPluginsMap;

    private void createActionMap() {
        try {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("gatewayConfig.json");
            byte[] byt = new byte[stream.available()];
            stream.read(byt);
            List<GatewayConfig> configs = JsonMapper.instance().readValue(byt, new TypeReference<List<GatewayConfig>>() {
            });

            actionPluginsMap = new HashMap<>();

            for (GatewayConfig config : configs) {
                ActionPlugins plugin = new ActionPlugins();
                plugin.setProxyUrl(config.getProxyUrl());
                //自定义过滤器
                if (config.getFilterClassName() != null && config.getFilterClassName().size() > 0) {
                    plugin.setFilterAdvice(createInstance(config.getFilterClassName()));
                }
                //前置处理器
                if (config.getPreAdviceClassName() != null && !config.getPreAdviceClassName().isEmpty()) {
                    plugin.setPreAdvice((PreAdvice) Class.forName(config.getPreAdviceClassName()).newInstance());
                }
                //后置处理器
                if (config.getBehindAdviceClassName() != null && !config.getBehindAdviceClassName().isEmpty()) {
                    plugin.setBehindAdvice((BehindAdvice) Class.forName(config.getBehindAdviceClassName()).newInstance());
                }
                //核心处理器
                if (config.getCoreAdviceName() != null && !config.getCoreAdviceName().isEmpty()) {
                    plugin.setCoreAdvice((CoreAdvice) Class.forName(config.getCoreAdviceName()).newInstance());
                } else {
                    //默认核心处理器分为get和post
                    plugin.setCoreAdvice(config.getRequestType().equals("GET") ? new GetCoreAdvice() : new PostCoreAdvice());
                }
                actionPluginsMap.put(config.getActionName(), plugin);
            }
        } catch (IOException e) {
            throw new RuntimeException("未配置");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private List createInstance(List<String> className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List instances = new ArrayList();
        for (String cls : className) {
            instances.add(Class.forName(cls).newInstance());
        }
        return instances;
    }

    public GatewayHandler getHandler(String actionName) {
        return new GatewayHandler(actionPluginsMap.get(actionName));
    }
}
