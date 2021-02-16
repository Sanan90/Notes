package com.example.android.notes.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardSourceFirebaseImpl implements CardsSource {

    private static final String CARDS_COLLECTION = "cards"; //  Идентификатор нашей коллекции, который будем находить.
    private static final String TAG = "[CardsSourceFirebaseImpl]";  //

    //  База данных FireStore
    //  Перед работой с Firestore надо получить базу данных командой
    //  store = FirebaseFirestore.getInstance();
    private FirebaseFirestore store = FirebaseFirestore.getInstance();  //

    //  Коллекция документов
    //  Чтобы получить коллекцию, используем метод collection в классе базы данных
    //  collection = store.collection(CARDS_COLLECTION);,
    //  где CARDS_COLLECTION — имя коллекции.
    private CollectionReference collection = store.collection(CARDS_COLLECTION);    //  Указываем какую коллекцию карточек нужно найти и получить

    //  Сюда будем сохранять информацию из коллекции.
    private List<CardData> cardsData = new ArrayList<>();

    @Override
    public CardsSource init(final CardsSourceResponse cardsSourceResponse) {
        //  Получить всю коллекцию, отсортированную в полю <Дата>
        collection.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()   //  Указываем как сортировать. В данном случае по дату создания
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {            //
                    //  При удачном считывании данных, загрузим список карточек
                    @Override
                    //  Если все хорошо, загрузка прошла успешно, получаем Таск
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cardsData = new ArrayList<CardData>();
                            //  document это', по сути HashMap
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();   //  Object это наша cardData
                                String id = document.getId();
                                CardData cardData = CardDataMapping.toCardData(id, doc);
                                cardsData.add(cardData);
                            }
                            Log.d(TAG, "success " + cardsData.size() + " qnt");
                            cardsSourceResponse.initialized(CardSourceFirebaseImpl.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            //  Если произошла ошибка. Можно передать сообющение об ошибке
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "get dailed with ", e);
            }
        });
        return this;
    }

    //  Врзвращает карточку по позиции
    @Override
    public CardData getCardData(int position) {
        return cardsData.get(position);
    }

    //  Возвращаем размер. Это понадобиться для адаптера
    @Override
    public int size() {
        if (cardsData == null) {
            return 0;
        }
        return cardsData.size();
    }

    @Override
    public void deleteCardData(int position) {
        //  Удалить документ с определенным ID
        collection.document(cardsData.get(position).getId()).delete();  //  Удаляем из FireBase(базы данных)
        cardsData.remove(position);  // Также удаляем из массива карточек
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        String id = cardData.getId();
        //  Изменить документ по ID
        collection.document(cardsData.get(position).getId()).set(CardDataMapping.toDocument(cardData));
        cardsData.set(position, cardData);  // Работает и без этого
    }

    @Override
    public void addCardData(CardData cardData) {
        //  Добавить документ
        collection.add(CardDataMapping.toDocument(cardData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                cardData.setId(documentReference.getId());
            }
        });
    }

    //Динамически все удаляем
    @Override
    public void clearCardData() {
        for (CardData cardData : cardsData) {
            collection.document(cardData.getId()).delete();
        }
        cardsData = new ArrayList<>();  //  Создаем абсолютнр пучстой массив карточек
    }

    @Override
    public void deleteMultiple() {
        List<CardData> cardsData2;
        cardsData2 = cardsData;
        for (CardData cardData : cardsData) {
            if (cardData.isCheckbox()) {
                int a = cardsData.indexOf(cardData);
                String idForDelete = cardData.getId();
                collection.document(cardsData.get(a).getId()).delete();  //  Удаляем из FireBase(базы данных)

                for (int i = 0; i < cardsData.size(); i++) {
                    if (cardsData.get(i).getId().equals(idForDelete)) {
//                        cardsData2.remove(cardData);
                        break;
                    }
                }
                 // Также удаляем из массива карточек
            }
        }
        cardsData = cardsData2;
    }
//                deleteCardDataById(cardData.getId());
//                collection.document(cardData.getId()).delete();


    @Override
    public void readCheckbox(int position, boolean readCheck) {
        cardsData.get(position).setCheckbox(readCheck);
    }

}

//  Класс где проходит вся основная работа с базой данных.
//  Здесь мы будем подсоединяться с базой данных и получать с информацию оттуда.
//  Используем вместо CardSourceImpl
