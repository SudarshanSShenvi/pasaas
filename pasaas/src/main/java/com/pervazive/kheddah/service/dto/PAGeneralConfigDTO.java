package com.pervazive.kheddah.service.dto;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.pervazive.kheddah.domain.PAGeneralConfig;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A DTO representing a user, with his authorities, projects and organization
 */
public class PAGeneralConfigDTO extends PAGeneralConfig {

	
	private Long id;
	 
	private String createdBy;

    private ZonedDateTime createdDate;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;
    
    private String hdfsurl;
    private String sparkurl;
    private PAStatus pastatus;
    private String hdfsbasedir; 	//==> /user/pervazive
    private String trainingfeeddir;  	//==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project) /ppa-repo/traindata
    private String predictfeeddir;  //	==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project) /ppa-repo/predictdata
    private String trainingrollupinfile; //==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project) /ppa-repo/traindatafeed
    private String predictrollupinfile;  //==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project) /ppa-repo/predictdatafeed
    private String expressionfilepath;	//==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project) /ppa-repo/conf/
    private String trainingpatternoutfile; //==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project)/ppa-repo/traindata/temp/trainDW
    private String predictpatternoutfile; //==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project)/ppa-repo/predictdata/temp/predictDW
    private String trainingpatterntmpoutfile;	//==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project)/ppa-repo/temp/trainTD
    private String predictpatterntmpoutfile;	//==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project)/ppa-repo/temp/predictTD
	private String sqltmpuploadpath;        	//==> /opt?? + basedirectory (Organization/Project) + resultfile
    private byte[] exprfile; // expression.txt
    private String exprfileContentType;
    
    public PAGeneralConfigDTO(Long id, String createdBy, ZonedDateTime createdDate, String lastModifiedBy,
    		ZonedDateTime lastModifiedDate,String hdfsurl,String sparkurl,PAStatus pastatus,String hdfsbasedir,
    		String trainingfeeddir,String predictfeeddir,String trainingrollupinfile,String predictrollupinfile,
    		String expressionfilepath,String trainingpatternoutfile,String predictpatternoutfile,String trainingpatterntmpoutfile,
    		String predictpatterntmpoutfile,String sqltmpuploadpath,byte[] exprfile,String exprfileContentType) {
    	
    	this.id = id;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.hdfsurl = hdfsurl;
        this.sparkurl=sparkurl;
        this.pastatus=pastatus;
        this.hdfsbasedir=hdfsbasedir;
		this.trainingfeeddir=trainingfeeddir;
		this.predictfeeddir=predictfeeddir;
		this.trainingrollupinfile=trainingrollupinfile;
		this.predictrollupinfile=predictrollupinfile;
		this.expressionfilepath=expressionfilepath;
		this.trainingpatternoutfile=trainingpatternoutfile;
		this.predictpatternoutfile=predictpatternoutfile;
		this.trainingpatterntmpoutfile=trainingpatterntmpoutfile;
		this.predictpatterntmpoutfile=predictpatterntmpoutfile;
		this.sqltmpuploadpath=sqltmpuploadpath;
		this.exprfile=exprfile;
		this.exprfileContentType=exprfileContentType;
    }
    
    public PAGeneralConfigDTO() {
    }

    public PAGeneralConfigDTO(PAGeneralConfig generalConfig) {
    	this(generalConfig.getId(), generalConfig.getCreatedBy(), generalConfig.getCreatedDate(), generalConfig.getLastModifiedBy(), 
    			generalConfig.getLastModifiedDate(), generalConfig.getHdfsurl(), generalConfig.getSparkurl(), generalConfig.getPastatus(), 
    			generalConfig.getHdfsbasedir(), generalConfig.getTrainingfeeddir(), generalConfig.getPredictfeeddir(),
    			generalConfig.getTrainingrollupinfile(), generalConfig.getPredictrollupinfile(), generalConfig.getExpressionfilepath(),
    			generalConfig.getTrainingpatternoutfile(), generalConfig.getPredictpatternoutfile(), 
    			generalConfig.getTrainingpatterntmpoutfile(), generalConfig.getPredictpatterntmpoutfile(),
    			generalConfig.getSqltmpuploadpath(), generalConfig.getExprfile(), generalConfig.getExprfileContentType());
        
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public ZonedDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getHdfsurl() {
		return hdfsurl;
	}

	public void setHdfsurl(String hdfsurl) {
		this.hdfsurl = hdfsurl;
	}

	public String getSparkurl() {
		return sparkurl;
	}

	public void setSparkurl(String sparkurl) {
		this.sparkurl = sparkurl;
	}

	public PAStatus getPastatus() {
		return pastatus;
	}

	public void setPastatus(PAStatus pastatus) {
		this.pastatus = pastatus;
	}

	public String getHdfsbasedir() {
		return hdfsbasedir;
	}

	public void setHdfsbasedir(String hdfsbasedir) {
		this.hdfsbasedir = hdfsbasedir;
	}

	public String getTrainingfeeddir() {
		return trainingfeeddir;
	}

	public void setTrainingfeeddir(String trainingfeeddir) {
		this.trainingfeeddir = trainingfeeddir;
	}

	public String getPredictfeeddir() {
		return predictfeeddir;
	}

	public void setPredictfeeddir(String predictfeeddir) {
		this.predictfeeddir = predictfeeddir;
	}

	public String getTrainingrollupinfile() {
		return trainingrollupinfile;
	}

	public void setTrainingrollupinfile(String trainingrollupinfile) {
		this.trainingrollupinfile = trainingrollupinfile;
	}

	public String getPredictrollupinfile() {
		return predictrollupinfile;
	}

	public void setPredictrollupinfile(String predictrollupinfile) {
		this.predictrollupinfile = predictrollupinfile;
	}

	public String getExpressionfilepath() {
		return expressionfilepath;
	}

	public void setExpressionfilepath(String expressionfilepath) {
		this.expressionfilepath = expressionfilepath;
	}

	public String getTrainingpatternoutfile() {
		return trainingpatternoutfile;
	}

	public void setTrainingpatternoutfile(String trainingpatternoutfile) {
		this.trainingpatternoutfile = trainingpatternoutfile;
	}

	public String getPredictpatternoutfile() {
		return predictpatternoutfile;
	}

	public void setPredictpatternoutfile(String predictpatternoutfile) {
		this.predictpatternoutfile = predictpatternoutfile;
	}

	public String getTrainingpatterntmpoutfile() {
		return trainingpatterntmpoutfile;
	}

	public void setTrainingpatterntmpoutfile(String trainingpatterntmpoutfile) {
		this.trainingpatterntmpoutfile = trainingpatterntmpoutfile;
	}

	public String getPredictpatterntmpoutfile() {
		return predictpatterntmpoutfile;
	}

	public void setPredictpatterntmpoutfile(String predictpatterntmpoutfile) {
		this.predictpatterntmpoutfile = predictpatterntmpoutfile;
	}

	public String getSqltmpuploadpath() {
		return sqltmpuploadpath;
	}

	public void setSqltmpuploadpath(String sqltmpuploadpath) {
		this.sqltmpuploadpath = sqltmpuploadpath;
	}

	public byte[] getExprfile() {
		return exprfile;
	}

	public void setExprfile(byte[] exprfile) {
		this.exprfile = exprfile;
	}

	public String getExprfileContentType() {
		return exprfileContentType;
	}

	public void setExprfileContentType(String exprfileContentType) {
		this.exprfileContentType = exprfileContentType;
	}
    
}
