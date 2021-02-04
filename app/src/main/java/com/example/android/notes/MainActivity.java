package com.example.android.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMenus();
        initView();
    }

    //  Метод для нахождения и постройки, всех меню.
    private void initMenus() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    //  Принимаем наш Тулбар, чтобы установить, а также настраиваем боковое меню.
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //  Устанавливаем слушатель для кнопок бокового меню.
        NavigationView navigationView = findViewById(R.id.nav_viwe);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int d = item.getItemId();
                Toast.makeText(MainActivity.this, "text", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    //  Проверяем какой фрагмент показывать.
    private void initView() {
        if (getIntent().getExtras() == null) {  // Если это первый запуск.
            startApp();
        } else {    // Если не первый запуск
            showNote();
        }
//        initButtonFavorite();
//        initButtonSettings();
//        initButtonBack();
    }

    //  Создаем менеджера фрагментов, открываем транзакцию, затем передаем в транзакцию часть экрана, предназначенную для показа списка заметок и фрагмент со списком заметок.
    private void startApp() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainDisplay, new NotesListFragment()).commit();
    }
    //  Если это не первый запуск и открыта заметка.
    private void showNote() {
        NoteBodyFragment noteBodyFragment = new NoteBodyFragment();
        noteBodyFragment.setArguments(getIntent().getExtras());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //  Проверяем положение экрана.
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) { // Если портретный режим, открываем заметку в главном макете.
            transaction.replace(R.id.mainDisplay, noteBodyFragment).commit();
        }   else {
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
            fragmentTransaction2.replace(R.id.mainDisplay, new NotesListFragment()).commit();
            transaction.replace(R.id.notesBody, noteBodyFragment).commit();
        }
    }

    //  Устанавливаем слушатели для кнопок Тулбара
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_main:
                return true;
            case R.id.action_favorite:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //  Находим поиск и вешаем слушатель.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);

        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}