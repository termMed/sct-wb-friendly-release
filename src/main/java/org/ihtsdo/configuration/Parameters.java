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
package org.ihtsdo.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class Parameters.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameters {

	/** The rf2 release full folder. */
	private String rf2ReleaseFullFolder;
	
	/** The rf1 release folder. */
	private String rf1ReleaseFolder;
	
	/** The release date. */
	private String releaseDate;
	
	/** The friendly release date. */
	private String friendlyReleaseDate;
	
	/** The previous friendly release folder. */
	private String previousFriendlyReleaseFolder;
	
	
	/**
	 * Gets the rf2 release full folder.
	 *
	 * @return the rf2 release full folder
	 */
	public String getRf2ReleaseFullFolder() {
		return rf2ReleaseFullFolder;
	}
	
	/**
	 * Sets the rf2 release full folder.
	 *
	 * @param rf2ReleaseFullFolder the new rf2 release full folder
	 */
	public void setRf2ReleaseFullFolder(String rf2ReleaseFullFolder) {
		this.rf2ReleaseFullFolder = rf2ReleaseFullFolder;
	}
	
	/**
	 * Gets the release date.
	 *
	 * @return the release date
	 */
	public String getReleaseDate() {
		return releaseDate;
	}
	
	/**
	 * Sets the release date.
	 *
	 * @param releaseDate the new release date
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	/**
	 * Gets the friendly release date.
	 *
	 * @return the friendly release date
	 */
	public String getFriendlyReleaseDate() {
		return friendlyReleaseDate;
	}
	
	/**
	 * Sets the friendly release date.
	 *
	 * @param friendlyReleaseDate the new friendly release date
	 */
	public void setFriendlyReleaseDate(String friendlyReleaseDate) {
		this.friendlyReleaseDate = friendlyReleaseDate;
	}
	
	/**
	 * Gets the rf1 release folder.
	 *
	 * @return the rf1 release folder
	 */
	public String getRf1ReleaseFolder() {
		return rf1ReleaseFolder;
	}
	
	/**
	 * Sets the rf1 release folder.
	 *
	 * @param rf1ReleaseFolder the new rf1 release folder
	 */
	public void setRf1ReleaseFolder(String rf1ReleaseFolder) {
		this.rf1ReleaseFolder = rf1ReleaseFolder;
	}
	
	/**
	 * Gets the previous friendly release folder.
	 *
	 * @return the previous friendly release folder
	 */
	public String getPreviousFriendlyReleaseFolder() {
		return previousFriendlyReleaseFolder;
	}
	
	/**
	 * Sets the previous friendly release folder.
	 *
	 * @param previousFriendlyReleaseFolder the new previous friendly release folder
	 */
	public void setPreviousFriendlyReleaseFolder(String previousFriendlyReleaseFolder) {
		this.previousFriendlyReleaseFolder = previousFriendlyReleaseFolder;
	}
}
