package dev.di.map;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Mar 27, 2008
 * Time: 7:10:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroundCell {

    public static int W=24;
    public static int H=12;

    public GroundCell() {
    }

    public GroundCell(int alpha, int x, int y) {
        this.alpha = alpha;
        this.x = x;
        this.y = y;
    }

    public GroundCell(int alpha, int x, int y, int ox, int oy) {
        this.alpha = alpha;
        this.x = x;
        this.y = y;
        this.ox = ox;
        this.oy = oy;
    }

    public int alpha=0;
    public int x=0;
    public int y=0;
    public int ox=0;
    public int oy=0;


    public boolean equals(Object object) {
        GroundCell g=(GroundCell)object;
        if(alpha!=g.alpha)
            return false;

        if(alpha==0){
            if(x==g.x && y==g.y){
                return true;
            }
            return false;
        }
        else{
            if(x==g.x && y==g.y && ox==g.ox && oy==g.oy ){
                return true;
            }
            return false;
        }
    }

    public String getUniqueKey() {
        if(alpha==0){
            return String.valueOf(alpha)+String.valueOf(x)+String.valueOf(y);
        }
        else{
           return String.valueOf(alpha)+String.valueOf(x)+String.valueOf(y)+String.valueOf(ox)+String.valueOf(oy);
        }
    }

    public String toString() {
        String s="";
        s+="a: "+alpha;
        s+=" x: "+x;
        s+=" y: "+y;
        s+=" ox: "+ox;
        s+=" oy: "+oy;
        return s;
    }
}
