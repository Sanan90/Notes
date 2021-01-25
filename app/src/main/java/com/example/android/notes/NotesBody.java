package com.example.android.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class NotesBody extends AppCompatActivity {

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_body);
        showNoteBody();
     }

    private void showNoteBody() {
        noteBodyFragment fragment = new noteBodyFragment();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().
                replace(R.id.containerForNotesBody, fragment).commit();
    }
}
