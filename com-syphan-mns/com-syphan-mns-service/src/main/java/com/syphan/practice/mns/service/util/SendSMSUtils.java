package com.syphan.practice.mns.service.util;

import com.syphan.practice.common.api.enumclass.ErrType;
import com.syphan.practice.common.api.exception.BIZException;
import com.syphan.practice.mns.api.exception.SendSMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class SendSMSUtils {

    @Autowired
    private RestTemplate restTemplate;

    private static MediaType mediaType = MediaType.TEXT_HTML;
    private static final String SUCCESS_CODE = "<error_code>0</error_code>";

    public void sendSms(String username, String password, String alias, String endpointURL, String destination, String content) {
        String body = "" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "  <soapenv:Header/>\n" +
                "  <soapenv:Body>\n" +
                "    <tem:BulkSendSms>\n" +
                "      <tem:msisdn>" + destination + "</tem:msisdn>\n" +
                "      <tem:alias>" + alias + "</tem:alias>\n" +
                "      <tem:message>" + content + "</tem:message>\n" +
                "      <tem:sendTime></tem:sendTime>\n" +
                "      <tem:authenticateUser>" + username + "</tem:authenticateUser>\n" +
                "      <tem:authenticatePass>" + password + "</tem:authenticatePass>\n" +
                "    </tem:BulkSendSms>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(endpointURL, httpEntity, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new SendSMSException("SMS interface response status code isn't 200.");
            }
            if (!response.hasBody()) {
                throw new SendSMSException("The response body is empty.");
            }
            if (!(response.getBody() != null ? response.getBody() : "").contains(SUCCESS_CODE)) {
                throw new SendSMSException(response.getBody() != null ? response.getBody() : "The response body is empty.");
            }
        } catch (RestClientException ex) {
            throw BIZException.buildBIZException(ErrType.CONSTRAINT, "", "");
        }
    }
}
