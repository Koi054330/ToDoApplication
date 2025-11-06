package com.example.todoapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todoapplication.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    private ActivityMainBinding activityMainBinding;
    private ItemAdapter itemAdapter;
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
    }
    @Override
    protected void onResume(){
        super.onResume();
        activityMainBinding.listView.setOnItemClickListener(this);
        activityMainBinding.listView.setOnItemLongClickListener(this);
    }
    private List<Item> list=new ArrayList<>();
    private void initItems(){
        for(int i=0;i<10;i++){
            Item item=new Item(String.valueOf(i),"neirong", LocalDate.now());
            list.add(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"你刚才点击的是item: " + position + " id为: " + id,Toast.LENGTH_SHORT).show();
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
                        itemAdapter.remove(position);
                    }
                })
                .setNegativeButton("取消",null)
                .show();
        return true;
    }
}