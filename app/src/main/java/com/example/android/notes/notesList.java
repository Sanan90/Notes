package com.example.android.notes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notesList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notesList extends Fragment {

    private boolean isLandscape;

    public notesList() {
        // Required empty public constructor
    }

    public static notesList newInstance(String param1, String param2) {
        notesList fragment = new notesList();
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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initlist(view);
    }

    private void initlist(View view) {

        MaterialTextView textViewImpMeet = view.findViewById(R.id.importantMeetings);
        MaterialTextView textViewShopList = view.findViewById(R.id.shoppingList);
        MaterialTextView textViewSecMatter = view.findViewById(R.id.seconradyMatters);
        MaterialTextView textViewIntTh = view.findViewById(R.id.interestingThoughts);

        textViewImpMeet.setOnClickListener(v ->
                showNoteBodySettings(textViewImpMeet, 1)
        );

        textViewShopList.setOnClickListener(v ->
                showNoteBodySettings(textViewShopList,2)
        );

        textViewSecMatter.setOnClickListener(v ->
                showNoteBodySettings(textViewSecMatter,3)
        );

        textViewIntTh.setOnClickListener(v ->
                showNoteBodySettings(textViewIntTh,4)
        );
    }

    private void showNoteBodySettings(MaterialTextView textView, int num) {
        if (isLandscape) {
            showNoteBodyForLandscape(textView, num);
        } else {
            showNoteBody(textView, num);
        }
    }

    private void showNoteBodyForLandscape(MaterialTextView textView, int num) {
        Context context = getContext();
        if (context != null) {
            noteBodyFragment fragment = noteBodyFragment.newInstance(num);

            FragmentManager  fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.notesBody, fragment);
            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }

    private void showNoteBody(MaterialTextView textView, int num) {


        Context context = getContext();
        if (context != null) {

            Intent intent = new Intent(context, NotesBody.class);
            intent.putExtra(noteBodyFragment.ARG_INDEX, textView.getId());
            intent.putExtra(noteBodyFragment.ARG_INDEX2, num);
            startActivity(intent);

//            noteBodyFragment fragment = noteBodyFragment.newInstance(textView.getId());
//
//            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(textView.getId(), fragment);
//            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            fragmentTransaction.commit();
        }
    }

}