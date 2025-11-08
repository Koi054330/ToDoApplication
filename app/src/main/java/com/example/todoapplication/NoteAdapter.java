package com.example.todoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.time.LocalDate;
import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private List<Note> list;
    private Context context;

    public NoteAdapter(List<Note> list, Context context){
        this.list=list;
        this.context=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.item,null);
            viewHolder=new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.title=convertView.findViewById(R.id.title);
            viewHolder.summary=convertView.findViewById(R.id.content);
            viewHolder.update=convertView.findViewById(R.id.update);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Note item=list.get(position);
        viewHolder.title.setText(item.title);
        viewHolder.summary.setText(item.summary);
        viewHolder.update.setText(item.update);

        return convertView;
    }

    public Note remove(int position){
        Note item=list.remove(position);
        notifyDataSetChanged();
        return item;
    }

    public void update(List<Note> notes){
        list=notes;
        notifyDataSetChanged();
    }

    static class ViewHolder{
        public TextView title,summary,update;
    }
}
