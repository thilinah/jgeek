// package dev.di.sound;

import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.MediaException;
import javax.microedition.media.control.ToneControl;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Oct 27, 2007
 * Time: 6:02:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class SoundSynthesizer {
    private static SoundSynthesizer me=null;

    private Player click=null;
    private Player humanFire=null;
    private Player machineFire=null;
    private Player smallExp=null;
    private Player bigExp=null;
    private Player moveMan=null;
    private Player moveTruck=null;
    private Player moveTruck2=null;
    private Player moveHeli=null;
    private Player movePlane=null;

    private SoundSynthesizer(){}

    public static SoundSynthesizer getInstance(){
        if(me==null)
            me=new SoundSynthesizer();
        return me;
    }

    
    public void playClickSound(){

        if(click==null || click.getState()==Player.CLOSED){
            click= getAudioPlayerMdi("resource:/s24.amr",1);
        }
        try {
            click.start();
        } catch (MediaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void playHumanFireSound(){

        if(humanFire==null || humanFire.getState()==Player.CLOSED){
            humanFire= getAudioPlayerMdi("resource:/s20.amr",1);
        }
        try {
            humanFire.start();
        } catch (MediaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    public void playMachineFireSound(){

        if(machineFire==null || machineFire.getState()==Player.CLOSED){
            machineFire= getAudioPlayerMdi("resource:/s24.amr",1);
        }
        try {
            machineFire.start();
        } catch (MediaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    public void playSmallExpFireSound(){

        if(smallExp==null || smallExp.getState()==Player.CLOSED){
            smallExp= getAudioPlayerMdi("resource:/s25.amr",1);
        }
        try {
            smallExp.start();
        } catch (MediaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


     public void playbigExpSound(){

        if(bigExp==null || bigExp.getState()==Player.CLOSED){
            bigExp= getAudioPlayerMdi("resource:/s31.amr",1);
        }
        try {
            bigExp.start();
        } catch (MediaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }



     public void playMoveManSound(){

        if(moveMan==null || moveMan.getState()==Player.CLOSED){
            moveMan= getAudioPlayerMdi("resource:/s40.amr",1);
        }
        try {
            moveMan.start();
        } catch (MediaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void playMoveTruckSound(){

        if(moveTruck==null || moveTruck.getState()==Player.CLOSED){
            moveTruck= getAudioPlayerMdi("resource:/s42.amr",1);
        }
        try {
            moveTruck.start();
        } catch (MediaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void playMoveTruckSound2(){

        if(moveTruck==null || moveTruck.getState()==Player.CLOSED){
            moveTruck= getAudioPlayerMdi("resource:/s41.amr",1);
        }
        try {
            moveTruck.start();
        } catch (MediaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void playMoveHeliSound(){

        if(moveHeli==null || moveHeli.getState()==Player.CLOSED){
            moveHeli= getAudioPlayerMdi("resource:/s45.amr",1);
        }
        try {
            moveHeli.start();
        } catch (MediaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void playMovePlaneSound(){

        if(movePlane==null || movePlane.getState()==Player.CLOSED){
            movePlane= getAudioPlayerMdi("resource:/s43.amr",1);
        }
        try {
            movePlane.start();
        } catch (MediaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }







    private Player getAudioPlayerMdi(String path, int loopCount) {
            Player p= null;
            try {
                p = createPlayer(path);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            p.setLoopCount(loopCount);
            return p;
    }




    private Player createPlayer(String url) throws Exception {
        if (url.startsWith("resource")) {
            int idx = url.indexOf(':');
            String loc = url.substring(idx + 1);
            InputStream is = getClass().getResourceAsStream(loc);
            String ctype = guessContentType(url);
            return Manager.createPlayer(is, ctype);
        } else {
            return Manager.createPlayer(url);
        }
    }

    private static String guessContentType(String url)
        throws Exception {
        String ctype;

        // some simple test for the content type
        if (url.endsWith("wav")) {
            ctype = "audio/angXY-wav";
        } else if (url.endsWith("jts")) {
            ctype = "audio/angXY-tone-seq";
        } else if (url.endsWith("mid")) {
            ctype = "audio/midi";
        } else if (url.endsWith("amr")) {
            ctype = "audio/amr";
        } else {
            throw new Exception("Cannot guess content type from URL: " + url);
        }

        return ctype;
    }
}
