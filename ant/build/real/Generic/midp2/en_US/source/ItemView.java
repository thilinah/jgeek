//#condition polish.usePolishGui

/*
 * Created on Nov 27, 2006 at 1:06:40 PM.
 * 
 * Copyright (c) 2006 Robert Virkus / Enough Software
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
// package de.enough.polish.ui;

import javax.microedition.lcdui.Graphics;

/**
 * <p>An item view can take over the rendering of an item.</p>
 *
 * <p>Copyright Enough Software 2006</p>
 * <pre>
 * history
 *        Nov 27, 2006 - rob creation
 * </pre>
 * @author Robert Virkus, j2mepolish@enough.de
 */
public abstract class ItemView {
	
	protected int contentWidth;
	protected int contentHeight;
	protected int paddingVertical;
	protected int paddingHorizontal;
	protected int layout;
	protected boolean isLayoutCenter;
	protected boolean isLayoutRight;
	protected Item parentItem;
	protected boolean isFocused;
	
	/**
	 * Initialises this item view. 
	 * The implementation needs to calculate and set the contentWidth and 
	 * contentHeight fields. 
	 * The implementation should take the fields preferredWidth and preferredHeight
	 * into account.
	 * 
	 * @param parent the parent item
	 * @param firstLineWidth the maximum width of the first line 
	 * @param lineWidth the maximum width of any following lines
	 * @see #contentWidth
	 * @see #contentHeight
	 */
	protected abstract void initContent(Item parent, int firstLineWidth, int lineWidth);

	/**
	 * Paints this item view.
	 * 
	 * @param parent the parent item
	 * @param x the left start position
	 * @param y the upper start position
	 * @param leftBorder the left border, nothing must be painted left of this position
	 * @param rightBorder the right border, nothing must be painted right of this position
	 * @param g the Graphics on which this item should be painted.
	 */
	protected abstract void paintContent( Item parent, int x, int y, int leftBorder, int rightBorder, Graphics g );
	
	/**
	 * Calls the original initContent method on the parent.
	 * This is only useful when the parent should later onward paint (parts) of the item's interface.
	 * contentWidth and contentHeight fields are set according to the parent results.
	 * 
	 * @param parent the parent item
	 * @param firstLineWidth the maximum width of the first line 
	 * @param lineWidth the maximum width of any following lines
	 */
	protected void initContentByParent( Item parent, int firstLineWidth, int lineWidth) {
		parent.initContent(firstLineWidth, lineWidth);
		this.contentWidth = parent.contentWidth;
		this.contentHeight = parent.contentHeight;
	}

	/**
	 * Paints this item view by the parent.
	 * This could make sense if a specific view is only useful for a special case of the parent item.
	 * 
	 * @param parent the parent item
	 * @param x the left start position
	 * @param y the upper start position
	 * @param leftBorder the left border, nothing must be painted left of this position
	 * @param rightBorder the right border, nothing must be painted right of this position
	 * @param g the Graphics on which this item should be painted.
	 */
	protected void paintContentByParent( Item parent, int x, int y, int leftBorder, int rightBorder, Graphics g ) {
		parent.paintContent(x, y, leftBorder, rightBorder, g);
	}


		
	/**
	 * Sets the focus to this container view.
	 * The default implementation sets the style and the field "isFocused" to true.
	 * 
	 * @param focusstyle the appropriate style.
	 * @param direction the direction from the which the focus is gained, 
	 *        either Canvas.UP, Canvas.DOWN, Canvas.LEFT, Canvas.RIGHT or 0.
	 *        When 0 is given, the direction is unknown.1
	 * 
	 */
	public void focus(Style focusstyle, int direction) {
		this.isFocused = true;
		setStyle( focusstyle );
	}

	
	/**
	 * Notifies this view that the parent container is not focused anymore.
	 * Please call super.defocus() when overriding this method.
	 * The default implementation calls setStyle( originalStyle )
	 * and sets the field "isFocused" to false.
	 * 
	 * @param originalStyle the previous used style.
	 */
	protected void defocus( Style originalStyle ) {
		this.isFocused = false;
		setStyle( originalStyle );
	}
	
	
	/**
	 * Sets the style for this view.
	 * The style can include additional parameters for the view.
	 * Subclasses should call super.setStyle(style) first.
	 * 
	 * @param style the style
	 */
	protected void setStyle( Style style ) {
		//#debug
		//# System.out.println("Setting style for " + this + " with vertical padding=" + style.paddingVertical  );
		this.paddingHorizontal = style.paddingHorizontal;
		this.paddingVertical = style.paddingVertical;
		this.layout = style.layout;
		// horizontal styles: center -> right -> left
		if ( ( this.layout & Item.LAYOUT_CENTER ) == Item.LAYOUT_CENTER ) {
			this.isLayoutCenter = true;
			this.isLayoutRight = false;
		} else {
			this.isLayoutCenter = false;
			if ( ( this.layout & Item.LAYOUT_RIGHT ) == Item.LAYOUT_RIGHT ) {
				this.isLayoutRight = true;
			} else {
				this.isLayoutRight = false;
				// meaning: layout == Item.LAYOUT_LEFT
			}
		}		
	}
	
	/**
	 * Removes the background from the parent container so that the containerview implementation can paint it itself.
	 * 
	 * @return the background of the parent, can be null
	 */
	public Background removeParentBackground() {
		if (this.parentItem == null) {
			//#debug warn
			//# System.out.println("Unable to remove parent background when parent field is not set.");
			return null;
		}
		Background bg = this.parentItem.background;
		this.parentItem.background = null;
		return bg;
	}
	
	/**
	 * Removes the border from the parent container so that the containerview implementation can paint it itself.
	 * 
	 * @return the border of the parent, can be null
	 */
	public Border removeParentBorder() {
		if (this.parentItem == null) {
			//#debug warn
			//# System.out.println("Unable to remove parent border when parentContainer field is not set.");
			return null;
		}
		Border border = this.parentItem.border;
		this.parentItem.border = null;
		return border;
	}
	
	/**
	 * Animates this view.
	 * 
	 * @return true when the view was actually animated.
	 */
	public boolean animate() {
		return false;
	}

	/**
	 * Notifies this view that it is about to be shown (again).
	 * The default implementation does nothing.
	 */
	public void showNotify() {
		// subclasses can override this
	}
	
	/**
	 * Called by the system to notify the item that it is now completely
	 * invisible, when it previously had been at least partially visible.  No
	 * further <code>paint()</code> calls will be made on this item
	 * until after a <code>showNotify()</code> has been called again.
	 */
	public void hideNotify() {
		// subclasses can override this
	}
	
	/**
	 * Retrieves the screen to which this view belongs to.
	 * This is necessary since the getScreen()-method of item has only protected
	 * access. The screen can be useful for setting the title for example. 
	 * 
	 * @return the screen in which this view is embedded.
	 */
	protected Screen getScreen() {
		return this.parentItem.getScreen();
	}
	
	/**
	 * Handles the given keyPressed event when the currently focused item was not able to handle it.
	 * The default implementation just calls getNextItem() and focuses the returned item.
	 * 
	 * @param keyCode the key code
	 * @param gameAction the game action like Canvas.UP etc
	 * @return true when the key was handled.
	 */
	public boolean handleKeyPressed( int keyCode, int gameAction) {
		return false;
	}

	//#ifdef polish.hasPointerEvents
	//# /**
	 //# * Handles pointer pressed events.
	 //# * This is an optional feature that doesn't need to be implemented by subclasses, since the parent container already forwards the event to the appropriate item (when this method returns false).
	 //# * The default implementation just returns false.
	 //# * You only need to implement this method when there are pointer events:
	 //# * <pre>
	 //# * //#if polish.hasPointerEvents
	 //# * </pre>
	 //# * 
	 //# * @param x the x position of the event
	 //# * @param y the y position of the event
	 //# * @return true when the event has been handled. When false is returned the parent container
	 //# *         will forward the event to the affected item.
	 //# */
	//# public boolean handlePointerPressed(int x, int y) {
		//# return false;
	//# }
	//#endif
	

}
