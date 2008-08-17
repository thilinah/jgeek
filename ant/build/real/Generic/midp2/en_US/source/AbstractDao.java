// package dev.di.data.dao;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Oct 25, 2007
 * Time: 8:34:12 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractDao {
    public int numOfAttribs=0;

    public String[] getAttributes(String s){
        ////System.out.println(s);
        ////System.out.println("numOfAttribs"+numOfAttribs);
        String[] k=new String[numOfAttribs];
        int i=0;
        while(s.indexOf("|")>-1){
            ////System.out.println("I "+i);
            k[i]=s.substring(0,s.indexOf("|"));
           // //System.out.println("k[i]"+k[i]);
            ////System.out.println("S:"+s);
            s=s.substring(s.indexOf("|")+1);
            ////System.out.println(s);
            i=i+1;
        }
        return k;
    }

    public Hashtable getTable(String s){
        //ee,dd#dd,ff#
        Hashtable ht=new Hashtable();
        String x=null;
        if(s.trim().equals("")){
            return ht;
        }
        while(s.indexOf("#")>0){

            x=s.substring(0,s.indexOf("#"));
            ht.put(x.substring(0,s.indexOf(",")),x.substring(s.indexOf(",")+1));
            s=s.substring(s.indexOf("#")+1);
        }
        return ht;
    }

    public String tableToString(Hashtable ht){
        Enumeration en=ht.keys();
        String s="";
        String k=null;
        String v=null;
        while(en.hasMoreElements()){
            k=String.valueOf(en.nextElement()).trim();
            v=String.valueOf(ht.get(k)).trim();
            s+=k+","+v+"#";
        }
        return s;
    }
}
