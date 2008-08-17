package dev.di.data.dao;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 5, 2007
 * Time: 8:04:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Building extends AbstractDao{

    String id=null;
    String totalStrength=null;
    String strength=null;
    Hashtable unitLevels; //what are the gameParams that can be build and current amounts built
    String level=null;
    Hashtable resourceMatrix; //what are the resourse this can generate and generation rates
    Hashtable levelMatrix;  //what are the resources and their amounts needed to upgrade to next level
    String maxLevel=null;
    String delay=null;
    String description =null;
    int x=0;
    int y=0;
    String type=null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotalStrength() {
        return totalStrength;
    }

    public void setTotalStrength(String totalStrength) {
        this.totalStrength = totalStrength;
    }

    public String getStrength() {
        return strength;
    }

    public int getStrengthInt() {
        return Integer.parseInt(strength);
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public Hashtable getUnitLevels() {
        return unitLevels;
    }

    public void setUnitLevels(Hashtable unitLevels) {
        this.unitLevels = unitLevels;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Hashtable getResourceMatrix() {
        return resourceMatrix;
    }

    public void setResourceMatrix(Hashtable resourceMatrix) {
        this.resourceMatrix = resourceMatrix;
    }

    public Hashtable getLevelMatrix() {
        return levelMatrix;
    }

    public void setLevelMatrix(Hashtable levelMatrix) {
        this.levelMatrix = levelMatrix;
    }

    public Building() {
    }

    public Building(String id, String totalStrength, String strength,
                    Hashtable unitLevels, String level, Hashtable resourceMatrix,
                    Hashtable levelMatrix, String maxLevel, String delay, String rate,
                    int x,int y,String type) {
        this.id = id;
        this.totalStrength = totalStrength;
        this.strength = strength;
        this.unitLevels = unitLevels;
        this.level = level;
        this.resourceMatrix = resourceMatrix;
        this.levelMatrix = levelMatrix;
        this.maxLevel = maxLevel;
        this.delay = delay;
        this.description = rate;
        this.x=x;
        this.y=y;
        this.type=type;
    }

    public String getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(String maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Building(String fullStr) {
        numOfAttribs=13;
        String[] s=getAttributes(fullStr);
        id=s[0];
        totalStrength=s[1];
        strength=s[2];
        unitLevels =getTable(s[3]);
        level=s[4];
        resourceMatrix =getTable(s[5]);
        levelMatrix =getTable(s[6]);
        maxLevel=s[7];
        delay=s[8];
        description =s[9];
        x=Integer.parseInt(s[10]);
        y=Integer.parseInt(s[11]);
        type=s[12];

    }

    public String toString() {
        return id+"|"+totalStrength+"|"+strength+"|"+tableToString(unitLevels)+"|"+
                level+"|"+tableToString(resourceMatrix)+"|"+tableToString(levelMatrix)+"|"+maxLevel+"|"+delay+"|"+description +"|"
                +String.valueOf(x) +"|"+String.valueOf(y) +"|"+type+"|";
    }

    public Resource getUpgradeResource(){
        Enumeration e=levelMatrix.keys();
        while(e.hasMoreElements()){
            String key=String.valueOf(e.nextElement());
            return new Resource(key,String.valueOf(levelMatrix.get(key)),"0","0" );
        }
        return null;
    }

}
