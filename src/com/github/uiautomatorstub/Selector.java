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

public class Selector {

	private String text;
	private String textContains;
	private String textMatches;
	private String textStartsWith;
	private String className;
	private String classNameMatches;
	private String description;
	private String descriptionContains;
	private String descriptionMatches;
	private String descriptionStartsWith;
	private boolean checkable;
	private boolean checked;
	private boolean clickable;
	private boolean longClickable;
	private boolean scrollable;
	private boolean enabled;
	private boolean focusable;
	private boolean focused;
	private boolean selected;
	private String packageName;
	private String packageNameMatches;
	private String resourceId;
	private String resourceIdMatches;
	private int index;
	private int instance;
	private Selector[] childOrSiblingSelector = {};
	private String[] childOrSibling = {};

	private long mask;

	public static final long MASKTEXT = 0x01;
	public static final long MASKTEXTCONTAINS = 0x02;
	public static final long MASKTEXTMATCHES = 0x04;
	public static final long MASKTEXTSTARTSWITH = 0x08;
	public static final long MASKCLASSNAME = 0x10;
	public static final long MASKCLASSNAMEMATCHES = 0x20;
	public static final long MASKDESCRIPTION = 0x40;
	public static final long MASKDESCRIPTIONCONTAINS = 0x80;
	public static final long MASKDESCRIPTIONMATCHES = 0x0100;
	public static final long MASKDESCRIPTIONSTARTSWITH = 0x0200;
	public static final long MASKCHECKABLE = 0x0400;
	public static final long MASKCHECKED = 0x0800;
	public static final long MASKCLICKABLE = 0x1000;
	public static final long MASKLONGCLICKABLE = 0x2000;
	public static final long MASKSCROLLABLE = 0x4000;
	public static final long MASKENABLED = 0x8000;
	public static final long MASKFOCUSABLE = 0x010000;
	public static final long MASKFOCUSED = 0x020000;
	public static final long MASKSELECTED = 0x040000;
	public static final long MASKPACKAGENAME = 0x080000;
	public static final long MASKPACKAGENAMEMATCHES = 0x100000;
	public static final long MASKRESOURCEID = 0x200000;
	public static final long MASKRESOURCEIDMATCHES = 0x400000;
	public static final long MASKINDEX = 0x800000;
	public static final long MASKINSTANCE = 0x01000000;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.mask |= MASKTEXT;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
		this.mask |= MASKCLASSNAME;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		this.mask |= MASKDESCRIPTION;
	}

	public String getTextContains() {
		return textContains;
	}

	public void setTextContains(String textContains) {
		this.textContains = textContains;
		this.mask |= MASKTEXTCONTAINS;
	}

	public String getTextMatches() {
		return textMatches;
	}

	public void setTextMatches(String textMatches) {
		this.textMatches = textMatches;
		this.mask |= MASKTEXTMATCHES;
	}

	public String getTextStartsWith() {
		return textStartsWith;
	}

	public void setTextStartsWith(String textStartsWith) {
		this.textStartsWith = textStartsWith;
		this.mask |= MASKTEXTSTARTSWITH;
	}

	public String getClassNameMatches() {
		return classNameMatches;
	}

	public void setClassNameMatches(String classNameMatches) {
		this.classNameMatches = classNameMatches;
		this.mask |= MASKCLASSNAMEMATCHES;
	}

	public String getDescriptionContains() {
		return descriptionContains;
	}

	public void setDescriptionContains(String descriptionContains) {
		this.descriptionContains = descriptionContains;
		this.mask |= MASKDESCRIPTIONCONTAINS;
	}

	public String getDescriptionMatches() {
		return descriptionMatches;
	}

	public void setDescriptionMatches(String descriptionMatches) {
		this.descriptionMatches = descriptionMatches;
		this.mask |= MASKDESCRIPTIONMATCHES;
	}

	public String getDescriptionStartsWith() {
		return descriptionStartsWith;
	}

	public void setDescriptionStartsWith(String descriptionStartsWith) {
		this.descriptionStartsWith = descriptionStartsWith;
		this.mask |= MASKDESCRIPTIONSTARTSWITH;
	}

	public boolean isCheckable() {
		return checkable;
	}

	public void setCheckable(boolean checkable) {
		this.checkable = checkable;
		this.mask |= MASKCHECKABLE;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		this.mask |= MASKCHECKED;
	}

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
		this.mask |= MASKCLICKABLE;
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
		this.mask |= MASKSCROLLABLE;
	}

	public boolean isLongClickable() {
		return longClickable;
	}

	public void setLongClickable(boolean longClickable) {
		this.longClickable = longClickable;
		this.mask |= MASKLONGCLICKABLE;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		this.mask |= MASKENABLED;
	}

	public boolean isFocusable() {
		return focusable;
	}

	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
		this.mask |= MASKFOCUSABLE;
	}

	public boolean isFocused() {
		return focused;
	}

	public void setFocused(boolean focused) {
		this.focused = focused;
		this.mask |= MASKFOCUSED;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		this.mask |= MASKSELECTED;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
		this.mask |= MASKPACKAGENAME;
	}

	public String getPackageNameMatches() {
		return packageNameMatches;
	}

	public void setPackageNameMatches(String packageNameMatches) {
		this.packageNameMatches = packageNameMatches;
		this.mask |= MASKPACKAGENAMEMATCHES;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
		this.mask |= MASKRESOURCEID;
	}

	public String getResourceIdMatches() {
		return resourceIdMatches;
	}

	public void setResourceIdMatches(String resourceIdMatches) {
		this.resourceIdMatches = resourceIdMatches;
		this.mask |= MASKRESOURCEIDMATCHES;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
		this.mask |= MASKINDEX;
	}

	public int getInstance() {
		return instance;
	}

	public void setInstance(int instance) {
		this.instance = instance;
		this.mask |= MASKINSTANCE;
	}

	public long getMask() {
		return mask;
	}

	public void setMask(long mask) {
		this.mask = mask;
	}

	public Selector[] getChildOrSiblingSelector() {
		return childOrSiblingSelector;
	}

	public void setChildOrSiblingSelector(Selector[] childOrSiblingSelector) {
		this.childOrSiblingSelector = childOrSiblingSelector;
	}

	public String[] getChildOrSibling() {
		return childOrSibling;
	}

	public void setChildOrSibling(String[] childOrSibling) {
		this.childOrSibling = childOrSibling;
	}
}
