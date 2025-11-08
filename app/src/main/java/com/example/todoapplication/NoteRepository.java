package com.example.todoapplication;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todoapplication.Interface.NoteDao;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> list;

    private ThreadPoolExecutor executor;

    public NoteRepository(Application application){
        NoteDatabase noteDatabase=NoteDatabase.getDataBase(application);
        noteDao=noteDatabase.noteDao();
        list=noteDao.list();
        executor=new ThreadPoolExecutor(3,6,3, TimeUnit.SECONDS,new LinkedBlockingQueue<>(10));
    }
    public LiveData<List<Note>> list(){
        return list;
    }
    public void insert(Note note){
        executor.execute(()-> noteDao.insert(note));
    }
    public void update(Note note){
        executor.execute(()-> noteDao.update(note));
    }
    public void delete(Note note){
        executor.execute(()-> noteDao.delete(note));
    }
    public Note get(int id) {
        Note note=null;
        try {
            note = executor.submit(() -> noteDao.getNoteById(id)).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return note;
    }
}
