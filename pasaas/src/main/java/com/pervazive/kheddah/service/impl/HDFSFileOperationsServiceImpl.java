package com.pervazive.kheddah.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pervazive.kheddah.service.HDFSFileOperationsService;

@Service
@Transactional
public class HDFSFileOperationsServiceImpl implements HDFSFileOperationsService{

	public HDFSFileOperationsServiceImpl() {
		
	}
	
	String hdfsURL = "hdfs://spark:8020"; //value to be fetched from general config 
	String basedirectiory = "/user/pervazive/"; //value to be fetched from general config 
	
	public Configuration init(String hdfsURL, String basedirectiory, String user ){
		Configuration conf = new Configuration();
		if(hdfsURL == null) hdfsURL = this.hdfsURL;
		if(basedirectiory == null) basedirectiory = this.basedirectiory;
		conf.set("fs.defaultFS", hdfsURL+basedirectiory);
		//conf.set("hadoop.job.ugi", "hadoop");
		return conf;
	}
	/**
	   * create a existing file from local filesystem to hdfs
	   * @param source
	   * @param dest
	   * @param conf
	   * @throws IOException
	   */
	  public void addFile(MultipartFile source, String dest, Configuration conf) throws IOException {

	    FileSystem fileSystem = FileSystem.get(conf);
	    // Get the filename out of the file path
	    //String filename = source.substring(source.lastIndexOf('/') + 1,source.length());

	    // Create the destination path including the filename.
	    /*if (dest.charAt(dest.length() - 1) != '/') {
	      dest = dest + "/" + filename;
	    } else {
	      dest = dest + filename;
	    }*/
	    // Check if the file already exists
	    
	    	
	    	Path path = new Path(source.getOriginalFilename());
		    if (fileSystem.exists(path)) {
		      System.out.println("File " + dest + " already exists");
		      return;
		    }

	    // Create a new file and write data to it.
	    FSDataOutputStream out = fileSystem.create(path);
	    InputStream in = new BufferedInputStream(new ByteArrayInputStream(source.getBytes()));

	    byte[] b = new byte[1024];
	    int numBytes = 0;
	    while ((numBytes = in.read(b)) > 0) {
	      out.write(b, 0, numBytes);
	    }

	    // Close all the file descriptors
	    in.close();
	    out.close();
	    fileSystem.close();
	  }
	  
	  /**
	   * create a existing file from local filesystem to hdfs
	   * @param source
	   * @param dest
	   * @param conf
	   * @throws IOException
	   */
	  public void addMultipleFiles(MultipartFile[] source, String dest, Configuration conf) throws IOException {

	    FileSystem fileSystem = FileSystem.get(conf);
	    // Get the filename out of the file path
	    //String filename = source.substring(source.lastIndexOf('/') + 1,source.length());

	    // Create the destination path including the filename.
	    /*if (dest.charAt(dest.length() - 1) != '/') {
	      dest = dest + "/" + filename;
	    } else {
	      dest = dest + filename;
	    }*/
	    // Check if the file already exists
	    int i =0;
	    for(MultipartFile file : source){
	    	
	    	Path path = new Path(dest+i+".txt");
		    if (fileSystem.exists(path)) {
		      System.out.println("File " + dest + " already exists");
		      return;
		    }

	    // Create a new file and write data to it.
	    FSDataOutputStream out = fileSystem.create(path);
	    InputStream in = new BufferedInputStream(new ByteArrayInputStream(file.getBytes()));

	    byte[] b = new byte[1024];
	    int numBytes = 0;
	    while ((numBytes = in.read(b)) > 0) {
	      out.write(b, 0, numBytes);
	    }

	    // Close all the file descriptors
	    in.close();
	    out.close();
	    i++;
	    }
	    fileSystem.close();
	  }

	  /**
	   * read a file from hdfs
	   * @param file
	   * @param conf
	   * @throws IOException
	   */
	  public void readFile(String file, Configuration conf) throws IOException {
	    FileSystem fileSystem = FileSystem.get(conf);

	    Path path = new Path(file);
	    if (!fileSystem.exists(path)) {
	      System.out.println("File " + file + " does not exists");
	      return;
	    }

	    FSDataInputStream in = fileSystem.open(path);

	    String filename = file.substring(file.lastIndexOf('/') + 1,
	        file.length());

	    OutputStream out = new BufferedOutputStream(new FileOutputStream(
	        new File(filename)));

	    byte[] b = new byte[1024];
	    int numBytes = 0;
	    while ((numBytes = in.read(b)) > 0) {
	      out.write(b, 0, numBytes);
	    }

	    in.close();
	    out.close();
	    fileSystem.close();
	  }
	  
	  public List<String> readFileO(String file, Configuration conf) throws IOException {
		    FileSystem fileSystem = FileSystem.get(conf);

		    Path path = new Path(file);
		    if (!fileSystem.exists(path)) {
		      System.out.println("File " + file + " does not exists");
		      return null;
		    }

		    FSDataInputStream in = fileSystem.open(path);
		    BufferedReader br=new BufferedReader(new InputStreamReader(in));
		    
		    List<String> tsvString = new ArrayList<String>();
		    String line=br.readLine();
		    tsvString.add(line);
		    int readLines = 0;
	        while (line != null && readLines < 1000){
	                //System.out.println(line);
	                line=br.readLine();
	                tsvString.add(line);
	                readLines++;
	        }
	        br.close();
		    in.close();
		    fileSystem.close();
		    
		    return tsvString;
		  }
	  
	  
	  public File passFileForUpload(String file, Configuration conf) throws IOException {
		    FileSystem fileSystem = FileSystem.get(conf);

		    Path path = new Path(file);
		    if (!fileSystem.exists(path)) {
		      System.out.println("File " + file + " does not exists");
		      return null;
		    }

		    FSDataInputStream in = fileSystem.open(path);
		    
		    File loadFile = new File("loadtsv");
		    
			    OutputStream out = new BufferedOutputStream(new FileOutputStream(loadFile));

			    byte[] b = new byte[1024];
			    int numBytes = 0;
			    while ((numBytes = in.read(b)) > 0) {
			      out.write(b, 0, numBytes);
			    }

			    in.close();
			    out.close();
			    fileSystem.close();
			    
			    
		    return loadFile;
		  }
	  
	  /**
	   * read a file from hdfs
	   * @param file
	   * @param conf
	   * @throws IOException
	   */
	  
	  @Transactional(readOnly = true)
	  public FileStatus[] readFileList(String dir, Configuration conf) throws IOException {
	    FileSystem fileSystem = FileSystem.get(conf);

	    Path path = new Path(dir);
	    if (!fileSystem.exists(path)) {
	      System.out.println("Directory " + dir + " does not exists");
	      return null; //TODO send out actual dir information
	    }
	    FileStatus[] fileListStatus =  fileSystem.listStatus(path);
	    fileSystem.close();
	    return fileListStatus;
	  }

	  /**
	   * delete a directory in hdfs
	   * @param file
	   * @throws IOException
	   */
	  public void deleteFile(String file, Configuration conf) throws IOException {
	    FileSystem fileSystem = FileSystem.get(conf);
	    Path path = new Path(file);
	    System.out.println("attempting delete of :"+file);
	    if (!fileSystem.exists(path)) {
	      System.out.println("File " + file + " does not exists");
	      return;
	    }

	    fileSystem.delete(new Path(file), true);
	    fileSystem.close();
	  }

	  /**
	   * create directory in hdfs
	   * @param dir
	   * @throws IOException
	   */
	  public void mkdir(String dir, Configuration conf) throws IOException {

		 //For organization - hdfsURL/basedirectiory/organizationname/projectname
		FileSystem fileSystem = FileSystem.get(conf); //create directory when organization is created and create project under it when project is created 
	    Path path = new Path(dir);
	    if (fileSystem.exists(path)) {
	      System.out.println("Dir " + dir + " already not exists");
	      return;
	    }
	    fileSystem.mkdirs(path);
	    fileSystem.close();
	  }
	  
	  public void copyHdfsFile(String hdfsSource, String hdfsDest, Configuration conf) throws IOException {
		    FileSystem fileSystem = FileSystem.get(conf);
		    FileStatus[] fileListStatus =  fileSystem.listStatus(new Path(hdfsSource));
		    if(fileListStatus.length > 0) {
		    	for(FileStatus filestat : fileListStatus){
		    		FileUtil.copy(fileSystem, filestat.getPath(), fileSystem, new Path(hdfsDest), true, conf);
		    	}
		    }
		    /*Path sourcePath = new Path(hdfsDest);
		    FSDataInputStream in = fileSystem.open(sourcePath);
		    Path destPath = new Path(hdfsDest);
			// Create a new file and write data to it.
		    FSDataOutputStream out = fileSystem.create(destPath);
		    InputStream inval = new BufferedInputStream(in);

		    byte[] b = new byte[1024];
		    int numBytes = 0;
		    while ((numBytes = inval.read(b)) > 0) {
		      out.write(b, 0, numBytes);
		    }

		    // Close all the file descriptors
		    inval.close();
		    out.close();*/
		    fileSystem.close();
		  }
	  
/*	  public static void main(String[] args) {
		  String hdfsURL = "hdfs://spark:8020"; //value to be fetched from general config 
			String basedirectiory = "/user/pervazive/"; //value to be fetched from general config 
		  Configuration conf = new Configuration();
			conf.set("fs.defaultFS", hdfsURL+basedirectiory);
		  HDFSFileOperationsServiceImpl hdfsFileOperationsServiceImpl = new HDFSFileOperationsServiceImpl();
		  try {
			FileStatus fileStatus = hdfsFileOperationsServiceImpl.readFileList("newfromapi/here.txt", conf);
			System.out.println("RUNNING This "+fileStatus.getOwner());
			System.out.println("RUNNING This "+fileStatus.getPermission());
			System.out.println("RUNNING This "+fileStatus.getPath().getName());
			System.out.println("RUNNING This "+fileStatus.getLen());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}
