package com.unitapplications.colornotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.unitapplications.colornotes.Model.Models;
import com.unitapplications.colornotes.data.MyDbHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class writeNoteActivity extends AppCompatActivity {
    ImageView save,back_arrow;
    EditText et_title, et_note;
    String pinned;

    private ArrayList<Models> modelArrayList;
    private MyDbHandler myDbHandler;

    //this method works when back button is pressed
    @Override
    public void onBackPressed() {
        //so we made this to do save note
        // when back button is pressed
       saveNote();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        Objects.requireNonNull(getSupportActionBar()).hide();

        myDbHandler = new MyDbHandler(this);
        modelArrayList = new ArrayList<>();

        //Initialization of Views
        et_note = findViewById(R.id.title_et);
        et_title = findViewById(R.id.note_et);


        save = findViewById(R.id.save);
        back_arrow = findViewById(R.id.backArrow);

        try {
            Intent intent1 = getIntent();

            String edit_title = intent1.getStringExtra("title_key2");
            String edit_note = intent1.getStringExtra("note_key2");

            et_title.setText(edit_note);
            et_note.setText(edit_title);
        } catch (Exception e) {
            log("something WEnt wroing while edit");
            msg("Something went wrong");
        }

        save.setOnClickListener(view -> {
            //save button saving note
            saveNote();

        });
        back_arrow.setOnClickListener(view -> {
        // custom back arrow save note
            saveNote();
        });




    }
    public String getDateTime(){
        long date = System.currentTimeMillis();

        SimpleDateFormat dateF = new SimpleDateFormat("EEE, MMM dd yyyy");
        SimpleDateFormat timeF = new SimpleDateFormat("h:mm a");
        String date_str = dateF.format(date);
        String time_str = timeF.format(date);
        String final_dateTime = date_str+" at "+time_str;
        return final_dateTime;
    }


    public void saveNote(){
        // this is the ultimate method that saves
        // the note we just need to call it whenever we want
        // this took me lots of struggle to optimize it like this

        //Initialization of Db handler
        myDbHandler = new MyDbHandler(this);
        Models models = new Models();

        modelArrayList = new ArrayList<>();


        Intent intent = new Intent(writeNoteActivity.this, MainActivity.class);
        startActivity(intent);

        String note_str = et_note.getText().toString();
        String title_str = et_title.getText().toString();


        //this is custom made method to simplify toasting
        //msg("Note has been added.");

        //this just empty the text of edittext on click
        et_note.setText("");
        et_title.setText("");
        // validating if the text fields are empty or not.


        models.setColum(note_str);//setting data to database
        models.setColum1(title_str);
        models.setDateTime(getDateTime());

        myDbHandler.addNote(models);//here it adds to detabase



        //this is for recyclerView to refresh list
        //  again on click to save
        //setRecyclerView();
        ArrayList<Models> allNotes = myDbHandler.getAllNotes();
        for (Models model : allNotes) {
            // it starts the loop and populate the arraylist from database

            //this is just a log for debug purpose
            log("Id: " + model.getId() +
                    "" + model.getColum() +
                    "" + model.getColum1() + "\n");

             //this add to arraylist
            //the zero in this is for indexing the recent
            //to be on top
            modelArrayList.add(0, model);

        }

    }

    public void log(String l) {
        Log.d("TAG", l);
    }

    public void msg(String m) {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }


}