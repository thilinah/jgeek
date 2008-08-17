package dev.di.forms;

import de.enough.polish.util.Locale;
import dev.di.data.dao.Resource;
import dev.di.data.dao.Unit;
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
public class FormUnitDetails extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdBack = new Command("Back", Command.BACK, 1);
    //    Command cmdDetails = new Command("Create", Command.OK, 1);
    Unit unit = null;
    public List menuScreen = null;

    public FormUnitDetails(String s, ChildForm parent, Unit unit) {
        super(s);
        this.parent = parent;

        //#style mainScreenF
        this.menuScreen = new List(unit.getId(), List.IMPLICIT);


        this.unit = unit;
        String resString = "";
        //Create resource string
        Enumeration e1 = unit.getResources().keys();
        while (e1.hasMoreElements()) {
            String key = String.valueOf(e1.nextElement());
            resString += "  " + key + " : +" + Integer.parseInt(unit.getResources().get(key).toString()) * JCageConfigurator.resourceNeedMultiplicationAmountUnit + "\n";

            //System.out.println(resString);
        }
        if (!resString.equals("")) {
            resString = Locale.get("msg.Need") + "\n" + resString;

            //System.out.println(resString);
        }

        //menuScreen.append("",MainMid.getImage("/"+unit.getId()+ "_a.png"));
        System.out.println("unit "+unit);
        System.out.println("unit "+unit.getDescription());
        if (unit.getDescription() != null && !unit.getDescription().equals("")) {
            //#style mainCommandF
            menuScreen.append(unit.getDescription(), null);
        }
        if (!resString.equals("")) {
            //#style mainCommandF
            menuScreen.append(resString, null);
        }


        menuScreen.addCommand(cmdBack);
//        menuScreen.addCommand(cmdDetails);
        menuScreen.setCommandListener(this);
    }


    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();
        }
//        else if (command == cmdDetails) {
//            ProgressForm f = new ProgressForm(this, JCageConfigurator.ACTION_UPGRADE_BUILDING,
//                    unit.getDelay());
//            MainMid.getClientConfigurator().display(f);
//
//        }
    }

    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        return menuScreen;
    }


}
