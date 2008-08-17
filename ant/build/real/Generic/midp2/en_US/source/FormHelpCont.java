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
public class FormHelpCont extends Form implements CommandListener, ChildForm {
    ChildForm parent = null;
    Command cmdBack = new Command("Back", Command.BACK, 1);

    public List menuScreen = null;
    boolean select = false;

    public FormHelpCont(String s, ChildForm parent, int i) {
        super(s);
        this.parent = parent;
        //#style mainScreenF
        menuScreen = new List("Help", List.IMPLICIT, StyleSheet.mainscreenfStyle );
        //#style mainCommandF
        menuScreen.append("", MainMid.getImage("/help-center.png"), StyleSheet.maincommandfStyle );
        if (i == 0) {
            //#style mainCommandF
            menuScreen.append("Enter a user name and a password, this user name and password can be used to login to our website and update your profile.", null, StyleSheet.maincommandfStyle );
        } else if (i == 1) {
            //#style mainCommandF
            menuScreen.append("Now you are a ruler of a planet and will soon become an emperor. The goal is to colonize the universe and score points. To become the next emperor you have to gather resources while defending your planet and then you can challenge other emperors. Make alliances with other emperors and get their help to defend your planet. Dark Inspiration is the ultimate test of your strategic planning ability.", null, StyleSheet.maincommandfStyle );
        } else if (i == 2) {
            //#style mainCommandF
            menuScreen.append("When you start the game, you will get a view of your planet from outer space. This screen displays your score and current rank. By selecting the Enter command you see the landscape of your planet. There you can create buildings to gather resources, defend your planet and buildings for creating your army. To find more information about each building, go to Main menu and select buildings. You can get more information by clicking on each building. To get more information about your units, go to units menu. In next section, I will teach you how to create buildings.", null, StyleSheet.maincommandfStyle );
        } else if (i == 3) {
            //#style mainCommandF
            menuScreen.append("First enter your planet. When you see the terrain, and a green arrow on it. You can move that cursor using the joystick or 1,2,3 and 4. The cursor is used to select units and buildings and issue move commands to units. You can click the cursor using select button or 5.", null, StyleSheet.maincommandfStyle );
        } else if (i == 4) {
            //#style mainCommandF
            menuScreen.append("First enter your planet. When you see the terrain of the planet, press 1. This will take you to buildings menu. Select the building you want to build. Now you will see the terrain again. Move the cursor until you see a red square, not a red cross. Then click on the ground to place the building. Initially the health bar of the building will be blue. You can’t use the building, until it turns completely green. When you move the cursor on to a building and click on it, it will be selected and health bar will be displayed. Click on the same building again to get the menu for the building.", null, StyleSheet.maincommandfStyle );
        } else if (i == 5) {
            //#style mainCommandF
            menuScreen.append("At the start of the game you will be given some resources to build Refineries and solar plants. These buildings will boost the gathering rates of metal, crystal and energy. When building a Refinery you have to select a resource rich ground for that. Build metal Refineries in grassless regions near mountains and Build your crystal Refineries near shallow waters near mountains to make gathering rates of these resources higher. Even though you build Refineries in correct places rates of those Refineries depends on the abundance of the resource in your planet.When building solar plants, build them in grassless regions to get the best efficiency from the plant. After building few resource buildings (Refineries and Solar plants), you will see the amount of resources you are having is increasing.", null, StyleSheet.maincommandfStyle );
        } else if (i == 6) {
            //#style mainCommandF
            menuScreen.append("Now it is the time to build an army for you. First you have to build a Barrack which train armies and a Factory which build machines like war tanks and trucks. After building a Barrack or a Factory, double click(click on it once and when the red square curser is displayed, click on it again) on it to get the menu. From the menu you are getting select create. Then select the unit you want to build. It will be created and appeared next to the building.", null, StyleSheet.maincommandfStyle );
        } else if (i == 7) {
            //#style mainCommandF
            menuScreen.append("Double click on a Factory or a Barrack to get the unit menu. Select the unit to build.", null, StyleSheet.maincommandfStyle );
        } else if (i == 8) {
            //#style mainCommandF
            menuScreen.append("Click on a unit and the health bar will be displayed. Then move the cursor to a point and click there to move the selected unit  to that point.", null, StyleSheet.maincommandfStyle );
        } else if (i == 9) {
            //#style mainCommandF
            menuScreen.append("You can destroy our units and buildings by selecting them and pressing 9.", null, StyleSheet.maincommandfStyle );
        } else if (i == 10) {
            //#style mainCommandF
            menuScreen.append("Go to main menu and select other planets. Now select Get random maps option to get some maps randomly from the server. If you know the name of the planet you can type the name to get information about it by selecting Get map menu.The maps in your list will be used to send messages and missions to other planets.", null, StyleSheet.maincommandfStyle );
        } else if (i == 11) {
            //#style mainCommandF
            menuScreen.append("Now you have a developing world and you are ready to contact other planets. First you have to find information about some planets. To do that, go to the main menu and select “Other planets”. Now select get planets from the menu to get the list of planets. By clicking selecting each planet you can see the information about it. Some plants are untouched and some planets are colonized by other emperors. Some planets are home planets of other emperors and for now it’s better to stay away from them.Now you can send messages to those planets, if you are interested to build an alliance or if you just need to know what kind of emperor is ruling it. For that you have to send your message to a home planet or a colonized planet.For that, go to the main menu and select “Messages”. Then select “new message”. In the empty window you are getting, type a subject for your message and the message body. Then select the planet to send it and press send. You might get a replay after that message is delivered to the emperor.", null, StyleSheet.maincommandfStyle );
        } else if (i == 12) {
            //#style mainCommandF
            menuScreen.append("Now you have a developed planet and an army to fight for you. By creating missions you can send that army in your home planet to other planets. Send units from your army to planets of other emperors to attack their planet and weaken their strength and development progress. If you send sufficient amount of units to a planet which is colonized by another emperor you can capture that planet. But you can not capture a home planet of another emperor that way.If you send a mission to a planet which is not occupied by another emperor (an unclaimed planet), that planet becomes yours. But the maximum number of planets you can capture at a time is five. When you need more resources, you can use this method to capture planets and extract resources from those planets.To create a mission, select missions from the main menu. Then select the target planet. Also add units to that mission and select send to send it to the target.If you can’t see any planets, go to Other planes menu and get information about some planets.", null, StyleSheet.maincommandfStyle );
        } else if (i == 13) {
            //#style mainCommandF
            menuScreen.append("To colonize another planet, you can create a mission to an unclaimed planet or send sufficient number of units by creating a mission against a planet occupied by another emperor. When you create a mission against an unclaimed planet, it becomes yours. Then you can use that planet to gather resources you need to build your empire.If you want to capture a planet occupied by another emperor, you have to keep creating missions against that planet and send those to the enemy planet. If the enemy couldn’t stand against your power, the planet will become yours.When you capture a planet that way, you will receive a notification. Then the captured planet will be displayed under “Colonies” in main menu. By selecting a planet from that list, you can enter it.", null, StyleSheet.maincommandfStyle );
        } else {
            //#style mainCommandF
            menuScreen.append("When you colonized a planet, you can build resource buildings on that planet to gather resources. But you need to keep a small platoon on that planet to protect your buildings from attacks that can come from other emperors and inhabitant rebel groups of that planet. To send more units to your colonies, create a space mission and send it to your colony.", null, StyleSheet.maincommandfStyle );
        }


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

        }
    }
}
