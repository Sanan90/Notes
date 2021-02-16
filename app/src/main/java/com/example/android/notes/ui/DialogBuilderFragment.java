package com.example.android.notes.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.android.notes.MainActivity;
import com.example.android.notes.R;

public class DialogBuilderFragment extends DialogFragment {

    int a = 0;
    Fragment fragment;

    public DialogBuilderFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();

        // Вытаскиваем макет диалога
            // https://stackoverflow.com/questions/15151783/stackoverflowerror-when-trying-to-inflate-a-custom-layout-for-an-alertdialog-ins
        final View contentView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_custom, null);
        AlertDialog.Builder builder = buildertMenu(activity, contentView);
        AlertDialog.Builder builder2 = buildertMenu(activity, contentView);
        return builder.create();
    }

    private AlertDialog.Builder buildertMenu(MainActivity activity, View contentView) {
        NotesListFragment fragment = (NotesListFragment) this.fragment;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setView(contentView)
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fragment.deletee();
                        dismiss();  //  для завершения диалога вызываем dismiss() и передаём управление активити.
                    }
                })
                .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder;
    }

    @Override
    public boolean getShowsDialog() {
        return super.getShowsDialog();
    }
}
