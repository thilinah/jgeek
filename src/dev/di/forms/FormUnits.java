package dev.di.forms;

import de.enough.polish.util.Locale;
import dev.di.data.dao.Building;
import dev.di.main.JCageConfigurator;
import dev.di.main.MainMid;
import dev.di.util.StringUtil;

import javax.microedition.lcdui.*;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 5, 2007
 * Time: 3:30:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormUnits extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdBack = new Command("Back", Command.BACK, 1);
    boolean select = false;
    public List menuScreen = null;
    Hashtable ht = null;

    public FormUnits(String s, ChildForm parent, boolean select) {
        super(s);
        this.select = select;
        this.parent = parent;
        //#style mainScreenBE
        this.menuScreen = new List(Locale.get("g.build"), List.IMPLICIT);
        Enumeration en = JCageConfigurator.buildings.keys();
        ht = new Hashtable();
        while (en.hasMoreElements()) {
            Object u = en.nextElement();
            Building b = (Building) JCageConfigurator.buildings.get(u);
            //System.out.println("building type :"+b.getType());
            if (b.getType().equals("uni")) {
                //#style mainCommandBE
                menuScreen.append(b.getId(), MainMid.getImage("/" + b.getId() + "_a.png"));


            }
        }

        menuScreen.addCommand(cmdBack);
        menuScreen.setCommandListener(this);

    }


    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();
        } else if (command.getLabel().equals("Select")) {
            String selected = menuScreen.getString(menuScreen.getSelectedIndex()).trim();
            //System.out.println("Selected text: " + selected);
            Building b = null;
            b = JCageConfigurator.getBuildingById(selected);

            FormBuildingUnits f = new FormBuildingUnits("rb", this, b, select,null);
            MainMid.getClientConfigurator().display(f);
        }
    }

    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        return menuScreen;
    }


}
