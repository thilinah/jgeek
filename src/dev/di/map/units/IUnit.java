package dev.di.map.units;

import dev.di.data.dao.Unit;
import dev.di.map.Environment;
import dev.di.map.buildings.IBuilding;

import javax.microedition.lcdui.game.Sprite;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 20, 2008
 * Time: 10:36:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IUnit {
    void move(int timerVal);
    void moveAway();
    void openFire(int timerVal);
    void fireClosest();
    void fire(IUnit unit);
    void fire(IBuilding building);
    void fire();
    void stop();
    Unit getBase();
    Sprite getSprite();
    int getX();
    int getY();
    int getSizeX();
    int getSizeY();
    void setX(int x);
    void setY(int y);
    void registerMove(int x,int y);
    void setEnvironment(Environment environment);
    int getCurrentDirection();
    void pause();
    void resume();
    int getStrength();
    void fly(int timerval);
    void setStrength(int strength);
    String getOwener();
    boolean isMyUnit(IUnit u);
    boolean isMyBuilding(IBuilding u);
    int getDistance(IUnit u);
    boolean getEngagedInFire();
    void dontFollow(IUnit u);
    void distroy();
    void think(int timerVal);
    void suspendFollow();
    void followUnit(IUnit u);
    void playMoveSound();
    void playFireSound();
    void playFlySound();
    void setLastUnitFiredMe(IUnit lastUnitFiredMe);
    IUnit getLastUnitFiredMe();
}
