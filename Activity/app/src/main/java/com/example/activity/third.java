package com.example.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link third#newInstance} factory method to
 * create an instance of this fragment.
 */
public class third extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public third() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment third.
     */
    // TODO: Rename and change types and number of parameters
    public static third newInstance(String param1, String param2) {
        third fragment = new third();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        List<Integer> images = new LinkedList<>();
        images.add(R.drawable.cat1);
        images.add(R.drawable.cat2);
        images.add(R.drawable.cat3);
        images.add(R.drawable.cat4);
        images.add(R.drawable.cat5);
        images.add(R.drawable.cat6);
        TableLayout table = new TableLayout(getActivity());
        TextView txt = new TextView(getActivity());
        txt.setText("Kitties:");
        table.addView(txt);

        int counter = 0;
        for (int i = 1; i <= 3; i++) {

            TableRow row = new TableRow(getActivity());
            row.setGravity(Gravity.CENTER);
            for (int j = 1; j <= 2; j++) {
                ImageView imgView = new ImageView(getActivity());
                imgView.setImageResource(images.get(counter));
                row.addView(imgView);
                counter++;
            }
            table.addView(row);
        }
        //return inflater.inflate(R.layout.fragment_third, container, false);
        return table;
    }

}