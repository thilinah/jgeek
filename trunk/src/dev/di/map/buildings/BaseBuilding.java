package dev.di.map.buildings;

import dev.di.data.dao.AbstractDao;
import dev.di.data.dao.Building;
import dev.di.main.JCageConfigurator;
import dev.di.main.MainMid;
import dev.di.map.*;
import dev.di.map.units.IUnit;
import dev.di.util.ImageUtility;

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
public class BaseBuilding extends AbstractDao implements IBuilding {
    protected Building buildingBase = null;
    private String buildingId = null;
    private String buildingType = null;
    private int x = 0;
    private int y = 0;
    private Sprite pic = null;
    private String missionId = null;
    private int strength = 0;
    private int status = 0;
    Hashtable resourceMatrix = null;
    private String owener = null;

    Environment environment = null;


    public BaseBuilding(Environment environment, String mission, Building base, String owener) {
        this.environment = environment;
        this.buildingBase = base;
        this.missionId = mission;
        Date d = new Date();
        this.buildingId = String.valueOf(d.getTime());
        this.buildingType = base.getId();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.strength = Integer.parseInt(base.getStrength());


        Enumeration en = buildingBase.getResourceMatrix().keys();
        String s = "";
        String k = null;
        resourceMatrix = new Hashtable();
        while (en.hasMoreElements()) {
            k = String.valueOf(en.nextElement()).trim();
            this.resourceMatrix.put(k, buildingBase.getResourceMatrix().get(k));
        }
        this.owener = owener;
        if (owener.equals(MainMid.getClientConfigurator().user)) {
            pic = new Sprite(MainMid.getImage("/" + base.getId() + "_a.png"));
        } else {
            pic = new Sprite(ImageUtility.replaceColourBlue(MainMid.getImage("/" + base.getId() + "_a.png")));
        }
    }

    public BaseBuilding(String fullStr) {
        numOfAttribs = 8;
        String[] s = getAttributes(fullStr);
        missionId = s[0].substring(0, s[0].indexOf("*"));
        buildingId = s[0].substring(s[0].indexOf("*") + 1);
        buildingType = s[1];
        buildingBase = JCageConfigurator.getBuildingById(buildingType);
        x = Integer.parseInt(s[2]);
        y = Integer.parseInt(s[3]);
        strength = Integer.parseInt(s[4]);
        status = Integer.parseInt(s[5]);
        resourceMatrix = getTable(s[6]);
        owener = s[7];


    }

    public void setEnvironmentAndPicture(Environment en) {
        environment = en;
        if (owener.equals(MainMid.getClientConfigurator().user)) {
            pic = new Sprite(MainMid.getImage("/" + buildingType + "_a.png"));
        } else {
            pic = new Sprite(ImageUtility.replaceColourBlue(MainMid.getImage("/" + buildingType + "_a.png")));
        }
    }

    public Hashtable getResourceMatrix() {
        return resourceMatrix;
    }

    public void setResourceMatrix(Hashtable resourceMatrix) {
        this.resourceMatrix = resourceMatrix;
    }


    public String getOwener() {
        return owener;
    }

    public void setOwener(String owener) {
        this.owener = owener;
    }

    public int getDistance(IBuilding u) {
        int x = getX() - u.getX();
        int y = getY() - u.getY();
        return x * x + y * y;
    }

    public boolean isAllowedCell(int x, int y) {
        int rx00 = getX() - getSizeX() + x;
        int ry00 = getY() - getSizeY() + y;
        int rx10 = getX() + getSizeX() + x;
        int ry10 = getY() - getSizeY() + y;
        int rx01 = getX() - getSizeX() + x;
        int ry01 = getY() + getSizeY() + y;
        int rx11 = getX() + getSizeX() + x;
        int ry11 = getY() + getSizeY() + y;

        try {
            String t = buildingBase.getResourceMatrix().isEmpty() ? "uni" : "res";

            GroundCell g1 = environment.getCellType(rx00, ry00);
            GroundCell g2 = environment.getCellType(rx01, ry01);
            GroundCell g3 = environment.getCellType(rx10, ry10);
            GroundCell g4 = environment.getCellType(rx11, ry11);

            Point p1 = environment.getGroundPoint(rx00, ry00);
            Point p2 = environment.getGroundPoint(rx01, ry01);
            Point p3 = environment.getGroundPoint(rx10, ry10);
            Point p4 = environment.getGroundPoint(rx11, ry11);
            Point p5 = environment.getGroundPoint(getX(), getY());

//            if (getBuildingType().equals(JCageConfigurator.BUILDING_UNIT)) {
//                p2.setX(p2.getX() + 2);
//                p4.setX(p4.getX() + 2);
//            }


            boolean b1 = JCageConfigurator.isCellValied(t, g1);
            boolean b2 = JCageConfigurator.isCellValied(t, g2);
            boolean b3 = JCageConfigurator.isCellValied(t, g3);
            boolean b4 = JCageConfigurator.isCellValied(t, g4);


            if (b1 && b2 && b3 && b4) {
                boolean isue = false;
                System.out.println("1");
                if (!isue && environment.getUm().isBuildingCellExists(p1) == null &&
                        environment.getUm().isBuildingCellExists(p2) == null &&
                        environment.getUm().isBuildingCellExists(p3) == null &&
                        environment.getUm().isBuildingCellExists(p4) == null &&
                        environment.getUm().isBuildingCellExists(p5) == null) {
                    System.out.println("2");
                    return true;
                }
                System.out.println("3");

            }

//            Hashtable ht=environment.getUm().getAllBuildingsInRange(x-Ground.width*GroundCell.W*2 )


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return false;
    }


    public boolean isAllowedCell(int x, int y, int ox, int oy) {
        int rx00 = ox - getSizeX() / 4 + x;
        int ry00 = oy - getSizeY() / 4 + y;
        int rx10 = ox + getSizeX() / 4 + x;
        int ry10 = oy - getSizeY() / 4 + y;
        int rx01 = ox - getSizeX() / 4 + x;
        int ry01 = oy + getSizeY() / 4 + y;
        int rx11 = ox + getSizeX() / 4 + x;
        int ry11 = oy + getSizeY() / 4 + y;


        try {
            String t = buildingBase.getResourceMatrix().isEmpty() ? "uni" : "res";

            GroundCell g1 = environment.getCellType(rx00, ry00);
            GroundCell g2 = environment.getCellType(rx01, ry01);
            GroundCell g3 = environment.getCellType(rx10, ry10);
            GroundCell g4 = environment.getCellType(rx11, ry11);

            Point p1 = environment.getGroundPoint(rx00, ry00);
            Point p2 = environment.getGroundPoint(rx01, ry01);
            Point p3 = environment.getGroundPoint(rx10, ry10);
            Point p4 = environment.getGroundPoint(rx11, ry11);
            Point p5 = environment.getGroundPoint(ox, oy);

//            if (getBuildingType().equals(JCageConfigurator.BUILDING_UNIT)) {
//                p2.setX(p2.getX() + 2);
//                p4.setX(p4.getX() + 2);
//            }


            boolean b1 = JCageConfigurator.isCellValied(t, g1);
            boolean b2 = JCageConfigurator.isCellValied(t, g2);
            boolean b3 = JCageConfigurator.isCellValied(t, g3);
            boolean b4 = JCageConfigurator.isCellValied(t, g4);


            if (b1 && b2 && b3 && b4) {
                boolean isue = false;
                if (!isue && environment.getUm().isBuildingCellExists(p1) == null &&
                        environment.getUm().isBuildingCellExists(p2) == null &&
                        environment.getUm().isBuildingCellExists(p3) == null &&
                        environment.getUm().isBuildingCellExists(p4) == null &&
                        environment.getUm().isBuildingCellExists(p5) == null) {
                    return true;
                }

            }

//            Hashtable ht=environment.getUm().getAllBuildingsInRange(x-Ground.width*GroundCell.W*2 )


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return false;
    }


    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    public int getSizeX() {
        return pic.getWidth();
    }

    public int getSizeY() {
        return pic.getHeight();
    }

    public Building getBuildingBase() {
        return buildingBase;
    }

    public void setBuildingBase(Building buildingBase) {
        this.buildingBase = buildingBase;
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


    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    protected Sprite getPic() {
        return pic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toString() {
        return missionId + "*" + buildingId + "|" + buildingType + "|" + x + "|" + y + "|" + strength + "|" + status + "|" + tableToString(resourceMatrix) + "|"
                + owener + "|";
    }


    public void openFire(int timerVal) {
        if (!moveTimerFire(timerVal)) {
            return;
        }
        fire();
    }

    public void fireClosest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void fire(IUnit unit) {

        MovingAnimation ma1 = new BigFire(environment, getX(), getY(), unit.getX(), unit.getY());
//        Animation a= new SmallExplosion(unit.getX(),unit.getY(),environment);
//        environment.addExplosion(a);
        environment.addFire(ma1);
    }

    public void fire(IBuilding building) {

        MovingAnimation ma1 = new BigFire(environment, getX(), getY(), building.getX(), building.getY());
        environment.addFire(ma1);
    }

    public void fire() {
        UnitManager um = environment.getUm();
        int k = 150;//Integer.parseInt(buildingBase.getTotalStrength());
        IUnit u1 = um.getFirstEnemyUnitInRange(getX() - k, getY() - k, getX() + k, getY() + k, this);
        ////System.out.println("enamy " + u1);

        if (u1 != null && !isStillCreating()) {
            fire(u1);
        }
    }


    public Building getBase() {
        return buildingBase;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Sprite getSprite() {
        return pic;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public void updateResourceMatrix() {
        if (buildingBase.getType().equals(JCageConfigurator.BUILDING_RESOURCE)) {
            //System.out.println("........ updateResourceMatrix()");
            Enumeration en = buildingBase.getResourceMatrix().keys();
            String s = "";
            String k = null;
            int v = 0;
            while (en.hasMoreElements()) {
                k = String.valueOf(en.nextElement()).trim();
                v = Integer.parseInt(String.valueOf(buildingBase.getResourceMatrix().get(k)).trim())*JCageConfigurator.resourceProductionFactor* Integer.parseInt((String)JCageConfigurator.currentProxy.getBuildingLevels().get(buildingBase.getId()));
//                //System.out.println("Key " + k + " " + v);
//                int u = JCageConfigurator.resourceProductionMinimum * Integer.parseInt(buildingBase.getLevel()) * getPlanetAdvantage();
//                if (k.equals("Crystal")) {
//                    int a1 = environment.numberOfMountainCellsExistsInRange(x, y);
//
//                    int a2 = environment.numberOfShallowWaterCellsExistsInRange(x, y);
//                    //System.out.println(a1);
//                    //System.out.println(a2);
//                    int a3 = (a1 > a2) ? a2 : a1;
//                    u += a3*JCageConfigurator.resourceProductionFactor;
//                } else if (k.equals("Metal")) {
//                    int a1 = environment.numberOfMountainCellsExistsInRange(x, y);
//                    int a2 = environment.numberOfGressLessCellsExistsInRange(x, y);
//                    //System.out.println(a1);
//                    //System.out.println(a2);
//                    int a3 = (a1 > a2) ? a2 : a1;
//                    u += a3*JCageConfigurator.resourceProductionFactor;
//                }
//                u = (u > v) ? v : u;
//                //System.out.println("Resouce level " + k + " :" + u);
                resourceMatrix.put(k, String.valueOf(v));
            }
            //System.out.println("........ updateResourceMatrix() end");
        }
    }

//    private int getPlanetAdvantage(){
//        int f=Integer.parseInt(environment.planet.getSize());
//        if(f<8000){
//            return 4;
//        }
//        else if(f<10000){
//            return 3;
//        }
//        else if(f<13000){
//            return 2;
//        }
//        return 1;
//    }



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

    public boolean moveTimerFire(int x) {
        return (x % 40 == 0);
    }

    public boolean updateBuildingStatus(int x) {
        return (x % 100 == 0);
    }

    public boolean isStillCreating() {
        return status < Integer.parseInt(buildingBase.getTotalStrength());
    }

    public void updateStatus(int timer) {
        if (isStillCreating()) {
            if (!updateBuildingStatus(timer)) {
                return;
            }

            status += 60;
        }
    }

    public void distroy() {
        Hashtable ht = environment.getUm().getAllUnitsInRange(0, 0,
                GroundCell.W * Ground.width, GroundCell.H * Ground.height);
         IUnit u=environment.getUm().getRandomEnemyUnitInRange(ht);
    }

}
