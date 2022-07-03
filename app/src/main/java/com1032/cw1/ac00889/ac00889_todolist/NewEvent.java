/**
 * NewEvent.java
 */
package com1032.cw1.ac00889.ac00889_todolist;
/**
 * defines the activity that allows you create a new event for the Instructions
 *
 * @author Saqlain Chowdhury
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewEvent extends AppCompatActivity{

    /**the new event we're looking to create*/
    private Event event;
    /**Simple date format*/
    private SimpleDateFormat format = new SimpleDateFormat("dd/MMMM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final Button saveButton = (Button) findViewById(R.id.button3);
        final DatePicker date = (DatePicker) findViewById(R.id.datePicker);
        final EditText text = (EditText) findViewById(R.id.edit_message);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.show);

        //if checkbox is checked, show the calendar, else ignore it
        if(!checkBox.isChecked()) {
            date.setVisibility(View.GONE);
        }
        if(checkBox.isChecked()){
            date.setVisibility(View.VISIBLE);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    date.setVisibility(View.VISIBLE);
                }
                else{
                    date.setVisibility(View.GONE);
                }
            }
        });

        //create a new invent to pass back into the MainActivity
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the string from the EditText, this will be the title
                String name = text.getText().toString();
                Date thisDate = new Date();
                event = new Event(name, null, thisDate.toString(), false);
                //set a date only if the user wants one
                if(checkBox.isChecked()){
                    int day = date.getDayOfMonth();
                    int month = date.getMonth();
                    int year = date.getYear();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year,month,day);
                    event = new  Event(name, format.format(calendar.getTime()), thisDate.toString(), false);
                    Toast.makeText(NewEvent.this, "Due date: " + day +"/"+ (month + 1) +"/"+ year, Toast.LENGTH_SHORT).show();
                    
                }
                Intent intent = new Intent();
                //return the event with the name title
                intent.putExtra("title", event);
                setResult(MainActivity.RESULT_OK, intent);
                finish();
            }
        });

    }


}
