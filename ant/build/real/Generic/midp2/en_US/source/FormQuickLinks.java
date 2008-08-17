// package dev.di.forms;

//import de.enough.polish.util.Locale;
//import dev.di.main.MainMid;
//import dev.di.sound.SoundSynthesizer;

import javax.microedition.lcdui.CommandListener;  					import javax.microedition.lcdui.AlertType; 					import javax.microedition.lcdui.Canvas; 					import javax.microedition.lcdui.Command; 					import javax.microedition.lcdui.Display; 					import javax.microedition.lcdui.Displayable; 					import javax.microedition.lcdui.Font; 					import javax.microedition.lcdui.Graphics; 					import javax.microedition.lcdui.Image;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 16, 2008
 * Time: 1:07:57 AM
 * To change this template use File | Settings | File Templates.
 */

public class FormQuickLinks extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdExit = new Command("Back", Command.EXIT, 1);
    public List menuScreen = null;



    public FormQuickLinks(String s, ChildForm parent) {
        super(s);
        this.parent = parent;
        //#style mainScreenE
        this.menuScreen = new List("Dark Inspiration", List.IMPLICIT, StyleSheet.mainscreeneStyle );



            //this.menuScreen.append(Locale.get("g.news"), MainMid.getImage(Locale.get("g.news.i")));
            //#style mainCommandE
            this.menuScreen.append("Messages", MainMid.getImage("/message.png"), StyleSheet.maincommandeStyle );
        
        menuScreen.addCommand(cmdExit);
        menuScreen.setCommandListener(this);

    }


    public void commandAction(Command command, Displayable displayable) {

        if (command == cmdExit) {
            MainMid.getClientConfigurator().displayParent();
        }


        if (command.getLabel().equals("Select")) {
            SoundSynthesizer.getInstance().playClickSound();
//            if (menuScreen.getSelectedIndex() == 0) {
//
//                 FormNews f = new FormNews("News", this);
//                MainMid.getClientConfigurator().display(f);
//            }
            if (menuScreen.getSelectedIndex() == 0) {

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

