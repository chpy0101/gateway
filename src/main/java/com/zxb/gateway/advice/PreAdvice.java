package com.zxb.gateway.advice;

import com.zxb.gateway.GatewayRequest;

import javax.servlet.http.HttpServletRequest;

public interface PreAdvice {
    GatewayRequest advice(GatewayRequest request, HttpServletRequest originalRequest);
}
