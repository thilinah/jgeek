package dev.di.map.units;

import dev.di.main.MainMid;
import dev.di.map.units.IUnit;
import dev.di.map.units.BaseUnitService;
import dev.di.map.units.BaseUnit;
import dev.di.map.buildings.IBuilding;
import dev.di.map.buildings.BaseBuildingService;
import dev.di.map.buildings.BaseBuilding;
import dev.di.map.Environment;
import dev.di.map.Animation;

import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 23, 2008
 * Time: 4:29:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class BigDumyExplosion extends Animation {
    protected static Image explosion1 = null;
    Environment en = null;
    int range;
    int hurtValue;

    public BigDumyExplosion(int x, int y, Environment en) {
        super(x, y, 0, 3, 0);
        this.en = en;
        range = 22;
        hurtValue = 5;
    }


    protected Sprite getInitialSprite() {
        if (explosion1 == null)
            explosion1 = MainMid.getImage("/explosion1.png");
        return new Sprite(explosion1, 32, 32);
    }




    public void executeCustomMethods() {

    }
}
