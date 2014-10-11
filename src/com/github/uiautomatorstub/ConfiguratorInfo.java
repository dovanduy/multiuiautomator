/*******************************************************************************
 * Copyright (c) 2014, Lorenzo Keller
 *
 * Based on work Copyright (c) 2013 Xiaocong He (see LICENSE)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.github.uiautomatorstub;

public class ConfiguratorInfo {

	private long actionAcknowledgmentTimeout;
	private long keyInjectionDelay;
	private long scrollAcknowledgmentTimeout;
	private long waitForIdleTimeout;
	private long waitForSelectorTimeout;

	public ConfiguratorInfo() {

	}

	public long getActionAcknowledgmentTimeout() {
		return actionAcknowledgmentTimeout;
	}

	public void setActionAcknowledgmentTimeout(long actionAcknowledgmentTimeout) {
		this.actionAcknowledgmentTimeout = actionAcknowledgmentTimeout;
	}

	public long getKeyInjectionDelay() {
		return keyInjectionDelay;
	}

	public void setKeyInjectionDelay(long keyInjectionDelay) {
		this.keyInjectionDelay = keyInjectionDelay;
	}

	public long getScrollAcknowledgmentTimeout() {
		return scrollAcknowledgmentTimeout;
	}

	public void setScrollAcknowledgmentTimeout(long scrollAcknowledgmentTimeout) {
		this.scrollAcknowledgmentTimeout = scrollAcknowledgmentTimeout;
	}

	public long getWaitForIdleTimeout() {
		return waitForIdleTimeout;
	}

	public void setWaitForIdleTimeout(long waitForIdleTimeout) {
		this.waitForIdleTimeout = waitForIdleTimeout;
	}

	public long getWaitForSelectorTimeout() {
		return waitForSelectorTimeout;
	}

	public void setWaitForSelectorTimeout(long waitForSelectorTimeout) {
		this.waitForSelectorTimeout = waitForSelectorTimeout;
	}

}
