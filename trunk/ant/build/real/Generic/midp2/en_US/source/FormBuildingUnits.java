// package dev.di.forms;

//import de.enough.polish.util.Locale;
//import dev.di.data.dao.Building;
//import dev.di.data.dao.Unit;
//import dev.di.engine.GameLogic;
//import dev.di.main.JCageConfigurator;
//import dev.di.main.MainMid;
//import dev.di.map.GameProxy;

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
public class FormBuildingUnits extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Building building = null;
    Command cmdBack = new Command("Back", Command.BACK, 1);
    boolean select;
    //custom commands
    Hashtable ht = null;
    Command cmdDetails = new Command("More", Command.OK, 1);
    public List menuScreen = null;
    String fromMap = "";//="map" when called from a map

    public FormBuildingUnits(String fromMap, ChildForm parent, Building building, boolean select,GameProxy gp) {
        super(fromMap);

        this.fromMap = fromMap;
        this.select = select;
        this.parent = parent;
        //#style mainScreenBE
        this.menuScreen = new List("Units", List.IMPLICIT, StyleSheet.mainscreenbeStyle );
        this.building = building;
        updateScreen(gp);


        menuScreen.addCommand(cmdBack);
        if (!select) {
            if (fromMap.equals("map"))
                menuScreen.addCommand(cmdDetails);
        }
        menuScreen.setCommandListener(this);
    }

    public void updateScreen(GameProxy gp) {
        menuScreen.deleteAll();
        Enumeration en = building.getUnitLevels().keys();
        ht = new Hashtable();
        while (en.hasMoreElements()) {
            Object unitId = en.nextElement();
            System.out.println(unitId);
            System.out.println(Integer.parseInt((String.valueOf(gp.getBuildingLevels().get(building.getId())))));
//            if (Integer.parseInt(String.valueOf(building.getUnitLevels().get(unitId))) <= Integer.parseInt(building.getLevel())) {
            if (Integer.parseInt(String.valueOf(building.getUnitLevels().get(unitId))) <= Integer.parseInt((String.valueOf(gp.getBuildingLevels().get(building.getId()))))) {
                Unit u = JCageConfigurator.getUnitById(String.valueOf(unitId));
                System.out.println(u);
                System.out.println(u.getId());
                int xk = 0;
                System.out.println("$$$$$$$");
                if (JCageConfigurator.currentProxy != null && JCageConfigurator.currentProxy.getUnitAmounts().get(u.getId()) != null) {
                    xk = Integer.parseInt(String.valueOf(JCageConfigurator.currentProxy.getUnitAmounts().get(u.getId())));
                }
                System.out.println("$$$$$$$##");
                //#style mainCommandBE
                menuScreen.append(u.getId() + "(" + xk + ")", MainMid.getSpriteImage("/" + u.getId() + "_a.png", u.getSizeX(), u.getSizeY(), 0), StyleSheet.maincommandbeStyle );


            }
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();

        } else if (command == cmdDetails) {
            String str = menuScreen.getString(menuScreen.getSelectedIndex());
            str = str.substring(0, str.indexOf("("));
            Unit u = JCageConfigurator.getUnitById(str);
            FormUnitDetails f = new FormUnitDetails(u.getId(), this, u);
            MainMid.getClientConfigurator().display(f);


        } else if (command.getLabel().equals("Select")) {
             if (fromMap.equals("map")) {
                String str = menuScreen.getString(menuScreen.getSelectedIndex());
                str = str.substring(0, str.indexOf("("));

                Unit u = null;
                u = JCageConfigurator.getUnitById(str.trim());

                int xr = GameLogic.getInstance().createUnit(u);
                if (xr == 0) {
                    JCageConfigurator.currentSelectedUnit = u;
                    MainMid.getClientConfigurator().displayParent();
                } else if (xr == -1) {
                    FormAlert f = new FormAlert("Not enough resources to create unit", FormAlert.WARNING, this.getParent());
                    MainMid.getClientConfigurator().display(f);

                } else if (xr == -2) {
                    FormAlert f = new FormAlert("Population limit exceeded. Build more Forts and Outposts", FormAlert.WARNING, this.getParent());
                    MainMid.getClientConfigurator().display(f);

                }

            } else {
                String str = menuScreen.getString(menuScreen.getSelectedIndex());
                str = str.substring(0, str.indexOf("("));
                Unit u = JCageConfigurator.getUnitById(str);
                //System.out.println("Unit=" + u);
                FormUnitDetails f = new FormUnitDetails(u.getId(), this, u);
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
