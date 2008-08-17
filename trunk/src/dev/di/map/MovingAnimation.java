package dev.di.map;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 23, 2008
 * Time: 4:12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class MovingAnimation {
    int x;
    int y;
    int lastX;
    int lastY;
    int startX;
    int startY;
    int endX;
    int endY;
    int startFrame = 0;
    int endFrame = 0;
    Sprite sprite = null;
    boolean animatedOnce = false;
    int frameSkipRate = 0;
    int frameCurrent = 0;
    int xSpeed = 0;
    int ySpeed = 0;
    Environment en = null;
    long creationTime=0;

    protected abstract Sprite getInitialSprite();

    protected MovingAnimation(Environment en, int startX, int startY, int endX, int endY, int frameSkipRate, int xSpeed) {
        this.startX = startX;
        this.startY = startY;
        this.lastX = startX;
        this.lastY = startY;
        this.endX = endX;
        this.endY = endY;

        this.sprite = getInitialSprite();
        this.sprite.setFrame(startFrame);
        this.sprite.setPosition(this.startX, this.startY);
        this.frameSkipRate = frameSkipRate;
        if((endX - startX)!=0){
            this.xSpeed = xSpeed * ((endX - startX) / Math.abs(endX - startX));
        }
        else{
            this.xSpeed=0;
        }
        if (this.xSpeed != 0) {
            ySpeed = ((endY - startY) * this.xSpeed) / (endX - startX);
        } else {
            ySpeed = xSpeed * ((endY - startY) / Math.abs(endY - startY));
        }

        this.startFrame = getStartFrame(this.xSpeed, ySpeed);
        this.endFrame = getStartFrame(this.xSpeed, ySpeed);


        this.en = en;
        creationTime=System.currentTimeMillis();
    }

    private void setSpriteNextFrame() {
        if (sprite.getFrame() < endFrame && sprite.getFrame() >= startFrame) {
            sprite.nextFrame();
        } else {
            sprite.setFrame(startFrame);
        }
    }

    boolean isAnimationEnd(int spx, int spy) {

        if(System.currentTimeMillis()-creationTime>1500){
            return true;
        }

        if (spx <= 0 && spy <= 0) {
            return (endX>=x || endY>=y);
        } else if (spx <= 0 && spy > 0) {
            return (endX>=x || endY<=y);
        } else if (spx > 0 && spy <= 0) {
            return (endX<=x || endY>=y);
        } else {
            return (endX<=x || endY<=y);
        }



    }

    void updatePosition(int spx, int spy) {

        if (spx <= 0 && spy <= 0) {
            if(endX<x) x = x + xSpeed;
            if(endY<y) y = y + ySpeed;
        } else if (spx <= 0 && spy > 0) {
            if(endX<x) x = x + xSpeed;
            if(endY>y) y = y + ySpeed;
        } else if (spx > 0 && spy <= 0) {
            if(endX>x) x = x + xSpeed;
            if(endY<y) y = y + ySpeed;
        } else {
            if(endX>x) x = x + xSpeed;
            if(endY>y) y = y + ySpeed;
        }
        moveSprite();
    }

    void moveSprite(){
        sprite.move(x-lastX, y-lastY);
        lastX=x;
        lastY=y;
    }

    public boolean animate(Graphics g) {
        if (animatedOnce) {
            if (frameSkipRate == 0) {
                setSpriteNextFrame();
                updatePosition(xSpeed,ySpeed);


            } else {
                frameCurrent++;
                if (frameCurrent > 1000) frameCurrent = 0;
                if (frameCurrent % frameSkipRate == 0) {
                    setSpriteNextFrame();
                    updatePosition(xSpeed,ySpeed);
                }
            }
        } else {
            animatedOnce = true;
            setSpriteNextFrame();
            x = startX;
            y = startY;
        }
        sprite.paint(g);

        if (isAnimationEnd(xSpeed,ySpeed)) {
            Animation a = getCustomAnimation();
            a.setX(endX);
            a.setY(endY);
            updateCustomAnimation(a);
            return false;
        }
        return true;
    }

    abstract Animation getCustomAnimation();

    abstract void updateCustomAnimation(Animation a);

    abstract int getStartFrame(int spx, int spY);

    abstract int getEndFrame(int spx, int spY);

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getSizeX() {
        return sprite.getWidth();
    }

    public int getSizeY() {
        return sprite.getHeight();
    }

    public void setStartFrame(int startFrame) {
        this.startFrame = startFrame;
    }

    public void setEndFrame(int endFrame) {
        this.endFrame = endFrame;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
