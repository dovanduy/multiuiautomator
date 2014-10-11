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


public class NotImplementedException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public NotImplementedException() {
    }

    public NotImplementedException(String message) {
    	super(message);
    }
}
