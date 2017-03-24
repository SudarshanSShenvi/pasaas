package com.pervazive.kheddah.service.dto;

import java.io.File;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.permission.FsPermission;

public class FileStatusDTO {
	
	

	/*public FileStatusDTO(Long accessTime, String group, String owner, String path, Long fileSize, FsPermission permission) {
		this.accessTime = accessTime;
		this.group = group;
		this.owner = owner;
		this.path = path;
		this.fileSize = fileSize;
		this.permisssion = permission;
	}
    
    public FileStatusDTO(FileStatus fileStatus){
    	this(fileStatus.getAccessTime(), fileStatus.getGroup(), fileStatus.getOwner(), fileStatus.getPath().toString(), fileStatus.getLen(), fileStatus.getPermission());
    }*/
    public FileStatusDTO(Long accessTime, String path, Long fileSize) {
		this.accessTime = accessTime;
		this.path = path;
		this.fileSize = fileSize;
	}
    
    public FileStatusDTO(FileStatus fileStatus){
    	this(fileStatus.getAccessTime(), fileStatus.getPath().toString(), fileStatus.getLen());
    }
    
    public FileStatusDTO(){
    	
    }
    private Long accessTime;
    private Long fileSize;
   
	//private String group;
    //private String owner;
    private String path;
    //private FsPermission permisssion;
    
    public Long getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Long accessTime) {
		this.accessTime = accessTime;
	}

	/*public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}*/

	public String getPath() {
		File file = new File(path);
		return file.getName();
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	 public Long getFileSize() {
			return fileSize;
		}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/*public FsPermission getPermisssion() {
		return permisssion;
	}

	public void setPermisssion(FsPermission permisssion) {
		this.permisssion = permisssion;
	}*/
	
}
