package com.example.android.notes.data;

import com.example.android.notes.data.CardData;

public interface CardsSource {
    CardsSource init(CardsSourceResponse cardsSourceResponse);
    CardData getCardData(int position);
    int size();
    void deleteCardData(int position);
    void updateCardData(int position, CardData cardData);
    void addCardData(CardData cardData);
    void clearCardData();
    void deleteMultiple();

    void readCheckbox(int position, boolean readCheck);
}

//  Источник данных
