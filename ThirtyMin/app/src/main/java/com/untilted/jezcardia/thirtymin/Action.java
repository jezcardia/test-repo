package com.untilted.jezcardia.thirtymin;

/**
 * Created by Jezcardia on 023 23 Sep.
 */
public class Action {

    private int _id;
    private String name;
    private int points; // 1 point for every 30min per day
    private long time; // total quantity of time spent on Action

    // Base Action constructor
    public Action(){

    }

    // Constructor for new Action
    public Action (int id, String name){
        this._id = id;
        this.name = name;

        // new Actions start with zero points, zero time spent
        this.points = 0;
        this.time = 0;
    }

    // Getters and setters

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
