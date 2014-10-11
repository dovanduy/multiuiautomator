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
 * Used to enumerate a container's UI elements for the purpose of counting,
 * or targeting a sub elements by a child's text or description.
 * @since API Level 16
 */
public class UiCollection extends UiObject {

    /**
     * Constructs an instance as described by the selector
     *
     * @param selector
     * @since API Level 16
     */
    public UiCollection(UiDevice device, UiSelector selector) {
        super(device, selector);
    }

    /**
     * Searches for child UI element within the constraints of this UiCollection {@link UiSelector}
     * selector.
     *
     * It looks for any child matching the <code>childPattern</code> argument that has
     * a child UI element anywhere within its sub hierarchy that has content-description text.
     * The returned UiObject will point at the <code>childPattern</code> instance that matched the
     * search and not at the identifying child element that matched the content description.</p>
     *
     * @param childPattern {@link UiSelector} selector of the child pattern to match and return
     * @param text String of the identifying child contents of of the <code>childPattern</code>
     * @return {@link UiObject} pointing at and instance of <code>childPattern</code>
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public UiObject getChildByDescription(final UiSelector childPattern, final String text)
            throws UiObjectNotFoundException {
    	
    	final Selector realSelector = childPattern.toSelector(); 
    	
    	ObjectRetriever retriever = new ObjectRetriever() {
			@Override
			public String getObjectId() throws UiObjectNotFoundException {
				return mAutomatorService.childByDescription(mSelector.toSelector(),
															realSelector,
															text);
			}
			
		};
    	
		return new UiObject(retriever, mAutomatorService);
    	
    }

    /**
     * Searches for child UI element within the constraints of this UiCollection {@link UiSelector}
     * selector.
     *
     * It looks for any child matching the <code>childPattern</code> argument that has
     * a child UI element anywhere within its sub hierarchy that is at the <code>instance</code>
     * specified. The operation is performed only on the visible items and no scrolling is performed
     * in this case.
     *
     * @param childPattern {@link UiSelector} selector of the child pattern to match and return
     * @param instance int the desired matched instance of this <code>childPattern</code>
     * @return {@link UiObject} pointing at and instance of <code>childPattern</code>
     * @since API Level 16
     */
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
     * Searches for child UI element within the constraints of this UiCollection {@link UiSelector}
     * selector.
     *
     * It looks for any child matching the <code>childPattern</code> argument that has
     * a child UI element anywhere within its sub hierarchy that has text attribute =
     * <code>text</code>. The returned UiObject will point at the <code>childPattern</code>
     * instance that matched the search and not at the identifying child element that matched the
     * text attribute.</p>
     *
     * @param childPattern {@link UiSelector} selector of the child pattern to match and return
     * @param text String of the identifying child contents of of the <code>childPattern</code>
     * @return {@link UiObject} pointing at and instance of <code>childPattern</code>
     * @throws UiObjectNotFoundException
     * @since API Level 16
     */
    public UiObject getChildByText(UiSelector childPattern, final String text)
            throws UiObjectNotFoundException {

    	final Selector realSelector = childPattern.toSelector(); 
    	
    	ObjectRetriever retriever = new ObjectRetriever() {
			@Override
			public String getObjectId() throws UiObjectNotFoundException {
				return mAutomatorService.childByText(mSelector.toSelector(),
															realSelector,
															text);
			}
			
		};
    	
		return new UiObject(retriever, mAutomatorService);
    	
    }

    /**
     * Counts child UI element instances matching the <code>childPattern</code>
     * argument. The method returns the number of matching UI elements that are
     * currently visible.  The count does not include items of a scrollable list
     * that are off-screen.
     *
     * @param childPattern a {@link UiSelector} that represents the matching child UI
     * elements to count
     * @return the number of matched childPattern under the current {@link UiCollection}
     * @since API Level 16
     */
    public int getChildCount(UiSelector childPattern) {
    	//TODO: implement this
    	throw new UnsupportedOperationException();
    }
}
