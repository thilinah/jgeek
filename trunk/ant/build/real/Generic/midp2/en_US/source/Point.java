// package dev.di.map;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 14, 2008
 * Time: 7:53:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class Point {
    int x=0;
    int y=0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object object) {
        Point p=(Point)object;
        return (p.x==x && p.y==y);
    }
}
