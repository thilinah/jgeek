// package dev.di.map;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 2, 2008
 * Time: 9:02:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AllowedCell {
    String[] unitType;
    int numberOfCells;
    int numberOfTypes;
    GroundCell[] allowed;
    boolean negative=false;
    int current=0;
    int currentT=0;

    public AllowedCell(int numberOfUnitTypes, int numberOfCells,boolean negative) {
        this.negative=negative;
        this.numberOfTypes = numberOfUnitTypes;
        this.numberOfCells = numberOfCells;
        allowed=new GroundCell[numberOfCells];
        unitType=new String[numberOfTypes];
    }

    public void addCell(GroundCell gc){
        allowed[current++]=gc;
    }

    public void addType(String type){
        unitType[currentT++]=type;
    }

    public String[] getUnitType() {
        return unitType;
    }

    public GroundCell[] getAllowed() {
        return allowed;
    }

    public boolean isNegative() {
        return negative;
    }

    public boolean containsType(String type){
        for(int i=0;i<numberOfTypes;i++){
            if(unitType[i].equals(type)){
                return true;
            }
        }
        return false;
    }

    public boolean containsCell(GroundCell cell){
        for(int i=0;i<numberOfCells;i++){
            if(allowed[i].equals(cell)){
                return true;
            }
        }
        return false;
    }
}
