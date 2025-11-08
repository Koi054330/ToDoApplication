package com.example.todoapplication.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoapplication.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Note note);

    @Update
    int update(Note note);

    @Delete
    int delete(Note note);

    @Query("select * from note")
    LiveData<List<Note>> list();

    @Query("select * from note where id = :noteId")
    Note getNoteById(int noteId);
}
