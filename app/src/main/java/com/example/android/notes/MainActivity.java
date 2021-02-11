package com.example.android.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.notes.ui.MenuBuild;
import com.example.android.notes.ui.Navigation;
import com.example.android.notes.ui.NoteBodyFragment;
import com.example.android.notes.ui.NotesListFragment;
import com.example.android.notes.observe.Publisher;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Navigation navigation;
    Publisher publisher = new Publisher();
    MenuBuild menuBuild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuBuild = new MenuBuild();
        navigation = new Navigation(getSupportFragmentManager()); // Усстанавливаем фоагмент Менеджера
        menuBuild.initMenus(this);    //  Настраиваем меню, верхнее, боковое и т.д.
        if (getIntent().getExtras() != null) {  //  Проверка на наличие аргументов на нашем интенте.
            // При налиичи аргументов, значит открываем какую то заметку. Вызываем метод нашей навигации addFragment и передаем ей нужный фрагмент, и параметр, для добавления в бекстек.
            getNavigation().addFragment(NoteBodyFragment.newInstance(getIntent().getExtras().getParcelable(NoteBodyFragment.ARG_INDEX2)), false); //
        }   else {
            //  Если аргументов нету, значит открываем список заметок. Передаем все в тот же метод нашей навигации нужный фрагмент и параметр для бекстека.
            getNavigation().addFragment(NotesListFragment.newInstance(), false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //  Возвращаем Navigation со своим фрагмент менеджером и методом addFragment
    public Navigation getNavigation() {
        return navigation;
    }

    //  Возвращаем publisher
    public Publisher getPublisher () {
        return publisher;
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
}