// package dev.di.forms;

//import de.enough.polish.util.Locale;
//import dev.di.data.dao.PlanetDao;
//import dev.di.data.service.MapService;
//import dev.di.main.JCageConfigurator;
//import dev.di.main.MainMid;

import javax.microedition.lcdui.CommandListener;  					import javax.microedition.lcdui.AlertType; 					import javax.microedition.lcdui.Canvas; 					import javax.microedition.lcdui.Command; 					import javax.microedition.lcdui.Display; 					import javax.microedition.lcdui.Displayable; 					import javax.microedition.lcdui.Font; 					import javax.microedition.lcdui.Graphics; 					import javax.microedition.lcdui.Image;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 5, 2007
 * Time: 3:30:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormOtherMaps extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdBack = new Command("Back", Command.BACK, 1);
    boolean select = false;

    public List menuScreen = null;


    public FormOtherMaps(String s, ChildForm parent, boolean select) {
        super(s);
        this.select = select;
        this.parent = parent;
        //#style mainScreenE
        this.menuScreen = new List("Other Planets", List.IMPLICIT, StyleSheet.mainscreeneStyle );
        updateScreen();
        menuScreen.addCommand(cmdBack);
        menuScreen.setCommandListener(this);

    }

    private void updateScreen() {
        menuScreen.deleteAll();
        Enumeration en = JCageConfigurator.planets.keys();

        while (en.hasMoreElements()) {
            PlanetDao b = (PlanetDao) JCageConfigurator.planets.get(en.nextElement());
                //#style mainCommandE
                menuScreen.append(b.getName(), MainMid.getImage("/notes.png"), StyleSheet.maincommandeStyle );
        }
    }


    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();
        } else if (command.getLabel().equals("Select")) {
            //Resources selected
            //System.out.println("::::: " + menuScreen.getSelectedIndex());
            PlanetDao b = (PlanetDao) JCageConfigurator.planets.get(menuScreen.getString(menuScreen.getSelectedIndex()));
            //System.out.println("Select " + select);
            //Planet f = new Planet(this, b, select, false);
            //MainMid.getClientConfigurator().display(f);

        }
    }

    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        updateScreen();
        return menuScreen;
    }


}
