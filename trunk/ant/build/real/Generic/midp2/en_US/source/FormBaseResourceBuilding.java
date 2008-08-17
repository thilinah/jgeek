// package dev.di.forms;

//import dev.di.util.StringUtil;
//import dev.di.data.dao.Building;
//import dev.di.data.dao.Resource;
//import dev.di.main.JCageConfigurator;
//import dev.di.main.MainMid;
//import dev.di.map.buildings.BaseBuilding;

import javax.microedition.lcdui.CommandListener;  					import javax.microedition.lcdui.AlertType; 					import javax.microedition.lcdui.Canvas; 					import javax.microedition.lcdui.Command; 					import javax.microedition.lcdui.Display; 					import javax.microedition.lcdui.Displayable; 					import javax.microedition.lcdui.Font; 					import javax.microedition.lcdui.Graphics; 					import javax.microedition.lcdui.Image;
import java.util.Enumeration;

//import de.enough.polish.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 15, 2008
 * Time: 7:31:19 PM
 * To change this template use File | Settings | File Templates.
 */

public class FormBaseResourceBuilding extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdBack = new Command("Ok", Command.BACK, 1);
    //Command cmdUpgrade = new Command("Upgrade", Command.OK, 1);
    BaseBuilding building = null;
    public List menuScreen = null;

    public FormBaseResourceBuilding(String s, ChildForm parent, BaseBuilding building) {
        super(s);
        this.parent = parent;


            //#style mainScreenF
            this.menuScreen = new List(building.getBase().getId(), List.IMPLICIT, StyleSheet.mainscreenfStyle );



        this.building = building;
        //Create resource string
        String resString = "";

        Enumeration e1 = building.getResourceMatrix().keys();
        while (e1.hasMoreElements()) {
            String key = String.valueOf(e1.nextElement());
                resString += "  " + key + " : +" + building.getResourceMatrix().get(key).toString() + "\n";

        }


        if (!resString.equals("")) {
                resString = "Generates:" + "\n" + resString;

        }



        //#style mainCommandF
        menuScreen.append("", MainMid.getImage("/" + building.getBase().getId() + "_a.png"), StyleSheet.maincommandfStyle );

            if (!resString.equals("")) {
                //#style mainCommandF
                menuScreen.append(resString, null, StyleSheet.maincommandfStyle );
            }
            if (building.getBase().getDescription() != null && !building.getBase().getDescription().equals("")) {
                //#style mainCommandF
                menuScreen.append(StringUtil.breakString(building.getBase().getDescription(), 0, "*"), null, StyleSheet.maincommandfStyle );
            }




        menuScreen.addCommand(cmdBack);
        //menuScreen.addCommand(cmdUpgrade);
        menuScreen.setCommandListener(this);
    }


    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();
        }
//        else if (command == cmdUpgrade) {
//            ProgressForm f = new ProgressForm(this, JCageConfigurator.ACTION_UPGRADE_BUILDING,
//                    Integer.parseInt(building.getDelay()));
//            MainMid.getClientConfigurator().display(f);
//
//        }
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
