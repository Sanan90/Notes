package com.example.android.notes.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.android.notes.MainActivity;
import com.example.android.notes.R;

public class DialogForRemoveAllOrMarkedBuilderFragment extends DialogFragment {

    int a = 0;
    Fragment fragment;

    public DialogForRemoveAllOrMarkedBuilderFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();

        // Вытаскиваем макет диалога
            // https://stackoverflow.com/questions/15151783/stackoverflowerror-when-trying-to-inflate-a-custom-layout-for-an-alertdialog-ins
        final View contentView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_delete_all_or_marked, null);
        AlertDialog.Builder deleteAllOrSelective = deleteAllOrSelectively(activity, contentView);
        return deleteAllOrSelective.create();
    }

    private AlertDialog.Builder deleteAllOrSelectively(MainActivity activity, View contentView) {
        NotesListFragment fragment = (NotesListFragment) this.fragment;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setView(contentView)
                .setNeutralButton(R.string.Delete_all, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fragment.deleteAll();
                        dismiss();  //  для завершения диалога вызываем dismiss() и передаём управление активити.
                    }
                })
                .setPositiveButton(R.string.delete_marked, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fragment.deleteMarked();
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
