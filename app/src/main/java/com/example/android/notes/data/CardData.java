package com.example.android.notes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class CardData implements Parcelable {

    private String id;
    private String notes_title;
    private String notes_body;
    private boolean checkbox;
    private Date date;

    public CardData(String notes_title, String notes_body, boolean checkbox,
                    Date date) {    //  Здесь нужно добавить id и передавать случайное число и юуквы.
        this.notes_title = notes_title;
        this.notes_body = notes_body;
        this.checkbox = checkbox;
        this.date = date;
        this.id = id;
    }

    protected CardData(Parcel in) {
        notes_title = in.readString();
        notes_body = in.readString();
        checkbox = in.readByte() != 0;
        date = new Date(in.readLong());
        id = in.readString();
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    //  Возвращаем описание заметки в виде String-а
    public String getNotes_title() {
        return notes_title;
    }

    //  Возвращаем содержимое заметки в виде String-а
    public String getNotes_body() {
        return notes_body;
    }

    //  Возвращаем галочку нашей заметки
    public boolean isCheckbox() {
        return checkbox;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNotes_title(String notes_title) {
        this.notes_title = notes_title;
    }

    public void setNotes_body(String notes_body) {
        this.notes_body = notes_body;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(notes_title);
        dest.writeString(notes_body);
        dest.writeByte((byte) (checkbox ? 1 : 0));
        dest.writeLong(date.getTime());
        dest.writeString(id);
    }


}
