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
import java.util.TreeSet;

import org.ihtsdo.configuration.MetadataConfig;
import org.ihtsdo.util.FileHelper;


/**
 * The Class PatchRF2RetiredISA_PartOfConceptRetired.
 */
public class PatchRF2RetiredISA_PartOfConceptRetired {

	/** The inferred rels. */
	private File inferredRels;
	
	/** The ret cpt isas. */
	private File retCptIsas;
	
	/** The R f1 cause inactive concept. */
	private TreeSet<String> RF1CauseInactiveConcept;
	
	/** The output folder. */
	private File outputFolder;
	
	/** The ret cpt stated isas. */
	private File retCptStatedIsas;


	/**
	 * Instantiates a new patch r f2 retired is a_ part of concept retired.
	 *
	 * @param inferredRels the inferred rels
	 * @param retCptIsas the ret cpt isas
	 * @param retCptStatedIsas the ret cpt stated isas
	 * @param outputFolder the output folder
	 */
	public PatchRF2RetiredISA_PartOfConceptRetired(File inferredRels,File retCptIsas,File retCptStatedIsas, File outputFolder) {
		super();
		this.inferredRels=inferredRels;
		this.retCptIsas=retCptIsas;
		this.retCptStatedIsas=retCptStatedIsas;
		this.outputFolder=outputFolder;
	}


	/**
	 * Gets the metadata values.
	 *
	 * @return the metadata values
	 * @throws Exception the exception
	 */
	private void getMetadataValues()  throws Exception {
		MetadataConfig config =new MetadataConfig();

		RF1CauseInactiveConcept=config.getRF1CauseInactiveConcept();

	}
	
	/**
	 * Execute.
	 */
	public void execute(){

		try {
			long start1 = System.currentTimeMillis();

			getMetadataValues();

			String nextLine;
			String[] splittedLine;
			double lines = 0;
			int contPO=0;
			File tmp=new File ("temp");
			if (!tmp.exists()){
				tmp.mkdirs();
			}
			File RF2File= new File(tmp, inferredRels.getName());

			FileOutputStream fos = new FileOutputStream( RF2File);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);

			FileInputStream rfis = new FileInputStream(inferredRels	);
			InputStreamReader risr = new InputStreamReader(rfis,"UTF-8");
			BufferedReader rbr = new BufferedReader(risr);


			String header=rbr.readLine();
			bw.append(header);
			bw.append("\r\n");

			nextLine=null;
			splittedLine=null;

			while ((nextLine= rbr.readLine()) != null) {
				splittedLine = nextLine.split("\t",-1);

				if (!(splittedLine[7].compareTo("123005000")==0 && splittedLine[8].compareTo("900000000000011006")==0)){
					bw.append(nextLine);
					bw.append("\r\n");
					lines++;
				}else{
					contPO++;
				}
			}
			rbr.close();
			bw.close();
			rfis=null;
			risr=null;

			File newInfRels=new File (outputFolder,inferredRels.getName());
			if (newInfRels.exists()){
				newInfRels.delete();
			}
			RF2File.renameTo(newInfRels);

			System.gc();
			long end1 = System.currentTimeMillis();
			long elapsed1 = (end1 - start1);
			System.out.println(lines + " lines in output file  : " + newInfRels.getAbsolutePath());
			System.out.println("Completed in " + elapsed1 + " ms");
			System.out.println("Part Of removed: " + contPO);

			start1 = System.currentTimeMillis();
			contPO=0;
			lines=0;

			RF2File= new File("temp" , retCptIsas.getName());

			fos = new FileOutputStream( RF2File);
			osw = new OutputStreamWriter(fos,"UTF-8");
			bw = new BufferedWriter(osw);

			rfis = new FileInputStream(retCptIsas	);
			risr = new InputStreamReader(rfis,"UTF-8");
			rbr = new BufferedReader(risr);


			header=rbr.readLine();
			bw.append(header);
			bw.append("\r\n");

			nextLine=null;
			splittedLine=null;

			while ((nextLine= rbr.readLine()) != null) {
				splittedLine = nextLine.split("\t",-1);

				if (!RF1CauseInactiveConcept.contains( splittedLine[4])){
					bw.append(nextLine);
					bw.append("\r\n");
					lines++;
				}else{
					contPO++;
				}
			}
			rbr.close();
			bw.close();
			rfis=null;
			risr=null;

			File newRetIsasRels=new File (outputFolder,retCptIsas.getName());
			if (newRetIsasRels.exists()){
				newRetIsasRels.delete();
			}
			RF2File.renameTo(newRetIsasRels);

			System.gc();
			end1 = System.currentTimeMillis();
			elapsed1 = (end1 - start1);
			System.out.println(lines + " lines in output file  : " + newRetIsasRels.getAbsolutePath());
			System.out.println("Completed in " + elapsed1 + " ms");
			System.out.println("Special concept removed: " + contPO);

			start1 = System.currentTimeMillis();
			contPO=0;
			lines=0;

			RF2File= new File("temp" , retCptStatedIsas.getName());

			fos = new FileOutputStream( RF2File);
			osw = new OutputStreamWriter(fos,"UTF-8");
			bw = new BufferedWriter(osw);

			rfis = new FileInputStream(retCptStatedIsas	);
			risr = new InputStreamReader(rfis,"UTF-8");
			rbr = new BufferedReader(risr);


			header=rbr.readLine();
			bw.append(header);
			bw.append("\r\n");

			nextLine=null;
			splittedLine=null;

			while ((nextLine= rbr.readLine()) != null) {
				splittedLine = nextLine.split("\t",-1);

				if (!RF1CauseInactiveConcept.contains( splittedLine[4])){
					bw.append(nextLine);
					bw.append("\r\n");
					lines++;
				}else{
					contPO++;
				}
			}
			rbr.close();
			bw.close();
			rfis=null;
			risr=null;

			File newRetStatedIsasRels=new File (outputFolder,retCptStatedIsas.getName());
			if (newRetStatedIsasRels.exists()){
				newRetStatedIsasRels.delete();
			}
			RF2File.renameTo(newRetStatedIsasRels);

			FileHelper.removeFolderTree(tmp);
			System.gc();
			end1 = System.currentTimeMillis();
			elapsed1 = (end1 - start1);
			System.out.println(lines + " lines in output file  : " + newRetStatedIsasRels.getAbsolutePath());
			System.out.println("Completed in " + elapsed1 + " ms");
			System.out.println("Special concept removed: " + contPO);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
