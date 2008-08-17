package dev.di.map;

import dev.di.map.units.IUnit;
import dev.di.map.units.DynamicUnit;
import dev.di.map.buildings.BaseBuilding;
import dev.di.map.buildings.IBuilding;
import dev.di.main.JCageConfigurator;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 21, 2008
 * Time: 5:42:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnitManager {
    private static Random RAND = null;
    Environment en;
    int height = 0;
    int width = 0;
    private IUnit lastSelectedUnit = null;
    private IBuilding lastSelectedBuilding = null;
    private boolean unitSelected = false;
    private boolean buildingSelected = false;
    BaseBuilding buildingToBuild = null;
    private boolean showInfo = false;

    public Hashtable unitGroundCells = new Hashtable();
    public Hashtable buildingGroundCells = new Hashtable();


    public UnitManager(Environment en, int height, int width) {
        this.en = en;
        this.height = height;
        this.width = width;
        if (RAND == null)
            RAND = new Random();
        en.setUm(this);
    }

    public BaseBuilding getBuildingToBuild() {
        return buildingToBuild;
    }

    public void setBuildingToBuild(BaseBuilding buildingToBuild) {
        this.buildingToBuild = buildingToBuild;
    }

    public IUnit getLastSelectedUnit() {
        return lastSelectedUnit;
    }

    public IBuilding getLastSelectedBuilding() {
        return lastSelectedBuilding;
    }

    public void setLastSelectedBuilding(IBuilding lastSelectedBuilding) {
        this.lastSelectedBuilding = lastSelectedBuilding;
    }

    public void setLastSelectedUnit(IUnit lastSelectedUnit) {
        this.lastSelectedUnit = lastSelectedUnit;
    }

    public boolean isUnitSelected() {
        return unitSelected;
    }

    public void setUnitSelected(boolean unitSelected) {
        buildingSelected = false;
        this.unitSelected = unitSelected;
    }

    public boolean isBuildingSelected() {
        return buildingSelected;
    }

    public void setBuildingSelected(boolean buildingSelected) {
        unitSelected = false;
        this.buildingSelected = buildingSelected;
    }

    public boolean getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(boolean showInfo) {
        this.showInfo = showInfo;
    }

    public IUnit getUnitAtLocation(int x, int y) {
//        synchronized (Environment.unitHtLock) {
        Enumeration e = en.units.keys();
        while (e.hasMoreElements()) {
            IUnit bu = (IUnit) en.units.get(e.nextElement());
            if (bu.getSizeX() < 25 || bu.getY() < 25) {
                if ((bu.getX() - bu.getSizeX() / 2 <= x && x <= bu.getX() + bu.getSizeX() / 2) &&
                        (bu.getY() - bu.getSizeY() / 2 <= y && y <= bu.getY() + bu.getSizeY() / 2)) {
                    if (!isUnitSelected())
                        lastSelectedUnit = bu;
                    return bu;
                }
            } else {
                if ((bu.getX() - (bu.getSizeX() * 2) / 5 <= x && x <= bu.getX() + (bu.getSizeX() * 2) / 5) &&
                        (bu.getY() - (bu.getSizeY() * 2) / 5 <= y && y <= bu.getY() + (bu.getSizeY() * 2) / 5)) {
                    if (!isUnitSelected())
                        lastSelectedUnit = bu;
                    return bu;
                }
            }
        }
        return null;
//        }
    }

    public IBuilding getBuildingAtLocation(int x, int y) {
        Enumeration e = en.buildings.keys();
        while (e.hasMoreElements()) {
            IBuilding bu = (IBuilding) en.buildings.get(e.nextElement());
            if (bu.getSizeX() < 25 || bu.getY() < 25) {
                if ((bu.getX() - bu.getSizeX() / 2 <= x && x <= bu.getX() + bu.getSizeX() / 2) &&
                        (bu.getY() - bu.getSizeY() / 2 <= y && y <= bu.getY() + bu.getSizeY() / 2)) {
                    if (!isBuildingSelected())
                        lastSelectedBuilding = bu;
                    return bu;
                }
            } else {
                if ((bu.getX() - (bu.getSizeX() * 2) / 5 <= x && x <= bu.getX() + (bu.getSizeX() * 2) / 5) &&
                        (bu.getY() - (bu.getSizeY() * 2) / 5 <= y && y <= bu.getY() + (bu.getSizeY() * 2) / 5)) {
                    if (!isBuildingSelected())
                        lastSelectedBuilding = bu;
                    return bu;
                }
            }
        }
        return null;
    }


    public IUnit isUnitCellExists(Point point, String myId) {
        Enumeration e = unitGroundCells.keys();
        while (e.hasMoreElements()) {
            String key = String.valueOf(e.nextElement());
            if (!key.equals(myId)) {
                Point[] ps = (Point[]) unitGroundCells.get(key);
                if (containsItemInArray(ps, point)) {
                    return (IUnit) en.units.get(key);
                }
            }
        }
        return null;
    }

    public IBuilding isBuildingCellExists(Point point) {
        Enumeration e = buildingGroundCells.keys();
        while (e.hasMoreElements()) {
            String key = String.valueOf(e.nextElement());
            Point[] ps = (Point[]) buildingGroundCells.get(key);
            if (containsItemInArray(ps, point)) {
                return (IBuilding) en.buildings.get(key);
            }
        }
        return null;
    }


    public boolean containsItemInArray(Object[] oa, Object o) {
        for (int i = 0; i < oa.length; i++) {
            if (oa[i].equals(o)) {
                return true;
            }
        }
        return false;
    }


    public void updateUnitGroundCells() {
//        synchronized (Environment.unitHtLock) {

        unitGroundCells = new Hashtable();
        Enumeration e = en.units.keys();
        while (e.hasMoreElements()) {
            IUnit bu = (IUnit) en.units.get(e.nextElement());
            Point[] ps = en.getCellsInRange(bu.getX() - bu.getSizeX() / 2, bu.getY() - bu.getSizeY() / 2,
                    bu.getX() + bu.getSizeX() / 2, bu.getY() + bu.getSizeY() / 2);
            DynamicUnit du = (DynamicUnit) bu;
            unitGroundCells.put(du.getUnitId(), ps);
        }
//        }
    }

    public void updateBuildingGroundCells() {
        buildingGroundCells = new Hashtable();
        Enumeration e = en.buildings.keys();
        while (e.hasMoreElements()) {
            IBuilding bu = (IBuilding) en.buildings.get(e.nextElement());
            Point[] ps = en.getCellsInRange(bu.getX() - bu.getSizeX() / 2, bu.getY() - bu.getSizeY() / 2,
                    bu.getX() + bu.getSizeX() / 2, bu.getY() + bu.getSizeY() / 2);
            BaseBuilding du = (BaseBuilding) bu;
            buildingGroundCells.put(du.getBuildingId(), ps);
        }
    }


    public IUnit getFirstUnitInRange(int x1, int y1, int x2, int y2, IUnit me) {
//        synchronized (Environment.unitHtLock) {

        Enumeration e = en.units.keys();
        while (e.hasMoreElements()) {
            IUnit bu = (IUnit) en.units.get(e.nextElement());
            if (bu != me && !me.isMyUnit(bu)) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    return bu;
                }
            }
        }

        return null;
//        }
    }

    public IBuilding getFirstBuildingInRange(int x1, int y1, int x2, int y2, IUnit me) {

        Enumeration e = en.buildings.keys();
        while (e.hasMoreElements()) {
            IBuilding bu = (IBuilding) en.buildings.get(e.nextElement());
            if (!me.isMyBuilding(bu)) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    return bu;
                }
            }
        }

        return null;
    }


    public Hashtable getAllUnitsInRange(int x1, int y1, int x2, int y2, IUnit me) {
//        synchronized (Environment.unitHtLock) {
        Hashtable ht = new Hashtable();
        Enumeration e = en.units.keys();
        while (e.hasMoreElements()) {
            IUnit bu = (IUnit) en.units.get(e.nextElement());
            if (!((DynamicUnit) me).getUnitId().equals(((DynamicUnit) bu).getUnitId())) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    ht.put(((DynamicUnit) bu).getUnitId(), bu);
                }
            }
        }

        return ht;
//        }
    }


    public Hashtable getAllUnitsInRange(int x1, int y1, int x2, int y2) {
//        synchronized (Environment.unitHtLock) {
        Hashtable ht = new Hashtable();
        Enumeration e = en.units.keys();
        while (e.hasMoreElements()) {
            IUnit bu = (IUnit) en.units.get(e.nextElement());
            if ((bu.getX() > x1 && x2 > bu.getX()) &&
                    (bu.getY() > y1 && y2 > bu.getY())) {

                ht.put(((DynamicUnit) bu).getUnitId(), bu);
            }
        }

        return ht;
//        }
    }

    public Hashtable getAllBuildingsInRange(int x1, int y1, int x2, int y2) {
        Hashtable ht = new Hashtable();
        Enumeration e = en.buildings.keys();
        while (e.hasMoreElements()) {
            IBuilding bu = (IBuilding) en.buildings.get(e.nextElement());
            //System.out.println("x1 :" + x1);
            //System.out.println("y1 :" + y1);
            //System.out.println("x2 :" + x2);
            //System.out.println("y2 :" + y2);
            //System.out.println("bu.getX() " + bu.getX());
            //System.out.println("bu.getY() " + bu.getY());
            if ((bu.getX() > x1 && x2 > bu.getX()) &&
                    (bu.getY() > y1 && y2 > bu.getY())) {

                ht.put(((BaseBuilding) bu).getBuildingId(), bu);
            }
        }

        return ht;
    }


    public IUnit getFirstEnemyUnitInRange(int x1, int y1, int x2, int y2, IBuilding me) {

        Enumeration e = en.units.keys();
        while (e.hasMoreElements()) {
            IUnit bu = (IUnit) en.units.get(e.nextElement());
            if (!me.isMyUnit(bu)) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    return bu;
                }
            }
        }

        return null;
    }

    public IBuilding getFirstEnemyBuildingInRange(int x1, int y1, int x2, int y2, IBuilding me) {

        Enumeration e = en.buildings.keys();
        while (e.hasMoreElements()) {
            IBuilding bu = (IBuilding) en.buildings.get(e.nextElement());
            if (bu != me && !me.isMyBuilding(bu)) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    return bu;
                }
            }
        }

        return null;
    }

//    public IUnit getRandomUnitInRange(int x1, int y1, int x2, int y2, IUnit me) {
//        int r=RAND.nextInt()%en.units.size()-1;
//        int i=0;
//        Enumeration e = en.units.keys();
//        while (e.hasMoreElements()) {
//            IUnit bu = (IUnit) en.units.get(e.nextElement());
//            if (bu != me && i==r) {
//                if ((bu.getX() > x1 && x2 > bu.getX()) &&
//                        (bu.getY() > y1 && y2 > bu.getY())) {
//
//                    return bu;
//                }
//            }
//        }
//        return null;
//    }


    public Hashtable getEnemyUnitsInRange(int x1, int y1, int x2, int y2, IUnit me) {
//        synchronized (Environment.unitHtLock) {
        Hashtable ht = new Hashtable();
        Enumeration e = en.units.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IUnit bu = (IUnit) en.units.get(key);
            if (bu != me && !me.isMyUnit(bu)) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    ht.put(key, bu);
                }
            }
        }
        return ht;
//        }
    }

    public Hashtable getAllyUnitsInRange(int x1, int y1, int x2, int y2, IUnit me) {
//        synchronized (Environment.unitHtLock) {
        Hashtable ht = new Hashtable();
        Enumeration e = en.units.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IUnit bu = (IUnit) en.units.get(key);
            if (bu != me && me.isMyUnit(bu)) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    ht.put(key, bu);
                }
            }
        }
        return ht;
//        }
    }


    public Hashtable getUnitsInRange(int x1, int y1, int x2, int y2, IUnit me) {
//        synchronized (Environment.unitHtLock) {
        Hashtable ht = new Hashtable();
        Enumeration e = en.units.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IUnit bu = (IUnit) en.units.get(key);
            if (bu != me) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    ht.put(key, bu);
                }
            }
        }
        return ht;
//        }
    }

    public Hashtable getAllayBuildingsInRange(int x1, int y1, int x2, int y2, IUnit me) {
        Hashtable ht = new Hashtable();
        Enumeration e = en.buildings.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IBuilding bu = (IBuilding) en.buildings.get(key);
            if (me.isMyBuilding(bu)) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    ht.put(key, bu);
                }
            }
        }
        return ht;
    }

     public Hashtable getAllayBuildingsInRange(int x1, int y1, int x2, int y2, String owner) {
        Hashtable ht = new Hashtable();
        Enumeration e = en.buildings.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IBuilding bu = (IBuilding) en.buildings.get(key);
            if (owner.equals(bu.getOwener())){
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    ht.put(key, bu);
                }
            }
        }
        return ht;
    }


    public Hashtable getEnemyBuildingsInRange(int x1, int y1, int x2, int y2, IUnit me) {
        Hashtable ht = new Hashtable();
        Enumeration e = en.buildings.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IBuilding bu = (IBuilding) en.buildings.get(key);
            if (!me.isMyBuilding(bu)) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    ht.put(key, bu);
                }
            }
        }
        return ht;
    }


    public Hashtable getBuildingsInRange(int x1, int y1, int x2, int y2, IBuilding me) {
        Hashtable ht = new Hashtable();
        Enumeration e = en.buildings.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IBuilding bu = (IBuilding) en.buildings.get(key);
            if (bu != me) {
                if ((bu.getX() > x1 && x2 > bu.getX()) &&
                        (bu.getY() > y1 && y2 > bu.getY())) {

                    ht.put(key, bu);
                }
            }
        }
        return ht;
    }


    public IUnit getRandomEnemyUnitInRange(int x1, int y1, int x2, int y2, IUnit me) {
        IUnit u = null;
        Hashtable ht = getEnemyUnitsInRange(x1, y1, x2, y2, me);
        if (ht.size() > 0) {
            int k = Math.abs(RAND.nextInt()) % ht.size();
            u = (IUnit) JCageConfigurator.getSelectedIndex(k, ht);
        }
        return u;
    }

    public IUnit getRandomEnemyUnitInRange(Hashtable ht) {
        IUnit u = null;
        if (ht.size() > 0) {
            int k = Math.abs(RAND.nextInt()) % ht.size();
            u = (IUnit) JCageConfigurator.getSelectedIndex(k, ht);
        }
        return u;
    }


    public IUnit getRandomAllayUnitInRange(int x1, int y1, int x2, int y2, IUnit me) {
        IUnit u = null;
        Hashtable ht = getAllyUnitsInRange(x1, y1, x2, y2, me);
        if (ht.size() > 0) {
            int k = Math.abs(RAND.nextInt()) % ht.size();
            u = (IUnit) JCageConfigurator.getSelectedIndex(k, ht);
        }
        return u;
    }

    public IBuilding getRandomEnemyBuildingInRange(int x1, int y1, int x2, int y2, IUnit me) {
        IBuilding u = null;
        Hashtable ht = getEnemyBuildingsInRange(x1, y1, x2, y2, me);
        if (ht.size() > 0) {
            int k = Math.abs(RAND.nextInt()) % ht.size();
            u = (IBuilding) JCageConfigurator.getSelectedIndex(k, ht);
        }
        return u;
    }

    public void moveAndFireUnits(int timerValue) {

        updateBuildingGroundCells();

        Enumeration e = en.units.keys();
        while (e.hasMoreElements()) {
            IUnit bu = (IUnit) en.units.get(e.nextElement());
            bu.think(timerValue);
            bu.move(timerValue);
            if (!bu.getBase().getType().equals(JCageConfigurator.UNIT_GROUND_A)) {
                bu.openFire(timerValue);
            }
        }

        Enumeration e1 = en.buildings.keys();
        while (e1.hasMoreElements()) {
            IBuilding bu = (IBuilding) en.buildings.get(e1.nextElement());
            if (bu.getBase().getType().equals(JCageConfigurator.BUILDING_DEFEND))
                bu.openFire(timerValue);
            bu.updateStatus(timerValue);
        }
    }

}
