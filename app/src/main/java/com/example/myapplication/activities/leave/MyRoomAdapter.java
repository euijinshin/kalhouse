package com.example.myapplication.activities.leave;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MyRoomAdapter extends RecyclerView.Adapter<MyRoomAdapter.ViewHolder> {

    private ArrayList<JoinMember> mData = null;
    private final Context context;
    Dialog dialog;

    public MyRoomAdapter(Context context, ArrayList<JoinMember> mData) {
        this.mData = mData;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.people_name);
            score = itemView.findViewById(R.id.people_score);

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_details);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    showDialog(pos);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    mData.remove(pos);
                    notifyDataSetChanged();
                    return true;
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.myroom_people, parent, false) ;
        MyRoomAdapter.ViewHolder vh = new MyRoomAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = mData.get(position).getName();
        String score = mData.get(position).getScore();
        holder.name.setText(name);
        holder.score.setText(score);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void showDialog(int pos) {
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        String image = mData.get(pos).getImgUrl();
        String name = mData.get(pos).getName();
        String details = mData.get(pos).getScore();

        ImageView iv_image = dialog.findViewById(R.id.dialog_img);
        TextView tv_name = dialog.findViewById(R.id.dialog_name);
        TextView tv_details = dialog.findViewById(R.id.dialog_details);

        tv_name.setText(name);
        tv_details.setText(details);

        Button confirm_btn = dialog.findViewById(R.id.dialog_confirm);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
