package com.example.todoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.todoapplication.databinding.ActivityMainBinding;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private List<Item> list;
    private Context context;

    public ItemAdapter(List<Item> list,Context context){
        this.list=list;
        this.context=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.item,null);
            viewHolder=new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.title=convertView.findViewById(R.id.title);
            viewHolder.content=convertView.findViewById(R.id.content);
            viewHolder.update=convertView.findViewById(R.id.update);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Item item=list.get(position);
        viewHolder.title.setText(item.getTitle());
        viewHolder.content.setText(item.getContent());
        viewHolder.update.setText(item.getUpdate().toString());

        return convertView;
    }

    public void remove(int position){
        list.remove(position);
        notifyDataSetChanged();
    }

    static class ViewHolder{
        public TextView title,content,update;
    }
}
