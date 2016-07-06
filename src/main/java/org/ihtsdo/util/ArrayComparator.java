/**
 * Copyright (c) 2016 International Health Terminology Standards Development
 * Organisation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ihtsdo.util;

import java.util.Comparator;

/**
 * The Class ArrayComparator.
 */
public class ArrayComparator implements Comparator<String[]> {

	/** The indexes. */
	private int[] indexes;
	
	/** The reverse. */
	private boolean reverse;

	/**
	 * Instantiates a new array comparator.
	 *
	 * @param indexes the indexes
	 * @param reverse the reverse
	 */
	public ArrayComparator(int[] indexes, boolean reverse) {
		this.indexes = indexes;
		this.reverse = reverse;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(String[] o1, String[] o2) {
		for (int i = 0; i < indexes.length; i++) {
			if (o1[indexes[i]].compareTo(o2[indexes[i]]) < 0) {
				return reverse ? 1 : -1;
			} else if (o1[indexes[i]].compareTo(o2[indexes[i]]) > 0) {
				return reverse ? -1 : 1;
			}
		}
		return 0;
	}
}
