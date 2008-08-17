package dev.di.forms;

import de.enough.polish.util.Locale;
import dev.di.data.dao.Resource;
import dev.di.main.JCageConfigurator;
import dev.di.main.MainMid;
import dev.di.util.StringUtil;

import javax.microedition.lcdui.*;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 5, 2007
 * Time: 3:30:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormResources extends Form implements CommandListener, ChildForm, Updatable {
    ChildForm parent = null;
    Command cmdBack = new Command("Back", Command.BACK, 1);
    Command cmdUpdate = new Command("Update", Command.OK, 1);
    public List menuScreen = null;

    public FormResources(String s, ChildForm parent) {
        super(s);

            this.parent = parent;
            //#style mainScreenF
            this.menuScreen = new List(Locale.get("g.res"), List.IMPLICIT);
            Enumeration en = JCageConfigurator.resources.keys();
            
            while (en.hasMoreElements()) {
                Resource res = (Resource) JCageConfigurator.resources.get(en.nextElement());
                    //#style mainCommandF
                    menuScreen.append(res.getId() + " " + res.getAmount() /*+ "| +" + res.getRate()*/, MainMid.getImage(Locale.get("res.def")));


            }


        MainMid.getClientConfigurator().game.registerResourceMenu(this);
        menuScreen.addCommand(cmdBack);
        menuScreen.addCommand(cmdUpdate);
        menuScreen.setCommandListener(this);
    }


    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();
            MainMid.getClientConfigurator().game.unregisterResourceMenu();
        }
    }

    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        return menuScreen;
    }

    public void update() {
        Enumeration en = JCageConfigurator.resources.keys();
        int i = 0;
        int k=menuScreen.getSelectedIndex();
        while (en.hasMoreElements()) {
            Resource res = (Resource) JCageConfigurator.resources.get(en.nextElement());
                //#style mainCommandF
                menuScreen.set(i, res.getId() + " " + res.getAmount() /*+ "| +" + res.getRate()*/, MainMid.getImage(Locale.get("res.def")));

            i++;
        }
        menuScreen.setSelectedIndex(k,true);
        MainMid.getClientConfigurator().display(this);
    }
}
