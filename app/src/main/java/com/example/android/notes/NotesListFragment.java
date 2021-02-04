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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class NotesListFragment extends Fragment {

    private boolean isLandscape;   // Чтобы знать режим экрана

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
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initlist(view);
        initlist(view);
        initPopupMenu(view);
    }

    //  Начинаме работу со списком
    private void initlist(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycl_for_notes_list);
        String[] notes = view.getResources().getStringArray(R.array.notes_title);
        MyAdapter myAdapter = new MyAdapter(notes);
        recyclerView.setAdapter(myAdapter);

        //  Вешаем слушатели для элементов списка
        myAdapter.MyItemClickListener(new MyAdapter.MyClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showNoteBodySettings(position + 1); // Выясняем какая по счету заметка нажата, и передаем эту цифру в метод showNoteBodySettings
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
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
//        text1.setOnClickListener(v -> {
//            Activity activity = requireActivity();
//            PopupMenu popupMenu = new PopupMenu(activity, v);
//            activity.getMenuInflater().inflate(R.menu.popup2, popupMenu.getMenu());
//            Menu menu = popupMenu.getMenu();
//            popupMenu.setOnMenuItemClickListener(item -> {
//                int id = item.getItemId();
//                switch (id) {
//                    case R.id.item1_popup:
//                        Toast.makeText(getContext(), "choose1", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.item2_popup:
//                        Toast.makeText(getContext(), "choose2", Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//                return false;
//            });
//            popupMenu.show();
//        });
//
//        text2.setOnClickListener(v -> {
//            Activity activity = requireActivity();
//            PopupMenu popupMenu = new PopupMenu(activity, v);
//            activity.getMenuInflater().inflate(R.menu.popup2, popupMenu.getMenu());
//            popupMenu.setOnMenuItemClickListener(item -> {
//                int id = item.getItemId();
//                switch (id) {
//                    case R.id.item1_popup:
//                        Toast.makeText(getContext(), "choose1", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.item2_popup:
//                        Toast.makeText(getContext(), "choose2", Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//                return false;
//            });
//            popupMenu.show();
//        });
//
//        text3.setOnClickListener(v -> {
//            Activity activity = requireActivity();
//            PopupMenu popupMenu = new PopupMenu(activity, v);
//            activity.getMenuInflater().inflate(R.menu.popup2, popupMenu.getMenu());
//            popupMenu.setOnMenuItemClickListener(item -> {
//                int id = item.getItemId();
//                switch (id) {
//                    case R.id.item1_popup:
//                        Toast.makeText(getContext(), "choose1", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.item2_popup:
//                        Toast.makeText(getContext(), "choose2", Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//                return false;
//            });
//            popupMenu.show();
//        });
//
//        text4.setOnClickListener(v -> {
//            Activity activity = requireActivity();
//            PopupMenu popupMenu = new PopupMenu(activity, v);
//            activity.getMenuInflater().inflate(R.menu.popup2, popupMenu.getMenu());
//            popupMenu.setOnMenuItemClickListener(item -> {
//                int id = item.getItemId();
//                switch (id) {
//                    case R.id.item1_popup:
//                        Toast.makeText(getContext(), "choose1", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.item2_popup:
//                        Toast.makeText(getContext(), "choose2", Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//                return false;
//            });
//            popupMenu.show();
//        });
    }

    //  проверяем режим эурана, портретный или ландшавный
    private void showNoteBodySettings(int num) {
        if (isLandscape) {
            showNoteBodyForLandscape(num);  //  если ландшафный
        } else {
            showNoteBody(num);  // Если портретный
        }
    }

    //  Метод вызываеться принажатии на заметку при ландшавном режиме
    private void showNoteBodyForLandscape(int num) {
        Context context = getContext();
        if (context != null) {
            NoteBodyFragment fragment = NoteBodyFragment.newInstance(num);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.notesBody, fragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //  Берем текущий интент и меняем там передаваемый аргумент, чтобы при повороте экрана передавался именно новое значение Intent2
            Intent intent = getActivity().getIntent();
            intent.putExtra(NoteBodyFragment.ARG_INDEX2, num);

            fragmentTransaction.commit();
        }
    }

    //  Метод вызываеться принажатии на заметку при портретном режиме
    private void showNoteBody(int num) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(NoteBodyFragment.ARG_INDEX2, num);
            startActivity(intent);
        }
    }

}