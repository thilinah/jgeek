// package dev.di.forms;

import javax.microedition.lcdui.Displayable;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 15, 2007
 * Time: 7:08:22 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ChildForm {
    ChildForm getParent();
    Displayable getDisplay();
}
