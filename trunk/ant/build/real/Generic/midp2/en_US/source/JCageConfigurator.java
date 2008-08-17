// package dev.di.main;

//import de.enough.polish.util.Locale;
//import dev.di.data.dao.*;
//import dev.di.engine.Game;
//import dev.di.forms.ChildForm;
//import dev.di.map.*;
//import dev.di.map.buildings.BaseBuilding;
//import dev.di.util.RecordStoreManager;

//import javax.microedition.lcdui.Alert;
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
        Alert.setCurrent( display, a, currentScreen.getDisplay() );
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
        Alert.setCurrent( display, a, currentScreen.getDisplay() );
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
        Resource res = new Resource("Metal|10000|0|m|");
        resources.put(res.getId(), res);
        res = new Resource("Crystal|10000|0|c|");
        resources.put(res.getId(), res);
        res = new Resource("Energy|10000|0|e|");
        resources.put(res.getId(), res);
        res = new Resource("Dark Matter|10000|0|d|");
        resources.put(res.getId(), res);
    }

    private void loadBuildings() {
        Building bu = new Building("Factory|800|800|Bomber,3#Chopper,2#War Tank,1#War Bike,2#Hover Craft,3#Cannon,2#|1|Metal,4#Crystal,4#Energy,8#|Crystal,1500#|3|1|Factory builds machines like war tanks and supply truck, War Bike, Cannon, Hover Craft, and Chopper. Different amount of Crystal or metal is needed for upgrade to the next level of factory.|0|0|uni|");
        buildings.put(bu.getId(), bu);
        bu = new Building("Refinery|400|400||1|Metal,5#Crystal,4#|Metal,1000#|3|3|Refinery is a resource building capable of boosting the gathering rates of crystal and metal. Make sure to build metal Refineries in grassless regions near mountains and crystal Refineries near shallow waters near mountains to make gathering rates of these resources higher. To upgrade to the next level it needs certain amount of crystal and metal. Once updated to the next level the production rate of crystal and metal increases|0|0|res|");
        buildings.put(bu.getId(), bu);
        bu = new Building("Barrack|600|600|Sniper,2#Rocket Launcher,1#Commando,3#|1|Metal,3#Crystal,4#Energy,4#|Dark Matter,800#|3|3|Barrack trains armies. This is capable of building units such as Rocket Launcher, Commando and Sniper according to the level of the barrack. Crystal or metal is needed for upgrade a barrack|0|0|uni|");
        buildings.put(bu.getId(), bu);
        bu = new Building("Outpost|400|400||1|Metal,2#Crystal,2#Energy,5#|Metal,1000#|3|2|Outpost is useful when your planet is under the power of another emperor?s mission as a defencive unit. Having more outposts increase your population limit|0|0|def|");
        buildings.put(bu.getId(), bu);
        bu = new Building("Fort|2000|2000||1|Crystal,4#Energy,4#Dark Matter,5#|Dark Matter,1000#|3|2|Fort  is useful at a time of you are under the power of another emperor?s  mission. At each level of the fort, there is a maximum number of forts that could be built and different amount of dark matter or crystal is needed to upgrade to next level. Having more forts increase your population limit|0|0|def|");
        buildings.put(bu.getId(), bu);
        bu = new Building("Solar Plant|200|200||1|Energy,5#Dark Matter,1#|Metal,800#|3|5|Solar Plant is a resource building capable of boosting the gathering rate of energy and dark matter. It is recommended to build it in a grassless region for higher efficiency. Different amount of crystal and metal is needed to upgrade to the next level. Once updated to a higher level the production rate of energy and dark matter increases. At each level there are a maximum number of solar plant units that could be made|0|0|res|");
        buildings.put(bu.getId(), bu);
    }

    private void loadUnits() {
        Unit un = new Unit("Commando|400|hrmb|60|Metal,1#Crystal,1#Energy,8#|Commando is an individual who is specialize in offensive or assault tasks. Commando could be built in a level two barrack. Metal and crystal is used to produce a Commando|0|4|100|12|14|");
        units.put(un.getId(), un);
        un = new Unit("Hover Craft|70|grwa|60|Metal,6#Crystal,3#Energy,15#|Hover craft is often used by militaries for missions. It could be built in a level three factory. Metal and crystal is used to produce a Hover Craft. It can move both on water and ground|0|3|100|40|28|");
        units.put(un.getId(), un);
        un = new Unit("Bomber|50|airb|60|Metal,5#Crystal,10#Energy,2#|A bomber is a military aircraft designed to attack ground targets, by dropping bombs. Bomber could be built in a level three factory. Metal and crystal is used to produce a Bomber|0|4|100|26|18|");
        units.put(un.getId(), un);
        un = new Unit("War Bike|90|grsa|60|Metal,6#Crystal,6#Energy,6#|War bike is used to enhance the power of a mission. It is a fast unit and can move on shallow waters. War bike could be built in a level two factory.  Metal and crystal is used to produce a war tank|0|3|100|24|24|");
        units.put(un.getId(), un);
        un = new Unit("War Tank|100|grdb|60|Metal,4#Crystal,3#Energy,5#|A war tank is an armored vehicle designed for front-line action, combining strong offensive and defensive capabilities. War tank could be built in a level one factory. Metal and crystal is used to produce a war tank|0|4|100|34|26|");
        units.put(un.getId(), un);
        un = new Unit("Chopper|60|aira|60|Metal,9#Crystal,9#Energy,5#|Chopper could be useful as an attacking or as a defensive unit. It could be built in a level three factory. Metal, crystal and dark matter is used to produce a Chopper|0|3|100|28|24|");
        units.put(un.getId(), un);
        un = new Unit("Cannon|120|grdb|60|Metal,10#Crystal,5#Energy,6#|Cannon is a type of artillery, which uses gun power to launch a projectile over a distance. It could be built in a level three factory. Metal and crystal is used to produce a Cannon|0|8|100|28|26|");
        units.put(un.getId(), un);
        un = new Unit("Sniper|70|hrmc|60|Metal,4#Crystal,6#Energy,3#|A sniper is a combatant who specializes in shooting from a concealed position over longer ranges, with a sniper rifle. Sniper could be built in a level three factory. Metal, crystal and dark matter is used to produce a Sniper|0|6|150|18|16|");
        units.put(un.getId(), un);
        un = new Unit("Rocket Launcher|50|hrma|60|Metal,4#Crystal,1#Energy,10#|Rocket launcher is used to launch missiles, which are military rockets containing an explosive warhead. Rocket Launcher could be built in a level one barrack. Metal is used to produce a Rocket Launcher|0|6|100|14|16|");
        units.put(un.getId(), un);
    }


    private void loadMaps() {
        String map = "refo1:30*30*19,0,0,0*1,0,6,6*1,0,6,7*9,0,4,0*12,0,0,0*1,0,6,6*1,0,6,7*5,0,2,6*11,0,4,0*9,0,0,0*1,0,6,6*2,0,2,6*10,0,4,0*1,0,3,6*7,0,4,0*4,0,0,0*1,0,6,6*1,0,1,7*3,0,0,0*1,0,4,6*2,0,1,9*2,0,1,8*1,0,3,9*1,0,2,7*1,0,4,6*4,0,4,0*1,0,2,7*1,0,0,0*1,0,0,7*6,0,4,0*2,0,0,0*1,0,6,6*1,0,6,7*1,0,4,0*1,0,5,6*3,0,0,0*1,0,0,7*1,0,2,7*5,0,0,0*1,0,0,7*3,0,3,6*1,0,2,7*3,0,0,0*1,0,4,6*5,0,4,0*1,0,0,0*1,0,6,6*1,0,6,7*2,0,3,7*1,0,2,7*18,0,0,0*1,0,0,7*3,0,3,6*2,0,4,0*1,0,6,7*2,0,4,0*1,0,2,7*24,0,0,0*1,0,4,6*3,0,4,0*1,0,2,7*25,0,0,0*1,0,4,6*1,0,4,0*1,0,3,6*1,0,2,7*26,0,0,0*1,0,4,6*1,0,4,0*28,0,0,0*1,0,0,7*1,0,1,8*29,0,0,0*1,0,4,6*1,1,5,1,0,0*1,1,0,1,0,0*1,1,0,2,0,0*1,1,4,2,0,0*25,0,0,0*1,0,0,7*2,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*11,0,0,0*1,0,1,5*1,0,3,5*15,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*10,0,0,0*1,0,1,5*2,0,3,0*1,0,3,5*14,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*8,0,0,0*1,0,1,5*1,0,4,4*4,0,3,0*2,0,4,4*1,0,3,5*11,0,0,0*1,1,1,2,0,0*1,1,0,2,0,0*1,1,2,2,0,0*7,0,0,0*1,0,1,5*7,0,3,0*1,0,0,5*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*7,0,0,0*1,0,6,4*9,0,3,0*11,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*7,0,0,0*1,0,6,4*9,0,3,0*11,0,0,0*1,1,1,2,0,0*1,1,0,2,0,0*7,0,0,0*1,0,2,5*10,0,3,0*11,0,0,0*1,1,4,2,0,0*1,1,0,2,0,0*7,0,0,0*1,0,2,5*9,0,3,0*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*8,0,0,0*1,0,2,5*7,0,3,0*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*9,0,0,0*1,0,2,5*5,0,3,0*1,0,4,5*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*13,0,0,0*1,0,2,5*1,0,3,0*13,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*28,0,0,0*1,1,1,2,0,0*174,0,0,0*";
        String[] kx = new String[]{map.substring(0, map.indexOf(":")), map.substring(map.indexOf(":") + 1)};
        maps.put(kx[0], kx[1]);
        System.out.println(kx[0]);
        System.out.println(kx[1]);
        map = "refo2:30*30*19,0,0,0*1,0,6,6*1,0,6,7*9,0,4,0*12,0,0,0*1,0,6,6*1,0,6,7*5,0,2,6*11,0,4,0*9,0,0,0*1,0,6,6*2,0,2,6*10,0,4,0*1,0,3,6*7,0,4,0*4,0,0,0*1,0,6,6*1,0,1,7*3,0,0,0*1,0,4,6*2,0,1,9*2,0,1,8*1,0,3,9*1,0,2,7*1,0,4,6*4,0,4,0*1,0,2,7*1,0,0,0*1,0,0,7*6,0,4,0*2,0,0,0*1,0,6,6*1,0,6,7*1,0,4,0*1,0,5,6*3,0,0,0*1,0,0,7*1,0,2,7*5,0,0,0*1,0,0,7*3,0,3,6*1,0,2,7*3,0,0,0*1,0,4,6*5,0,4,0*1,0,0,0*1,0,6,6*1,0,6,7*2,0,3,7*1,0,2,7*18,0,0,0*1,0,0,7*3,0,3,6*2,0,4,0*1,0,6,7*2,0,4,0*1,0,2,7*24,0,0,0*1,0,4,6*3,0,4,0*1,0,2,7*25,0,0,0*1,0,4,6*1,0,4,0*1,0,3,6*1,0,2,7*26,0,0,0*1,0,4,6*1,0,4,0*28,0,0,0*1,0,0,7*1,0,1,8*29,0,0,0*1,0,4,6*1,1,5,1,0,0*1,1,0,1,0,0*1,1,0,2,0,0*1,1,4,2,0,0*25,0,0,0*1,0,0,7*2,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*11,0,0,0*1,0,1,5*1,0,3,5*15,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*10,0,0,0*1,0,1,5*2,0,3,0*1,0,3,5*14,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*8,0,0,0*1,0,1,5*1,0,4,4*4,0,3,0*2,0,4,4*1,0,3,5*11,0,0,0*1,1,1,2,0,0*1,1,0,2,0,0*1,1,2,2,0,0*7,0,0,0*1,0,1,5*7,0,3,0*1,0,0,5*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*7,0,0,0*1,0,6,4*9,0,3,0*11,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*7,0,0,0*1,0,6,4*9,0,3,0*11,0,0,0*1,1,1,2,0,0*1,1,0,2,0,0*7,0,0,0*1,0,2,5*10,0,3,0*11,0,0,0*1,1,4,2,0,0*1,1,0,2,0,0*7,0,0,0*1,0,2,5*9,0,3,0*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*8,0,0,0*1,0,2,5*7,0,3,0*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*9,0,0,0*1,0,2,5*5,0,3,0*1,0,4,5*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*13,0,0,0*1,0,2,5*1,0,3,0*13,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*28,0,0,0*1,1,1,2,0,0*174,0,0,0*";
        kx = new String[]{map.substring(0, map.indexOf(":")), map.substring(map.indexOf(":") + 1)};
        maps.put(kx[0], kx[1]);
        map = "refo3:30*30*19,0,0,0*1,0,6,6*1,0,6,7*9,0,4,0*12,0,0,0*1,0,6,6*1,0,6,7*5,0,2,6*11,0,4,0*9,0,0,0*1,0,6,6*2,0,2,6*10,0,4,0*1,0,3,6*7,0,4,0*4,0,0,0*1,0,6,6*1,0,1,7*3,0,0,0*1,0,4,6*2,0,1,9*2,0,1,8*1,0,3,9*1,0,2,7*1,0,4,6*4,0,4,0*1,0,2,7*1,0,0,0*1,0,0,7*6,0,4,0*2,0,0,0*1,0,6,6*1,0,6,7*1,0,4,0*1,0,5,6*3,0,0,0*1,0,0,7*1,0,2,7*5,0,0,0*1,0,0,7*3,0,3,6*1,0,2,7*3,0,0,0*1,0,4,6*5,0,4,0*1,0,0,0*1,0,6,6*1,0,6,7*2,0,3,7*1,0,2,7*18,0,0,0*1,0,0,7*3,0,3,6*2,0,4,0*1,0,6,7*2,0,4,0*1,0,2,7*24,0,0,0*1,0,4,6*3,0,4,0*1,0,2,7*25,0,0,0*1,0,4,6*1,0,4,0*1,0,3,6*1,0,2,7*26,0,0,0*1,0,4,6*1,0,4,0*28,0,0,0*1,0,0,7*1,0,1,8*29,0,0,0*1,0,4,6*1,1,5,1,0,0*1,1,0,1,0,0*1,1,0,2,0,0*1,1,4,2,0,0*25,0,0,0*1,0,0,7*2,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*11,0,0,0*1,0,1,5*1,0,3,5*15,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*10,0,0,0*1,0,1,5*2,0,3,0*1,0,3,5*14,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*8,0,0,0*1,0,1,5*1,0,4,4*4,0,3,0*2,0,4,4*1,0,3,5*11,0,0,0*1,1,1,2,0,0*1,1,0,2,0,0*1,1,2,2,0,0*7,0,0,0*1,0,1,5*7,0,3,0*1,0,0,5*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*7,0,0,0*1,0,6,4*9,0,3,0*11,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*7,0,0,0*1,0,6,4*9,0,3,0*11,0,0,0*1,1,1,2,0,0*1,1,0,2,0,0*7,0,0,0*1,0,2,5*10,0,3,0*11,0,0,0*1,1,4,2,0,0*1,1,0,2,0,0*7,0,0,0*1,0,2,5*9,0,3,0*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*8,0,0,0*1,0,2,5*7,0,3,0*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*9,0,0,0*1,0,2,5*5,0,3,0*1,0,4,5*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*13,0,0,0*1,0,2,5*1,0,3,0*13,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*28,0,0,0*1,1,1,2,0,0*174,0,0,0*";
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
