package com.example.android.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.notes.R;
import com.example.android.notes.data.CardData;
import com.example.android.notes.data.CardsSource;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

//    String[] notesList; //  Создаем массив строк
    public MyClickListener myClickListener; //  Создаем экземпляр нашего слушателя
    private CardsSource dataSource;
    private final Fragment fragment;

    public int getMenuPosition() {
        return menuPosition;
    }

    private int menuPosition;






    //  передаем в конструктор источник данных
    //  Сейчас это массив, но может быть зарос в база данных
    public MyAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public  void setDataSource(CardsSource dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
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
        private TextView date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_note_title);
            editText = itemView.findViewById(R.id.note_body_text);
            checkBox = itemView.findViewById(R.id.checkbox_note_title);
            date = itemView.findViewById(R.id.date);
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
        //      Усстанавливаем слушатель элементу списка во время длинного нажатия
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
//                fragment.onRegister(itemView);
            }
        }

        //  Получаем в качестве аргумента CardData и передаем нашему списку значения полученные с полученного аргумента
        public void setData(CardData cardData) {
            textView.setText(cardData.getNotes_title());
            editText.setText(cardData.getNotes_body());
            checkBox.setChecked(cardData.isCheckbox());
            date.setText(new SimpleDateFormat("dd-MM-YYYY HH-mm").format(cardData.getDate()));
        }
    }
}
