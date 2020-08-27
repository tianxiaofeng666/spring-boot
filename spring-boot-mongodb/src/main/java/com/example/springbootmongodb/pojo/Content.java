package com.example.springbootmongodb.pojo;

import com.example.springbootmongodb.annotation.AutoIncKey;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_content")
public class Content {
    @AutoIncKey
    @Id
    private long id;

    private String title;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Content(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public Content(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
