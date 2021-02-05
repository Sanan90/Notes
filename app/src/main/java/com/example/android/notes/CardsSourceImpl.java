package com.example.android.notes;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class CardsSourceImpl implements CardsSource {

    private List<CardData> dataSource;
    private Resources resources;    //  Ресурсы приложения

    public CardsSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(4);
        this.resources = resources;
    }

    public CardsSourceImpl init() {
        //  Получем наш список строк
        String[] notes_titles = resources.getStringArray(R.array.notes_title);
        String[] notes_body = resources.getStringArray(R.array.notes_body);
        //  Заполнение источника данных
        for (int i = 0; i < notes_titles.length; i++) {
            dataSource.add(new CardData(notes_titles[i], notes_body[i], false));
        }
        return this;
    }

    //

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
}
