/*
 * Copyright (C) 2014 Lorenzo Keller
 * 
 * Based on work:
 * 
 * Copyright (C) 2013 The Android Open Source Project
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

import com.github.uiautomatorstub.AutomatorService;
import com.github.uiautomatorstub.ConfiguratorInfo;
import com.github.uiautomatorstub.NotImplementedException;

/**
 * Allows you to set key parameters for running uiautomator tests. The new
 * settings take effect immediately and can be changed any time during a test run.
 *
 * To modify parameters using Configurator, first obtain an instance by calling
 * {@link #getInstance()}. As a best practice, make sure you always save
 * the original value of any parameter that you are modifying. After running your
 * tests with the modified parameters, make sure to also restore
 * the original parameter values, otherwise this will impact other tests cases.
 * @since API Level 18
 */
public final class Configurator {
	
	private AutomatorService mAutomatorService;
	
	public static Configurator getInstance(UiDevice device) {
		return new Configurator(device.getAutomatorService());
	}
	
	static Configurator getInstance(AutomatorService device) {
		return new Configurator(device);
	}
	
	private Configurator(AutomatorService automatorService) {
		mAutomatorService = automatorService;
	}
	
    /**
     * Sets the timeout for waiting for the user interface to go into an idle
     * state before starting a uiautomator action.
     *
     * By default, all core uiautomator objects except {@link UiDevice} will perform
     * this wait before starting to search for the widget specified by the
     * object's {@link UiSelector}. Once the idle state is detected or the
     * timeout elapses (whichever occurs first), the object will start to wait
     * for the selector to find a match.
     * See {@link #setWaitForSelectorTimeout(long)}
     *
     * @param timeout Timeout value in milliseconds
     * @return self
     * @since API Level 18
     */
    public Configurator setWaitForIdleTimeout(long timeout) {
    	
    	try {
			ConfiguratorInfo info = mAutomatorService.getConfigurator();
			
			info.setWaitForIdleTimeout(timeout);
			
			mAutomatorService.setConfigurator(info);
			
			return this;
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
        
    }

    /**
     * Gets the current timeout used for waiting for the user interface to go
     * into an idle state.
     *
     * By default, all core uiautomator objects except {@link UiDevice} will perform
     * this wait before starting to search for the widget specified by the
     * object's {@link UiSelector}. Once the idle state is detected or the
     * timeout elapses (whichever occurs first), the object will start to wait
     * for the selector to find a match.
     * See {@link #setWaitForSelectorTimeout(long)}
     *
     * @return Current timeout value in milliseconds
     * @since API Level 18
     */
    public long getWaitForIdleTimeout() {
    	try {
			return mAutomatorService.getConfigurator().getWaitForIdleTimeout();
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Sets the timeout for waiting for a widget to become visible in the user
     * interface so that it can be matched by a selector.
     *
     * Because user interface content is dynamic, sometimes a widget may not
     * be visible immediately and won't be detected by a selector. This timeout
     * allows the uiautomator framework to wait for a match to be found, up until
     * the timeout elapses.
     *
     * @param timeout Timeout value in milliseconds.
     * @return self
     * @since API Level 18
     */
    public Configurator setWaitForSelectorTimeout(long timeout) {

    	try {
			ConfiguratorInfo info = mAutomatorService.getConfigurator();
			
			info.setWaitForSelectorTimeout(timeout);
			
			mAutomatorService.setConfigurator(info);
			
			return this;
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Gets the current timeout for waiting for a widget to become visible in
     * the user interface so that it can be matched by a selector.
     *
     * Because user interface content is dynamic, sometimes a widget may not
     * be visible immediately and won't be detected by a selector. This timeout
     * allows the uiautomator framework to wait for a match to be found, up until
     * the timeout elapses.
     *
     * @return Current timeout value in milliseconds
     * @since API Level 18
     */
    public long getWaitForSelectorTimeout() {
    	try {
			return mAutomatorService.getConfigurator().getWaitForSelectorTimeout();
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Sets the timeout for waiting for an acknowledgement of an
     * uiautomtor scroll swipe action.
     *
     * The acknowledgment is an <a href="http://developer.android.com/reference/android/view/accessibility/AccessibilityEvent.html">AccessibilityEvent</a>,
     * corresponding to the scroll action, that lets the framework determine if
     * the scroll action was successful. Generally, this timeout should not be modified.
     * See {@link UiScrollable}
     *
     * @param timeout Timeout value in milliseconds
     * @return self
     * @since API Level 18
     */
    public Configurator setScrollAcknowledgmentTimeout(long timeout) {
    	try {
			ConfiguratorInfo info = mAutomatorService.getConfigurator();
			
			info.setScrollAcknowledgmentTimeout(timeout);
			
			mAutomatorService.setConfigurator(info);
			
			return this;
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Gets the timeout for waiting for an acknowledgement of an
     * uiautomtor scroll swipe action.
     *
     * The acknowledgment is an <a href="http://developer.android.com/reference/android/view/accessibility/AccessibilityEvent.html">AccessibilityEvent</a>,
     * corresponding to the scroll action, that lets the framework determine if
     * the scroll action was successful. Generally, this timeout should not be modified.
     * See {@link UiScrollable}
     *
     * @return current timeout in milliseconds
     * @since API Level 18
     */
    public long getScrollAcknowledgmentTimeout() {
    	try {
			return mAutomatorService.getConfigurator().getScrollAcknowledgmentTimeout();
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Sets the timeout for waiting for an acknowledgment of generic uiautomator
     * actions, such as clicks, text setting, and menu presses.
     *
     * The acknowledgment is an <a href="http://developer.android.com/reference/android/view/accessibility/AccessibilityEvent.html">AccessibilityEvent</a>,
     * corresponding to an action, that lets the framework determine if the
     * action was successful. Generally, this timeout should not be modified.
     * See {@link UiObject}
     *
     * @param timeout Timeout value in milliseconds
     * @return self
     * @since API Level 18
     */
    public Configurator setActionAcknowledgmentTimeout(long timeout) {
    	try {
			ConfiguratorInfo info = mAutomatorService.getConfigurator();
			
			info.setActionAcknowledgmentTimeout(timeout);
			
			mAutomatorService.setConfigurator(info);
			
			return this;
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Gets the current timeout for waiting for an acknowledgment of generic
     * uiautomator actions, such as clicks, text setting, and menu presses.
     *
     * The acknowledgment is an <a href="http://developer.android.com/reference/android/view/accessibility/AccessibilityEvent.html">AccessibilityEvent</a>,
     * corresponding to an action, that lets the framework determine if the
     * action was successful. Generally, this timeout should not be modified.
     * See {@link UiObject}
     *
     * @return current timeout in milliseconds
     * @since API Level 18
     */
    public long getActionAcknowledgmentTimeout() {
    	try {
			return mAutomatorService.getConfigurator().getActionAcknowledgmentTimeout();
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Sets a delay between key presses when injecting text input.
     * See {@link UiObject#setText(String)}
     *
     * @param delay Delay value in milliseconds
     * @return self
     * @since API Level 18
     */
    public Configurator setKeyInjectionDelay(long delay) {

    	try {
			ConfiguratorInfo info = mAutomatorService.getConfigurator();
			
			info.setKeyInjectionDelay(delay);
			
			mAutomatorService.setConfigurator(info);
			
			return this;
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }

    /**
     * Gets the current delay between key presses when injecting text input.
     * See {@link UiObject#setText(String)}
     *
     * @return current delay in milliseconds
     * @since API Level 18
     */
    public long getKeyInjectionDelay() {
    	try {
			return mAutomatorService.getConfigurator().getKeyInjectionDelay();
		} catch (NotImplementedException e) {
			throw new UnsupportedOperationException();
		}
    }
}
