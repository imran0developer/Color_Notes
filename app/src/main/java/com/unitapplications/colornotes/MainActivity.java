package com.unitapplications.colornotes;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unitapplications.colornotes.Adapters.AdapterClass;
import com.unitapplications.colornotes.Model.Models;
import com.unitapplications.colornotes.data.MyDbHandler;


import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton ft_newNote;
    androidx.appcompat.widget.SearchView searchView;
    RecyclerView recyclerView;
    AdapterClass adapterClass;
    private ArrayList<Models> modelArrayList;
    private MyDbHandler myDbHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();


       searchView = findViewById(R.id.search_bar);

        //Initialization of Db handler
        myDbHandler = new MyDbHandler(this);

        Models models = new Models();

        modelArrayList = new ArrayList<>();

        //Initialization of RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        try {
            //getting the intent
            //to grab the id of note that user wants to delete
            //this work could'nt be done in adapter class so
            // i made this intent to do this in main activity
            Intent intent1 = getIntent();
            int id_to_delete = intent1.getIntExtra("key_to_delete", -1);
            myDbHandler.deleteNoteById(id_to_delete);
            setRecyclerView();

        } catch (Exception e) {
            log("something WEnt wroing while edit");
        }

        //this is floating button to add new note
        ft_newNote = findViewById(R.id.ft_newNote);
        ft_newNote.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,writeNoteActivity.class)));

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            //this is search method that do search simple!
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }

        });
    }


    private void filter(String s) {
        // this is interesting method that filters arraylist
        //according to search

        ArrayList<Models> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Models item : modelArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getColum().toLowerCase().contains(s.toLowerCase())
                    || item.getColum1().toLowerCase().contains(s.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(0, item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapterClass.filteringList(filteredlist);
        }

    }

    public void setRecyclerView() {
        // here i made layout manager and arraylist from database


        ArrayList<Models> allNotes = myDbHandler.getAllNotes();
        for (Models model : allNotes) {
            // it starts the loop and populate the arraylist from database

            //this is just a log for debug purpose
            log("Id: " + model.getId() +
                    "\ncolum : " + model.getColum() +
                    "\ncolum1 : " + model.getColum1());
            //this add to arraylist
            //the zero in this is for indexing the recent
            //to be on top
            modelArrayList.add(0, model);

        }
        //here view set adapter and layout manager to recycler view
        init();
    }
    public void init(){
        //i am using this init function to make sure the recylerview not adding all notes again and again
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        adapterClass = new AdapterClass(modelArrayList, MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterClass);
    }

    public void log(String l) {
        Log.d("TAG", l);
    }

    public void msg(String m) {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }


}