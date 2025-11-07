package com.example.todoapplication;

import java.time.LocalDate;

public class Item {

    private int id;
    private String title;
    private String content;
    private LocalDate update;
    public Item(String title,String context,LocalDate update){
        this.title=title;
        this.content=context;
        this.update=update;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String context){
        this.content=context;
    }

    public LocalDate getUpdate() {
        return update;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public void setUpdate(LocalDate update){
        this.update=update;
    }
}
