//#condition polish.usePolishGui
/*
 * Created on 12-Mar-2004 at 21:46:17.
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


import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

//#if polish.doja
//# import com.nttdocomo.ui.Frame;
//#endif

//import de.enough.polish.ui.backgrounds.TranslucentSimpleBackground;
//import de.enough.polish.util.ArrayList;
//import de.enough.polish.util.Locale;

//#ifdef polish.Screen.imports:defined
	//#include ${polish.Screen.imports}
//#endif


/**
 * The common superclass of all high-level user interface classes.
 * 
 * The contents displayed and their interaction with the user are defined by
 * subclasses.
 * 
 * <P>Using subclass-defined methods, the application may change the contents
 * of a <code>Screen</code> object while it is shown to the user.  If
 * this occurs, and the
 * <code>Screen</code> object is visible, the display will be updated
 * automatically.  That
 * is, the implementation will refresh the display in a timely fashion without
 * waiting for any further action by the application.  For example, suppose a
 * <code>List</code> object is currently displayed, and every element
 * of the <code>List</code> is
 * visible.  If the application inserts a new element at the beginning of the
 * <code>List</code>, it is displayed immediately, and the other
 * elements will be
 * rearranged appropriately.  There is no need for the application to call
 * another method to refresh the display.</P>
 * 
 * <P>It is recommended that applications change the contents of a
 * <code>Screen</code> only
 * while it is not visible (that is, while another
 * <code>Displayable</code> is current).
 * Changing the contents of a <code>Screen</code> while it is visible
 * may result in
 * performance problems on some devices, and it may also be confusing if the
 * <code>Screen's</code> contents changes while the user is
 * interacting with it.</P>
 * 
 * <P>In MIDP 2.0 the four <code>Screen</code> methods that defined
 * read/write ticker and
 * title properties were moved to <code>Displayable</code>,
 * <code>Screen's</code> superclass.  The
 * semantics of these methods have not changed.</P>
 * <HR>
 * 
 * @since MIDP 1.0
 * 
 */
public abstract class Screen
//#if polish.Bugs.needsNokiaUiForSystemAlerts && !polish.SystemAlertNotUsed
	//#define tmp.needsNokiaUiForSystemAlerts
//#endif
//#if polish.hasCommandKeyEvents || (polish.key.LeftSoftKey:defined && polish.key.RightSoftKey:defined)
	//#define tmp.hasCommandKeyEvents
//#endif
//#if polish.useFullScreen
	//#if (polish.midp2 && !tmp.needsNokiaUiForSystemAlerts) && (!polish.useMenuFullScreen || tmp.hasCommandKeyEvents)
		//#define tmp.fullScreen
		//# extends Canvas
	//#elif polish.classes.fullscreen:defined
		//#define tmp.fullScreen
		//#= extends ${polish.classes.fullscreen}
	//#endif
//#endif
//#if !tmp.fullScreen
	extends Canvas
//#endif
implements AccessibleCanvas
{
	private final static int POSITION_TOP = 0;
	private final static int POSITION_LEFT = 1;
	
	//#if tmp.fullScreen || polish.midp1 || (polish.usePolishTitle == true)
		//#define tmp.usingTitle
		//# protected Item title;
		//# private boolean excludeTitleForBackground;
		//#ifdef polish.css.title-style
			//# private Style titleStyle;
		//#endif
		//#if polish.css.title-position
			//# private boolean paintTitleAtTop = true;
		//#endif	
		//#if polish.Vendor.Motorola || polish.Bugs.ignoreTitleCall
			//#define tmp.ignoreMotorolaTitleCall
			//# private boolean ignoreMotorolaTitleCall = true;
		//#endif
	//#endif

	//#ifdef polish.Vendor.Siemens
		//# // Siemens sometimes calls hideNotify directly
		//# // after showNotify for some reason.
		//# // So hideNotify checks how long the screen
		//# // has been shown - if not long enough,
		//# // the call will be ignored.
		//# private long showNotifyTime;
	//#endif
	private Item subTitle;
	protected int subTitleHeight;
	protected int titleHeight;
	protected Background background;
	protected Border border;
	protected Style style;
	/** the screen height minus the ticker height and the height of the menu bar */
	protected int screenHeight;
	/** the screen height minus the height of the menu bar */
	protected int originalScreenHeight;
	protected final int screenWidth;
	//#ifndef polish.skipTicker
		//# private Ticker ticker;
		//#if polish.Ticker.Position:defined
			//#if top == ${ lowercase(polish.Ticker.Position) }
				//#define tmp.paintTickerAtTop
			//#else
				//#define tmp.paintTickerAtBottom
			//#endif
		//#elif polish.css.ticker-position
			//# private boolean paintTickerAtTop;
		//#else
			//#define tmp.paintTickerAtBottom
		//#endif
	//#endif
	protected String cssSelector;
	private ForwardCommandListener forwardCommandListener;
	protected Container container;
	private boolean isLayoutCenter;
	private boolean isLayoutRight;
	private boolean isLayoutVCenter;
	private boolean isLayoutBottom;
	private boolean isLayoutHorizontalShrink;
	private boolean isLayoutVerticalShrink;
	private boolean isInitialized;
	//#if polish.ScreenChangeAnimation.forward:defined
		//# protected Command lastTriggeredCommand;
	//#endif	
	//#if (polish.useMenuFullScreen && tmp.fullScreen) || polish.needsManualMenu
		//#define tmp.menuFullScreen
		//# /** the real, complete height of the screen - this includes title, subtitle, content and menubar */
		//# protected int fullScreenHeight;
		//# protected int menuBarHeight;
		//# private boolean excludeMenuBarForBackground;
		//#ifdef polish.key.ReturnKey:defined
			//# private Command backCommand;
		//#endif
//# 		
		//# private Command okCommand;
		//#if polish.MenuBar.useExtendedMenuBar || polish.classes.MenuBar:defined
			//#if polish.classes.MenuBar:defined
				//#= private final ${polish.classes.MenuBar} menuBar;
			//#else
				//# private final MenuBar menuBar;
			//#endif
			//#define tmp.useExternalMenuBar
		//#else
			//#ifdef polish.key.LeftSoftKey:defined
				//#= private final static int LEFT_SOFT_KEY = ${polish.key.LeftSoftKey};
			//#else
				//# private final static int LEFT_SOFT_KEY = -6;
			//#endif
			//#ifdef polish.key.RightSoftKey:defined
				//#= private final static int RIGHT_SOFT_KEY = ${polish.key.RightSoftKey};
			//#else
				//# private final static int RIGHT_SOFT_KEY = -7;
			//#endif
			//# private Command menuSingleLeftCommand;
			//# private String menuLeftString;
			//# private Command menuSingleRightCommand;
			//# private String menuRightString;
			//# private Container menuContainer;
			//# private ArrayList menuCommands;
			//# private boolean menuOpened;
			//# private Font menuFont;
			//# private int menuFontColor = 0;
			//# private int menuBarColor = 0xFFFFFF;
			//#ifdef polish.hasPointerEvents
				//# private int menuRightCommandX;
				//# private int menuLeftCommandX;
			//#endif
		//#endif
	//#endif
	/** The currently focused items which has item-commands */
	private Item focusedItem;
	//#if polish.useScrollBar || polish.classes.ScrollBar:defined
		//#define tmp.useScrollBar
		//#if polish.classes.ScrollBar:defined
			//#style scrollbar?
			//#= protected final ${polish.classes.ScrollBar} scrollBar = new ${polish.classes.ScrollBar}();
		//#else
			//#style scrollbar?
			protected final ScrollBar scrollBar = new ScrollBar();
		//#endif
		//#if polish.css.scrollbar-position
			//# protected boolean paintScrollBarOnRightSide = true;
		//#endif
	//#elif !polish.deactivateScrollIndicator
		//#define tmp.useScrollIndicator
		//# private boolean paintScrollIndicator;
		//# private boolean paintScrollIndicatorUp;
		//# private boolean paintScrollIndicatorDown;
		//# private int scrollIndicatorColor;
		//# private int scrollIndicatorX; // left x position of scroll indicator
		//# private int scrollIndicatorY; // top y position of scroll indicator
		//# private int scrollIndicatorWidth; // width of the indicator
		//# private int scrollIndicatorHeight; // width of the indicator
		//#if polish.css.scrollindicator-up-image || polish.css.scrollindicator-down-image 
			//# private Image scrollIndicatorUpImage; 
			//# private Image scrollIndicatorDownImage; 
		//#endif
	//#endif
	//#if tmp.usingTitle || tmp.menuFullScreen
		//# private boolean showTitleOrMenu = true;
	//#endif
	/** an info text which is shown e.g. when some content is added to textfields */
	private StringItem infoItem;
	/** determines whether the info text should be shown */
	private boolean showInfoItem;
	protected int infoHeight;
	//#if tmp.fullScreen && polish.midp2 && polish.Bugs.fullScreenInPaint
		//#define tmp.fullScreenInPaint
		//# private boolean isInFullScreenMode;
	//#endif
	//#ifdef polish.css.foreground-image
		//# private Image foregroundImage;
		//# private int foregroundX;
		//# private int foregroundY;
	//#endif
	//#if polish.css.clip-screen-info
		//# private boolean clipScreenInfo;
	//#endif	
	//#if polish.blackberry
		//# public boolean keyPressedProcessed;
	//#endif
	protected int contentX;
	protected int contentY;
	protected int contentWidth;
	protected int contentHeight;
	private int marginLeft;
	private int marginRight;
	private int marginTop;
	private int marginBottom;
	//#if polish.css.separate-menubar
		//# private boolean separateMenubar = true;
	//#endif
	//#if polish.css.repaint-previous-screen
		private boolean repaintPreviousScreen;
		//#if polish.Screen.dontBufferPreviousScreen
			//# private AccessibleCanvas previousScreen;
		//#else
			private Image previousScreenImage;
		//#endif
		//#if !polish.Bugs.noTranslucencyWithDrawRgb
			private Background previousScreenOverlayBackground;
		//#endif
	//#endif
	protected ScreenStateListener screenStateListener;
	private boolean isScreenChangeDirtyFlag;
	private final Object paintLock = new Object();
	private ArrayList itemCommands;
	private Object data;

	/**
	 * Creates a new screen, this constructor can be used together with the //#style directive.
	 * 
	 * @param title the title, or null for no title
	 * @param createDefaultContainer true when the default container should be created.
	 */
	public Screen( String title, boolean createDefaultContainer ) {
		this( title, null, createDefaultContainer );
	}

	/**
	 * Creates a new screen, this constructor can be used together with the //#style directive.
	 * 
	 * @param title the title, or null for no title
	 * @param style the style of this screen
	 * @param createDefaultContainer true when the default container should be created.
	 */
	public Screen( String title, boolean createDefaultContainer, Style style ) {
		this( title, style, createDefaultContainer );
	}

	/**
	 * Creates a new screen
	 * 
	 * @param title the title, or null for no title
	 * @param style the style of this screen
	 * @param createDefaultContainer true when the default container should be created.
	 */
	public Screen( String title, Style style, boolean createDefaultContainer ) {
		super();
		//#if !(polish.Bugs.fullScreenInShowNotify || polish.Bugs.fullScreenInPaint || tmp.needsNokiaUiForSystemAlerts)
			//#if tmp.fullScreen && polish.midp2
				//# super.setFullScreenMode( true );
			//#endif			
		//#endif
			
		// get the screen dimensions:
		// this is a bit complicated, since Nokia's FullCanvas fucks
		// up when calling super.getHeight(), so we need to use hardcoded values...
		
		//#ifdef tmp.menuFullScreen
			//#if polish.needsManualMenu && !tmp.fullScreen
				//# this.fullScreenHeight = getHeight();
			//#else
				//#if tmp.needsNokiaUiForSystemAlerts
					//#ifdef polish.NokiaFullCanvasHeight:defined
						//#= this.fullScreenHeight = ${ polish.NokiaFullCanvasHeight };
					//#else
						//# this.fullScreenHeight = getHeight();
					//#endif
				//#else
					//#ifdef polish.FullCanvasHeight:defined
						//#= this.fullScreenHeight = ${ polish.FullCanvasHeight };
					//#else
						//# this.fullScreenHeight = getHeight();
					//#endif
				//#endif
			//#endif
			//# this.screenHeight = this.fullScreenHeight; 
		//#else
			this.screenHeight = getHeight();
		//#endif
		this.originalScreenHeight = this.screenHeight;
		
		//#ifdef polish.ScreenWidth:defined
			//#= this.screenWidth = ${ polish.ScreenWidth };
		//#else
			this.screenWidth = getWidth();
		//#endif
		//#if tmp.useScrollBar
			this.scrollBar.screen = this;
		//#endif
						
		// creating standard container:
		if (createDefaultContainer) {
			this.container = new Container( true );
			this.container.screen = this;
			this.container.isFocused = true;
		}
		this.style = style;
		this.forwardCommandListener = new ForwardCommandListener();
		//#ifndef tmp.menuFullScreen
			super.setCommandListener(this.forwardCommandListener);
		//#endif
		//#ifdef tmp.useExternalMenuBar
			//#style menubar, menu, default
			//# this.menuBar = new MenuBar( this );
		//#endif
		setTitle( title );
	}
		
	/**
	 * Initialises this screen before it is painted for the first time.
	 */
	private void init() {
		//#debug
		//# System.out.println("Initialising screen " + this );
		boolean startAnimationThread = false;
		if (StyleSheet.animationThread == null) {
			StyleSheet.animationThread = new AnimationThread();
			startAnimationThread = true;
		}
		//#ifdef polish.Screen.initCode:defined
			//#include ${polish.Screen.initCode}
		//#endif
		if (this.style != null) {
			setStyle( this.style );
		}
		//#ifdef polish.useDynamicStyles
			//# // check if this screen has got a style:
			//# if (this.style == null) {
				//# this.cssSelector = createCssSelector();
				//# setStyle( StyleSheet.getStyle( this ) );
			//# } else {
				//# this.cssSelector = this.style.name;
			//# }
		//#endif
		//#ifdef tmp.menuFullScreen
			//#ifdef tmp.useExternalMenuBar
				//# int availableScreenWidth = this.screenWidth - (this.marginLeft + this.marginRight);
				//# this.menuBarHeight = this.menuBar.getItemHeight( availableScreenWidth, availableScreenWidth );
				//#if tmp.useScrollIndicator
					//# int scrollWidth = this.menuBar.contentHeight + this.menuBar.paddingTop + this.menuBar.paddingBottom;
					//# int scrollHeight = scrollWidth;
					//#if polish.css.scrollindicator-up-image
						//# if (this.scrollIndicatorUpImage != null) {
							//# scrollWidth = this.scrollIndicatorUpImage.getWidth(); 
							//# scrollHeight = this.scrollIndicatorUpImage.getHeight();
						//# }
					//#elif polish.css.scrollindicator-down-image
						//# if (this.scrollIndicatorDownImage != null) {
							//# scrollWidth = this.scrollIndicatorDownImage.getWidth(); 
							//# scrollHeight = this.scrollIndicatorDownImage.getHeight();
						//# }
					//#endif
					//# this.scrollIndicatorWidth = scrollWidth;
					//# this.scrollIndicatorHeight = scrollHeight;
					//# this.scrollIndicatorX = (this.screenWidth >> 1) - (scrollWidth >> 1);
					//# int space = this.menuBarHeight - ((scrollHeight << 1) + 1);
					//# System.out.println("space=" + space + ", menubarHEIGHT="+ this.menuBarHeight);
					//# this.scrollIndicatorY = (this.fullScreenHeight - this.menuBarHeight) + (space >> 1);
				//#endif
				//# //System.out.println("without ExternalMenu: scrollIndicatorY=" + this.scrollIndicatorY + ", screenHeight=" + this.screenHeight + ", FullScreenHeight=" + this.fullScreenHeight );	
				//# //System.out.println("Screen.init: menuBarHeight=" + this.menuBarHeight + " scrollIndicatorWidth=" + this.scrollIndicatorWidth );
			//#else
				//#ifdef polish.css.style.menu
					//# Style menustyle = StyleSheet.menuStyle;
				//#else
					//# Style menustyle = this.style;
				//#endif
				//# if (menustyle != null) {
					//# Integer colorInt = null;
					//# if (this.style != null) {
						//# colorInt = this.style.getIntProperty(16);
					//# }
					//# if (colorInt == null) {
						//# colorInt = menustyle.getIntProperty(16);
					//# }
					//# if (colorInt != null) {
						//# this.menuBarColor = colorInt.intValue();
					//# }
					//# this.menuFontColor = menustyle.getFontColor();
					//# if (menustyle.font != null) {
						//# this.menuFont = menustyle.font;
					//# } else {
						//# this.menuFont = Font.getFont( Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM );				
					//# }			
				//# } else {
					//# this.menuFont = Font.getFont( Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM );
				//# }
				//# int localMenuBarHeight = this.menuFont.getHeight();
				//#ifdef polish.MenuBar.PaddingBottom:defined
					//#= localMenuBarHeight += ${polish.MenuBar.PaddingBottom};  
				//#endif
				//#ifdef polish.MenuBar.PaddingTop:defined
					//#= localMenuBarHeight += ${polish.MenuBar.PaddingTop};  
				//#endif
				//#if !polish.MenuBar.PaddingBottom:defined && !polish.MenuBar.PaddingTop:defined
					//# localMenuBarHeight += 2;
				//#endif
				//#if tmp.useScrollIndicator
					//# int scrollWidth = localMenuBarHeight;
					//# int scrollHeight = scrollWidth;
					//#if polish.css.scrollindicator-up-image
						//# if (this.scrollIndicatorUpImage != null) {
							//# scrollWidth = this.scrollIndicatorUpImage.getWidth(); 
							//# scrollHeight = this.scrollIndicatorUpImage.getHeight();
						//# }
					//#elif polish.css.scrollindicator-down-image
						//# if (this.scrollIndicatorDownImage != null) {
							//# scrollWidth = this.scrollIndicatorDownImage.getWidth(); 
							//# scrollHeight = this.scrollIndicatorDownImage.getHeight();
						//# }
					//#endif
					//# this.scrollIndicatorWidth = scrollWidth;
					//# this.scrollIndicatorHeight = scrollHeight;
					//# this.scrollIndicatorX = this.screenWidth / 2 - scrollWidth / 2;
					//# int space = localMenuBarHeight - ((scrollHeight << 1) + 1);
					//# this.scrollIndicatorY = (this.fullScreenHeight - localMenuBarHeight) + (space >> 1);
				//#endif
				//# //System.out.println("without ExternalMenu: scrollIndicatorY=" + this.scrollIndicatorY + ", screenHeight=" + this.screenHeight + ", FullScreenHeight=" + this.fullScreenHeight + ", localMenuBarHeight=" + localMenuBarHeight);	
				//#ifdef polish.MenuBar.MarginBottom:defined 
					//#= localMenuBarHeight += ${polish.MenuBar.MarginBottom};
					//#= this.scrollIndicatorY -=  ${polish.MenuBar.MarginBottom};
				//#endif
				//#ifdef polish.MenuBar.MarginTop:defined 
					//#= localMenuBarHeight += ${polish.MenuBar.MarginTop};
				//#endif
				//#if polish.doja
					//# localMenuBarHeight = 0;
				//#endif
				//# this.menuBarHeight = localMenuBarHeight;
				//# updateMenuTexts();
			//#endif
			//# int diff = this.originalScreenHeight - this.screenHeight;
			//# this.originalScreenHeight = this.fullScreenHeight - this.menuBarHeight;
			//# this.screenHeight = this.originalScreenHeight - diff;
			//# // set position of scroll indicator:
		//#elif polish.vendor.Siemens && tmp.useScrollIndicator
			//# // set the position of scroll indicator for Siemens devices 
			//# // on the left side, so that the menu-indicator is visible:
			//# int scrollWidth = 12;
			//#if polish.css.scrollindicator-up-image
				//# if (this.scrollIndicatorUpImage != null) {
					//# scrollWidth = this.scrollIndicatorUpImage.getWidth(); 
				//# }
			//#elif polish.css.scrollindicator-down-image 
				//# if (this.scrollIndicatorDownImage != null) {
					//# scrollWidth = this.scrollIndicatorDownImage.getWidth(); 
				//# }
			//#endif
			//# this.scrollIndicatorWidth = scrollWidth;
			//# this.scrollIndicatorX = 0;
			//# this.scrollIndicatorY = this.screenHeight - (this.scrollIndicatorWidth + 1);
		//#elif tmp.useScrollIndicator
			//# // set position of scroll indicator:
			//# int scrollWidth = 12;
			//#if polish.css.scrollindicator-up-image
				//# if (this.scrollIndicatorUpImage != null) {
					//# scrollWidth = this.scrollIndicatorUpImage.getWidth(); 
				//# }
			//#elif polish.css.scrollindicator-down-image 
				//# if (this.scrollIndicatorDownImage != null) {
					//# scrollWidth = this.scrollIndicatorDownImage.getWidth(); 
				//# }
			//#endif
			//# this.scrollIndicatorWidth = scrollWidth;
			//# this.scrollIndicatorX = this.screenWidth - this.scrollIndicatorWidth;
			//# this.scrollIndicatorY = this.screenHeight - (this.scrollIndicatorWidth + 1);
		//#endif
			
		//System.out.println("final: scrollIndicatorY=" + this.scrollIndicatorY + ", screenHeight=" + this.screenHeight + ", FullScreenHeight=" + this.fullScreenHeight );	
		if (this.container != null) {
			this.container.screen = this;
		}
		calculateContentArea( 0, 0, this.screenWidth, this.screenHeight );
		
		// start the animmation thread if necessary: 
		if (startAnimationThread) {
			StyleSheet.animationThread.start();
		}
		this.isInitialized = true;
	}
		
	/**
	 * Calculates and sets the content area for this screen.
	 * Usually no items are painted outside of the specified area.
	 * This method knows about the title, subtitle, infoarea and ticker
	 * and adjusts the content area accordingly
	 * 
	 * @param x left start of the content area, might later be adjusted by an external scrollindicator
	 * @param y top start of the content area, is adjusted by the top margin, title height, subtitle height, 
	 *        info height and maybe ticker height (when the ticker should be painted at the top).
	 * @param width width of the content area, might later be adjusted by an external scrollindicator
	 * @param height height of the content area, is adjusted by the title height, subtitle height, 
	 *        info height and ticker height.
	 */
	protected void calculateContentArea( int x, int y, int width, int height ) {
		//#debug
		//# System.out.println("calculateContentArea(" + x + ", " + y + ", " + width + ", " + height + ")");
		
		x += this.marginLeft;
		width -= this.marginLeft + this.marginRight;
		y += this.marginTop;
		height -= this.marginTop; // + this.marginBottom;
		//#if !polish.css.title-position || !tmp.usingTitle
			int topHeight = this.titleHeight + this.subTitleHeight + this.infoHeight;
		//#else
			//# int topHeight = this.subTitleHeight + this.infoHeight;
			//# if (this.paintTitleAtTop) {
				//# topHeight += this.titleHeight;
			//# } else {
				//# height -= this.titleHeight;
			//# }
		//#endif
		y += topHeight;
		//#ifndef polish.skipTicker			
			//# int tickerHeight = 0;
			//# if (this.ticker != null) {
				//# tickerHeight = this.ticker.getItemHeight( width, width );
			//# } 	
			//# height -= topHeight + tickerHeight;
		//#else
			height -= topHeight;	
		//#endif
		//#if tmp.useScrollBar
			if ( this.container != null ) {
				this.scrollBar.scrollBarHeight = height;
				//System.out.println("calculateContentArea for " + this + ": container.isInitialised=" + this.container.isInitialised );
				int scrollBarWidth = this.scrollBar.getItemWidth(width, width);
				int containerHeight = this.container.getItemHeight(width - scrollBarWidth, width - scrollBarWidth);
				if (containerHeight > height) {
					//System.out.println("calculateContentArea for" + this + ": scrollBar is required for containerHeight of " + containerHeight + ", availableHeight=" + height );					
					width -= scrollBarWidth;
				} else {
					//System.out.println("calculateContentArea for" + this + ": scrollBar is NOT required for containerHeight of " + containerHeight + ", availableHeight=" + height );					
					this.container.requestFullInit();
				}
			}
		//#endif
			
		// now set the content coordinates:	
		this.contentX = x;
		//#ifndef polish.skipTicker			
			//#if tmp.paintTickerAtTop
				//# y += tickerHeight;
			//#elif polish.css.ticker-position && !polish.TickerPosition:defined
				//# if (this.paintTickerAtTop) {
					//# y += tickerHeight;
				//# }
			//#endif
		//#endif
		this.contentY = y;
		this.contentWidth = width;
		this.contentHeight = height;
		//#debug
		//# System.out.println("calculateContentArea: x=" + this.contentX + ", y=" + this.contentY + ", width=" + this.contentWidth + ", height=" + this.contentHeight);
		if (this.container != null) {
			this.container.setScrollHeight( height );
		}
	}
	
	

	/**
	 * Initialises this screen and informs all items about being painted soon.
	 */
	public void showNotify() {
		//#debug
		//# System.out.println("showNotify " + this + " isInitialised=" + this.isInitialized);
		try {
			//#ifdef polish.Screen.showNotifyCode:defined
				//#include ${polish.Screen.showNotifyCode}
			//#endif
			//#if tmp.fullScreen && polish.midp2 && polish.Bugs.fullScreenInShowNotify
				//# super.setFullScreenMode( true );
				//#ifdef polish.FullCanvasHeight:defined
					//#= this.fullScreenHeight = ${polish.FullCanvasHeight};
				//#else
					//# this.fullScreenHeight = getHeight();
				//#endif
				//# this.screenHeight = this.fullScreenHeight - this.menuBarHeight;
				//# this.originalScreenHeight = this.screenHeight;
				//# this.scrollIndicatorY = this.screenHeight + 1; //- this.scrollIndicatorWidth - 1;
			//#endif
			if (!this.isInitialized) {
				init();
			}
			//#if polish.css.repaint-previous-screen
				if (this.repaintPreviousScreen) {
					Displayable currentDisplayable = StyleSheet.display.getCurrent();
					//#if polish.Screen.dontBufferPreviousScreen
						//# if ( currentDisplayable != this && currentDisplayable instanceof AccessibleCanvas) {
							//# this.previousScreen = (AccessibleCanvas) currentDisplayable;							
							//#if !polish.Bugs.noTranslucencyWithDrawRgb
								//#if polish.color.overlay:defined
									//#= this.previousScreenOverlayBackground = new TranslucentSimpleBackground( ${polish.color.overlay} );
								//#else
									//# this.previousScreenOverlayBackground = new TranslucentSimpleBackground( 0xAA000000 );
								//#endif
							//#endif
						//# }
					//#else
						if ( currentDisplayable != this && currentDisplayable instanceof AccessibleCanvas ) {
							if (this.previousScreenImage == null) {
								//#if tmp.menuFullScreen
									//# this.previousScreenImage = Image.createImage( this.screenWidth, this.fullScreenHeight);
								//#else
									this.previousScreenImage = Image.createImage( this.screenWidth, this.screenHeight);
								//#endif
							}
							//#debug
							//# System.out.println("storing previous screen " + currentDisplayable + " to image buffer...");
							Graphics g = this.previousScreenImage.getGraphics();
							((AccessibleCanvas)currentDisplayable).paint(g);
							//#if !polish.Bugs.noTranslucencyWithDrawRgb
								//#if polish.color.overlay:defined
									//#= this.previousScreenOverlayBackground = new TranslucentSimpleBackground( ${polish.color.overlay} );
								//#else
									this.previousScreenOverlayBackground = new TranslucentSimpleBackground( 0xAA000000 );
								//#endif
							//#endif
						}
					//#endif
				}
			//#endif
			
			// inform all root items that they belong to this screen
			// and that they will be shown soon:
			Item[] items = getRootItems();
			for (int i = 0; i < items.length; i++) {
				Item item = items[i];
				item.screen = this;
				item.showNotify();
			}
			if (this.container != null) {
				this.container.showNotify();
			}
			//#if tmp.ignoreMotorolaTitleCall
				//# this.ignoreMotorolaTitleCall = true;
			//#endif
			//#if tmp.fullScreen && polish.midp2 && !polish.blackberry
				//# // this is needed on Sony Ericsson for example,
				//# // since the fullscreen mode is not resumed automatically
				//# // when the previous screen was in the "normal" mode:
				//#if ! tmp.fullScreenInPaint
					//# super.setFullScreenMode( true );
				//#endif
			//#endif
		
			// init components:
			int	width = this.screenWidth - (this.marginLeft + this.marginRight);
			//#ifdef tmp.menuFullScreen
				//#ifdef tmp.useExternalMenuBar
					//# if (!this.menuBar.isInitialized) {
						//# this.menuBar.init( width, width );
					//# }
				//#else
					//# if (this.menuOpened) {
						//# if (!this.menuContainer.isInitialized) {
							//# this.menuContainer.init( width, width );
						//# }
					//# } else
				//#endif
			//#endif
			if (this.container != null && !this.container.isInitialized) {
				this.container.init( width, width );
			}
			//#ifdef tmp.usingTitle
				//# if (this.title != null && !this.title.isInitialized) {
					//# this.title.init( width, width );
				//# }
			//#endif
			//#ifndef polish.skipTicker
				//# if (this.ticker != null && !this.ticker.isInitialized) {
					//# this.ticker.init( width, width );					
				//# }
			//#endif
			calculateContentArea(0, 0, this.screenWidth, this.screenHeight);

		} catch (Exception e) {
			//#debug error
			//# System.out.println("error while calling showNotify" + e );
		}

		// register this screen:
		StyleSheet.currentScreen = this;

		//#ifdef polish.Vendor.Siemens
			//# this.showNotifyTime = System.currentTimeMillis();
		//#endif
		//#if polish.ScreenInfo.enable
			ScreenInfo.setScreen( this );
		//#endif
	}
	
	/**
	 * Unregisters this screen and notifies all items that they will not be shown anymore.
	 */
	public void hideNotify() {
		//#if polish.css.repaint-previous-screen
			//#if polish.Screen.dontBufferPreviousScreen
				//# this.previousScreen = null;
			//#else
				this.previousScreenImage = null;
			//#endif
			//#if !polish.Bugs.noTranslucencyWithDrawRgb
				this.previousScreenOverlayBackground = null;
			//#endif
		//#endif
		//#ifdef polish.Vendor.Siemens
			//# // Siemens sometimes calls hideNotify directly
			//# // after showNotify for some reason.
			//# // So hideNotify checks how long the screen
			//# // has been shown - if not long enough,
			//# // the call will be ignored:
			//# if (System.currentTimeMillis() - this.showNotifyTime < 500) {
				//#debug
				//# System.out.println("Ignoring hideNotify on Siemens");
				//# return;
			//# }
		//#endif
		//#debug
		//# System.out.println("hideNotify " + this);
		//#if !polish.css.repaint-previous-screen
			//# // un-register this screen:
			//# if (StyleSheet.currentScreen == this) {
				//# StyleSheet.currentScreen = null;
			//# }
		//#endif
		Item[] items = getRootItems();
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			item.hideNotify();
		}
		if (this.container != null) {
			this.container.hideNotify();
		}
		//#ifdef tmp.ignoreMotorolaTitleCall
			//# this.ignoreMotorolaTitleCall = true;
		//#endif
		//#if polish.ScreenInfo.enable
			// de-register screen from ScreenInfo element:
			if (ScreenInfo.item.screen == this ) {
				ScreenInfo.setScreen( null );
			}
		//#endif
	}
	
	/**
	 * Sets the style of this screen.
	 * 
	 * @param style the style
	 */
	public void setStyle(Style style) {
		if (style != this.style && this.style != null ) {
			this.style.releaseResources();
		}
	 	//#debug
		//# System.out.println("Setting screen-style for " + getClass().getName() );
		this.style = style;
		this.background = style.background;
		this.border = style.border;
		this.marginTop = style.marginTop;
		this.marginBottom = style.marginBottom;
		this.marginLeft = style.marginRight;
		this.marginRight = style.marginRight;
		if (this.container != null) {
			// use the same style for the container - but ignore the background and border settings:
			this.container.setStyle(style, true);
		}
		this.isLayoutVCenter = (( style.layout & Item.LAYOUT_VCENTER ) == Item.LAYOUT_VCENTER);
		this.isLayoutBottom = !this.isLayoutVCenter 
							&& (( style.layout & Item.LAYOUT_BOTTOM ) == Item.LAYOUT_BOTTOM);
		this.isLayoutCenter = (( style.layout & Item.LAYOUT_CENTER ) == Item.LAYOUT_CENTER);
		this.isLayoutRight = !this.isLayoutCenter
							&& (( style.layout & Item.LAYOUT_RIGHT ) == Item.LAYOUT_RIGHT);
		this.isLayoutHorizontalShrink = (style.layout & Item.LAYOUT_SHRINK) == Item.LAYOUT_SHRINK; 
		this.isLayoutVerticalShrink = (style.layout & Item.LAYOUT_VSHRINK) == Item.LAYOUT_VSHRINK; 
		//#if polish.css.scrollindicator-up-image && tmp.useScrollIndicator
			//# String scrollUpUrl = style.getProperty(102);
			//# if (scrollUpUrl != null) {
				//# try {
					//# this.scrollIndicatorUpImage = StyleSheet.getImage(scrollUpUrl, null, true);
				//# } catch (IOException e) {
					//#debug error
					//# System.out.println("Unable to load scroll up image" + e );
				//# }
			//# } else {
				//# this.scrollIndicatorUpImage = null;
			//# }
		//#endif		
		//#if polish.css.scrollindicator-down-image && tmp.useScrollIndicator
			//# String scrollDownUrl = style.getProperty(103);
			//# if (scrollDownUrl != null) {
				//# try {
					//# this.scrollIndicatorDownImage = StyleSheet.getImage(scrollDownUrl, null, true);
				//# } catch (IOException e) {
					//#debug error
					//# System.out.println("Unable to load scroll down image" + e );
				//# }
			//# } else {
				//# this.scrollIndicatorDownImage = null;
			//# }
		//#endif		
		//#if polish.css.scrollindicator-up-image && polish.css.scrollindicator-down-image && tmp.useScrollIndicator
			//# if (this.scrollIndicatorUpImage != null && this.scrollIndicatorDownImage != null) {
				//# int height = this.scrollIndicatorUpImage.getHeight() + this.scrollIndicatorDownImage.getHeight();
				//# int width = Math.max( this.scrollIndicatorUpImage.getWidth(), this.scrollIndicatorDownImage.getWidth() );
				//# this.scrollIndicatorWidth = width;
				//# this.scrollIndicatorX = this.screenWidth / 2 - width / 2;
				//#ifdef tmp.menuFullScreen
					//#ifdef tmp.useExternalMenuBar
						//# this.scrollIndicatorY = this.fullScreenHeight - (this.menuBar.marginBottom + 1 + height);
					//#else
						//# this.scrollIndicatorY = this.fullScreenHeight - (height + 1);
					//#endif					
				//#elif polish.vendor.Siemens
					//# // set the position of scroll indicator for Siemens devices 
					//# // on the left side, so that the menu-indicator is visible:
					//# this.scrollIndicatorWidth = width;
					//# this.scrollIndicatorX = 0;
					//# this.scrollIndicatorY = this.screenHeight - height - 1;
				//#else
					//# // set position of scroll indicator:
					//# this.scrollIndicatorWidth = width;
					//# this.scrollIndicatorX = this.screenWidth - width - 1;
					//# this.scrollIndicatorY = this.screenHeight - height - 1;
				//#endif					
			//# }
		//#endif

		//#if polish.css.scrollindicator-color && tmp.useScrollIndicator
			//# Integer scrollIndicatorColorInt = style.getIntProperty( 36 );
			//# if (scrollIndicatorColorInt != null) {
				//# this.scrollIndicatorColor = scrollIndicatorColorInt.intValue();
			//# }
		//#endif
		//#if tmp.usingTitle && polish.css.title-style
			//# this.titleStyle = (Style) style.getObjectProperty(2);
			//# if (this.titleStyle != null && this.title != null) {
				//# this.title.setStyle(this.titleStyle);
				//# int width = this.screenWidth - (this.marginLeft + this.marginRight);
				//# this.titleHeight = this.title.getItemHeight( width, width );
			//# } else {
		//#endif
		 //#if tmp.usingTitle
		 	//# if (this.title != null && this.title.isInitialized) {
		 		//# this.title.isInitialized = false;
				//# int width = this.screenWidth - (this.marginLeft + this.marginRight);
				//# this.titleHeight = this.title.getItemHeight( width, width );
		 	//# }
		 //#endif
		//#if tmp.usingTitle && polish.css.title-style				
			//# }
		//#endif
		//#if tmp.usingTitle && polish.css.title-position
			//# Integer titlePositionInt = style.getIntProperty(115);
			//# if (titlePositionInt == null && this.title != null && this.title.style != null) {
				//# titlePositionInt = this.title.style.getIntProperty(115);
			//# }
			//# if (titlePositionInt != null) {
				//# this.paintTitleAtTop = (titlePositionInt.intValue() == POSITION_TOP);
			//# }
//# 			
		//#endif
		//#if tmp.useScrollBar && polish.css.scrollbar-style
			//# Style scrollbarStyle = (Style) style.getObjectProperty(126);
			//# if (scrollbarStyle != null) {
				//# this.scrollBar.setStyle( scrollbarStyle );
			//# }
		//#endif
		//#if tmp.useScrollBar && polish.css.scrollbar-position
			//# Integer scrollBarPositionInt = style.getIntProperty( 118 );
			//# if (scrollBarPositionInt == null && this.scrollBar.style != null) {
				//# scrollBarPositionInt = this.scrollBar.style.getIntProperty( 118 );
			//# }
			//# if (scrollBarPositionInt != null) {
				//# this.paintScrollBarOnRightSide = (scrollBarPositionInt.intValue() != POSITION_LEFT);
			//# }
		//#endif
			

		//#ifdef polish.css.foreground-image
			//# String foregroundImageUrl = this.style.getProperty(52);
			//# if (foregroundImageUrl == null) {
				//# this.foregroundImage = null;
			//# } else {
				//# try {
					//# this.foregroundImage = StyleSheet.getImage(foregroundImageUrl, null,true);
					//#ifdef polish.css.foreground-x
						//# Integer xInteger = this.style.getIntProperty(53);
						//# if (xInteger != null) {
							//# this.foregroundX = xInteger.intValue();
						//# }
					//#endif
					//#ifdef polish.css.foreground-y
						//# Integer yInteger = this.style.getIntProperty(54);
						//# if (yInteger != null) {
							//# this.foregroundY = yInteger.intValue();
						//# }
					//#endif
				//# } catch (IOException e) {
					//#debug error
					//# System.out.println("Unable to load foreground-image [" + foregroundImageUrl + "]: " + e);
				//# }
			//# }
		//#endif
		//#if polish.css.clip-screen-info
			//# Boolean clipScreenInfoBool = style.getBooleanProperty( 87 );
			//# if (clipScreenInfoBool != null) {
				//# this.clipScreenInfo = clipScreenInfoBool.booleanValue();
			//# }
		//#endif	
		//#if polish.css.menubar-style  && tmp.useExternalMenuBar
			//# Style menuBarStyle = (Style) style.getObjectProperty(117);
			//# if (menuBarStyle != null) {
				//# this.menuBar.setStyle( menuBarStyle );
			//# }
		//#endif

		//#ifndef polish.skipTicker
			//#if polish.css.ticker-position
				//# Integer tickerPositionInt = style.getIntProperty(114);
				//# if (tickerPositionInt == null && this.ticker != null && this.ticker.style != null) {
					//# tickerPositionInt = this.ticker.style.getIntProperty(114);
				//# }
				//# if (tickerPositionInt != null) {
					//# this.paintTickerAtTop = (tickerPositionInt.intValue() == POSITION_TOP);
				//# }
			//#endif
		//#endif
		//#if polish.css.repaint-previous-screen
			Boolean repaintPreviousScreenBool = style.getBooleanProperty(116);
			if (repaintPreviousScreenBool != null) {
				this.repaintPreviousScreen = repaintPreviousScreenBool.booleanValue();
			}
		//#endif
		//#if polish.css.separate-menubar
			//# Boolean separateMenubarBool = style.getBooleanProperty(-1);
			//# if (separateMenubarBool != null) {
				//# this.separateMenubar = separateMenubarBool.booleanValue();
			//# }
		//#endif
		//#if tmp.menuFullScreen
			//#if polish.css.background-bottom
				//# Integer backgroundBottomInt = style.getIntProperty(153);
				//# if (backgroundBottomInt != null) {
					//# this.excludeMenuBarForBackground = backgroundBottomInt.intValue() == 1; 
				//# }
			//#endif
		//#endif
		//#ifdef tmp.usingTitle
			//#if polish.css.background-top	
				//# Integer backgroundTopInt = style.getIntProperty(154);
				//# if (backgroundTopInt != null) {
					//# this.excludeTitleForBackground = backgroundTopInt.intValue() == 1; 
				//# }
			//#endif
		//#endif
	}
	
	/**
	 * Animates this Screen.
	 * All embedded items are also animated.
	 * 
	 * @return true when at least one animated item needs a redraw/repaint.
	 */
	public boolean animate() {
		if (!this.isInitialized) {
			return false;
		}
			synchronized (this.paintLock) {
			try {
				boolean animated = false;
				if (this.background != null) {
					animated = this.background.animate();
				}
				//#ifdef tmp.menuFullScreen
					//#ifdef tmp.useExternalMenuBar
						//# animated = animated | this.menuBar.animate();
					//#else
						//# if (this.menuOpened) {
							//# animated = animated | this.menuContainer.animate();
						//# } else
					//#endif
				//#endif
				if (this.container != null) {
					animated = animated | this.container.animate();
				}
				//#ifdef tmp.usingTitle
					//# if (this.title != null) {
						//# animated = animated | this.title.animate();
					//# }
				//#endif
				//#ifndef polish.skipTicker
					//# if (this.ticker != null) {
						//# animated = animated | this.ticker.animate();
					//# }
				//#endif
				//#if polish.ScreenInfo.enable
					if (ScreenInfo.item != null && ScreenInfo.isVisible()) {
						animated = animated | ScreenInfo.item.animate();
					}
				//#endif
				return animated;
			} catch (Exception e) {
				//#debug error
				//# System.out.println("animate() threw an exception" + e );
				return false;
			}
		}
	}
	
	/**
	 * Paints the screen.
	 * When you subclass Screen you should override paintScreen(Graphics g) instead, if possible.
	 * 
	 * @param g the graphics context.
	 * @see #paintScreen(Graphics)
	 */
	public void paint(Graphics g) {
		//System.out.println("Painting screen "+ this + ", background == null: " + (this.background == null));
		synchronized (this.paintLock ) {
			//#if polish.Bugs.losesFullScreen
				//# super.setFullScreenMode( true );
			//#endif
			//#if tmp.fullScreenInPaint
				//# if (!this.isInFullScreenMode) {
					//# super.setFullScreenMode( true );
					//# this.isInFullScreenMode = true;
					//#if tmp.menuFullScreen
						//#ifdef polish.FullCanvasHeight:defined
							//#= this.fullScreenHeight = ${polish.FullCanvasHeight};
						//#else
							//# this.fullScreenHeight = getHeight();
						//#endif
						//# this.screenHeight = this.fullScreenHeight - this.menuBarHeight;
						//# this.originalScreenHeight = this.screenHeight;
						//#if tmp.useScrollIndicator
							//# this.scrollIndicatorY = this.screenHeight + 1; //- this.scrollIndicatorWidth - 1 - this.menuBarHeight;
						//#endif
					//#endif
				//# }
			//#endif
			//#if !tmp.menuFullScreen
				int translateY = g.getTranslateY();
				if (translateY != 0 && this.screenHeight == this.originalScreenHeight) {
					this.screenHeight -= translateY;
					//#if tmp.useScrollIndicator 
						//# this.scrollIndicatorY -= translateY;
					//#endif
					//#debug
					//# System.out.println("Adjusting screenheight from " + this.originalScreenHeight + " to " + this.screenHeight );
					if (this.container != null) {
						int y = translateY;
						calculateContentArea( 0, y, this.screenWidth, this.screenHeight - y );
					}
				}
			//#endif
			//#if tmp.fullScreen && polish.FullCanvasSize:defined && polish.Bugs.setClipForFullScreenNeeded
				//# g.translate( -g.getTranslateX(), -g.getTranslateY() );
				//#= g.setClip( 0, 0, ${polish.FullCanvasWidth}, ${polish.FullCanvasHeight} );
			//#endif
			//#if polish.debug.error
			//# try {
			//#endif
				//#if polish.css.repaint-previous-screen
					//#if polish.Screen.dontBufferPreviousScreen
						//# if (this.repaintPreviousScreen && this.previousScreen != null) {
							//# this.previousScreen.paint(g);
							//#if !polish.Bugs.noTranslucencyWithDrawRgb
								//# this.previousScreenOverlayBackground.paint(0, 0, this.screenWidth, this.screenHeight, g);
							//#endif
						//# }
					//#else
						if (this.repaintPreviousScreen && this.previousScreenImage != null) {
							g.drawImage(this.previousScreenImage, 0, 0, Graphics.TOP | Graphics.LEFT );
							//#if !polish.Bugs.noTranslucencyWithDrawRgb
								this.previousScreenOverlayBackground.paint(0, 0, this.screenWidth, this.screenHeight, g);
							//#endif
						}
					//#endif
				//#endif
				int sWidth = this.screenWidth - this.marginLeft - this.marginRight;
				int leftBorder = this.marginLeft;
				int rightBorder = leftBorder + sWidth;
				if (this.isLayoutHorizontalShrink) {
					int contWidth = this.contentWidth;
					if (this.container != null) {
						contWidth = this.container.getItemWidth(sWidth, sWidth); 
						//#ifdef tmp.usingTitle
							//# if (this.title != null && this.title.itemWidth > contWidth ) {
								//# this.titleHeight = this.title.getItemHeight( contWidth, contWidth );
							//# }
						//#endif
					}
					sWidth = contWidth;
					//System.out.println("is horizontal shrink - from sWidth=" + (this.screenWidth - this.marginLeft - this.marginRight) + ", to=" + sWidth );					
					if (this.isLayoutRight) {
						leftBorder = rightBorder - sWidth;
					} else if (this.isLayoutCenter) {
						leftBorder = (this.screenWidth - sWidth) / 2;
						rightBorder = this.screenWidth - leftBorder;
					} else {
						rightBorder = this.screenWidth - sWidth;
					}
					//System.out.println("leftBorder=" + leftBorder + ", rightBorder=" + rightBorder );
				}
				
				//#ifdef tmp.menuFullScreen
					//# int sHeight = this.fullScreenHeight - this.marginTop - this.marginBottom;
				//#else
					int sHeight = this.screenHeight - this.marginTop - this.marginBottom;
				//#endif
				int topBorder = this.marginTop;
				if (this.isLayoutVerticalShrink) {
					int contHeight = this.contentHeight;
					if (this.container != null) {
						contHeight = this.container.getItemHeight(sWidth, sWidth); 
					}
//					//#if tmp.menuFullScreen
//						sHeight = contHeight + this.titleHeight + this.menuBarHeight;
//					//#else
						sHeight = contHeight + this.titleHeight;
//					//#endif
					//System.out.println("isLayoutVerticalShrink - sHeight: from=" + (this.fullScreenHeight - this.marginTop - this.marginBottom) + ", to=" + sHeight + ", contentHeight=" + this.contentHeight + ", topBorder=" + topBorder );
					if (this.isLayoutBottom) {
//						//#ifdef tmp.menuFullScreen
//							topBorder = this.fullScreenHeight - (this.marginBottom + sHeight + 1);
//						//#else
							topBorder = this.screenHeight - (this.marginBottom + sHeight + 1);
//						//#endif
						//System.out.println("bottom -> topBorder=" + topBorder + ", contY=>" + (topBorder + this.titleHeight) );
					} else if (this.isLayoutVCenter) {
//						//#ifdef tmp.menuFullScreen
//							topBorder = (this.fullScreenHeight - (this.marginBottom + this.marginBottom))/2 - sHeight/2;
//						//#else
							topBorder = (this.screenHeight - (this.marginBottom + this.marginTop + sHeight))/2;
//						//#endif						 
						//System.out.println("vcenter -> topBorder=" + topBorder + ", contY=>" + (topBorder + this.titleHeight) );
					}
				}

				// paint background:
				if (this.background != null) {
					int backgroundHeight = sHeight;
					int backgroundY = topBorder;
					//#ifdef tmp.menuFullScreen
						//# if (this.excludeMenuBarForBackground) {
							//# backgroundHeight = this.screenHeight - this.marginTop - this.marginBottom + 1;
						//# }
					//#endif
					//#ifdef tmp.usingTitle
						//# if (this.excludeTitleForBackground) {
							//# backgroundHeight -= this.titleHeight;
							//# backgroundY += this.titleHeight;
						//# }
					//#endif
					// System.out.println("Screen (" + this + ": painting background at leftBorder=" + leftBorder + ", backgroundY=" + backgroundY);
					this.background.paint(leftBorder, backgroundY, sWidth, backgroundHeight, g);
				} else {
					//System.out.println("Screen (" + this + ": clearing area...");
					int backgroundHeight = sHeight;
					int backgroundY = topBorder;
					//#ifdef tmp.menuFullScreen
						//# if (this.excludeMenuBarForBackground) {
							//# backgroundHeight = this.screenHeight - this.marginTop - this.marginBottom + 1;
						//# }
					//#endif
					//#ifdef tmp.usingTitle
						//# if (this.excludeTitleForBackground) {
							//# backgroundHeight -= this.titleHeight;
							//# backgroundY += this.titleHeight;
						//# }
					//#endif
					g.setColor( 0xFFFFFF );
					g.fillRect( leftBorder, backgroundY, sWidth, backgroundHeight );
				}
				
				int topHeight = topBorder;
				//#ifdef tmp.usingTitle
					//#if polish.css.title-position
						//# if (this.paintTitleAtTop) {
					//#endif
							//# // paint title:
							//# if (this.title != null && this.showTitleOrMenu) {
								//# this.title.paint( leftBorder, topBorder, leftBorder, rightBorder, g);
								//# topHeight += this.titleHeight;
							//# }
					//#if polish.css.title-position
						//# }
					//#endif
				//#endif
				if (this.subTitle != null) {
					this.subTitle.paint( leftBorder, topHeight, leftBorder, rightBorder, g );
					this.subTitle.relativeY = topHeight;
					topHeight += this.subTitleHeight;
				}
				//#ifndef polish.skipTicker			
					//#if tmp.paintTickerAtTop
						//# if (this.ticker != null) {
							//# this.ticker.paint( leftBorder, topHeight, leftBorder, rightBorder, g);
							//# topHeight += this.ticker.itemHeight;
						//# }
					//#elif polish.css.ticker-position && !polish.TickerPosition:defined
						//# if (this.paintTickerAtTop && this.ticker != null) {
							//# this.ticker.paint( leftBorder, topHeight, leftBorder, rightBorder, g);
							//# topHeight += this.ticker.itemHeight;
						//# }
					//#endif
				//#endif
	
				int infoItemY = topHeight;
				//#if polish.clip-screen-info
					//# if (this.showInfoItem && this.clipScreenInfo) {			
						//# topHeight += this.infoHeight;
					//# }
				//#endif
				//System.out.println("topHeight=" + topHeight + ", contentY=" + contentY);
				
				// protect the title, ticker and the full-screen-menu area:
	//			int clipHeight = this.screenHeight - topHeight;
	//			//#if tmp.menuFullScreen
	//				int cHeight = this.fullScreenHeight - topHeight - this.marginBottom;
	//				if (cHeight < clipHeight) {
	//					clipHeight = cHeight;
	//				}
	//			//#endif
//				System.out.println("paintScreen with clipping " + g.getClipX() + ", " + g.getClipY() + ", " + g.getClipWidth() + ", " + g.getClipHeight() + " before clipRect");
				int clipX = g.getClipX();
				int clipY = g.getClipY();
				int clipWidth = g.getClipWidth();
				int clipHeight = g.getClipHeight();
				g.setClip(leftBorder, topHeight, sWidth, this.screenHeight - topHeight  );
				//g.clipRect(leftBorder, topHeight, sWidth, this.screenHeight - topHeight  );
	
				// paint content:
				//System.out.println("starting to paint content of screen");
				paintScreen( g );
				//System.out.println("done painting content of screen");
//				System.out.println("paintScreen with clipping " + g.getClipX() + ", " + g.getClipY() + ", " + g.getClipWidth() + ", " + g.getClipHeight() );
//				g.setColor( 0xff0000 );
//				g.drawRect(g.getClipX() + 1 , g.getClipY() + 1 , g.getClipWidth() - 2 ,  g.getClipHeight() - 2);
				
				//#if tmp.useScrollBar
					if (this.container != null && this.container.itemHeight > this.contentHeight) {
						// paint scroll bar: - this.container.yOffset
						//#debug
						//# System.out.println("Screen/ScrollBar: container.contentY=" + this.container.contentY + ", container.internalY=" +  this.container.internalY + ", container.yOffset=" + this.container.yOffset + ", container.height=" + this.container.availableHeight + ", container.relativeY=" + this.container.relativeY);
						
						int scrollX = sWidth + this.marginLeft 
									- this.scrollBar.initScrollBar(sWidth, this.contentHeight, this.container.itemHeight, this.container.yOffset, this.container.internalY, this.container.internalHeight, this.container.focusedIndex, this.container.size() );
						//TODO allow scroll bar on the left side
						this.scrollBar.relativeX = scrollX;
						this.scrollBar.relativeY = this.contentY;
						this.scrollBar.paint( scrollX, this.contentY, scrollX, rightBorder, g);
					}
				//#endif
				
				// allow painting outside of the screen again:
				//g.setClip( clipX, clipY, clipWidth, clipHeight );
				//#ifdef tmp.menuFullScreen
				 	//# g.setClip(0, 0, this.screenWidth, this.fullScreenHeight );
				//#else
				 	g.setClip(0, 0, this.screenWidth, this.originalScreenHeight );
				//#endif
				 
				// paint info element:
				if (this.showInfoItem) {			
					this.infoItem.paint( this.marginLeft, infoItemY, this.marginLeft, rightBorder, g );
				}
	 	
				int bottomY = this.contentY + this.contentHeight;
				//#ifndef polish.skipTicker			
					//#if tmp.paintTickerAtBottom
						//# if (this.ticker != null) {
							//# this.ticker.paint( this.marginLeft, bottomY, this.marginLeft, rightBorder, g);
							//# bottomY += this.ticker.itemHeight;
						//# }
					//#elif polish.css.ticker-position && !polish.TickerPosition:defined
						//# if (!this.paintTickerAtTop && this.ticker != null) {
							//# this.ticker.paint( this.marginLeft, bottomY, this.marginLeft, rightBorder, g);
							//# bottomY += this.ticker.itemHeight;
						//# }
					//#endif
				//#endif
				//#if tmp.usingTitle && polish.css.title-position
						//# if (!this.paintTitleAtTop) {
							//# // paint title:
							//# if (this.title != null && this.showTitleOrMenu) {
								//# this.title.paint( leftBorder, bottomY, leftBorder, rightBorder, g);
								//# //bottomY += this.titleHeight;
							//# }
						//# }
				//#endif
				
				// paint border:
				if (this.border != null) {
	//				//#ifdef tmp.menuFullScreen
	//					this.border.paint(this.marginLeft, this.marginTop, sWidth, this.screenHeight - this.marginBottom - this.marginTop, g);
	//				//#else
						this.border.paint(leftBorder, this.marginTop, sWidth, sHeight, g);
	//				//#endif
				}
				
				//#if polish.ScreenInfo.enable
					ScreenInfo.paint( g, topHeight, this.screenWidth );
				//#endif
					
				// paint menu in full-screen mode:
				int menuLeftX = 0;
				int menuRightX = this.screenWidth;
				int menuY = this.screenHeight; // + this.marginBottom;
				//#if polish.css.separate-menubar
					//# if (!this.separateMenubar) {
						//# menuLeftX = leftBorder;
						//# menuRightX = rightBorder;
						//# menuY = this.screenHeight - this.marginBottom;
					//# }
				//#endif
				//#ifdef tmp.menuFullScreen
					//#ifdef tmp.useExternalMenuBar
						//# this.menuBar.paint(menuLeftX, menuY, menuLeftX, menuRightX, g);
						//#if tmp.useScrollIndicator
							//# if (this.menuBar.isOpened) {
								//# this.paintScrollIndicator = this.menuBar.paintScrollIndicator;
								//# this.paintScrollIndicatorUp = this.menuBar.canScrollUpwards;
								//# this.paintScrollIndicatorDown = this.menuBar.canScrollDownwards;
							//# }
						//#endif
					//#else
						//# if (this.menuOpened) {
							//# topHeight -= this.infoHeight;
							//# int menuHeight = this.menuContainer.getItemHeight(this.screenWidth, this.screenWidth);
							//# int y = this.originalScreenHeight - (menuHeight + 1);
							//# if (y < topHeight) {
								//#if tmp.useScrollIndicator
								//# this.paintScrollIndicator = true;
								//# this.paintScrollIndicatorUp = (this.menuContainer.yOffset != 0);
								//# this.paintScrollIndicatorDown = ( (this.menuContainer.focusedIndex != this.menuContainer.size() - 1)
										//# && (this.menuContainer.yOffset + menuHeight > this.originalScreenHeight - topHeight)) ;
								//#endif
								//# y = topHeight; 
							//#if tmp.useScrollIndicator
							//# } else {
								//# this.paintScrollIndicator = false;
							//#endif
							//# }
							//# this.menuContainer.setScrollHeight(this.originalScreenHeight-topHeight);
							//# g.setClip(0, topHeight, this.screenWidth, this.originalScreenHeight - topHeight );
							//# this.menuContainer.paint(menuLeftX, y, menuLeftX, menuLeftX + this.screenWidth, g);
						 	//# g.setClip(0, 0, this.screenWidth, this.fullScreenHeight );
						//# } 
						//#if !polish.doja
							//# if (this.showTitleOrMenu || this.menuOpened) {
								//# // clear menu-bar:
								//# if (this.menuBarColor != Item.TRANSPARENT) {
									//# g.setColor( this.menuBarColor );
									//# //TODO check use menuY instead of this.originalScreenHeight?
									//# g.fillRect(menuLeftX, this.originalScreenHeight, menuRightX,  this.menuBarHeight );
								//# }
								//# g.setColor( this.menuFontColor );
								//# g.setFont( this.menuFont );
								//# String menuText = this.menuLeftString;
								//# if (menuText != null) {
									//#ifdef polish.MenuBar.MarginLeft:defined
										//#= menuLeftX += ${polish.MenuBar.MarginLeft};
									//#elifdef polish.MenuBar.PaddingLeft:defined
										//#= menuLeftX += ${polish.MenuBar.PaddingLeft};
									//#else
										//# menuLeftX += 2;
									//#endif
									//#ifdef polish.MenuBar.MarginTop:defined
										//#= g.drawString(menuText, menuX, this.originalScreenHeight + ${polish.MenuBar.MarginTop}, Graphics.TOP | Graphics.LEFT );
									//#else
										//# g.drawString(menuText, menuLeftX, this.originalScreenHeight + 2, Graphics.TOP | Graphics.LEFT );
									//#endif
//# 									
								//# }
								//# menuText = this.menuRightString;
								//# if (menuText != null) {
									//#ifdef polish.MenuBar.MarginRight:defined
										//#= menuRightX -= ${polish.MenuBar.MarginRight};
									//#elifdef polish.MenuBar.PaddingRight:defined
										//#= menuRightX -= ${polish.MenuBar.PaddingRight};
									//#elifdef polish.MenuBar.MarginLeft:defined
										//# menuRightX -= 2;
									//#endif
//# 
									//#ifdef polish.MenuBar.MarginTop:defined
										//#= g.drawString(menuText, menuRightX, this.originalScreenHeight + ${polish.MenuBar.MarginTop}, Graphics.TOP | Graphics.RIGHT );
									//#else
										//# g.drawString(menuText, menuRightX, this.originalScreenHeight + 2, Graphics.TOP | Graphics.RIGHT );
									//#endif
									//#ifdef polish.hasPointerEvents
										//# this.menuRightCommandX = menuRightX - this.menuFont.stringWidth( menuText );
									//#endif
								//# }
							//# } // if this.showTitleOrMenu || this.menuOpened
						//#endif
					//#endif
				//#endif
						
				//#if tmp.useScrollIndicator
					//# // paint scroll-indicator in the middle of the menu:					
					//# if (this.paintScrollIndicator) {
						//# g.setColor( this.scrollIndicatorColor );
						//# int x = this.scrollIndicatorX;
						//# int y = this.scrollIndicatorY;
						//# //System.out.println("paint: this.scrollIndicatorY=" + this.scrollIndicatorY);
						//# int width = this.scrollIndicatorWidth;
						//# int halfWidth = width / 2;
						//# if (this.paintScrollIndicatorUp) {
							//#if polish.css.scrollindicator-up-image
								//# if (this.scrollIndicatorUpImage != null) {
									//# g.drawImage(this.scrollIndicatorUpImage, x, y, Graphics.LEFT | Graphics.TOP );
								//# } else {
							//#endif						
								//#ifdef polish.midp2
									//# g.fillTriangle(x, y + halfWidth-1, x + width, y + halfWidth-1, x + halfWidth, y );
								//#else
									//# g.drawLine( x, y + halfWidth-1, x + width, y + halfWidth-1 );
									//# g.drawLine( x, y + halfWidth-1, x + halfWidth, y );
									//# g.drawLine( x + width, y + halfWidth-1, x + halfWidth, y );
								//#endif
							//#if polish.css.scrollindicator-up-image
								//# }
							//#endif
						//# }
						//# if (this.paintScrollIndicatorDown) {
							//#if polish.css.scrollindicator-down-image
								//# if (this.scrollIndicatorDownImage != null) {
									//#if polish.css.scrollindicator-down-image
										//# if (this.scrollIndicatorUpImage != null) {
											//# y += this.scrollIndicatorUpImage.getHeight() + 1;
										//# } else {
											//# y += halfWidth;
										//# }
									//#else
										//# y += halfWidth;
									//#endif
									//# g.drawImage(this.scrollIndicatorDownImage, x, y, Graphics.LEFT | Graphics.TOP );
								//# } else {
							//#endif						
								//#ifdef polish.midp2
									//# g.fillTriangle(x, y + halfWidth+1, x + width, y + halfWidth+1, x + halfWidth, y + width );
								//#else
									//# g.drawLine( x, y + halfWidth+1, x + width, y + halfWidth+1 );
									//# g.drawLine( x, y + halfWidth+1, x + halfWidth, y + width );
									//# g.drawLine(x + width, y + halfWidth+1, x + halfWidth, y + width );
								//#endif
							//#if polish.css.scrollindicator-down-image
								//# }
							//#endif
						//# }
					//# }
				//#endif
				//#ifdef polish.css.foreground-image
					//# if (this.foregroundImage != null) {
						//# g.drawImage( this.foregroundImage, this.foregroundX, this.foregroundY, Graphics.TOP | Graphics.LEFT  );
					//# }
				//#endif
			
			//#if polish.debug.error
			//# } catch (RuntimeException e) {
				//#debug error
				//# System.out.println( "unable to paint screen (" + getClass().getName() + "):" + e );
			//# }
			//#endif
		}
	}
	
	/**
	 * Paints the screen.
	 * This method also needs to set the protected variables
	 * paintScrollIndicator, paintScrollIndicatorUp and paintScrollIndicatorDown.
	 * 
	 * @param g the graphics on which the screen should be painted
	 * @see #contentX
	 * @see #contentY
	 * @see #contentWidth
	 * @see #contentHeight
	 * @see #paintScrollIndicator
	 * @see #paintScrollIndicatorUp
	 * @see #paintScrollIndicatorDown
	 */
	protected void paintScreen( Graphics g ) {
		int y = this.contentY;
		int x = this.contentX;
		int height = this.contentHeight;
		int width = this.contentWidth;
		int containerHeight = this.container.getItemHeight( width, width);
		//#if tmp.useScrollIndicator
			//# this.paintScrollIndicator = false; // defaults to false
		//#endif
		if (containerHeight > this.container.availableHeight ) {
			//#if tmp.useScrollIndicator
				//# this.paintScrollIndicator = true;
				//# this.paintScrollIndicatorUp = (this.container.yOffset != 0);
					//# //&& (this.container.focusedIndex != 0);
				//# this.paintScrollIndicatorDown = (  
						//# ( (this.container.focusedIndex != this.container.size() - 1) 
								//# || (this.container.focusedItem != null && this.container.focusedItem.itemHeight > this.container.availableHeight) )
						 //# && (this.container.getScrollYOffset() + containerHeight > this.container.availableHeight) );
			//#endif
		} else if (this.isLayoutVCenter) {
			/*
			//#debug
			//# System.out.println("Screen: adjusting y from [" + y + "] to [" + ( y + (height - containerHeight) / 2) + "] - containerHeight=" + containerHeight);
			*/
			if (this.isLayoutVerticalShrink) {
				y = g.getClipY();
			} else {
				y += ((height - containerHeight) / 2);
			}
		} else if (this.isLayoutBottom) {
			if (this.isLayoutVerticalShrink) {
				y = g.getClipY();
			} else {
				y += (height - containerHeight);
			}
//			System.out.println("content: y=" + y + ", contentY=" + this.contentY + ", contentHeight="+ this.contentHeight + ", containerHeight=" + containerHeight);
		}
		int containerWidth = this.container.itemWidth;
		if (this.isLayoutCenter) {
			int diff = (width - containerWidth) / 2;
			x += diff;
			width -= (width - containerWidth);
		} else if (this.isLayoutRight) {
			int diff = width - containerWidth;
			x += diff;
			width -= diff;
		}
		this.container.relativeX = x;
		this.container.relativeY = y;
		//System.out.println("content: x=" + x + ", rightBorder=" + (x + width) );
		this.container.paint( x, y, x, x + width, g );
	}
	
	//#ifdef tmp.usingTitle
	//# /**
	 //# * Gets the title of the Screen. 
	 //# * Returns null if there is no title.
	 //# * 
	 //# * @return the title of this screen
	 //# */
	//# public String getTitle()
	//# {
		//# if (this.title == null) {
			//# return null;
		//# } else if ( !(this.title instanceof StringItem ) ) {
			//# return null;
		//# } else {
			//# return ((StringItem)this.title).getText();
		//# }
	//# }
	//#endif

	//#ifdef tmp.usingTitle
	//# /**
	 //# * Sets the title of the Screen. If null is given, removes the title.
	 //# * 
	 //# * If the Screen is physically visible, the visible effect
	 //# * should take place no later than immediately
	 //# * after the callback or
	 //# * <A HREF="../../../javax/microedition/midlet/MIDlet.html#startApp()"><CODE>startApp</CODE></A>
	 //# * returns back to the implementation.
	 //# * 
	 //# * @param s - the new title, or null for no title
	 //# */
	//# public void setTitle( String s)
	//# {
		//# setTitle( s, null );
	//# }
	//#endif
	
	//#ifdef tmp.usingTitle
	//# /**
	 //# * Sets the title of the Screen. If null is given, removes the title.
	 //# * 
	 //# * If the Screen is physically visible, the visible effect
	 //# * should take place no later than immediately
	 //# * after the callback or
	 //# * <A HREF="../../../javax/microedition/midlet/MIDlet.html#startApp()"><CODE>startApp</CODE></A>
	 //# * returns back to the implementation.
	 //# * 
	 //# * @param text the new title, or null for no title
	 //# * @param tStyle the new style for the title, is ignored when null
	 //# */
	//# public void setTitle( String text, Style tStyle)
	//# {
		//#debug
		//# System.out.println("Setting title " + text );
		//#ifdef tmp.ignoreMotorolaTitleCall
			//# if (text == null) {
				//# if (this.ignoreMotorolaTitleCall) {
					//# this.ignoreMotorolaTitleCall = false;
					//# return;
				//# }
				//# //return;
			//# }
		//#endif
		//# if (text != null) {
			//#style title, default
			//# this.title = new StringItem( null, text );
			//# this.title.screen = this;
			//# if ( tStyle != null ) {
				//# this.title.setStyle( tStyle );
			//# }
			//#ifdef polish.css.title-style
				//# else if (this.titleStyle != null) {
					//# this.title.setStyle( this.titleStyle );
				//# }
			//#endif
			//# // the Nokia 6600 has an amazing bug - when trying to refer the
			//# // field screenWidth, it returns 0 in setTitle(). Obviously this works
			//# // in other phones and in the simulator, but not on the Nokia 6600.
			//# // That's why hardcoded values are used here. 
			//# // The name of the field does not matter by the way. This is 
			//# // a very interesting behaviour and should be analysed
			//# // at some point...
			//#ifdef polish.ScreenWidth:defined
				//#= int width = ${polish.ScreenWidth}  - (this.marginLeft + this.marginRight);
			//#else
				//# int width = this.screenWidth - (this.marginLeft + this.marginRight);
			//#endif
			//# this.titleHeight = this.title.getItemHeight( width, width );			
		//# } else {
			//# this.title = null;
			//# this.titleHeight = 0;
		//# }
		//# if (this.isInitialized && super.isShown()) {
			//# calculateContentArea( 0, 0, this.screenWidth, this.screenHeight );
			//# repaint();
		//# }
	//# }
	//#endif
	
	/**
	 * Sets an Item as the title for this screen.
	 * WARNING: You must not call setTitle(String) after calling this method anymore!
	 * 
	 * @param item the title Item 
	 */
	public void setTitle(Item item) {
		//#ifdef tmp.usingTitle
		//# this.title = item;
		//# if (item != null){
			//#ifdef polish.ScreenWidth:defined
				//#= int width = ${polish.ScreenWidth}  - (this.marginLeft + this.marginRight);
			//#else
				//# int width = this.screenWidth - (this.marginLeft + this.marginRight);
			//#endif
			//# this.titleHeight = this.title.getItemHeight( width, width );
			//# item.screen = this;
		//# } else {
			//# this.titleHeight = 0;
		//# }
		//# if (this.isInitialized && super.isShown()) {
			//# calculateContentArea( 0, 0, this.screenWidth, this.screenHeight );
			//# repaint();
		//# }
		//#endif
	}

	/**
	 * Sets the information which should be shown to the user.
	 * The info is shown below the title (if any) and can be designed
	 * using the predefined style "info".
	 * At the moment this feature is only used by the TextField implementation,
	 * when the direct input mode is enabled.
	 * A repaint is not triggered automatically by calling this method.
	 * 
	 * @param infoText the text which will be shown to the user
	 */
	public void setInfo( String infoText ) {
		if (this.infoItem == null) {
			//#style info, default
			this.infoItem = new StringItem( null, infoText , StyleSheet.defaultStyle );
			this.infoItem.screen = this;
		} else {
			this.infoItem.setText( infoText );
		}
		if (infoText == null) {
			this.infoHeight = 0;
			this.showInfoItem = false;
		} else {
			this.infoHeight = this.infoItem.getItemHeight(this.screenWidth, this.screenWidth);
			this.showInfoItem = true;
		}
		
		if (this.isInitialized && this.container != null) {
			int previousContentHeight = this.contentHeight;
			calculateContentArea( 0, 0, this.screenWidth, this.screenHeight );
			int differentce = this.contentHeight - previousContentHeight;
			//System.out.println("ADJUSTING CONTAINER.YOFFSET BY " + differentce + " PIXELS FOR CONTAINER " + this.container );
			this.container.yOffset += differentce;
		} else {
			//System.out.println("!!!NOT ADJUSTING CONTAINER.YOFFSET for container " + this.container );
			calculateContentArea( 0, 0, this.screenWidth, this.screenHeight ); 
		}
		
	}

	//#ifndef polish.skipTicker
	//# /**
	 //# * Set a ticker for use with this Screen, replacing any previous ticker.
	 //# * If null, removes the ticker object
	 //# * from this screen. The same ticker is may be shared by several Screen
	 //# * objects within an application. This is done by calling setTicker() on
	 //# * different screens with the same Ticker object.
	 //# * If the Screen is physically visible, the visible effect
	 //# * should take place no later than immediately
	 //# * after the callback or
	 //# * <CODE>startApp</CODE>
	 //# * returns back to the implementation.
	 //# * 
	 //# * @param ticker - the ticker object used on this screen
	 //# */
	//# public void setPolishTicker( Ticker ticker)
	//# {
		//#debug
		//# System.out.println("setting ticker " + ticker);
		//# this.ticker = ticker;
		//# if (ticker != null) {
			//# ticker.screen = this;
			//# // initialise ticker, so that subsequently ticker.itemHeight can be called:
			//# ticker.getItemHeight(this.screenWidth, this.screenWidth );
			//# //System.out.println("setTicker(): tickerHeight=" + tickerHeight );
			//# //this.screenHeight = this.originalScreenHeight - tickerHeight;
		//# }
		//# //System.out.println("setTicker(): screenHeight=" + this.screenHeight + ", original=" + this.originalScreenHeight );
		//# calculateContentArea( 0, 0, this.screenWidth, this.screenHeight );
		//# if (super.isShown()) {
			//# repaint();
		//# }
	//# }
	//#endif
	
	//#ifndef polish.skipTicker
	//# /**
	 //# * Gets the ticker used by this Screen.
	 //# * 
	 //# * @return ticker object used, or null if no ticker is present
	 //# */
	//# public Ticker getPolishTicker()
	//# {
		//# return this.ticker;
	//# }
	//#endif
		
	/**
	 * Handles key events.
	 * 
	 * WARNING: When this method should be overwritten, one need
	 * to ensure that super.keyPressed( int ) is called!
	 * 
	 * @param keyCode The code of the pressed key
	 */
	public void keyPressed(int keyCode) {
		synchronized (this.paintLock) {
			try {
				//#debug
				//# System.out.println("keyPressed: [" + keyCode + "].");
				int gameAction = -1;
				//#if polish.blackberry
					//# this.keyPressedProcessed = true;
				//#endif
	

				try {
					gameAction = getGameAction(keyCode);
				} catch (Exception e) { // can happen when code is a  LEFT/RIGHT softkey on SE devices, for example
					//#debug warn
					//# System.out.println("Unable to get game action for key code " + keyCode + ": " + e );
				}
				boolean processed = false;
				//#if tmp.menuFullScreen
					//# boolean letTheMenuBarProcessKey;
					//#ifdef tmp.useExternalMenuBar
						//# letTheMenuBarProcessKey = this.menuBar.isOpened;
					//#else
						//# letTheMenuBarProcessKey = this.menuOpened;
					//#endif
					//#if polish.Bugs.SoftKeyMappedToFire
						//#if polish.key.LeftSoftKey:defined && polish.key.RightSoftKey:defined
							//#= if (gameAction == FIRE && (keyCode == ${polish.key.LeftSoftKey} || keyCode == ${polish.key.RightSoftKey} )) {
						//#else
						//# if (gameAction == FIRE && (keyCode == -6 || keyCode == -7)) {
						//#endif
							//# letTheMenuBarProcessKey = true;
							//# gameAction = 0;
						//# }
					//#endif
					//# if (!letTheMenuBarProcessKey) {
				//#endif
						processed = handleKeyPressed(keyCode, gameAction);
				//#if tmp.menuFullScreen
					//# }
				//#endif
				
				//#ifdef polish.debug.debug
					//# if (!processed) {
						//#debug
						//# System.out.println("unable to handle key [" + keyCode + "].");
					//# }
				//#endif
				//#if tmp.menuFullScreen
					//# if (!processed) {
						//#ifdef tmp.useExternalMenuBar
							//# if (this.menuBar.handleKeyPressed(keyCode, 0)) {
								//# //System.out.println("menubar handled key " + keyCode );
								//# repaint();
								//# return;
							//# }
							//# if (this.menuBar.isSoftKeyPressed) {
								//# //System.out.println("menubar detected softkey " + keyCode );
								//#if polish.blackberry
									//# this.keyPressedProcessed = false;
								//#endif
								//# return;
							//# }
							//# //System.out.println("menubar did not handle " + keyCode );
						//#else
							//# // internal menubar is used:
							//# if (keyCode == LEFT_SOFT_KEY) {
								//# if ( this.menuSingleLeftCommand != null) {
									//# callCommandListener( this.menuSingleLeftCommand );
									//# return;
								//# } else {
									//# if (!this.menuOpened 
											//# && this.menuContainer != null 
											//# &&  this.menuContainer.size() != 0 ) 
									//# {
										//# openMenu( true );
										//# repaint();
										//# return;
									//# } else {
										//# gameAction = Canvas.FIRE;
									//# }
								//# }
							//# } else if (keyCode == RIGHT_SOFT_KEY) {
								//# if (!this.menuOpened && this.menuSingleRightCommand != null) {
									//# callCommandListener( this.menuSingleRightCommand );
									//# return;
								//# }
							//# }
							//# boolean doReturn = false;
							//# if (keyCode == LEFT_SOFT_KEY || keyCode == RIGHT_SOFT_KEY ) {
								//#if polish.blackberry
									//# this.keyPressedProcessed = false;
								//#endif
								//# doReturn = true;
							//# }
							//# if (this.menuOpened) {
								//# if (keyCode == RIGHT_SOFT_KEY ) {
									//# int selectedIndex = this.menuContainer.getFocusedIndex();
									//# if (!this.menuContainer.handleKeyPressed(0, LEFT)
											//# || selectedIndex != this.menuContainer.getFocusedIndex() ) 
									//# {
										//# openMenu( false );
									//# }
		//# //						} else if ( gameAction == Canvas.FIRE ) {
		//# //							int focusedIndex = this.menuContainer.getFocusedIndex();
		//# //							Command cmd = (Command) this.menuCommands.get( focusedIndex );
		//# //							this.menuOpened = false;
		//# //							callCommandListener( cmd );
								//# } else { 
									//# processed = this.menuContainer.handleKeyPressed(keyCode, gameAction);
									//#if polish.key.ReturnKey:defined && polish.key.ReturnKey != polish.key.ClearKey
									//# if ((!processed)
										//#= && ( (keyCode == ${polish.key.ReturnKey}) && this.menuOpened ) 
									//# ) {
										//# openMenu( false );
									//# }
									//#endif
								//# }
								//# repaint();
								//# return;
							//# }
							//# if (doReturn) {
								//# return;
							//# }
						//#endif
					//# }
				//#endif
				//#if (polish.Screen.FireTriggersOkCommand == true) && tmp.menuFullScreen
					//# if (gameAction == FIRE && keyCode != Canvas.KEY_NUM5 && this.okCommand != null) {
						//# callCommandListener(this.okCommand);
						//# return;
					//# }
				//#endif
				//#if tmp.menuFullScreen && polish.key.ReturnKey:defined
					//# if (!processed) {
						//#= if ( (keyCode == ${polish.key.ReturnKey}) && (this.backCommand != null) ) {
								//#debug
								//# System.out.println("keyPressed: invoking commandListener for " + this.backCommand.getLabel() );
								//# callCommandListener( this.backCommand );
								//# processed = true;
						//# }
					//# }
				//#endif
				//#if polish.blackberry
					//# this.keyPressedProcessed = processed;
				//#endif
				//#if tmp.menuFullScreen
					//# if (!processed && gameAction == FIRE && keyCode != Canvas.KEY_NUM5 && this.okCommand != null) {
						//# callCommandListener(this.okCommand);
					//# }
				//#endif
				if (processed) {
					notifyScreenStateChanged();
					repaint();
				}
			} catch (Exception e) {
				//#if !polish.debug.error 
					e.printStackTrace();
				//#endif
				//#debug error
				//# System.out.println("keyPressed() threw an exception" + e );
			} finally {
				this.isScreenChangeDirtyFlag = false;
			}
		}
	}
	
	
	/**
	 * Just maps the event to the the keyPressed method.
	 * 
	 * @param keyCode the code of the key, which is pressed repeatedly
	 */
	public void keyRepeated(int keyCode) {
		//synchronized (this.paintLock) {
			//#debug
			//# System.out.println("keyRepeated(" + keyCode + ")");
			int gameAction = 0;
			try {
				gameAction = getGameAction( keyCode );
			} catch (Exception e) { // can happen when code is a  LEFT/RIGHT softkey on SE devices, for example
				//#debug warn
				//# System.out.println("Unable to get game action for key code " + keyCode + ": " + e );
			}
			//#if tmp.menuFullScreen
				//#ifdef tmp.useExternalMenuBar
					//# if (this.menuBar.handleKeyRepeated(keyCode, gameAction)) {
						//# repaint();
						//# return;
					//# } else if (this.menuBar.isOpened) {
						//# return;
					//# }
				//#else
					//# if (this.menuOpened  && this.menuContainer != null ) {
						//# if (this.menuContainer.handleKeyRepeated(keyCode, gameAction)) {
							//# repaint();
						//# }
						//# return;
					//# }
//# 	
				//#endif
			//#endif
			boolean handled = handleKeyRepeated( keyCode, gameAction );
			if ( handled ) {
				repaint();
			}
		//}
	}

	/**
	 * Is called when a key is released.
	 * 
	 * @param keyCode the code of the key, which has been released
	 */
	public void keyReleased(int keyCode) {
		//#debug
		//# System.out.println("keyReleased(" + keyCode + ")");
		int gameAction = 0;
		try {
			gameAction = getGameAction( keyCode );
		} catch (Exception e) { // can happen when code is a  LEFT/RIGHT softkey on SE devices, for example
			//#debug warn
			//# System.out.println("Unable to get game action for key code " + keyCode + ": " + e );
		}
		//#if tmp.menuFullScreen
			//#ifdef tmp.useExternalMenuBar
				//# if (this.menuBar.handleKeyReleased(keyCode, gameAction)) {
					//# repaint();
					//# return;
				//# } else if (this.menuBar.isOpened) {
					//# return;
				//# }
			//#else
				//# if (this.menuOpened  && this.menuContainer != null ) {
					//# if (this.menuContainer.handleKeyReleased(keyCode, gameAction)) {
						//# repaint();
					//# }
					//# return;
				//# }
//# 
			//#endif
		//#endif
		boolean handled = handleKeyReleased( keyCode, gameAction );
		if ( handled ) {
			repaint();
		}
	}

	//#ifdef polish.useDynamicStyles	
	//# /**
	 //# * Retrieves the CSS selector for this screen.
	 //# * The CSS selector is used for the dynamic assignment of styles -
	 //# * that is the styles are assigned by the usage of the screen and
	 //# * not by a predefined style-name.
	 //# * With the #style preprocessing command styles are set in a static way, this method
	 //# * yields in a faster GUI and is recommended. When in a style-sheet
	 //# * dynamic styles are used, e.g. "form>p", than the selector of the
	 //# * screen is needed.
	 //# * This abstract method needs only be implemented, when dynamic styles
	 //# * are used: #ifdef polish.useDynamicStyles
	 //# * 
	 //# * @return the name of the appropriate CSS Selector for this screen.
	 //# */
	//# protected abstract String createCssSelector();	
	//#endif
	
	/**
	 * Retrieves all root-items of this screen.
	 * The root items are those in first hierarchy, in a Form this is 
	 * a Container for example.
	 * The default implementation does return an empty array, since apart
	 * from the container no additional items are used.
	 * Subclasses which use more root items than the container needs
	 * to override this method.
	 * 
	 * @return the root items an array, the array can be empty but not null.
	 */
	protected Item[] getRootItems() {
		return new Item[0];
	}
	
	/**
	 * Handles the key-pressed event.
	 * Please note, that implementation should first try to handle the
	 * given key-code, before the game-action is processed.
	 * 
	 * @param keyCode the code of the pressed key, e.g. Canvas.KEY_NUM2
	 * @param gameAction the corresponding game-action, e.g. Canvas.UP
	 * @return true when the key-event was processed
	 */
	protected boolean handleKeyPressed( int keyCode, int gameAction ) {
		if (this.container == null) {
			return false;
		}
		return this.container.handleKeyPressed(keyCode, gameAction);
	}
	
	/**
	 * Handles the key-repeated event.
	 * Please note, that implementation should first try to handle the
	 * given key-code, before the game-action is processed.
	 * 
	 * @param keyCode the code of the repeated key, e.g. Canvas.KEY_NUM2
	 * @param gameAction the corresponding game-action, e.g. Canvas.UP
	 * @return true when the key-event was processed
	 */
	protected boolean handleKeyRepeated( int keyCode, int gameAction ) {
		if (this.container == null) {
			return false;
		}
		return this.container.handleKeyRepeated(keyCode, gameAction);
	}
	
	/**
	 * Handles the key-released event.
	 * Please note, that implementation should first try to handle the
	 * given key-code, before the game-action is processed.
	 * 
	 * @param keyCode the code of the released key, e.g. Canvas.KEY_NUM2
	 * @param gameAction the corresponding game-action, e.g. Canvas.UP
	 * @return true when the key-event was processed
	 */
	protected boolean handleKeyReleased( int keyCode, int gameAction ) {
		if (this.container == null) {
			return false;
		}
		return this.container.handleKeyReleased(keyCode, gameAction);
	}

	
	/**
	 * Sets the screen listener for this screen.
	 * 
	 * @param listener the listener that is notified whenever the user changes the internal state of this screen.
	 */
	public void setScreenStateListener( ScreenStateListener listener ) {
		this.screenStateListener = listener;
	}
	
	/**
	 * Notifies the screen state change listener about a change in this screen.
	 */
	public void notifyScreenStateChanged() {
		if (this.screenStateListener != null && !this.isScreenChangeDirtyFlag) {
			this.isScreenChangeDirtyFlag = true;
			this.screenStateListener.screenStateChanged( this );
		}
	}

	
	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.Displayable#setCommandListener(javax.microedition.lcdui.CommandListener)
	 */
	public void setCommandListener(CommandListener listener) {
		this.forwardCommandListener.realCommandListener = listener;
	}
	
	/**
	 * Retrieves the asscociated command listener of this screen (if any).
	 * 
	 * @return the command listener or null when none has been registered before.
	 */
	public CommandListener getCommandListener() {
		return this.forwardCommandListener.realCommandListener;
	}

	//#if tmp.menuFullScreen && !tmp.useExternalMenuBar
	//# private void updateMenuTexts() {
		//# String left = null;
		//# String right = null;
		//# int menuLeftX = 0;
		//# int menuRightX = this.screenWidth;
		//#if polish.css.separate-menubar
			//# if (!this.separateMenubar) {
				//# menuLeftX = this.marginLeft;
				//# menuRightX -= this.marginRight;
			//# }
		//#endif
		//# if (this.menuContainer != null && this.menuContainer.size() > 0) {
			//# String menuText = null;
			//# if (this.menuOpened) {
				//#ifdef polish.i18n.useDynamicTranslations
					//# menuText = "Select"; 
				//#elifdef polish.command.select:defined
					//#= menuText = "${polish.command.select}";
				//#else
					//# menuText = "Select";
				//#endif
			//# } else {
				//# if (this.menuSingleLeftCommand != null) {
					//# menuText = this.menuSingleLeftCommand.getLabel();
				//# } else {
					//#ifdef polish.i18n.useDynamicTranslations
						//# menuText = "Options"; 
					//#elifdef polish.command.options:defined
						//#= menuText = "${polish.command.options}";
					//#else
						//# menuText = "Options";				
					//#endif
				//# }
			//# }
			//# left = menuText;
			//#ifdef polish.hasPointerEvents
				//# this.menuLeftCommandX = menuLeftX + this.menuFont.stringWidth( menuText );
			//#endif
			//# if ( this.menuOpened ) {
				//# // set cancel string:
				//#ifdef polish.i18n.useDynamicTranslations
					//# menuText = "Cancel"; 
				//#elifdef polish.command.cancel:defined
					//#= menuText = "${polish.command.cancel}";
				//#else
					//# menuText = "Cancel";
				//#endif
				//#ifdef polish.MenuBar.MarginRight:defined
					//#= menuRightX -= ${polish.MenuBar.MarginRight};
				//#elifdef polish.MenuBar.PaddingRight:defined
					//#= menuRightX -= ${polish.MenuBar.PaddingRight};
				//#elifdef polish.MenuBar.MarginLeft:defined
					//# menuRightX -= 2;
				//#endif
				//# right = menuText;
			//# }
		//# }
		//# if (this.menuSingleRightCommand != null && !this.menuOpened) {
			//# String menuText = this.menuSingleRightCommand.getLabel();
			//#ifdef polish.MenuBar.MarginRight:defined
				//#= menuRightX -= ${polish.MenuBar.MarginRight};
			//#elifdef polish.MenuBar.PaddingRight:defined
				//#= menuRightX -= ${polish.MenuBar.PaddingRight};
			//#elifdef polish.MenuBar.MarginLeft:defined
				//# menuRightX -= 2;
			//#endif
			//#ifdef polish.hasPointerEvents
				//# this.menuRightCommandX = menuRightX - this.menuFont.stringWidth( menuText );
			//#endif
			//# right = menuText;
		//# }
		//# this.menuLeftString = left;
		//# this.menuRightString = right;
		//#if polish.doja
			//# Frame frame = (Frame) ((Object)this);
			//# frame.setSoftLabel( Frame.SOFT_KEY_1, left );
			//# frame.setSoftLabel( Frame.SOFT_KEY_2, right );
		//#endif
	//# }
	//#endif
	
	//#if tmp.menuFullScreen && !tmp.useExternalMenuBar
	//# private void openMenu( boolean open ) {
		//# if (!open && this.menuOpened) {
			//# this.menuContainer.hideNotify();
		//# } else if (open && !this.menuOpened) {
			//#if !polish.MenuBar.focusFirstAfterClose
				//# // focus the first item again, so when the user opens the menu again, it will be "fresh" again
				//# this.menuContainer.focus(0);
			//#endif
			//# this.menuContainer.showNotify();
		//# }
		//# this.menuOpened = open;
		//# updateMenuTexts();
	//# }
	//#endif

	//#ifdef tmp.menuFullScreen
	//# /* (non-Javadoc)
	 //# * @see javax.microedition.lcdui.Displayable#addCommand(javax.microedition.lcdui.Command)
	 //# */
	//# public void addCommand(Command cmd) {
		//#style menuitem, menu, default
		//# addCommand( cmd );
	//# }
	//#endif
	
	//#ifdef tmp.menuFullScreen
	//# /**
	 //# * Adds a command to this screen with the specified style.
	 //# * 
	 //# * @param cmd the command
	 //# * @param commandStyle the style for the command
	 //# */
	//# public void addCommand(Command cmd, Style commandStyle ) {
		//#debug
		//# System.out.println("adding command [" + cmd.getLabel() + "] to screen [" + this + "].");
		//#if tmp.useExternalMenuBar && polish.vendor.siemens
			//# if (this.menuBar == null) {
				//#debug
				//# System.out.println("Ignoring command [" + cmd.getLabel() + "] that is added while Screen is not even initialized.");
				//# return;
			//# }
		//#endif
		//# int cmdType = cmd.getCommandType();
		//# if ( cmdType == Command.OK 
				//# &&  (this.okCommand == null || this.okCommand.getPriority() < cmd.getPriority() ) ) 
		//# {
			//# this.okCommand = cmd;
		//# }
		//#ifdef polish.key.ReturnKey:defined
			//# else if ( (cmdType == Command.BACK || cmdType == Command.CANCEL || cmdType == Command.EXIT ) 
				//# && ( this.backCommand == null || cmd.getPriority() < this.backCommand.getPriority() )  ) 
			//# {
				//#debug
				//# System.out.println("setting new backcommand=" + cmd.getLabel() + " for screen " + this + " " + getTitle() );
				//# this.backCommand = cmd;
			//# }
		//#endif
		//#ifdef tmp.useExternalMenuBar
			//# this.menuBar.addCommand(cmd, commandStyle);
			//# if (super.isShown()) {
				//# if (this.menuBarHeight == 0 && this.isInitialized) {
					//# int availableWidth = this.screenWidth - (this.marginLeft + this.marginRight );
					//# this.menuBarHeight = this.menuBar.getItemHeight( availableWidth, availableWidth );
					//# this.screenHeight = this.fullScreenHeight - this.menuBarHeight;
				//# }
				//# repaint();
			//# }
		//#else
			//# if (this.menuCommands == null) {
				//# this.menuCommands = new ArrayList( 6, 50 );
				//#style menu, default
				 //# this.menuContainer = new Container( true );
				 //# this.menuContainer.screen = this;
				 //# if (this.menuContainer.style != null) {
					 //# //System.out.println("setting style for menuContainer " + this.menuContainer);
					 //# this.menuContainer.setStyle( this.menuContainer.style );
				 //# }
				 //# this.menuContainer.layout |= Item.LAYOUT_SHRINK;
			//# }
			//# if (cmd == this.menuSingleLeftCommand 
					//# || cmd == this.menuSingleRightCommand 
					//# || this.menuCommands.contains(cmd)) 
			//# {
				//# // do not add an existing command again...
				//#debug
				//# System.out.println("Ignoring existing command " + cmd.getLabel() );
				//# return;
			//# }
			//# if ( (cmdType == Command.BACK || cmdType == Command.CANCEL || cmdType == Command.EXIT) ) 
			//# {
				//# if ( (this.menuSingleRightCommand == null)
						//# || (cmd.getPriority() < this.menuSingleRightCommand.getPriority())	)
				//# {
					//# // okay set the right menu command:
					//# if (this.menuSingleRightCommand != null) {
						//# // the right menu command is replaced by the new one,
						//# // so insert the original one into the options-menu:
						//# CommandItem menuItem = new CommandItem( this.menuSingleRightCommand, this.menuContainer, commandStyle  );
						//# menuItem.screen = this;
						//# this.menuContainer.add( menuItem );
						//# if (this.menuContainer.size() == 1) {
							//# this.menuSingleLeftCommand = this.menuSingleRightCommand;
						//# } else {
							//# this.menuSingleLeftCommand = null;
						//# }
						//# this.menuCommands.add( this.menuSingleRightCommand );
					//# }					
					//# // this is a command for the right side of the menu:
					//# this.menuSingleRightCommand = cmd;
					//# if (super.isShown()) {
						//# updateMenuTexts();
						//# repaint();
					//# }
					//# return;
				//# }
			//# }
			//# CommandItem menuItem = new CommandItem( cmd, this.menuContainer, commandStyle  );
			//# menuItem.screen = this;
			//# if ( this.menuCommands.size() == 0 ) {
				//# this.menuCommands.add( cmd );
				//# this.menuContainer.add( menuItem );
				//# this.menuSingleLeftCommand = cmd;
			//# } else {
				//# this.menuSingleLeftCommand = null;
				//# // there are already several commands,
				//# // so add this cmd to the appropriate sorted position:
				//# int priority = cmd.getPriority();
				//# Command[] myCommands = (Command[]) this.menuCommands.toArray( new Command[ this.menuCommands.size() ]);
				//# boolean inserted = false;
				//# for (int i = 0; i < myCommands.length; i++) {
					//# Command command = myCommands[i];
					//# if ( cmd == command ) {
						//# // ignore existing command:
						//# return;
					//# }
					//# if (command.getPriority() > priority ) {
						//# this.menuCommands.add( i, cmd );
						//# this.menuContainer.add(i, menuItem);
						//# inserted = true;
						//# break;
					//# }
				//# }
				//# if (!inserted) {
					//# this.menuCommands.add( cmd );
					//# this.menuContainer.add( menuItem );
				//# }
			//# }
			//# if (super.isShown()) {
				//# updateMenuTexts();
				//# repaint();
			//# }
		//#endif
	//# }
	//#endif
	
	/**
	 * Retrieves the CommandItem used for rendering the specified command. 
	 * 
	 * @param command the command
	 * @return the corresponding CommandItem or null when this command is not present in this MenuBar.
	 */
	public CommandItem getCommandItem(Command command) {
		//#if tmp.menuFullScreen
			//#ifdef tmp.useExternalMenuBar
				//# return this.menuBar.getCommandItem( command );
			//#else
				//# int index = this.menuCommands.indexOf(command);
				//# if (index != -1) {
					//# return (CommandItem) this.menuContainer.get(index);
				//# } else {
					//# for (int i = 0; i < this.menuContainer.size(); i++) {
						//# CommandItem item = (CommandItem) this.menuContainer.get(i);
						//# item = item.getChild(command);
						//# if (item != null) {
							//# return item;
						//# }
					//# }
				//# }
			//#endif
		//#endif
		//#ifndef tmp.useExternalMenuBar
			return null;
		//#endif
	}

	/**
	 * Removes all commands from this screen.
	 * This option is only available when the "menu" fullscreen mode is activated.
	 */
	public void removeAllCommands() {
		//#ifdef tmp.menuFullScreen
			//#ifdef tmp.useExternalMenuBar
				//# this.menuBar.removeAllCommands();
			//#else
				//# this.menuCommands.clear();
				//# this.menuContainer.clear();
				//# updateMenuTexts();
			//#endif
			//# if (super.isShown()) {
				//# this.isInitialized = false;
				//# repaint();
			//# }
		//#endif
	}

	
	/**
	 * Adds the given command as a subcommand to the specified parent command.
	 * 
	 * @param child the child command
	 * @param parent the parent command
	 */
	public void addSubCommand(Command child, Command parent) {
		//#style menuitem, menu, default
		addSubCommand( child, parent , StyleSheet.menuitemStyle );
	}
	
	/**
	 * Adds the given command as a subcommand to the specified parent command.
	 * 
	 * @param child the child command
	 * @param parent the parent command
	 * @param commandStyle the style for the command
	 * @throws IllegalStateException when the parent command has not been added before
	 */
	public void addSubCommand(Command child, Command parent, Style commandStyle) {
		//#if !tmp.menuFullScreen
			addCommand( child );
		//#else
			//#debug
			//# System.out.println("Adding subcommand " + child.getLabel() );
			//#ifdef tmp.useExternalMenuBar
				//# this.menuBar.addSubCommand( child, parent, commandStyle );
			//#else
				//# // find parent CommandItem, could be tricky, especially when there are nested commands over several layers
				//# if ( this.menuCommands == null ) {
					//# throw new IllegalStateException();
				//# }
				//# int index = this.menuCommands.indexOf( parent );
				//# CommandItem parentCommandItem = null;
				//# if (index != -1) {
					//# // found it:
					//# parentCommandItem = (CommandItem) this.menuContainer.get( index );
				//# } else {
					//# // search through all commands
					//# for ( int i=0; i < this.menuContainer.size(); i++ ) {
						//# CommandItem item = (CommandItem) this.menuContainer.get( i );
						//# parentCommandItem = item.getChild( parent );
						//# if ( parentCommandItem != null ) {
							//# break;
						//# }
					//# }
				//# }
				//# if ( parentCommandItem == null ) {
					//# throw new IllegalStateException();
				//# }
				//# parentCommandItem.addChild( child, commandStyle );
				//# if (parent == this.menuSingleLeftCommand) {
					//# this.menuSingleLeftCommand = null;
				//# }
				//# if (this.menuOpened) {
					//# this.isInitialized = false;
					//# repaint();
				//# }				
			//#endif	
			//# if (super.isShown()) {
				//# repaint();
			//# }
		//#endif
	}

	
	//#ifdef tmp.menuFullScreen
	//# /* (non-Javadoc)
	 //# * @see javax.microedition.lcdui.Displayable#removeCommand(javax.microedition.lcdui.Command)
	 //# */
	//# public void removeCommand(Command cmd) {
		//#ifdef tmp.useExternalMenuBar
			//# this.menuBar.removeCommand(cmd);
			//# if (super.isShown()) {
				//# repaint();
			//# }
		//#else
			//# if (this.menuSingleRightCommand == cmd) {
				//# this.menuSingleRightCommand = null;
				//# //move another suitable command-item to the right-pos:
				//# if (this.menuCommands != null) {
					//# Command[] commands = (Command[]) this.menuCommands.toArray( new Command[ this.menuCommands.size()]);
					//# for (int i = 0; i < commands.length; i++) {
						//# Command command = commands[i];
						//# int type = command.getCommandType(); 
						//# if ( type == Command.BACK || type == Command.CANCEL ) {
							//# this.menuContainer.remove( i );
							//# this.menuCommands.remove( i );
							//# this.menuSingleRightCommand = command;
							//# break;
						//# }
					//# }
				//# }
				//# if (super.isShown()) {
					//# updateMenuTexts();
					//# repaint();
				//# }
				//# return;
			//# }
			//# if (this.menuCommands == null) {
				//# return;
			//# }
			//# int index = this.menuCommands.indexOf(cmd);
			//# if (index == -1) {
				//# return;
			//# }
			//# this.menuCommands.remove(index);
			//# if (this.menuSingleLeftCommand == cmd ) {
				//# this.menuSingleLeftCommand = null;
				//# this.menuContainer.remove(index);			
			//# } else {
				//# this.menuContainer.remove(index);			
			//# }
			//# if (super.isShown()) {
				//# updateMenuTexts();
				//# repaint();
			//# }
		//#endif
	//# }
	//#endif
	
	/**
	 * Sets the commands of the given item
	 * 
	 * @param item the item which has at least one command 
	 * @see #removeItemCommands(Item)
	 */
	protected void setItemCommands( Item item ) {
		this.focusedItem = item;
		if (item.commands != null) {
			Command[] commands = (Command[]) item.commands.toArray( new Command[item.commands.size()] );
			// register item commands, so that later onwards only commands that have been actually added
			// will be removed:
			if (this.itemCommands == null) {
				this.itemCommands = new ArrayList( commands.length );
			}
			for (int i = 0; i < commands.length; i++) {
				Command command = commands[i];
				// workaround for cases where the very same command has been added to both an item as well as this screen:
				//System.out.println("scren: add ItemCommand " + command.getLabel() );
				//#ifdef tmp.useExternalMenuBar
					//# if  ( !this.menuBar.commandsList.contains( command ) ) {
						//# this.menuBar.addCommand(command);
						//# this.itemCommands.add( command );
					//# }
				//#elif tmp.menuFullScreen
					//# if ( ( this.menuCommands == null) ||  !this.menuCommands.contains( command) ) {
						//# addCommand(command);
						//# this.itemCommands.add( command );
					//# }
				//#else
					addCommand(command);
					this.itemCommands.add( command );
				//#endif
			}
		} else if (this.itemCommands != null) {
			this.itemCommands.clear();
		}
		//#ifdef tmp.useExternalMenuBar
			//# if (super.isShown()) {
				//# repaint();
			//# }
		//#endif
	}
	
	/**
	 * Removes the commands of the given item.
	 *  
	 * @param item the item which has at least one command 
	 * @see #setItemCommands(Item)
	 */
	protected void removeItemCommands( Item item ) {
		if (item.commands != null && this.itemCommands != null) {
			// use the Screen's itemCommands list, since in this list only commands that are only present on the item
			// are listed (not commands that are also present on the screen).
			Command[] commands = (Command[]) this.itemCommands.toArray( new Command[this.itemCommands.size()] );
			for (int i = 0; i < commands.length; i++) {
				Command command = commands[i];
				//#ifdef tmp.useExternalMenuBar
					//# this.menuBar.removeCommand(command);
				//#else
					removeCommand(command);
				//#endif
			}
		}
		if (this.focusedItem == item) {
			this.focusedItem = null;
		}
		//#ifdef tmp.useExternalMenuBar
			//# if (super.isShown()) {
				//# repaint();
			//# }
		//#endif
	}
	
	/**
	 * Calls the command listener with the specified command.
	 * 
	 * @param cmd the command wich should be issued to the listener
	 */
	protected void callCommandListener( Command cmd ) {
		//#ifdef tmp.useExternalMenuBar
			//# this.menuBar.setOpen(false);
		//#elif tmp.menuFullScreen
			//# openMenu(false);
		//#endif
		//#if polish.ScreenChangeAnimation.forward:defined
			//# this.lastTriggeredCommand = cmd;
		//#endif
		if (this.forwardCommandListener != null) {
			try {
				this.forwardCommandListener.commandAction(cmd, this );
			} catch (Exception e) {
				//#debug error
				//# System.out.println("Screen: unable to process command [" + cmd.getLabel() + "]" + e  );
			}
		}
	}
	
	/**
	 * Retrieves the available height for this screen.
	 * This is equivalent to the Canvas#getHeight() method.
	 * This method cannot be overriden for Nokia's FullScreen though.
	 * So this method is used insted.
	 * 
	 * @return the available height in pixels.
	 */
	public int getAvailableHeight() {
		return this.screenHeight - this.titleHeight;
	}
	 	
	
	//#if polish.debugVerbose && polish.useDebugGui
	//# public void commandAction( Command command, Displayable screen ) {
		//# StyleSheet.display.setCurrent( this );
	//# }
	//#endif
	

	//#ifdef polish.hasPointerEvents
	//# /* (non-Javadoc)
	 //# * @see javax.microedition.lcdui.Canvas#pointerPressed(int, int)
	 //# */
	//# public void pointerPressed(int x, int y) {
		//#debug
		//# System.out.println("PointerPressed at " + x + ", " + y );
		//# try {
			//# // check for scroll-indicator:
			//#if tmp.useScrollIndicator
				//# //TODO support pointer events for scroll bar
				//# if (  this.paintScrollIndicator &&
						//# (x > this.scrollIndicatorX) &&
						//# (y > this.scrollIndicatorY) &&
						//# (x < this.scrollIndicatorX + this.scrollIndicatorWidth) &&
						//# (y < this.scrollIndicatorY + (this.scrollIndicatorHeight << 1) + 1) ) 
				//# {
					//#debug
					//# System.out.println("ScrollIndicator has been clicked... ");
					//# // the scroll-indicator has been clicked:
					//# int gameAction;
					//# if ( (( !this.paintScrollIndicatorUp) || (y > this.scrollIndicatorY + this.scrollIndicatorHeight)) && this.paintScrollIndicatorDown) {
						//# gameAction = Canvas.DOWN;
					//# } else {
						//# gameAction = Canvas.UP;
					//# }
					//#if tmp.menuFullScreen
						//#ifdef tmp.useExternalMenuBar
							//# if (this.menuBar.isOpened) {
								//# this.menuBar.handleKeyPressed( 0, gameAction );
							//# } else {
								//# handleKeyPressed( 0, gameAction );
							//# }
						//#else
							//# if (this.menuOpened) {
								//# this.menuContainer.handleKeyPressed( 0, gameAction );
							//# } else {
								//# handleKeyPressed( 0, gameAction );
							//# }
						//#endif
					//#else
						//# handleKeyPressed( 0, gameAction );
					//#endif
					//# repaint();
					//# return;
				//# }
			//#endif
			//#ifdef tmp.menuFullScreen
				//#ifdef tmp.useExternalMenuBar
					//# if (this.menuBar.handlePointerPressed(x, y)) {
						//# repaint();
						//# return;
					//# }
				//#else
					//# // check if one of the command buttons has been pressed:
					//# if (y > this.screenHeight) {
//# //						System.out.println("pointer at x=" + x + ", menuLeftCommandX=" + menuLeftCommandX );
						//# if (x >= this.menuRightCommandX && this.menuRightCommandX != 0) {
							//# if (this.menuOpened) {
								//# openMenu( false );
							//# } else if (this.menuSingleRightCommand != null) {
								//# callCommandListener(this.menuSingleRightCommand );
							//# }
							//# repaint();
							//# return;
						//# } else if (x <= this.menuLeftCommandX){
							//# // assume that the left command has been pressed:
//# //							System.out.println("x <= this.menuLeftCommandX: open=" + this.menuOpened );
							//# if (this.menuOpened ) {
								//# // the "SELECT" command has been clicked:
								//# this.menuContainer.handleKeyPressed(0, Canvas.FIRE);
								//# openMenu( false );
							//# } else if (this.menuSingleLeftCommand != null) {								
								//# this.callCommandListener(this.menuSingleLeftCommand);
							//# } else {
								//# openMenu( true );
								//# //this.menuOpened = true;
							//# }
							//# repaint();
							//# return;
						//# }
					//# } else if (this.menuOpened) {
						//# // a menu-item could have been selected:
						//# int menuY = this.originalScreenHeight - (this.menuContainer.itemHeight + 1);
						//# if (!this.menuContainer.handlePointerPressed( x, y - menuY )) {
							//# openMenu( false );
						//# }
						//# repaint();
						//# return;
					//# }
				//#endif
			//#endif
			//# if (this.subTitle != null && this.subTitle.handlePointerPressed(x - this.subTitle.relativeX, y - this.subTitle.relativeY)) {
				//# return;
			//# }
			//#if tmp.useScrollBar
				//# if (this.scrollBar.handlePointerPressed( x - this.scrollBar.relativeX, y - this.scrollBar.relativeY )) {
					//# return;
				//# }
			//#endif
			//# // let the screen handle the pointer pressing:
			//# boolean processed = handlePointerPressed( x, y  );
			//#ifdef tmp.usingTitle
				//# //boolean processed = handlePointerPressed( x, y - (this.titleHeight + this.infoHeight + this.subTitleHeight) );
				//# if (processed) {
					//# notifyScreenStateChanged();
					//# repaint();
				//# }
			//#else
				//# if (processed) {
					//# repaint();
				//# }
			//#endif
//# 			
			//# // #ifdef polish.debug.debug
				//# if (!processed) {
					//#debug
					//# System.out.println("PointerPressed at " + x + ", " + y + " not processed.");					
				//# }
			//# // #endif
		//# } catch (Exception e) {
			//#debug error
			//# System.out.println("PointerPressed at " + x + "," + y + " resulted in exception" + e );
		//# } finally {
			//# this.isScreenChangeDirtyFlag = false;
		//# }
	//# }
	//#endif
	
	//#ifdef polish.hasPointerEvents
	//# /**
	 //# * Handles the pressing of a pointer.
	 //# * This method should be overwritten only when the polish.hasPointerEvents 
	 //# * preprocessing symbol is defined.
	 //# * When the screen could handle the pointer pressing, it needs to 
	 //# * return true.
	 //# * The default implementation returns the result of calling the container's
	 //# *  handlePointerPressed-method
	 //# *  
	 //# * @param x the absolute x position of the pointer pressing
	 //# * @param y the absolute y position of the pointer pressing
	 //# * @return true when the pressing of the pointer was actually handled by this item.
	 //# */
	//# protected boolean handlePointerPressed( int x, int y ) {
		//# if (this.container == null) {
			//# return false;
		//# }
		//# return this.container.handlePointerPressed(x - this.container.relativeX, y - this.container.relativeY );
	//# }
	//#endif
	
	
	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.Canvas#setFullScreenMode(boolean)
	 */
	public void setFullScreenMode(boolean enable) {
		//#if polish.midp2 && !tmp.fullScreen
			super.setFullScreenMode(enable);
		//#endif
	    //#if tmp.usingTitle || tmp.menuFullScreen
			//# this.showTitleOrMenu = !enable;
		//#endif
		//#ifdef tmp.menuFullScreen
			//# if (enable) {
				//# this.screenHeight = this.fullScreenHeight;
			//# } else {
				//# this.screenHeight = this.originalScreenHeight;
				//#if tmp.usingTitle
					//# //this.screenHeight = this.originalScreenHeight - this.titleHeight;
				//#else
					//# //this.screenHeight = this.originalScreenHeight;
				//#endif
			//# }
		//#endif
		repaint();
	}
	
	
	//#if polish.midp2 && !polish.Bugs.needsNokiaUiForSystemAlerts 
	public void sizeChanged(int width, int height) {
		 //#if !polish.Bugs.sizeChangedReportsWrongHeight 
			if (!this.isInitialized) {
				return;
			}
			//#debug
			//# System.out.println("Screen: sizeChanged to width=" + width + ", height=" + height );
			//#ifdef tmp.menuFullScreen
				//# this.fullScreenHeight = height;
				//# this.screenHeight = height - this.menuBarHeight;
				//# this.originalScreenHeight = this.screenHeight;
			//#else
				this.screenHeight = height;
			//#endif
			init();
			calculateContentArea( 0, 0, this.screenWidth, this.screenHeight  );
		//#endif
	}
	//#endif
	
	/**
	 * <p>A command listener which forwards commands to the item command listener in case it encounters an item command.</p>
	 *
	 * <p>Copyright Enough Software 2004, 2005</p>

	 * <pre>
	 * history
	 *        09-Jun-2004 - rob creation
	 * </pre>
	 * @author Robert Virkus, robert@enough.de
	 */
	class ForwardCommandListener implements CommandListener {
		/** the original command listener set by the programmer */
		public CommandListener realCommandListener;

		/* (non-Javadoc)
		 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
		 */
		public void commandAction(Command cmd, Displayable thisScreen) {
			//check if the given command is from the currently focused item:
			Item item = Screen.this.getCurrentItem();
			//#debug
			//# System.out.println("FowardCommandListener: processing command " + cmd.getLabel() + " for item " + Screen.this.focusedItem + " and screen " + Screen.this );
			if ((item != null) && (item.itemCommandListener != null) && (item.commands != null)) {
				if ( item.commands.contains(cmd)) {
					item.itemCommandListener.commandAction(cmd, item);
					return;
				}
			} else if (item instanceof Container) {
				item = ((Container) item).getFocusedItem();
				while (item != null) {
					if ((item.itemCommandListener != null) && (item.commands != null) && item.commands.contains(cmd) ) {
						item.itemCommandListener.commandAction(cmd, item);
						return;
					}
					if (item instanceof Container) {
						item = ((Container) item).getFocusedItem();						
					} else {
						item = null;
					}
				}
			}
			// now invoke the usual command listener:
			if (this.realCommandListener != null) {
				this.realCommandListener.commandAction(cmd, thisScreen);
			}
		}
		
	}
	
	/**
	 * Focuses the specified item.
	 * 
	 * @param item the item which is already shown on this screen.
	 */
	public void focus(Item item) {
		int index;
		if (item == null) {
			index = -1;
		} else {
			index = this.container.itemsList.indexOf(item);
		}
		focus( index, item, false );
	}
	
	/**
	 * Focuses the specified item.
	 * 
	 * @param item the item which is already shown on this screen.
	 * @param force true when the item should be focused even when it is inactive (like a label for example)
	 */
	public void focus(Item item, boolean force ) {
		int index;
		if (item == null) {
			index = -1;
		} else {
			index = this.container.itemsList.indexOf(item);
		}
		focus( index, item, force );
	}
	
	/**
	 * Focuses the specified item.
	 * 
	 * @param index the index of the item which is already shown on this screen.
	 */
	public void focus(int index) {
		Item item = null;
		if (index != -1) {
			item = this.container.get(index);
		}
		focus( index, item, false );
	}
	
	/**
	 * Focuses the specified item.
	 * 
	 * @param index the index of the item which is already shown on this screen.
	 * @param force true when the item should be focused even when it is inactive (like a label for example)
	 */
	public void focus(int index, boolean force) {
		Item item = null;
		if (index != -1) {
			item = this.container.get(index);
		}
		focus( index, item, force );
	}
	
	/**
	 * Focuses the specified item.
	 * 
	 * @param index the index of the item which is already shown on this screen.
	 * @param item the item which is already shown on this screen.
	 * @param force true when the item should be focused even when it is inactive (like a label for example)
	 */
	public void focus(int index, Item item, boolean force) {
		if (index != -1 && item != null && (item.appearanceMode != Item.PLAIN || force ) ) {
			//#debug
			//# System.out.println("Screen: focusing item " + index );
			this.container.focus( index, item, 0 );
			if (index == 0) {
				this.container.yOffset = 0;
			}
		} else if (index == -1) {
			this.container.focus( -1 );
		} else {
			//#debug warn
			//# System.out.println("Screen: unable to focus item (did not find it in the container or is not activatable) " + index);
		}
	}
	
	/**
	 * Sets the subtitle element.
	 * The subtitle is drawn directly below of the title (above the info-item, if there
	 * is any) and is always shown (unless it is null).
	 * 
	 * @param subTitle the new subtitle element.
	 */
	protected void setSubTitle( Item subTitle ) {
		this.subTitle = subTitle;
		if (subTitle == null) {
			this.subTitleHeight = 0;
		} else {
			subTitle.screen = this;
			this.subTitle.relativeY = this.titleHeight;
			//#ifdef polish.ScreenWidth:defined
				//#= this.subTitleHeight = subTitle.getItemHeight(${polish.ScreenWidth}, ${polish.ScreenWidth});
			//#else
				this.subTitleHeight = subTitle.getItemHeight( this.screenWidth, this.screenWidth );
			//#endif
		}
		calculateContentArea( 0, 0, this.screenWidth, this.screenHeight );
	}
	
	//#if polish.Bugs.displaySetCurrentFlickers  && polish.useFullScreen
	//# /**
	 //# * Determines whether the screen is currently shown.
	 //# * When the screen is shown but the menu is openend, this method return false.
	 //# * 
	 //# * @return <code>true</code> if the screen is currently shown, <code>false</code> otherwise
	 //# * @see javax.microedition.lcdui.Displayable#isShown()
	 //# */
	//# public boolean isShown() {
		//# return (StyleSheet.currentScreen == this);
	//# }
	//#endif
	
	// when returning false in isShown(), repaint() will not trigger an actual repaint() on Sony Ericsson devices
	// so we cannot implement this behavior differently
//	//#if polish.Bugs.displaySetCurrentFlickers || tmp.menuFullScreen
//	/**
//	 * Determines whether the screen is currently shown.
//	 * When the screen is shown but the menu is openend, this method return false.
//	 * 
//	 * @return <code>true</code> if the screen is currently shown, <code>false</code> otherwise
//	 * @see javax.microedition.lcdui.Displayable#isShown()
//	 */
//	public boolean isShown() {
//		boolean isShown;
//		//#if polish.Bugs.displaySetCurrentFlickers
//			isShown = (StyleSheet.currentScreen == this);
//		//#else
//			isShown = super.isShown();
//		//#endif
//		//#if tmp.menuFullScreen
//			//#if tmp.useExternalMenuBar
//				isShown &= this.menuBar != null && !this.menuBar.isOpened;
//			//#else
//				isShown &= !this.menuOpened;
//			//#endif
//		//#endif
//		return isShown;
//	}
//	//#endif
	
	/**
	 * Retrieves the currently focused item.
	 * 
	 * @return the currently focused item, null when none is focused.
	 */
	public Item getCurrentItem() {
		if (this.container != null) {
			if (this.container.autoFocusEnabled) {
				Item[] items = this.container.getItems();
				for (int i = 0; i < items.length; i++) {
					if (i >= this.container.autoFocusIndex && items[i].appearanceMode != Item.PLAIN) {
						return items[i];
					}
				}
			}
			return this.container.focusedItem;
		}
		return this.focusedItem;
	}
	
	/**
	 * Retrieves the index of the currently focused item.
	 * 
	 * @return  the index of the currently focused item, -1 when no item has been selected
	 */
	public int getCurrentIndex() {
		if (this.container != null) {
			return this.container.focusedIndex;
		}
		return -1;
	}

	/**
	 * Checks whether the commands menu of the screen is currently opened.
	 * Useful when overriding the keyPressed() method.
	 * 
	 * @return true when the commands menu is opened.
	 */
	public boolean isMenuOpened() {
		//#if !tmp.menuFullScreen
			return false;
		//#elif tmp.useExternalMenuBar
			//# return this.menuBar.isOpened;
		//#else
			//# return this.menuOpened;
		//#endif

	}
	
	/**
	 * Releases all (memory intensive) resources such as images or RGB arrays of this item.
	 * The default implementation does release any background resources.
	 */
	public void releaseResources() {
		if (this.background != null) {
			this.background.releaseResources();
		}
		if (this.container != null) {
			this.container.releaseResources();
		}
		//#ifdef tmp.menuFullScreen
			//#ifdef tmp.useExternalMenuBar
				//# this.menuBar.releaseResources();
			//#else
				//# this.menuContainer.releaseResources();
			//#endif
		//#endif
		//#ifdef tmp.usingTitle
			//# if (this.title != null) {
				//# this.title.releaseResources();
			//# }
		//#endif
		//#ifndef polish.skipTicker
			//# if (this.ticker != null) {
				//# this.ticker.releaseResources();
			//# }
		//#endif	
	}

	/**
	 * Scrolls this screen by the given amount.
	 * 
	 * @param amount the number of pixels, positive values scroll upwards, negative scroll downwards
	 */
	public void scrollRelative(int amount) {
		if (this.container != null) {
			this.container.setScrollYOffset( this.container.getScrollYOffset() + amount );
			repaint();
		}
	}

	/**
	 * Attaches data to this screen.
	 * This mechanism can be used to add business logic to screens.
	 * 
	 * @param data the screen specific data
	 * @see UiAccess#setData(Screen, Object)
	 * @see UiAccess#getData(Screen)
	 */
	public void setScreenData(Object data) {
		this.data = data;
	}
	
	/**
	 * Retrieves screen specific data.
	 * This mechanism can be used to add business logic to screens.
	 * 
	 * @return any screen specific data or null when no data has been attached before
	 * @see UiAccess#setData(Screen, Object)
	 * @see UiAccess#getData(Screen)
	 */
	public Object getScreenData() {
		return this.data;
	}


		
	
//#ifdef polish.Screen.additionalMethods:defined
	//#include ${polish.Screen.additionalMethods}
//#endif
	
	

}
