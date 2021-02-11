package com.example.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class LaptopFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<DataModel> mLaptopData;
    private Adapter mAdapter;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_laptop, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter(getContext(), mLaptopData);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLaptopData = new ArrayList<>();
        initializeData();
    }
    private void initializeData() {

        String[] LaptopNames = getResources().getStringArray(R.array.Laptop_names);
        String[] LaptopPrices = getResources().getStringArray(R.array.Laptops_price);
        String[] LaptopsInfo = getResources().getStringArray(R.array.Laptops_info);
        String[] LaptopsBuy  = getResources().getStringArray(R.array.Laptop_Buy);
        TypedArray LaptopImages = getResources().obtainTypedArray(R.array.Laptops_Images);

        // Clear the existing data (to avoid duplication).
        mLaptopData.clear();

        // information about each sport.
        for (int i = 0; i < LaptopNames.length; i++) {
            mLaptopData.add(new DataModel(LaptopNames[i], LaptopPrices[i],LaptopsInfo[i],LaptopsBuy[i],
                    LaptopImages.getResourceId(i, 0)));
        }
        LaptopImages.recycle();
        // Notify the adapter of the change.
//        mAdapter.notifyDataSetChanged();
    }
}
