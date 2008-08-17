package dev.di.data.service;

import dev.di.util.RecordStoreManager;


/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Oct 25, 2007
 * Time: 3:53:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractService {
    protected String recordStoreName = "";
    public RecordStoreManager rsm = RecordStoreManager.getInstance();

    public AbstractService() {
    }

    public String createAttribString(String[] stringSet) {
        String s = "";
        for (int i = 0; i < stringSet.length; i++) {
            s += stringSet[i] + "|";
        }
        return s;
    }
}
