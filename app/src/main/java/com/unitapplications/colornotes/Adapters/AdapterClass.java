package com.unitapplications.colornotes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.unitapplications.colornotes.MainActivity;
import com.unitapplications.colornotes.Model.Models;
import com.unitapplications.colornotes.R;
import com.unitapplications.colornotes.data.MyDbHandler;
import com.unitapplications.colornotes.readNoteActivity;
import com.unitapplications.colornotes.writeNoteActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {

    // variable for our array list and context
    private ArrayList<Models> modelArrayList;
    private Context context;
    private MyDbHandler myDbHandler = new MyDbHandler(context);


    // constructor
    public AdapterClass(ArrayList<Models> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        // on below line we are setting data
        // to our views of recycler view item.
        Models model = modelArrayList.get(position);
        holder.tv_title.setText(model.getColum());
        holder.tv_note.setText(model.getColum1());
        holder.date_tv.setText(model.getDateTime());
        // holder.pin_icon.setVisibility(View.VISIBLE);

        //adding onclick to itemView
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendToRead(model.getColum(), model.getColum1());

            }
        });
        holder.tv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("tag",model.getColum1());
                sendToRead(model.getColum(), model.getColum1());

            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("tag",model.getColum1());
                sendToRead(model.getColum(), model.getColum1());

            }

        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.cardView);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.pin_menu:
                                //we will work on it after publish
                                Toast.makeText(context, "This Feature coming soon", Toast.LENGTH_SHORT).show();

                                break;

                            case R.id.edit_menu:
                                //this sends the data to method of send to edit
                             sendToEdit(model.getColum(),model.getColum1());

                                break;

                            case R.id.delete_menu:
                                //handle delete_menu click
                                 int id_delete = model.getId();
                                Intent intent3 = new Intent(context, MainActivity.class);
                                intent3.putExtra("key_to_delete", id_delete);
                                context.startActivity(intent3);

                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();


                return false;
            }
        });



        int colorCode = RandomColor();
        holder.cardView.setCardBackgroundColor(
                holder.itemView.getResources().getColor(colorCode, null));
    }

    private int RandomColor() {
        //this is a good method that randomly fill colors on view
        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.color1);
        colors.add(R.color.color7);
        colors.add(R.color.color2);
        colors.add(R.color.color3);
        //colors.add(R.color.color4);
        colors.add(R.color.color5);
        colors.add(R.color.color6);
        colors.add(R.color.color7);
        colors.add(R.color.color8);
        colors.add(R.color.color9);
        //colors.add(R.color.color10);
        colors.add(R.color.color11);
        colors.add(R.color.color12);
        colors.add(R.color.color13);
        colors.add(R.color.color14);
        colors.add(R.color.color15);
        colors.add(R.color.color6);
        colors.add(R.color.color3);
        //colors.add(R.color.color4);
        colors.add(R.color.color9);
        colors.add(R.color.color11);
        colors.add(R.color.color12);

        Random random = new Random();
        int random_color = random.nextInt(colors.size());
        return colors.get(random_color);
    }


    @Override
    public int getItemCount() {
        // returning the size of our array list
        return modelArrayList.size();
    }

    public void filteringList(ArrayList<Models> allFilteredNotes) {
        modelArrayList = allFilteredNotes;

        notifyDataSetChanged();
        // i don't know about this notify method i just did according
        // to tutorial of search view
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView tv_note, tv_title,date_tv;
        CardView cardView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            tv_note = itemView.findViewById(R.id.note_tv);
            tv_title = itemView.findViewById(R.id.title_tv);
            cardView = itemView.findViewById(R.id.cardView);
            date_tv = itemView.findViewById(R.id.date_tv);

        }

    }

    public void sendToEdit(String title_2, String note_2) {
        //send to edit sends data to write activity
        Intent intent;
        intent = new Intent(context, writeNoteActivity.class);
        intent.putExtra("title_key2", title_2);
        intent.putExtra("note_key2", note_2);
        context.startActivity(intent);
    }
    public void sendToRead(String title_2, String note_2) {
        //send to read sends datat to read activity
        Intent intent;
        intent = new Intent(context, readNoteActivity.class);
        intent.putExtra("title_key2", title_2);
        intent.putExtra("note_key2", note_2);
        context.startActivity(intent);
    }


}