package dev.di.map;

import dev.di.data.dao.PlanetDao;

import javax.microedition.lcdui.game.Sprite;
import java.util.Hashtable;
import java.util.Date;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 20, 2008
 * Time: 12:40:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Environment {
    public static Object unitHtLock =new Object();
    public boolean initialized = false;
    public int iniParam = 0;
    private GroundCell[][] ground;
    private int curserX = 0;
    private int curserY = 0;
    private int startPointX = 0;
    private int startPointY = 0;
    private int sizeX = 0;
    private int sizeY = 0;
    private int screenSizeX = 0;
    private int screenSizeY = 0;
    private int cellSizeX = 0;
    private int cellSizeY = 0;

    private Sprite curser = null;
    private Sprite curserSel = null;

    public Hashtable units = new Hashtable();
    public Hashtable buildings = new Hashtable();
    public Hashtable explosions = new Hashtable();
    public Hashtable trees = new Hashtable();
    public Hashtable fires = new Hashtable();

    private UnitManager um;


    Random r = new Random();

    public void setCurser(int x, int y) {
        curserX = x;
        curserY = y;
    }

    public void setStartPoint(int x, int y) {
        startPointX = x;
        startPointY = y;
    }

    public void setCellSize(int x, int y) {
        cellSizeX = x;
        cellSizeY = y;
    }

    public void setSize(int x, int y) {
        sizeX = x;
        sizeY = y;
    }

    public GroundCell[][] getGround() {
        return ground;
    }

    public int getCurserX() {
        return curserX;
    }

    public int getCurserY() {
        return curserY;
    }

    public int getStartPointX() {
        return startPointX;
    }

    public int getStartPointY() {
        return startPointY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getCellSizeX() {
        return cellSizeX;
    }

    public int getCellSizeY() {
        return cellSizeY;
    }

    public void setGround(GroundCell[][] ground) {
        this.ground = ground;
    }

    public Sprite getCurser() {
        return curser;
    }

    public void setCurser(Sprite curser) {
        this.curser = curser;
    }

    public Sprite getCurserSel() {
        return curserSel;
    }

    public void setCurserSel(Sprite curserSel) {
        this.curserSel = curserSel;
    }

    public int getScreenSizeX() {
        return screenSizeX;
    }

    public void setScreenSizeX(int screenSizeX) {
        this.screenSizeX = screenSizeX;
    }

    public int getScreenSizeY() {
        return screenSizeY;
    }

    public void setScreenSizeY(int screenSizeY) {
        this.screenSizeY = screenSizeY;
    }

    public GroundCell getCellType(int x, int y) throws Exception {
        if (x < 0 || x > getSizeX() || y < 0 || y > getSizeY()) {
            throw new Exception("env bound exceed");
        }
        int cx = (x - (x % cellSizeX)) / cellSizeX;
        int cy = (y - (y % cellSizeY)) / cellSizeY;
        return ground[cx][cy];
    }

    public Point getGroundPoint(int x, int y) throws Exception {
        if (x < 0 || x > getSizeX() || y < 0 || y > getSizeY()) {
            throw new Exception("env bound exceed");
        }
        int cx = (x - (x % cellSizeX)) / cellSizeX;
        int cy = (y - (y % cellSizeY)) / cellSizeY;
        return new Point(cx, cy);
    }


    private int getClosestDiv(int k1,int d){
        int r1 =(k1-(k1%d));
        int r2 =(k1-(k1%d))+d;
        if(k1-r1>r2-k1){
            return r2;
        }
        else{
            return r1;
        }
    }

    public Point[] getCellsInRange(int x1, int y1, int x2, int y2) {
        Point[] gc = new Point[22];
        int k = 0;
        x1=getClosestDiv(x1,GroundCell.W);
        x2=getClosestDiv(x2,GroundCell.W);
        if(x1<=x2 - GroundCell.W/2){
            x2++;
        }
        y1=getClosestDiv(y1,GroundCell.H);
        y2=getClosestDiv(y2,GroundCell.H);
        if(y1<=y2 - GroundCell.H/2){
            y2++;
        }
        for (int i = x1; i <= x2 - GroundCell.W/2; i += GroundCell.W) {
            for (int j = y1; j <= y2 - GroundCell.H/2; j += GroundCell.H) {
                try {
                    gc[k] = getGroundPoint(i, j);
                    k++;
                } catch (Exception e) {

                }
            }
        }
        if (k < 22) {
            Point[] ps = new Point[k];
            for (int i = 0; i < k; i++) {
                ps[i] = gc[i];
            }
            return ps;
        }
        return gc;
    }


    public int numberOfMountainCellsExistsInRange(int x, int y) {
        int c = 0;
        for (int i = x - 2 * GroundCell.W; i <= x + 2 * GroundCell.W; i = i + GroundCell.W) {
            for (int j = y - 3 * GroundCell.H; j <= y + 3 * GroundCell.H; j = j + GroundCell.H) {
                try {
                    if (checkMountainCell(getCellType(i, j))) {
                        c++;
                    }
                } catch (Exception e) {
                    //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        //System.out.println("numberOfMountainCellsExistsInRange(int x,int y) " + c);
        return c;
    }

    public int numberOfGressLessCellsExistsInRange(int x, int y) {
        int c = 0;
        for (int i = x - 2 * GroundCell.W; i <= x + 2 * GroundCell.W; i = i + GroundCell.W) {
            for (int j = y - 3 * GroundCell.H; j <= y + 3 * GroundCell.H; j = j + GroundCell.H) {
                try {
                    if (checkGrassLessCell(getCellType(i, j))) {
                        //System.out.println("i " + i + " j " + j);
                        c++;
                    }
                } catch (Exception e) {
                    //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        //System.out.println("numberOfGressLessCellsExistsInRange(int x,int y) " + c);
        return c;
    }

    public int numberOfShallowWaterCellsExistsInRange(int x, int y) {
        int c = 0;
        for (int i = x - 2 * GroundCell.W; i <= x + 2 * GroundCell.W; i = i + GroundCell.W) {
            for (int j = y - 3 * GroundCell.H; j <= y + 3 * GroundCell.H; j = j + GroundCell.H) {
                try {
                    if (checkShallowWaterCell(getCellType(i, j))) {
                        c++;
                    }
                } catch (Exception e) {
                    //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        //System.out.println("numberOfShallowWaterCellsExistsInRange(int x,int y) " + c);
        return c;
    }

//    public int numberOfMountainCellsExistsInRange(int x,int y){
//        int c=0;
//        for(int oy=y-6;oy<y+7;oy=oy+2){
//            try {
//                if(oy==y-4){
//                    if(checkMountainCell(getCellType(x,oy))){
//                        c++;
//                    }
//                }
//                if(oy==y-2){
//                    if(checkMountainCell(getCellType(x-2,oy))){
//                        c++;
//                    }
//                    if(checkMountainCell(getCellType(x,oy))){
//                        c++;
//                    }
//                    if(checkMountainCell(getCellType(x+2,oy))){
//                        c++;
//                    }
//                }
//
//                if(oy==y){
//                   if(checkMountainCell(getCellType(x-4,oy))){
//                       c++;
//                   }
//                   if(checkMountainCell(getCellType(x-2,oy))){
//                       c++;
//                   }
//                   if(checkMountainCell(getCellType(x,oy))){
//                       c++;
//                   }
//                   if(checkMountainCell(getCellType(x+2,oy))){
//                       c++;
//                   }
//                   if(checkMountainCell(getCellType(x+4,oy))){
//                       c++;
//                   }
//                }
//
//                if(oy==y+2){
//                    if(checkMountainCell(getCellType(x-2,oy))){
//                        c++;
//                    }
//                    if(checkMountainCell(getCellType(x,oy))){
//                        c++;
//                    }
//                    if(checkMountainCell(getCellType(x+2,oy))){
//                        c++;
//                    }
//                }
//
//                if(oy==y+4){
//                    if(checkMountainCell(getCellType(x,oy))){
//                        c++;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//        return c;
//    }
//
//    public int numberOfGressLessCellsExistsInRange(int x,int y){
//        int c=0;
//        for(int oy=y-2;oy<y+3;oy++){
//            try {
//                if(oy==y-2){
//                    if(checkGrassLessCell(getCellType(x,oy))){
//                        c++;
//                    }
//                }
//                if(oy==y-1){
//                    if(checkGrassLessCell(getCellType(x-1,oy))){
//                        c++;
//                    }
//                    if(checkGrassLessCell(getCellType(x,oy))){
//                        c++;
//                    }
//                    if(checkGrassLessCell(getCellType(x+1,oy))){
//                        c++;
//                    }
//                }
//
//                if(oy==y){
//                   if(checkGrassLessCell(getCellType(x-2,oy))){
//                       c++;
//                   }
//                   if(checkGrassLessCell(getCellType(x-1,oy))){
//                       c++;
//                   }
//                   if(checkGrassLessCell(getCellType(x,oy))){
//                       c++;
//                   }
//                   if(checkGrassLessCell(getCellType(x+1,oy))){
//                       c++;
//                   }
//                   if(checkGrassLessCell(getCellType(x+2,oy))){
//                       c++;
//                   }
//                }
//
//                if(oy==y+1){
//                    if(checkGrassLessCell(getCellType(x-1,oy))){
//                        c++;
//                    }
//                    if(checkGrassLessCell(getCellType(x,oy))){
//                        c++;
//                    }
//                    if(checkGrassLessCell(getCellType(x+1,oy))){
//                        c++;
//                    }
//                }
//
//                if(oy==y+2){
//                    if(checkGrassLessCell(getCellType(x,oy))){
//                        c++;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//        return c;
//    }
//
//
//    public int numberOfShallowWaterCellsExistsInRange(int x,int y){
//        int c=0;
//        for(int oy=y-2;oy<y+3;oy++){
//            try {
//                if(oy==y-2){
//                    if(checkShallowWaterCell(getCellType(x,oy))){
//                        c++;
//                    }
//                }
//                if(oy==y-1){
//                    if(checkShallowWaterCell(getCellType(x-1,oy))){
//                        c++;
//                    }
//                    if(checkShallowWaterCell(getCellType(x,oy))){
//                        c++;
//                    }
//                    if(checkShallowWaterCell(getCellType(x+1,oy))){
//                        c++;
//                    }
//                }
//
//                if(oy==y){
//                   if(checkShallowWaterCell(getCellType(x-2,oy))){
//                       c++;
//                   }
//                   if(checkShallowWaterCell(getCellType(x-1,oy))){
//                       c++;
//                   }
//                   if(checkShallowWaterCell(getCellType(x,oy))){
//                       c++;
//                   }
//                   if(checkShallowWaterCell(getCellType(x+1,oy))){
//                       c++;
//                   }
//                   if(checkShallowWaterCell(getCellType(x+2,oy))){
//                       c++;
//                   }
//                }
//
//                if(oy==y+1){
//                    if(checkShallowWaterCell(getCellType(x-1,oy))){
//                        c++;
//                    }
//                    if(checkShallowWaterCell(getCellType(x,oy))){
//                        c++;
//                    }
//                    if(checkShallowWaterCell(getCellType(x+1,oy))){
//                        c++;
//                    }
//                }
//
//                if(oy==y+2){
//                    if(checkShallowWaterCell(getCellType(x,oy))){
//                        c++;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//        return c;
//    }

    private boolean checkMountainCell(GroundCell gc) {
        //System.out.println("checkMountainCell(GroundCell gc)");
        //System.out.println(gc);
        if (gc.alpha == 1) {
            if (0 < gc.y && gc.y < 4) {
                return true;
            } else if (gc.y == 4 && gc.x < 4) {
                return true;
            }
        }
        return false;
    }


    private boolean checkGrassLessCell(GroundCell gc) {
        ////System.out.println("checkGrassLessCell(GroundCell gc)");
        //System.out.println(gc);
        if (gc.alpha == 0) {
            if (gc.y == 0 && gc.x == 3) {

                return true;
            } else if (gc.y == 5) {
                return true;
            } else if (gc.y == 4 && gc.x > 3) {
                return true;
            } else if (gc.y == 6 && gc.x < 2) {
                return true;
            }
        }
        return false;
    }

    private boolean checkShallowWaterCell(GroundCell gc) {
        //System.out.println("checkShallowWaterCell(GroundCell gc)");
        //System.out.println(gc);
        if (gc.alpha == 0) {
            if (gc.y == 8) {
                return true;
            } else if (gc.y == 9 && gc.x < 5) {
                return true;
            }
        }
        return false;
    }

    private boolean containsCell(GroundCell[] ga, GroundCell g) {
        for (int i = 0; i < ga.length; i++) {
            if (ga[i].equals(g)) {
                return true;
            }
        }
        return false;
    }

    public void addExplosion(Animation exp) {
        Date d = new Date();
        String s1 = String.valueOf(r.nextInt() % 1000);
        String s2 = String.valueOf(d.getTime());
        explosions.put(s2 + s1, exp);
    }

    public void addTree(Animation exp) {
        Date d = new Date();
        String s1 = String.valueOf(r.nextInt() % 1000);
        String s2 = String.valueOf(d.getTime());
        trees.put(s2 + s1, exp);
    }

    public void addFire(MovingAnimation fire) {
        Date d = new Date();
        String s1 = String.valueOf(r.nextInt() % 1000);
        String s2 = String.valueOf(d.getTime());
        fires.put(s2 + s1, fire);
    }

    public UnitManager getUm() {
        return um;
    }

    public void setUm(UnitManager um) {
        this.um = um;
    }

    public Point[] getUnitInsetionPoints() {
        GroundCell gc=new GroundCell(1,0,13);
        Point[] ps = new Point[10];
        int k=0;
        for (int i = 0; i < Ground.width ; i++) {
            for (int j = 0; j < Ground.height; j++) {
                if(ground[i][j].alpha==gc.alpha && ground[i][j].x ==gc.x && ground[i][j].y==gc.y){
                    ps[k++]=new Point(i*GroundCell.W,j*GroundCell.H);
                }
            }
        }
        Point[] px = new Point[k];
        for(int i=0;i<k;i++){
            px[i]=ps[i];
        }
        return px;
    }

}
