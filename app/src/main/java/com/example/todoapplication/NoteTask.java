package com.example.todoapplication;

import android.os.AsyncTask;

import com.example.todoapplication.Interface.NoteDao;

public class NoteTask extends AsyncTask<Note,Void,Void> {
    private final NoteDao noteDao;
    public NoteTask(NoteDao noteDao){
        this.noteDao=noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        for(Note note:notes){
            noteDao.insert(note);
        }
        return null;
    }
}
