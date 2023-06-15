package com.example.to_dolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter {
    Context ctx;
    private ArrayList<ToDo> ToDos;

    public ToDoAdapter(Context ctx, ArrayList<ToDo> ToDos) {
        this.ctx = ctx;
        this.ToDos = ToDos;
    }

    class VHTodo extends RecyclerView.ViewHolder{
        private TextView tvActivity, tvTime;

        public VHTodo(View rowView) {
            super(rowView);
            this.tvActivity = rowView.findViewById(R.id.tvActivity);
            this.tvTime = rowView.findViewById(R.id.tvTime);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(this.ctx)
                .inflate(R.layout.todo_item, parent, false);
        return new VHTodo(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VHTodo vh = (VHTodo) holder;
        ToDo t = this.ToDos.get(position);
        vh.tvActivity.setText(t.activity);
        vh.tvTime.setText(t.time);
    }

    @Override
    public int getItemCount() {
        return ToDos.size();
    }
}
