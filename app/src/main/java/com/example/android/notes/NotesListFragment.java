package com.example.android.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

public class NotesListFragment extends Fragment implements OnRegisterMenu {

    private boolean isLandscape;   // Чтобы знать режим экрана

    private CardsSource data;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;

    public NotesListFragment() {
    }

    public static NotesListFragment newInstance(String param1, String param2) {
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycl_for_notes_list);
        //  Получим имточник данных для списка
//        initRecyclerView(recyclerView, data);
        initView(view);
        setHasOptionsMenu(true);
        initPopupMenu(view);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                data.addCardData(new CardData("Новая заметка" + data.size(), "Содержимое заметки" + data.size(), false));
                myAdapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                myAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycl_for_notes_list);
        data = new CardsSourceImpl(getResources()).init();
        initRecyclerView();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = myAdapter.getMenuPosition();

        switch (item.getItemId()) {
            case R.id.action_update:
                data.updateCardData(position, new CardData("Измененная заметка" + position,
                        "Новое содержимое", false));
                myAdapter.notifyItemChanged(position);
                return true;
            case R.id.action_delete:
                data.deleteCardData(position);
                myAdapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void initRecyclerView() {

        //  Эта установка служит для увеличения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроченным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //  Установим адаптер
        myAdapter = new MyAdapter(data, this);
        recyclerView.setAdapter(myAdapter);

//        // Добавим разделитель карточек
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
//        itemDecoration.setDrawable(getResources().getDrawable(R.drawable., null));
//        recyclerView.addItemDecoration(itemDecoration);

        //  Устновим слушателя
        myAdapter.MyItemClickListener(new MyAdapter.MyClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showNoteBodySettings(data.getCardData(position)); // Выясняем какая по счету заметка нажата, и передаем эту цифру в метод showNoteBodySettings
            }
        });
    }

    //  Настраиваем контекстное меню
    private void initPopupMenu(View view) {
        LinearLayout layout = view.findViewById(R.id.notesListsContainer);

        layout.setOnClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, v);
            activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
            Menu menu = popupMenu.getMenu();
            menu.findItem(R.id.item2_popup).setVisible(false);
            menu.add(0, 123456, 12, R.string.new_menu_item_added);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    switch (id) {
                        case R.id.item1_popup:
                            Toast.makeText(getContext(), "choose1", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.item2_popup:
                            Toast.makeText(getContext(), "choose2", Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return true;
                }
            });
            popupMenu.show();
        });
    }

    //  проверяем режим эурана, портретный или ландшавный
    private void showNoteBodySettings(CardData cardData) {
        if (isLandscape) {
            showNoteBodyForLandscape(cardData);  //  если ландшафный
        } else {
            showNoteBody(cardData);  // Если портретный
        }
    }

    //  Метод вызываеться принажатии на заметку при ландшавном режиме
    private void showNoteBodyForLandscape(CardData cardData) {
        Context context = getContext();
        if (context != null) {
            NoteBodyFragment fragment = NoteBodyFragment.newInstance(cardData);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.notesBody, fragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //  Берем текущий интент и меняем там передаваемый аргумент, чтобы при повороте экрана передавался именно новое значение Intent2
            Intent intent = getActivity().getIntent();
            intent.putExtra(NoteBodyFragment.ARG_INDEX2, cardData);

            fragmentTransaction.commit();
        }
    }

    //  Метод вызываеться принажатии на заметку при портретном режиме
    private void showNoteBody(CardData cardData) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(NoteBodyFragment.ARG_INDEX2, cardData);
            startActivity(intent);
        }
    }

    @Override
    public void onRegister(View view) {
        registerForContextMenu(view);
    }
}