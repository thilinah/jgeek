// package dev.di.forms;

//import de.enough.polish.util.Locale;
//import dev.di.data.dao.Building;
//import dev.di.data.dao.Resource;
//import dev.di.main.JCageConfigurator;
//import dev.di.main.MainMid;
//import dev.di.util.StringUtil;
//import dev.di.engine.GameLogic;

import javax.microedition.lcdui.CommandListener;  					import javax.microedition.lcdui.AlertType; 					import javax.microedition.lcdui.Canvas; 					import javax.microedition.lcdui.Command; 					import javax.microedition.lcdui.Display; 					import javax.microedition.lcdui.Displayable; 					import javax.microedition.lcdui.Font; 					import javax.microedition.lcdui.Graphics; 					import javax.microedition.lcdui.Image;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 5, 2007
 * Time: 3:30:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormResourcesBuilding extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdBack = new Command("Ok", Command.BACK, 1);
    Command cmdUpgrade = new Command("Upgrade", Command.OK, 1);
    Building building = null;
    public List menuScreen = null;

    public FormResourcesBuilding(String s, ChildForm parent, Building building) {
        super(s);
        this.parent = parent;

        //#style mainScreenF
        this.menuScreen = new List(building.getId(), List.IMPLICIT, StyleSheet.mainscreenfStyle );


        this.building = building;
        //Create resource string
        String resString = "";
        if (building.getType().equals(JCageConfigurator.BUILDING_RESOURCE)) {

            Enumeration e1 = building.getResourceMatrix().keys();
            while (e1.hasMoreElements()) {
                String key = String.valueOf(e1.nextElement());
                resString += "  " + key + " : +" + Integer.parseInt(building.getResourceMatrix().get(key).toString()) * JCageConfigurator.resourceProductionFactor + "\n";

                //System.out.println(resString);
            }


            if (!resString.equals("")) {
                resString = "Generates:" + "\n" + resString;

                //System.out.println(resString);
            }
        }

        //=======================

        //Create resource string
        String needString = "";
        Enumeration e1 = building.getResourceMatrix().keys();
        while (e1.hasMoreElements()) {
            String key = String.valueOf(e1.nextElement());
            needString += "  " + key + " : +" + Integer.parseInt(building.getResourceMatrix().get(key).toString()) * JCageConfigurator.resourceNeedMultiplicationAmountBuilding + "\n";

            //System.out.println(resString);
        }


        if (!needString.equals("")) {
            needString = "Need:" + "\n" + needString;

            //System.out.println(resString);
        }

        //=====================


        String upString = "";
        Enumeration e2 = building.getLevelMatrix().keys();
        while (e2.hasMoreElements()) {
            String key = String.valueOf(e2.nextElement());
            upString += "  " + key + " : " + building.getLevelMatrix().get(key).toString() + "\n";

            //System.out.println(upString);
        }

        if (!upString.equals("")) {
            upString = "Upgrade to next level:" + "\n" + upString;

        }

        //#style mainCommandF
        menuScreen.append("", MainMid.getImage("/" + building.getId() + "_a.png"), StyleSheet.maincommandfStyle );

        if (building.getDescription() != null && !building.getDescription().equals("")) {
            //#style mainCommandF
            menuScreen.append(StringUtil.breakString(building.getDescription(), 0, "*"), null, StyleSheet.maincommandfStyle );
        }
        if (!resString.equals("")) {
            //#style mainCommandF
            menuScreen.append(resString, null, StyleSheet.maincommandfStyle );
        }
        if (!needString.equals("")) {
            //#style mainCommandF
            menuScreen.append(needString, null, StyleSheet.maincommandfStyle );
        }
        if (!upString.equals("")) {
            //#style mainCommandF
            menuScreen.append(upString, null, StyleSheet.maincommandfStyle );
        }

        //#style mainCommandF

        menuScreen.append("Level" + " " + JCageConfigurator.currentProxy.getBuildingLevels().get(building.getId()), null, StyleSheet.maincommandfStyle );


        menuScreen.addCommand(cmdBack);
        menuScreen.addCommand(cmdUpgrade);
        menuScreen.setCommandListener(this);
    }


    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();
        } else if (command == cmdUpgrade) {
            Building b = this.building;
            int xr = GameLogic.getInstance().upgradeBuilding(b);

            if (xr == 0) {

            } else if (xr == -1) {
                FormAlert f = new FormAlert("No enough resources to upgrade the building level", FormAlert.WARNING, this.getParent());
                MainMid.getClientConfigurator().display(f);

            } else if (xr == -2) {
                FormAlert f = new FormAlert("Can not upgrade the building. Maximum level reached", FormAlert.WARNING, this.getParent());
                MainMid.getClientConfigurator().display(f);

            }

        }
    }

    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        return menuScreen;
    }


}

//History
// upgrade building button removed
