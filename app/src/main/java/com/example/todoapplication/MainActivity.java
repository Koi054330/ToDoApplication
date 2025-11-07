package com.example.todoapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.todoapplication.Interface.NoteDao;
import com.example.todoapplication.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    private ActivityMainBinding activityMainBinding;
    private ItemAdapter itemAdapter;

    private NoteDao noteDao;
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
        initItems();
        itemAdapter=new ItemAdapter(list,MainActivity.this);
        activityMainBinding.listView.setAdapter(itemAdapter);
        setClickListener();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.e("tag","onRestart...........");
        itemAdapter.notifyDataSetChanged();
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
                Intent intent=new Intent(MainActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });
    }
    private List<Item> list=new ArrayList<>();
    private void initItems(){
        noteDao = AppDatabase.getDataBase(this).noteDao();
        new Thread(()->{
            List<Note> list1 = noteDao.list();
            if(list1==null){
                Toast.makeText(this,"failed to loading list",Toast.LENGTH_SHORT).show();
                return;
            }
            for(Note note:list1){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Item item=new Item(note.title,note.summary,LocalDate.parse(note.update));
                    item.setId(note.id);
                    list.add(item);
                }
            }
        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(this,"你刚才点击的是item: " + position + " id为: " + id,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this, NoteActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(this,"你刚才长按的是item: " + position + " id为: " + id,Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(this)
                .setTitle("删除")
                .setMessage("确定要删除此项吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Item item=itemAdapter.remove(position);
                        Note note=new Note(item.getTitle(),item.getContent(),item.getUpdate().toString());
                        note.id=item.getId();
                        noteDao.delete(note);
                        //todo delete the content from File Storage
                    }
                })
                .setNegativeButton("取消",null)
                .show();
        return true;
    }
}