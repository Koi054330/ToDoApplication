package com.example.todoapplication.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapplication.Note;
import com.example.todoapplication.NoteAdapter;
import com.example.todoapplication.ViewModel.NoteViewModel;
import com.example.todoapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {
    private ActivityMainBinding activityMainBinding;
    private NoteAdapter noteAdapter;
    private NoteViewModel noteViewModel;
    private List<Note> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(activityMainBinding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init(){
        noteAdapter =new NoteAdapter(list,MainActivity.this);
        activityMainBinding.listView.setAdapter(noteAdapter);
        setClickListener();
        noteViewModel=new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.list().observe(this,notes -> {
            if(notes!=null){
                list.clear();
                list.addAll(notes);
                noteAdapter.update(list);
            }
        });
    }
    private void setClickListener(){
        //长按笔记监听器
        activityMainBinding.listView.setOnItemClickListener(this);
        //点击笔记监听器
        activityMainBinding.listView.setOnItemLongClickListener(this);
        //添加笔记监听器
        activityMainBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });
        activityMainBinding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(MainActivity.this, NoteActivity.class);
        int int_id=list.get(position).id;
        intent.putExtra("id",int_id);
        startActivity(intent);
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        new AlertDialog.Builder(this)
                .setTitle("删除")
                .setMessage("确定要删除此项吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noteViewModel.delete(list.get(position));
                        //todo delete the content from File Storage
                    }
                })
                .setNegativeButton("取消",null)
                .show();
        return true;
    }
}