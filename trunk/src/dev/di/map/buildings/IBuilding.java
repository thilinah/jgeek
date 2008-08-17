package dev.di.map.buildings;

import dev.di.map.Environment;
import dev.di.map.units.IUnit;
import dev.di.data.dao.Building;

import javax.microedition.lcdui.game.Sprite;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 2, 2008
 * Time: 4:52:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IBuilding {
    void openFire(int timerVal);
    void fireClosest();
    void fire(IUnit unit);
    void fire(IBuilding building);
    void fire();
    Building getBase(); //imp
    Sprite getSprite(); //imp
    int getX(); //imp
    int getY(); ///imp
    int getSizeX(); //imp
    int getSizeY(); //imp
    void setX(int x); //imp
    void setY(int y); //imp
    void setEnvironment(Environment environment);
    int getStrength();
    void setStrength(int strength);
    String getOwener();
    boolean isMyUnit(IUnit u);
    boolean isMyBuilding(IBuilding u);
    int getDistance(IBuilding u);
    int getStatus();
    void setStatus(int status);
    boolean isStillCreating();
    void updateStatus(int timer);
    void distroy();

}
