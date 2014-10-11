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

public class DeviceInfo {
	private String currentPackageName;
	private int displayWidth;
	private int displayHeight;
	private int displayRotation;
	private int displaySizeDpX;
	private int displaySizeDpY;
	private String productName;
	private boolean naturalOrientation;
	
	private int sdkInt;

	private DeviceInfo() {
	}

	public String getCurrentPackageName() {
		return currentPackageName;
	}

	public void setCurrentPackageName(String currentPackageName) {
		this.currentPackageName = currentPackageName;
	}

	public int getDisplayWidth() {
		return displayWidth;
	}

	public void setDisplayWidth(int displayWidth) {
		this.displayWidth = displayWidth;
	}

	public int getDisplayHeight() {
		return displayHeight;
	}

	public void setDisplayHeight(int displayHeight) {
		this.displayHeight = displayHeight;
	}

	public int getDisplayRotation() {
		return displayRotation;
	}

	public void setDisplayRotation(int displayRotation) {
		this.displayRotation = displayRotation;
	}

	public int getDisplaySizeDpX() {
		return displaySizeDpX;
	}

	public void setDisplaySizeDpX(int displaySizeDpX) {
		this.displaySizeDpX = displaySizeDpX;
	}

	public int getDisplaySizeDpY() {
		return displaySizeDpY;
	}

	public void setDisplaySizeDpY(int displaySizeDpY) {
		this.displaySizeDpY = displaySizeDpY;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public boolean isNaturalOrientation() {
		return naturalOrientation;
	}

	public void setNaturalOrientation(boolean naturalOrientation) {
		this.naturalOrientation = naturalOrientation;
	}

	public int getSdkInt() {
		return sdkInt;
	}

	public void setSdkInt(int sdkInt) {
		this.sdkInt = sdkInt;
	}

}
