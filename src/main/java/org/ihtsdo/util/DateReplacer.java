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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.ihtsdo.configuration.Parameters;

/**
 * The Class DateReplacer.
 */
public class DateReplacer {
	 
 	/** The date to replace. */
 	String dateToReplace;
	 
 	/** The replace with. */
 	String replaceWith;
	 
 	/** The folder. */
 	File folder;

	/**
	 * Instantiates a new date replacer.
	 *
	 * @param dateToReplace the date to replace
	 * @param replaceWith the replace with
	 * @param outputFolder the output folder
	 */
	public DateReplacer(String dateToReplace, String replaceWith, File outputFolder) {
		super();
		this.dateToReplace = dateToReplace;
		this.replaceWith = replaceWith;
		this.folder = outputFolder;
	}

	/**
	 * Instantiates a new date replacer.
	 *
	 * @param config the config
	 * @param outputFolder the output folder
	 */
	public DateReplacer(Parameters config, File outputFolder) {
		super();

		this.dateToReplace = config.getReleaseDate();
		this.replaceWith = config.getFriendlyReleaseDate();
		this.folder = outputFolder;
	}

	/**
	 * Execute.
	 */
	public void execute(){
		processFolderRec(folder);
	}
	
	/**
	 * Process folder rec.
	 *
	 * @param coreFolder the core folder
	 */
	public void processFolderRec(File coreFolder) {
		if (coreFolder.isDirectory()) {
			File[] list = coreFolder.listFiles();
			for (File file : list) {
				processFolderRec(file);
			}
		} else {
			changedFileDate(coreFolder);
		}
	}

	/**
	 * Changed file date.
	 *
	 * @param coreFolder the core folder
	 */
	private void changedFileDate(File coreFolder) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(coreFolder), "UTF-8"));
			File destFile = new File(coreFolder.getParentFile(),coreFolder.getName() + "-copy");
			BufferedWriter bwSubsets = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile), "UTF-8"));
			while (br.ready()) {
				String line = br.readLine();
				line = line.replaceAll( "\t" + dateToReplace + "\t", "\t" + replaceWith + "\t");
				bwSubsets.write(line+"\r\n");
			}
			br.close();
			bwSubsets.close();
			
			String name = coreFolder.getName();
//
			name = name.replaceAll("_" + dateToReplace, "_" + replaceWith);
			if(coreFolder.delete()){
				destFile.renameTo(new File(destFile.getParentFile(), name));
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

}
