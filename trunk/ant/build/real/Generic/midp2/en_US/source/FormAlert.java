// package dev.di.forms;

//import dev.di.main.MainMid;


import javax.microedition.lcdui.CommandListener;  					import javax.microedition.lcdui.AlertType; 					import javax.microedition.lcdui.Canvas; 					import javax.microedition.lcdui.Command; 					import javax.microedition.lcdui.Display; 					import javax.microedition.lcdui.Displayable; 					import javax.microedition.lcdui.Font; 					import javax.microedition.lcdui.Graphics; 					import javax.microedition.lcdui.Image;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 5, 2007
 * Time: 3:30:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormAlert extends Form implements CommandListener, ChildForm {

    public static int WARNING = 0;
    public static int MESSAGE = 1;

    ChildForm parent = null;
    Command cmdCancel = new Command("Cancel", Command.BACK, 1);
    Command cmdOk = new Command("Ok", Command.BACK, 1);
    Command cmdMsgYes = new Command("Yes", Command.OK, 1);
    Command cmdNo = new Command("No", Command.BACK, 1);
    public List menuScreen = null;
    boolean stop = false;
    int type = 0;

    public FormAlert(String text, int type, ChildForm parent) {
        super("");
        this.type = type;
        this.parent = parent;

            //#style mainScreenMsg
            this.menuScreen = new List("", List.IMPLICIT, StyleSheet.mainscreenmsgStyle );
            //#style mainCommandMsg
            menuScreen.append("", getIconFromType(), StyleSheet.maincommandmsgStyle );
            //#style mainCommandMsg
            menuScreen.append(text, null, StyleSheet.maincommandmsgStyle );

        addCommands();
        menuScreen.setCommandListener(this);
    }

    private void addCommands() {
        if (type == WARNING) {
            menuScreen.addCommand(cmdCancel);
            menuScreen.addCommand(cmdOk);
        } else if (type == MESSAGE) {
            menuScreen.addCommand(cmdMsgYes);
            menuScreen.addCommand(cmdNo);
        }
    }


    public Image getIconFromType() {
        if (type == WARNING) {
            return MainMid.getImage("/warning.png");
        } else if (type == MESSAGE) {
            return MainMid.getImage("/mail-get.png");
        }
        return null;
    }


    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdCancel) {
            MainMid.getClientConfigurator().displayParent();
        } else if (command == cmdOk) {
            MainMid.getClientConfigurator().displayParent();
        } else if (command == cmdNo) {
            MainMid.getClientConfigurator().displayParent();
        } else if (command == cmdMsgYes) {

        }
    }

    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        return menuScreen;
    }

}
