package com.softgen.gate.model;

/**
 * Created by 9Jeevan on 18-09-2016.
 */
public class Required {
    private String Name, amount, rating;
    private int pid;


    public Required() {
    }

    public Required(String Name, String amount, String rating, int pid) {
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
