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


public class Rect {

	private int top;
	private int bottom;
	private int left;
	private int right;

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public android.graphics.Rect toRect() {		
		return new android.graphics.Rect(left, top, right, bottom);
	}

}
