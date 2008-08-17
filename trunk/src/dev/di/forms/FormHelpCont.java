package dev.di.forms;

import dev.di.main.MainMid;

import javax.microedition.lcdui.*;

import de.enough.polish.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 21, 2008
 * Time: 9:54:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormHelpCont extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdBack = new Command("Back", Command.BACK, 1);

    public List menuScreen = null;
    boolean select = false;

    public FormHelpCont(String s, ChildForm parent, int i) {
        super(s);
        this.parent = parent;
        //#style mainScreenF
        menuScreen = new List("Help", List.IMPLICIT);
        //#style mainCommandF
        menuScreen.append("", MainMid.getImage("/help-center.png"));
        if (i == 0) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p1.c"), null);
        } else if (i == 1) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p2.c"), null);
        } else if (i == 2) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p3.c"), null);
        } else if (i == 3) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p4.c"), null);
        } else if (i == 4) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p5.c"), null);
        } else if (i == 5) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p6.c"), null);
        } else if (i == 6) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p7.c"), null);
        } else if (i == 7) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p8.c"), null);
        } else if (i == 8) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p9.c"), null);
        } else if (i == 9) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p10.c"), null);
        } else if (i == 10) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p11.c"), null);
        } else if (i == 11) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p12.c"), null);
        } else if (i == 12) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p13.c"), null);
        } else if (i == 13) {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p14.c"), null);
        } else {
            //#style mainCommandF
            menuScreen.append(Locale.get("h.p15.c"), null);
        }


        menuScreen.addCommand(cmdBack);
        menuScreen.setCommandListener(this);
    }


    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        return menuScreen;
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();

        }
    }
}
