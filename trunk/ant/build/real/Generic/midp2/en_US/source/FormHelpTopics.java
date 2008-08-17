// package dev.di.forms;

//import dev.di.main.MainMid;

import javax.microedition.lcdui.CommandListener;  					import javax.microedition.lcdui.AlertType; 					import javax.microedition.lcdui.Canvas; 					import javax.microedition.lcdui.Command; 					import javax.microedition.lcdui.Display; 					import javax.microedition.lcdui.Displayable; 					import javax.microedition.lcdui.Font; 					import javax.microedition.lcdui.Graphics; 					import javax.microedition.lcdui.Image;

//import de.enough.polish.util.Locale;

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
        menuScreen = new List("Help", List.IMPLICIT, StyleSheet.mainscreeneStyle );
        //#style mainCommandE
        
        
        menuScreen.append("login screen", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Introduction", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Getting started", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Moving cursor", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Creating buildings", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Resource buildings", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Unit Buildings", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Creating units", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Moving units", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Self destroying", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Getting other planets", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Communicating with other emperors", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Creating space missions and attacking other planets", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Colonizing other planets", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );
        //#style mainCommandE
        menuScreen.append("Sending units to planets you colonized", MainMid.getImage("/help-center.png"), StyleSheet.maincommandeStyle );


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
