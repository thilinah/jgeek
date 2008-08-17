// package dev.di.map;

//import dev.di.main.MainMid;

import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;
/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 18, 2008
 * Time: 8:32:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmallFire extends MovingAnimation{
    private static Image explosion1=null;

    public SmallFire(Environment en, int startX, int startY, int endX, int endY) {
        super(en, startX, startY, endX, endY, 0, 12);
        //setStartFrame(getStartFrame(dir));
        //setEndFrame(getEndFrame(dir));
    }

    protected Sprite getInitialSprite() {
       if(explosion1==null)
                explosion1= MainMid.getImage("/fire2.png");
            return new Sprite(explosion1,6,6);
    }

    Animation getCustomAnimation() {
        return new SmallExplosion(0,0,en);
    }

    void updateCustomAnimation(Animation a) {
        en.addExplosion(a);
    }

    int getStartFrame(int spx, int spy) {
       return 0;

    }

    int getEndFrame(int spx, int spY) {
        return 2;
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
