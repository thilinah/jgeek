// package dev.di.util;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 16, 2007
 * Time: 10:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
//import javax.microedition.lcdui.Gauge;
import java.util.TimerTask;

/**
 * shows a progress bar when connection to the server
 */
public class ProgressTimer extends TimerTask {
    public boolean gaugeRunning=true;
    Gauge prograss;

    public ProgressTimer(boolean gaugeRunning, Gauge prograss) {
        this.gaugeRunning = gaugeRunning;
        this.prograss = prograss;
    }

    public void run() {
        if (gaugeRunning) {
            if (prograss.getValue() < prograss.getMaxValue()) {
                prograss.setValue(prograss.getValue() + 1);
            } else {
                gaugeRunning = false;
            }

        } else {
            cancel();
        }
    }
}
