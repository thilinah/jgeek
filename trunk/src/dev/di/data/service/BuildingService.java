package dev.di.data.service;

import dev.di.data.dao.Building;

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
public class BuildingService extends AbstractService {

    private static BuildingService me = null;

    private BuildingService() {
        super();
        recordStoreName = "building";
    }

    public static BuildingService getInstance() {
        if (me == null)
            me = new BuildingService();
        return me;
    }

    public void insertBuilding(Building resource) throws DuplicateDataException, Exception {
        String[] s = null;
        try {
            s = rsm.getRecordById(recordStoreName, resource.getId());
        } catch (RecordStoreNotFoundException e) {
        }
        if (s != null)
            throw new DuplicateDataException("Duplicated");
        rsm.createRecord(recordStoreName, resource.getId() + ":" +
                resource.toString());

    }

    public void insertOrUpdate(Hashtable resources) throws Exception {
        Enumeration en = resources.keys();
        while (en.hasMoreElements()) {
            Building res = (Building) resources.get(en.nextElement());
            insertOrUpdate(res);
        }
    }

    public void updateBuilding(Building resource) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, resource.getId());
        rsm.updateRecord(recordStoreName, resource.getId() + ":" +
                resource.toString(), Integer.parseInt(s[0]));
    }

    public void insertOrUpdate(Building resource) throws Exception {
        try {
            insertBuilding(resource);
        } catch (DuplicateDataException e1) {
            updateBuilding(resource);
        }
    }

    public void deleteBuilding(Building res) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, res.getId());
        rsm.deleteRecord(recordStoreName, Integer.parseInt(s[0]));
    }

    public Hashtable getAllBuildings() throws Exception {
        try {
            Hashtable ht = rsm.getRecordPairs(recordStoreName);
            Hashtable k = new Hashtable();
            Enumeration e = ht.keys();

            while (e.hasMoreElements()) {
                String s = String.valueOf(e.nextElement());
                Building b = new Building(String.valueOf(ht.get(s)));
                k.put(b.getId(), b);
            }
            return k;
        } catch (Exception e1) {
            throw e1;
        }
    }

    public Building getBuilding(String id) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, id);
        Building res = null;
        if (s != null) {
            res = new Building(s[1].substring(s[1].indexOf(":") + 1));
        }
        return res;
    }
}
