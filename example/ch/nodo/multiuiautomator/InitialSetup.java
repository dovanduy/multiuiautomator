/*******************************************************************************
 * Copyright (c) 2014, Lorenzo Keller
 *
 * Based on an example from the Android SDK
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

package ch.nodo.multiuiautomator;

import org.junit.Before;
import org.junit.Test;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import static org.junit.Assert.*;

public class InitialSetup extends UiAutomatorTestCase {

	UiDevice mDevice;
	
	@Before
	public void setUp() {

		mDevice = getEmulatorController("first").getUiDevice();

		/*
		
		EmulatorController emulator = getEmulatorController("first");
		
		String appPath = System.getProperty("APP_APK");
		
		if (appPath != null) { 
			throw new RuntimeException("Please set APP_APK pointing to the application apk"); 
		}
		
		emulator.installApplication(appPath);
		*/
	}

	@Test
	public void testInitialSetup() throws Exception {

		mDevice.pressHome();

		UiObject okButton = new UiObject(mDevice, new UiSelector().text("OK"));

		okButton.click();

		UiObject allAppsButton = new UiObject(mDevice,	new UiSelector().description("Apps"));

		allAppsButton.waitForExists(1000);

		allAppsButton.clickAndWaitForNewWindow();

		okButton.waitForExists(1000);
		
		okButton.click();
		
		UiObject appsTab = new UiObject(mDevice, new UiSelector().text("Apps"));

		appsTab.click();

		UiScrollable appViews = new UiScrollable(mDevice, new UiSelector().scrollable(true));

		appViews.setAsHorizontalList();

		UiObject settingsApp = appViews.getChildByText(new UiSelector().className("android.widget.TextView"), "Settings");
		settingsApp.clickAndWaitForNewWindow();

		UiObject settingsValidation = new UiObject(mDevice, new UiSelector().packageName("com.android.settings"));
		assertTrue("Unable to detect Settings", settingsValidation.exists());

	}

}
