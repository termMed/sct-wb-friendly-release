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
package org.ihtsdo.runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.ihtsdo.configuration.Parameters;
import org.ihtsdo.task.ConsolidateRelsSnapshotAndDelta;
import org.ihtsdo.task.FileSorter;
import org.ihtsdo.task.PatchRF2RetiredISA_PartOfConceptRetired;
import org.ihtsdo.util.CommonUtils;
import org.ihtsdo.util.DateReplacer;
import org.ihtsdo.util.FileHelper;
import org.ihtsdo.util.I_Constants;
import org.ihtsdo.util.SnapshotGenerator;
import org.ihtsdo.util.XmlMapUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * The Class ProcessRunner.
 */
public class ProcessRunner {

	/** The logger. */
	private static Logger logger;
	
	/** The config. */
	private static Parameters config;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){

		logger = Logger.getLogger("org.ihtsdo.runner.ProcessRunner");
		long start=new Date().getTime();
		ProcessRunner pr=new ProcessRunner();
		try {
			pr.execute(args[0]);
			long end=new Date().getTime();
			logger.info("Process took " + (end-start)/1000 + " seconds.");
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/** The output folder. */
	private File outputFolder;
	
	/** The sorted folder. */
	private File sortedFolder;
	
	/** The working folder. */
	private File workingFolder;
	
	/** The extra folder. */
	private File extraFolder;
	
	/** The output retired cpt isas file. */
	private File outputRetiredCptIsasFile;
	
	/** The output retired cpt stated isas file. */
	private File outputRetiredCptStatedIsasFile;
	
	/** The special cpts. */
	private ArrayList<String> specialCpts;
	
	/** The historical output file. */
	private File historicalOutputFile;

	/**
	 * Execute.
	 *
	 * @param config the config
	 * @throws Exception the exception
	 */
	public void execute(String config) throws Exception {
		File configFile=new File(config);
		getParams(configFile);
		createFolders();
		assocRelationships();
		retiredConceptInferredIsas();
		retiredConceptStatedIsas();
		partOfRelationshipsPatch();
		copyToOutput();
		replaceDate();
		removeTmps();
	}
	
	private void removeTmps() {
		FileHelper.removeFolderTree(sortedFolder);
		FileHelper.removeFolderTree(workingFolder);
	}

	/**
	 * Copy to output.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void copyToOutput() throws IOException {

		logger.info("Copying files to output folder: " + outputFolder.getAbsolutePath());
		int count=0;
		File dir=new File(config.getRf2ReleaseFullFolder());
		HashSet<String> files=new HashSet<String>();
		FileHelper.findAllFiles(dir, files,null,null);	
		for(String sfile :files){
			File file=new File(sfile);
			if (!file.getName().toLowerCase().contains("_relationship_")){
				count++;
				FileHelper.copyTo(file, new File(outputFolder,file.getName()));
			}
		}
		logger.info(count + " files added to output folder.");
	}
	
	
	
	/**
	 * Gets the params.
	 *
	 * @param file the file
	 * @return the params
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void getParams(File file) throws ClassNotFoundException {

		logger.info("Paramaters in " + file.getAbsolutePath());
		config=XmlMapUtil.getConfigFromFileSystem(file);
		GsonBuilder gsonb=new GsonBuilder();
		gsonb.setPrettyPrinting();
		Gson gson=gsonb.create();
		logger.info(gson.toJson(config,Class.forName("org.ihtsdo.configuration.Parameters")));
		
		specialCpts=new ArrayList<String>();
		specialCpts.add(I_Constants.DUPLICATE_CONCEPT);
		specialCpts.add(I_Constants.AMBIGUOUS_CONCEPT);
		specialCpts.add(I_Constants.OUTDATED_CONCEPT);
		specialCpts.add( I_Constants.ERRONEOUS_CONCEPT);
		specialCpts.add(I_Constants.LIMITED_CONCEPT);
		specialCpts.add(I_Constants.MOVED_ELSEWHERE_CONCEPT);
		specialCpts.add(I_Constants.REASON_NOT_STATED_CONCEPT);
	}
	
	/**
	 * Replace date.
	 */
	private void replaceDate() {
		
		logger.info("Starting replacement of release date: " + config.getReleaseDate() +  " by friendly release date: " + config.getFriendlyReleaseDate());
		DateReplacer dr=new DateReplacer(config, outputFolder);
		dr.execute();
		dr=null;
		logger.info("End of replacement of release date by friendly release date");
		
	}
	
	/**
	 * Creates the folders.
	 */
	private void createFolders() {
		outputFolder=new File("FriendlyRelease_"  + config.getFriendlyReleaseDate());
		logger.info("Creating output folder:" + outputFolder.getAbsolutePath());
		if (outputFolder.exists()){
			FileHelper.emptyFolder(outputFolder);
		}else{
			outputFolder.mkdirs();
		}
		sortedFolder=new File("Sorted_"  + config.getFriendlyReleaseDate());
		if (sortedFolder.exists()){
			FileHelper.emptyFolder(sortedFolder);
		}else{
			sortedFolder.mkdirs();
		}
		logger.info(outputFolder.getAbsolutePath());
		workingFolder=new File("Working_"  + config.getFriendlyReleaseDate());
		if (workingFolder.exists()){
			FileHelper.emptyFolder(workingFolder);
		}else{
			workingFolder.mkdirs();
		}
		extraFolder=new File("extra_"  + config.getFriendlyReleaseDate());
		if (extraFolder.exists()){
			FileHelper.emptyFolder(extraFolder);
		}else{
			extraFolder.mkdirs();
		}
	}
	
	/**
	 * Retired concept inferred isas.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	private void retiredConceptInferredIsas() throws IOException, Exception{
		logger.info("Starting generation of isas relationship of retired concept on inferred view.");
		String file=FileHelper.getFile(new File(config.getPreviousFriendlyReleaseFolder()), "rf2-relationships", null, "retiredisa", "stated");
		
		File RF2SortedFile= new File(sortedFolder,new File( file).getName());
		FileSorter sf=new FileSorter(new File(file),RF2SortedFile,workingFolder,new int[]{0,1});
		sf.execute();
		sf=null;
		File snapshotSortedPreviousfile=new File(sortedFolder,"snap_" + RF2SortedFile.getName());
		SnapshotGenerator sg=new SnapshotGenerator(RF2SortedFile, config.getReleaseDate(), 0, 1, snapshotSortedPreviousfile, null, null);
		sg.execute();
		sg=null;
		System.gc();
		
		file=FileHelper.getFile(new File(config.getRf1ReleaseFolder()), "rf1-relationships", null, null, "stated");
		File filteredFile=getRF1RetConceptIsas(new File(file));
		
		File RF1SortedFile= new File(sortedFolder,new File( file).getName());
		
		sf=new FileSorter(filteredFile,RF1SortedFile,workingFolder,new int[]{0});
		sf.execute();
		sf=null;
		outputRetiredCptIsasFile=new File(outputFolder,"res2_RetiredIsaRelationship_Full_INT_" + config.getReleaseDate() + ".txt" );
		
		File deltaFinalFile=new File(extraFolder,"delta_" + outputRetiredCptIsasFile.getName());
		File snapshotFinalFile=new File(extraFolder,"snap_" + outputRetiredCptIsasFile.getName());
		
		ConsolidateRelsSnapshotAndDelta  cis=new ConsolidateRelsSnapshotAndDelta(I_Constants.INFERRED, snapshotSortedPreviousfile,RF1SortedFile,snapshotFinalFile,deltaFinalFile,config.getReleaseDate());
		cis.execute();
		cis=null;
		System.gc();

		HashSet<File> hFile=new HashSet<File>();
		hFile.add(RF2SortedFile);
		hFile.add(deltaFinalFile);

		CommonUtils.MergeFile(hFile,  outputRetiredCptIsasFile);
		System.gc();
		logger.info("End of generation of isas relationship of retired concept on inferred view.");
	}
	
	/**
	 * Retired concept stated isas.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	private void retiredConceptStatedIsas() throws IOException, Exception{
		logger.info("Starting generation of isas relationship of retired concept on stated view.");
		String file=FileHelper.getFile(new File(config.getPreviousFriendlyReleaseFolder()), "rf2-relationships", null, "retiredstatedisa", null );
		
		File RF2SortedFile= new File(sortedFolder,new File( file).getName());
		FileSorter sf=new FileSorter(new File(file),RF2SortedFile,workingFolder,new int[]{0,1});
		sf.execute();
		sf=null;
		
		File snapshotSortedPreviousfile=new File(sortedFolder,"snap_" + RF2SortedFile.getName());
		SnapshotGenerator sg=new SnapshotGenerator(RF2SortedFile, config.getReleaseDate(), 0, 1, snapshotSortedPreviousfile, null, null);
		sg.execute();
		sg=null;
		System.gc();
		
		file=FileHelper.getFile(new File(config.getRf1ReleaseFolder()), "rf1-relationships", null, "stated", null);
		File filteredFile=getRF1RetConceptIsas(new File(file));
		
		File RF1SortedFile= new File(sortedFolder,new File( file).getName());
		
		sf=new FileSorter(filteredFile,RF1SortedFile,workingFolder,new int[]{0});
		sf.execute();
		sf=null;
		
		outputRetiredCptStatedIsasFile=new File(outputFolder,"res2_RetiredStatedIsaRelationship_Full_INT_" + config.getReleaseDate() + ".txt" );
		
		File deltaFinalFile=new File(extraFolder,"delta_" + outputRetiredCptStatedIsasFile.getName());
		File snapshotFinalFile=new File(extraFolder,"snap_" + outputRetiredCptStatedIsasFile.getName());
		
		ConsolidateRelsSnapshotAndDelta  cis=new ConsolidateRelsSnapshotAndDelta(I_Constants.STATED, snapshotSortedPreviousfile,RF1SortedFile,snapshotFinalFile,deltaFinalFile,config.getReleaseDate());
		cis.execute();
		cis=null;
		System.gc();

		HashSet<File> hFile=new HashSet<File>();
		hFile.add(RF2SortedFile);
		hFile.add(deltaFinalFile);

		CommonUtils.MergeFile(hFile,  outputRetiredCptStatedIsasFile);
		System.gc();
		logger.info("End of generation of isas relationship of retired concept on stated view.");
	}
	
	/**
	 * Part of relationships patch.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public void partOfRelationshipsPatch() throws IOException, Exception{
		logger.info("Starting patch to removing of isas of special concepts -as retired- and part of relationships -as inferred type- ");
		String infRelsfile=FileHelper.getFile(new File(config.getRf2ReleaseFullFolder()), "rf2-relationships", null, null, "stated");
		
		PatchRF2RetiredISA_PartOfConceptRetired rels=new PatchRF2RetiredISA_PartOfConceptRetired(new File(infRelsfile),outputRetiredCptIsasFile,outputRetiredCptStatedIsasFile,outputFolder);
		rels.execute();
		rels=null;
		System.gc();
		logger.info("End of patch to removing of isas of special concepts -as retired- and part of relationships -as inferred type- ");
	}
	
	/**
	 * Assoc relationships.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	private void assocRelationships() throws IOException, Exception{
		logger.info("Starting generation of historical associations file");
		String file=FileHelper.getFile(new File(config.getPreviousFriendlyReleaseFolder()), "rf2-relationships", null, "historical", null);
		
		File RF2SortedFile= new File(sortedFolder,new File( file).getName());
		FileSorter sf=new FileSorter(new File(file),RF2SortedFile,workingFolder,new int[]{0,1});
		sf.execute();
		sf=null;
		File snapshotSortedPreviousfile=new File(sortedFolder,"snap_" + RF2SortedFile.getName());
		SnapshotGenerator sg=new SnapshotGenerator(RF2SortedFile, config.getReleaseDate(), 0, 1, snapshotSortedPreviousfile, null, null);
		sg.execute();
		sg=null;
		System.gc();
		
		file=FileHelper.getFile(new File(config.getRf1ReleaseFolder()), "rf1-relationships", null, null, "stated");
		
		File filteredFile=getRF1Associations(new File(file));
		
		File RF1SortedFile= new File(sortedFolder,new File( file).getName());
		
		sf=new FileSorter(filteredFile,RF1SortedFile,workingFolder,new int[]{0});
		sf.execute();
		sf=null;
		
		historicalOutputFile=new File(outputFolder,"sct2_RelationshipHistorical_Full_INT_" + config.getReleaseDate() + ".txt" );
		
		File deltaFinalFile=new File(extraFolder,"delta_" + historicalOutputFile.getName());
		File snapshotFinalFile=new File(extraFolder,"snap_" + historicalOutputFile.getName());
		
		ConsolidateRelsSnapshotAndDelta  cis=new ConsolidateRelsSnapshotAndDelta("-1", snapshotSortedPreviousfile,RF1SortedFile,snapshotFinalFile,deltaFinalFile,config.getReleaseDate());
		cis.execute();
		cis=null;
		System.gc();

		HashSet<File> hFile=new HashSet<File>();
		hFile.add(RF2SortedFile);
		hFile.add(deltaFinalFile);

		CommonUtils.MergeFile(hFile,  historicalOutputFile);
		System.gc();
		logger.info("End of generation of historical associations file");
	}
	
	/**
	 * Gets the r f1 associations.
	 *
	 * @param file the file
	 * @return the r f1 associations
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private File getRF1Associations(File file) throws IOException {
		BufferedReader br=FileHelper.getReader(file);
		File outFile=new File(sortedFolder,"FiltAssoc_" + file.getName());
		BufferedWriter bw = FileHelper.getWriter(outFile);
		bw.append(br.readLine());
		bw.append("\r\n");
		String line;
		String[] spl;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if ( spl[4].equals(I_Constants.ADDITIONAL_RF1_CHARTYPE) ){
				bw.append(line);
				bw.append("\r\n");
			}
		}
		br.close();
		bw.close();
		return outFile;
	}

	/**
	 * Gets the r f1 ret concept isas.
	 *
	 * @param file the file
	 * @return the r f1 ret concept isas
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private File getRF1RetConceptIsas(File file) throws IOException {
		BufferedReader br=FileHelper.getReader(file);
		File outFile=new File(sortedFolder,"FiltIsa_" + file.getName());
		BufferedWriter bw = FileHelper.getWriter(outFile);
		bw.append(br.readLine());
		bw.append("\r\n");
		String line;
		String[] spl;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if ( spl[2].equals(I_Constants.ISA) && specialCpts.contains(spl[3])){
				bw.append(line);
				bw.append("\r\n");
			}
			
		}
		br.close();
		bw.close();
		return outFile;
	}

}
