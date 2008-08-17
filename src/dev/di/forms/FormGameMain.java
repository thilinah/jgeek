package dev.di.forms;

import de.enough.polish.util.Locale;
import dev.di.data.dao.Mission;
import dev.di.data.dao.PlanetDao;
import dev.di.main.JCageConfigurator;
import dev.di.main.MainMid;
import dev.di.map.PlanetMap;
import dev.di.sound.SoundSynthesizer;

import javax.microedition.lcdui.*;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Oct 25, 2007
 * Time: 10:03:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormGameMain extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    //Command cmdCancel=new Command("Back", Command.BACK, 1);
    Command cmdExit = new Command("Exit", Command.EXIT, 1);
    public List menuScreen = null;

//    public static FormGameMain getInstance(String title,Displayable screen){
//        if(me==null){
//            me=new FormGameMain(title,screen);
//        }
//        return me;
//    }

    public FormGameMain(String s, ChildForm parent) {
        super(s);
        this.parent = parent;
        //#style mainScreenE
        this.menuScreen = new List(Locale.get("g.menu"), List.IMPLICIT);

            //#style mainCommandE
            this.menuScreen.append(Locale.get("g.res"), MainMid.getImage(Locale.get("g.res.i")));
            //#style mainCommandE
            this.menuScreen.append(Locale.get("g.build"), MainMid.getImage(Locale.get("g.build.i")));
            //#style mainCommandE
            this.menuScreen.append(Locale.get("g.units"), MainMid.getImage(Locale.get("g.units.i")));


        menuScreen.addCommand(cmdExit);
        menuScreen.setCommandListener(this);

    }


    public void commandAction(Command command, Displayable displayable) {

        if (command == cmdExit) {
            MainMid.getClientConfigurator().stopGameTimer();
            MainMid.getClientConfigurator().display(MainMid.getClientConfigurator().mainMidlet);
            JCageConfigurator.gameStarted = false;
        }


        if (command.getLabel().equals("Select")) {
            SoundSynthesizer.getInstance().playClickSound();
            //System.out.println("menuScreen.getSelectedIndex()" + menuScreen.getSelectedIndex());
            if (menuScreen.getSelectedIndex() == 0) {
                FormResources f = new FormResources("res", this);
                MainMid.getClientConfigurator().display(f);
                JCageConfigurator.buildingsUpdated = true;
            } else if (menuScreen.getSelectedIndex() == 1) {

                FormBuilding f = new FormBuilding("bil", this,false);
                MainMid.getClientConfigurator().display(f);
            } else if (menuScreen.getSelectedIndex() == 2) {
                FormUnits f = new FormUnits("bil", this, false);
                MainMid.getClientConfigurator().display(f);
            } else if (menuScreen.getSelectedIndex() == 3) {
//                Planet f = new Planet(this, JCageConfigurator.mymap, false, true);
//                MainMid.getClientConfigurator().display(f);
                //PlanetDao pd=new PlanetDao("TestMap","150,100,150","10000",0,0,0,10,10,);
                //PlanetMap f = new PlanetMap("", this, JCageConfigurator.mymap);
               // MainMid.getClientConfigurator().display(f);

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
