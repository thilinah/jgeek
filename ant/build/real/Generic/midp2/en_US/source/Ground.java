// package dev.di.map;

//import dev.di.main.MainMid;
//import dev.di.main.JCageConfigurator;
//import dev.di.map.units.DynamicUnit;
//import dev.di.map.units.BaseUnitService;
//import dev.di.map.units.BaseUnit;
//import dev.di.map.buildings.BaseBuildingService;
//import dev.di.map.buildings.BaseBuilding;
//import dev.di.data.dao.Unit;
//import dev.di.data.dao.PlanetDao;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 20, 2008
 * Time: 12:24:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ground {

    public static Random rand = new Random();
    static String saveGameName="";
    public static Hashtable cellCash;

    public static int width = 0;
    public static int height = 0;

    public static int cellWidth = 24;
    public static int cellHeight = 12;

    static String testEn = "30|30|0,4,0|0,4,0|0,4,0|0,4,0|0,1,7|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,7|0,1,7|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,7|0,1,7|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,7|0,5,7|0,4,0|0,4,0|0,4,0|0,4,0|0,4,7|0,1,7|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,7|0,5,7|0,4,0|0,4,0|0,4,0|0,4,0|0,4,7|0,1,7|0,0,0|0,0,0|0,2,0|0,0,0|0,1,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,7|0,5,7|0,4,0|0,4,0|0,4,0|0,4,0|0,4,7|0,1,7|0,0,0|0,0,0|0,2,0|1,5,1,1,0|1,0,3,2,0|1,0,2,0,0|1,2,2,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,4,6|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,2,6|0,1,7|1,5,1,0,0|0,2,0|0,2,0|1,3,1,2,0|1,4,1,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,0|0,2,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,4,6|0,5,7|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,7|0,1,7|0,2,0|0,0,0|0,0,0|1,4,1,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,4,6|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,5,6|0,2,0|0,0,0|0,0,0|1,4,1,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,6,6|0,6,7|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,5,6|0,2,0|0,2,0|0,2,0|1,4,1,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,6,6|0,6,7|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,3,7|0,2,7|0,0,0|0,2,0|0,0,0|1,4,1,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,6,6|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,3,7|0,2,7|0,1,0|1,6,1,1,0|1,1,1,0,0|1,1,1,1,0|1,1,2,1,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,1,5|0,3,5|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,6,6|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,2,7|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,1,0|0,0,0|0,0,0|0,0,0|0,0,0|0,1,5|0,4,4|0,3,0|0,0,5|0,4,4|0,0,0|0,0,0|0,0,0|0,0,0|0,6,6|0,4,0|0,4,0|0,4,0|0,4,0|0,4,0|0,3,7|0,3,6|0,3,6|0,2,7|0,0,0|0,2,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,1,5|0,1,6|0,3,0|0,3,0|0,3,0|0,4,5|0,0,0|0,0,0|0,0,0|0,0,0|0,6,7|0,4,0|0,4,0|0,4,0|0,4,0|0,3,7|0,2,7|0,2,0|0,0,0|0,2,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,1,5|0,1,6|0,1,6|0,3,0|0,5,5|0,4,5|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,4,0|0,4,0|0,4,0|0,4,0|0,3,7|0,2,7|0,2,0|0,0,0|0,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,5|0,0,6|0,3,0|0,3,0|0,5,5|0,4,5|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,4,0|0,3,7|0,3,6|0,3,6|0,2,7|0,2,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,5|0,3,0|0,3,0|0,4,5|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,3,7|0,2,7|0,0,0|0,0,0|0,2,0|0,2,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,5|0,4,5|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,7|0,0,0|0,2,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,2,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|0,0,0|";

    public static Image getGroundCell(GroundCell c) {
        Image img = getCachedGroundCell(c);
        if (img == null) {
            //System.out.println("Cached cells : " + cellCash.size());
            if (c.alpha == 0) {
                img = cropImage(MainMid.getImage("/000.png"), 24, 12, getFrame(c.x, c.y));

            } else {
                img = cropAlphaImage(MainMid.getImage("/000.png"), 24, 12, getFrame(c.x, c.y)
                        , getFrame(c.ox, c.oy));

            }
            cellCash.put(c.getUniqueKey(), img);
        }
        return img;
    }


    public static Image getCachedGroundCell(GroundCell c) {
        if (cellCash == null) cellCash = new Hashtable();
        return (Image) cellCash.get(c.getUniqueKey());
    }


    static int getFrame(int x, int y) {
        return y * 7 + x;
    }


    private static GroundCell[][] getGroundFromMap(String map) {
        String k[] = getShape(map);
        //System.out.println("5333");
        ////System.out.println(map);
        width = Integer.parseInt(k[0]);
        height = Integer.parseInt(k[1]);
        int u = 2;
        int p=-1;
        GroundCell[] gw=null;
        GroundCell [][] grd = new GroundCell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(p==-1){
                    gw=getCell(k[u]);
                    p=0;
                }
                grd[i][j] = gw[p];
                p++;
                if(p==gw.length){
                    p=-1;
                    u++;
                }
            }
        }
        //System.out.println("222222222222");
        return grd;
    }

    public static Environment getEnvironment(String map,String saveName) {
        saveGameName=saveName;
        Environment en = new Environment();
        GroundCell[][] gca = getGroundFromMap(map.replace('*', '|'));
        en.setGround(gca);
        en.setCellSize(24, 12);
        en.setCurser(0, 0);
        en.setSize(24 * width, 12 * height);
        en.setStartPoint(0, 0);
        en.setCurser(new Sprite(MainMid.getImage("/005.png"), 20, 20));
        CurserManager.regulerCurser = en.getCurser();


        addUnits(en, saveName);
        addBuildings(en, saveName);

        return en;
    }


    public static Environment getEnvironmentForUnitCreation(String map,String saveName) {
        saveGameName=saveName;
        Environment en = new Environment();
        GroundCell[][] gca = getGroundFromMap(map.replace('*', '|'));
        en.setGround(gca);
        en.setCellSize(24, 12);
        en.setCurser(0, 0);
        en.setSize(24 * width, 12 * height);
        en.setStartPoint(0, 0);
        en.setCurser(new Sprite(MainMid.getImage("/005.png"), 20, 20));
        CurserManager.regulerCurser = en.getCurser();
        UnitManager um = new UnitManager(en, EnvirnmentRenderer.screenYK, EnvirnmentRenderer.screenXK);
        addUnits(en, saveName);
        addBuildings(en, saveName);

        return en;
    }

    private static void addBuildings(Environment en,String saveGameName) {
        try {
            Hashtable ht = BaseBuildingService.getInstance().getAll();
            Enumeration e = ht.keys();
            //System.out.println("11111");

            while (e.hasMoreElements()) {
                String key=String.valueOf(e.nextElement());
                System.out.println("Loading saved building :"+key);
                BaseBuilding bu = (BaseBuilding) ht.get(key);
                bu.setEnvironmentAndPicture(en); //essential
                System.out.println("building mid "+bu.getMissionId());
                if (bu.getMissionId().equals(saveGameName)) {
                    en.buildings.put(bu.getBuildingId(), bu);
                    ////System.out.println("Building added");
                }

            }
        } catch (Exception
                e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    private static void addUnits(Environment en, String saveGameName) {
        try {
            Hashtable ht = BaseUnitService.getInstance().getAll();
            Enumeration e = ht.keys();

            while (e.hasMoreElements()) {
                BaseUnit bu = (BaseUnit) ht.get(e.nextElement());
                DynamicUnit du = new DynamicUnit(en, bu.getMissionId(), bu.getBase(), bu.getOwener());
                du.setX(bu.getX());
                du.setY(bu.getY());
                du.setUnitType(bu.getUnitType());
                du.setUnitId(bu.getUnitId());
                du.setStrength(bu.getStrength());
                du.setEnvironmentAndPicture(en); //essential
                if (bu.getMissionId().equals(saveGameName)) {
//                    synchronized (Environment.unitHtLock) {
                    en.units.put(du.getUnitId(), du);
//                    }
                    //System.out.println("unit added");
                }

            }
        } catch (Exception
                e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public static void addNewUnits(Environment en) {
        try {
            Hashtable ht = BaseUnitService.getInstance().getAll();
            Enumeration e = ht.keys();

            while (e.hasMoreElements()) {
                BaseUnit bu = (BaseUnit) ht.get(e.nextElement());
                DynamicUnit du = new DynamicUnit(en, bu.getMissionId(), bu.getBase(), bu.getOwener());
                du.setX(bu.getX());
                du.setY(bu.getY());
                du.setUnitType(bu.getUnitType());
                du.setUnitId(bu.getUnitId());
                du.setStrength(bu.getStrength());
                du.setEnvironmentAndPicture(en); //essential
                if (bu.getMissionId().equals(saveGameName)) {
//                    synchronized (Environment.unitHtLock) {
                    if (!en.units.containsKey(bu.getUnitId())) {
                        en.units.put(du.getUnitId(), du);
                        //System.out.println("new unit added ###########");
                    }
//                    }

                }

            }
        } catch (Exception
                e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public static Environment getTestEnvironment() {
//        Environment e = new Environment();
//        GroundCell[][] gca = getGroundFromMap(testEn);
//        e.setGround(gca);
//        e.setCellSize(24, 12);
//        e.setCurser(0, 0);
//        e.setSize(24 * width, 12 * height);
//        e.setStartPoint(0, 0);
//        e.setCurser(new Sprite(MainMid.getImage("/005.png"), 20, 20));
//
//        Unit u = new Unit("Light Fighter", "500", "dut", 100, new Hashtable(), "ffff", 70, 4, 25, 34, 26);
//        Unit u2 = new Unit("Light Fighter", "500", "dut", 100, new Hashtable(), "ffff", 70, 4, 25, 34, 26);
//        Unit u3 = new Unit("Light Fighter", "500", "dut", 100, new Hashtable(), "ffff", 70, 4, 25, 34, 26);
//        Unit u4 = new Unit("Light Fighter", "500", "dut", 100, new Hashtable(), "ffff", 70, 4, 25, 34, 26);
//        Unit u1 = new Unit("Battleship", "400", "dut", 100, new Hashtable(), "ffff", 70, 4, 25, 14, 16);
//
//        //DynamicUnit d1 = new DynamicUnit("m1", JCageConfigurator.getUnitById("Light Fighter"));
//        DynamicUnit d1 = new DynamicUnit(e, "m1", u, JCageConfigurator.mymap.getName());
//        d1.setX(30);
//        d1.setY(90);
//
//        //DynamicUnit d2 = new DynamicUnit("m1", JCageConfigurator.getUnitById("Battleship"));
//        DynamicUnit d2 = new DynamicUnit(e, "m1", u1, JCageConfigurator.mymap.getName());
//        d2.setX(120);
//        d2.setY(200);
//
//        DynamicUnit d3 = new DynamicUnit(e, "m1", u2, JCageConfigurator.mymap.getName());
//        d3.setX(60);
//        d3.setY(200);
//
//        DynamicUnit d4 = new DynamicUnit(e, "m1", u3, JCageConfigurator.mymap.getName());
//        d4.setX(120);
//        d4.setY(20);
//
//        DynamicUnit d5 = new DynamicUnit(e, "m1", u4, JCageConfigurator.mymap.getName());
//        d5.setX(320);
//        d5.setY(100);
//
//        e.units.put(d1.getUnitId(), d1);
//        e.units.put(d2.getUnitId(), d2);
//        e.units.put(d3.getUnitId(), d3);
//        e.units.put(d4.getUnitId(), d4);
//        e.units.put(d5.getUnitId(), d5);
        return null;
    }


    public static String[] getShape(String s) {
        String[] k = new String[60000];
        int i = 0;
        while (s.indexOf("|") > -1) {
            k[i] = s.substring(0, s.indexOf("|"));
            s = s.substring(s.indexOf("|") + 1);
            i = i + 1;
        }
        String[] x = new String[i];

        for (int j = 0; j < i; j++) {
            x[j] = k[j];
        }
        return x;
    }

    public static GroundCell[] getCell(String s) {
        s += ",";
        String[] k = new String[6];
        int i = 0;
        while (s.indexOf(",") > -1) {
            k[i] = s.substring(0, s.indexOf(","));
            s = s.substring(s.indexOf(",") + 1);
            i = i + 1;
        }


        GroundCell[] gx = new GroundCell[Integer.parseInt(k[0])];
        for (int w = 0; w < gx.length; w++) {
            GroundCell g = new GroundCell();
            g.alpha = Integer.parseInt(k[1]);
            g.x = Integer.parseInt(k[2]);
            g.y = Integer.parseInt(k[3]);
            if (g.alpha == 1) {
                g.ox = Integer.parseInt(k[4]);
                g.oy = Integer.parseInt(k[5]);

            }
            gx[w]=g;
        }
        return gx;
    }

    public static Image cropImage(Image src, int newX, int newY, int frame) {
        Sprite s = new Sprite(src, newX, newY);
        s.setFrame(frame);
        Image tmp = Image.createImage(newX, newY);
        Graphics g = tmp.getGraphics();
        s.paint(g);
        return tmp;
    }

    private static Image cropAlphaImage(Image src, int newX, int newY, int frame, int frameSrc) {
        Sprite s = new Sprite(src, newX, newY);
        s.setFrame(frameSrc);
        Image tmp = Image.createImage(newX, newY);
        Graphics g = tmp.getGraphics();
        s.paint(g);
        s.setFrame(frame);
        s.paint(g);
        return tmp;
    }


}
