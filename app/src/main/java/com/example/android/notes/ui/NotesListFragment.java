package com.example.android.notes.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

import com.example.android.notes.MainActivity;
import com.example.android.notes.R;
import com.example.android.notes.data.CardData;
import com.example.android.notes.data.CardSourceFirebaseImpl;
import com.example.android.notes.data.CardsSource;
import com.example.android.notes.data.CardsSourceImpl;
import com.example.android.notes.data.CardsSourceResponse;
import com.example.android.notes.observe.Observer;
import com.example.android.notes.observe.Publisher;

public class NotesListFragment extends Fragment implements OnRegisterMenu {

    private boolean isLandscape;   // Чтобы знать режим экрана
    private CardData cardData;
    private CardsSource data;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    public final String DATA = "com/example/android/notes/data";

    DialogFragment dialogFragment;
    DialogForRemoveAllOrMarkedBuilderFragment dialogFragmentClear;

    private Navigation navigation;
    private Publisher publisher;
    // признак, что при повторном открытии фрагмента
    // (возврате из фрагмента, добавляющего запись)
    // надо прыгнуть на последнюю запись
    private boolean moveToFirstPosition;

    public NotesListFragment() {
    }

    //  Возвращаем NotesListFragment. Здесь можно добавить какие то параметры и аргументы.
    public static NotesListFragment newInstance() {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        //  Получим имточник данных для списка
        initView(view);
        data = new CardSourceFirebaseImpl().init(new CardsSourceResponse() {
            @Override
            public void initialized(CardsSource cardsData) {
                myAdapter.notifyDataSetChanged();
            }
        });
        myAdapter.setDataSource(data);
        initPopupMenu(view);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }

    private boolean onItemSelected(int menuItemId) {
        switch (menuItemId) {
            case R.id.action_add:
                navigation.addFragment(CardFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.addCardData(cardData);
                        myAdapter.notifyItemInserted(data.size());
                        // это сигнал, чтобы вызванный метод onCreateView
                        // перепрыгнул на начало списка
                        moveToFirstPosition = true;
                    }
                });
                return true;

            case R.id.action_update:
                final int updatePosition = myAdapter.getMenuPosition();
                navigation.addFragment(CardFragment.newInstance(data.getCardData(updatePosition)),
                        true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.updateCardData(updatePosition, cardData);
                        myAdapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;

            case R.id.action_delete:

                dialogFragment = new DialogBuilderFragment(this);
                dialogFragment.setCancelable(false);
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "TAG");
                return true;

            case R.id.action_clear:

                dialogFragmentClear = new DialogForRemoveAllOrMarkedBuilderFragment(this);
                dialogFragmentClear.setCancelable(false);
                dialogFragmentClear.show(requireActivity().getSupportFragmentManager(), "TAG");
                return true;
        }
        return false;
    }

    //  Удаляем все заметки
    public void deleteAll() {
        data.clearCardData();
        myAdapter.notifyDataSetChanged();
    }

    //  Удаляем отмеченные заметки
    public void deleteMarked() {
        data.deleteMultiple();
        myAdapter.notifyDataSetChanged();
    }
    //  Удаляем выбранную заметку
    public void deletee() {
        int deletePosition = myAdapter.getMenuPosition();
        data.deleteCardData(deletePosition);
        myAdapter.notifyItemRemoved(deletePosition);
    }

    //  Создаем наш список.
    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycl_for_notes_list);
        initRecyclerView();
    }


    // Данный метод вызываеться, когда фрагмент связывается с активностью. Мы передаем данноve фрагмента, navigation и publisher нашего  Activity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
        dialogFragment = activity.getDialogFragment();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        Toast.makeText(getContext(), "asssss", Toast.LENGTH_SHORT).show();
        super.onDetach();
        dialogFragment = null;
    }

    //  Метод который вызываеться во время длинного нажатия на элемент списка
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    private void initRecyclerView() {
        //  Эта установка служит для увеличения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроченным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //  Установим адаптер
        myAdapter = new MyAdapter(this, this);
        recyclerView.setAdapter(myAdapter);

//        // Добавим разделитель карточек
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
//        itemDecoration.setDrawable(getResources().getDrawable(R.drawable., null));
//        recyclerView.addItemDecoration(itemDecoration);

        // Установим анимацию. А чтобы было хорошо заметно, сделаем анимацию долгой
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(animator);

        if (moveToFirstPosition && data.size() > 0) {
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }

        //  Уснававливаем слушатели для элементов списка
        myAdapter.MyItemClickListener(new MyAdapter.MyClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showNoteBodySettings(data.getCardData(position), position); // Выясняем какая по счету заметка нажата, получаем данную cardData и передаем эту цифру в метод showNoteBodySettings
            }

            @Override
            public void onCheckClick(boolean readCheck, int position) {
                Toast.makeText(getContext(), String.valueOf(readCheck), Toast.LENGTH_SHORT).show();
                data.readCheckbox(position, readCheck);
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
    private void showNoteBodySettings(CardData cardData, int position) {
        if (isLandscape) {
            showNoteBodyForLandscape(cardData, position);  //  если ландшафный
        } else {
            showNoteBody(cardData, position);  // Если портретный
        }
    }

    //  Метод вызываеться принажатии на заметку при ландшавном режиме
    private void showNoteBodyForLandscape(CardData cardData, int position) {
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
    private void showNoteBody(CardData cardData, int position) {
        navigation.addFragment(NoteBodyFragment.newInstance(cardData), true);
        publisher.subscribe(new Observer() {
            @Override
            public void updateCardData(CardData cardData) {
                data.updateCardData(position, cardData);
                myAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public void onRegister(View view) {
        registerForContextMenu(view);
    }
}