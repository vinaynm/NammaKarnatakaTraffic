package com.yogeshojha.nammakarnatakatraffic;

/**
 * Created by y0g3sh on 14/12/16.
 */
public class DataObject {
    private String offence;
    DataObject (String text1){
        offence = text1;
    }

    public String getmText1() {
        return offence;
    }

    public void setmText1(String mText1) {
        this.offence = mText1;
    }

}