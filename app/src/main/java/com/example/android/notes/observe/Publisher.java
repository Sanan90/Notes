package com.example.android.notes.observe;

import com.example.android.notes.data.CardData;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    //  Создаем массив из Observer-ов
    private List <Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    //  Добавить в списко полученный observer
    public void subscribe (Observer observer) {
        observers.add(observer);
    }

    //  Удалить из списка полученный observer
    public void unsubscribe (Observer observer) {
        observers.remove(observer);
    }

    //  Разослать событие
    public void notifySingle (CardData cardData) {
        for (Observer observer : observers) {
            observer.updateCardData(cardData);
            unsubscribe(observer);
        }
    }
}
