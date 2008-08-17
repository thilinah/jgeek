package dev.di.map.buildings;

import dev.di.map.buildings.BaseBuilding;
import dev.di.map.units.IUnit;
import dev.di.map.Environment;
import dev.di.data.dao.Building;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 2, 2008
 * Time: 5:07:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceBuilding extends BaseBuilding implements IResourceBuilding{

    public ResourceBuilding(Environment environment,String mission, Building base,String owner) {
        super(environment, mission, base,owner);
    }

    public ResourceBuilding(String fullStr) {
        super(fullStr);
    }

    public void collectResources() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
