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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.ihtsdo.configuration.Parameters;


/**
 * The Class XmlMapUtil.
 */
public class XmlMapUtil {


	

	/**
	 * Gets the configuration from file system.
	 *
	 * @param file the file
	 * @return the configuration from file system
	 */
	public static Parameters getConfigFromFileSystem(File file) {

		Parameters config = null;
		try {

			JAXBContext context = JAXBContext.newInstance(Parameters.class);
			Unmarshaller u = context.createUnmarshaller();

			FileInputStream rfis = new FileInputStream(file);
			InputStreamReader is = new InputStreamReader(rfis,"UTF-8");

			config = (Parameters) u.unmarshal(is);

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}
}