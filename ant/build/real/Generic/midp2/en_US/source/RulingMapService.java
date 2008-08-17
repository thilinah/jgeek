// package dev.di.data.service;


//import dev.di.data.dao.PlanetDao;

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
public class RulingMapService extends AbstractService {

    private static RulingMapService me = null;

    private RulingMapService() {
        super();
        recordStoreName = "rmap";
    }

    public static RulingMapService getInstance() {
        if (me == null)
            me = new RulingMapService();
        return me;
    }

    public void insert(PlanetDao resource) throws DuplicateDataException, Exception {
        String[] s = null;
        try {
            s = rsm.getRecordById(recordStoreName, resource.getName());
        } catch (RecordStoreNotFoundException e) {
        }
        if (s != null)
            throw new DuplicateDataException("Duplicated");
        rsm.createRecord(recordStoreName, resource.getName() + ":" +
                resource.toString());

    }

    public void insertOrUpdate(Hashtable resources) throws Exception {
        Enumeration en = resources.keys();
        while (en.hasMoreElements()) {
            PlanetDao res = (PlanetDao) resources.get(en.nextElement());
            insertOrUpdate(res);
        }
    }

    public void update(PlanetDao resource) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, resource.getName());
        rsm.updateRecord(recordStoreName, resource.getName() + ":" +
                resource.toString(), Integer.parseInt(s[0]));
    }

    public void insertOrUpdate(PlanetDao resource) throws Exception {
        try {
            insert(resource);
        } catch (DuplicateDataException e1) {
            update(resource);
        }
    }

    public void delete(PlanetDao res) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, res.getName());
        rsm.deleteRecord(recordStoreName, Integer.parseInt(s[0]));
    }

    public Hashtable getAll() throws Exception {
        try {
            Hashtable ht = rsm.getRecordPairs(recordStoreName);
            Hashtable k = new Hashtable();
            Enumeration e = ht.keys();

            while (e.hasMoreElements()) {
                String s = String.valueOf(e.nextElement());
                PlanetDao d = new PlanetDao(String.valueOf(ht.get(s)));
                k.put(d.getName(), d);
            }
            return k;
        } catch (Exception e1) {
            throw e1;
        }
    }

    public PlanetDao get(String id) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, id);
        PlanetDao res = null;
        if (s != null) {
            res = new PlanetDao(s[1].substring(s[1].indexOf(":") + 1));
        }
        return res;
    }
}
