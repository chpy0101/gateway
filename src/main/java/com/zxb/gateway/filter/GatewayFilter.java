package com.zxb.gateway.filter;

import javax.servlet.http.HttpServletRequest;

public interface GatewayFilter {
    boolean filter(HttpServletRequest request);
}
