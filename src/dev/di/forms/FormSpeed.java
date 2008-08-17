package dev.di.forms;

import dev.di.main.JCageConfigurator;
import dev.di.main.MainMid;
import dev.di.util.RecordStoreManager;
import dev.di.map.PlanetMap;

import javax.microedition.lcdui.*;


/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: Jul 12, 2007
 * Time: 9:59:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormSpeed extends Form implements CommandListener, ChildForm {

    ChildForm parent = null;
    List menuSpeed = null;
    Command cmdOk = new Command("Ok", Command.BACK, 1);
    String sp = "";

    public FormSpeed(ChildForm parent) {
        super("Select");
        this.parent = parent;
        initialize();
    }

    private void initialize() {
        createLangMenu();

        try {
            String[] sk = RecordStoreManager.getInstance().getRecordById(JCageConfigurator.SYSTEM_RECORDSTORE_NAME, "speed");
            RecordStoreManager.getInstance().updateRecord(JCageConfigurator.SYSTEM_RECORDSTORE_NAME
                    , "speed" + ":" + menuSpeed.getString(menuSpeed.getSelectedIndex()), Integer.parseInt(sk[0]));

            sp = sk[1];
        } catch (Exception e) {
            //System.out.println("first time");
            RecordStoreManager.getInstance().createRecord(JCageConfigurator.SYSTEM_RECORDSTORE_NAME, "speed:00000");
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            sp = "00000";
        }
        menuSpeed.addCommand(cmdOk);
        for (int i = 0; i < 8; i++) {
            if (menuSpeed.getString(i).trim().equals(sp)) {
                menuSpeed.setSelectedIndex(i, true);
            }
        }
        menuSpeed.setCommandListener(this);
        this.setCommandListener(this);
    }

    public void createLangMenu() {
        //#style mainScreenE
        menuSpeed = new List("Select Phone Speed", List.EXCLUSIVE);
        //#style mainCommandE
        menuSpeed.append("00000000", null);
        //#style mainCommandE
        menuSpeed.append("0000000", null);
        //#style mainCommandE
        menuSpeed.append("000000", null);
        //#style mainCommandE
        menuSpeed.append("00000", null);
        //#style mainCommandE
        menuSpeed.append("0000", null);
        //#style mainCommandE
        menuSpeed.append("000", null);
        //#style mainCommandE
        menuSpeed.append("00", null);
        //#style mainCommandE
        menuSpeed.append("0", null);

    }

    public void commandAction(Command command, Displayable displayable) {
        if (command.getLabel().equals("Select")) {

        }
        if (command == cmdOk) {
            try {
                String[] sk = RecordStoreManager.getInstance().getRecordById(JCageConfigurator.SYSTEM_RECORDSTORE_NAME, "speed");
                RecordStoreManager.getInstance().updateRecord(JCageConfigurator.SYSTEM_RECORDSTORE_NAME
                        , "speed" + ":" + menuSpeed.getString(menuSpeed.getSelectedIndex()), Integer.parseInt(sk[0]));

                PlanetMap.refreshPeriod = menuSpeed.getString(menuSpeed.getSelectedIndex()).trim().length() * 20;
            } catch (Exception e) {
                RecordStoreManager.getInstance().createRecord(JCageConfigurator.SYSTEM_RECORDSTORE_NAME, "speed:" + menuSpeed.getString(menuSpeed.getSelectedIndex()));
                PlanetMap.refreshPeriod = menuSpeed.getString(menuSpeed.getSelectedIndex()).trim().length() * 20;
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            MainMid.getClientConfigurator().displayParent();
        }


    }

    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        return this.menuSpeed;
    }
}
