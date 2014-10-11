/*
 * 
 * Copyright (C) 2014 Lorenzo Keller
 * 
 * Based on work:
 * 
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.uiautomator.core;

import com.github.uiautomatorstub.Selector;

/**
 * UiScrollable is a {@link UiCollection} and provides support for searching
 * for items in scrollable layout elements. This class can be used with
 * horizontally or vertically scrollable controls.
 * @since API Level 16
 */
public class UiScrollable extends UiCollection {

    // Used in ScrollForward() and ScrollBackward() to determine swipe direction
    private boolean mIsVerticalList = true;
	private int mMaxSearchSwipes;

    /**
     * Constructor.
     *
     * @param container a {@link UiSelector} selector to identify the scrollable
     *     layout element.
     * @since API Level 16
     */
    public UiScrollable(UiDevice device, UiSelector container) {
        // wrap the container selector with container so that QueryController can handle
        // this type of enumeration search accordingly
        super(device, container);
    }

    /**
     * Set the direction of swipes to be vertical when performing scroll actions.
     * @return reference to itself
     * @since API Level 16
     */
    public UiScrollable setAsVerticalList() {    	
        mIsVerticalList = true;
        return this;
    }

    /**
     * Set the direction of swipes to be horizontal when performing scroll actions.
     * @return reference to itself
     * @since API Level 16
     */
    public UiScrollable setAsHorizontalList() {
        mIsVerticalList = false;
        return this;
    }

    /**
     * Searches for a child element in the present scrollable container.
     * The search first looks for a child element that matches the selector
     * you provided, then looks for the content-description in its children elements.
     * If both search conditions are fulfilled, the method returns a {@ link UiObject}
     * representing the element matching the selector (not the child element in its
     * subhierarchy containing the content-description). By default, this method performs a
     * scroll search.
     * See {@link #getChildByDescription(UiSelector, String, boolean)}
     *
     * @param childPattern {@link UiSelector} for a child in a scollable layout element
     * @param text Content-description to find in the children of 
     * the <code>childPattern</code> match
     * @return {@link UiObject} representing the child element that matches the search conditions
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    @Override
    public UiObject getChildByDescription(UiSelector childPattern, final String text)
            throws UiObjectNotFoundException {

    	return getChildByDescription(childPattern, text, true);
		
    }

    /**
     * Searches for a child element in the present scrollable container.
     * The search first looks for a child element that matches the selector
     * you provided, then looks for the content-description in its children elements.
     * If both search conditions are fulfilled, the method returns a {@ link UiObject}
     * representing the element matching the selector (not the child element in its
     * subhierarchy containing the content-description).
     *
     * @param childPattern {@link UiSelector} for a child in a scollable layout element
     * @param text Content-description to find in the children of 
     * the <code>childPattern</code> match (may be a partial match)
     * @param allowScrollSearch set to true if scrolling is allowed
     * @return {@link UiObject} representing the child element that matches the search conditions
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public UiObject getChildByDescription(UiSelector childPattern, final String text,
            final boolean allowScrollSearch) throws UiObjectNotFoundException {
        

    	final Selector realSelector = childPattern.toSelector(); 
    	
    	ObjectRetriever retriever = new ObjectRetriever() {
			@Override
			public String getObjectId() throws UiObjectNotFoundException {
				return mAutomatorService.childByDescription(mSelector.toSelector(),
															realSelector,
															text, allowScrollSearch);
			}
			
		};
    	
		return new UiObject(retriever, mAutomatorService);
		
    }

    /**
     * Searches for a child element in the present scrollable container that
     * matches the selector you provided. The search is performed without
     * scrolling and only on visible elements.
     *
     * @param childPattern {@link UiSelector} for a child in a scollable layout element
     * @param instance int number representing the occurance of 
     * a <code>childPattern</code> match
     * @return {@link UiObject} representing the child element that matches the search conditions
     * @since API Level 16
     */
    @Override
    public UiObject getChildByInstance(UiSelector childPattern, final int instance)
            throws UiObjectNotFoundException {
        
    	final Selector realSelector = childPattern.toSelector(); 
    	
    	ObjectRetriever retriever = new ObjectRetriever() {
			@Override
			public String getObjectId() throws UiObjectNotFoundException {
				return mAutomatorService.childByInstance(mSelector.toSelector(),
															realSelector,
															instance);
			}
			
		};
    	
		return new UiObject(retriever, mAutomatorService);
    }

    /**
     * Searches for a child element in the present scrollable
     * container. The search first looks for a child element that matches the
     * selector you provided, then looks for the text in its children elements.
     * If both search conditions are fulfilled, the method returns a {@ link UiObject}
     * representing the element matching the selector (not the child element in its
     * subhierarchy containing the text). By default, this method performs a
     * scroll search.
     * See {@link #getChildByText(UiSelector, String, boolean)}
     *
     * @param childPattern {@link UiSelector} selector for a child in a scrollable layout element
     * @param text String to find in the children of the <code>childPattern</code> match
     * @return {@link UiObject} representing the child element that matches the search conditions
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    @Override
    public UiObject getChildByText(UiSelector childPattern, String text)
            throws UiObjectNotFoundException {
        return getChildByText(childPattern, text, true);
    }

    /**
     * Searches for a child element in the present scrollable container. The
     * search first looks for a child element that matches the
     * selector you provided, then looks for the text in its children elements.
     * If both search conditions are fulfilled, the method returns a {@ link UiObject}
     * representing the element matching the selector (not the child element in its
     * subhierarchy containing the text).
     *
     * @param childPattern {@link UiSelector} selector for a child in a scrollable layout element
     * @param text String to find in the children of the <code>childPattern</code> match
     * @param allowScrollSearch set to true if scrolling is allowed
     * @return {@link UiObject} representing the child element that matches the search conditions
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public UiObject getChildByText(UiSelector childPattern, final String text, final boolean allowScrollSearch)
            throws UiObjectNotFoundException {
        
    	final Selector realSelector = childPattern.toSelector(); 
    	
    	ObjectRetriever retriever = new ObjectRetriever() {
			@Override
			public String getObjectId() throws UiObjectNotFoundException {
				return mAutomatorService.childByText(mSelector.toSelector(),
															realSelector,
															text,
															allowScrollSearch);
			}
			
		};
    	
		return new UiObject(retriever, mAutomatorService);
    }

    /**
     * Performs a forward scroll action on the scrollable layout element until
     * the content-description is found, or until swipe attempts have been exhausted.
     * See {@link #setMaxSearchSwipes(int)}
     *
     * @param text content-description to find within the contents of this scrollable layout element.
     * @return true if item is found; else, false
     * @since API Level 16
     */
    public boolean scrollDescriptionIntoView(String text) throws UiObjectNotFoundException {
        return scrollIntoView(new UiSelector().description(text));
    }

    /**
     * Perform a forward scroll action to move through the scrollable layout element until
     * a visible item that matches the {@link UiObject} is found.
     *
     * @param obj {@link UiObject}
     * @return true if the item was found and now is in view else false
     * @since API Level 16
     */
    public boolean scrollIntoView(UiObject obj) throws UiObjectNotFoundException {
    	//FIXME: handle objects that have no selector
    	return mAutomatorService.scrollTo(mSelector.toSelector(), obj.mSelector.toSelector(), mIsVerticalList);
    }

    /**
     * Perform a scroll forward action to move through the scrollable layout 
     * element until a visible item that matches the selector is found.
     *
     * See {@link #scrollDescriptionIntoView(String)} and {@link #scrollTextIntoView(String)}.
     *
     * @param selector {@link UiSelector} selector
     * @return true if the item was found and now is in view; else, false
     * @since API Level 16
     */
    public boolean scrollIntoView(UiSelector selector) throws UiObjectNotFoundException {
    	return mAutomatorService.scrollTo(mSelector.toSelector(), selector.toSelector(), mIsVerticalList);
    }

    /**
     * Performs a forward scroll action on the scrollable layout element until
     * the text you provided is visible, or until swipe attempts have been exhausted.
     * See {@link #setMaxSearchSwipes(int)}
     *
     * @param text test to look for
     * @return true if item is found; else, false
     * @since API Level 16
     */
    public boolean scrollTextIntoView(String text) throws UiObjectNotFoundException {
        return scrollIntoView(new UiSelector().text(text));
    }

    /**
     * Sets the maximum number of scrolls allowed when performing a
     * scroll action in search of a child element.
     * See {@link #getChildByDescription(UiSelector, String)} and
     * {@link #getChildByText(UiSelector, String)}.
     *
     * @param swipes the number of search swipes to perform until giving up
     * @return reference to itself
     * @since API Level 16
     */
    public UiScrollable setMaxSearchSwipes(int swipes) {
        mMaxSearchSwipes = swipes;
        return this;
    }

    /**
     * Gets the maximum number of scrolls allowed when performing a
     * scroll action in search of a child element.
     * See {@link #getChildByDescription(UiSelector, String)} and
     * {@link #getChildByText(UiSelector, String)}.
     *
     * @return max the number of search swipes to perform until giving up
     * @since API Level 16
     */
    public int getMaxSearchSwipes() {
        return mMaxSearchSwipes;
    }

    /**
     * Performs a forward fling with the default number of fling steps (5).
     * If the swipe direction is set to vertical, then the swipes will be
     * performed from bottom to top. If the swipe
     * direction is set to horizontal, then the swipes will be performed from
     * right to left. Make sure to take into account devices configured with
     * right-to-left languages like Arabic and Hebrew.
     *
     * @return true if scrolled, false if can't scroll anymore
     * @since API Level 16
     */
    public boolean flingForward() throws UiObjectNotFoundException {
    	return mAutomatorService.flingForward(mSelector.toSelector(), mIsVerticalList);
    }

    /**
     * Performs a forward scroll with the default number of scroll steps (55).
     * If the swipe direction is set to vertical,
     * then the swipes will be performed from bottom to top. If the swipe
     * direction is set to horizontal, then the swipes will be performed from
     * right to left. Make sure to take into account devices configured with
     * right-to-left languages like Arabic and Hebrew.
     *
     * @return true if scrolled, false if can't scroll anymore
     * @since API Level 16
     */
    public boolean scrollForward() throws UiObjectNotFoundException {
    	return mAutomatorService.scrollForward(mSelector.toSelector(), mIsVerticalList, 55);
    }

    /**
     * Performs a forward scroll. If the swipe direction is set to vertical,
     * then the swipes will be performed from bottom to top. If the swipe
     * direction is set to horizontal, then the swipes will be performed from
     * right to left. Make sure to take into account devices configured with
     * right-to-left languages like Arabic and Hebrew.
     *
     * @param steps number of steps. Use this to control the speed of the scroll action
     * @return true if scrolled, false if can't scroll anymore
     * @since API Level 16
     */
    public boolean scrollForward(int steps) throws UiObjectNotFoundException {
    	return mAutomatorService.scrollForward(mSelector.toSelector(), mIsVerticalList, steps);
    }

    /**
     * Performs a backwards fling action with the default number of fling
     * steps (5). If the swipe direction is set to vertical,
     * then the swipe will be performed from top to bottom. If the swipe
     * direction is set to horizontal, then the swipes will be performed from
     * left to right. Make sure to take into account devices configured with
     * right-to-left languages like Arabic and Hebrew.
     *
     * @return true if scrolled, and false if can't scroll anymore
     * @since API Level 16
     */
    public boolean flingBackward() throws UiObjectNotFoundException {
    	return mAutomatorService.flingBackward(mSelector.toSelector(), mIsVerticalList);
    }

    /**
     * Performs a backward scroll with the default number of scroll steps (55).
     * If the swipe direction is set to vertical,
     * then the swipes will be performed from top to bottom. If the swipe
     * direction is set to horizontal, then the swipes will be performed from
     * left to right. Make sure to take into account devices configured with
     * right-to-left languages like Arabic and Hebrew.
     *
     * @return true if scrolled, and false if can't scroll anymore
     * @since API Level 16
     */
    public boolean scrollBackward() throws UiObjectNotFoundException {
    	return mAutomatorService.scrollBackward(mSelector.toSelector(), mIsVerticalList, 55);
    }

    /**
     * Performs a backward scroll. If the swipe direction is set to vertical,
     * then the swipes will be performed from top to bottom. If the swipe
     * direction is set to horizontal, then the swipes will be performed from
     * left to right. Make sure to take into account devices configured with
     * right-to-left languages like Arabic and Hebrew.
     *
     * @param steps number of steps. Use this to control the speed of the scroll action.
     * @return true if scrolled, false if can't scroll anymore
     * @since API Level 16
     */
    public boolean scrollBackward(int steps) throws UiObjectNotFoundException {
    	return mAutomatorService.scrollBackward(mSelector.toSelector(), mIsVerticalList, steps);
    }

    /**
     * Scrolls to the beginning of a scrollable layout element. The beginning
     * can be at the  top-most edge in the case of vertical controls, or the
     * left-most edge for horizontal controls. Make sure to take into account
     * devices configured with right-to-left languages like Arabic and Hebrew.
     *
     * @param steps use steps to control the speed, so that it may be a scroll, or fling
     * @return true on scrolled else false
     * @since API Level 16
     */
    public boolean scrollToBeginning(int maxSwipes, int steps) throws UiObjectNotFoundException {
    	return mAutomatorService.scrollToBeginning(mSelector.toSelector(), mIsVerticalList, maxSwipes, steps);
    }

    /**
     * Scrolls to the beginning of a scrollable layout element. The beginning
     * can be at the  top-most edge in the case of vertical controls, or the
     * left-most edge for horizontal controls. Make sure to take into account
     * devices configured with right-to-left languages like Arabic and Hebrew.
     *
     * @param maxSwipes
     * @return true on scrolled else false
     * @since API Level 16
     */
    public boolean scrollToBeginning(int maxSwipes) throws UiObjectNotFoundException {
    	return mAutomatorService.scrollToBeginning(mSelector.toSelector(), mIsVerticalList, maxSwipes, 55);
    }

    /**
     * Performs a fling gesture to reach the beginning of a scrollable layout element.
     * The beginning can be at the  top-most edge in the case of vertical controls, or
     * the left-most edge for horizontal controls. Make sure to take into
     * account devices configured with right-to-left languages like Arabic and Hebrew.
     *
     * @param maxSwipes
     * @return true on scrolled else false
     * @since API Level 16
     */
    public boolean flingToBeginning(int maxSwipes) throws UiObjectNotFoundException {
        return mAutomatorService.flingToBeginning(mSelector.toSelector(), mIsVerticalList, maxSwipes);
    }

    /**
     * Scrolls to the end of a scrollable layout element. The end can be at the
     * bottom-most edge in the case of vertical controls, or the right-most edge for
     * horizontal controls. Make sure to take into account devices configured with
     * right-to-left languages like Arabic and Hebrew.
     *
     * @param steps use steps to control the speed, so that it may be a scroll, or fling
     * @return true on scrolled else false
     * @since API Level 16
     */
    public boolean scrollToEnd(int maxSwipes, int steps) throws UiObjectNotFoundException {
    	return mAutomatorService.scrollToEnd(mSelector.toSelector(), mIsVerticalList, maxSwipes, steps);
    }

    /**
     * Scrolls to the end of a scrollable layout element. The end can be at the
     * bottom-most edge in the case of vertical controls, or the right-most edge for
     * horizontal controls. Make sure to take into account devices configured with
     * right-to-left languages like Arabic and Hebrew.
     *
     * @param maxSwipes
     * @return true on scrolled, else false
     * @since API Level 16
     */
    public boolean scrollToEnd(int maxSwipes) throws UiObjectNotFoundException {
    	return scrollToEnd(maxSwipes, 55);
    }

    /**
     * Performs a fling gesture to reach the end of a scrollable layout element.
     * The end can be at the  bottom-most edge in the case of vertical controls, or
     * the right-most edge for horizontal controls. Make sure to take into
     * account devices configured with right-to-left languages like Arabic and Hebrew.
     *
     * @param maxSwipes
     * @return true on scrolled, else false
     * @since API Level 16
     */
    public boolean flingToEnd(int maxSwipes) throws UiObjectNotFoundException {
    	return mAutomatorService.flingToEnd(mSelector.toSelector(), mIsVerticalList, maxSwipes);
    }

    /**
     * Returns the percentage of a widget's size that's considered as a no-touch
     * zone when swiping. The no-touch zone is set as a percentage of a widget's total
     * width or height, denoting a margin around the swipable area of the widget.
     * Swipes must start and end inside this margin. This is important when the
     * widget being swiped may not respond to the swipe if started at a point
     * too near to the edge. The default is 10% from either edge.
     *
     * @return a value between 0 and 1
     * @since API Level 16
     */
    public double getSwipeDeadZonePercentage() {
    	//TODO:
    	throw new UnsupportedOperationException();
    }

    /**
     * Sets the percentage of a widget's size that's considered as no-touch
     * zone when swiping.
     * The no-touch zone is set as percentage of a widget's total width or height,
     * denoting a margin around the swipable area of the widget. Swipes must
     * always start and end inside this margin. This is important when the
     * widget being swiped may not respond to the swipe if started at a point
     * too near to the edge. The default is 10% from either edge.
     *
     * @param swipeDeadZonePercentage is a value between 0 and 1
     * @return reference to itself
     * @since API Level 16
     */
    public UiScrollable setSwipeDeadZonePercentage(double swipeDeadZonePercentage) {
    	//TODO:
    	throw new UnsupportedOperationException();
    }
}
