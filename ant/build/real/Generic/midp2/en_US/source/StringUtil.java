// package dev.di.util;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Feb 4, 2008
 * Time: 9:20:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {
    public static String breakString(String str,int element,String splitter){
        int i=str.indexOf(splitter);
        int k=0;
        String s="";
        while(i!=-1){
            s=str.substring(0,i);
            if(k == element){
                return s;
            }
            str=str.substring(i+1);
            i=str.indexOf(splitter);
            k++;
            if(k==element && i==-1){
                return str;
            }
        }
        return null;
    }

    public static String revise0(String s){
        s=s.replace('0','A');
        return s;
    }
}
