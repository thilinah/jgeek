//#condition polish.usePolishGui

/*
 * Created on 27-May-2005 at 18:54:36.
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

/**
 * <p>Moves the new screen from the left to the front.</p>
 *
 * <p>Copyright (c) 2005, 2006 Enough Software</p>
 * <pre>
 * history
 *        31-May-2005 - mkoch creation
 * </pre>
 * @author Michael Koch, michael@enough.de
 */
public class VerticalFlashScreenChangeAnimation extends ScreenChangeAnimation
{	
	private int currentX;
	private int currentSize;
	//#if polish.css.vertical-flash-screen-change-animation-speed
	//# private int speed = 2;
	//#endif
	//#if polish.css.vertical-flash-screen-change-animation-color
	//# private int color = 0;
	//#endif

	/**
	 * Creates a new animation 
	 */
	public VerticalFlashScreenChangeAnimation()
	{
		// Do nothing here.
	}

	/* (non-Javadoc)
	 * @see de.enough.polish.ui.ScreenChangeAnimation#show(de.enough.polish.ui.Style, javax.microedition.lcdui.Display, int, int, javax.microedition.lcdui.Image, javax.microedition.lcdui.Image, de.enough.polish.ui.Screen)
	 */
	protected void show(Style style, Display dsplay, int width, int height,
											Image lstScreenImage, Image nxtScreenImage, AccessibleCanvas nxtCanvas, Displayable nxtDisplayable  ) 
	{
		this.currentX = width / 2;
		//#if polish.css.vertical-flash-screen-change-animation-speed
		//# Integer speedInt = style.getIntProperty(77);
//# 		
		//# if (speedInt != null)
		//# {
			//# this.speed = speedInt.intValue();
		//# }
		//#endif
		
		//#if polish.css.vertical-flash-screen-change-animation-color
		//# Integer colorInt = style.getIntProperty(78);
//# 		
		//# if (colorInt != null)
		//# {
			//# this.color = colorInt.intValue();
		//# }
		//#endif
		
		super.show(style, dsplay, width, height, lstScreenImage, nxtScreenImage, nxtCanvas, nxtDisplayable );
	}
	
	/* (non-Javadoc)
	 * @see de.enough.polish.ui.ScreenChangeAnimation#animate()
	 */
	protected boolean animate()
	{
		if (this.currentX > 0)
		{
			//#if polish.css.vertical-flash-screen-change-animation-speed
			//# this.currentX -= this.speed;
			//# this.currentSize += this.speed * 2;
			//#else
			this.currentX -= 2;
			this.currentSize += 4;
			//#endif
			
			if (this.currentX < 0)
			{
				this.currentX = 0;
				this.currentSize = this.screenWidth;
			}

			return true;
		}
		else
		{
			// Reset values.
			this.currentX = this.screenWidth / 2;
			this.currentSize = 0;

			return false;
		}
	}

	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.Canvas#paint(javax.microedition.lcdui.Graphics)
	 */
	public void paint(Graphics g)
	{
		//#if polish.Bugs.fullScreenInPaint
		//# if (! this.fullScreenModeSet)
		//# {
			//# setFullScreenMode(true);
			//# this.fullScreenModeSet = true;
		//# }
		//#endif
		
		g.drawImage(this.lastCanvasImage, 0, 0, Graphics.TOP | Graphics.LEFT);
		//#if polish.css.vertical-flash-screen-change-animation-color
		//# g.setColor(this.color);
		//#else
		g.setColor(0);
		//#endif
		g.drawRect(this.currentX - 1, 0, 0, this.screenHeight);
		g.drawRect(this.currentX + this.currentSize, 0, 0, this.screenHeight);
		g.setClip(this.currentX, 0, this.currentSize, this.screenHeight);
		g.drawImage(this.nextCanvasImage, 0, 0, Graphics.TOP | Graphics.LEFT);
		this.display.callSerially(this);
	}
}
