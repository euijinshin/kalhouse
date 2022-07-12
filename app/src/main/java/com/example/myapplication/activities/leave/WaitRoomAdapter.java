package com.example.myapplication.activities.leave;

import android.app.Activity;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.main.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class WaitRoomAdapter extends RecyclerView.Adapter<WaitRoomAdapter.ViewHolder> {

    private List<RoomData> mData = null ;
    private final Context context;
    Dialog dialog;

    public void setItems(List<RoomData> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView details;
        ImageView profile;

        ViewHolder(View itemView) {
            super(itemView) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, LeaveActivity.class);

//                        intent.putExtra("image", mData.get(pos).getImageUrl());
//                        intent.putExtra("name", mData.get(pos).getRoomName());
//                        intent.putExtra("details", mData.get(pos).getRoomDetails());

                        intent.putExtra("roomId", mData.get(pos).getRoomId());
                        intent.putExtra("user_id", ((WaitRoomActivity) WaitRoomActivity.mContext).user_id);

                        context.startActivity(intent);
                    }
                }
            });

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_details);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    int pos = getAdapterPosition();
                    showDialog(pos);

                    return true;
                }
            });

            name = itemView.findViewById(R.id.tv_name_room);
            details = itemView.findViewById(R.id.tv_details_room);
            profile = itemView.findViewById(R.id.iv_room);



        }
    }

    WaitRoomAdapter(Context context, ArrayList<RoomData> list) {
        this.context = context;
        this.mData = list ;
    }

    @Override
    public WaitRoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.waitroom_item, parent, false) ;
        WaitRoomAdapter.ViewHolder vh = new WaitRoomAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(WaitRoomAdapter.ViewHolder holder, int position) {
        String image = mData.get(position).getImageUrl();
        String name = mData.get(position).getRoomName();
        String details = mData.get(position).getRoomDetails();
        Glide.with(context).load(image).into(holder.profile);
        holder.name.setText(name);
        holder.details.setText(details);
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public void showDialog(int pos) {
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        String image = mData.get(pos).getHostImg();
        String name = mData.get(pos).getHostName();
        String details = mData.get(pos).getHostGender();

        ImageView iv_image = dialog.findViewById(R.id.dialog_img);
        TextView tv_name = dialog.findViewById(R.id.dialog_name);
        TextView tv_details = dialog.findViewById(R.id.dialog_details);

        tv_name.setText(name);
        tv_details.setText(details);
        Glide.with(context).load(image).into(iv_image);

        Button confirm_btn = dialog.findViewById(R.id.dialog_confirm);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}