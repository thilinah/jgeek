package dev.di.map.units;


import dev.di.data.dao.Unit;
import dev.di.data.service.AbstractService;
import dev.di.data.service.DuplicateDataException;
import dev.di.map.buildings.BaseBuilding;

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
public class BaseUnitService extends AbstractService {

    private static BaseUnitService me = null;

    private BaseUnitService() {
        super();
        recordStoreName = "baseunit";
    }

    public static BaseUnitService getInstance() {
        if (me == null)
            me = new BaseUnitService();
        return me;
    }

    public void insert(BaseUnit unit) throws DuplicateDataException, Exception {
        String[] s = null;
        try {
            s = rsm.getRecordById(recordStoreName, unit.getMissionId()+"*"+unit.getUnitId());
        } catch (RecordStoreNotFoundException e) {
        }
        if (s != null)
            throw new DuplicateDataException("Duplicated");
        rsm.createRecord(recordStoreName, unit.getMissionId()+"*"+unit.getUnitId() + ":" +
                unit.toString());

    }

    public void insertOrUpdate(Hashtable resources) throws Exception {
        Enumeration en = resources.keys();
        while (en.hasMoreElements()) {
            BaseUnit res = (BaseUnit) resources.get(en.nextElement());
            insertOrUpdate(res);
        }
    }

    public void update(BaseUnit unit) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, unit.getMissionId()+"*"+unit.getUnitId());
        rsm.updateRecord(recordStoreName, unit.getMissionId()+"*"+unit.getUnitId() + ":" +
                unit.toString(), Integer.parseInt(s[0]));
    }

    public void insertOrUpdate(BaseUnit resource) throws Exception {
        try {
            insert(resource);
        } catch (DuplicateDataException e1) {
            update(resource);
        }
    }

    public void delete(BaseUnit unit) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, unit.getMissionId()+"*"+unit.getUnitId());
        rsm.deleteRecord(recordStoreName, Integer.parseInt(s[0]));
    }

    public Hashtable getAll() throws Exception {
        try {
            Hashtable ht = rsm.getRecordPairs(recordStoreName);
            Hashtable k = new Hashtable();
            Enumeration e = ht.keys();

            while (e.hasMoreElements()) {
                String s = String.valueOf(e.nextElement());
                BaseUnit u = new BaseUnit(String.valueOf(ht.get(s)));
                k.put(u.getMissionId()+"*"+u.getUnitId(), u);
            }
            return k;
        } catch (Exception e1) {
            throw e1;
        }
    }

    public BaseUnit get(String id) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, id);
        BaseUnit res = null;
        if (s != null) {
            res = new BaseUnit(s[1].substring(s[1].indexOf(":") + 1));
        }
        return res;
    }

    public void deleteAllBaseUnitsByPlanet(String planet){
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
            BaseUnit bu=(BaseUnit)ht.get(key);
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
