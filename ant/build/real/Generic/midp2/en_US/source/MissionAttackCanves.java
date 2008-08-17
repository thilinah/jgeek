// package dev.di.engine;

//import dev.di.forms.ChildForm;
//import dev.di.main.JCageConfigurator;
//import dev.di.engine.data.MissionUnitMovement;

import javax.microedition.lcdui.CommandListener;  					import javax.microedition.lcdui.AlertType; 					import javax.microedition.lcdui.Canvas; 					import javax.microedition.lcdui.Command; 					import javax.microedition.lcdui.Display; 					import javax.microedition.lcdui.Displayable; 					import javax.microedition.lcdui.Font; 					import javax.microedition.lcdui.Graphics; 					import javax.microedition.lcdui.Image;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Feb 19, 2008
 * Time: 7:57:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class MissionAttackCanves extends Canvas implements Runnable, ChildForm, CommandListener {
    protected void paint(Graphics graphics) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ChildForm getParent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Displayable getDisplay() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void commandAction(Command command, Displayable displayable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    int countMovingUnits(String unit, Hashtable ht){
        int sum=0;
        Enumeration en = ht.keys();
        while(en.hasMoreElements()){
            MissionUnitMovement mu=(MissionUnitMovement)ht.get(en.nextElement());
            if(mu.getUnit().equals(unit)){
                sum++;
            }
        }
        return sum;
    }
}
