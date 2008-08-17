package dev.di.map;

import dev.di.main.MainMid;

import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 24, 2008
 * Time: 12:42:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class BigFire extends MovingAnimation {

    private static Image explosion1=null;

    public BigFire(Environment en, int startX, int startY, int endX, int endY) {
        super(en, startX, startY, endX, endY, 0, 7);
        //setStartFrame(getStartFrame(dir));
        //setEndFrame(getEndFrame(dir));
    }

    protected Sprite getInitialSprite() {
       if(explosion1==null)
                explosion1= MainMid.getImage("/fire1.png");
            return new Sprite(explosion1,22,16);
    }

    Animation getCustomAnimation() {
        return new BigExplosion(0,0,en);
    }

    void updateCustomAnimation(Animation a) {
        en.addExplosion(a);
    }

    int getStartFrame(int spx, int spy) {
        if(spx<=0 && spy<=0){
            if(Math.abs(spx)>Math.abs(spy))
                return 6;
            else
                return 9;
        }
        else if(spx<=0 && spy>0){
            if(Math.abs(spx)>Math.abs(spy))
                return 6;
            else
                return 3;
        }
        else if(spx>0 && spy<=0){
            if(Math.abs(spx)>Math.abs(spy))
                return 0;
            else
                return 9;
        }
        else{
            if(Math.abs(spx)>Math.abs(spy))
                return 0;
            else
                return 3;
        }

    }

    int getEndFrame(int spx, int spY) {
        return getStartFrame(spx,spY)+2;
    }
//    private int getStartFrame(int dir) {
//        if (dir == 1)
//            return 9;
//        else if (dir == 2)
//            return 0;
//        else if (dir == 3)
//            return 3;
//        else
//            return 6;
//    }
//
//    private int getEndFrame(int dir) {
//        if (dir == 1)
//            return 11;
//        else if (dir == 2)
//            return 2;
//        else if (dir == 3)
//            return 5;
//        else
//            return 8;
//    }


}
