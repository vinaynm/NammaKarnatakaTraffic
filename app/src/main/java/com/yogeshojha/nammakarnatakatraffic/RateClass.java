package com.yogeshojha.nammakarnatakatraffic;

import java.sql.Date;

/**
 * Created by y0g3sh on 16/12/16.
 */

public class RateClass {

    //private variables
    int _id;
    String _lastshown;
    String _clickedon;

    // Empty constructor
    public RateClass(){

    }
    // constructor
        public RateClass(int id, String _lastshown, String _clickedon){
        this._id = id;
        this._lastshown = _lastshown;
        this._clickedon= _clickedon;
    }

    // constructor
    public RateClass(String _lastshown, String _clickedon){
        this._lastshown = _lastshown;
        this._clickedon = _clickedon;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String get_lastshown(){
        return this._lastshown;
    }

    // setting name
    public void setName(Date name){
        this._lastshown = _lastshown;
    }

    // getting phone number
    public String get_clickedon(){
        return this._clickedon;
    }

    // setting phone number
    public void set_clickedon(String _clickedon){
        this._clickedon = _clickedon;
    }
}