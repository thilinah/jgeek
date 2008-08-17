// package dev.di.map.units;

//import dev.di.data.dao.Unit;
//import dev.di.map.*;
//import dev.di.map.buildings.IBuilding;
//import dev.di.main.JCageConfigurator;
//import dev.di.main.MainMid;
//import dev.di.sound.SoundSynthesizer;

import javax.microedition.lcdui.game.Sprite;
import java.util.Date;
import java.util.Random;
import java.util.Hashtable;
import java.util.Enumeration;


/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 20, 2008
 * Time: 10:47:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class DynamicUnit extends BaseUnit {

    boolean truckMove=false;
    IUnit lastUnitFiredMe=null;
    boolean lastFiredUnitUpdated=false;
    boolean lastMoveStatus = false;
    long lastThoughtTime = 0;
    long lastMoveChangedPlayTime = 0;
    long lastMovedTime = 0;

    private static int STEP = 2;
    private static int FIRE_INTERVAL = 2;
    int moveToX = 0;
    int moveToY = 0;
    boolean moveMe = false;
    int sizeRatio = 0;
    boolean dirPriority = false;
    int [] repeatBufferX = null;
    int [] repeatBufferY = null;
    int repeatBufferEffLengthX = 0;
    int repeatBufferEffLengthY = 0;
    int lastOffX = 0;
    int lastOffY = 0;
    boolean regMoveChanged = false;
    int originalRegX = 0;
    int originalRegY = 0;
    int offsetX = 0;
    int offsetY = 0;
    private static Random rand = null;

    int numberOfLessTurns = 0;
    final int maxNumberOfLessTurns = 5;
    final int maxMovesPerLessTurn = 50;
    int movesFromLastTurn = 0;
    IUnit unitToFollow = null;


    public boolean engaged = false;
    public boolean engagedRescure = false;


    public DynamicUnit(Environment environment, String mission, Unit base, String owener) {
        super(environment, mission, base, owener);
        Date d = new Date();
        sizeRatio = (int) (d.getTime() % 2);
        repeatBufferX = new int[10];
        repeatBufferY = new int[10];
        for (int i = 0; i < repeatBufferX.length; i++) {
            repeatBufferX[i] = 2;
            repeatBufferY[i] = 2;
        }
        if (rand == null)
            rand = new Random();
    }


    private void detectRepeats() {
        if (movesFromLastTurn <= maxMovesPerLessTurn) {
            numberOfLessTurns++;
        } else {
            numberOfLessTurns = 0;
        }
        movesFromLastTurn = 0;

        if (numberOfLessTurns >= maxNumberOfLessTurns) {
            numberOfLessTurns = 0;
            changeRegistredMove();
        }
    }


    private boolean moveLeftOrRight(int dis) {
        //Date d = new Date();
        if (sizeRatio == 0) {
            return moveLeft(dis);
        } else {
            return moveRight(dis);
        }
    }

    private boolean moveRightOrLeft(int dis) {
        //Date d = new Date();
        if (sizeRatio == 0) {
            return moveRight(dis);
        } else {
            return moveLeft(dis);
        }
    }

    private void addToRepeatBuffer(int offX, int offY) {
        repeatBufferX[repeatBufferEffLengthX++] = offX;
        repeatBufferY[repeatBufferEffLengthY++] = offY;
        if (repeatBufferEffLengthX == repeatBufferX.length) {
            repeatBufferEffLengthX = 0;
        }
        if (repeatBufferEffLengthY == repeatBufferY.length) {
            repeatBufferEffLengthY = 0;
        }
    }

    private boolean detectRepeating() {
        int x = 0, y = 0;
        for (int i = 0; i < repeatBufferX.length; i++) {
            x += repeatBufferX[i];
        }
        for (int i = 0; i < repeatBufferY.length; i++) {
            y += repeatBufferY[i];
        }
        if ((x / repeatBufferX.length <= 1 && x / repeatBufferX.length >= -1) &&
                (y / repeatBufferY.length <= 1 && y / repeatBufferY.length >= -1)) {
//            //System.out.println("REP DETECTED %%%%%%%###############*********");
            for (int i = 0; i < repeatBufferX.length; i++) {
                repeatBufferX[i] = 2;
                repeatBufferY[i] = 2;
            }
            return true;

        }
        return false;
    }
//
//
//    private boolean checkRepeating(){
//        for(int i=2;i<=repeatBufferX.length/2;i++){
//            if(checkIrep(i,repeatBufferX)){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean checkIrep(int i,int[] repbuf){
//        int[] y=new int[i];
//        for(int x=0;x<i;x++)
//            y[x]=repbuf[x];
//        int rnds=repbuf.length-i;//(repbuf.length-(repbuf.length%i))/i;
//        for(int h=0;h<rnds;h++){
//            if(repbuf[h]!=repbuf[i+h]){
//                return false;
//            }
//        }
//        return true;
//    }

    private boolean moveUpOrDown(int dis) {
        //Date d = new Date();
        if (sizeRatio == 0) {
            return moveUp(dis);
        } else {
            return moveDown(dis);
        }
    }

    private boolean moveDownOrUp(int dis) {
        //Date d = new Date();
        if (sizeRatio == 0) {
            return moveDown(dis);
        } else {
            return moveUp(dis);
        }
    }

    private void switchSizeRatio() {
        sizeRatio = (sizeRatio == 0) ? 1 : 0;
    }

    public void move(int timerValue) {
        if (!moveTimer(timerValue)) {
            return;
        }
        if (!moveMe) {
            if (unitBase.getType().equals(JCageConfigurator.UNIT_AIR_A) ||
                    unitBase.getType().equals(JCageConfigurator.UNIT_AIR_B)) {
                fly(timerValue);
            }
            return;
        }

        refreshFollowing();

        offsetX = moveToX - getX();
        offsetY = moveToY - getY();
//        addToRepeatBuffer(lastOffX - offsetX, lastOffY - offsetY);
//        if (detectRepeating()) {
//            changeRegistredMove();
//        }
        lastOffX = offsetX;
        lastOffY = offsetY;
        if ((-(STEP + 1) < offsetY && offsetY < (STEP + 1)) && (-(STEP + 1) < offsetX && offsetX < (STEP + 1))) {
            //System.out.println("** Destination reached");
            if (moveToX == originalRegX && moveToY == originalRegY) {
                moveMe = false;
                regMoveChanged = false;
            }

            if (regMoveChanged) {
                moveToX = originalRegX;
                moveToY = originalRegY;
            } else {
                moveMe = false;
            }
        } else {
            if (!((-(STEP + 1) < offsetX && offsetX < (STEP + 1))) && !dirPriority) {
                if (offsetX < 0) {
                    if (!moveLeft(STEP)) {
                        if (!moveUpOrDown(STEP)) {
                            if (!moveDownOrUp(STEP)) {
                                switchSizeRatio();
                                if (!moveRight(STEP)) {
                                    //moveMe = false;
                                }
                            } else {
                                if (!((-(STEP + 1) < offsetY && offsetY < (STEP + 1)))) {
                                    dirPriority = !dirPriority;
//                                    //System.out.println("$$$$$$$$$$$$$$$$");
                                    detectRepeats();
                                } else {
                                    //moveMe = false;
                                }
                            }
                        }
                    }
                } else if (offsetX > 0) {
                    if (!moveRight(STEP)) {
                        if (!moveUpOrDown(STEP)) {
                            if (!moveDownOrUp(STEP)) {
                                switchSizeRatio();
                                if (!moveLeft(STEP)) {
                                    //moveMe = false;
                                }
                            } else {
                                if (!((-(STEP + 1) < offsetY && offsetY < (STEP + 1)))) {
                                    dirPriority = !dirPriority;
//                                    //System.out.println("$$$$$$$$$$$$$$$$");
                                    detectRepeats();
                                } else {
                                    //moveMe = false;
                                }
                            }
                        }
                    }
                }
            }

            if (!((-(STEP + 1) < offsetY && offsetY < (STEP + 1))) && dirPriority) {

                if (offsetY < 0) {
                    if (!moveUp(STEP)) {
                        if (!moveLeftOrRight(STEP)) {
                            if (!moveRightOrLeft(STEP)) {
                                switchSizeRatio();
                                if (!moveDown(STEP)) {
                                    //moveMe = false;
                                }
                            } else {
                                if (!((-(STEP + 1) < offsetX && offsetX < (STEP + 1)))) {
                                    dirPriority = !dirPriority;
//                                    //System.out.println("$$$$$$$$$$$$$$$$");
                                    detectRepeats();

                                } else {
                                    //moveMe = false;
                                }
                            }

                        }
                    }

                } else {
                    if (!moveDown(STEP)) {
                        if (!moveLeftOrRight(STEP)) {
                            if (!moveRightOrLeft(STEP)) {
                                switchSizeRatio();
                                if (!moveUp(STEP)) {
                                    //moveMe = false;
                                }
                            } else {
                                if (!((-(STEP + 1) < offsetX && offsetX < (STEP + 1)))) {
                                    dirPriority = !dirPriority;
//                                    //System.out.println("$$$$$$$$$$$$$$$$");
                                    detectRepeats();

                                } else {
                                    //moveMe = false;
                                }
                            }
                        }
                    }
                }
            }


            if ((-(STEP + 1) < offsetX && offsetX < (STEP + 1)) && !dirPriority) {
                dirPriority = true;
            } else if ((-(STEP + 1) < offsetY && offsetY < (STEP + 1)) && dirPriority) {
                dirPriority = false;
            }


        }

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
        unit.setLastUnitFiredMe(this);
        if(lastUnitFiredMe!=null){
            if(Math.abs(lastUnitFiredMe.getX()-getX())<unitBase.getFireRange() &&
                    Math.abs(lastUnitFiredMe.getY()-getY())<unitBase.getFireRange()){
                unit=lastUnitFiredMe;
            }
        }
        if (!turnToDirection(unit.getX(), unit.getY())) {
            return;
        }
        playFireSound();
        MovingAnimation ma1 = getFireByUnit(environment, getX(), getY(), unit.getX(), unit.getY());
//        Animation a= new SmallExplosion(unit.getX(),unit.getY(),environment);
//        environment.addExplosion(a);
        environment.addFire(ma1);
    }

    public void fire(IBuilding building) {
        if (!turnToDirection(building.getX(), building.getY())) {
            return;
        }
        playFireSound();
        MovingAnimation ma1 = getFireByUnit(environment, getX(), getY(), building.getX(), building.getY());
        environment.addFire(ma1);
    }

    public void fire() {
        UnitManager um = environment.getUm();


        int k = unitBase.getFireRange();


        if(lastUnitFiredMe!=null && lastFiredUnitUpdated){
            registerMove(lastUnitFiredMe.getX(),lastUnitFiredMe.getY());
            lastFiredUnitUpdated=false;
        }


        IUnit u1 = um.getRandomEnemyUnitInRange(getX() - k, getY() - k, getX() + k, getY() + k, this);
        //Hashtable htx = um.getAllyUnitsInRange(getX() - k, getY() - k, getX() + k, getY() + k, this);
//        //System.out.println("enamy " + u1);
//        Enumeration enx = htx.keys();
//        while (enx.hasMoreElements()) {
//            IUnit ux = (IUnit) htx.get(enx.nextElement());
//            if (Math.abs(ux.getX() - getX()) < getSizeX() && Math.abs(ux.getY() - getY()) < getSizeY()) {
//                if (isAllowedCell(getX() + 20, getY() + 20)) {
//                    registerMove(getX() + 20, getY() + 20);
//                }
//            }
//        }


        if (u1 != null) {
            BaseUnit ux = (BaseUnit) u1;
            if (getFirePossible(getBase().getType(), ux.getBase().getType())) {
                pause();
                engaged = true;
                fire(u1);
            }
        } else {
            IBuilding b = um.getFirstBuildingInRange(getX() - k, getY() - k, getX() + k, getY() + k, this);
            ////System.out.println("building "+b);
            if (b != null) {
                pause();
                engaged = true;
                fire(b);

            } else {
                engaged = false;
                resume();
            }
        }
    }


    private boolean getFirePossible(String myType, String enemyType) {
        if (enemyType.equals(JCageConfigurator.UNIT_AIR_A) || enemyType.equals(JCageConfigurator.UNIT_AIR_B)) {
            if (myType.equals(JCageConfigurator.UNIT_GROUND_HUMAN_A) || myType.equals(JCageConfigurator.UNIT_AIR_A) || myType.equals(JCageConfigurator.UNIT_GROUND_WATER_A))
            {
                return true;
            }
            return false;
        }
        return true;
    }

    private MovingAnimation getFireByUnit(Environment en, int x, int y, int ox, int oy) {
        String type = getBase().getType();
        if (type.equals(JCageConfigurator.UNIT_AIR_A) ||
                type.equals(JCageConfigurator.UNIT_GROUND_SHALLOWWATER_A) ||
                type.equals(JCageConfigurator.UNIT_GROUND_WATER_A) ||
                type.equals(JCageConfigurator.UNIT_GROUND_HUMAN_A) ||
                type.equals(JCageConfigurator.UNIT_GROUND_HUMAN_C)) {

            return new SmallFire(en, x, y, ox, oy);

        }
        return new BigFire(en, x, y, ox, oy);
    }


    public void stop() {
        moveMe = false;
        regMoveChanged = false;
        lastMoveStatus = moveMe;

    }

    public void pause() {
        lastMoveStatus = moveMe;
        moveMe = false;
    }

    public void resume() {
        moveMe = lastMoveStatus;
    }

    public Unit getBase() {
        return getUnitBase();
    }

    public Sprite getSprite() {
        return getPic();
    }

    public void registerMove(int x, int y) {
        stop();
        //System.out.println("registering move");
        moveToX = x;
        moveToY = y;
        moveMe = true;
        lastMoveStatus = moveMe;
//        if(System.currentTimeMillis() - lastMoveChangedPlayTime >5000){
//            playMoveSound();
//            lastMoveChangedPlayTime = System.currentTimeMillis();
//        }


    }


    private void faceLeft() {
        getSprite().setFrame(9);
    }

    private void faceRight() {
        getSprite().setFrame(3);
    }

    private void faceUp() {
        getSprite().setFrame(6);
    }

    private void faceDown() {
        getSprite().setFrame(0);
    }


    public void fly(int timerval) {
        ////System.out.println("flying");
        //int k = getCurrentDirection();
        ////System.out.println("dir :"+k);
        //if (k == 1) {
        pushUp();
        //} else if (k == 2) {
        pushRight();
        //} else if (k == 3) {
        pushLeft();
        //} else {
        pushDown();
        //}
        //if (timerval % 25 == 0)
            playFlySound();
    }


    private void pushLeft() {
        if (getSprite().getFrame() == 9 || getSprite().getFrame() == 10) {
            getSprite().nextFrame();
        } else if (getSprite().getFrame() == 11) {
            getSprite().setFrame(9);
        }
    }

    private void pushRight() {
        if (getSprite().getFrame() == 3 || getSprite().getFrame() == 4) {
            getSprite().nextFrame();
        } else if (getSprite().getFrame() == 5) {
            getSprite().setFrame(3);
        }
    }

    private void pushUp() {
        if (getSprite().getFrame() == 6 || getSprite().getFrame() == 7) {
            getSprite().nextFrame();
        } else if (getSprite().getFrame() == 8) {
            getSprite().setFrame(6);
        }
    }

    private void pushDown() {
        if (getSprite().getFrame() == 0 || getSprite().getFrame() == 1) {
            getSprite().nextFrame();
        } else if (getSprite().getFrame() == 2) {
            getSprite().setFrame(0);
        }
    }

    public int getCurrentDirection() {
        if (getSprite().getFrame() >= 0 && getSprite().getFrame() < 3) {
            return 3;
        } else if (getSprite().getFrame() >= 3 && getSprite().getFrame() < 6) {
            return 2;
        } else if (getSprite().getFrame() >= 6 && getSprite().getFrame() < 9) {
            return 1;
        } else {
            return 4;
        }
    }

    private boolean turnLeft() {
        if (getCurrentDirection() == 4) {
            return true;
        } else if (getCurrentDirection() == 3) {
            faceLeft();
            return false;
        } else if (getCurrentDirection() == 2) {
            faceDown();
            return false;
        } else if (getCurrentDirection() == 1) {
            faceLeft();
            return false;
        }
        return false;
    }

    private boolean turnRight() {
        if (getCurrentDirection() == 2) {
            return true;
        } else if (getCurrentDirection() == 3) {
            faceRight();
            return false;
        } else if (getCurrentDirection() == 4) {
            faceUp();
            return false;
        } else if (getCurrentDirection() == 1) {
            faceRight();
            return false;
        }
        return false;
    }

    private boolean turnUp() {
        if (getCurrentDirection() == 1) {
            return true;
        } else if (getCurrentDirection() == 2) {
            faceUp();
            return false;
        } else if (getCurrentDirection() == 3) {
            faceLeft();
            return false;
        } else if (getCurrentDirection() == 4) {
            faceUp();
            return false;
        }
        return false;
    }

    private boolean turnDown() {
        if (getCurrentDirection() == 3) {
            return true;
        } else if (getCurrentDirection() == 4) {
            faceDown();
            return false;
        } else if (getCurrentDirection() == 1) {
            faceLeft();
            return false;
        } else if (getCurrentDirection() == 2) {
            faceDown();
            return false;
        }
        return false;
    }

    private boolean moveLeft(int distance) {
        if (isAllowedCell(-(distance), 0)) {
            if (!turnLeft()) return true;
//            while (!turnLeft()) {
//            }
            pushLeft();
            setX(getX() - distance);
//            //System.out.println("unit moved left");
//            //System.out.println(getX() + " " + getY());
//            //System.out.println(moveToX + " " + moveToY);
            movesFromLastTurn++;
//            playMoveSound();
            lastMovedTime=System.currentTimeMillis();
            return true;
        }
//        //System.out.println("cant move left");
        return false;
    }

    private boolean moveRight(int distance) {
        if (isAllowedCell(distance, 0)) {
            if (!turnRight()) return true;
//            while (!turnRight()) {
//            }
            pushRight();
            setX(getX() + distance);
            //System.out.println("unit moved right");
//            //System.out.println(getX() + " " + getY());
//            //System.out.println(moveToX + " " + moveToY);
            movesFromLastTurn++;
//            playMoveSound();
            lastMovedTime=System.currentTimeMillis();
            return true;
        }
        //System.out.println("cant move right");
        return false;
    }

    private boolean moveUp(int distance) {
        if (isAllowedCell(0, -(distance))) {
            if (!turnUp()) return true;
//            while (!turnUp()) {
//            }
            pushUp();
            setY(getY() - distance);
//            System.out.println("unit moved up");
//            //System.out.println(getX() + " " + getY());
//            //System.out.println(moveToX + " " + moveToY);
            movesFromLastTurn++;
//            playMoveSound();
            lastMovedTime=System.currentTimeMillis();
            return true;
        }
//        System.out.println("cant move up");
        return false;
    }

    private boolean moveDown(int distance) {
        if (isAllowedCell(0, distance)) {
            if (!turnDown()) return true;
//            while (!turnDown()) {
//            }
            pushDown();
            setY(getY() + distance);
//            System.out.println("unit moved down");
//            //System.out.println(getX() + " " + getY());
//            //System.out.println(moveToX + " " + moveToY);
            movesFromLastTurn++;
//            playMoveSound();
            lastMovedTime=System.currentTimeMillis();
            return true;
        }
//        System.out.println("cant move down");
        return false;
    }

    private void changeRegistredMove() {
        if (!regMoveChanged) {
            originalRegX = moveToX;
            originalRegY = moveToY;
        }
        int t = Math.abs(rand.nextInt()) % 400;
        int k = t;
        int x1 = getX() + k;
        int y1 = getY() + k;
        int x2 = getX() - k;
        int y2 = getY() - k;
        for (int h = 0; h < 20; h++) {
            if (isAllowedCell(0, 0, x1, y1)) {
                moveToX = x1;
                moveToY = y1;
                regMoveChanged = true;
                return;
            }
            if (isAllowedCell(0, 0, x1, y2)) {
                moveToX = x1;
                moveToY = y2;
                regMoveChanged = true;
                return;

            }
            if (isAllowedCell(0, 0, x2, y1)) {
                moveToX = x2;
                moveToY = y1;
                regMoveChanged = true;
                return;

            }
            if (isAllowedCell(0, 0, x2, y2)) {
                moveToX = x2;
                moveToY = y2;
                regMoveChanged = true;
                return;

            }
            k = Math.abs(rand.nextInt()) % 300;
            x1 = getX() + k;
            y1 = getY() + k;
            x2 = getX() - k;
            y2 = getY() - k;
        }

    }

    private void changeRegistredMove(int deviation) {
        if (!regMoveChanged) {
            originalRegX = moveToX;
            originalRegY = moveToY;
        }
        int t = Math.abs(rand.nextInt()) % deviation;
        int k = t;
        int x1 = getX() + k;
        int y1 = getY() + k;
        int x2 = getX() - k;
        int y2 = getY() - k;
        for (int h = 0; h < 20; h++) {
            if (isAllowedCell(0, 0, x1, y1)) {
                moveToX = x1;
                moveToY = y1;
                regMoveChanged = true;
                return;
            }
            if (isAllowedCell(0, 0, x1, y2)) {
                moveToX = x1;
                moveToY = y2;
                regMoveChanged = true;
                return;

            }
            if (isAllowedCell(0, 0, x2, y1)) {
                moveToX = x2;
                moveToY = y1;
                regMoveChanged = true;
                return;

            }
            if (isAllowedCell(0, 0, x2, y2)) {
                moveToX = x2;
                moveToY = y2;
                regMoveChanged = true;
                return;

            }
            k = Math.abs(rand.nextInt()) % deviation;
            x1 = getX() + k;
            y1 = getY() + k;
            x2 = getX() - k;
            y2 = getY() - k;
        }

    }

    boolean turnToDirection(int x, int y) {
        int k = findDirection(x, y);
        if (k == 1) {
            return turnUp();
        } else if (k == 2) {
            return turnRight();
        } else if (k == 3) {
            return turnDown();
        } else {
            return turnLeft();
        }
    }

    public void moveAway() {
        //forceMove=true;
        changeRegistredMove(100);
    }

    private void callForHelp() {
        Hashtable ht = environment.getUm().getAllyUnitsInRange(0, 0,
                GroundCell.W * Ground.width, GroundCell.H * Ground.height, this);
        Enumeration e = ht.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IUnit bu = (IUnit) ht.get(key);
            if (!bu.getEngagedInFire()) {
                moveToMe(bu);
            }
        }
    }


    public int getDistance(IUnit u) {
        int x = getX() - u.getX();
        int y = getY() - u.getY();
        return x * x + y * y;
    }


    public void moveToMe(IUnit u) {
        u.registerMove(getX() + getSizeX(), getY() + getSizeY());
    }

    public boolean getEngagedInFire() {
        return engaged;
    }

    public boolean isEngagedRescure() {
        return engagedRescure;
    }

    public void followUnit(IUnit u) {
        if (unitToFollow == null) {
            unitToFollow = u;
            System.out.println("my unit to follow set :" + getUnitId());
            moveMe = true;
        }
    }

    private void refreshFollowing() {
//        System.out.println("refreashing unit to follow.......");
        if (unitToFollow != null) {
            if (unitToFollow.getStrength() <= 0) {
                unitToFollow = null;
            } else {
                System.out.println("registering move.... following");
                registerMove(unitToFollow.getX(), unitToFollow.getY());
            }
        }
    }

    public void distroy() {
        Hashtable ht = environment.getUm().getAllUnitsInRange(0, 0,
                GroundCell.W * Ground.width, GroundCell.H * Ground.height, this);
        Enumeration e = ht.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            IUnit bu = (IUnit) ht.get(key);
            bu.dontFollow(this);
            if(bu.getLastUnitFiredMe()==this){
                bu.setLastUnitFiredMe(null);
            }
        }

        IUnit u=environment.getUm().getRandomEnemyUnitInRange(ht);
        if(u!=null){
            if(getOwener().equals(MainMid.getClientConfigurator().user )){
                //JCageConfigurator.updateScore(u.getOwener(),Integer.parseInt(getBase().getStrength()));
            }
            else{
                //JCageConfigurator.updateScore(JCageConfigurator.mymap.getName(),Integer.parseInt(getBase().getStrength()));
            }
        }
    }

    public void dontFollow(IUnit u) {
        if (unitToFollow == u) {
            unitToFollow = null;
            engaged = false;
            stop();
        }
    }

    public void suspendFollow() {
        unitToFollow = null;
    }

    public void think(int timerVal) {

        if (!thinkTimer(timerVal)) {
            return;
        }


        Hashtable ht2 = environment.getUm().getEnemyUnitsInRange(getX() - unitBase.getFireRange() * 2, getY() - unitBase.getFireRange() * 2,
                getX() + unitBase.getFireRange() * 2, getY() + unitBase.getFireRange() * 2, this);


        if (getOwener().equals(MainMid.getClientConfigurator().user)) {     //handle my units

//            if (!moveMe) {
//
//                if (ht2.size() == 0) {
//                    System.out.println(getUnitId() + " :im stopping");
//                    moveMe = false;
//                    engaged = false;
//                    return;
//                } else {
//                    System.out.println(getUnitId() + " :found enemy units: " + ht2.size());
//                }
//
//            }


        } else { //enemy AI

            Hashtable ht1 = environment.getUm().getAllyUnitsInRange(getX() - unitBase.getFireRange() * 2, getY() - unitBase.getFireRange() * 2,
                    getX() + unitBase.getFireRange() * 2, getY() + unitBase.getFireRange() * 2, this);


            IUnit u = environment.getUm().getRandomEnemyUnitInRange(ht2);
            IBuilding bu = environment.getUm().getRandomEnemyBuildingInRange(getX() - unitBase.getFireRange() * 2, getY() - unitBase.getFireRange() * 2,
                    getX() + unitBase.getFireRange() * 2, getY() + unitBase.getFireRange() * 2, this);
            if (u != null && ht1.size()+1 >= ht2.size()) {
                System.out.println(getUnitId() + " :im going to follow :" + u.getBase().getId());
                followUnit(u);
                return;
            } else if (bu != null) {
                int y = rand.nextInt() % 2;
                if (y == 0) y = 1;
                registerMove(bu.getX() + y * getBase().getFireRange() / 2, bu.getY() + y * getBase().getFireRange() / 2);
            }


            if (ht1.size()*3 < ht2.size()) {
                System.out.println(getUnitId() + " :im calling for help");
                callForHelp();
                return;
            }


            if (!engaged && (System.currentTimeMillis() - lastMovedTime > 60000)) {
//            long x=(Math.abs(rand.nextInt())  % ((GroundCell.W * Ground.width)/5))-((GroundCell.W * Ground.width)/10);
                long x = Math.abs(rand.nextInt()) % (GroundCell.W * Ground.width-40)+20;
//            long y=(Math.abs(rand.nextInt())  % ((GroundCell.H * Ground.height)/5))-((GroundCell.W * Ground.width)/10);
                long y = Math.abs(rand.nextInt()) % (GroundCell.H * Ground.height-40)+20;
                int x1 = (int) x;
                int y1 = (int) y;
                if (isAllowedCell(x1, y1)) {
                    System.out.println(getUnitId() + " :im moving to " + x1 + " " + y1);
                    registerMove(x1, y1);

                }
            }
        }

    }


    public void playMoveSound() {
        if (isInBounds()) {
            String k = getBase().getType();
            if (k.equals(JCageConfigurator.UNIT_AIR_A)) {
                SoundSynthesizer.getInstance().playMoveHeliSound();
            } else if (k.equals(JCageConfigurator.UNIT_AIR_B)) {
                SoundSynthesizer.getInstance().playMovePlaneSound();
            } else
            if (k.equals(JCageConfigurator.UNIT_GROUND_HUMAN_A) || k.equals(JCageConfigurator.UNIT_GROUND_HUMAN_B) || k.equals(JCageConfigurator.UNIT_GROUND_HUMAN_C))
            {
                SoundSynthesizer.getInstance().playMoveManSound();
            } else {
                truckMove=!truckMove;
                if(truckMove){
                    SoundSynthesizer.getInstance().playMoveTruckSound();
                }
                else{
                    SoundSynthesizer.getInstance().playMoveTruckSound2();

                }
            }
        }
    }

    public void playFireSound() {
        if (isInBounds()) {
            String k = getBase().getType();
            if(k.equals(JCageConfigurator.UNIT_GROUND_B) && k.equals(JCageConfigurator.UNIT_GROUND_SHALLOWWATER_A) && k.equals(JCageConfigurator.UNIT_AIR_B))
                SoundSynthesizer.getInstance().playMachineFireSound();
            else
                SoundSynthesizer.getInstance().playHumanFireSound();

        }
    }

    public void playFlySound() {
        if (isInBounds()) {
            String k = getBase().getType();
            if (k.equals(JCageConfigurator.UNIT_AIR_A)) {
                SoundSynthesizer.getInstance().playMoveHeliSound();
            }
        }
    }

    public IUnit getLastUnitFiredMe() {
        return lastUnitFiredMe;
    }

    public void setLastUnitFiredMe(IUnit lastUnitFiredMe) {
        if(this.lastUnitFiredMe==null && this.lastUnitFiredMe!=lastUnitFiredMe){
            this.lastUnitFiredMe = lastUnitFiredMe;
            this.lastFiredUnitUpdated=true;
        }

        if(lastUnitFiredMe==null){
            this.lastUnitFiredMe=null;
        }

    }

}
