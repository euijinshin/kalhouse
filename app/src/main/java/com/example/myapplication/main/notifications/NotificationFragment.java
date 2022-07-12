package com.example.myapplication.main.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.main.notifications.NotificationAdapter;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    View view;
    private ArrayList<String> itemList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;

    public static Fragment fragment;
    private RecyclerView recyclerView;

    public NotificationFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_notification, container, false);
        fragment = this;
        recyclerView = (RecyclerView) view.findViewById(R.id.rvNotification);
        notificationAdapter = new NotificationAdapter(getActivity(), itemList);
        recyclerView.setAdapter(notificationAdapter);

        notificationAdapter.notifyDataSetChanged();

        return view;
    }

}
