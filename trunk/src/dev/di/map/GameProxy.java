package dev.di.map;

import dev.di.data.dao.PlanetDao;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 19, 2008
 * Time: 12:09:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameProxy {
    String saveName;
    Hashtable players;
    Player humanPlayer;
    Environment en;
    String mapName;
    String map;
//    Hashtable buildingLevels=new Hashtable();
//    Hashtable unitAmounts =new Hashtable();
    //we use games main resource list at runtime. but push data to this list when saving
    //when a saved game is  loaded ... this has to be loaded in to main resouce hashtable
    Hashtable resources=new Hashtable();

    public GameProxy(String saveName,Player humanPlayer,Player p2) {
        this.saveName = saveName;
        players=new Hashtable();
        this.humanPlayer=humanPlayer;
        players.put(p2.getName(),p2);
    }

    public GameProxy(String saveName,Player humanPlayer,Player p2,Player p3) {
        this.saveName = saveName;
        players=new Hashtable();
        this.humanPlayer=humanPlayer;
        players.put(p2.getName(),p2);
        players.put(p3.getName(),p3);
    }

    public Environment getEn() {
        return en;
    }

    public void setEn(Environment en) {
        this.en = en;
        Enumeration ex=players.keys();
        while(ex.hasMoreElements()){
            Player p=(Player)players.get(ex.nextElement());
            p.setEn(en);
        }
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public Hashtable getBuildingLevels() {
        return humanPlayer.getBuildingLevels();
    }

    public Hashtable getUnitAmounts() {
        return humanPlayer.getBuildingLevels();
    }


    public void setUnitAmounts(Hashtable unitAmounts) {
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public Hashtable getPlayers() {
        return players;
    }

    public void setPlayers(Hashtable players) {
        this.players = players;
    }

    public Hashtable getResources() {
        return resources;
    }

    public Player getPlayer(String name){
        Enumeration en=players.keys();
        while(en.hasMoreElements()){
            String key=(String)en.nextElement();
            if(key.equals(name)){
                return (Player)players.get(key);
            }

        }
        return null;
    }
}
