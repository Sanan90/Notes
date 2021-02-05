package com.example.android.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class CardData implements Parcelable {

    private String notes_title;
    private String notes_body;
    private boolean checkbox;

    public CardData(String notes_title, String notes_body, boolean checkbox) {
        this.notes_title = notes_title;
        this.notes_body = notes_body;
        this.checkbox = checkbox;
    }

    protected CardData(Parcel in) {
        notes_title = in.readString();
        notes_body = in.readString();
        checkbox = in.readByte() != 0;
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

    public String getNotes_title() {
        return notes_title;
    }

    public String getNotes_body() {
        return notes_body;
    }

    public boolean isCheckbox() {
        return checkbox;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(notes_title);
        dest.writeString(notes_body);
        dest.writeByte((byte) (checkbox ? 1 : 0));
    }


}
