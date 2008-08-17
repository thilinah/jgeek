package dev.di.map;

import dev.di.main.JCageConfigurator;
import dev.di.main.MainMid;
import dev.di.map.buildings.BaseBuildingService;
import dev.di.map.buildings.IBuilding;
import dev.di.map.buildings.BaseBuilding;
import dev.di.map.units.IUnit;
import dev.di.map.units.BaseUnit;
import dev.di.map.units.DynamicUnit;
import dev.di.map.units.BaseUnitService;
import dev.di.forms.FormBuildingUnits;
import dev.di.forms.FormBaseResourceBuilding;

import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Form;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 21, 2008
 * Time: 5:42:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class CurserManager {
    private final int MAX_CURSER_SPEED = 15;
    private final int MIN_CURSER_SPEED = 4;
    public static Sprite regulerCurser = null;
    Environment en;
    int curserSpeed = 4;
    int height = 0;
    int width = 0;
    private IUnit selectedUnit = null;
    UnitManager um = null;
    GameProxy gp=null;

    public CurserManager(Environment en, int height, int width,GameProxy gp) {
        this.en = en;
        this.height = height;
        this.width = width;
        this.gp=gp;
    }

    public int getCurserSpeed() {
        return curserSpeed;
    }

    public void setCurserSpeed(int curserSpeed) {
        this.curserSpeed = curserSpeed;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public IUnit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(IUnit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void setUm(UnitManager um) {
        this.um = um;
    }


    public int getX() {
        return en.getCurser().getX() + 10;
    }

    public int getY() {
        return en.getCurser().getY() + 10;
    }


    public void moveUp() {
        if (getY() > curserSpeed) {
            en.getCurser().move(0, -curserSpeed);
        } else if (en.getStartPointY() > curserSpeed) {
            en.setStartPoint(en.getStartPointX(), en.getStartPointY() - curserSpeed);
        }
        changeMoveCurser();

    }

    public void moveDown() {
        if (getY() < getHeight() - curserSpeed) {
            en.getCurser().move(0, curserSpeed);
        } else if (en.getStartPointY() < en.getSizeY() - getHeight() - curserSpeed) {
            en.setStartPoint(en.getStartPointX(), en.getStartPointY() + curserSpeed);
        }
        changeMoveCurser();

    }

    public void moveLeft() {
        if (getX() > curserSpeed) {
            en.getCurser().move(-curserSpeed, 0);
        } else if (en.getStartPointX() > curserSpeed) {
            en.setStartPoint(en.getStartPointX() - curserSpeed, en.getStartPointY());
        }
        changeMoveCurser();

    }

    public void moveRight() {
        if (getX() < getWidth() - curserSpeed) {
            en.getCurser().move(curserSpeed, 0);
        } else if (en.getStartPointX() < en.getSizeX() - getWidth() - curserSpeed) {
            en.setStartPoint(en.getStartPointX() + curserSpeed, en.getStartPointY());
        }
        changeMoveCurser();
    }

    public void moveScreenUp() {
        if (en.getStartPointY() > curserSpeed) {
            en.setStartPoint(en.getStartPointX(), en.getStartPointY() - curserSpeed);
        }
        changeMoveCurser();

    }

    public void moveScreenDown() {
        if (en.getStartPointY() < en.getSizeY() - getHeight() - curserSpeed) {
            en.setStartPoint(en.getStartPointX(), en.getStartPointY() + curserSpeed);
        }
        changeMoveCurser();

    }

    public void moveScreenLeft() {
        if (en.getStartPointX() > curserSpeed) {
            en.setStartPoint(en.getStartPointX() - curserSpeed, en.getStartPointY());
        }
        changeMoveCurser();

    }

    public void moveScreenRight() {
        if (en.getStartPointX() < en.getSizeX() - getWidth() - curserSpeed) {
            en.setStartPoint(en.getStartPointX() + curserSpeed, en.getStartPointY());
        }
        changeMoveCurser();
    }

    public void rightClicked() {
        if (um.isUnitSelected()) {
            if (en.getCurser().getFrame() == 3) {//curser is on an enemy unit or building
                //todo attack
            } else {
                um.getLastSelectedUnit().registerMove(getX() + en.getStartPointX(),
                        getY() + en.getStartPointY());
            }
        }
    }

    public void delete() {
        if (um.isBuildingSelected()) {
            IBuilding b = um.getLastSelectedBuilding();
            try {
                BaseBuilding bx = (BaseBuilding) b;
                BaseBuildingService.getInstance().delete(bx);
                BigExplosion be = new BigExplosion(b.getX(), b.getY(), en);
                en.addExplosion(be);
                en.buildings.remove(bx.getBuildingId());

            } catch (Exception e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else if (um.isUnitSelected()) {
            IUnit b = um.getLastSelectedUnit();
            try {
                DynamicUnit bx = (DynamicUnit) b;
                BaseUnitService.getInstance().delete(bx);
                BigExplosion be = new BigExplosion(b.getX(), b.getY(), en);
                en.addExplosion(be);
                en.units.remove(bx.getUnitId());

            } catch (Exception e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    public void clicked(PlanetMap parent) {
        IBuilding bu = um.getBuildingAtLocation(getX() + en.getStartPointX(), getY() + en.getStartPointY());
        IUnit u = um.getUnitAtLocation(getX() + en.getStartPointX(),
                getY() + en.getStartPointY());
        if (JCageConfigurator.currentSelectedBuilding == null) {

            if (um.isUnitSelected()) {
                if (en.getCurser().getFrame() == 3) {//curser is on an enemy unit or building
                    if (u != null) {
                        u.suspendFollow();
                        um.getLastSelectedUnit().followUnit(u);
                        um.getLastSelectedUnit().playMoveSound();
                    }
                } else if (en.getCurser().getFrame() == 1) {//cursor is not on an enemy unit
                    if (u != null) {//cursor is on allay unit.. so select it
                        um.setUnitSelected(false);
                        u = um.getUnitAtLocation(getX() + en.getStartPointX(),
                                getY() + en.getStartPointY());
                        um.setUnitSelected(true);

                    } else {
                        um.getLastSelectedUnit().suspendFollow();
                        um.getLastSelectedUnit().registerMove(getX() + en.getStartPointX(),
                                getY() + en.getStartPointY());
                        um.getLastSelectedUnit().playMoveSound();

                    }
                }
            }




            if (bu != null) {
                if (um.isBuildingSelected()) {
                    if (bu == um.getLastSelectedBuilding() && !bu.isStillCreating())
                    { //if an already selected builing is clicked show unit screen
                        if (bu.getBase().getType().equals(JCageConfigurator.BUILDING_UNIT)) {
                            JCageConfigurator.currentSelectedBaseBuilding = (BaseBuilding) bu;
                            FormBuildingUnits f = new FormBuildingUnits("map", parent, bu.getBase(), false,gp);
                            MainMid.getClientConfigurator().display(f);
                        }
                        else if(bu.getBase().getType().equals(JCageConfigurator.BUILDING_RESOURCE )){
                            FormBaseResourceBuilding f=new FormBaseResourceBuilding("",parent,(BaseBuilding)bu);
                            MainMid.getClientConfigurator().display(f);
                        }
                    } else {
                        um.setBuildingSelected(false);
                        um.getBuildingAtLocation(getX() + en.getStartPointX(), getY() + en.getStartPointY());
                        um.setBuildingSelected(true);
                    }

                }
                if (bu.getOwener().equals(MainMid.getClientConfigurator().user)) {
                    um.setBuildingSelected(true);
                }
            }

            if (u != null && u.getOwener().equals(MainMid.getClientConfigurator().user)) {
                um.setUnitSelected(true);
            }

        } else {

            JCageConfigurator.currentSelectedBuilding = null;

            if (en.getCurser().getFrame() == 3) {
                um.getBuildingToBuild().setX(getX() + en.getStartPointX());
                um.getBuildingToBuild().setY(getY() + en.getStartPointY());
                um.getBuildingToBuild().updateResourceMatrix();
                try {
                    BaseBuildingService.getInstance().insert(um.getBuildingToBuild());
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                en.buildings.put(um.getBuildingToBuild().getBuildingId(), um.getBuildingToBuild());
                //System.out.println("Building add to buildings ht");
                um.setBuildingToBuild(null);

            }
            else if(en.getCurser().getFrame() == 1){

            }


        }
//        Animation b=new SmallExplosion(getX() + en.getStartPointX(),
//                getY() + en.getStartPointY(),en);
//        Animation b1=new MovingTree1(getX() + en.getStartPointX(),
//                getY() + en.getStartPointY());
//        Animation b2=new MovingTree2(getStartX()+ 50 + en.getStartPointX(),
//                getStartY() + en.getStartPointY()+50);
//        en.addTree(b1);
//        en.addTree(b2);
//        en.addExplosion(b);

    }

    public void cancel() {
        if (um.isUnitSelected()) {
            um.setUnitSelected(false);
            um.setLastSelectedUnit(null);
        }
        if (um.isBuildingSelected()) {
            um.setBuildingSelected(false);
            um.setLastSelectedBuilding(null);
        }
    }

    private void changeMoveCurser() {
        IUnit u = um.getUnitAtLocation(getX() + en.getStartPointX(), getY() + en.getStartPointY());
        IBuilding bu = um.getBuildingAtLocation(getX() + en.getStartPointX(), getY() + en.getStartPointY());
        um.setShowInfo(false);
        ////System.out.println("Selected building :" + bu);
        if (JCageConfigurator.currentSelectedBuilding == null) {

            if (u != null) {
                if (!um.isUnitSelected()) { //unit is not selected and curser is  on another unit
                    en.getCurser().setFrame(1);
                } else { //unit is  selected and curser is  on another unit
                    if (u.getOwener().equals(MainMid.getClientConfigurator().user))
                    {  //if curser is on a unit of same team
                        en.getCurser().setFrame(1);
                    } else {
                        en.getCurser().setFrame(3);
                    }
                }


            } else if (bu != null) {

                if (bu.getOwener().equals(MainMid.getClientConfigurator().user)) {
                    en.getCurser().setFrame(1);
                } else {
                    en.getCurser().setFrame(3);
                }

            } else {  //curser is not on another unit
                if (um.isUnitSelected()) {
                    en.getCurser().setFrame(1);
                } else {
                    en.getCurser().setFrame(0);
                }
            }
        } else {

            System.out.println("cx "+ (getX() -10  + en.getStartPointX()));
            System.out.println("cy "+ (getY() -10  + en.getStartPointY()));
            if (um.getBuildingToBuild().isAllowedCell(0,0,getX() -10  + en.getStartPointX(), getY() -10 + en.getStartPointY())) {
                en.getCurser().setFrame(3);
            } else {
                en.getCurser().setFrame(2);

            }

            if (en.getCurser().getFrame() != 2) {

                if (u != null) {
                    en.getCurser().setFrame(2);
                } else {
                    en.getCurser().setFrame(3);
                }
            }
        }
    }

    public void incCurserSpeed
            () {
        if (curserSpeed < MAX_CURSER_SPEED) {
            curserSpeed++;
        }
    }

    public void resetCurserSpeed
            () {
        curserSpeed = MIN_CURSER_SPEED;
    }


}
