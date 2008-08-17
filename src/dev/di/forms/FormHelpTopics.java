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
public class FormHelpTopics extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdBack = new Command("Back", Command.BACK, 1);

    public List menuScreen = null;
    boolean select = false;

    public FormHelpTopics(String s, ChildForm parent) {
        super(s);
        this.parent = parent;
        //#style mainScreenE
        menuScreen = new List("Help", List.IMPLICIT);
        //#style mainCommandE
        //menuScreen.append("", MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p1.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p2.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p3.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p4.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p5.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p6.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p7.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p8.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p9.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p10.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p11.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p12.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p13.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p14.t"), MainMid.getImage("/help-center.png"));
        //#style mainCommandE
        menuScreen.append(Locale.get("h.p15.t"), MainMid.getImage("/help-center.png"));


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

        } else if (command.getLabel().equals("Select")) {
            FormHelpCont f = new FormHelpCont("s", this, menuScreen.getSelectedIndex());
            MainMid.getClientConfigurator().display(f);
        }
    }
}
