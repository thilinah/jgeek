// package dev.di.map;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 23, 2008
 * Time: 4:12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Animation {
    int x;
    int y;
    int startFrame=0;
    int endFrame=0;
    Sprite sprite=null;
    boolean animatedOnce =false;
    int frameSkipRate=0;
    int frameCurrent=0;

    protected abstract Sprite getInitialSprite();



    protected Animation(int x, int y, int startFrame, int endFrame, int frameSkipRate) {
        this.x = x;
        this.y = y;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.sprite = getInitialSprite();
        this.sprite.setFrame(startFrame);
        this.sprite.setPosition(x,y);
        this.frameSkipRate=frameSkipRate;
    }

    public Sprite getSprite() {
        return sprite;
    }


    public boolean animate(Graphics g){
        if(animatedOnce){
            if(frameSkipRate==0){
                sprite.nextFrame();
            }
            else{
                frameCurrent++;
                if(frameCurrent>1000) frameCurrent=0;
                if(frameCurrent%frameSkipRate==0)
                    sprite.nextFrame();
            }
        }
        else{
            animatedOnce =true;
        }

        sprite.paint(g);
        if(sprite.getFrame()==endFrame){
            executeCustomMethods();
            return false;
        }
        return true;
    }

    public int getX() {
        return x;

    }

    public int getY() {
        return y;
    }

     public int getSizeX() {
        return sprite.getWidth();
    }

    public int getSizeY() {
        return sprite.getHeight();
    }

    public void setX(int x) {
        this.x = x;
        this.sprite.setPosition(x,y);
    }

    public void setY(int y) {
        this.y = y;
        this.sprite.setPosition(x,y);
    }

    public void executeCustomMethods(){

    }
}
