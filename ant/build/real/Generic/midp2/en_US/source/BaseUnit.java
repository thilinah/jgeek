// package dev.di.map.units;

//import dev.di.data.dao.AbstractDao;
//import dev.di.data.dao.Unit;
//import dev.di.main.JCageConfigurator;
//import dev.di.main.MainMid;
//import dev.di.map.*;
//import dev.di.map.buildings.IBuilding;
//import dev.di.util.ImageUtility;

import javax.microedition.lcdui.game.Sprite;
import java.util.Date;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 20, 2008
 * Time: 10:40:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseUnit extends AbstractDao implements IUnit {
    protected boolean forceMove = false; //move over a unit
    int forceMoveCount = 0;
    int fireRate=5;
    int thinkRate=5;
    protected Unit unitBase = null;
    private String unitId = null;
    private String unitType = null;
    private int x = 0;
    private int y = 0;
    private Sprite pic = null;
    private String missionId = null;
    private int strength = 0;
    private String owener = null;

    Environment environment = null;


    public BaseUnit(Environment environment, String mission, Unit base, String owener) {

        this.environment = environment;
        this.unitBase = base;
        this.owener = owener;
        this.missionId = mission;
        Date d = new Date();
        this.unitId = String.valueOf(d.getTime());
        this.unitType = base.getId();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.strength = Integer.parseInt(base.getStrength());

        if (owener.equals(MainMid.getClientConfigurator().user)) {
            pic = new Sprite(MainMid.getImage("/" + base.getId() + "_a.png"), base.getSizeX(), base.getSizeY());
        } else {
            pic = new Sprite(ImageUtility.replaceColourBlue(MainMid.getImage("/" + base.getId() + "_a.png")), base.getSizeX(), base.getSizeY());
        }
        setOtherParam();

    }

    public BaseUnit(String fullStr) {
        numOfAttribs = 6;
        String[] s = getAttributes(fullStr);
        missionId = s[0].substring(0, s[0].indexOf("*"));
        unitId = s[0].substring(s[0].indexOf("*") + 1);
        unitType = s[1];
//        //System.out.println(unitType);
        unitBase = JCageConfigurator.getUnitById(unitType);
        x = Integer.parseInt(s[2]);
        y = Integer.parseInt(s[3]);
        strength = Integer.parseInt(s[4]);
        owener = s[5];
        setOtherParam();
    }

    private void setOtherParam(){
        fireRate=Math.abs(Ground.rand.nextInt() % 5)+4;
        thinkRate=Math.abs(Ground.rand.nextInt() % 4)+4;
    }


    public void setEnvironmentAndPicture(Environment en) {
        environment = en;
        if (owener.equals(MainMid.getClientConfigurator().user)) {
            pic = new Sprite(MainMid.getImage("/" + unitType + "_a.png"), unitBase.getSizeX(), unitBase.getSizeY());
        } else {
            pic = new Sprite(ImageUtility.replaceColourBlue(MainMid.getImage("/" + unitType + "_a.png")), unitBase.getSizeX(), unitBase.getSizeY());
        }
    }


    boolean checkOtherUnitLocations1(int x1, int x2, int y1, int y2) {
        Hashtable ht = environment.getUm().getAllBuildingsInRange(x1, y1, x2, y2);
        if (ht.size() > 0) {
//            //System.out.println(getUnitId() + " :building on my way");
            return false;
        } else {
            ht = environment.getUm().getAllUnitsInRange(x1, y1, x2, y2, this);
            if (ht.size() > 0) {
//                //System.out.println(getUnitId() + " :unit on my way =" + ht.size());

                if (forceMove) {      //to handle unit on unit senario
//                    //System.out.println("Skiped on force move " + this);
                    return true;
                }
                forceMoveCount++;

                if (forceMoveCount > 5) {
                    forceMove = true;
                }


                Enumeration e = ht.keys();
                while (e.hasMoreElements()) {
                    IUnit bu = (IUnit) ht.get(e.nextElement());
//                    //System.out.println("unit :" + bu);
//                    //System.out.println("me :" + this);
                    if (isMyUnit(bu)) {
                        bu.moveAway();
                    }
                }
                return false;
            } else {
                forceMove = false;
                forceMoveCount = 0;
//                //System.out.println("force move made false :" + this);
                return true;
            }
        }
    }

    boolean checkOtherUnitLocations(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return checkOtherUnitLocations(x1, y1)
                && checkOtherUnitLocations(x2, y2)
                && checkOtherUnitLocations(x3, y3)
                && checkOtherUnitLocations(x4, y4);
    }

    boolean checkOtherUnitLocations(int x, int y) {
        IBuilding bu = environment.getUm().getBuildingAtLocation(getX(), getY());
//        //System.out.println(bu);
        if (bu != null) {
            return false;
        } else {
            IUnit u = environment.getUm().getUnitAtLocation(getX(), getY());
            if (u != null && u != this) {
                u.moveAway();
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Check current possition is allowed
     *
     * @param x
     * @param y
     * @return x
     */

    public boolean isAllowedCell(int x, int y) {

        int bx = ((unitBase.getSizeX() / 4 > 10) ? unitBase.getSizeX() / 4 : 10);
        int by = ((unitBase.getSizeY() / 4 > 10) ? unitBase.getSizeY() / 4 : 10);

        int rx00 = getX() - bx + x;
        int ry00 = getY() - by + y;
        int rx10 = getX() + bx + x;
        int ry10 = getY() - by + y;
        int rx01 = getX() - bx + x;
        int ry01 = getY() + by + y;
        int rx11 = getX() + bx + x;
        int ry11 = getY() + by + y;

//        int rx00x = getX() - ((unitBase.getSizeX()/2>10)?unitBase.getSizeX():10) + x;
//        int ry00x = getY() - ((unitBase.getSizeY()/2>10)?unitBase.getSizeY():10) + y;
//        int rx11x = getX() + ((unitBase.getSizeX()/2>10)?unitBase.getSizeX():10) + x;
//        int ry11x = getY() + ((unitBase.getSizeY()/2>10)?unitBase.getSizeY():10) + y;

        try {
            String t = unitBase.getType();

            GroundCell g1 = environment.getCellType(rx00, ry00);
            GroundCell g2 = environment.getCellType(rx01, ry01);
            GroundCell g3 = environment.getCellType(rx10, ry10);
            GroundCell g4 = environment.getCellType(rx11, ry11);
            Point p1 = environment.getGroundPoint(rx00, ry00);
            Point p2 = environment.getGroundPoint(rx01, ry01);
            Point p3 = environment.getGroundPoint(rx10, ry10);
            Point p4 = environment.getGroundPoint(rx11, ry11);
            boolean b1 = JCageConfigurator.isCellValied(t, g1);
            boolean b2 = JCageConfigurator.isCellValied(t, g2);
            boolean b3 = JCageConfigurator.isCellValied(t, g3);
            boolean b4 = JCageConfigurator.isCellValied(t, g4);


            if (b1 && b2 && b3 && b4) {
                boolean isue = false;
                if (!(t.equals(JCageConfigurator.UNIT_AIR_A) || t.equals(JCageConfigurator.UNIT_AIR_B))) {
                    if (!isue && environment.getUm().isBuildingCellExists(p1) == null &&
                            environment.getUm().isBuildingCellExists(p2) == null &&
                            environment.getUm().isBuildingCellExists(p3) == null &&
                            environment.getUm().isBuildingCellExists(p4) == null) {
                        return true;
                    }
                    else{
                        return false;
                    }


                }
                return true;

            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Check given possition is allowed
     *
     * @param x
     * @param y
     * @param ox
     * @param oy
     * @return x
     */

    protected boolean isAllowedCell(int x, int y, int ox, int oy) {

        int bx = ((unitBase.getSizeX() / 4 > 10) ? unitBase.getSizeX() / 4 : 10);
        int by = ((unitBase.getSizeY() / 4 > 10) ? unitBase.getSizeY() / 4 : 10);

        int rx00 = ox - bx + x;
        int ry00 = oy - by + y;
        int rx10 = ox + bx + x;
        int ry10 = oy - by + y;
        int rx01 = ox - bx + x;
        int ry01 = oy + by + y;
        int rx11 = ox + bx + x;
        int ry11 = oy + by + y;

//        int rx00x = ox - ((unitBase.getSizeX()/2>10)?unitBase.getSizeX():20) + x;
//        int ry00x = oy - ((unitBase.getSizeY()/2>10)?unitBase.getSizeY():20) + y;
//        int rx11x = ox + ((unitBase.getSizeX()/2>10)?unitBase.getSizeX():20) + x;
//        int ry11x = oy + ((unitBase.getSizeY()/2>10)?unitBase.getSizeY():20) + y;

        try {
            String t = unitBase.getType();

            GroundCell g1 = environment.getCellType(rx00, ry00);
            GroundCell g2 = environment.getCellType(rx01, ry01);
            GroundCell g3 = environment.getCellType(rx10, ry10);
            GroundCell g4 = environment.getCellType(rx11, ry11);

            Point p1 = environment.getGroundPoint(rx00, ry00);
            Point p2 = environment.getGroundPoint(rx01, ry01);
            Point p3 = environment.getGroundPoint(rx10, ry10);
            Point p4 = environment.getGroundPoint(rx11, ry11);

            boolean b1 = JCageConfigurator.isCellValied(t, g1);
            boolean b2 = JCageConfigurator.isCellValied(t, g2);
            boolean b3 = JCageConfigurator.isCellValied(t, g3);
            boolean b4 = JCageConfigurator.isCellValied(t, g4);


            if (b1 && b2 && b3 && b4) {
//                if (checkOtherUnitLocations(rx00,ry00,rx01,ry01,rx10,ry10,rx11,ry11)){
//                    return true;
//                }
                boolean isue = false;

//                IUnit u1 = environment.getUm().isUnitCellExists(p1, getUnitId());
//                IUnit u2 = environment.getUm().isUnitCellExists(p2, getUnitId());
//                IUnit u3 = environment.getUm().isUnitCellExists(p3, getUnitId());
//                IUnit u4 = environment.getUm().isUnitCellExists(p4, getUnitId());
//
//
//                if (u1 == null && u2 == null && u3 == null && u4 == null) {
//                    forceMove=false;
//                    isue = false;
//                } else {
//                    if (u1 != null)
//                        u1.moveAway();
//                    if (u2 != null && !u2.equals(u1)) {
//                        u2.moveAway();
//                    }
//                    if (u3 != null && !u3.equals(u1) && !u3.equals(u2)) {
//                        u3.moveAway();
//                    }
//                    if (u4 != null && !u4.equals(u1) && !u4.equals(u2) && !u4.equals(u3)) {
//                        u4.moveAway();
//                    }
//                     return forceMove;
//                }

                if (!(t.equals(JCageConfigurator.UNIT_AIR_A) || t.equals(JCageConfigurator.UNIT_AIR_B))) {

                    if (!isue && environment.getUm().isBuildingCellExists(p1) == null &&
                            environment.getUm().isBuildingCellExists(p2) == null &&
                            environment.getUm().isBuildingCellExists(p3) == null &&
                            environment.getUm().isBuildingCellExists(p4) == null) {
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                return true;

            }

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return false;
    }


    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public int getCurrentDirection() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getSizeX() {
        return unitBase.getSizeX();
    }

    public int getSizeY() {
        return unitBase.getSizeY();
    }

    public Unit getUnitBase() {
        return unitBase;
    }

    public void setUnitBase(Unit unitBase) {
        this.unitBase = unitBase;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x > 0 && x < environment.getSizeX())
            this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y > 0 && y < environment.getSizeY())
            this.y = y;
    }

    public void registerMove(int x, int y) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getStrength() {
        return strength;
    }

    public void fly(int timerval) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getOwener() {
        return owener;
    }

    public void setOwener(String owener) {
        this.owener = owener;
    }

    protected Sprite getPic() {
        return pic;
    }

    public String toString() {
        return missionId + "*" + unitId + "|" + unitType + "|" + x + "|" + y + "|" + strength + "|" + owener + "|";
    }


    public void move(int timerVal) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void moveAway() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void openFire(int timerVal) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void fireClosest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void fire(IUnit unit) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void fire(IBuilding building) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void fire() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Unit getBase() {
        return unitBase;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Sprite getSprite() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean moveTimer(int x) {
        return (x % (unitBase.getSpeed()) == 0);
    }

    public boolean moveTimerFire(int x) {
        return (x % (unitBase.getSpeed() * 8) == 0);
    }

    public boolean thinkTimer(int x) {
        return (x % thinkRate == 0);
    }


    int findDirection(int x1, int y1) {
        int dx = x1 - x;
        int dy = y1 - y;
        int kx = Math.abs(dx);
        int ky = Math.abs(dy);
        if (dx <= 0 && dy <= 0) {
            if (kx >= ky) {
                return 4;
            } else {
                return 1;
            }
        } else if (dx <= 0 && dy > 0) {
            if (kx >= ky) {
                return 4;
            } else {
                return 3;
            }
        } else if (dx > 0 && dy <= 0) {
            if (kx >= ky) {
                return 2;
            } else {
                return 1;
            }
        } else {
            if (kx >= ky) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    public boolean isMyUnit(IUnit u) {
        if (u.getOwener().equals(getOwener())) {
            return true;
        }
        return false;
    }

    public boolean isMyBuilding(IBuilding u) {
        if (u.getOwener().equals(getOwener())) {
            return true;
        }
        return false;
    }

    public int getDistance(IUnit u) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void followMe(IUnit u) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getEngagedInFire() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void dontFollow(IUnit u) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void distroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void think(int timerVal) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void suspendFollow() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void followUnit(IUnit u) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void playMoveSound() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void playFireSound() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void playFlySound() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLastUnitFiredMe(IUnit lastUnitFiredMe) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public IUnit getLastUnitFiredMe() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    protected boolean isInBounds(){
        if(environment.getStartPointX()<getX() && environment.getStartPointX()+environment.getScreenSizeX()>getX()
                && environment.getStartPointY()<getY() && environment.getStartPointY()+environment.getScreenSizeY()>getY()){
            return true;
        }
        return false;
    }


}
