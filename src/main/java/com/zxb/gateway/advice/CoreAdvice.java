package com.zxb.gateway.advice;

import javax.servlet.http.HttpServletRequest;

public interface CoreAdvice {

    String proxyTransRequest(String proxyUrl, Object body, HttpServletRequest servlet) throws Exception;

    String utf8 = "utf-8";
}
