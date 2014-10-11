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
package android.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SparseArray<T> {

	private Map<Integer, T> mMap = new HashMap<Integer, T>();
	private List<Integer> mOrderedKeys = new LinkedList<Integer>();
	
	@Override
	public SparseArray<T> clone() {
		SparseArray<T> cloned = new SparseArray<T>();
		
		cloned.mMap.putAll(mMap);
		cloned.mOrderedKeys.addAll(mOrderedKeys);
		
		return cloned;
	}

	public void put(int key, T value) {
		
		if (!mMap.containsKey(key)) {
			mOrderedKeys.add(key);
		}
		
		mMap.put(key, value);
		
	}

	public T get(int key, T defaultValue) {
		
		if ( mMap.containsKey(key)) {
			return mMap.get(key);
		} else {
			return defaultValue;
		}		
	}
	
	public T get(int key) {
		return mMap.get(key);
	}

	public int indexOfKey(int i) {
		return mOrderedKeys.indexOf(i);
	}

	public T valueAt(int i) {
		return mMap.get(mOrderedKeys.get(i));
	}

	public int size() {
		return mOrderedKeys.size();
	}

	public int keyAt(int i) {
		return mOrderedKeys.get(i);
	}

	
	
}
