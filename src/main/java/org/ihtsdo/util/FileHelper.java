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
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

/**
 * The Class FileHelper.
 */
public class FileHelper {
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger(FileHelper.class);
	
	
	/**
	 * Count lines.
	 *
	 * @param file the file
	 * @param firstLineHeader the first line header
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int countLines(File file, boolean firstLineHeader) throws IOException {

		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		LineNumberReader reader = new LineNumberReader(isr);
		int cnt = 0;
		String lineRead = "";
		while ((lineRead = reader.readLine()) != null) {
		}
		
		cnt = reader.getLineNumber();
		reader.close();
		isr.close();
		fis.close();
		if(firstLineHeader){
			return cnt-1;
		}else{
			return cnt;
		}
	}

	/**
	 * Find all files.
	 *
	 * @param releaseFolder the release folder
	 * @param hashSimpleRefsetList the hash simple refset list
	 * @param mustHave the must have
	 * @param doesntMustHave the doesnt must have
	 */
	public static void findAllFiles(File releaseFolder, HashSet< String> hashSimpleRefsetList, String mustHave, String doesntMustHave) {
		String name="";
		if (hashSimpleRefsetList==null){
			hashSimpleRefsetList=new HashSet<String>();
			
		}
		for (File file:releaseFolder.listFiles()){
			if (file.isDirectory()){
				findAllFiles(file, hashSimpleRefsetList, mustHave, doesntMustHave);
			}else{
				if (file.isHidden()){
					continue;
				}
				name=file.getName().toLowerCase();
				if ( mustHave!=null && !name.contains(mustHave.toLowerCase()) ){
					continue;
				}
				if ( doesntMustHave!=null && name.contains(doesntMustHave.toLowerCase()) ){
					continue;
				}
				if (name.endsWith(".txt")){ 
					hashSimpleRefsetList.add(file.getAbsolutePath());
				}
			}
		}

	}
	
	/**
	 * Gets the file type by header.
	 *
	 * @param inputFile the input file
	 * @return the file type by header
	 */
	public static String getFileTypeByHeader(File inputFile) {
		String namePattern =null;
		try {
			Thread currThread = Thread.currentThread();
			if (currThread.isInterrupted()) {
				return null;
			}
			File patternFile=new File("config/validation-rules.xml");
			if (!patternFile.exists()){
				log.info("Pattern file 'config/validation-rules.xml' doesn't exist. Actual directory is:" +
			              System.getProperty("user.dir"));
			}
//			else{
//				System.out.println("using config/validation-rules.xml");
//			}
			XMLConfiguration xmlConfig = new XMLConfiguration(patternFile);
			List<String> namePatterns = new ArrayList<String>();

			Object prop = xmlConfig.getProperty("files.file.fileType");
			if (prop instanceof Collection) {
				namePatterns.addAll((Collection) prop);
			}
//			System.out.println("");
			boolean toCheck = false;
			String headerRule = null;
			FileInputStream fis = new FileInputStream(inputFile);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String header = br.readLine();
			if (header!=null){
				for (int i = 0; i < namePatterns.size(); i++) {
					if (currThread.isInterrupted()) {
						return null;
					}
					headerRule = xmlConfig.getString("files.file(" + i + ").headerRule.regex");
					namePattern = namePatterns.get(i);
	//				log.info("===================================");
	//				log.info("For file : " + inputFile.getAbsolutePath());
	//				log.info("namePattern:" + namePattern);
	//				log.info("headerRule:" + headerRule);
					if( header.matches(headerRule)){
	
	//					log.info("Match");
						if ((inputFile.getName().toLowerCase().contains("textdefinition") 
								&& namePattern.equals("rf2-descriptions")) 
								|| (inputFile.getName().toLowerCase().contains("description") 
										&& namePattern.equals("rf2-textDefinition"))){
							continue;
						}
						toCheck = true;
						break;
					}
				}
			}
			if (!toCheck) {
				log.info("Header for null pattern:" + header);
				namePattern=null;
				//System.out.println( "Cannot found header matcher for : " + inputFile.getName());
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		} catch (IOException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		} catch (ConfigurationException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		}
		return namePattern;
	}

	/**
	 * Gets the writer.
	 *
	 * @param outFile the out file
	 * @return the writer
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 */
	public static BufferedWriter getWriter(String outFile) throws UnsupportedEncodingException, FileNotFoundException {

		FileOutputStream tfos = new FileOutputStream( outFile);
		OutputStreamWriter tfosw = new OutputStreamWriter(tfos,"UTF-8");
		return new BufferedWriter(tfosw);

	}
	
	/**
	 * Gets the reader.
	 *
	 * @param inFile the in file
	 * @return the reader
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 */
	public static BufferedReader getReader(String inFile) throws UnsupportedEncodingException, FileNotFoundException {

		FileInputStream rfis = new FileInputStream(inFile);
		InputStreamReader risr = new InputStreamReader(rfis,"UTF-8");
		BufferedReader rbr = new BufferedReader(risr);
		return rbr;

	}

	/**
	 * Gets the file.
	 *
	 * @param pathFolder the path folder
	 * @param patternFile the pattern file
	 * @param defaultFolder the default folder
	 * @param mustHave the must have
	 * @param doesntMustHave the doesnt must have
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public static String getFile(File pathFolder,String patternFile,String defaultFolder, String mustHave, String doesntMustHave) throws IOException, Exception{
		if (!pathFolder.exists()){
			pathFolder.mkdirs();
		}

		HashSet<String> files = getFilesFromFolder(pathFolder.getAbsolutePath(), mustHave,  doesntMustHave);
		String previousFile = getFileByHeader(files,patternFile);
		if (previousFile==null && defaultFolder!=null){

			File relFolder=new File(defaultFolder);
			if (!relFolder.exists()){
				relFolder.mkdirs();
			}
			files = getFilesFromFolder(relFolder.getAbsolutePath(), mustHave, doesntMustHave);
			previousFile = getFileByHeader(files,patternFile);
			return previousFile;
		}
		return previousFile;
	}

	/**
	 * Gets the file by header.
	 *
	 * @param files the files
	 * @param patternType the pattern type
	 * @return the file by header
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	private static String getFileByHeader(HashSet<String> files, String patternType) throws IOException, Exception {
		if (files!=null){
			for (String file:files){
				String pattern=getFileTypeByHeader(new File(file));
				if (pattern==null){
					log.info("null pattern for file:" + file);
					continue;
				}
				if (pattern.equals(patternType)){
					return file;
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the files from folder.
	 *
	 * @param folder the folder
	 * @param mustHave the must have
	 * @param doesntMustHave the doesnt must have
	 * @return the files from folder
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	private static HashSet<String> getFilesFromFolder(String folder, String mustHave, String doesntMustHave) throws IOException, Exception {
		HashSet<String> result = new HashSet<String>();
		File dir=new File(folder);
		HashSet<String> files=new HashSet<String>();
		findAllFiles(dir, files, mustHave, doesntMustHave);
		result.addAll(files);
		return result;

	}
	
	/**
	 * Copy to.
	 *
	 * @param inputFile the input file
	 * @param outputFile the output file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copyTo(File inputFile,File outputFile)  throws IOException {

		FileInputStream fis = new FileInputStream(inputFile);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		LineNumberReader reader = new LineNumberReader(isr);
		

		FileOutputStream fos = new FileOutputStream( outputFile);
		OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		
		String lineRead = "";
		while ((lineRead = reader.readLine()) != null) {
			bw.append(lineRead);
			bw.append("\r\n");
		}
		reader.close();
		bw.close();
			
	}
	
	/**
	 * Empty folder.
	 *
	 * @param folder the folder
	 */
	public static void emptyFolder(File folder){
		if(folder.isDirectory()){
			File[] files = folder.listFiles();
			for (File file : files) {
				if(file.isDirectory()){
					emptyFolder(file);
				}else{
					file.delete();
				}
			}
		}
	}

	/**
	 * Gets the folder.
	 *
	 * @param parentFolder the parent folder
	 * @param folderName the folder name
	 * @param empty the empty
	 * @return the folder
	 */
	public static File getFolder(File parentFolder,String folderName,boolean empty) {
		File folder=null;
		if (parentFolder!=null){
			folder = new File(parentFolder,folderName);
		}else{
			folder = new File(folderName);
		}
		if (!folder.exists()){
			folder.mkdirs();
		}else if (empty){
			FileHelper.emptyFolder(folder);
		}
		return folder;
	}
	
	/**
	 * Removes the folder tree.
	 *
	 * @param folder the folder
	 */
	public static void removeFolderTree(File folder) {
		if (folder.isDirectory()){
			for (File file:folder.listFiles()){
				if (file.isDirectory()){
					removeFolderTree(file);
				}else{
					file.delete();
				}
			}
		}
		folder.delete();
	}

	/**
	 * Gets the reader.
	 *
	 * @param inFile the in file
	 * @return the reader
	 * @throws FileNotFoundException the file not found exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static BufferedReader getReader(File inFile) throws FileNotFoundException, UnsupportedEncodingException {

		FileInputStream rfis = new FileInputStream(inFile);
		InputStreamReader risr = new InputStreamReader(rfis,"UTF-8");
		BufferedReader rbr = new BufferedReader(risr);
		return rbr;
	}

	/**
	 * Gets the writer.
	 *
	 * @param outFile the out file
	 * @return the writer
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws FileNotFoundException the file not found exception
	 */
	public static BufferedWriter getWriter(File outFile)throws UnsupportedEncodingException, FileNotFoundException {

		FileOutputStream tfos = new FileOutputStream( outFile);
		OutputStreamWriter tfosw = new OutputStreamWriter(tfos,"UTF-8");
		return new BufferedWriter(tfosw);

	}
}


class FileNameComparator implements Comparator<String>{

	private static final Logger log = Logger.getLogger(FileNameComparator.class);
	private int fieldToCompare;
	private String separator;
	
	public FileNameComparator(int fieldToCompare, String separator){
		this.separator = separator;
		this.fieldToCompare = fieldToCompare;
	}
	
	public int compare(String file1, String file2) {
		String[] file1Split = file1.split(separator); 
		String[] file2Split = file2.split(separator);
		
		String date1 = file1Split[fieldToCompare];
		String date2 = file2Split[fieldToCompare];
		log.debug("First file date: " + date1);
		log.debug("Second file date: " + date2);
		
		return date1.compareTo(date2);
	}
	
}