package dev.di.data.dao;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 26, 2007
 * Time: 9:23:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlanetDao extends AbstractDao{
    private String name;
	private String color;
	private String size;
	private int x;
	private int y;
	private int z;
	private int maxUnits;
	private int maxBuilding;
    private String map;

    public PlanetDao(String name, String color, String size, int x, int y, int z, int maxUnits, int maxBuilding,String map) {
        this.name = name;
        this.color = color;
        this.size = size;
        this.x = x;
        this.y = y;
        this.z = z;
        this.maxUnits = maxUnits;
        this.maxBuilding = maxBuilding;
        this.map=map;
    }

     public PlanetDao(String fullStr){
        numOfAttribs=9;
        String[] s=getAttributes(fullStr);
        name=s[0];
        color=s[1];
        size=s[2];
        x=Integer.parseInt(s[3]);
        y=Integer.parseInt(s[4]);
        z=Integer.parseInt(s[5]);
        maxUnits=Integer.parseInt(s[6]);
        maxBuilding=Integer.parseInt(s[7]);
        map=s[8];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getMaxUnits() {
        return maxUnits;
    }

    public void setMaxUnits(int maxUnits) {
        this.maxUnits = maxUnits;
    }

    public int getMaxBuilding() {
        return maxBuilding;
    }

    public void setMaxBuilding(int maxBuilding) {
        this.maxBuilding = maxBuilding;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String toString() {
        return name+"|"+color+"|"+size+"|"+String.valueOf(x)
                +"|"+String.valueOf(y)
                +"|"+String.valueOf(z)
                +"|"+String.valueOf(maxUnits)
                +"|"+String.valueOf(maxBuilding)
                +"|"+map
                +"|";
    }
}
