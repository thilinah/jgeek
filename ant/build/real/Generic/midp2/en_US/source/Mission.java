// package dev.di.data.dao;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 13, 2007
 * Time: 7:56:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Mission extends AbstractDao{

    String name;
    String saveName;
    String type;
    long time;
    Hashtable gameParams =new Hashtable();
    //user1_score
    //user1_building_level

    public Mission() {
    }

    public Mission(String name, String target, String type, int time, Hashtable units) {
        this.name = name;
        this.saveName = target;
        this.type = type;
        this.time = time;
        this.gameParams = units;
    }


    public Mission(String fullStr){
        numOfAttribs=5;
        String[] s=getAttributes(fullStr);
        name=s[0];
        saveName =s[1];
        type=s[2];
        time=Integer.parseInt(s[3]);
        gameParams = getTable(s[4]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Hashtable getGameParams() {
        return gameParams;
    }

    public void setGameParams(Hashtable gameParams) {
        this.gameParams = gameParams;
    }

    public String toString() {
        return name+"|"+saveName +"|"+type+"|"+time+"|"+tableToString(gameParams)+"|";
    }
}
