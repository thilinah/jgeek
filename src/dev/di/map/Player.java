package dev.di.map;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 19, 2008
 * Time: 12:12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    
    public static final int ST_DEFENCE=1;
    public static final int ST_ECON=2;
    public static final int ST_ATTACK=3;
    public static final int ST_NORMAL=4;

    String name;
    int level;
    Point homeLocation;
    boolean human;
    int stratergy=ST_NORMAL;
    Environment en;
    Hashtable resources=new Hashtable();
    Hashtable buildingLevels=new Hashtable();
    Hashtable unitAmounts =new Hashtable();
    long score;

    public Player(String name, int level, Point homeLocation, boolean human) {
        this.name = name;
        this.level = level;
        this.homeLocation = homeLocation;
        this.human = human;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Point getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(Point homeLocation) {
        this.homeLocation = homeLocation;
    }

    public boolean isHuman() {
        return human;
    }

    public void setHuman(boolean human) {
        this.human = human;
    }

    public int getStratergy() {
        return stratergy;
    }

    public void setStratergy(int stratergy) {
        this.stratergy = stratergy;
    }

    public Environment getEn() {
        return en;
    }

    public void setEn(Environment en) {
        this.en = en;
    }

    public Hashtable getResources() {
        return resources;
    }

    public void setResources(Hashtable resources) {
        this.resources = resources;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Hashtable getBuildingLevels() {
        return buildingLevels;
    }

    public void setBuildingLevels(Hashtable buildingLevels) {
        this.buildingLevels = buildingLevels;
    }

    public Hashtable getUnitAmounts() {
        return unitAmounts;
    }

    public void setUnitAmounts(Hashtable unitAmounts) {
        this.unitAmounts = unitAmounts;
    }
}
