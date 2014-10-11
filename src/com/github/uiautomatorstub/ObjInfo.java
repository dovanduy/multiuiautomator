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

public class ObjInfo {

	private Rect bounds;
	private Rect visibleBounds;
	private int chileCount;
	private String className;
	private String contentDescription;
	private String packageName;
	private String text;
	private boolean checkable;
	private boolean checked;
	private boolean clickable;
	private boolean enabled;
	private boolean focusable;
	private boolean focused;
	private boolean longClickable;
	private boolean scrollable;
	private boolean selected;

	public ObjInfo() {

	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}

	public Rect getVisibleBounds() {
		return visibleBounds;
	}

	public void setVisibleBounds(Rect visibleBounds) {
		this.visibleBounds = visibleBounds;
	}

	public int getChileCount() {
		return chileCount;
	}

	public void setChileCount(int chileCount) {
		this.chileCount = chileCount;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getContentDescription() {
		return contentDescription;
	}

	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isCheckable() {
		return checkable;
	}

	public void setCheckable(boolean checkable) {
		this.checkable = checkable;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isFocusable() {
		return focusable;
	}

	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}

	public boolean isFocused() {
		return focused;
	}

	public void setFocused(boolean focused) {
		this.focused = focused;
	}

	public boolean isLongClickable() {
		return longClickable;
	}

	public void setLongClickable(boolean longClickable) {
		this.longClickable = longClickable;
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}