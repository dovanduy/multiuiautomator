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


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

class Subprocess {

	
	public static void readProcessOutput(Process p) {
		new ReaderThread(p.getInputStream()).start();
		new ReaderThread(p.getErrorStream()).start();
	}


	public static void checkCall(String[] command) {
	
		try {
			
			for ( String part : command) System.out.print(part + " ");
			
			System.out.println();
			
			Process p = Runtime.getRuntime().exec(command);
			
			new ReaderThread(p.getErrorStream()).start();
			new ReaderThread(p.getInputStream()).start();
			
			p.waitFor();
			
			if ( p.exitValue() != 0) {
				throw new RuntimeException("Error while executing command");
			}
			
		} catch (IOException e) {
			throw new RuntimeException("Error executing command", e);
		} catch (InterruptedException e) {
			throw new RuntimeException("Error executing command", e);
		}
	}

	public static String checkOutput(String[] command) {

		try {

			Process p = Runtime.getRuntime().exec(command);
			
			new ReaderThread(p.getErrorStream()).start();
			
			StringBuilder b = new StringBuilder();
			
			Reader r = new InputStreamReader(p.getInputStream());
			
			int len;
			char [] data = new char[1000];
			
			while ( (len = r.read(data) ) != -1) {
				b.append(data, 0, len);
			}
			
			p.waitFor();
			
			if ( p.exitValue() != 0) {
				throw new RuntimeException("Command returned error");
			}

			return b.toString();
		
		} catch (IOException e) {
			throw new RuntimeException("Error while running command", e);
		} catch (InterruptedException e) {
			throw new RuntimeException("Error while running commmand", e);
		}

	}

	
	static class ReaderThread extends Thread {
		
		private InputStream mIn;

		public ReaderThread(InputStream in) {
			this.mIn = in;
		}
		
		public void run() {
			int ch;
			
			try {
				while  ( (ch = mIn.read()) != -1) {
					System.out.write(ch);
				}
			} catch (IOException e) {
				e.printStackTrace();
				// do nothing
			} finally {
				try { mIn.close(); } catch (IOException e) {}
			}
			
		}
	}
	
}
