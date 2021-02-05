package com.example.android.notes;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import javax.sql.DataSource;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

//    String[] notesList; //  Создаем массив строк
    public MyClickListener myClickListener; //  Создаем экземпляр нашего слушателя
    private CardsSource dataSource;
    private final OnRegisterMenu fragment;

    public int getMenuPosition() {
        return menuPosition;
    }

    private int menuPosition;

    //  передаем в конструктор источник данных
    //  Сейчас это массив, но может быть зарос в база данных
    public MyAdapter(CardsSource dataSource, OnRegisterMenu fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    //  Передаем созданному экземпляру, полученного аргумент

    public void MyItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

//    //  Инициализируем наш массив строк
//    public MyAdapter(String[] notesList) {
//        this.notesList = notesList;
//    }

    //  Вызываеться столько раз, сколько элементов списка видно на экране
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_for_note, parent, false);
        return new MyViewHolder(v);
    }

    //  Вызыввается каждый раз когда на экране появляется (Становиться видимым) какой то элемент списка.
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.setData(dataSource.getCardData(position));
    }

    //  Выясняем сколько строк в нашем списке
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    //  Интерфейс для нажатий.
    public interface MyClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView textView;
        private TextInputEditText editText;
        private CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_note_title);
            editText = itemView.findViewById(R.id.note_body_text);
            checkBox = itemView.findViewById(R.id.checkbox_note_title);

            registerContextMenu(itemView);

            //  Вешаем слушатель на элементы списка
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = MyViewHolder.this.getAdapterPosition();
                    myClickListener.onItemClick(itemView, position);
                }
            });
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment!=null) {
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
                fragment.onRegister(itemView);
            }
        }

        public void setData(CardData cardData) {
            textView.setText(cardData.getNotes_title());
            editText.setText(cardData.getNotes_body());
            checkBox.setChecked(cardData.isCheckbox());
        }
    }
}
