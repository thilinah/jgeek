package dev.di.map;

import dev.di.forms.ChildForm;
import dev.di.data.dao.PlanetDao;
import dev.di.main.MainMid;

import javax.microedition.lcdui.*;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 19, 2008
 * Time: 6:36:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestImageColour extends Canvas implements ChildForm, CommandListener {
    ChildForm parent = null;
    Command cmdBack = new Command("Leave Planet", Command.BACK, 1);

    public TestImageColour(String s, ChildForm parent) {
        //super(false);
        this.parent = parent;
        this.addCommand(cmdBack);
        setCommandListener(this);
        repaint();
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == cmdBack) {
            MainMid.getClientConfigurator().displayParent();
        }
    }

    protected void paint(Graphics g) {
        Image i1 = MainMid.getImage("/" + "War Bike_a.png");
        g.setColor(0, 255, 255);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(i1, getWidth() / 2, 10, Graphics.TOP | Graphics.HCENTER);
        Image u=replaceColour(i1);
        g.drawImage(u, getWidth() / 2, getHeight() / 2, Graphics.TOP | Graphics.HCENTER);

    }


    private Image replaceColour(Image img) {
        System.out.println("replaceColour");
        int[] byteArray=new int[img.getWidth()*img.getHeight()];
        img.getRGB(byteArray, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        //replace the color that you had put before for a transparent pixel
        System.out.println("RGB Array :"+byteArray);
        System.out.println("Total number of pix" +byteArray.length);
        int k=0;
        int k1=0;
        for (int i = 0; i < byteArray.length; i++) {
            //System.out.println(byteArray[i]);
            int a=(byteArray[i] & 0xff000000)>>24;
            int r=(byteArray[i] & 0x00ff0000)>>16;
            int g=(byteArray[i] & 0x0000ff00)>>8;
            int b=byteArray[i] & 0x000000ff;
            if (r>g && r>b) {
                r=b;
                //g=g;
                b=r;
                k++;
                byteArray[i] =b + (g<<8) + (r<<16) + (a<<24) ;
//                byteArray[i] =0xff00ff00 ;
            }
//            if (byteArray[i] > 0xFF000000) {
//                k1++;
//            }
        }
        System.out.println("ttx :"+k);
        //and then create the image with a transparent background
        Image img1 = Image.createRGBImage(byteArray, img.getWidth(), img.getHeight(), true);
        return img1;
    }

    public ChildForm getParent() {
        return parent;
    }

    public Displayable getDisplay() {
        return this;
    }
}
