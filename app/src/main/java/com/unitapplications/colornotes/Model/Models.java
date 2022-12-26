package com.unitapplications.colornotes.Model;


public class Models {
    private int id;
    private String colum;
    private String colum1;
    private String dateTime;


    public Models(int id) {
        this.id = id;
    }
    public Models() {

    }

    public Models(int id, String colum) {
        this.id = id;
        this.colum = colum;
    }
    public Models(String colum,String colum1) {
        this.colum = colum;
        this.colum1 = colum1;
    }

    public Models(String colum, String colum1,String dateTime) {
        this.colum = colum;
        this.colum1 = colum1;
        this.dateTime = dateTime;
    }
    public Models(String colum1) {
        this.colum1 = colum1;
    }


    public Models(int id, String colum, String colum1) {
        this.id = id;
        this.colum = colum;
        this.colum1 = colum1;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColum() {
        return colum;
    }

    public void setColum(String colum) {
        this.colum = colum;
    }

    public String getColum1() {
        return colum1;
    }

    public void setColum1(String colum1) {
        this.colum1 = colum1;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}