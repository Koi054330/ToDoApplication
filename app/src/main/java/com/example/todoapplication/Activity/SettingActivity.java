package com.example.todoapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todoapplication.R;
import com.example.todoapplication.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    private void init(){
        setClick();
        setSync();
    }
    private void setSync(){
        sp=getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean isLogged = sp.getBoolean("isLogged", false);
        if (isLogged){
            binding.sync.setVisibility(View.VISIBLE);
            binding.syncState.setVisibility(View.VISIBLE);
        }else{
            binding.sync.setVisibility(View.GONE);
            binding.syncState.setVisibility(View.GONE);
        }
        boolean isSync = sp.getBoolean("isSync", false);
        if(isSync){
            binding.sync.setTextColor(getResources().getColor(R.color.green));
            binding.syncState.setImageResource(R.drawable.success);
        }else{
            binding.sync.setTextColor(getResources().getColor(R.color.red));
            binding.syncState.setImageResource(R.drawable.fail);
        }
    }
    private void setClick(){
        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.constraintlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.constraintlayout2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boolean isSync = sp.getBoolean("isSync", false);
                isSync=!isSync;
                StringBuilder sb=new StringBuilder();
                sb.append("云同步");
                if(isSync){
                    sb.append("已开启");
                }else{
                    sb.append("已关闭");
                }
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("isSync",isSync);
                editor.apply();
                setSync();
                Toast.makeText(SettingActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}