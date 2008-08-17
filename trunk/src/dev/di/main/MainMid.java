package dev.di.main;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 16, 2007
 * Time: 11:19:18 AM
 * To change this template use File | Settings | File Templates.
 */


import de.enough.polish.util.Locale;
import dev.di.forms.ChildForm;
import dev.di.forms.FormHelpTopics;
import dev.di.forms.FormSpeed;
import dev.di.map.*;
import dev.di.data.dao.PlanetDao;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class MainMid extends MIDlet implements CommandListener, ChildForm {
    //hold a reference to current display
    public static boolean langSelected = false;
    List menuScreen = null;
    private static JCageConfigurator clientConfigurator = null;
    int temp = 0;

    public static JCageConfigurator getClientConfigurator() {
        return clientConfigurator;
    }


    protected void startApp() {
        initialize();
        System.out.println("App initialized");
        createMenu();
        clientConfigurator.display = Display.getDisplay(this);
        clientConfigurator.mainMidlet = this;
        menuScreen.setCommandListener(this); 
        MainMid.getClientConfigurator().display(clientConfigurator.mainMidlet);


    }

    public void createMenu() {
        //#style mainScreenE
        menuScreen = new List(Locale.get("menu"), List.IMPLICIT);
        //#style mainCommandE
        menuScreen.append(Locale.get("menu.Game"), getImage(Locale.get("menu.Game.i")));
        //#style mainCommandE
        menuScreen.append(Locale.get("menu.Speed"), getImage(Locale.get("menu.Speed.i")));
        //#style mainCommandE
        menuScreen.append(Locale.get("menu.Help"), getImage(Locale.get("menu.Help.i")));
        //#style mainCommandE
        menuScreen.append(Locale.get("menu.Quit"), getImage(Locale.get("menu.Quit.i")));
    }


    private void initialize() {
        clientConfigurator = new JCageConfigurator();

    }

    //pause
    protected void pauseApp() {
    }

    //destroy application
    protected void destroyApp(boolean b) {

    }


    public static void prepareLoginSuccessMenu() {

    }


    /**
     * @param imagePath path of the returned image
     * @return Image
     */
    public static Image getImage(String imagePath) {
        Image img = null;
        try {
            img = Image.createImage(imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    public static Image getSpriteImage(String imagePath, int x, int y, int frame) {
        Image img = getImage(imagePath);
        img = Ground.cropImage(img, x, y, frame);
        return img;

    }

    public void commandAction(Command command, Displayable displayable) {

        if (menuScreen.getSelectedIndex() == 0) {
            //todo show new game screen
            Player p1=new Player("Thilina",1,new Point(23,34),true);
            Player p2=new Player("ThilinaX",1,new Point(23,34),false);
            GameProxy gp=new GameProxy("ddd",p1,p2);
            MainMid.getClientConfigurator().loadMetaData();
            MainMid.getClientConfigurator().startNewGame(gp);
//            String s="30*30*19,0,0,0*1,0,6,6*1,0,6,7*9,0,4,0*12,0,0,0*1,0,6,6*1,0,6,7*5,0,2,6*11,0,4,0*9,0,0,0*1,0,6,6*2,0,2,6*10,0,4,0*1,0,3,6*7,0,4,0*4,0,0,0*1,0,6,6*1,0,1,7*3,0,0,0*1,0,4,6*2,0,1,9*2,0,1,8*1,0,3,9*1,0,2,7*1,0,4,6*4,0,4,0*1,0,2,7*1,0,0,0*1,0,0,7*6,0,4,0*2,0,0,0*1,0,6,6*1,0,6,7*1,0,4,0*1,0,5,6*3,0,0,0*1,0,0,7*1,0,2,7*5,0,0,0*1,0,0,7*3,0,3,6*1,0,2,7*3,0,0,0*1,0,4,6*5,0,4,0*1,0,0,0*1,0,6,6*1,0,6,7*2,0,3,7*1,0,2,7*18,0,0,0*1,0,0,7*3,0,3,6*2,0,4,0*1,0,6,7*2,0,4,0*1,0,2,7*24,0,0,0*1,0,4,6*3,0,4,0*1,0,2,7*25,0,0,0*1,0,4,6*1,0,4,0*1,0,3,6*1,0,2,7*26,0,0,0*1,0,4,6*1,0,4,0*28,0,0,0*1,0,0,7*1,0,1,8*29,0,0,0*1,0,4,6*1,1,5,1,0,0*1,1,0,1,0,0*1,1,0,2,0,0*1,1,4,2,0,0*25,0,0,0*1,0,0,7*2,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*11,0,0,0*1,0,1,5*1,0,3,5*15,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*10,0,0,0*1,0,1,5*2,0,3,0*1,0,3,5*14,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*8,0,0,0*1,0,1,5*1,0,4,4*4,0,3,0*2,0,4,4*1,0,3,5*11,0,0,0*1,1,1,2,0,0*1,1,0,2,0,0*1,1,2,2,0,0*7,0,0,0*1,0,1,5*7,0,3,0*1,0,0,5*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*7,0,0,0*1,0,6,4*9,0,3,0*11,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*7,0,0,0*1,0,6,4*9,0,3,0*11,0,0,0*1,1,1,2,0,0*1,1,0,2,0,0*7,0,0,0*1,0,2,5*10,0,3,0*11,0,0,0*1,1,4,2,0,0*1,1,0,2,0,0*7,0,0,0*1,0,2,5*9,0,3,0*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*8,0,0,0*1,0,2,5*7,0,3,0*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*9,0,0,0*1,0,2,5*5,0,3,0*1,0,4,5*12,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*13,0,0,0*1,0,2,5*1,0,3,0*13,0,0,0*1,1,3,1,0,0*1,1,4,1,0,0*28,0,0,0*1,1,1,2,0,0*174,0,0,0*";
            gp.setMap((String)JCageConfigurator.maps.get("refo1"));
            gp.setMapName("refo1");
            PlanetMap k=new PlanetMap("s",this,gp);
            System.out.println("k :"+k);
            MainMid.getClientConfigurator().display(k);
            
        }

        if (menuScreen.getSelectedIndex() == 2) {
            FormSpeed f = new FormSpeed(this);
            MainMid.getClientConfigurator().display(f);

        }
        if (menuScreen.getSelectedIndex() == 3) {
            FormHelpTopics f = new FormHelpTopics("", this);
            MainMid.getClientConfigurator().display(f);

        }
        if (menuScreen.getSelectedIndex() == 4) {
            notifyDestroyed();
        }

    }

    public ChildForm getParent() {
        return null;
    }

    public Displayable getDisplay() {
        return menuScreen;
    }
}
