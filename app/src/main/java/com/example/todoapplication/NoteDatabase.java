package com.example.todoapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todoapplication.Interface.NoteDao;

@Database(entities = {Note.class},version = 1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static volatile NoteDatabase INSTANCE;

    public static NoteDatabase getDataBase(final Context context){
        if(INSTANCE==null){
            synchronized (NoteDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class,"note-database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
