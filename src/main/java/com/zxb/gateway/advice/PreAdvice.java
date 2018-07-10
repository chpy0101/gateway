package com.zxb.gateway.advice;

import com.zxb.gateway.GatewayRequest;

public interface PreAdvice {
    GatewayRequest advice(GatewayRequest request);
}
