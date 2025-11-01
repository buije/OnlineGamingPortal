package com.gamingportal.model;

public class Player {
    private int id;
    private String gamerTag;
    private String password;
    private int credits;

//constructors
public Player(int id, String gamerTag, String password, int credits) {
        this.id = id;
        this.gamerTag = gamerTag;
        this.password = password;
        this.credits = credits;
    }

    // Getters and Setters - standard JavaBean pattern
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getGamerTag() { return gamerTag; }
    public void setGamerTag(String gamerTag) { this.gamerTag = gamerTag; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
}








