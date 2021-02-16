package com.example.android.notes.data;



import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class CardDataMapping {
    public static class Fields{
//        public final static String PICTURE = "picture";   // Мне картинка пока не нужна
        public final static String DATE = "date";
        public final static String NOTE_TITLE = "note_title";
        public final static String NOTE_BODY = "note_body";
        public final static String CHECKBOX = "checkbox";
    }

//  Получаем map и из него по ключу достаем все нужные значения. Все те же поля, что содержит CardData.
    public static CardData toCardData(String id, Map<String, Object> doc) {
//        long indexPic = (long) doc.get(Fields.PICTURE);   //  Получаем ссылку на картинку
        Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);  // Получаем дату
        CardData answer = new CardData((String) doc.get(Fields.NOTE_TITLE),
                (String) doc.get(Fields.NOTE_BODY),
                (boolean) doc.get(Fields.CHECKBOX),
                timeStamp.toDate());
        answer.setId(id);
        return answer;
    }

    //  Метод который переводит нашу дату в документ, понятный нашему FireStore.
    //  Прямо противоположный метод toCardData-e
    public static Map<String, Object> toDocument(CardData cardData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NOTE_TITLE, cardData.getNotes_title());
        answer.put(Fields.NOTE_BODY, cardData.getNotes_body());
//      answer.put(Fields.PICTURE, PictureIndexConverter.getIndexByPicture(cardData.getPicture()));
        answer.put(Fields.CHECKBOX, cardData.isCheckbox());
        answer.put(Fields.DATE, cardData.getDate());
        return answer;
    }
}

//  Суть этого вспомогательного класса,
//  помогать расшифрововать наши данные которые мы получаем из Фейрбейса