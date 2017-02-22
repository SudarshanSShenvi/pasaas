package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAGeneralConfig.
 */
@Entity
@Table(name = "pa_general_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAGeneralConfig extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hdfsurl")
    private String hdfsurl;

    @Column(name = "sparkurl")
    private String sparkurl;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;
    
    @Column(name = "hdfsbasedir")
    private String hdfsbasedir; 	//==> /user/pervazive
    
    @Column(name = "trainingfeeddir")
    private String trainingfeeddir;  	//==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project) /ppa-repo/traindata
    
    @Column(name = "predictfeeddir")
    private String predictfeeddir;  //	==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project) /ppa-repo/predictdata
    
    @Column(name = "trainingrollupinfile")
    private String trainingrollupinfile; //==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project) /ppa-repo/traindatafeed
    
    @Column(name = "predictrollupinfile")
    private String predictrollupinfile;  //==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project) /ppa-repo/predictdatafeed
    
    @Column(name = "expressionfilepath")
    private String expressionfilepath;	//==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project) /ppa-repo/conf/
    
    @Column(name = "trainingpatternoutfile")
    private String trainingpatternoutfile; //==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project)/ppa-repo/traindata/temp/trainDW
    
    @Column(name = "predictpatternoutfile")
    private String predictpatternoutfile; //==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project)/ppa-repo/predictdata/temp/predictDW
    
    @Column(name = "trainingpatterntmpoutfile")
    private String trainingpatterntmpoutfile;	//==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project)/ppa-repo/temp/trainTD
    
    @Column(name = "predictpatterntmpoutfile")
    private String predictpatterntmpoutfile;	//==> hdfsurl + hdfsbaseDir + basedirectory (Organization/Project)/ppa-repo/temp/predictTD
    
    @Column(name = "sqltmpuploadpath")
	private String sqltmpuploadpath;        	//==> /opt?? + basedirectory (Organization/Project) + resultfile
    
    @Lob
    @Column(name = "exprfile")
    private byte[] exprfile; // expression.txt

    @Column(name = "exprfile_content_type")
    private String exprfileContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHdfsurl() {
        return hdfsurl;
    }

    public PAGeneralConfig hdfsurl(String hdfsurl) {
        this.hdfsurl = hdfsurl;
        return this;
    }

    public void setHdfsurl(String hdfsurl) {
        this.hdfsurl = hdfsurl;
    }

    public String getSparkurl() {
        return sparkurl;
    }

    public PAGeneralConfig sparkurl(String sparkurl) {
        this.sparkurl = sparkurl;
        return this;
    }

    public void setSparkurl(String sparkurl) {
        this.sparkurl = sparkurl;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAGeneralConfig pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAGeneralConfig pAGeneralConfig = (PAGeneralConfig) o;
        if (pAGeneralConfig.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAGeneralConfig.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAGeneralConfig{" +
            "id=" + id +
            ", hdfsurl='" + hdfsurl + "'" +
            ", sparkurl='" + sparkurl + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
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
