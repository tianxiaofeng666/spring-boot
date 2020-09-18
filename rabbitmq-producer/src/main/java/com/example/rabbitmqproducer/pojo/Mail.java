package com.example.rabbitmqproducer.pojo;

public class Mail {

    private long id;

    private String topic;

    private String eamil;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Mail() {
    }

    public Mail(long id, String topic, String eamil, String text) {
        this.id = id;
        this.topic = topic;
        this.eamil = eamil;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    @Override
    public String toString() {
        return "主题：" + topic + ",内容：" + text + ",接受者：" + eamil;
    }
}
