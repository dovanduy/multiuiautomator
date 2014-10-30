/*******************************************************************************
 * Copyright (c) 2014, Lorenzo Keller
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
package com.android.uiautomator.testrunner;

import java.io.File;
import java.util.HashMap;

import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TestName;

import ch.nodo.multiuiautomator.SdkTools;
import ch.nodo.multiuiautomator.SdkTools.EmulatorController;

import com.android.uiautomator.core.UiDevice;


/**
 * 
 * Base class for tests on emulators. 
 * 
 * The class provides methods to create, setup and shut down emulators. Moreover
 * at the end of the test it takes care of capturing screen output and ui tree
 * to help debugging test failures.
 *
 */

public class UiAutomatorTestCase {
	
	private static HashMap<String, EmulatorController> mEmulators = new HashMap<String,EmulatorController>();	
	
	@Rule
	public TestName name = new TestName();
	
	/**
	 * 
	 * Creates and starts a new emulator instance
	 * 
	 * @param name the name of the emulator instance
	 * @return an object that can be used to control the emulator
	 *  
	 * @throws Exception
	 */
	protected EmulatorController getEmulatorController(String name) {
		
		EmulatorController emulator = mEmulators.get(name);
		
		if ( emulator == null ) { 
		
			emulator = SdkTools.getDefaultSDK().createEmulatorController("automatic-" + name);
			
			emulator.create();
			
			emulator.start();
			
			emulator.waitForOnline();						
						
			mEmulators.put(name, emulator);

		}
		
		emulator.startUIAutomatorServer();
		
		return emulator;
		
	}
	
	/**
	 * 
	 * Return a {@link UiDevice} instance that can be used to control the 
	 * ui on a given emulator.
	 * 
	 * @param name the name of an emulator that was created with {@link #createEmulator(String)}
	 * @return
	 */
	protected static UiDevice getUiDevice(String name) {
		return mEmulators.get(name).getUiDevice();
	}
	
	@After
	public void tearDown() throws Exception {
			
		// here we dump the screenshot and the UI tree to help debugging failed test cases
		
		File resultsDir = new File("results");
				
		File currentTestCaseDir = new File(resultsDir, this.getClass().getCanonicalName());
		
		File currentTestDir = new File(currentTestCaseDir, name.getMethodName());
		
		for ( String key : mEmulators.keySet()) {
			
			EmulatorController emulator = mEmulators.get(key);
			
			File currentEmulatorDir = new File(currentTestDir, key); 
			
			currentEmulatorDir.mkdirs();
			
			emulator.stopUIAutomatorServer();
			
			try {
				emulator.captureScreenShot(new File(currentEmulatorDir, "screenshot.png").getAbsolutePath());
				emulator.captureUIStructure(new File(currentEmulatorDir, "ui.xml").getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
					
		}

	}	
	
}
