// package dev.di.data.dao;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 13, 2007
 * Time: 10:25:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Unit extends AbstractDao{
    String id;
    String strength;
    String type;
    int delay;
    Hashtable resources;
    String description;
    int amount;
    int speed;
    int fireRange;
    int sizeX;
    int sizeY;

    public Unit(String id, String strength, String type,
                int delay, Hashtable resources, String description,
                int amount,int speed,int fireRange,int sizeX,int sizeY
                ) {
        this.id = id;
        this.strength = strength;
        this.type = type;
        this.delay = delay;
        this.resources = resources;
        this.description = description;
        this.amount = amount;
        this.speed=speed;
        this.fireRange=fireRange;
        this.sizeX =sizeX;
        this.sizeY =sizeY;
    }

    public Unit(String fullStr){
        numOfAttribs=11;
        String[] s=getAttributes(fullStr);
        id=s[0];
        strength=s[1];
        type=s[2];
        delay=Integer.parseInt(s[3]);
        resources=getTable(s[4]);
        description= s[5];
        amount= Integer.parseInt(s[6]);
        speed= Integer.parseInt(s[7]);
        fireRange= Integer.parseInt(s[8]);
        sizeX = Integer.parseInt(s[9]);
        sizeY = Integer.parseInt(s[10]);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Hashtable getResources() {
        return resources;
    }

    public void setResources(Hashtable resources) {
        this.resources = resources;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFireRange() {
        return fireRange;
    }

    public void setFireRange(int fireRange) {
        this.fireRange = fireRange;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public String toString() {
        return id+"|"+strength+"|"+type+"|" +String.valueOf(delay) +"|"
                +tableToString(resources)+"|"+description+"|"+String.valueOf(amount)
                +"|"+String.valueOf(speed)+"|"+String.valueOf(fireRange)+"|"
                +String.valueOf(sizeX)+"|"+String.valueOf(sizeY)+"|";
    }
}
