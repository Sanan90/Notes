package com.example.android.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class NoteBodyFragment extends Fragment {

    static final String ARG_INDEX = "index";
    static final String ARG_INDEX2 = "index2";
    static final String LAST_TEXT = "last_text";
    static String text;
    private int index;
    private int index2;

    public NoteBodyFragment() {
    }

    public static NoteBodyFragment newInstance(int index) {
        NoteBodyFragment fragment = new NoteBodyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX2, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_body, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextInputEditText importantMeetingsBody = view.findViewById(R.id.importantMeetingsBody);
        TextInputEditText shoppingListBody = view.findViewById(R.id.shoppingListBody);
        TextInputEditText seconradyMattersBody = view.findViewById(R.id.seconradyMattersBody);
        TextInputEditText interestingThoughtsBody = view.findViewById(R.id.interestingThoughtsBody);
        TextInputEditText a = seconradyMattersBody;


        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
            index2 = getArguments().getInt(ARG_INDEX2);
        }

        super.onViewCreated(view, savedInstanceState);
        if (index2 == 1) {
            importantMeetingsBody.setVisibility(View.VISIBLE);
            a = importantMeetingsBody;
        } else if (index2 == 2) {
            shoppingListBody.setVisibility(View.VISIBLE);

            a = shoppingListBody;
        } else if (index2 == 3) {
            seconradyMattersBody.setVisibility(View.VISIBLE);

            a = seconradyMattersBody;
        } else if (index2 == 4) {
            interestingThoughtsBody.setVisibility(View.VISIBLE);

            a = interestingThoughtsBody;
        }
    }


}