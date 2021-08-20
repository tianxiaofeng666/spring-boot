package com.example.springbootdemo.pojo;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * @author shs-cyhlwzytxf
 */
public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
    private final static String METHOD_NAME = "GET";
    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
    public HttpGetWithEntity() {
        super();
    }

    public HttpGetWithEntity(URI uri) {
        super();
        setURI(uri);
    }

    public HttpGetWithEntity(String uri) {
        super();
        setURI(URI.create(uri));
    }
}
