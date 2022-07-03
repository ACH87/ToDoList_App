package com1032.cw1.ac00889.ac00889_todolist;

/**
 *Event.java
 */

import java.io.Serializable;

/**
 * Created by Saqlain Chowdhury on 27/02/2017.
 *
 * Defines the properties of an individual event the user of the app needs to do
 */

public class Event implements Serializable{
    /** The title of the event, this is what the person will refer too*/
    private String title = null;
    /**This will be the date of when the person needs to complete the task*/
    private String date = null;
    /**says the day it was created*/
    private String dateCreated = null;
    /**says whether or not the task is completed*/
    private Boolean done = false;

    /**
     * Constructor. Sets the field values
     * @param title
     *   the title
     * @param date
     *   the date
     * @param done
     *   whether or not its done
     */
    public Event(String title, String date, String dateCreated, Boolean done){
        this.title = title;
        this.date = date;
        this.dateCreated = dateCreated;
        this.done = done;
    }

    /**
     * @return
     *   return the title of the object
     */
    public String getTitle(){
        return this.title;
    }
    /**
     * @return
     *   returns whether or the task is completed
     */
    public Boolean getDone(){
        return this.done;
    }
    /**
     * @return
     *   returns the due date
     */
    public String getDate(){
        return this.date;
    }
    /**
     * @return
     *   returns the date the event was created
     */
    public String getCreationDate(){
        return this.dateCreated;
    }
    /**
     * sets whetehr or the task is done, simply by inversing the byte
     */
    public void setDone(){
        this.done = !done;
    }

}
