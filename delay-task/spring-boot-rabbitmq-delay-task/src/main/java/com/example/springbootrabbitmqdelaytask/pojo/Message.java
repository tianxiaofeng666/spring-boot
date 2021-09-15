package com.example.springbootrabbitmqdelaytask.pojo;

import java.io.Serializable;

/**
 * @author shs-cyhlwzytxf
 */
public class Message implements Serializable {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
