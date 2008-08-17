// package dev.di.map;

//import dev.di.main.MainMid;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 23, 2008
 * Time: 5:49:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MovingTree1 extends Animation{
private static Image explosion1=null;

    protected MovingTree1(int x, int y) {
        super(x, y, 0, 2, 30);

    }

    protected Sprite getInitialSprite() {
         if(explosion1==null)
                explosion1= MainMid.getImage("/mtree1.png");
            return new Sprite(explosion1,16,26);
    }
}
