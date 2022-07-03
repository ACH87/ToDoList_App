/**
 * ViewDetails.java
 */
package com1032.cw1.ac00889.ac00889_todolist;

/**
 * This class deines the activity that allows you view the details of an event
 *
 * @author Saqlain Chowdhury
 *
 */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


public class ViewDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
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
        TextView textView = (TextView) findViewById(R.id.title1);
        TextView date = (TextView) findViewById(R.id.date);
        TextView lastDate = (TextView) findViewById(R.id.information);

        //get the event object passed through by the adapter and display the inofrmation
        Bundle strings = getIntent().getExtras();
        String title = (String) strings.get("title");
        textView.setText(title);
        lastDate.setText(strings.get("dateCreated").toString());
        if(strings.get("date") != null){
            date.setText(strings.get("date").toString());
        }
        if(strings.get("date") == null){
            date.setText("");
        }
    }

}
