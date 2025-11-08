package com.example.todoapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel{
    private NoteRepository repository;
    public LiveData<List<Note>> noteList;
    public NoteViewModel(Application application){
        super(application);
        repository=new NoteRepository(application);
        noteList= repository.list();
    }
    public LiveData<List<Note>> list(){
        return noteList;
    }

    public void insert(Note note){
        repository.insert(note);
    }

    public void update(Note note){
        repository.update(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public Note get(int id){
        return repository.get(id);
    }
}
