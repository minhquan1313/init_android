package com.mtb.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {
    private View view;

    public NewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);

        bindComponents();
        bindData();

        return view;
    }

    private void bindComponents() {
    }

    private void bindData() {
    }

    private View findViewById(int id) {
        return view.findViewById(id);
    }
}