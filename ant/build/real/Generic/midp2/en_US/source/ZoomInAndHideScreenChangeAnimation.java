//#condition polish.usePolishGui && polish.midp2

/*
 * Created on 30-May-2005 at 01:14:36.
 * 
 * Copyright (c) 2005 Robert Virkus / Enough Software
 *
 * This file is part of J2ME Polish.
 *
 * J2ME Polish is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * J2ME Polish is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with J2ME Polish; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * Commercial licenses are also available, please
 * refer to the accompanying LICENSE.txt or visit
 * http://www.j2mepolish.org for details.
 */
// package de.enough.polish.ui.screenanimations;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

//import de.enough.polish.ui.AccessibleCanvas;
//import de.enough.polish.ui.ScreenChangeAnimation;
//import de.enough.polish.ui.Style;
//import de.enough.polish.util.ImageUtil;

/**
 * <p>Magnifies the last screen and gradually resolves it.</p>
 *
 * <p>Copyright (c) 2005, 2006 Enough Software</p>
 * <pre>
 * history
 *        30-May-2005 - rob creation
 * </pre>
 * @author Robert Virkus, j2mepolish@enough.de
 */
public class ZoomInAndHideScreenChangeAnimation extends ScreenChangeAnimation {
	private int scaleFactor = 260;
	private int steps = 10;
	private int currentStep;
	private int[] lastScreenRgb;
	private int[] scaledScreenRgb;

	/**
	 * Creates a new animation 
	 */
	public ZoomInAndHideScreenChangeAnimation() {
		super();
	}


	/* (non-Javadoc)
	 * @see de.enough.polish.ui.ScreenChangeAnimation#show(de.enough.polish.ui.Style, javax.microedition.lcdui.Display, int, int, javax.microedition.lcdui.Image, javax.microedition.lcdui.Image, de.enough.polish.ui.Screen)
	 */
	protected void show(Style style, Display dsplay, int width, int height,
			Image lstScreenImage, Image nxtScreenImage, AccessibleCanvas nxtCanvas, Displayable nxtDisplayable  ) 
	{
		if ( this.lastScreenRgb == null ) {
			this.lastScreenRgb = new int[ width * height ];
			this.scaledScreenRgb = new int[ width * height ];
		}
		lstScreenImage.getRGB( this.lastScreenRgb, 0, width, 0, 0, width, height );		
		System.arraycopy( this.lastScreenRgb, 0, this.scaledScreenRgb, 0,  width * height );
		//ImageUtil.scale( 200, this.scaleFactor, this.screenWidth, this.screenHeight, this.lastScreenRgb, this.scaledScreenRgb);
		super.show(style, dsplay, width, height, lstScreenImage,
				nxtScreenImage, nxtCanvas, nxtDisplayable );
	}
	
	/* (non-Javadoc)
	 * @see de.enough.polish.ui.ScreenChangeAnimation#animate()
	 */
	protected boolean animate() {
		this.currentStep++;
		if (this.currentStep >= this.steps) {
			this.scaleFactor = 200;
			this.steps = 10;
			this.currentStep = 0;
			this.scaledScreenRgb = null;
			this.lastScreenRgb = null;
			return false;
		}
		int factor = 100 + (this.scaleFactor - 100) * this.currentStep / this.steps;
		int opacity = (255 * ( this.steps - this.currentStep ))  / this.steps;

		ImageUtil.scale(opacity, factor, this.screenWidth, this.screenHeight, this.lastScreenRgb, this.scaledScreenRgb);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.Canvas#paint(javax.microedition.lcdui.Graphics)
	 */
	public void paint(Graphics g) {
		//#if polish.Bugs.fullScreenInPaint
			//# if (! this.fullScreenModeSet ) {
				//# setFullScreenMode(true);
				//# this.fullScreenModeSet = true;
			//# }
		//#endif
		g.drawImage( this.nextCanvasImage, 0, 0, Graphics.TOP | Graphics.LEFT );
		g.drawRGB(this.scaledScreenRgb, 0, this.screenWidth, 0, 0, this.screenWidth, this.screenHeight, true );
		
		this.display.callSerially( this );
	}

}
