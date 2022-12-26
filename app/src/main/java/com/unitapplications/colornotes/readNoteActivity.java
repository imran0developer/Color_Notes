package com.unitapplications.colornotes;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class readNoteActivity extends AppCompatActivity {

    TextView title_read, note_read, edit_tv;
    ImageView edit, back_arrow;
    String read_title;
    String read_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);
        Objects.requireNonNull(getSupportActionBar()).hide();


        // hers there is all intialization of views
        title_read = findViewById(R.id.title_read);
        note_read = findViewById(R.id.note_read);
        edit = findViewById(R.id.edit);
        edit_tv = findViewById(R.id.edit_tv);
        back_arrow = findViewById(R.id.backArrow);



        try {
            // in this try method i get strings of note and title
            // from write main activty to show on read activity
            Intent intent1 = getIntent();
            read_title = intent1.getStringExtra("title_key2");
            read_note = intent1.getStringExtra("note_key2");

            note_read.setText(read_note);
            title_read.setText(read_title);
        } catch (Exception e) {
            // if try fails then this catch will handle it by
            // toasting msg and log
            log("something WEnt wroing while edit");
            msg("Something went wrong");
        }

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this is just custom back arrow
                Intent intent = new Intent(readNoteActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this is edit button
                //that sends note and title to
                //write note activity for edit
                sendToEdit(read_title, read_note);
            }
        });
        edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this does same as edit
                sendToEdit(read_title, read_note);
            }
        });

    }

    public void sendToEdit(String title_2, String note_2) {
        //this is the intent method to send title and note to edit
        Intent intent;
        intent = new Intent(readNoteActivity.this, writeNoteActivity.class);
        intent.putExtra("title_key2", title_2);
        intent.putExtra("note_key2", note_2);
        startActivity(intent);
    }


    public void log(String l) {
        Log.d("TAG", l);
    }

    public void msg(String m) {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }
}