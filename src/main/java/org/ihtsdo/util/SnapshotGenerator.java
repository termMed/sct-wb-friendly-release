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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * The Class SnapshotGenerator.
 */
public class SnapshotGenerator {

	/** The sorted file. */
	private File sortedFile;
	
	/** The column filter ixs. */
	private Integer[] columnFilterIxs;
	
	/** The column filter values. */
	private String[] columnFilterValues;
	
	/** The date. */
	private String date;
	
	/** The component column. */
	private int componentColumn;
	
	/** The effective time column. */
	private int effectiveTimeColumn;
	
	/** The output file. */
	private File outputFile;

	/**
	 * Instantiates a new snapshot generator.
	 *
	 * @param sortedFile the sorted file
	 * @param date the date
	 * @param componentColumn the component column
	 * @param effectiveTimeColumn the effective time column
	 * @param outputFile the output file
	 * @param columnFilterIxs the column filter ixs
	 * @param columnFilterValues the column filter values
	 */
	public SnapshotGenerator(File sortedFile, String date,
			int componentColumn, int effectiveTimeColumn, File outputFile,
			Integer[] columnFilterIxs,String[] columnFilterValues) {
		super();
		this.sortedFile = sortedFile;
		this.date = date;
		this.componentColumn = componentColumn;
		this.effectiveTimeColumn = effectiveTimeColumn;
		this.outputFile = outputFile;
		this.columnFilterIxs=columnFilterIxs;
		this.columnFilterValues=columnFilterValues;
	}

	/**
	 * Execute.
	 */
	public void execute(){
		
		try {
			long start1 = System.currentTimeMillis();

			FileInputStream fis = new FileInputStream(sortedFile);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			BufferedReader br = new BufferedReader(isr);

			double lines = 0;
			String nextLine;
			String header = br.readLine();

			if (outputFile.exists()){
				outputFile.delete();
			}
			FileOutputStream fos = new FileOutputStream( outputFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.append(header);
			bw.append("\r\n");
			
			String prevCompo="";
			String prevLine="";
			String[] splittedLine;
			String[] prevSplittedLine;
			boolean bContinue=true;

			while ((prevLine= br.readLine()) != null) {
				prevSplittedLine =prevLine.split("\t",-1);
				
				if (columnFilterIxs!=null){
					bContinue = true;
					for (int i=0;i<columnFilterIxs.length;i++){
						if (prevSplittedLine[columnFilterIxs[i]].compareTo(columnFilterValues[i])!=0){
							bContinue=false;
							break;
						}
					}
				}
				if (bContinue){
					if (prevSplittedLine[effectiveTimeColumn].compareTo(date)<=0){
						prevCompo=prevSplittedLine[componentColumn];
						break;
					}
				}
			}
			if ( !prevCompo.equals("") ){
				while ((nextLine= br.readLine()) != null) {
					splittedLine = nextLine.split("\t",-1);
					
					if (columnFilterIxs!=null){
						bContinue = true;
						for (int i=0;i<columnFilterIxs.length;i++){
							if (splittedLine[columnFilterIxs[i]].compareTo(columnFilterValues[i])!=0){
								bContinue=false;
								break;
							}
						}
					}
					if (bContinue){
						if(splittedLine[componentColumn].equals(prevCompo)){
							if (splittedLine[effectiveTimeColumn].compareTo(date)<=0){
								prevLine=nextLine;
								
							}
						}else{
							if (splittedLine[effectiveTimeColumn].compareTo(date)<=0){
								bw.append(prevLine);
								bw.append("\r\n");
								prevLine=nextLine;
								prevCompo=splittedLine[componentColumn];
								lines++;
							}
						}
					}
				}
				bw.append(prevLine);
				bw.append("\r\n");
				lines++;
				
			}
			
			bw.close();
			br.close();
			long end1 = System.currentTimeMillis();
			long elapsed1 = (end1 - start1);
			System.out.println("Lines in output file  : " + lines);
			System.out.println("Completed in " + elapsed1 + " ms");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
