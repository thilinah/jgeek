// package dev.di.map;

//import dev.di.data.dao.PlanetDao;
//import dev.di.data.dao.Unit;
//import dev.di.forms.ChildForm;
//import dev.di.forms.FormBuilding;
//import dev.di.forms.FormResources;
//import dev.di.forms.FormQuickLinks;
//import dev.di.main.JCageConfigurator;
//import dev.di.main.MainMid;
//import dev.di.map.buildings.BaseBuilding;
//import dev.di.map.buildings.BaseBuildingService;
//import dev.di.map.buildings.IBuilding;
//import dev.di.map.units.BaseUnitService;
//import dev.di.map.units.DynamicUnit;

import javax.microedition.lcdui.CommandListener;  					import javax.microedition.lcdui.AlertType; 					import javax.microedition.lcdui.Canvas; 					import javax.microedition.lcdui.Command; 					import javax.microedition.lcdui.Display; 					import javax.microedition.lcdui.Displayable; 					import javax.microedition.lcdui.Font; 					import javax.microedition.lcdui.Graphics; 					import javax.microedition.lcdui.Image;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 5, 2007
 * Time: 3:30:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlanetMap extends Canvas implements Runnable, ChildForm, CommandListener {

    //public static String currentUnitCreationMap=null;
    int timer = 1;
    int ucTimer = 1500;
    Thread myThread = null;
    boolean exec = false;
    ChildForm parent = null;
    Command cmdBack = new Command("Leave Planet", Command.BACK, 1);
    //Command cmdBuild = new Command("Build", Command.BACK, 2);
    //Command cmdNew = new Command("New Mission", Command.OK, 1);

    String title;
    StringItem si = null;
    Environment en = null;
    public static Environment ENV = null;

    //game variables
    int lastArrowKey = 0;
    public static int refreshPeriod = 80;
    CurserManager cm = null;
    UnitManager um = null;
    int startFlag = 0;
    boolean finished = false;
    GameProxy gameProxy;

    public PlanetMap(String s, ChildForm parent, GameProxy gameProxy) {
        //super(false);
        this.parent = parent;
        this.gameProxy=gameProxy;
        //System.out.println("my p " + myPlanet);
        this.addCommand(cmdBack);
        //this.addCommand(cmdBuild);
        this.setCommandListener(this);
        setFullScreenMode(true);
        System.out.println("11111");
        en = Ground.getEnvironment(gameProxy.getMap(), gameProxy.getSaveName());
        System.out.println("21111");
        System.out.println(en);
        ENV = en;
        cm = new CurserManager(en, getHeight(), getWidth(),gameProxy);
        um = new UnitManager(en, getHeight(), getWidth());
        System.out.println("##1");
        cm.setUm(um);
        exec = true;
        myThread = new Thread(this);
        System.out.println("##2");


        try {
            JCageConfigurator.scoreTable = new Hashtable();
            System.out.println("Starting...");
            myThread.start();
            System.out.println("started...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void paint(Graphics g) {
        if (startFlag == 0) {
            g.setColor(0, 0, 0);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(255, 255, 255);
            g.drawString("Loading Terrain....0%", getWidth() / 2, 0, Graphics.TOP | Graphics.HCENTER);
            startFlag = 1;
        } else {
            drawGround(g);
        }
    }

    private void drawGround(Graphics g) {
        EnvirnmentRenderer.render(en, g, getWidth(), getHeight());
    }


    public void run() {
        long k = 0;
        long paintTimer = 0;
        boolean setTime = true;
        boolean setTimePaint = true;
        finished = false;

        while (exec) {
            try {
                timer++;
                if (timer >= 20000) {
                    timer = 0;

                }
//                if ((timer) % ucTimer == 0) {
//                    boolean created = false;
//                    int ir = Math.abs(Ground.rand.nextInt()) % 2 + 1;
//                    for (int i = 0; i < ir; i++) {
//                        created = created || handleInhabitantUnitCreation(timer);
//                    }
//                    if (!created) {
//                        System.out.println("No units created");
//                        timer = ucTimer - 100;
//                    }
//                }




                handleBuildingCreation();
                handleUnitCreation();
                um.moveAndFireUnits(timer);
                repaint();
                setTime = true;
                //}


                Thread.sleep(refreshPeriod);
            } catch (InterruptedException ex1) {
                ex1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (Exception ex2) {
                ex2.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        finished = true;
    }


    private void handleBuildingCreation() {
        //handle building creation
        if (JCageConfigurator.currentSelectedBuilding != null && um.getBuildingToBuild() == null) {
            um.setBuildingToBuild(new BaseBuilding(en, gameProxy.getSaveName(), JCageConfigurator.currentSelectedBuilding,
                    MainMid.getClientConfigurator().user));
            um.getBuildingToBuild().setEnvironment(en);
        }
    }


    private void handleUnitCreation() {
        //handle building creation
        if (JCageConfigurator.currentSelectedUnit != null && JCageConfigurator.currentSelectedBaseBuilding != null) {
            DynamicUnit bu = new DynamicUnit(en, gameProxy.getSaveName(), JCageConfigurator.currentSelectedUnit, MainMid.getClientConfigurator().user);

            //possitioning the newly created unit

            int tempX = JCageConfigurator.currentSelectedBaseBuilding.getX() + bu.getSizeX() / 2 + JCageConfigurator.currentSelectedBaseBuilding.getSizeX() / 2;
            int tempY = JCageConfigurator.currentSelectedBaseBuilding.getY() + bu.getSizeY() / 2 + JCageConfigurator.currentSelectedBaseBuilding.getSizeY() / 2;


            if (bu.isAllowedCell(tempX, tempY)) {
                bu.setX(tempX);
                bu.setY(tempY);
            } else {
                bu.setX(tempX + 10);
                bu.setY(tempY + 10);
            }

            int ra1 = Ground.rand.nextInt() % 2;
            int ra2 = Ground.rand.nextInt() % 2;

            if (ra1 == 0 && ra2 == 0) {
                ra1 = 1;
                ra2 = 1;
            }

            bu.registerMove(JCageConfigurator.currentSelectedBaseBuilding.getX() + bu.getSizeX() / 2 + ra1 * JCageConfigurator.currentSelectedBaseBuilding.getSizeX() / 2,
                    JCageConfigurator.currentSelectedBaseBuilding.getY() + bu.getSizeY() / 2 + ra2 * JCageConfigurator.currentSelectedBaseBuilding.getSizeY() / 2);
            //save building
            try {
                BaseUnitService.getInstance().insert(bu);
                //System.out.println("Building saved");
//                synchronized (Environment.unitHtLock) {
                en.units.put(bu.getUnitId(), bu);
//                }
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            JCageConfigurator.currentSelectedBaseBuilding = null;
            JCageConfigurator.currentSelectedUnit = null;


        }

    }

    private boolean handleInhabitantUnitCreation(int timer) {
        //handle building creation

        //if (timer % 1000 != 0) return;

        System.out.println("%%%%%%%%%%%%%%% creatin IH U");
        int k = Math.abs(Ground.rand.nextInt()) % 3;
        System.out.println("k " + k);
        Unit setU = null;
        String type = "";
        if (k == 0) {
            type = JCageConfigurator.UNIT_GROUND_HUMAN_A;
        } else if (k == 1) {
            type = JCageConfigurator.UNIT_GROUND_HUMAN_B;
        } else {
            type = JCageConfigurator.UNIT_GROUND_HUMAN_C;
        }
        Enumeration e = JCageConfigurator.units.keys();
        while (e.hasMoreElements()) {
            String key = String.valueOf(e.nextElement());
            Unit bu = (Unit) JCageConfigurator.units.get(key);
            System.out.println(bu.getType());
            if (bu.getType().equals(type)) {
                setU = bu;
                break;
            }
        }
        //Point[] pts = en.getUnitInsetionPoints();
        //Point point = pts[Math.abs(Ground.rand.nextInt()) % pts.length];
        DynamicUnit bx = new DynamicUnit(en, gameProxy.getSaveName(), setU, MainMid.getClientConfigurator().user + "ene");

        long x = Math.abs(Ground.rand.nextInt()) % (GroundCell.W * Ground.width);
        long y = Math.abs(Ground.rand.nextInt()) % (GroundCell.H * Ground.height);
        int x1 = (int) x;
        int y1 = (int) y;
        if (bx.isAllowedCell(x1, y1)) {
            //possitioning the newly created unit
            bx.setX(x1);
            bx.setY(y1);

            System.out.println("Saving iH unit");
            try {
                BaseUnitService.getInstance().insert(bx);
                //System.out.println("Building saved");
//            synchronized (Environment.unitHtLock) {
                en.units.put(bx.getUnitId(), bx);
//            }
            } catch (Exception ex) {
                ex.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return true;
        }
        return false;

    }


    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            exec = false;
            try {
                BaseUnitService.getInstance().insertOrUpdate(en.units);
                BaseBuildingService.getInstance().insertOrUpdate(en.buildings);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            while (finished) {

            }

            ENV = null;
            MainMid.getClientConfigurator().displayParent();
        }
    }

    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        return this;
    }

    protected void keyPressed(int keyCode) {
        switch (getGameAction(keyCode)) {
            case Canvas.UP:
                cm.moveUp();
                repaint();
                break;
            case Canvas.DOWN:
                cm.moveDown();
                repaint();
                break;
            case Canvas.LEFT:
                cm.moveLeft();
                repaint();
                break;
            case Canvas.RIGHT:
                cm.moveRight();
                repaint();
                break;

            case Canvas.FIRE:
                cm.clicked(this);
                repaint();
                break;

            default:
                switch (keyCode) {
                    case Canvas.KEY_NUM1:
                        FormBuilding f1 = new FormBuilding("bil", this, true);
                        MainMid.getClientConfigurator().display(f1);
                        break;
                    case Canvas.KEY_NUM2:
                        cm.moveScreenUp();
                        repaint();
                        break;
                    case Canvas.KEY_NUM3:
                        cm.cancel();
                        break;
                    case Canvas.KEY_NUM4:
                        cm.moveScreenLeft();
                        repaint();
                        break;
                    case Canvas.KEY_NUM5:
                        um.getLastSelectedUnit().fire();
                        break;
                    case Canvas.KEY_NUM6:
                        cm.moveScreenRight();
                        repaint();
                        break;
                    case Canvas.KEY_NUM7:
                        FormResources f = new FormResources("res", this);
                        MainMid.getClientConfigurator().display(f);
                        break;
                    case Canvas.KEY_NUM8:
                        cm.moveScreenDown();
                        repaint();
                        break;
                    case Canvas.KEY_NUM9:
                        cm.delete();
                        break;
                    case Canvas.KEY_NUM0:
                        FormQuickLinks f2 = new FormQuickLinks("", this);
                        MainMid.getClientConfigurator().display(f2);
                        break;
                }
        }
    }


    protected void keyRepeated(int keyCode) {
        int x = getGameAction(keyCode);

        if (x == Canvas.UP || x == Canvas.DOWN || x == Canvas.LEFT || x == Canvas.RIGHT ||
                keyCode == Canvas.KEY_NUM2 || keyCode == Canvas.KEY_NUM4 || keyCode == Canvas.KEY_NUM6 ||
                keyCode == Canvas.KEY_NUM8) {
            keyPressed(keyCode);
            if (lastArrowKey == x) {
                cm.incCurserSpeed();
            } else {
                cm.resetCurserSpeed();
            }
            lastArrowKey = x;
        }

//        if (x == Canvas.UP || x == Canvas.DOWN || x == Canvas.LEFT || x == Canvas.RIGHT) {
//
//            if (lastArrowKey == x) {
//                cm.incCurserSpeed();
//            } else {
//                cm.resetCurserSpeed();
//            }
//            lastArrowKey = x;
//        }
    }


}
