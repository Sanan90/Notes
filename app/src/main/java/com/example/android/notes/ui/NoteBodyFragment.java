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
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.android.notes.MainActivity;
import com.example.android.notes.R;
import com.example.android.notes.data.CardData;
import com.example.android.notes.observe.Publisher;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class NoteBodyFragment extends Fragment {

    TextInputEditText notes_body_text;
    final String SAVE = "save";
    final String SAVE2 = "save2";
    String saveText;
    String saveText2;
    public static final String ARG_INDEX2 = "index2";
    private CardData index2;
    private Navigation navigation;
    private DatePicker datePicker;
    Publisher publisher;

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
        //  Если есть аргумент, значит получаем cardData которое содержит всю информацию о заметке.
        if (getArguments() != null) {
            index2 = getArguments().getParcelable(ARG_INDEX2);
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
        datePicker = view.findViewById(R.id.inputDate);

        // Передаем на страницу заметки, соответствующий текст.
        notes_body_text.setText(index2.getNotes_body());

        MaterialButton button = view.findViewById(R.id.btn_ok_save_note);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index2 = collectCardData();
                navigation.closeFragment();
            }
        });
    }

    //  Получение даты из datePicker
    private Date getDateFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.datePicker.getYear());
        calendar.set(calendar.MONTH, this.datePicker.getMonth());
        calendar.set(calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return calendar.getTime();
    }


    private CardData collectCardData() {
        String note_title = index2.getNotes_title();
        String note_body = notes_body_text.getText().toString();
        Date date = getDateFromDatePicker();
        CardData answer;
        answer = new CardData(note_title, note_body, index2.isCheckbox(), date);
        answer.setId(index2.getId()); //  Разобраться, работает ли приложение корректно без этого
        return answer;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        this.navigation = activity.getNavigation();
        this.publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        navigation = null;
        Toast.makeText(getContext(), "asssss", Toast.LENGTH_SHORT).show();
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        publisher.notifySingle(index2);
    }
}