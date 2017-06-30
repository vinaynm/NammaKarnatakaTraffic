package com.yogeshojha.nammakarnatakatraffic;

import java.sql.Date;

/**
 * Created by y0g3sh on 16/12/16.
 */

public class RateClass {

    int _id;
    String _lastshown;
    String _clickedon;
    public RateClass(){

    }
    // constructor
        public RateClass(int id, String _lastshown, String _clickedon){
        this._id = id;
        this._lastshown = _lastshown;
        this._clickedon= _clickedon;
    }

    public RateClass(String _lastshown, String _clickedon){
        this._lastshown = _lastshown;
        this._clickedon = _clickedon;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String get_lastshown(){
        return this._lastshown;
    }

    public void setName(Date name){
        this._lastshown = _lastshown;
    }

    public String get_clickedon(){
        return this._clickedon;
    }

    public void set_clickedon(String _clickedon){
        this._clickedon = _clickedon;
    }
}