package com.api.marvel.service.impl;

import com.api.marvel.exception.ApiErrorException;
import com.api.marvel.service.HttpClientService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class HttpClientServiceImpl implements HttpClientService {

    private RestTemplate restTemplate;

    public HttpClientServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T httpGet(String url, Map<String, String> params, Class<T> responseType) {

        String finalUrl = buildFinalUrl(url, params);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.GET, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            String errorMessage = String.format("Error consumiento del API [ {} - {} ], codigo de respuesta es: {}",
                    HttpMethod.GET, finalUrl, response.getStatusCode().value());
            throw new ApiErrorException(errorMessage);
        }

        return response.getBody();
    }

    @Override
    public <T, R> T httpPost(String url, Map<String, String> params, Class<T> responseType, R bodyRequest) {

        String finalUrl = buildFinalUrl(url, params);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(bodyRequest, httpHeaders);

        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.POST, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value() || response.getStatusCode().value() != HttpStatus.CREATED.value()) {
            String errorMessage = String.format("Error consumiento del API [ {} - {} ], codigo de respuesta es: {}",
                    HttpMethod.POST, finalUrl, response.getStatusCode().value());
            throw new ApiErrorException(errorMessage);
        }
        return response.getBody();
    }

    @Override
    public <T, R> T httpPut(String url, Map<String, String> params, Class<T> responseType, R bodyRequest) {
        String finalUrl = buildFinalUrl(url, params);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(bodyRequest, httpHeaders);

        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.PUT, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            String errorMessage = String.format("Error consumiento del API [ {} - {} ], codigo de respuesta es: {}",
                    HttpMethod.PUT, finalUrl, response.getStatusCode().value());
            throw new ApiErrorException(errorMessage);
        }
        return response.getBody();
    }

    @Override
    public <T> T httpDelete(String url, Map<String, String> params, Class<T> responseType) {
        String finalUrl = buildFinalUrl(url, params);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.DELETE, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            String errorMessage = String.format("Error consumiento del API [ {} - {} ], codigo de respuesta es: {}",
                    HttpMethod.DELETE, finalUrl, response.getStatusCode().value());
            throw new ApiErrorException(errorMessage);
        }

        return response.getBody();
    }

    private static String buildFinalUrl(String url, Map<String, String> params) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url); // ?nombre=Santiago

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) { // "nombre" : "Santiago", "apellido" : "Perez"
                uriBuilder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        String finalUrl = uriBuilder.build().toUriString();
        return finalUrl;
    }
}
