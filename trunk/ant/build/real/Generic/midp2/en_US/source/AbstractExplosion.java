// package dev.di.map;

//import dev.di.map.units.IUnit;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 23, 2008
 * Time: 4:29:02 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractExplosion extends Animation {


    Environment en;
    int range;
    int hurtValue;

    protected AbstractExplosion(int x, int y, int startFrame, int endFrame, int frameSkipRate, Environment en, int range, int hurtValue) {
        super(x, y, startFrame, endFrame, frameSkipRate);
        this.en = en;
        this.range = range;
        this.hurtValue = hurtValue;
    }

    public void hurtUnits() {
        Hashtable ht = en.getUm().getUnitsInRange(x - range, y - range, x + range, y + range, null);
        Enumeration e = ht.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IUnit bu = (IUnit) ht.get(key);
            if (Integer.parseInt(bu.getBase().getStrength()) - hurtValue <= 0) {
                //synchronized (Environment.unitHtLock) {
                    en.units.remove(key);
                //}

            } else {
                bu.getBase().setStrength(String.valueOf(Integer.parseInt(bu.getBase().getStrength()) - hurtValue));
            }
        }
    }

    public void executeCustomMethods() {
        hurtUnits();
    }

}
