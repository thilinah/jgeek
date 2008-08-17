// package dev.di.map;

//import dev.di.map.buildings.IBuilding;
//import dev.di.map.units.IUnit;
//import dev.di.main.JCageConfigurator;

import javax.microedition.lcdui.Graphics;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 20, 2008
 * Time: 1:00:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnvirnmentRenderer {
    public static int UNIT_MARGIN = 60;
    public static int screenXK=0;
    public static int screenYK=0;




    public static void render(Environment en, Graphics g, int screenX, int screenY) {
        screenXK=screenX;
        screenYK=screenY;
        en.setScreenSizeX(screenX);
        en.setScreenSizeY(screenY);
        try {
            renderGround(en, g, screenX, screenY);
            if (en.initialized) {
                renderBuildings(en, g, screenX, screenY);
                renderGroundUnits(en, g, screenX, screenY);
                renderTrees(en, g, screenX, screenY);
                renderFires(en, g, screenX, screenY);
                renderExplosions(en, g, screenX, screenY);
                renderAirUnits(en, g, screenX, screenY);
                renderCurser(en, g, screenX, screenY);

            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static void renderGround(Environment en, Graphics g, int screenX, int screenY) {
        GroundCell bc1 = new GroundCell(0, 0, 0);
        GroundCell bc2 = new GroundCell(1, 0, 13);
        if (!en.initialized) {
            int k1 = en.getGround()[0].length;
            int k2 = en.getGround().length;
            int totP = k1 * k2;
            int i = en.iniParam;
            for (; i < k2; i++) {
                for (int j = 0; j < k1; j++) {

                    Ground.getGroundCell(en.getGround()[i][j]);
                }
                int cp = (((i + 1) * k1) * 100) / totP;
                g.setColor(0, 0, 0);
                g.fillRect(0, 0, screenX, screenY);
                g.setColor(255, 255, 255);
                g.drawString("Loading Terrain..." + cp + "%", screenX / 2, 0, Graphics.TOP | Graphics.HCENTER);
                break;
            }
            en.iniParam++;
            if (en.iniParam >= k2) {
                en.initialized = true;
            }
        } else {
            int rx = en.getStartPointX() % en.getCellSizeX();
            int mx = (en.getStartPointX() - rx) / en.getCellSizeX();

            int ry = en.getStartPointY() % en.getCellSizeY();


            for (int i = -rx; i < screenX; i = i + en.getCellSizeX()) {
                int my = (en.getStartPointY() - ry) / en.getCellSizeY();
                for (int j = -ry; j < screenY; j = j + en.getCellSizeY()) {

                    GroundCell gcToRen = en.getGround()[mx][my];
                    if (gcToRen.alpha == bc2.alpha && gcToRen.x == bc2.x && gcToRen.y == bc2.y) {
                        gcToRen = bc1;
                    }

//                if(en.getGround()[mx][my]>=16 && en.getGround()[mx][my]<=23){
//                    g.drawImage(Ground.getGroundCell(Ground.GRASS),i,j,
//                        Graphics.LEFT | Graphics.TOP);
//                }
//                    if((en.getUm().isBuildingCellExists(new Point(mx,my))!=null) || (en.getUm().isUnitCellExists(new Point(mx,my),"")!=null)){
//                        g.fillRect(i,j,24,12);
//                    }
//                    else{
                    g.drawImage(Ground.getGroundCell(gcToRen), i, j,
                            Graphics.LEFT | Graphics.TOP);
//                    }
                    my++;
                }
                mx++;
            }
        }
    }

    private static void renderCurser(Environment en, Graphics g, int screenX, int screenY) {
        //en.getCurser().setPosition(en.getCurserX(),en.getCurserY());
        en.getCurser().paint(g);
    }

    private static void drawHealthLine(int x, int y, int tt, int tx, Graphics g) {
        g.setColor(255, 0, 0);
        g.fillRect(x + tt / 4, y - 5, tt, 2);
        g.setColor(0, 255, 0);
        g.fillRect(x + tt / 4, y - 5, tx, 2);
    }

    private static void drawStatusLine(int x, int y, int tt, int tx, Graphics g) {
        g.setColor(0, 0, 255);
        g.fillRect(x + tt / 4, y - 5, tt, 2);
        g.setColor(0, 255, 0);
        g.fillRect(x + tt / 4, y - 5, tx, 2);
    }

    private static void renderGroundUnits(Environment en, Graphics g, int screenX, int screenY) {
//        synchronized (Environment.unitHtLock) {
            Enumeration e = en.units.keys();
            UnitManager um = en.getUm();
            while (e.hasMoreElements()) {
                IUnit bu = (IUnit) en.units.get(e.nextElement());
                if (!(bu.getBase().getType().equals(JCageConfigurator.UNIT_AIR_A) || bu.getBase().getType().equals(JCageConfigurator.UNIT_AIR_A)))
                {
                    if ((en.getStartPointX() - UNIT_MARGIN < bu.getX() && bu.getX() < en.getStartPointX() + screenX) &&
                            (en.getStartPointY() - UNIT_MARGIN < bu.getY() && bu.getY() < en.getStartPointY() + screenY))
                    {
                        bu.getSprite().setPosition(bu.getX() - en.getStartPointX() - bu.getSizeX() / 2,
                                bu.getY() - en.getStartPointY() - bu.getSizeY() / 2);
                        bu.getSprite().paint(g);
                        if (um.isUnitSelected() && um.getLastSelectedUnit().equals(bu)) {
                            IUnit iu = um.getLastSelectedUnit();
                            if (iu != null) {
                                int tt = screenX / 20;
                                tt = (tt < iu.getSprite().getWidth() / 2) ? iu.getSprite().getWidth() / 2 : tt;
                                int tx = (tt * iu.getStrength()) / Integer.parseInt(iu.getBase().getStrength());
                                drawHealthLine(bu.getX() - en.getStartPointX() - bu.getSizeX() / 2,
                                        bu.getY() - en.getStartPointY() - bu.getSizeY() / 2, tt, tx, g);
                            }
                        }
                    }
                }
            }
//        }
    }

    private static void renderAirUnits(Environment en, Graphics g, int screenX, int screenY) {
//        synchronized (Environment.unitHtLock) {
            Enumeration e = en.units.keys();
            UnitManager um = en.getUm();
            while (e.hasMoreElements()) {
                IUnit bu = (IUnit) en.units.get(e.nextElement());
                if (bu.getBase().getType().equals(JCageConfigurator.UNIT_AIR_A) || bu.getBase().getType().equals(JCageConfigurator.UNIT_AIR_B))
                {
                    if ((en.getStartPointX() - UNIT_MARGIN < bu.getX() && bu.getX() < en.getStartPointX() + screenX) &&
                            (en.getStartPointY() - UNIT_MARGIN < bu.getY() && bu.getY() < en.getStartPointY() + screenY))
                    {
                        bu.getSprite().setPosition(bu.getX() - en.getStartPointX() - bu.getSizeX() / 2,
                                bu.getY() - en.getStartPointY() - bu.getSizeY() / 2);
                        //bu.fly();
                        bu.getSprite().paint(g);
                        if (um.isUnitSelected() && um.getLastSelectedUnit().equals(bu)) {
                            IUnit iu = um.getLastSelectedUnit();
                            if (iu != null) {
                                int tt = screenX / 20;
                                tt = (tt < iu.getSprite().getWidth() / 2) ? iu.getSprite().getWidth() / 2 : tt;
                                int tx = (tt * iu.getStrength()) / Integer.parseInt(iu.getBase().getStrength());
                                drawHealthLine(bu.getX() - en.getStartPointX() - bu.getSizeX() / 2,
                                        bu.getY() - en.getStartPointY() - bu.getSizeY() / 2, tt, tx, g);
                            }
                        }
                    }
                }
            }
//        }
    }

    private static void renderBuildings
            (Environment
                    en, Graphics
                    g, int screenX,
                       int screenY) {
        Enumeration e = en.buildings.keys();
        UnitManager um = en.getUm();
        while (e.hasMoreElements()) {
            IBuilding bu = (IBuilding) en.buildings.get(e.nextElement());
            ////System.out.println("Building found " + bu);
            if ((en.getStartPointX() - UNIT_MARGIN < bu.getX() && bu.getX() < en.getStartPointX() + screenX) &&
                    (en.getStartPointY() - UNIT_MARGIN < bu.getY() && bu.getY() < en.getStartPointY() + screenY)) {
                bu.getSprite().setPosition(bu.getX() - en.getStartPointX() - bu.getSizeX() / 2,
                        bu.getY() - en.getStartPointY() - bu.getSizeY() / 2);

                bu.getSprite().paint(g);



                if (um.isBuildingSelected() && um.getLastSelectedBuilding().equals(bu)) {
                    IBuilding iu = um.getLastSelectedBuilding();
                    if (iu != null) {
                        int tt = screenX / 20;
                        tt = (tt < iu.getSprite().getWidth() / 2) ? iu.getSprite().getWidth() / 2 : tt;
                        int tx = (tt * iu.getStrength()) / Integer.parseInt(iu.getBase().getStrength());
                        drawHealthLine(bu.getX() - en.getStartPointX() - bu.getSizeX() / 2,
                                bu.getY() - en.getStartPointY() - bu.getSizeY() / 2, tt, tx, g);
                    }
                }

                //drawing status line
                if (bu.isStillCreating()) {

                    int tt = screenX / 20;
                    tt = (tt < bu.getSprite().getWidth() / 2) ? bu.getSprite().getWidth() / 2 : tt;
                    int tx = (tt * bu.getStatus()) / Integer.parseInt(bu.getBase().getTotalStrength());
                    drawStatusLine(bu.getX() - en.getStartPointX() - bu.getSizeX() / 2,
                            bu.getY() - en.getStartPointY() - bu.getSizeY() / 2, tt, tx, g);

                }
            }
        }
    }

    private static void renderExplosions
            (Environment
                    en, Graphics
                    g, int screenX,
                       int screenY) {
        Enumeration e = en.explosions.keys();
        while (e.hasMoreElements()) {
            Object k = e.nextElement();
            Animation bu = (Animation) en.explosions.get(k);

//            if((en.getStartPointX()-UNIT_MARGIN <bu.getX() && bu.getX() < en.getStartPointX() + screenX) &&
//                (en.getStartPointY()-UNIT_MARGIN <bu.getY() && bu.getY() < en.getStartPointY() + screenY)){

            bu.getSprite().setPosition(bu.getX() - en.getStartPointX() - bu.getSizeX() / 2,
                    bu.getY() - en.getStartPointY() - bu.getSizeY() / 2);
            if (!bu.animate(g)) {
                en.explosions.remove(k);
            }
//            }
        }
    }

    private static void renderTrees
            (Environment
                    en, Graphics
                    g, int screenX,
                       int screenY) {
        Enumeration e = en.trees.keys();
        while (e.hasMoreElements()) {
            Object k = e.nextElement();
            Animation bu = (Animation) en.trees.get(k);

            if ((en.getStartPointX() - UNIT_MARGIN < bu.getX() && bu.getX() < en.getStartPointX() + screenX) &&
                    (en.getStartPointY() - UNIT_MARGIN < bu.getY() && bu.getY() < en.getStartPointY() + screenY)) {

                bu.getSprite().setPosition(bu.getX() - en.getStartPointX() - bu.getSizeX() / 2,
                        bu.getY() - en.getStartPointY() - bu.getSizeY() / 2);
                bu.animate(g);

            }
        }
    }


    private static void renderFires
            (Environment
                    en, Graphics
                    g, int screenX,
                       int screenY) {
        Enumeration e = en.fires.keys();
        while (e.hasMoreElements()) {
            Object k = e.nextElement();
            MovingAnimation bu = (MovingAnimation) en.fires.get(k);

//            if((en.getStartPointX()-UNIT_MARGIN <bu.getX() && bu.getX() < en.getStartPointX() + screenX) &&
//                (en.getStartPointY()-UNIT_MARGIN <bu.getY() && bu.getY() < en.getStartPointY() + screenY)){

            bu.getSprite().setPosition(bu.getX() - en.getStartPointX() - bu.getSizeX() / 2,
                    bu.getY() - en.getStartPointY() - bu.getSizeY() / 2);
            if (!bu.animate(g)) {
                en.fires.remove(k);
            }

//            }
        }
    }
}
