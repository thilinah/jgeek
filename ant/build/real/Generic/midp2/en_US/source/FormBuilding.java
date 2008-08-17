// package dev.di.forms;

//import de.enough.polish.util.Locale;
//import dev.di.data.dao.Building;
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
public class FormBuilding extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdBack = new Command("Back", Command.BACK, 1);
    Command cmdMore = new Command("Details", Command.BACK, 2);
    Command cmdUpgrade = new Command("Upgrade", Command.BACK, 3);

    //custom commands
    Command cmdUnits = new Command("Show Units", Command.OK, 2);
    //    Command cmdUpdate = new Command("Upgrade", Command.OK, 3);
    public List menuScreen = null;
    boolean select = false;

    public FormBuilding(String s, ChildForm parent, boolean select) {
        super(s);

        this.select = select;
        this.parent = parent;
        //#style mainScreenBE
        menuScreen = new List("Buildings", List.IMPLICIT, StyleSheet.mainscreenbeStyle );

        Enumeration en = JCageConfigurator.buildings.keys();

        while (en.hasMoreElements()) {
            Building b = (Building) JCageConfigurator.buildings.get(en.nextElement());
            //#style mainCommandBE
            menuScreen.append(b.getId() + "\n", MainMid.getImage("/" + b.getId() + "_a.png"), StyleSheet.maincommandbeStyle );


        }

        menuScreen.addCommand(cmdBack);
        menuScreen.addCommand(cmdMore);
        menuScreen.addCommand(cmdUpgrade);
        //menuScreen.addCommand(cmdUnits);
        //if (!select)
//            menuScreen.addCommand(cmdUpdate);
        menuScreen.setCommandListener(this);

    }


    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();

        } else if (command == cmdMore) {
            Building b = (Building) JCageConfigurator.getSelectedIndex(menuScreen.getSelectedIndex(), JCageConfigurator.buildings);
            FormResourcesBuilding f = new FormResourcesBuilding("rb", this, b);
            MainMid.getClientConfigurator().display(f);
        }
        else if (command == cmdUpgrade) {
            Building b = (Building) JCageConfigurator.getSelectedIndex(menuScreen.getSelectedIndex(), JCageConfigurator.buildings);
            int xr= GameLogic.getInstance().upgradeBuilding(b);

            if(xr==0){

            }
            else if(xr==-1){
//                 if (Lang.CL.equals(Lang.EN)) {
                        FormAlert f = new FormAlert("No enough resources to upgrade the building level", FormAlert.WARNING, this.getParent());
                        MainMid.getClientConfigurator().display(f);
//                    } else {
//                        FormAlert f = new FormAlert(SinhalaConvertor.getInstance().getSinhalaWords(Locale.get("m.building.upgrade.nores.s")), FormAlert.WARNING, this.getParent());
//                        MainMid.getClientConfigurator().display(f);
//                    }
            }
            else if(xr==-2){
//                 if (Lang.CL.equals(Lang.EN)) {
                        FormAlert f = new FormAlert("Can not upgrade the building. Maximum level reached", FormAlert.WARNING, this.getParent());
                        MainMid.getClientConfigurator().display(f);
//                    } else {
//                        FormAlert f = new FormAlert(SinhalaConvertor.getInstance().getSinhalaWords(Locale.get("m.building.upgrade.s")), FormAlert.WARNING, this.getParent());
//                        MainMid.getClientConfigurator().display(f);
//                    }
            }

        }
        else if (command.getLabel().equals("Select")) {
            //Resources selected
            System.out.println("::::: " + menuScreen.getSelectedIndex());
            Building b = (Building) JCageConfigurator.getSelectedIndex(menuScreen.getSelectedIndex(), JCageConfigurator.buildings);
            System.out.println("select :" + select);
            if (select) {
                int xr = GameLogic.getInstance().createBuilding(b);
                if (xr == 0) {
                    JCageConfigurator.currentSelectedBuilding = b;
                    MainMid.getClientConfigurator().displayParent();
                } else if (xr == -1) {
                    FormAlert f = new FormAlert("Not enough resources to create building", FormAlert.WARNING, this.getParent());
                    MainMid.getClientConfigurator().display(f);

                } else if (xr == -2) {
                    FormAlert f = new FormAlert("Number of buildings allowed for this building type exceeded. Upgrade the level of the building", FormAlert.WARNING, this.getParent());
                    MainMid.getClientConfigurator().display(f);

                }
                //MainMid.getClientConfigurator().displayParent();

            } else {
                FormResourcesBuilding f = new FormResourcesBuilding("rb", this, b);
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
