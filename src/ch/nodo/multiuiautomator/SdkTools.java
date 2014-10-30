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
package ch.nodo.multiuiautomator;


import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;

import com.android.uiautomator.core.UiDevice;
import com.github.uiautomatorstub.AutomatorService;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

/**
 * 
 * This class provides a Java API to the most common adb and avd commands.
 * 
 */ 
/*  
 *  Implementation details: We use the command line interface because it is more 
 *  stable than the internal Java API
 *
 */
public class SdkTools {
	
	private static HashMap<String, EmulatorController> mActiveEmulators = new HashMap<String,EmulatorController>();	
	
	private String mSdkPath;
	private String mAndroidToolPath;
	private String mEmulatorPath;
	private String mAdbPath;
		
	private static SdkTools mDefault;

	/**
	 * Create a new object that uses the SDK at a given location
	 * 
	 * @param sdkPath the path to the sdk (should contain the tools directory)
	 */
	
	public SdkTools(String sdkPath) {
		mSdkPath = sdkPath;
		
		File toolsDir = new File(mSdkPath, "tools");
		
		mAndroidToolPath = new File(toolsDir, "android").toString();
		mEmulatorPath = new File(toolsDir, "emulator").toString();
		
		File platformToolsDir = new File(mSdkPath, "platform-tools");
		
		mAdbPath = new File(platformToolsDir, "adb").toString(); 
		
	}
	
	/**
	 * 
	 * Returns the {@link SdkTools} object that points to the default SDK 
	 * located at the path specified in the Java property ANDROID_SDK. 
	 * 
	 * @return
	 */
	public static SdkTools getDefaultSDK() {
		
		if ( mDefault == null) {
			
			if ( System.getProperty("ANDROID_SDK") == null ) {
				throw new RuntimeException("Please set a variable ANDROID_SDK pointing to the root of the android SDK");
			}
			
			mDefault = new SdkTools(System.getProperty("ANDROID_SDK"));
		}
		
		return mDefault;
	}

	/**
	 * 
	 * Creates an emulator controller that control the AVD with the desired 
	 * name. 
	 * 
	 * @param name name of the AVD
	 * @return
	 */
	public EmulatorController createEmulatorController(String name) {
		
		EmulatorController emulator = mActiveEmulators.get(name);
		
		if (emulator == null) {
			emulator = new EmulatorController(name);
			mActiveEmulators.put(name, emulator);
		}
		
		return emulator;
	}

	
	/**
	 * 
	 * Allows to control a specific emulator instance
	 *
	 */
	public class EmulatorController {
	
		private String mName;
		private Process mProcess;
		private int mPort;
		private Process mAutomatorProcess;
		private int mAutomatorPort;
		
		private EmulatorController(String name) {
			this.mName = name;
		}
		
		/**
		 * Starts the emulator.
		 * 
		 * This method doesn't wait for the emulator to be available.
		 * 
		 */
		public void start() {
			
			if (mProcess != null) return;
			
			mPort = findEmptyPort(5556, 5680);
			
			try {
				
				String [] command = { mEmulatorPath, "-scale", "0.6" , "-no-boot-anim", "-noaudio", "-avd", mName , 
													 "-port", Integer.toString(mPort)};
				mProcess = Runtime.getRuntime().exec(command);
				
			} catch (IOException e) {
				throw new RuntimeException("Error while starting AVD", e);
			} 
			
		}

		
		/**
		 * 
		 * Stops the emulator and wait for the process to be stopped.
		 * 
		 */
		public void stop() {
			
			stopUIAutomatorServer();
			
			try {
				if (mProcess == null) return;
				
				mProcess.destroy();
				
				mProcess.waitFor();
				
				mProcess = null;
			} catch (InterruptedException e) {
				throw new RuntimeException("Error while stopping AVD", e);
			}
		}

		/**
		 * 
		 * Delete the AVD of this emulator
		 * 
		 */
		public void delete() {
			
			stop();
			
			String [] command = { mAndroidToolPath, "delete", "avd", "-n", mName };
			
			Subprocess.checkCall(command);
			
		}
			
		/**
		 * 
		 * Creates an AVD for this emulator
		 * 
		 */
		public void create() {

			if ( isCreated() ) return;
			
			String [] command = { mAndroidToolPath, "create", "avd", "-n", mName, "-t", "android-19", 
													"--abi", "x86", "-d", "5.1in WVGA" };
			
			Subprocess.checkCall(command);
		}

		/**
		 * 
		 * Check if this emulator is up and ready to receive adb commands
		 * 
		 * @return
		 */
		public boolean isOnline() {
		
			String [] command = { mAdbPath , "devices"};
			
			String output = Subprocess.checkOutput(command);
			
			String[] avds = output.split("\n");
			
			for ( String avd : avds) {
				if ( avd.startsWith("emulator-" + mPort + "\t")) {
					return avd.endsWith("\tdevice");
				}
			}
			
			return false;
		
		}
		
		/**
		 * 
		 * Wait until adb on the device is ready to receive commands
		 * 
		 */
		public void waitForOnline() {
			try {
				while (!isOnline()) {
					Thread.sleep(1000);
				}
			} catch (InterruptedException ex ) {
				throw new RuntimeException("Interrupted while waiting for the device from becoming online");
			} 
		}

		/**
		 * 
		 * Check if the device AVD exists
		 * 
		 * @return
		 */
		public boolean isCreated() {
			
			String [] command = { mAndroidToolPath, "list", "avd", "-c"};
			
			String output = Subprocess.checkOutput(command);
			
			String[] avds = output.split("\n");
			
			for ( String avd : avds) {
				if ( avd.equals(mName)) return true;
			}
			
			return false;
			
		}

		private int findEmptyPort(int min, int max) {
			
			for ( int port = min ; port <= max; port += 2) {
				
				try {
					ServerSocket socket = new ServerSocket();
					
					socket.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), port));
					
					socket.close();
					
					return port;
				} catch (IOException e) {}
				
			}
			
			throw new RuntimeException("Unable find free port");
		}
		
		/**
		 * 
		 * Install the specified APK to the device
		 * 
		 * @param apk
		 */
		public void installApplication(String apk) {

			String [] command = { mAdbPath, "-s",  "emulator-" + mPort, "install", apk };

			Subprocess.checkCall(command);

			
		}

		/**
		 * 
		 * Removes the specified application from the device
		 * 
		 * @param apk
		 */
		public void uninstallApplication(String packageName) {

			String [] command = { mAdbPath, "-s",  "emulator-" + mPort, "uninstall", packageName};

			Subprocess.checkCall(command);

			
		}
		
		/**
		 * 
		 * Pushes the specified file to the device
		 * 
		 * @param origin the source path (on the host)
		 * @param destination the destination path (on the device)
		 */
		public void pushFile(String origin, String destination) {
			
			String [] command = { mAdbPath, "-s",  "emulator-" + mPort, "push", origin, destination };
			
			Subprocess.checkCall(command);
			
		}
		
		
		/**
		 * 
		 * Retrieves a file from the device
		 * 
		 * @param origin the source path (on the device) 
		 * @param destination the destination path (on the host)
		 */
		public void pullFile(String origin, String destination) {
			
			String [] command = { mAdbPath, "-s",  "emulator-" + mPort, "pull", origin, destination };
			
			Subprocess.checkCall(command);
			
		}

		/**
		 * 
		 * Executes a command on the device
		 * 
		 * @param shellCommand
		 */
		public void executeCommand(String shellCommand) {
			
			String [] command = { mAdbPath, "-s",  "emulator-" + mPort, "shell", shellCommand};
			
			Subprocess.checkCall(command);
			
		}

		
		/**
		 * Forwards a port from the host to the device
		 * 
		 * @param local the port on the host (uses adb forward syntax)
		 * @param remote the port on the device (uses adb forward syntax)
		 */
		public void forwardPort(String local, String remote) {
			String [] command = { mAdbPath, "-s",  "emulator-" + mPort, "forward", local, remote};
			
			Subprocess.checkCall(command);		
		}
		
		/**
		 * 
		 * Removes a port forwarding
		 * 
		 * @param local the local port 
		 */
		public void unforwardPort(String local) {
			
			String [] command = { mAdbPath, "-s",  "emulator-" + mPort, "forward", "--remove", local};
			
			Subprocess.checkCall(command);	
		}

		
		/**
		 * 
		 * Returns a UI device that can be used to control the UI of the device
		 * 
		 * To use the object you first need to call {@link #startUIAutomatorServer()}
		 * 
		 * @return
		 */
		public UiDevice getUiDevice() {
			
			JsonRpcHttpClient client = null;
			try {
				client = new JsonRpcHttpClient(new URL(
						"http://127.0.0.1:" +  mAutomatorPort + "/jsonrpc/0"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			AutomatorService service = ProxyUtil.createClientProxy(
					AutomatorService.class.getClassLoader(),
					AutomatorService.class, client);
			
			return new UiDevice(this, service);
		}
		
		/**
		 * 
		 * Starts the UI automator server that receives commands from the host.
		 * 
		 */		
		public void startUIAutomatorServer() {
			
			if (mAutomatorProcess != null ) {
				return;
			}
			
			mAutomatorPort = findEmptyPort(9008,9030);
			forwardPort("tcp:" + mAutomatorPort, "tcp:9008");		
			
			pushFile("android/bundle.jar", "/data/local/tmp");
			pushFile("android/uiautomator-stub.jar", "/data/local/tmp");		
			
			executeCommand("mount -t tmpfs none /sdcard");
			
			try {
									
				
				String [] command = { mAdbPath, "-s","emulator-" + mPort, "shell", 
						"uiautomator runtest uiautomator-stub.jar bundle.jar -c com.github.uiautomatorstub.Stub"};
				
				mAutomatorProcess = Runtime.getRuntime().exec(command);			
				
				while (true) {
					try{
						URL u = new URL("http://127.0.0.1:" + mAutomatorPort);
						HttpURLConnection connection = (HttpURLConnection) u.openConnection();					
						connection.connect();
						if ( connection.getResponseCode() != -1) {
							break;
						}
						
						Thread.sleep(1000);
						
					} catch (Exception e) {}
				}
				
			} catch (IOException e) {
				throw new RuntimeException("Error while starting AVD", e);
			} 
						
		}
		
		/**
		 * 
		 * Captures the UI structure on from the device.
		 * 
		 * Make sure you called  {@link #stopUIAutomatorServer()} before calling
		 * this method.
		 * 
		 * @param file a file on the host where to store the dump
		 */
		public void captureUIStructure(String file) {
			executeCommand("uiautomator dump /data/local/tmp/ui.xml");
			pullFile("/data/local/tmp/ui.xml", file);
			executeCommand("rm /data/local/tmp/ui.xml");			
		}
		
		/**
		 * 
		 * Captures a screenshot from the device.
		 * 
		 * Make sure you called  {@link #stopUIAutomatorServer()} before calling
		 * this method.
		 * 
		 * @param file a file on the host where to store the screenshot
		 */
		public void captureScreenShot(String file) {			
			executeCommand("screencap -p /data/local/tmp/screen.png");
			pullFile("/data/local/tmp/screen.png", file);
			executeCommand("rm /data/local/tmp/screen.png");			
		}
		
		/**
		 * 
		 * Stops the UI automator service on the server
		 * 
		 */
		public void stopUIAutomatorServer() {
		
			try {
				
				if (mAutomatorProcess == null) return;
			
				unforwardPort("tcp:" + mAutomatorPort);
				
				mAutomatorProcess.destroy();
				
				mAutomatorProcess.waitFor();
				
				mAutomatorProcess = null;
				
			} catch (InterruptedException e) {
				throw new RuntimeException("Error while stopping automator process", e);
			}
			
		}
		
	}
	
}
