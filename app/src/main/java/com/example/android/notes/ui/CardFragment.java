package com.example.android.notes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.android.notes.MainActivity;
import com.example.android.notes.data.CardData;
import com.example.android.notes.observe.Publisher;
import com.example.android.notes.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class CardFragment extends Fragment {

    private static final String ARG_CARD_DATA = "Param_CardData";

    private CardData cardData;      //  Данные по карточке
    private Publisher publisher;    //  Паблишер, с его помошью обмениваемся данными
    private Navigation navigation;
    private TextInputEditText note_title;
    private TextInputEditText note_body;
    private DatePicker datePicker;
    private MaterialButton btn_for_save_update;

    //  Для редактирования данных
    public static CardFragment newInstance(CardData cardData) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    //  Для добавления новых данных
    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    //  Метод вызываеться в момент соединения фрагмента с Активити
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();

    }

    @Override
    public void onDetach() {
        publisher = null;
        navigation = null;
        Toast.makeText(getContext(), "asssss", Toast.LENGTH_SHORT).show();
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        initView(view);
        //  Если cardData пустая то это добавление
        if (cardData != null) {
            populateView();
        }
        return view;
    }

    //  Здесь соберем данные из vievs
    @Override
    public void onStop() {
        super.onStop();
//        cardData = collectCardData();
    }

    //  Здесь соберем данные из паблишер
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        publisher.notifySingle(cardData);
    }

    private CardData collectCardData () {
        String note_title = this.note_title.getText().toString();
        String note_body = this.note_body.getText().toString();
        Date date = getDateFromDatePicker();
        if (cardData != null) {
            CardData answer;
            answer = new CardData(note_title, note_body, cardData.isCheckbox(), date);
            answer.setId(cardData.getId()); //  Разобраться, работает ли приложение корректно без этого
            return answer;
        }   else {
            return new CardData(note_title, note_body, false, date);
        }
    }

    //  Получение даты из datePicker
    private Date getDateFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.datePicker.getYear());
        calendar.set(calendar.MONTH, this.datePicker.getMonth());
        calendar.set(calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return calendar.getTime();
    }

    private void initView(View view) {
        note_title = view.findViewById(R.id.inputNoteTitle);
        note_body = view.findViewById(R.id.inputNoteBody);
        datePicker = view.findViewById(R.id.inputDate);
        btn_for_save_update = view.findViewById(R.id.btn_to_save_update);

        btn_for_save_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardData = collectCardData();
                navigation.closeFragment();
            }
        });
    }

    private void populateView() {
        note_title.setText(cardData.getNotes_title());
        note_body.setText(cardData.getNotes_body());
        initDatePicker(cardData.getDate());
    }

    //  Установка даты в datePicker
    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR), calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH), null);
    }

}