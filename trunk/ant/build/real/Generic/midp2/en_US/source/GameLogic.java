/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Dec 11, 2007
 * Time: 12:35:01 PM
 * To change this template use File | Settings | File Templates.
 */
// package dev.di.engine;

//import dev.di.data.dao.Building;
//import dev.di.data.dao.Resource;
//import dev.di.data.dao.Unit;
//import dev.di.data.service.BuildingService;
//import dev.di.data.service.ResourceService;
//import dev.di.main.JCageConfigurator;
//import dev.di.main.MainMid;
//import dev.di.map.buildings.BaseBuildingService;
//import dev.di.map.buildings.IBuilding;
//import dev.di.map.units.BaseUnit;
//import dev.di.map.units.BaseUnitService;
//import dev.di.map.units.IUnit;

import java.util.Enumeration;
import java.util.Hashtable;

public class GameLogic {
    private static GameLogic ourInstance = null;

    public static GameLogic getInstance() {
        if (ourInstance == null)
            ourInstance = new GameLogic();
        return ourInstance;
    }

    private GameLogic() {
    }

    public int createUnit(Unit unit) {
        Enumeration e = unit.getResources().keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            int b = Integer.parseInt(String.valueOf(unit.getResources().get(key))) * JCageConfigurator.resourceNeedMultiplicationAmountUnit;
            if (JCageConfigurator.getResourceById(key).getAmount() < b) {
                return -1;//not enough resources
            }

        }

        //check unit amount exceed
        if (JCageConfigurator.getUnitTotal() >= getAllowedPopulation() + 1) {
            return -2;
        }

        //reducing resources
        Enumeration ex = unit.getResources().keys();
        while (ex.hasMoreElements()) {
            String key = (String) ex.nextElement();
            int b = Integer.parseInt(String.valueOf(unit.getResources().get(key))) * JCageConfigurator.resourceNeedMultiplicationAmountUnit;
            Resource res = JCageConfigurator.getResourceById(key);
            int k = res.getAmount();
            k = k - b;
            res.setAmount(String.valueOf(k));
            try {
                ResourceService.getInstance().insertOrUpdate(res);
                JCageConfigurator.resources.put(key, res);
            } catch (Exception e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return -3;//error
            }

        }

//        unit.setAmount(unit.getAmount() + 1);
//        try {
//            UnitService.getInstance().insertOrUpdate(unit);
//        } catch (Exception e1) {
//            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            return -3;//error
//        }
        return 0;
    }


    public int createBuilding(Building building) {
        Enumeration e = building.getResourceMatrix().keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            int b = Integer.parseInt(String.valueOf(building.getResourceMatrix().get(key))) * JCageConfigurator.resourceNeedMultiplicationAmountBuilding;
            if (JCageConfigurator.getResourceById(key).getAmount() < b) {
                return -1;//not enough resources
            }

        }

        //check building amount exceed
        System.out.println("Get number of buildings : " + getTotalNumberOfAllayBuildingsByBaseType(building.getId()));
        if (Integer.parseInt(building.getDelay()) <= getTotalNumberOfAllayBuildingsByBaseType(building.getId())) {
            return -2;
        }

        //reducing resources
        Enumeration ex = building.getResourceMatrix().keys();
        while (ex.hasMoreElements()) {
            String key = (String) ex.nextElement();
            int b = Integer.parseInt(String.valueOf(building.getResourceMatrix().get(key))) * JCageConfigurator.resourceNeedMultiplicationAmountBuilding;
            Resource res = JCageConfigurator.getResourceById(key);
            int k = res.getAmount();
            k = k - b;
            res.setAmount(String.valueOf(k));
            try {
                ResourceService.getInstance().insertOrUpdate(res);
                JCageConfigurator.resources.put(key, res);
            } catch (Exception e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return -3;//error
            }

        }


        return 0;
    }


    public int upgradeBuilding(Building building) {

        if (JCageConfigurator.currentProxy == null) {
            return -1;
        }

        int currentLevel = Integer.parseInt((String) JCageConfigurator.currentProxy.getBuildingLevels().get(building.getId()));


        Enumeration e = building.getLevelMatrix().keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            int b = Integer.parseInt(String.valueOf(building.getLevelMatrix().get(key))) * currentLevel;
            if (JCageConfigurator.getResourceById(key).getAmount() < b) {
//
                return -1;//not enough resources
            }

        }

        //check building max level
        System.out.println("Building level : " + currentLevel);
        System.out.println("Building max level : " + building.getMaxLevel().trim());
        if (building.getMaxLevel().trim().equals(String.valueOf(currentLevel))) {
            return -2;
        }

        //reducing resources
        Enumeration ex = building.getLevelMatrix().keys();
        while (ex.hasMoreElements()) {
            String key = (String) ex.nextElement();
            int b = Integer.parseInt(String.valueOf(building.getLevelMatrix().get(key)))*currentLevel;
            Resource res = JCageConfigurator.getResourceById(key);
            int k = res.getAmount();
            k = k - b;
            res.setAmount(String.valueOf(k));
            try {
                ResourceService.getInstance().insertOrUpdate(res);
                JCageConfigurator.resources.put(key, res);
            } catch (Exception e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return -3;//error
            }

        }

        //if its all ok.. update the building level
        JCageConfigurator.currentProxy.getBuildingLevels().put(building.getId(),String.valueOf(currentLevel+1));

        return 0;
    }

    public int getTotalNumberOfAllayBuildingsByBaseType(String type) {
        int k = 0;
        Hashtable ht = null;
        try {
            ht = BaseBuildingService.getInstance().getAll();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (ht == null) return 0;
        Enumeration ex = ht.keys();
        while (ex.hasMoreElements()) {
            String key = String.valueOf(ex.nextElement());
            IBuilding bu = (IBuilding) ht.get(key);
            if (bu.getBase().getId().equals(type)) {
                k++;
            }
        }
        return k;
    }

    public int getTotalNumberOfAllyUnits() {
        int k = 0;
        Hashtable ht = null;
        try {
            ht = BaseUnitService.getInstance().getAll();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (ht == null) return 0;
        Enumeration ex = ht.keys();
        while (ex.hasMoreElements()) {
            String key = String.valueOf(ex.nextElement());
            IUnit bu = (IUnit) ht.get(key);
            if (bu.getOwener().equals(MainMid.getClientConfigurator().user)) {
                k++;
            }
        }
        return k;
    }

    public int getTotalNumberOfAllyUnitsByPlanet(String planet) {
        int k = 0;
        Hashtable ht = null;
        try {
            ht = BaseUnitService.getInstance().getAll();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (ht == null) return 0;
        Enumeration ex = ht.keys();
        while (ex.hasMoreElements()) {
            String key = String.valueOf(ex.nextElement());
            IUnit bu = (IUnit) ht.get(key);
            if (bu.getOwener().equals(MainMid.getClientConfigurator().user) &&
                    ((BaseUnit) bu).getMissionId().equals(planet)) {
                k++;
            }
        }
        return k;
    }


    public int getTotalNumberOfAllyUnitsByPlanetandType(String gameName, String type) {
        int k = 0;
        Hashtable ht = null;
        try {
            ht = BaseUnitService.getInstance().getAll();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (ht == null) return 0;
        Enumeration ex = ht.keys();
        while (ex.hasMoreElements()) {
            String key = String.valueOf(ex.nextElement());
            IUnit bu = (IUnit) ht.get(key);
            if (bu.getOwener().equals(MainMid.getClientConfigurator().user) &&
                    ((BaseUnit) bu).getMissionId().equals(gameName) && bu.getBase().getId().equals(type)) {
                k++;
            }
        }
        return k;
    }


    public int getTotalNumberOfEnemyUnitsByPlanet(String planet) {
        int k = 0;
        Hashtable ht = null;
        try {
            ht = BaseUnitService.getInstance().getAll();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (ht == null) return 0;
        Enumeration ex = ht.keys();
        while (ex.hasMoreElements()) {
            String key = String.valueOf(ex.nextElement());
            IUnit bu = (IUnit) ht.get(key);
            if (!bu.getOwener().equals(MainMid.getClientConfigurator().user) &&
                    ((BaseUnit) bu).getMissionId().equals(planet)) {
                k++;
            }
        }
        return k;
    }

    public int getAllowedPopulation() {
        int k = 0;
        int r = 0;
        Hashtable ht = null;
        try {
            ht = BuildingService.getInstance().getAllBuildings();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (ht == null) return 0;
        Enumeration ex = ht.keys();
        while (ex.hasMoreElements()) {
            String key = String.valueOf(ex.nextElement());
            Building bu = (Building) ht.get(key);
            if (bu.getType().equals(JCageConfigurator.BUILDING_DEFEND)) {
                k += getTotalNumberOfAllayBuildingsByBaseType(bu.getId()) * Integer.parseInt(bu.getTotalStrength());
                r += Integer.parseInt(bu.getTotalStrength());
            }
        }
        return (k * JCageConfigurator.populationCalculationBuildingWeight) / r;
    }


}
