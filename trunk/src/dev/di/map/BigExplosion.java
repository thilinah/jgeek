package dev.di.map;

import dev.di.main.MainMid;
import dev.di.map.units.IUnit;
import dev.di.map.units.BaseUnitService;
import dev.di.map.units.BaseUnit;
import dev.di.map.buildings.IBuilding;
import dev.di.map.buildings.BaseBuildingService;
import dev.di.map.buildings.BaseBuilding;
import dev.di.sound.SoundSynthesizer;

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
public class BigExplosion extends Animation {
    protected static Image explosion1 = null;
    Environment en = null;
    int range;
    int hurtValue;

    public BigExplosion(int x, int y, Environment en) {
        super(x, y, 0, 3, 0);
        this.en = en;
        range = 40;
        hurtValue = 8;
    }


    protected Sprite getInitialSprite() {
        if (explosion1 == null)
            explosion1 = MainMid.getImage("/explosion1.png");
        return new Sprite(explosion1, 32, 32);
    }

    public void hurtUnits() {
        Hashtable ht = en.getUm().getUnitsInRange(x - range, y - range, x + range, y + range, null);
        Enumeration e = ht.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IUnit bu = (IUnit) ht.get(key);
            if (bu.getStrength() - hurtValue <= 0) {

                try {
                    BaseUnitService.getInstance().delete((BaseUnit) bu);
                    bu.distroy();
//                    synchronized (Environment.unitHtLock) {
                        en.units.remove(key);
//                    }
                } catch (Exception e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            } else {
                bu.setStrength(bu.getStrength() - hurtValue);
            }
        }
    }

    public void hurtBuildings() {
        Hashtable ht = en.getUm().getBuildingsInRange(x - range, y - range, x + range, y + range, null);
        Enumeration e = ht.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IBuilding bu = (IBuilding) ht.get(key);
            if (bu.getStrength() - hurtValue <= 0) {

                try {
                    BaseBuildingService.getInstance().delete((BaseBuilding) bu);
                    bu.distroy();
                    en.buildings.remove(key);
                } catch (Exception e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            } else {
                bu.setStrength(bu.getStrength() - hurtValue);
            }
        }
    }

    public void executeCustomMethods() {
        SoundSynthesizer.getInstance().playbigExpSound();
        hurtUnits();
        hurtBuildings();
    }
}
