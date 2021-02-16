package com.example.android.notes.data;

import android.content.res.Resources;

import com.example.android.notes.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CardsSourceImpl implements CardsSource {

    private List<CardData> dataSource;
    private Resources resources;    //  Ресурсы приложения

    public CardsSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(4);
        this.resources = resources;
    }

    public CardsSource init(CardsSourceResponse cardsSourceResponse) {
        //  Получем наш список строк
        String[] notes_titles = resources.getStringArray(R.array.notes_title);
        String[] notes_body = resources.getStringArray(R.array.notes_body);
        //  Заполнение источника данных
        for (int i = 0; i < notes_titles.length; i++) {
            dataSource.add(new CardData(notes_titles[i], notes_body[i],
                    false, Calendar.getInstance().getTime()));
        }
        if (cardsSourceResponse != null) {
            cardsSourceResponse.initialized(this);
        }
        return this;
    }

    //  Получаем в качестве аргумента номер и возвращаем элемент из нашего списка с под этим номером
    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        dataSource.set(position, cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }

    @Override
    public void deleteMultiple() {

    }

    @Override
    public void readCheckbox(int position, boolean readCheck) {

    }
}

//  Класс который создает и хранит коллекцию захардкоденных данных, при каждом запуске