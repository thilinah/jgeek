package dev.di.engine;

import dev.di.data.dao.Mission;
import dev.di.data.dao.Resource;
import dev.di.data.dao.Unit;
import dev.di.data.service.MissionService;
import dev.di.data.service.ResourceService;
import dev.di.data.service.UnitService;
import dev.di.engine.data.MissionUnitMovement;
import dev.di.forms.Updatable;
import dev.di.main.JCageConfigurator;
import dev.di.main.MainMid;
import dev.di.map.buildings.BaseBuilding;
import dev.di.map.buildings.BaseBuildingService;
import dev.di.map.units.BaseUnit;
import dev.di.map.units.BaseUnitService;
import dev.di.map.units.IUnit;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 13, 2007
 * Time: 9:45:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game extends TimerTask {
    Updatable updatable = null;
    Date lastUpdatedTime = new Date();
    int runTimes = 0;

    Hashtable isEnemyIn = new Hashtable();

    public void run() {
//        if (runTimes % 5 == 0)    //run every 30 sec
//            updateResourceRates();

        //updateResourceAmounts();      //run every 10 sec
        //MainMid.getClientConfigurator().displayAleart("start to update");

        //MainMid.getClientConfigurator().displayAleart(" update screen");
        updateScreen();

//        if (runTimes % 1 == 0) {
//            updateResources();
//        }

        runTimes++;

        if (runTimes > 1000) {
            runTimes = 0;
        }
    }


    private void updateRetUnits(int interval) {
        Enumeration en = JCageConfigurator.retUnits.keys();
        while (en.hasMoreElements()) {
            MissionUnitMovement u = (MissionUnitMovement) JCageConfigurator.retUnits.get(en.nextElement());
            if (u.getTime() < 0) {
                Unit ut = JCageConfigurator.getUnitById(u.getUnit());
                ut.setAmount(ut.getAmount() + 1);
                try {
                    UnitService.getInstance().insertOrUpdate(ut);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                u.setTime(u.getTime() - interval);
            }
        }
    }


    private void updateSentUnits(int interval) {
        Enumeration en = JCageConfigurator.sentUnits.keys();
        while (en.hasMoreElements()) {
            MissionUnitMovement u = (MissionUnitMovement) JCageConfigurator.sentUnits.get(en.nextElement());
            Mission m = JCageConfigurator.getMissionById(u.getMission());
            if (u.getTime() > m.getTime()) {
                Object mua = m.getGameParams().get(u.getUnit());
                if (mua == null) {
                    m.getGameParams().put(u.getUnit(), "1");
                } else {
                    int ca = Integer.parseInt(String.valueOf(mua));
                    m.getGameParams().put(u.getUnit(), String.valueOf(ca + 1));
                }
                try {
                    MissionService.getInstance().insertOrUpdate(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                u.setTime(u.getTime() + interval);
            }
        }
    }


    private void updateScreen() {
        if (updatable != null)
            updatable.update();
    }

    public void registerResourceMenu(Updatable updatable) {
        this.updatable = updatable;
    }

    public void unregisterResourceMenu() {
        updatable = null;
    }


    boolean isEnemyUnitsInPlanet(String planet) {
        if (isEnemyIn.get(planet) != null) {
            return true;
        }

        Hashtable ht = null;
        try {
            ht = BaseUnitService.getInstance().getAll();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (ht == null) {
            return false;
        }

        Enumeration en = ht.keys();

        while (en.hasMoreElements()) {
            String key = String.valueOf(en.nextElement());
            IUnit un = (IUnit) ht.get(key);
            if (((BaseUnit) un).getMissionId().equals(planet)) {
                if (!un.getOwener().equals(MainMid.getClientConfigurator().user) /*&&
                        !un.getBase().getType().equals(JCageConfigurator.UNIT_GROUND_HUMAN_A) &&
                        !un.getBase().getType().equals(JCageConfigurator.UNIT_GROUND_HUMAN_B) &&
                        !un.getBase().getType().equals(JCageConfigurator.UNIT_GROUND_HUMAN_C)*/) {
                    isEnemyIn.put(planet, "1");
                    return true;
                }
            }

        }

        return false;
    }


    private void updateResources() {
        try {
            Hashtable ht = null;
            try {
                ht = BaseBuildingService.getInstance().getAll();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            System.out.println("Updating min res");
            Enumeration enx = JCageConfigurator.resources.keys();
            while (enx.hasMoreElements()) {
                Resource res = (Resource) JCageConfigurator.resources.get(enx.nextElement());
                int amount = res.getAmount();
                System.out.println(res.getId() + amount);
                amount += JCageConfigurator.resourceProductionMinimum;
                res.setAmount(String.valueOf(amount));
                JCageConfigurator.resources.put(res.getId(), res);
                System.out.println(res.getId() + res.getAmount());

            }

            if (ht == null) {
                System.out.println("Return due to ht null");
                return;
            }

            Enumeration en = ht.keys();

            while (en.hasMoreElements()) {
                String key = String.valueOf(en.nextElement());
                BaseBuilding b = (BaseBuilding) ht.get(key);
                if (b.getBase().getType().equals(JCageConfigurator.BUILDING_RESOURCE) && !b.isStillCreating()) {
                    Enumeration ena = b.getResourceMatrix().keys();
                    while (ena.hasMoreElements()) {
                        String bres = String.valueOf(ena.nextElement());
                        int val = Integer.parseInt(String.valueOf(b.getResourceMatrix().get(bres)));
                        Resource res = (Resource) JCageConfigurator.resources.get(bres);

                        int cval = res.getAmount();
                        String s = String.valueOf(val + cval);
                        res.setAmount(s);
                        JCageConfigurator.resources.put(bres, res);
                        System.out.println("RU :" + b.getBase().getType() + " : " + bres + " : " + val);
                    }
                }
            }

            try {
                ResourceService.getInstance().insertOrUpdate(JCageConfigurator.resources);

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

//    private void updateResourceAmounts
//            () {
//        Enumeration en = JCageConfigurator.resources.keys();
//        Date td = new Date();
//        long diff = td.getTime() - lastUpdatedTime.getTime();
//        while (en.hasMoreElements()) {
//            Resource res = (Resource) JCageConfigurator.resources.get(en.nextElement());
//            int amount = res.getAmount();
//            ////System.out.println("========== "+(td.getTime()-lastUpdatedTime.getTime()));
//            amount += (res.getRate() * diff) / MainMid.getClientConfigurator().resourceRateTime;
//            ////System.out.println("Temp amount ="+String.valueOf(amount));
//            res.setAmount(String.valueOf(amount));
//            ////System.out.println("Amount "+res.getId()+" "+res.getAmount());
//
//            if (runTimes % 10 == 0) {
//                try {
//                    ResourceService.getInstance().insertOrUpdate(res);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        lastUpdatedTime = td;
//        JCageConfigurator.buildingsUpdated = false;
//    }

//    private void updateResourceRates
//            () {
//        Enumeration en = JCageConfigurator.resources.keys();
//        while (en.hasMoreElements()) {
//            Resource res = (Resource) JCageConfigurator.resources.get(en.nextElement());
//            getResourceGenerationRate(res);
//            ////System.out.println(res.getId()+" "+res.getRate());
//        }
//        JCageConfigurator.buildingsUpdated = false;
//    }
//
//    private void getResourceGenerationRate
//            (Resource
//                    res) {
//        if (JCageConfigurator.buildingsUpdated) {
//            int rate = 0;
//            Enumeration en = JCageConfigurator.buildings.keys();
//            while (en.hasMoreElements()) {
//                Building b = (Building) JCageConfigurator.buildings.get(en.nextElement());
//                Enumeration en1 = b.getResourceMatrix().keys();
//                while (en1.hasMoreElements()) {
//                    String key = String.valueOf(en1.nextElement());
//                    if (key.equals(res.getId())) {
//                        rate += Integer.parseInt(String.valueOf(b.getResourceMatrix().get(key)));
//                    }
//                }
//            }
//            res.setRate(String.valueOf(rate));
//
//            if (runTimes % 5 == 0) {
//                try {
//                    ResourceService.getInstance().insertOrUpdate(res);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
