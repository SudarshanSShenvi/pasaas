package com.pervazive.kheddah.service;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.springframework.web.multipart.MultipartFile;

public interface HDFSFileOperationsService {

	public Configuration init(String hdfsURL, String basedirectiory, String user );
	
	/**
	   * create a existing file from local filesystem to hdfs
	   * @param source
	   * @param dest
	   * @param conf
	   * @throws IOException
	   */
	public void addMultipleFiles(MultipartFile[] source, String dest, Configuration conf) throws IOException;
	
	public void addFile(MultipartFile source, String dest, Configuration conf) throws IOException;

	public FileStatus[] readFileList(String dir, Configuration conf) throws IOException;
	  /**
	   * read a file from hdfs
	   * @param file
	   * @param conf
	   * @throws IOException
	   */
	  public void readFile(String file, Configuration conf) throws IOException;
	  
	  public List<String> readFileO(String file, Configuration conf) throws IOException;

	  /**
	   * delete a directory in hdfs
	   * @param file
	   * @throws IOException
	   */
	  public void deleteFile(String file, Configuration conf) throws IOException;
	  
	/**
	   * create directory in hdfs
	   * @param dir
	   * @throws IOException
	   */
	  public void mkdir(String dir, Configuration conf) throws IOException;
	  
	  public void copyHdfsFile(String hdfsSource, String hdfsDest, Configuration conf) throws IOException;

}
