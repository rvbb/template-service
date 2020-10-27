package com.smartosc.fintech.lms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class InOutLogInterceptorConfig implements ClientHttpRequestInterceptor {

    public final static String API_3RD="API_3RD";
    public final static String API_INTERNAL="API_INTERNAL";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>equest begin>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            log.debug("URI         : {}", request.getURI());
            log.debug("Method      : {}", request.getMethod());
            log.debug("Headers     : {}", request.getHeaders());
            log.debug("Request_Body: {}", new String(body, "UTF-8"));
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>request end>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<esponse begin<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            log.debug("Status_Code  : {}", response.getStatusCode());
            log.debug("Status_Text  : {}", response.getStatusText());
            log.debug("Headers      : {}", response.getHeaders());
            log.debug("Response_Body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<response end<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }
    }
}