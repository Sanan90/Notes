package com.example.android.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link noteBodyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class noteBodyFragment extends Fragment {

    static final String ARG_INDEX = "index";
    static final String ARG_INDEX2 = "index2";
    private int index;
    private int index2;

    public noteBodyFragment() {
        // Required empty public constructor
    }

    public static noteBodyFragment newInstance(int index) {
        noteBodyFragment fragment = new noteBodyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX2, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
            index2 = getArguments().getInt(ARG_INDEX2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_body, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextInputEditText importantMeetingsBody = view.findViewById(R.id.importantMeetingsBody);
        TextInputEditText shoppingListBody = view.findViewById(R.id.shoppingListBody);
        TextInputEditText seconradyMattersBody = view.findViewById(R.id.seconradyMattersBody);
        TextInputEditText interestingThoughtsBody = view.findViewById(R.id.interestingThoughtsBody);

        super.onViewCreated(view, savedInstanceState);
        if (index2 == 1) {
            importantMeetingsBody.setVisibility(View.VISIBLE);
        }   else if (index2 ==2) {
            shoppingListBody.setVisibility(View.VISIBLE);
        }   else if (index2 ==3) {
            seconradyMattersBody.setVisibility(View.VISIBLE);
        }   else if (index2 ==4) {
            interestingThoughtsBody.setVisibility(View.VISIBLE);
        }
    }
}