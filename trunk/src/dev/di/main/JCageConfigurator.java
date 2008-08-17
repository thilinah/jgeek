package dev.di.main;

import de.enough.polish.util.Locale;
import dev.di.data.dao.*;
import dev.di.engine.Game;
import dev.di.forms.ChildForm;
import dev.di.map.*;
import dev.di.map.buildings.BaseBuilding;
import dev.di.util.RecordStoreManager;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Oct 26, 2007
 * Time: 1:09:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class JCageConfigurator {

    public static boolean SOUND = true;

    public static int speed = 0;

    //unit types
    public static String UNIT_GROUND_A = "grda";
    public static String UNIT_GROUND_B = "grdb";
    public static String UNIT_GROUND_WATER_A = "grwa";
    public static String UNIT_GROUND_SHALLOWWATER_A = "grsa";
    public static String UNIT_GROUND_HUMAN_A = "hrma";
    public static String UNIT_GROUND_HUMAN_B = "hrmb";
    public static String UNIT_GROUND_HUMAN_C = "hrmc";
    public static String UNIT_AIR_A = "aira";
    public static String UNIT_AIR_B = "airb";

    public static String BUILDING_DEFEND = "def";
    public static String BUILDING_RESOURCE = "res";
    public static String BUILDING_UNIT = "uni";


    public static int resourceNeedMultiplicationAmountBuilding = 20;
    public static int resourceNeedMultiplicationAmountUnit = 3;
    public static int populationCalculationBuildingWeight = 5;
    public static int resourceProductionFactor = 24;
    public static int resourceProductionMinimum = 8;

    public static int RETRY_COUNT = 4;
    public static String SOCKET_CONNECTION_STRING = "socket://192.248.8.247:80";
    public static String SOCKET_CONNECTION_URL = "http://localhost:8088/fserver/config?m=";
    //    public static String SOCKET_CONNECTION_URL = "http://di.hostjava.net/config?m=";
    //    public static String SOCKET_CONNECTION_URL = "http://jcage.hostjava.net/fserver/config?m=";
    //    public static String SOCKET_CONNECTION_URL="http://192.248.8.247/fserver/config?m=";
    public static String SYSTEM_RECORDSTORE_NAME = "system";

    public int screenHeight = 0;
    public int screenWidth = 0;
    public static boolean SINHALA = false;

    public static int CONNECTED = 0;
    public static Hashtable scoreTable = new Hashtable();
    public static Hashtable resources = new Hashtable();
    public static Hashtable buildings = new Hashtable();
    public static Hashtable missins = new Hashtable();
    public static Hashtable planets = new Hashtable();
    public static Hashtable units = new Hashtable();
    public static Hashtable system = new Hashtable();
    public static Hashtable retUnits = new Hashtable();
    public static Hashtable sentUnits = new Hashtable();
    public static Hashtable unitTypeAllowedCells = new Hashtable();
    public static Hashtable maps = new Hashtable();
    public static GameProxy currentProxy = null;
    public static Building currentSelectedBuilding = null;
    public static Unit currentSelectedUnit = null;
    public static BaseBuilding currentSelectedBaseBuilding = null;
    public static String rulingPlanetToRemove = null;

    public static int rulingPlanetLimit = 3;


    public MainMid mainMidlet = null;

    public static boolean MESSAGE_PROCESSING = false;
    public static long MAX_PROCESS_TIME = 60000;
    public static int MAX_OTHER_MAPS = 4;


    //========
    private boolean loginSuccessfull = false;
    private boolean createMissionSuccessfull = false;

    //=========
    public static Timer gameTimer = null;
    public Game game = null;

    public static boolean buildingsUpdated = true;
    public static boolean gameStarted = false;

    ChildForm currentScreen = null;
    Display display = null;

    public String user = "";
    public String password = "";
    public String map = "";
    public int score = 0;

    //public int unitCreationDelay=1000;
    //public int buildingUpdateDelay=2000;

    public final int resourceRateTime = 20000;
    public final int messageRateTime = 1; //1 means 20000 (resourceRateTime)


    public Display getDisplay() {
        return display;
    }

    public boolean isCreateMissionSuccessfull() {
        return createMissionSuccessfull;
    }

    public void setCreateMissionSuccessfull(boolean createMissionSuccessfull) {
        this.createMissionSuccessfull = createMissionSuccessfull;
    }

    public boolean isLoginSuccessfull() {
        return loginSuccessfull;
    }

    public void setLoginSuccessfull(boolean loginSuccessfull) {
        //System.out.println("loginSuccessfull:" + loginSuccessfull);
        this.loginSuccessfull = loginSuccessfull;
    }

    public ChildForm getCurrentScreen() {
        return currentScreen;
    }

    public Displayable getCurrentDisplay() {
        return currentScreen.getDisplay();
    }

    public Displayable getCurrentParentDisplay() {
        return currentScreen.getParent().getDisplay();
    }

    public static Object getSelectedIndex(int i, Hashtable ht) {
        int j = 0;
        Enumeration e = ht.keys();
        while (e.hasMoreElements()) {
            if (j == i)
                return ht.get(e.nextElement());
            else
                e.nextElement();
            j++;
        }
        return null;
    }


    public static Building getBuildingById(String id) {
        Enumeration e = buildings.keys();
        while (e.hasMoreElements()) {
            Building b = (Building) buildings.get(e.nextElement());
            if (b.getId().equals(id)) {
                return b;
            }

        }
        return null;
    }


    public static Unit getUnitById(String id) {
        Enumeration e = units.keys();
        while (e.hasMoreElements()) {
            Unit b = (Unit) units.get(e.nextElement());
            if (b.getId().equals(id)) {
                return b;
            }

        }
        return null;
    }


    public static int getUnitTotal() {
        int k = 0;
        Enumeration e = units.keys();
        while (e.hasMoreElements()) {
            Unit b = (Unit) units.get(e.nextElement());
            k += b.getAmount();

        }
        return k;
    }

    public static Unit getUnitById(String id, Hashtable ht) {
        Enumeration e = ht.keys();
        while (e.hasMoreElements()) {
            Unit b = (Unit) ht.get(e.nextElement());
            if (b.getId().equals(id)) {
                return b;
            }

        }
        return null;
    }


    public static Mission getMissionById(String id) {
        Enumeration e = missins.keys();
        while (e.hasMoreElements()) {
            Mission b = (Mission) missins.get(e.nextElement());
            if (b.getName().equals(id)) {
                return b;
            }

        }
        return null;
    }


    public static PlanetDao getMapById(String id) {
        Enumeration e = planets.keys();
        while (e.hasMoreElements()) {
            PlanetDao b = (PlanetDao) planets.get(e.nextElement());
            if (b.getName().equals(id)) {
                return b;
            }

        }
        return null;
    }


    public static Resource getResourceById(String id) {
        Enumeration e = resources.keys();
        while (e.hasMoreElements()) {
            Resource b = (Resource) resources.get(e.nextElement());
            if (b.getId().equals(id)) {
                return b;
            }

        }
        return null;
    }

    public static int getCount(Hashtable ht) {
        int count = 0;
        Enumeration e = ht.keys();
        while (e.hasMoreElements()) {
            Object o = ht.get(e.nextElement());
            count++;

        }
        return count;
    }

    public static void addOrIncTable(String id, int amount, Hashtable ht) {
        Object k = ht.get(id);
        if (k == null) {
            ht.put(id, String.valueOf(amount));
        } else {
            int i = Integer.parseInt(String.valueOf(k));
            i = i + amount;
            ht.put(id, String.valueOf(i));
        }

    }

    public void display(ChildForm displayable) {
        currentScreen = displayable;
        display.setCurrent(displayable.getDisplay());
    }

    public void displayParent() {
        currentScreen = currentScreen.getParent();
        display.setCurrent(currentScreen.getDisplay());
    }

    public void displayParent(Alert a) {
        currentScreen = currentScreen.getParent();
        display.setCurrent(a, currentScreen.getDisplay());
    }

    public void displayParent2() {
        currentScreen = currentScreen.getParent().getParent();
        display.setCurrent(currentScreen.getDisplay());
    }

    public void displayParent3() {
        currentScreen = currentScreen.getParent().getParent().getParent();
        display.setCurrent(currentScreen.getDisplay());
    }


    public void displayAleart(String message) {
        Alert a = new Alert("J Cage", message, null, AlertType.INFO);
        a.setTimeout(Alert.FOREVER);
        display.setCurrent(a, currentScreen.getDisplay());
    }

    public void loadMetaData() {
        loadResources();
        loadUnits();
        loadBuildings();
        loadMaps();
        defineUnitTypeAllowedCells();
        startGameTimer();
        updateSpeed();
    }


    public void startNewGame(GameProxy gp) {
        currentProxy = gp;
        Enumeration en = buildings.keys();
        while (en.hasMoreElements()) {
            Object o = en.nextElement();
            Building b = (Building) buildings.get(o);
            gp.getBuildingLevels().put(b.getId(), "1");
            Enumeration en1 = gp.getPlayers().keys();
            while (en1.hasMoreElements()) {
                String key = (String) en1.nextElement();
                ((Player) gp.getPlayers().get(key)).getBuildingLevels().put(b.getId(), "1");
                System.out.println(o + " " + b.getId());

            }
            System.out.println(o + " " + b.getId());
        }

    }

    public void startGameTimer() {
        gameTimer = new Timer();
        game = new Game();
        gameTimer.schedule(game, 0, resourceRateTime);
    }

    public void stopGameTimer() {
        game.cancel();
        game = null;
        gameTimer = null;
    }

    private void loadResources() {
        Resource res = new Resource(Locale.get("r1"));
        resources.put(res.getId(), res);
        res = new Resource(Locale.get("r2"));
        resources.put(res.getId(), res);
        res = new Resource(Locale.get("r3"));
        resources.put(res.getId(), res);
        res = new Resource(Locale.get("r4"));
        resources.put(res.getId(), res);
    }

    private void loadBuildings() {
        Building bu = new Building(Locale.get("b1"));
        buildings.put(bu.getId(), bu);
        bu = new Building(Locale.get("b2"));
        buildings.put(bu.getId(), bu);
        bu = new Building(Locale.get("b3"));
        buildings.put(bu.getId(), bu);
        bu = new Building(Locale.get("b4"));
        buildings.put(bu.getId(), bu);
        bu = new Building(Locale.get("b5"));
        buildings.put(bu.getId(), bu);
        bu = new Building(Locale.get("b6"));
        buildings.put(bu.getId(), bu);
    }

    private void loadUnits() {
        Unit un = new Unit(Locale.get("u1"));
        units.put(un.getId(), un);
        un = new Unit(Locale.get("u2"));
        units.put(un.getId(), un);
        un = new Unit(Locale.get("u3"));
        units.put(un.getId(), un);
        un = new Unit(Locale.get("u4"));
        units.put(un.getId(), un);
        un = new Unit(Locale.get("u5"));
        units.put(un.getId(), un);
        un = new Unit(Locale.get("u6"));
        units.put(un.getId(), un);
        un = new Unit(Locale.get("u7"));
        units.put(un.getId(), un);
        un = new Unit(Locale.get("u8"));
        units.put(un.getId(), un);
        un = new Unit(Locale.get("u9"));
        units.put(un.getId(), un);
    }


    private void loadMaps() {
        String map = Locale.get("map.1");
        String[] kx = new String[]{map.substring(0, map.indexOf(":")), map.substring(map.indexOf(":") + 1)};
        maps.put(kx[0], kx[1]);
        System.out.println(kx[0]);
        System.out.println(kx[1]);
        map = Locale.get("map.2");
        kx = new String[]{map.substring(0, map.indexOf(":")), map.substring(map.indexOf(":") + 1)};
        maps.put(kx[0], kx[1]);
        map = Locale.get("map.3");
        kx = new String[]{map.substring(0, map.indexOf(":")), map.substring(map.indexOf(":") + 1)};
        maps.put(kx[0], kx[1]);

    }


    public static long getSystemtime() {
        String[] k = null;
        try {
            k = RecordStoreManager.getInstance().getRecordById(JCageConfigurator.SYSTEM_RECORDSTORE_NAME, "systime");
        } catch (Exception e) {

        }
        if (k != null) {
            return Long.parseLong(k[1]);
        }
        return -1;
    }

    public static long getRank() {
        String[] k = null;
        try {
            k = RecordStoreManager.getInstance().getRecordById(JCageConfigurator.SYSTEM_RECORDSTORE_NAME, "rank");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (k != null) {
            return Long.parseLong(k[1]);
        }
        return -1;
    }


    public static long getScore() {
        String[] k = null;
        try {
            k = RecordStoreManager.getInstance().getRecordById(JCageConfigurator.SYSTEM_RECORDSTORE_NAME, "score");
        } catch (Exception e) {

        }
        if (k != null) {
            return Long.parseLong(k[1]);
        }
        return -1;
    }


    public static String tableToString(Hashtable ht) {
        Enumeration en = ht.keys();
        String s = "";
        String k = null;
        String v = null;
        while (en.hasMoreElements()) {
            k = String.valueOf(en.nextElement()).trim();
            v = String.valueOf(ht.get(k)).trim();
            s += k + "," + v + "#";
        }
        return s;
    }


    public static void defineUnitTypeAllowedCells() {
        AllowedCell ac = new AllowedCell(5, 24, false); // ground units

        ac.addType("def");
        ac.addType("res");
        ac.addType("uni");
        ac.addType("grda");
        ac.addType("grdb");

        //basic grounds
        ac.addCell(new GroundCell(0, 0, 0));
        ac.addCell(new GroundCell(0, 1, 0));
        ac.addCell(new GroundCell(0, 2, 0));
        ac.addCell(new GroundCell(0, 3, 0));
        ac.addCell(new GroundCell(0, 5, 0));
        ac.addCell(new GroundCell(0, 6, 0));

        //grassless region
        ac.addCell(new GroundCell(0, 4, 4));
        ac.addCell(new GroundCell(0, 5, 4));
        ac.addCell(new GroundCell(0, 6, 4));
        ac.addCell(new GroundCell(0, 0, 5));
        ac.addCell(new GroundCell(0, 1, 5));
        ac.addCell(new GroundCell(0, 2, 5));
        ac.addCell(new GroundCell(0, 3, 5));
        ac.addCell(new GroundCell(0, 4, 5));
        ac.addCell(new GroundCell(0, 5, 5));
        ac.addCell(new GroundCell(0, 6, 5));
        ac.addCell(new GroundCell(0, 0, 6));
        ac.addCell(new GroundCell(0, 1, 6));
        //roads
        ac.addCell(new GroundCell(0, 5, 9));
        ac.addCell(new GroundCell(0, 6, 9));
        ac.addCell(new GroundCell(0, 0, 10));
        ac.addCell(new GroundCell(0, 1, 10));
        ac.addCell(new GroundCell(0, 2, 10));
        ac.addCell(new GroundCell(0, 3, 10));

        unitTypeAllowedCells.put(ac.getUnitType()[0], ac);


        AllowedCell ac1 = new AllowedCell(1, 50, false); // water and ground units

        ac1.addType("grwa");

        //basic grounds 6
        ac1.addCell(new GroundCell(0, 0, 0));
        ac1.addCell(new GroundCell(0, 1, 0));
        ac1.addCell(new GroundCell(0, 2, 0));
        ac1.addCell(new GroundCell(0, 3, 0));
        ac1.addCell(new GroundCell(0, 5, 0));
        ac1.addCell(new GroundCell(0, 6, 0));

        //grassless region  12
        ac1.addCell(new GroundCell(0, 4, 4));
        ac1.addCell(new GroundCell(0, 5, 4));
        ac1.addCell(new GroundCell(0, 6, 4));
        ac1.addCell(new GroundCell(0, 0, 5));
        ac1.addCell(new GroundCell(0, 1, 5));
        ac1.addCell(new GroundCell(0, 2, 5));
        ac1.addCell(new GroundCell(0, 3, 5));
        ac1.addCell(new GroundCell(0, 4, 5));
        ac1.addCell(new GroundCell(0, 5, 5));
        ac1.addCell(new GroundCell(0, 6, 5));
        ac1.addCell(new GroundCell(0, 0, 6));
        ac1.addCell(new GroundCell(0, 1, 6));
        //roads  6
        ac1.addCell(new GroundCell(0, 5, 9));
        ac1.addCell(new GroundCell(0, 6, 9));
        ac1.addCell(new GroundCell(0, 0, 10));
        ac1.addCell(new GroundCell(0, 1, 10));
        ac1.addCell(new GroundCell(0, 2, 10));
        ac1.addCell(new GroundCell(0, 3, 10));

        //water 13
        ac1.addCell(new GroundCell(0, 4, 0));
        ac1.addCell(new GroundCell(0, 2, 6));
        ac1.addCell(new GroundCell(0, 3, 6));
        ac1.addCell(new GroundCell(0, 4, 6));
        ac1.addCell(new GroundCell(0, 5, 6));
        ac1.addCell(new GroundCell(0, 6, 6));
        ac1.addCell(new GroundCell(0, 0, 7));
        ac1.addCell(new GroundCell(0, 1, 7));
        ac1.addCell(new GroundCell(0, 2, 7));
        ac1.addCell(new GroundCell(0, 3, 7));
        ac1.addCell(new GroundCell(0, 4, 7));
        ac1.addCell(new GroundCell(0, 5, 7));
        ac1.addCell(new GroundCell(0, 6, 7));

        //shallow water 13

        ac1.addCell(new GroundCell(0, 0, 8));
        ac1.addCell(new GroundCell(0, 1, 8));
        ac1.addCell(new GroundCell(0, 2, 8));
        ac1.addCell(new GroundCell(0, 3, 8));
        ac1.addCell(new GroundCell(0, 4, 8));
        ac1.addCell(new GroundCell(0, 5, 8));
        ac1.addCell(new GroundCell(0, 6, 8));
        ac1.addCell(new GroundCell(0, 0, 9));
        ac1.addCell(new GroundCell(0, 1, 9));
        ac1.addCell(new GroundCell(0, 2, 9));
        ac1.addCell(new GroundCell(0, 3, 9));
        ac1.addCell(new GroundCell(0, 4, 9));
        ac1.addCell(new GroundCell(0, 1, 13));

        unitTypeAllowedCells.put(ac1.getUnitType()[0], ac1);


        AllowedCell ac2 = new AllowedCell(1, 41, false); // shallow water and ground units

        ac2.addType("grsa");

        //basic grounds 6
        ac2.addCell(new GroundCell(0, 0, 0));
        ac2.addCell(new GroundCell(0, 1, 0));
        ac2.addCell(new GroundCell(0, 2, 0));
        ac2.addCell(new GroundCell(0, 3, 0));
        ac2.addCell(new GroundCell(0, 5, 0));
        ac2.addCell(new GroundCell(0, 6, 0));

        //grassless region  12
        ac2.addCell(new GroundCell(0, 4, 4));
        ac2.addCell(new GroundCell(0, 5, 4));
        ac2.addCell(new GroundCell(0, 6, 4));
        ac2.addCell(new GroundCell(0, 0, 5));
        ac2.addCell(new GroundCell(0, 1, 5));
        ac2.addCell(new GroundCell(0, 2, 5));
        ac2.addCell(new GroundCell(0, 3, 5));
        ac2.addCell(new GroundCell(0, 4, 5));
        ac2.addCell(new GroundCell(0, 5, 5));
        ac2.addCell(new GroundCell(0, 6, 5));
        ac2.addCell(new GroundCell(0, 0, 6));
        ac2.addCell(new GroundCell(0, 1, 6));
        //roads  6
        ac2.addCell(new GroundCell(0, 5, 9));
        ac2.addCell(new GroundCell(0, 6, 9));
        ac2.addCell(new GroundCell(0, 0, 10));
        ac2.addCell(new GroundCell(0, 1, 10));
        ac2.addCell(new GroundCell(0, 2, 10));
        ac2.addCell(new GroundCell(0, 3, 10));

        //shallow water 13

        ac2.addCell(new GroundCell(0, 0, 8));
        ac2.addCell(new GroundCell(0, 1, 8));
        ac2.addCell(new GroundCell(0, 2, 8));
        ac2.addCell(new GroundCell(0, 3, 8));
        ac2.addCell(new GroundCell(0, 4, 8));
        ac2.addCell(new GroundCell(0, 5, 8));
        ac2.addCell(new GroundCell(0, 6, 8));
        ac2.addCell(new GroundCell(0, 0, 9));
        ac2.addCell(new GroundCell(0, 1, 9));
        ac2.addCell(new GroundCell(0, 2, 9));
        ac2.addCell(new GroundCell(0, 3, 9));
        ac2.addCell(new GroundCell(0, 4, 9));
        ac2.addCell(new GroundCell(0, 1, 13));

        //mountains back region 4

        ac2.addCell(new GroundCell(1, 2, 1, 0, 0));
        ac2.addCell(new GroundCell(1, 3, 1, 0, 0));
        ac2.addCell(new GroundCell(1, 5, 1, 0, 0));
        ac2.addCell(new GroundCell(1, 6, 1, 0, 0));

        unitTypeAllowedCells.put(ac2.getUnitType()[0], ac2);


        AllowedCell ac3 = new AllowedCell(3, 28, false); // humans

        ac3.addType("hrma");
        ac3.addType("hrmb");
        ac3.addType("hrmc");

        //basic grounds 6
        ac3.addCell(new GroundCell(0, 0, 0));
        ac3.addCell(new GroundCell(0, 1, 0));
        ac3.addCell(new GroundCell(0, 2, 0));
        ac3.addCell(new GroundCell(0, 3, 0));
        ac3.addCell(new GroundCell(0, 5, 0));
        ac3.addCell(new GroundCell(0, 6, 0));

        //grassless region  12
        ac3.addCell(new GroundCell(0, 4, 4));
        ac3.addCell(new GroundCell(0, 5, 4));
        ac3.addCell(new GroundCell(0, 6, 4));
        ac3.addCell(new GroundCell(0, 0, 5));
        ac3.addCell(new GroundCell(0, 1, 5));
        ac3.addCell(new GroundCell(0, 2, 5));
        ac3.addCell(new GroundCell(0, 3, 5));
        ac3.addCell(new GroundCell(0, 4, 5));
        ac3.addCell(new GroundCell(0, 5, 5));
        ac3.addCell(new GroundCell(0, 6, 5));
        ac3.addCell(new GroundCell(0, 0, 6));
        ac3.addCell(new GroundCell(0, 1, 6));
        //roads  6
        ac3.addCell(new GroundCell(0, 5, 9));
        ac3.addCell(new GroundCell(0, 6, 9));
        ac3.addCell(new GroundCell(0, 0, 10));
        ac3.addCell(new GroundCell(0, 1, 10));
        ac3.addCell(new GroundCell(0, 2, 10));
        ac3.addCell(new GroundCell(0, 3, 10));

        //mountains back region

        ac3.addCell(new GroundCell(1, 2, 1, 0, 0));
        ac3.addCell(new GroundCell(1, 3, 1, 0, 0));
        ac3.addCell(new GroundCell(1, 5, 1, 0, 0));
        ac3.addCell(new GroundCell(1, 6, 1, 0, 0));


        unitTypeAllowedCells.put(ac3.getUnitType()[0], ac3);


        AllowedCell ac4 = new AllowedCell(2, 0, true); // air units

        ac4.addType("aira");
        ac4.addType("airb");

        unitTypeAllowedCells.put(ac4.getUnitType()[0], ac4);
    }

    public static boolean isCellValied(String type, GroundCell g) {
        //System.out.println(type+" "+g);
        Enumeration en = unitTypeAllowedCells.keys();
        while (en.hasMoreElements()) {
            AllowedCell ac = (AllowedCell) unitTypeAllowedCells.get(en.nextElement());
            if (ac.containsType(type)) {
                if (ac.containsCell(g)) {
                    return !ac.isNegative(); //false
                } else {
                    return ac.isNegative();
                }
            }
        }
        return false;
    }


    public static void updateSpeed() {
        try {
            String[] sk = RecordStoreManager.getInstance().getRecordById(JCageConfigurator.SYSTEM_RECORDSTORE_NAME, "speed");
            PlanetMap.refreshPeriod = sk[1].trim().length() * 20;
            System.out.println("PlanetMap.refreshPeriod " + PlanetMap.refreshPeriod);

        } catch (Exception e) {
            PlanetMap.refreshPeriod = 80;
        }
    }

    public static void updateSound() {
        try {
            String[] sk = RecordStoreManager.getInstance().getRecordById(JCageConfigurator.SYSTEM_RECORDSTORE_NAME, "sound");
            if (sk[1].equals("0")) {
                JCageConfigurator.SOUND = false;
            } else {
                JCageConfigurator.SOUND = true;
            }
            System.out.println("PlanetMap.refreshPeriod " + PlanetMap.refreshPeriod);

        } catch (Exception e) {
            JCageConfigurator.SOUND = true;

        }
    }
}