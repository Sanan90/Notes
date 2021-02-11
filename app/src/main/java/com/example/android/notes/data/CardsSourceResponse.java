package com.example.android.notes.data;

//  Вызываеться в том случае, если мы успешно подсоеденимся к нашей базе данных.
//
public interface CardsSourceResponse {
    void initialized(CardsSource cardsData);
}

//  Класс который мы будем доставать из нашего файрстора
//  чтобы дальше передать в адаптер и отобразить все данные