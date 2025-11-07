package com.example.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.todoapplication.Interface.NoteDao;
import com.example.todoapplication.databinding.ActivityNoteBinding;

import java.time.LocalDate;


public class NoteActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityNoteBinding binding;
    private NoteDao noteDao;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.imageView.setOnClickListener(this);
        noteDao=AppDatabase.getDataBase(this).noteDao();
        init();
    }

    @Override
    protected void onPause(){
        super.onPause();
        save();
    }

    private void save(){
        Log.e("tag","to save--------------------");
        String string = binding.content.getText().toString();
        int n= Math.min(string.length(), 20);
        String summary=string.substring(0,n);
        Note note=new Note(binding.title.getText().toString(),summary,binding.time.getText().toString());
        new Thread(()->{
            long id = noteDao.insert(note);
            Note t = noteDao.getNoteBYId((int) id);
            if(t==null){
                Log.e("tag","fail to save");
            }
            Log.e("tag","successed to save");
        }).start();
        //todo save content to FileStorage
    }
    private void init(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        if(id==-1){
            binding.time.setText(LocalDate.now().toString());
            return;
        }
        Note note= noteDao.getNoteBYId(id);
        if(note==null) {
            binding.time.setText(LocalDate.now().toString());
            return;
        }
        if(!note.title.isEmpty()){
            binding.title.setText(note.title);
        }
        if(!note.update.isEmpty()){
            binding.time.setText(note.update);
        }
        //todo get content from FileStorage
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
