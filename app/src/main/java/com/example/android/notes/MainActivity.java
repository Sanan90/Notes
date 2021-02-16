package com.example.android.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.notes.ui.DialogBuilderFragment;
import com.example.android.notes.ui.MenuBuild;
import com.example.android.notes.ui.Navigation;
import com.example.android.notes.ui.NoteBodyFragment;
import com.example.android.notes.ui.NotesListFragment;
import com.example.android.notes.observe.Publisher;
import com.example.android.notes.ui.StartFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Navigation navigation;
    Publisher publisher = new Publisher();
    MenuBuild menuBuild;
    DialogFragment dialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuBuild = new MenuBuild();
        navigation = new Navigation(getSupportFragmentManager()); // Усстанавливаем фоагмент Менеджера
        menuBuild.initMenus(this);    //  Настраиваем меню, верхнее, боковое и т.д.
        getNavigation().addFragment(StartFragment.newInstance(), false);
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

    public DialogFragment getDialogFragment() {
        return dialogFragment;
    }

    public void setDialogFragment(DialogFragment dialogFragment) {
        this.dialogFragment = dialogFragment;
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