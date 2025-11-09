package com.example.todoapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapplication.Note;
import com.example.todoapplication.ViewModel.NoteViewModel;
import com.example.todoapplication.databinding.ActivityNoteBinding;

import java.time.LocalDate;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class NoteActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityNoteBinding binding;
    private NoteViewModel noteViewModel;
    private ThreadPoolExecutor executor;
    private Note note;
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
        init();
    }
    @Override
    protected void onPause(){
        super.onPause();
        save();
    }
    private void save(){
        String context = binding.content.getText().toString();
        String title = binding.title.getText().toString();
        if(context.isEmpty()||title.isEmpty()){
            return;
        }
        String summary=context;
        String update=LocalDate.now().toString();
        Note curNote=new Note(title,summary,update);
        if(note.id==0){
            executor.execute(()->{
                noteViewModel.insert(curNote);
            });
        }else{
            curNote.id=note.id;
            executor.execute(()->{
                noteViewModel.update(curNote);
            });
        }
    }
    private void init(){
        int n=Runtime.getRuntime().availableProcessors();
        executor=new ThreadPoolExecutor(n,2*n,3, TimeUnit.SECONDS,new LinkedBlockingQueue<>(10));
        binding.exit.setOnClickListener(this);
        noteViewModel=new ViewModelProvider(this).get(NoteViewModel.class);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        if(id==-1){
            note=new Note();
            binding.time.setText(LocalDate.now().toString());
            return;
        }
        note=noteViewModel.get(id);
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
        if(!note.summary.isEmpty()){
            binding.content.setText(note.summary);
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
