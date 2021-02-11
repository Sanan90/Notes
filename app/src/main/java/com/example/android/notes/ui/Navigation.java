package com.example.android.notes.ui;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.android.notes.R;

public class Navigation {

    private final FragmentManager fragmentManager;

    //  Врзвращаем navigation со своим фрагментМенеджером
    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    //  Принимает в качестве аргумента какой то фрагмент и информацию, нужно ли добавлять фрагмент в бекстек или нет.
    public void addFragment (Fragment fragment, boolean useBackStack) {
        //  Открыть транзакцию
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainDisplay, fragment);
        if (useBackStack) {
            transaction.addToBackStack(null);
        }
        //  ЗАкрыть транзакцию
        transaction.commit();
    }


    public void closeFragment() {
        fragmentManager.popBackStack();
    }

}
