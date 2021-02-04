package com.example.android.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String[] notesList; //  Создаем массив строк
    public MyClickListener myClickListener; //  Создаем экземпляр нашего слушателя

    //  Передаем созданному экземпляру, полученного аргумент
    public void MyItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    //  Инициализируем наш массив строк
    public MyAdapter(String[] notesList) {
        this.notesList = notesList;
    }

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
        holder.bind(notesList[position]);
    }

    //  Выясняем сколько строк в нашем списке
    @Override
    public int getItemCount() {
        return notesList.length;
    }

    //  Интерфейс для нажатий.
    public interface MyClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_note_title);

            //  Вешаем слушатель на элементы списка
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = MyViewHolder.this.getAdapterPosition();
                    myClickListener.onItemClick(itemView, position);
                }
            });

        }
        public void bind(String s) {
            textView.setText(s);
        }
    }
}
