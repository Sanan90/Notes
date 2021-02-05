package com.example.android.notes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class NoteBodyFragment extends Fragment {

    static final String ARG_INDEX2 = "index2";
    private CardData index2;

    public NoteBodyFragment() {
    }

    //  Создаем фрагмент и передаем в каестве аргумента cardData, которое содержит название заметки и содержимое.
    public static NoteBodyFragment newInstance(CardData cardData) {
        NoteBodyFragment fragment = new NoteBodyFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX2, cardData);
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
        super.onViewCreated(view, savedInstanceState);
        TextInputEditText notes_body_text = view.findViewById(R.id.note_body_text);

        //  Если есть аргумент, значит получаем cardData которое содержит всю информацию о заметке.
        if (getArguments() != null) {
            index2 = getArguments().getParcelable(ARG_INDEX2);
        }

        // Передаем на страницу заметки, соответствующий текст.
        notes_body_text.setText(index2.getNotes_body());

        MaterialButton button = view.findViewById(R.id.btn_ok_save_note);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index2.setNotes_body(notes_body_text.getText().toString());
            }
        });

    }


}