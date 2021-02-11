package com.example.android.notes.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.android.notes.MainActivity;
import com.example.android.notes.R;
import com.google.android.material.navigation.NavigationView;

public class MenuBuild {

    MainActivity activity;
    //  Метод для нахождения и постройки, всех меню.
    public void initMenus(MainActivity activity) {
        this.activity = activity;
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ???
        initDrawer(toolbar, activity);
    }

    //  Принимаем наш Тулбар, чтобы установить, а также настраиваем боковое меню.
    private void initDrawer(Toolbar toolbar, MainActivity activity) {
        final DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //  Устанавливаем слушатель для кнопок бокового меню.
        NavigationView navigationView = activity.findViewById(R.id.nav_viwe);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int d = item.getItemId();
                Toast.makeText(activity, "text", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
