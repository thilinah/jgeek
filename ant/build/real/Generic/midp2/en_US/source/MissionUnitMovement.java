// package dev.di.engine.data;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Feb 19, 2008
 * Time: 7:36:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class MissionUnitMovement {
    String mission;
    String unit;
    long time;
    boolean sent;

    public MissionUnitMovement() {
    }

    public MissionUnitMovement(String mission, String unit, long time, boolean sent) {
        this.mission = mission;
        this.unit = unit;
        this.time = time;
        this.sent = sent;
    }


    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
