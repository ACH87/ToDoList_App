/**
 * MyAdapter.java
 */

package com1032.cw1.ac00889.ac00889_todolist;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * this class extends array adapter, allows me to create my own adapter for personal uses
 *
 * Created by Saqlain Chowdhury on 04/03/2017.
 */

public class MyAdapter extends ArrayAdapter<Event> {
    /**Instructions of events that comes from the Instructions*/
    private static List<Event> values;
    /**the context that represent the activity the adapter is used on*/
    private Context context;
    /**the request code used to send data*/
    private final static int REQ_CODE_STRING = 0001;
    /**date used to compare the date of the event to the current date*/
    private Date date = new Date();
    /**simple date format used to convert the current date to a comparable string*/
    private SimpleDateFormat format = new SimpleDateFormat("dd/MMMM/yyyy");

    /**
     *
     * Constructor for the adapter
     *
     * @param context
     *  this represents the activity the adapter is being used on
     * @param values
     *  the Instructions that the adapter will convert to a listview
     */
    public MyAdapter(Context context, List<Event> values)
    {
        super(context, R.layout.listview, values);
        this.values = values;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.listview, parent, false);
        final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);
        final TextView itemText = (TextView) rowView.findViewById(R.id.label);
        final Button button = (Button) rowView.findViewById(R.id.remove);
        rowView.setClickable(true);

        //sets the background color to cyan if an event is due for that day
        if(values.get(position).getDate()!=null) {
            if (format.format(date).equalsIgnoreCase(values.get(position).getDate())) {
                rowView.setBackgroundColor(Color.CYAN);
            }
        }

        //sets the background color and check box to red and ticked respectively, marking if the event is done
        if(values.get(position).getDone() == true){
            rowView.setBackgroundColor(Color.RED);
            checkBox.setChecked(true);
        }

        //set the TextView to the event title
        itemText.setText(values.get(position).getTitle());

        //on long click it removes the event from the Instructions and adapter, without displaying an alert builder
        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                values.remove(position);
                notifyDataSetChanged();
                return true;
            }
        });

        //checkbox that changes background color and set the done values in the event to true/false accordingly
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    rowView.setBackgroundColor(Color.RED);
                    values.get(position).setDone();
                }
                if(!checkBox.isChecked()){
                    rowView.setBackgroundColor(Color.WHITE);
                    values.get(position).setDone();
                }
            }
        });

        //allows you remove the the event on click but displays an alert builder
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder warning = new AlertDialog.Builder(context);
                warning.setMessage("Are you sure you want to delete this event?");
                warning.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        values.remove(position);
                        notifyDataSetChanged();
                    }
                });
                warning.setNegativeButton("no", null);
                warning.create();
                warning.show();
            }
        });

        //allows you to click on a row to display more information, starting an new invent and passing the date and position
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewDetails.class);
                intent.putExtra("title", values.get(position).getTitle());
                intent.putExtra("date", values.get(position).getDate());
                intent.putExtra("dateCreated", values.get(position).getCreationDate());
                ((Activity) context).startActivityForResult(intent, REQ_CODE_STRING);
            }
        });

        return rowView;
    }

    /**
     * @return
     *  returns the Instructions of the events in the adapter, used to save them to a text file
     */
    public static List<Event> getList(){
        return values;
    }

}
