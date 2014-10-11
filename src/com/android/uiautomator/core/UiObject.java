/*
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

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent.PointerCoords;

import com.github.uiautomatorstub.AutomatorService;
import com.github.uiautomatorstub.NotImplementedException;
import com.github.uiautomatorstub.Selector;

/**
 * A UiObject is a representation of a view. It is not in any way directly bound to a
 * view as an object reference. A UiObject contains information to help it
 * locate a matching view at runtime based on the {@link UiSelector} properties specified in
 * its constructor. Once you create an instance of a UiObject, it can
 * be reused for different views that match the selector criteria.
 * @since API Level 16
 */
public class UiObject {
    
    protected final UiSelector mSelector;

    protected AutomatorService mAutomatorService;

    protected interface ObjectRetriever {
    	String getObjectId() throws UiObjectNotFoundException ;    	
    };
    
    protected final ObjectRetriever mRetriever;
    
    protected final Configurator mConfigurator;
    
    /**
     * Constructs a UiObject to represent a view that matches the specified
     * selector criteria.
     * @param selector
     * @since API Level 16
     */
    public UiObject(UiDevice device, UiSelector selector) {
    	
        mSelector = selector;
        mAutomatorService = device.getAutomatorService();
        
        mRetriever = new ObjectRetriever() {
			@Override
			public String getObjectId() throws UiObjectNotFoundException {
				return mAutomatorService.getUiObject(mSelector.toSelector());
			}
		};
		
		mConfigurator = Configurator.getInstance(mAutomatorService);
    }
    
    protected UiObject(ObjectRetriever retriever, AutomatorService service) {
    	
    	mAutomatorService = service;
    	mSelector = null;
    	mRetriever = retriever;

		mConfigurator = Configurator.getInstance(mAutomatorService);
    }

    /**
     * Debugging helper. A test can dump the properties of a selector as a string
     * to its logs if needed. <code>getSelector().toString();</code>
     *
     * @return {@link UiSelector}
     * @since API Level 16
     */
    public final UiSelector getSelector() {
        return new UiSelector(mSelector);
    }

    /**
     * Creates a new UiObject for a child view that is under the present UiObject.
     *
     * @param selector for child view to match
     * @return a new UiObject representing the child view
     * @since API Level 16
     */
    public UiObject getChild(UiSelector selector) throws UiObjectNotFoundException {

    	final Selector realSelector = selector.toSelector(); 
    	
    	ObjectRetriever retriever = new ObjectRetriever() {
			@Override
			public String getObjectId() throws UiObjectNotFoundException {
				return mAutomatorService.getChild(mRetriever.getObjectId(), realSelector);
			}
			
		};
    	
		return new UiObject(retriever, mAutomatorService);
    	
    }

    /**
     * Creates a new UiObject for a sibling view or a child of the sibling view, 
     * relative to the present UiObject.
     *
     * @param selector for a sibling view or children of the sibling view
     * @return a new UiObject representing the matched view
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public UiObject getFromParent(UiSelector selector) throws UiObjectNotFoundException {

    	final Selector realSelector = selector.toSelector(); 
    	
    	ObjectRetriever retriever = new ObjectRetriever() {
			@Override
			public String getObjectId() throws UiObjectNotFoundException {
				return mAutomatorService.getFromParent(mRetriever.getObjectId(), realSelector);
			}
		};
    	
		return new UiObject(retriever, mAutomatorService);
    	
    }

    /**
     * Counts the child views immediately under the present UiObject.
     *
     * @return the count of child views.
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public int getChildCount() throws UiObjectNotFoundException {
        return mAutomatorService.objInfo(mRetriever.getObjectId()).getChileCount();
    }

    /**
     * Drags this object to a destination UiObject.
     * The number of steps specified in your input parameter can influence the
     * drag speed, and varying speeds may impact the results. Consider
     * evaluating different speeds when using this method in your tests.
     *
     * @param destObj the destination UiObject.
     * @param steps usually 40 steps. You can increase or decrease the steps to change the speed.
     * @return true if successful
     * @throws UiObjectNotFoundException
     * @since API Level 18
     */
    public boolean dragTo(UiObject destObj, int steps) throws UiObjectNotFoundException {
    	//TODO: 
    	throw new UnsupportedOperationException();
    }

    /**
     * Drags this object to arbitrary coordinates.
     * The number of steps specified in your input parameter can influence the
     * drag speed, and varying speeds may impact the results. Consider
     * evaluating different speeds when using this method in your tests.
     *
     * @param destX the X-axis coordinate.
     * @param destY the Y-axis coordinate.
     * @param steps usually 40 steps. You can increase or decrease the steps to change the speed.
     * @return true if successful
     * @throws UiObjectNotFoundException
     * @since API Level 18
     */
    public boolean dragTo(int destX, int destY, int steps) throws UiObjectNotFoundException {
    	try {
			return mAutomatorService.dragTo(mRetriever.getObjectId(), destX, destY, steps);
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Performs the swipe up action on the UiObject. 
     * See also:
     * <ul>
     * <li>{@link UiScrollable#scrollToBeginning(int)}</li>
     * <li>{@link UiScrollable#scrollToEnd(int)}</li>
     * <li>{@link UiScrollable#scrollBackward()}</li>
     * <li>{@link UiScrollable#scrollForward()}</li>
     * </ul>
     *
     * @param steps indicates the number of injected move steps into the system. Steps are
     * injected about 5ms apart. So a 100 steps may take about 1/2 second to complete.
     * @return true of successful
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean swipeUp(int steps) throws UiObjectNotFoundException {
        return mAutomatorService.swipe(mRetriever.getObjectId(), "up", steps);
    }

    /**
     * Performs the swipe down action on the UiObject. 
     * The swipe gesture can be performed over any surface. The targeted
     * UI element does not need to be scrollable.
     * See also:
     * <ul>
     * <li>{@link UiScrollable#scrollToBeginning(int)}</li>
     * <li>{@link UiScrollable#scrollToEnd(int)}</li>
     * <li>{@link UiScrollable#scrollBackward()}</li>
     * <li>{@link UiScrollable#scrollForward()}</li>
     * </ul>
     *
     * @param steps indicates the number of injected move steps into the system. Steps are
     * injected about 5ms apart. So a 100 steps may take about 1/2 second to complete.
     * @return true if successful
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean swipeDown(int steps) throws UiObjectNotFoundException {
    	return mAutomatorService.swipe(mRetriever.getObjectId(), "down", steps);
    }

    /**
     * Performs the swipe left action on the UiObject. 
     * The swipe gesture can be performed over any surface. The targeted
     * UI element does not need to be scrollable.
     * See also:
     * <ul>
     * <li>{@link UiScrollable#scrollToBeginning(int)}</li>
     * <li>{@link UiScrollable#scrollToEnd(int)}</li>
     * <li>{@link UiScrollable#scrollBackward()}</li>
     * <li>{@link UiScrollable#scrollForward()}</li>
     * </ul>
     *
     * @param steps indicates the number of injected move steps into the system. Steps are
     * injected about 5ms apart. So a 100 steps may take about 1/2 second to complete.
     * @return true if successful
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean swipeLeft(int steps) throws UiObjectNotFoundException {
    	return mAutomatorService.swipe(mRetriever.getObjectId(), "left", steps);
    }

    /**
     * Performs the swipe right action on the UiObject. 
     * The swipe gesture can be performed over any surface. The targeted
     * UI element does not need to be scrollable.
     * See also:
     * <ul>
     * <li>{@link UiScrollable#scrollToBeginning(int)}</li>
     * <li>{@link UiScrollable#scrollToEnd(int)}</li>
     * <li>{@link UiScrollable#scrollBackward()}</li>
     * <li>{@link UiScrollable#scrollForward()}</li>
     * </ul>
     *
     * @param steps indicates the number of injected move steps into the system. Steps are
     * injected about 5ms apart. So a 100 steps may take about 1/2 second to complete.
     * @return true if successful
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean swipeRight(int steps) throws UiObjectNotFoundException {
    	return mAutomatorService.swipe(mRetriever.getObjectId(), "right", steps);
    }

    /**
     * Performs a click at the center of the visible bounds of the UI element represented
     * by this UiObject.
     *
     * @return true id successful else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean click() throws UiObjectNotFoundException {
    	return mAutomatorService.click(mRetriever.getObjectId());
    }

    /**
     * Waits for window transitions that would typically take longer than the
     * usual default timeouts.
     * See {@link #clickAndWaitForNewWindow(long)}
     *
     * @return true if the event was triggered, else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    
    public boolean clickAndWaitForNewWindow() throws UiObjectNotFoundException {
    	return mAutomatorService.clickAndWaitForNewWindow(mRetriever.getObjectId(), mConfigurator.getWaitForIdleTimeout());
    }

    /**
     * Performs a click at the center of the visible bounds of the UI element represented
     * by this UiObject and waits for window transitions.
     *
     * This method differ from {@link UiObject#click()} only in that this method waits for a
     * a new window transition as a result of the click. Some examples of a window transition:
     * <li>launching a new activity</li>
     * <li>bringing up a pop-up menu</li>
     * <li>bringing up a dialog</li>
     *
     * @param timeout timeout before giving up on waiting for a new window
     * @return true if the event was triggered, else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean clickAndWaitForNewWindow(long timeout) throws UiObjectNotFoundException {
    	return mAutomatorService.clickAndWaitForNewWindow(mRetriever.getObjectId(), timeout);
    }

    /**
     * Clicks the top and left corner of the UI element
     *
     * @return true on success
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean clickTopLeft() throws UiObjectNotFoundException {
    	return mAutomatorService.click(mRetriever.getObjectId(), "topleft");
    }

    /**
     * Long clicks bottom and right corner of the UI element
     *
     * @return true if operation was successful
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean longClickBottomRight() throws UiObjectNotFoundException  {
    	return mAutomatorService.longClick(mRetriever.getObjectId(), "bottomright");
    }

    /**
     * Clicks the bottom and right corner of the UI element
     *
     * @return true on success
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean clickBottomRight() throws UiObjectNotFoundException {
    	return mAutomatorService.click(mRetriever.getObjectId(), "bottomright");
    }

    /**
     * Long clicks the center of the visible bounds of the UI element
     *
     * @return true if operation was successful
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean longClick() throws UiObjectNotFoundException  {
    	return mAutomatorService.longClick(mRetriever.getObjectId());
    }

    /**
     * Long clicks on the top and left corner of the UI element
     *
     * @return true if operation was successful
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean longClickTopLeft() throws UiObjectNotFoundException {
    	return mAutomatorService.longClick(mRetriever.getObjectId(), "topleft");
    }

    /**
     * Reads the <code>text</code> property of the UI element
     *
     * @return text value of the current node represented by this UiObject
     * @throws UiObjectNotFoundException if no match could be found
     * @since API Level 16
     */
    public String getText() throws UiObjectNotFoundException {
    	return mAutomatorService.getText(mRetriever.getObjectId());   	
    }

    /**
     * Retrieves the <code>className</code> property of the UI element.
     *
     * @return class name of the current node represented by this UiObject
     * @throws UiObjectNotFoundException if no match was found
     * @since API Level 18
     */
    public String getClassName() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).getClassName();
    }

    /**
     * Reads the <code>content_desc</code> property of the UI element
     *
     * @return value of node attribute "content_desc"
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public String getContentDescription() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).getContentDescription();
    }

    /**
     * Sets the text in an editable field, after clearing the field's content.
     *
     * The {@link UiSelector} selector of this object must reference a UI element that is editable.
     *
     * When you call this method, the method first simulates a {@link #click()} on
     * editable field to set focus. The method then clears the field's contents
     * and injects your specified text into the field.
     *
     * If you want to capture the original contents of the field, call {@link #getText()} first.
     * You can then modify the text and use this method to update the field.
     *
     * @param text string to set
     * @return true if operation is successful
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean setText(String text) throws UiObjectNotFoundException {
    	return mAutomatorService.setText(mRetriever.getObjectId(), text);
    }

    /**
     * Clears the existing text contents in an editable field.
     *
     * The {@link UiSelector} of this object must reference a UI element that is editable.
     *
     * When you call this method, the method first sets focus at the start edge of the field.
     * The method then simulates a long-press to select the existing text, and deletes the
     * selected text.
     *
     * If a "Select-All" option is displayed, the method will automatically attempt to use it
     * to ensure full text selection.
     *
     * Note that it is possible that not all the text in the field is selected; for example,
     * if the text contains separators such as spaces, slashes, at symbol etc.
     * Also, not all editable fields support the long-press functionality.
     *
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public void clearTextField() throws UiObjectNotFoundException {
    	mAutomatorService.clearTextField(mRetriever.getObjectId());
    }

    /**
     * Check if the UI element's <code>checked</code> property is currently true
     *
     * @return true if it is else false
     * @since API Level 16
     */
    public boolean isChecked() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).isChecked();
    }

    /**
     * Checks if the UI element's <code>selected</code> property is currently true.
     *
     * @return true if it is else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean isSelected() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).isSelected();
    }

    /**
     * Checks if the UI element's <code>checkable</code> property is currently true.
     *
     * @return true if it is else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean isCheckable() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).isCheckable();
    }

    /**
     * Checks if the UI element's <code>enabled</code> property is currently true.
     *
     * @return true if it is else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean isEnabled() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).isEnabled();
    }

    /**
     * Checks if the UI element's <code>clickable</code> property is currently true.
     *
     * @return true if it is else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean isClickable() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).isClickable();
    }

    /**
     * Check if the UI element's <code>focused</code> property is currently true
     *
     * @return true if it is else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean isFocused() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).isFocused();
    }

    /**
     * Check if the UI element's <code>focusable</code> property is currently true.
     *
     * @return true if it is else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean isFocusable() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).isFocusable();
    }

    /**
     * Check if the view's <code>scrollable</code> property is currently true
     *
     * @return true if it is else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean isScrollable() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).isScrollable();
    }

    /**
     * Check if the view's <code>long-clickable</code> property is currently true
     *
     * @return true if it is else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public boolean isLongClickable() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).isLongClickable();
    }

    /**
     * Reads the view's <code>package</code> property
     *
     * @return true if it is else false
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public String getPackageName() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).getPackageName();
    }

    /**
     * Returns the visible bounds of the view.
     *
     * If a portion of the view is visible, only the bounds of the visible portion are
     * reported.
     *
     * @return Rect
     * @throws UiObjectNotFoundException
     * @see {@link #getBounds()}
     * @since API Level 17
     */
    public Rect getVisibleBounds() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).getVisibleBounds().toRect();
    }

    /**
     * Returns the view's <code>bounds</code> property. See {@link #getVisibleBounds()}
     *
     * @return Rect
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public Rect getBounds() throws UiObjectNotFoundException {
    	return mAutomatorService.objInfo(mRetriever.getObjectId()).getBounds().toRect();
    }

    /**
     * Waits a specified length of time for a view to become visible.
     *
     * This method waits until the view becomes visible on the display, or
     * until the timeout has elapsed. You can use this method in situations where
     * the content that you want to select is not immediately displayed.
     *
     * @param timeout the amount of time to wait (in milliseconds)
     * @return true if the view is displayed, else false if timeout elapsed while waiting
     * @since API Level 16
     */
    public boolean waitForExists(long timeout) {
    	if ( mSelector != null ) {
    		return mAutomatorService.waitForExists(mSelector.toSelector(), timeout);
    	} else {
			try {
				return mAutomatorService.waitForExists(mRetriever.getObjectId(), timeout);
			} catch (UiObjectNotFoundException e) {
				//TODO: when this happens and can we something about it?
				throw new RuntimeException("Error while waiting");
			}
    	}		
    }

    /**
     * Waits a specified length of time for a view to become undetectable.
     *
     * This method waits until a view is no longer matchable, or until the
     * timeout has elapsed.
     *
     * A view becomes undetectable when the {@link UiSelector} of the object is
     * unable to find a match because the element has either changed its state or is no
     * longer displayed.
     *
     * You can use this method when attempting to wait for some long operation
     * to compete, such as downloading a large file or connecting to a remote server.
     *
     * @param timeout time to wait (in milliseconds)
     * @return true if the element is gone before timeout elapsed, else false if timeout elapsed
     * but a matching element is still found.
     * @since API Level 16
     */
    public boolean waitUntilGone(long timeout) {
    	if ( mSelector != null ) {
    		return mAutomatorService.waitUntilGone(mSelector.toSelector(), timeout);
    	} else {
			try {
				return mAutomatorService.waitUntilGone(mRetriever.getObjectId(), timeout);
			} catch (UiObjectNotFoundException e) {
				//TODO: when this happens and can we something about it?
				return true;
			}
    	}		
    }

    /**
     * Check if view exists.
     *
     * This methods performs a {@link #waitForExists(long)} with zero timeout. This
     * basically returns immediately whether the view represented by this UiObject
     * exists or not. If you need to wait longer for this view, then see
     * {@link #waitForExists(long)}.
     *
     * @return true if the view represented by this UiObject does exist
     * @since API Level 16
     */
    public boolean exists() {
    	if ( mSelector != null ) {
    		return mAutomatorService.exist(mSelector.toSelector());
    	} else {
			try {
				return mAutomatorService.exist(mRetriever.getObjectId());
			} catch (UiObjectNotFoundException e) {
				return false;
			}
    	}	
    }

    /**
     * Performs a two-pointer gesture, where each pointer moves diagonally
     * opposite across the other, from the center out towards the edges of the
     * this UiObject.
     * @param percent percentage of the object's diagonal length for the pinch gesture
     * @param steps the number of steps for the gesture. Steps are injected 
     * about 5 milliseconds apart, so 100 steps may take around 0.5 seconds to complete.
     * @return <code>true</code> if all touch events for this gesture are injected successfully,
     *         <code>false</code> otherwise
     * @throws UiObjectNotFoundException
     * @since API Level 18
     */
    public boolean pinchOut(int percent, int steps) throws UiObjectNotFoundException {
    	try {
			return mAutomatorService.pinchOut(mRetriever.getObjectId(), percent, steps);
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Performs a two-pointer gesture, where each pointer moves diagonally
     * toward the other, from the edges to the center of this UiObject .
     * @param percent percentage of the object's diagonal length for the pinch gesture
     * @param steps the number of steps for the gesture. Steps are injected 
     * about 5 milliseconds apart, so 100 steps may take around 0.5 seconds to complete.
     * @return <code>true</code> if all touch events for this gesture are injected successfully,
     *         <code>false</code> otherwise
     * @throws UiObjectNotFoundException
     * @since API Level 18
     */
    public boolean pinchIn(int percent, int steps) throws UiObjectNotFoundException {
    	try {
			return mAutomatorService.pinchIn(mRetriever.getObjectId(), percent, steps);
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Generates a two-pointer gesture with arbitrary starting and ending points.
     *
     * @param startPoint1 start point of pointer 1
     * @param startPoint2 start point of pointer 2
     * @param endPoint1 end point of pointer 1
     * @param endPoint2 end point of pointer 2
     * @param steps the number of steps for the gesture. Steps are injected 
     * about 5 milliseconds apart, so 100 steps may take around 0.5 seconds to complete.
     * @return <code>true</code> if all touch events for this gesture are injected successfully,
     *         <code>false</code> otherwise
     * @since API Level 18
     */
    public boolean performTwoPointerGesture(Point startPoint1, Point startPoint2, Point endPoint1,
            Point endPoint2, int steps) {
    	try {
			return mAutomatorService.gesture(mRetriever.getObjectId(), new com.github.uiautomatorstub.Point(startPoint1), 
																		new com.github.uiautomatorstub.Point(startPoint2),
																		new com.github.uiautomatorstub.Point(endPoint1),
																		new com.github.uiautomatorstub.Point(endPoint2), steps);
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		} catch (UiObjectNotFoundException e) {
			return false;
		}
    }

    /**
     * Performs a multi-touch gesture. You must specify touch coordinates for
     * at least 2 pointers. Each pointer must have all of its touch steps
     * defined in an array of {@link PointerCoords}. You can use this method to
     * specify complex gestures, like circles and irregular shapes, where each
     * pointer may take a different path.
     *
     * To create a single point on a pointer's touch path:
     * <code>
     *       PointerCoords p = new PointerCoords();
     *       p.x = stepX;
     *       p.y = stepY;
     *       p.pressure = 1;
     *       p.size = 1;
     * </code>
     * @param touches represents the pointers' paths. Each {@link PointerCoords}
     * array represents a different pointer. Each {@link PointerCoords} in an
     * array element represents a touch point on a pointer's path.
     * @return <code>true</code> if all touch events for this gesture are injected successfully,
     *         <code>false</code> otherwise
     * @since API Level 18
     */
    public boolean performMultiPointerGesture(PointerCoords[] ...touches) {
    	throw new UnsupportedOperationException();
    }
}
