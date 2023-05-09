package edu.upc.dsa.restproject.models;

public class VOPerformance {
    Integer level;
    Integer points;
    String date;

    public VOPerformance() {
        //this.id = RandomUtils.getId();
    }

    public VOPerformance(Integer level, Integer points, String date) {
        this.setLevel(level);
        this.setPoints(points);
        this.setDate(date);
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Performance [ level = "+ level +" puntos = "+ points +", date = " +date+"]";
    }

}