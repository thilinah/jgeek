// package dev.di.map.buildings;


//import dev.di.data.service.AbstractService;
//import dev.di.data.service.DuplicateDataException;
//import dev.di.map.buildings.BaseBuilding;

import javax.microedition.rms.RecordStoreNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Oct 25, 2007
 * Time: 2:54:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseBuildingService extends AbstractService {

    private static BaseBuildingService me = null;

    private BaseBuildingService() {
        super();
        recordStoreName = "basebuilding";
    }

    public static BaseBuildingService getInstance() {
        if (me == null)
            me = new BaseBuildingService();
        return me;
    }

    public void insert(BaseBuilding building) throws DuplicateDataException, Exception {
        String[] s = null;
        try {
            s = rsm.getRecordById(recordStoreName, building.getMissionId()+"*"+building.getBuildingId());
        } catch (RecordStoreNotFoundException e) {
        }
        if (s != null)
            throw new DuplicateDataException("Duplicated");
        rsm.createRecord(recordStoreName, building.getMissionId()+"*"+building.getBuildingId() + ":" +
                building.toString());

    }

    public void insertOrUpdate(Hashtable resources) throws Exception {
        Enumeration en = resources.keys();
        while (en.hasMoreElements()) {
            BaseBuilding res = (BaseBuilding) resources.get(en.nextElement());
            insertOrUpdate(res);
        }
    }

    public void update(BaseBuilding unit) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, unit.getMissionId()+"*"+unit.getBuildingId());
        rsm.updateRecord(recordStoreName, unit.getMissionId()+"*"+unit.getBuildingId() + ":" +
                unit.toString(), Integer.parseInt(s[0]));
    }

    public void insertOrUpdate(BaseBuilding resource) throws Exception {
        try {
            insert(resource);
        } catch (DuplicateDataException e1) {
            update(resource);
        }
    }

    public void delete(BaseBuilding unit) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, unit.getMissionId()+"*"+unit.getBuildingId());
        rsm.deleteRecord(recordStoreName, Integer.parseInt(s[0]));
    }

    public Hashtable getAll() throws Exception {
        try {
            Hashtable ht = rsm.getRecordPairs(recordStoreName);
            Hashtable k = new Hashtable();
            Enumeration e = ht.keys();

            while (e.hasMoreElements()) {
                String s = String.valueOf(e.nextElement());
                BaseBuilding u = new BaseBuilding(String.valueOf(ht.get(s)));
                
                k.put(u.getMissionId()+"*"+u.getBuildingId(), u);
            }
            return k;
        } catch (Exception e1) {
            throw e1;
        }
    }

    public BaseBuilding get(String id) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, id);
        BaseBuilding res = null;
        if (s != null) {
            res = new BaseBuilding(s[1].substring(s[1].indexOf(":") + 1));
        }
        return res;
    }

    public void deleteAllBaseBuildingsByPlanet(String planet){
        Hashtable ht=null;
        try {
            ht = getAll();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(ht==null) return;
        Enumeration ex = ht.keys();
        while (ex.hasMoreElements()) {
            String key=String.valueOf(ex.nextElement());
            BaseBuilding bu=(BaseBuilding)ht.get(key);
            if(bu.getMissionId().equals(planet)){
                try {
                    delete(bu);
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }
}
