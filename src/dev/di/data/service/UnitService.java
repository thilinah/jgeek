package dev.di.data.service;


import dev.di.data.dao.Unit;

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
public class UnitService extends AbstractService {

    private static UnitService me = null;

    private UnitService() {
        super();
        recordStoreName = "unit";
    }

    public static UnitService getInstance() {
        if (me == null)
            me = new UnitService();
        return me;
    }

    public void insert(Unit resource) throws DuplicateDataException, Exception {
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
            Unit res = (Unit) resources.get(en.nextElement());
            insertOrUpdate(res);
        }
    }

    public void update(Unit resource) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, resource.getId());
        rsm.updateRecord(recordStoreName, resource.getId() + ":" +
                resource.toString(), Integer.parseInt(s[0]));
    }

    public void insertOrUpdate(Unit resource) throws Exception {
        try {
            insert(resource);
        } catch (DuplicateDataException e1) {
            update(resource);
        }
    }

    public void delete(Unit res) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, res.getId());
        rsm.deleteRecord(recordStoreName, Integer.parseInt(s[0]));
    }

    public Hashtable getAll() throws Exception {
        try {
            Hashtable ht = rsm.getRecordPairs(recordStoreName);
            Hashtable k = new Hashtable();
            Enumeration e = ht.keys();

            while (e.hasMoreElements()) {
                String s = String.valueOf(e.nextElement());
                Unit u = new Unit(String.valueOf(ht.get(s)));
                k.put(u.getId(), u);
            }
            return k;
        } catch (Exception e1) {
            throw e1;
        }
    }

    public Unit get(String id) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, id);
        Unit res = null;
        if (s != null) {
            res = new Unit(s[1].substring(s[1].indexOf(":") + 1));
        }
        return res;
    }
}
