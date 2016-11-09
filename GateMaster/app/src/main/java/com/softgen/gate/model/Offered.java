package com.softgen.gate.model;

/**
 * Created by 9Jeevan on 16-09-2016.
 */
public class Offered {
    private String Name,amount,rating;
    private int pid;


    public Offered(){
    }

    public Offered(String Name, String amount, String rating, int pid) {
        this.Name = Name;
        this.amount = amount;
        this.rating = rating;
        this.pid = pid;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
