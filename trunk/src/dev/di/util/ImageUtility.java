package dev.di.util;

import javax.microedition.lcdui.Image;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 19, 2008
 * Time: 8:47:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageUtility {
    public static Image replaceColourBlue(Image img) {
        int[] byteArray=new int[img.getWidth()*img.getHeight()];
        img.getRGB(byteArray, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        //replace the color that you had put before for a transparent pixel
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
                byteArray[i] =b + (g<<8) + (r<<16) + (a<<24) ;
//                byteArray[i] =0xff00ff00 ;
            }
//            if (byteArray[i] > 0xFF000000) {
//                k1++;
//            }
        }
        //and then create the image with a transparent background
        Image img1 = Image.createRGBImage(byteArray, img.getWidth(), img.getHeight(), true);
        return img1;
    }
}
