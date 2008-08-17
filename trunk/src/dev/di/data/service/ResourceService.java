package dev.di.data.service;

import dev.di.data.dao.Resource;

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
public class ResourceService extends AbstractService {

    private static ResourceService me = null;

    private ResourceService() {
        super();
        recordStoreName = "resource";
    }

    public static ResourceService getInstance() {
        if (me == null)
            me = new ResourceService();
        return me;
    }

    public void insertResource(Resource resource) throws DuplicateDataException, Exception {
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
            Resource res = (Resource) resources.get(en.nextElement());
            insertOrUpdate(res);
        }
    }

    public void updateResource(Resource resource) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, resource.getId());
        rsm.updateRecord(recordStoreName, resource.getId() + ":" +
                resource.toString(), Integer.parseInt(s[0]));
    }

    public void insertOrUpdate(Resource resource) throws Exception {
        try {
            insertResource(resource);
        } catch (DuplicateDataException e1) {
            updateResource(resource);
        }
    }

    public void deleteResource(Resource res) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, res.getId());
        rsm.deleteRecord(recordStoreName, Integer.parseInt(s[0]));
    }

    public Hashtable getAllResources() throws Exception {
        try {
            Hashtable ht = rsm.getRecordPairs(recordStoreName);
            Hashtable k = new Hashtable();
            Enumeration e = ht.keys();

            while (e.hasMoreElements()) {
                String s = String.valueOf(e.nextElement());
                Resource r = new Resource(String.valueOf(ht.get(s)));
                k.put(r.getId(), r);
                ////System.out.println("RES : "+s+"  "+String.valueOf(ht.get(s) ));
            }
            //System.out.println("Resoureces read successfully");
            return k;
        } catch (Exception e1) {
            //System.out.println("Resoureces not read successfully");

            throw e1;
        }
    }

    public Resource getResource(String id) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, id);
        Resource res = null;
        if (s != null) {
            //o[0]=s[0];
            res = new Resource(s[1].substring(s[1].indexOf(":") + 1));
        }
        return res;
    }
}
