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
package org.ihtsdo.task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.ihtsdo.util.I_Constants;


/**
 * The Class ConsolidateRelsSnapshotAndDelta.
 */
public class ConsolidateRelsSnapshotAndDelta  {

	/** The snapshot sorted previousfile. */
	private File snapshotSortedPreviousfile;
	
	/** The snapshot sorted exportedfile. */
	private File snapshotSortedExportedfile;
	
	/** The snapshot final file. */
	private File snapshotFinalFile;
	
	/** The fields to compare. */
	private Integer[] fieldsToCompare;

/** The release date. */
//	private int index;
	private String releaseDate;
	
	/** The new line. */
	private String newLine="\r\n";
	
	/** The col len. */
	private int colLen;
	
	/** The delta final file. */
	private File deltaFinalFile;
	
	/** The bw. */
	private BufferedWriter bw;
	
	/** The dbw. */
	private BufferedWriter dbw;
	
	/** The effective time col index. */
	private int effectiveTimeColIndex;
	
	/** The indexes. */
	private int[] indexes;
	
	/** The type id. */
	private String typeId;

	/**
	 * Instantiates a new consolidate rels snapshot and delta.
	 *
	 * @param typeId the type id
	 * @param snapshotSortedPreviousfile the snapshot sorted previousfile
	 * @param snapshotSortedExportedfile the snapshot sorted exportedfile
	 * @param snapshotFinalFile the snapshot final file
	 * @param deltaFinalFile the delta final file
	 * @param releaseDate the release date
	 */
	public ConsolidateRelsSnapshotAndDelta(String typeId,
			File snapshotSortedPreviousfile, File snapshotSortedExportedfile,
			File snapshotFinalFile, File deltaFinalFile, String releaseDate) {
		this.typeId=typeId;
		this.snapshotSortedPreviousfile=snapshotSortedPreviousfile;	
		this.snapshotSortedExportedfile=snapshotSortedExportedfile;
		this.snapshotFinalFile=snapshotFinalFile;
		this.deltaFinalFile=deltaFinalFile;
		this.fieldsToCompare=new Integer[]{2,3,4,5,6,7,8,9};
		this.indexes=new int[]{0};
		this.effectiveTimeColIndex=1;
		this.releaseDate=releaseDate;
	}

	/**
	 * Execute.
	 *
	 * @throws Exception the exception
	 */
	public void execute() throws Exception {

		try {


			FileOutputStream fos = new FileOutputStream( snapshotFinalFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			bw = new BufferedWriter(osw);

			FileOutputStream dfos = new FileOutputStream( deltaFinalFile);
			OutputStreamWriter dosw = new OutputStreamWriter(dfos,"UTF-8");
			dbw = new BufferedWriter(dosw);

			FileInputStream fis = new FileInputStream(snapshotSortedPreviousfile	);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			BufferedReader br1 = new BufferedReader(isr);

			FileInputStream fis2 = new FileInputStream(snapshotSortedExportedfile	);
			InputStreamReader isr2 = new InputStreamReader(fis2,"UTF-8");
			BufferedReader br2 = new BufferedReader(isr2);


			double lines=0;
			String line1;
			String header=br1.readLine();
			br2.readLine();

			bw.append(header);
			bw.append(newLine);
			dbw.append(header);
			dbw.append(newLine);
			
			String[] columns=header.split("\t",-1);
			colLen=columns.length;
			String[] splittedLine1;
			String line2;
			String[] splittedLine2=null;

			line2=br2.readLine();
			if (line2!=null){
				splittedLine2=line2.split("\t",-1);
			}			

			while ((line1= br1.readLine()) != null) {
				splittedLine1 = line1.split("\t",-1);

				if (line2!=null){

					int comp = idxCompare(splittedLine1,splittedLine2);
//					int comp = splittedLine1[index].compareTo(splittedLine2[index]);
					if ( comp<0){

						addPreviousLine(splittedLine1,"0");
						lines++;
					}else{
						if (comp>0){
							while (comp>0){
								addRF1asRF2Line(splittedLine2);
								lines++;
								line2=br2.readLine();
								if (line2==null){
									comp=-1;
									break;
								}
								splittedLine2=line2.split("\t",-1);
								comp = idxCompare(splittedLine1,splittedLine2);
//								comp = splittedLine1[index].compareTo(splittedLine2[index]);
							}
							if ( comp<0){
								addPreviousLine(splittedLine1,"0");
								lines++;
							}
						}
						while(comp==0){
							addPreviousLine(splittedLine1,"1");
							lines++;
							line2=br2.readLine();
							if (line2==null){
								break;
							}
							splittedLine2=line2.split("\t",-1);
							comp = idxCompare(splittedLine1,splittedLine2);
//							comp = splittedLine1[index].compareTo(splittedLine2[index]);

						}
					}
				}else{
					addPreviousLine(splittedLine1,"0");
					lines++;
				}
			}

			if (line2!=null){

				addRF1asRF2Line(splittedLine2);
				lines++;

				while ((line2= br2.readLine()) != null) {
					splittedLine2=line2.split("\t",-1);
					addRF1asRF2Line(splittedLine2);
					lines++;
				}
			}
			br1.close();
			br2.close();
			bw.close();
			dbw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds the r f1as r f2 line.
	 *
	 * @param splittedLine the splitted line
	 * @throws Exception the exception
	 */
	private void addRF1asRF2Line( String[] splittedLine) throws Exception {

		StringBuffer sb=new StringBuffer();
		sb.append(splittedLine[0]);
		sb.append("\t");
		sb.append(releaseDate);
		sb.append("\t");
		sb.append("1");
		sb.append("\t");
		sb.append(I_Constants.CORE_MODULE_ID);
		sb.append("\t");
		sb.append(splittedLine[1]);
		sb.append("\t");
		sb.append(splittedLine[3]);
		sb.append("\t");
		sb.append("0");
		sb.append("\t");
		sb.append(splittedLine[2]);
		sb.append("\t");
		sb.append(typeId);
		sb.append("\t");
		bw.append("900000000000451002");
		bw.append("\r\n");
		
		String tmp=sb.toString();
		bw.append(tmp);
		dbw.append(tmp);
	}

	/**
	 * Idx compare.
	 *
	 * @param splittedLine1 the splitted line1
	 * @param splittedLine2 the splitted line2
	 * @return the int
	 */
	private int idxCompare(String[] splittedLine1, String[] splittedLine2) {
		int iComp;
		for (int i : indexes){
			iComp=splittedLine1[i].compareTo(splittedLine2[i]);
			if (iComp!=0)
				return iComp;
		}
		return 0;
	}
	
	/**
	 * Adds the previous line.
	 *
	 * @param splittedLine the splitted line
	 * @throws Exception the exception
	 */
	private void addPreviousLine(String[] splittedLine) throws Exception {
		for (int i = 0; i < splittedLine.length; i++) {
			bw.append(splittedLine[i]);
			if (i + 1 < splittedLine.length) {
				bw.append('\t');
			}
		}
		bw.append(newLine);
	}

	/**
	 * Adds the previous line.
	 *
	 * @param splittedLine the splitted line
	 * @param status the status
	 * @throws Exception the exception
	 */
	private void addPreviousLine(String[] splittedLine,String status) throws Exception {

		if (splittedLine[2].compareTo(status)!=0){
			StringBuffer sb=new StringBuffer();

			for (int i = 0; i < splittedLine.length; i++) {
				if (i==effectiveTimeColIndex){
					sb.append(releaseDate);
				}else if (i==2){
					sb.append(status);
				}else{
					sb.append(splittedLine[i]);
				}
				if (i + 1 < splittedLine.length) {
					sb.append('\t');
				}
			}
			sb.append(newLine);

			String tmp=sb.toString();
			bw.append(tmp);
			dbw.append(tmp);

		}else{		
			addPreviousLine(splittedLine);
		}

	}

}

