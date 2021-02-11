package com.example.android.notes.ui;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.notes.MainActivity;
import com.example.android.notes.R;
import com.example.android.notes.data.CardData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class NoteBodyFragment extends Fragment {

    TextInputEditText notes_body_text;
    final String SAVE = "save";
    final String SAVE2 = "save2";
    String saveText;
    String saveText2;
    SharedPreferences sPref;
    public static final String ARG_INDEX2 = "index2";
    private CardData index2;
    private Navigation navigation;

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
        if (savedInstanceState != null) {
            saveText2 = savedInstanceState.getString(SAVE2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_body, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notes_body_text = view.findViewById(R.id.note_body_text);

        //  Если есть аргумент, значит получаем cardData которое содержит всю информацию о заметке.
        if (getArguments() != null) {
            index2 = getArguments().getParcelable(ARG_INDEX2);
        }

        sPref = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);

        if (sPref.getString(index2.getNotes_title(), "") != null) {
            index2.setNotes_body(sPref.getString(index2.getId(), ""));
        }
        // Передаем на страницу заметки, соответствующий текст.
        notes_body_text.setText(index2.getNotes_body());


        MaterialButton button = view.findViewById(R.id.btn_ok_save_note);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index2.setNotes_body(notes_body_text.getText().toString());
                sPref = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sPref.edit();
                editor.putString(index2.getId(), index2.getNotes_body());
                editor.commit();
                getActivity().finish();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        this.navigation = activity.getNavigation();
    }
}