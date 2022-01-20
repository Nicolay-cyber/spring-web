package com.geekbrains.spring.web.endpoints;

import com.geekbrains.spring.web.services.ProductsService;
import com.geekbrains.spring.web.soap.products.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductsEndpoint {
    private static final String NAMESPACE_URI = "http://www.geekbrains.com/spring/web/products";
    private final ProductsService productsService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productsService.getById(request.getId()));
        return response;
    }

/*
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
        xmlns:f="http://www.geekbrains.com/spring/web/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getProductByTitleRequest>
                    <f:title>TV</f:title>
                </f:getProductByTitleRequest>
            </soapenv:Body>
        </soapenv:Envelope>
* */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByTitleRequest")
    @ResponsePayload
    public GetProductByTitleResponse getProductByName(@RequestPayload GetProductByTitleRequest request) {
        GetProductByTitleResponse response = new GetProductByTitleResponse();
        response.setProduct(productsService.getByTitle(request.getTitle()));
        return response;
    }

    /*
        Запрос: POST http://localhost:8189/app/web
        Header -> Content-Type: text/xml

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
        xmlns:f="http://www.geekbrains.com/spring/web/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductRequest")
    @ResponsePayload
    public GetAllProductResponse getAllProducts(@RequestPayload GetAllProductRequest request) {
        GetAllProductResponse response = new GetAllProductResponse();
        productsService.getAllProducts().forEach(response.getProducts()::add);
        return response;
    }
}
