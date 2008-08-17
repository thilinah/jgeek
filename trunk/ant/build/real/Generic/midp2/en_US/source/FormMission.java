// package dev.di.forms;

//import de.enough.polish.util.Locale;
//import dev.di.data.dao.Mission;
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
public class FormMission extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdBack = new Command("Back", Command.BACK, 1);
    public List menuScreen = null;
    String type = "";

    public FormMission(String s, ChildForm parent, String type) {
        super(s);
        this.parent = parent;
        this.type = type;

        refresh();

    }

    public void refresh() {
        //#style mainScreenG
        this.menuScreen = new List("Missions", List.IMPLICIT, StyleSheet.mainscreengStyle );
        menuScreen.deleteAll();
        Enumeration en = JCageConfigurator.missins.keys();

        while (en.hasMoreElements()) {
            Mission res = (Mission) JCageConfigurator.missins.get(en.nextElement());
            if (res.getType().equals(type)) {
                //#style mainCommandG
                menuScreen.append(res.getName(), MainMid.getImage("/notes.png"), StyleSheet.maincommandgStyle );
            }
        }
        menuScreen.addCommand(cmdBack);
        menuScreen.setCommandListener(this);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();

        }
    }

    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        refresh();
        return menuScreen;
    }


}
