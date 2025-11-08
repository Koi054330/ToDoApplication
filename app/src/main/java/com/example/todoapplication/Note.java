package com.example.todoapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "update")
    public String update;

    @ColumnInfo(name = "summary")
    public String summary;

    @ColumnInfo(name = "title")
    public String title;

    public Note(String title,String summary,String update){
        this.title=title;
        this.update=update;
        this.summary=summary;
    }
    public Note(){

    }
}
