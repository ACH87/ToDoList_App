/**
 * MainActivity.java
 */

package com1032.cw1.ac00889.ac00889_todolist;
/**
 * Defines the main page of the app, displaying the Instructions as well as add and delete button
 *
 * @author Saqlain Chowdhury
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    /**request code for the activity result*/
    private final static int REQ_CODE_STRING = 0001;
    /**the Instructions of events the person need to do*/
    private static List<Event> list = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "screen updated", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivityForResult(new Intent(MainActivity.this, ViewDetails.class), REQ_CODE_STRING);
            }
        });

        final Button addButton = (Button) findViewById(R.id.button1);
        final Button deleteButton = (Button) findViewById(R.id.button2);
        final MyAdapter adapter = new MyAdapter(this, list);
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        readFileData();
        //starts a new activity allowing you to add new elements
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, NewEvent.class), REQ_CODE_STRING);
            }
        });
        //clears the entire Instructions, displaying an alert builder as well
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder warning = new AlertDialog.Builder(MainActivity.this);
                warning.setMessage("Are you sure you want to delete all?");
                warning.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.clear();
                    }
                });
                warning.setNegativeButton("no", null);
                warning.create();
                warning.show();
            }
        });
    }

    /**
     * on return from the NewEvent activity
     *
     * @param requestCode
     *  the request code
     * @param resultCode
     *  the result code
     * @param data
     *  the date given through the intent put extra
     *
     *  method retrieves the data passed through the intent and adds it to the Instructions
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_STRING && resultCode == RESULT_OK){
            Event title = (Event) data.getSerializableExtra("title");
            list.add(title);
            final MyAdapter adapter = new MyAdapter(this, list);
            final ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * reads the data from a text file, then applies the read file helper
     */
    private void readFileData()
    {
        try
        {
            Scanner scanner = new Scanner( openFileInput( "lists.txt" ) );
            readFileHelper( scanner );
        }
        catch ( IOException ioe)
        {
            ioe.printStackTrace();
        }
    }


    /**
     *
     * allows you read the the file by splitting each line and converting them into an Event object
     *
     * @param scan
     *  the scanner object that goes through the text fie
     */
    private void readFileHelper( Scanner scan )
    {
        //make sure the Instructions is empty, this is in case the Instructions has some values already in it, making sure you dont get any double values
        list.clear();
        while ( scan.hasNextLine() )
        {
            String line = scan.nextLine();
            //creating an array everytime of strings splitting a line up into variables for an event
            String[] pieces = line.split( "-" );
            //creats a new event, and adds the event so long as the Instructions doesn't already contain the event
            Event event = new Event(pieces[0], pieces[1], pieces[2], toBoolean(pieces[3]));
            if(!list.contains(event)) {
                list.add(event);
            }
        }
    }

    /**
     *
     * private method used to convert a string into a boolean, allows to convert a string from the text file into a boolean value for an Event object
     * @param boo
     *  a string from the text file to convert
     * @return
     *  returns either true or false
     */
    private Boolean toBoolean(String boo){
        Boolean complete = false;
        if(boo.replace(" ", "").equalsIgnoreCase("true")){
           complete = true;
        }
        return complete;
    }

    /**
     *
     * whenever the app is paused, save all the events in the Instructions intoa  text file
     *
     */
    @Override
    public void onStop() {
         {
            try {
                //overwrite the previous text file
                PrintStream previousOutput = new PrintStream(openFileOutput("lists.txt", 0));
                for(Event saveEvent : MyAdapter.getList()) {
                    //for each event in the adapter, convert it into a string and split it up, allowing the programme to convert back into an event later on
                    PrintStream futureOutput = new PrintStream(openFileOutput("lists.txt", MODE_PRIVATE | MODE_APPEND));
                    futureOutput.println(saveEvent.getTitle() + "-" + saveEvent.getDate() + "-" + saveEvent.getCreationDate() + "-" + saveEvent.getDone().toString());
                    futureOutput.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        super.onStop();

    }
}
