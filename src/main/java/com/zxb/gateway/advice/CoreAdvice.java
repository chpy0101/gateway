package com.zxb.gateway.advice;

import com.zxb.gateway.GatewayRequest;

import javax.servlet.http.HttpServletRequest;

public interface CoreAdvice {

    String proxyTransRequest(String proxyUrl, GatewayRequest body, HttpServletRequest servlet) throws Exception;

    String utf8 = "utf-8";
}
