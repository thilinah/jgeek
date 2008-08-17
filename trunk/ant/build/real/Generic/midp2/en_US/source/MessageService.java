// package dev.di.data.service;

//import dev.di.data.dao.JCageMessage;

import javax.microedition.rms.RecordStoreNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Sumithi
 * Date: Dec 5, 2007
 * Time: 8:06:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageService extends AbstractService {

    private static MessageService me = null;

    private MessageService() {
        super();
        recordStoreName = "message";
    }

    public static MessageService getInstance() {
        if (me == null)
            me = new MessageService();
        return me;
    }

    public void insertMessage(JCageMessage message) throws DuplicateDataException, Exception {
        String[] s = null;
        try {
            s = rsm.getRecordById(recordStoreName, message.getId());
        } catch (RecordStoreNotFoundException e) {
        }
        if (s != null)
            throw new DuplicateDataException("Duplicated");
        rsm.createRecord(recordStoreName, message.getId() + ":" + message.toString());

    }

    public void insertOrUpdate(Hashtable message) throws Exception {
        Enumeration en = message.keys();
        while (en.hasMoreElements()) {
            JCageMessage mes = (JCageMessage) message.get(en.nextElement());
            insertOrUpdate(mes);
        }
    }

    public void insertOrUpdate(JCageMessage message) throws Exception {
        try {
            insertMessage(message);
        } catch (DuplicateDataException e1) {
            updateResource(message);
        }
    }

    public void deleteMessage(JCageMessage message) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, message.getId());
        rsm.deleteRecord(recordStoreName, Integer.parseInt(s[0]));
    }

    public void updateResource(JCageMessage message) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, message.getId());
        rsm.updateRecord(recordStoreName, message.getId() + ":" + message.toString(), Integer.parseInt(s[0]));
    }

    public Hashtable getAllMessages() throws Exception {
        try {
            Hashtable ht = rsm.getRecordPairs(recordStoreName);
            Hashtable k = new Hashtable();
            Enumeration e = ht.keys();

            while (e.hasMoreElements()) {
                String s = String.valueOf(e.nextElement());
                k.put(s, new JCageMessage(String.valueOf(ht.get(s))));
                ////System.out.println("RES : "+s+"  "+String.valueOf(ht.get(s) ));
            }
            return k;
        } catch (Exception e1) {
            throw e1;
        }
    }

    public JCageMessage getMessage(String id) throws Exception {
        String[] s = rsm.getRecordById(recordStoreName, id);
        JCageMessage mes = null;
        if (s != null) {
            //o[0]=s[0];
            mes = new JCageMessage(s[1].substring(s[1].indexOf(":") + 1));
        }
        return mes;
    }

}
