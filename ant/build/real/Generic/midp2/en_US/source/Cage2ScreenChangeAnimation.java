//#condition polish.usePolishGui && polish.midp2

/*
 * Created on 16.09.2005 at 16:54:17.
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

public class Cage2ScreenChangeAnimation extends ScreenChangeAnimation {
	private boolean stillRun = true;
	//row = wo sich das neue image befindet am display
	private int row = 0;
	private int[] rgbNew ;
	private int[] rgbimage ;
	private int width, height;
	
	public Cage2ScreenChangeAnimation() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	protected void show(Style style, Display dsplay, int width, int height,
			Image lstScreenImage, Image nxtScreenImage, AccessibleCanvas nxtCanvas, Displayable nxtDisplayable  ) 
	{
			int size = nxtScreenImage.getWidth() * nxtScreenImage.getHeight();
			this.height = height;
			this.width = width;
			this.rgbNew = new int [size];
			this.rgbimage = new int[size];
			nxtScreenImage.getRGB(this.rgbNew, 0, width, 0, 0, width, height );
			lstScreenImage.getRGB(this.rgbimage, 0, width, 0, 0, width, height );
			super.show(style, dsplay, width, height, lstScreenImage, nxtScreenImage, nxtCanvas, nxtDisplayable );
	}

	
	
	protected boolean animate() {
		// TODO Auto-generated method stub
		boolean Rowswitch = true;
		int row = 0;
		for(int i = 0; i < this.rgbimage.length;i++){		
			if(row <= this.row && Rowswitch){
				this.rgbimage[i] = this.rgbNew[i];
			}
			else if(row >= this.width-this.row && !Rowswitch){
				this.rgbimage[i] = this.rgbNew[i];
			}	
			row = (row + 1) % this.width;
			if(row == 0){
				if(Rowswitch){
					Rowswitch=false;
				}else{	
					Rowswitch=true;		
					}
				}
		}
		this.row+=4;
		if(this.row >= this.width)this.stillRun=false;
		return this.stillRun;
	}

	public void paint(Graphics g) {
		g.drawRGB(this.rgbimage,0,this.width,0,0,this.width,this.height,false);
		// TODO Auto-generated method stub
		this.display.callSerially( this );
	}

}
