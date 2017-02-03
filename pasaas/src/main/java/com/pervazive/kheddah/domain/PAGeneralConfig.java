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
public class PAGeneralConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hdfsurl")
    private String hdfsurl;

    @Column(name = "sparkurl")
    private String sparkurl;

    @Column(name = "dr_script")
    private String dr_script;

    @Column(name = "dr_inseriesformat")
    private String dr_inseriesformat;

    @Column(name = "dr_outseriesformat")
    private String dr_outseriesformat;

    @Column(name = "dr_inputfile")
    private String dr_inputfile;

    @Column(name = "dr_expressionfile")
    private String dr_expressionfile;

    @Column(name = "dr_outputfile")
    private String dr_outputfile;

    @Column(name = "dr_seriesgcolindex")
    private String dr_seriesgcolindex;

    @Column(name = "dr_seriesstart")
    private Integer dr_seriesstart;

    @Column(name = "dr_seriesend")
    private Integer dr_seriesend;

    @Column(name = "dr_seriesnxt")
    private Integer dr_seriesnxt;

    @Column(name = "df_inputfile")
    private String df_inputfile;

    @Column(name = "df_outputfile")
    private String df_outputfile;

    @Column(name = "df_entityfld")
    private String df_entityfld;

    @Column(name = "df_seriesfld")
    private String df_seriesfld;

    @Column(name = "df_inseriesformat")
    private String df_inseriesformat;

    @Column(name = "df_outseriesformat")
    private String df_outseriesformat;

    @Column(name = "df_isheader")
    private String df_isheader;

    @Column(name = "df_script")
    private String df_script;

    @Column(name = "df_skipfldindexes")
    private String df_skipfldindexes;

    @Column(name = "ss_inputfile")
    private String ss_inputfile;

    @Column(name = "ss_outputfile")
    private String ss_outputfile;

    @Column(name = "ss_saxcodefldindex")
    private Integer ss_saxcodefldindex;

    @Column(name = "ss_subseqinterval")
    private Integer ss_subseqinterval;

    @Column(name = "ss_subseqintervalthreshhold")
    private Integer ss_subseqintervalthreshhold;

    @Column(name = "ss_script")
    private String ss_script;

    @Column(name = "ss_tempopfile")
    private String ss_tempopfile;

    @Column(name = "ss_inputdirname")
    private String ss_inputdirname;

    @Column(name = "sq_ipaddr")
    private String sq_ipaddr;

    @Column(name = "sq_mysqlpwd")
    private String sq_mysqlpwd;

    @Column(name = "sq_mysqldb")
    private String sq_mysqldb;

    @Column(name = "sq_loadlocalinfile")
    private String sq_loadlocalinfile;

    @Column(name = "sq_daydumppath")
    private String sq_daydumppath;

    @Column(name = "sq_updquery")
    private String sq_updquery;

    @Column(name = "sq_insertquery")
    private String sq_insertquery;

    @Column(name = "sq_script")
    private String sq_script;

    @Column(name = "sq_command")
    private String sq_command;

    @Column(name = "sq_localinfiile")
    private String sq_localinfiile;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

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

    public String getDr_script() {
        return dr_script;
    }

    public PAGeneralConfig dr_script(String dr_script) {
        this.dr_script = dr_script;
        return this;
    }

    public void setDr_script(String dr_script) {
        this.dr_script = dr_script;
    }

    public String getDr_inseriesformat() {
        return dr_inseriesformat;
    }

    public PAGeneralConfig dr_inseriesformat(String dr_inseriesformat) {
        this.dr_inseriesformat = dr_inseriesformat;
        return this;
    }

    public void setDr_inseriesformat(String dr_inseriesformat) {
        this.dr_inseriesformat = dr_inseriesformat;
    }

    public String getDr_outseriesformat() {
        return dr_outseriesformat;
    }

    public PAGeneralConfig dr_outseriesformat(String dr_outseriesformat) {
        this.dr_outseriesformat = dr_outseriesformat;
        return this;
    }

    public void setDr_outseriesformat(String dr_outseriesformat) {
        this.dr_outseriesformat = dr_outseriesformat;
    }

    public String getDr_inputfile() {
        return dr_inputfile;
    }

    public PAGeneralConfig dr_inputfile(String dr_inputfile) {
        this.dr_inputfile = dr_inputfile;
        return this;
    }

    public void setDr_inputfile(String dr_inputfile) {
        this.dr_inputfile = dr_inputfile;
    }

    public String getDr_expressionfile() {
        return dr_expressionfile;
    }

    public PAGeneralConfig dr_expressionfile(String dr_expressionfile) {
        this.dr_expressionfile = dr_expressionfile;
        return this;
    }

    public void setDr_expressionfile(String dr_expressionfile) {
        this.dr_expressionfile = dr_expressionfile;
    }

    public String getDr_outputfile() {
        return dr_outputfile;
    }

    public PAGeneralConfig dr_outputfile(String dr_outputfile) {
        this.dr_outputfile = dr_outputfile;
        return this;
    }

    public void setDr_outputfile(String dr_outputfile) {
        this.dr_outputfile = dr_outputfile;
    }

    public String getDr_seriesgcolindex() {
        return dr_seriesgcolindex;
    }

    public PAGeneralConfig dr_seriesgcolindex(String dr_seriesgcolindex) {
        this.dr_seriesgcolindex = dr_seriesgcolindex;
        return this;
    }

    public void setDr_seriesgcolindex(String dr_seriesgcolindex) {
        this.dr_seriesgcolindex = dr_seriesgcolindex;
    }

    public Integer getDr_seriesstart() {
        return dr_seriesstart;
    }

    public PAGeneralConfig dr_seriesstart(Integer dr_seriesstart) {
        this.dr_seriesstart = dr_seriesstart;
        return this;
    }

    public void setDr_seriesstart(Integer dr_seriesstart) {
        this.dr_seriesstart = dr_seriesstart;
    }

    public Integer getDr_seriesend() {
        return dr_seriesend;
    }

    public PAGeneralConfig dr_seriesend(Integer dr_seriesend) {
        this.dr_seriesend = dr_seriesend;
        return this;
    }

    public void setDr_seriesend(Integer dr_seriesend) {
        this.dr_seriesend = dr_seriesend;
    }

    public Integer getDr_seriesnxt() {
        return dr_seriesnxt;
    }

    public PAGeneralConfig dr_seriesnxt(Integer dr_seriesnxt) {
        this.dr_seriesnxt = dr_seriesnxt;
        return this;
    }

    public void setDr_seriesnxt(Integer dr_seriesnxt) {
        this.dr_seriesnxt = dr_seriesnxt;
    }

    public String getDf_inputfile() {
        return df_inputfile;
    }

    public PAGeneralConfig df_inputfile(String df_inputfile) {
        this.df_inputfile = df_inputfile;
        return this;
    }

    public void setDf_inputfile(String df_inputfile) {
        this.df_inputfile = df_inputfile;
    }

    public String getDf_outputfile() {
        return df_outputfile;
    }

    public PAGeneralConfig df_outputfile(String df_outputfile) {
        this.df_outputfile = df_outputfile;
        return this;
    }

    public void setDf_outputfile(String df_outputfile) {
        this.df_outputfile = df_outputfile;
    }

    public String getDf_entityfld() {
        return df_entityfld;
    }

    public PAGeneralConfig df_entityfld(String df_entityfld) {
        this.df_entityfld = df_entityfld;
        return this;
    }

    public void setDf_entityfld(String df_entityfld) {
        this.df_entityfld = df_entityfld;
    }

    public String getDf_seriesfld() {
        return df_seriesfld;
    }

    public PAGeneralConfig df_seriesfld(String df_seriesfld) {
        this.df_seriesfld = df_seriesfld;
        return this;
    }

    public void setDf_seriesfld(String df_seriesfld) {
        this.df_seriesfld = df_seriesfld;
    }

    public String getDf_inseriesformat() {
        return df_inseriesformat;
    }

    public PAGeneralConfig df_inseriesformat(String df_inseriesformat) {
        this.df_inseriesformat = df_inseriesformat;
        return this;
    }

    public void setDf_inseriesformat(String df_inseriesformat) {
        this.df_inseriesformat = df_inseriesformat;
    }

    public String getDf_outseriesformat() {
        return df_outseriesformat;
    }

    public PAGeneralConfig df_outseriesformat(String df_outseriesformat) {
        this.df_outseriesformat = df_outseriesformat;
        return this;
    }

    public void setDf_outseriesformat(String df_outseriesformat) {
        this.df_outseriesformat = df_outseriesformat;
    }

    public String getDf_isheader() {
        return df_isheader;
    }

    public PAGeneralConfig df_isheader(String df_isheader) {
        this.df_isheader = df_isheader;
        return this;
    }

    public void setDf_isheader(String df_isheader) {
        this.df_isheader = df_isheader;
    }

    public String getDf_script() {
        return df_script;
    }

    public PAGeneralConfig df_script(String df_script) {
        this.df_script = df_script;
        return this;
    }

    public void setDf_script(String df_script) {
        this.df_script = df_script;
    }

    public String getDf_skipfldindexes() {
        return df_skipfldindexes;
    }

    public PAGeneralConfig df_skipfldindexes(String df_skipfldindexes) {
        this.df_skipfldindexes = df_skipfldindexes;
        return this;
    }

    public void setDf_skipfldindexes(String df_skipfldindexes) {
        this.df_skipfldindexes = df_skipfldindexes;
    }

    public String getSs_inputfile() {
        return ss_inputfile;
    }

    public PAGeneralConfig ss_inputfile(String ss_inputfile) {
        this.ss_inputfile = ss_inputfile;
        return this;
    }

    public void setSs_inputfile(String ss_inputfile) {
        this.ss_inputfile = ss_inputfile;
    }

    public String getSs_outputfile() {
        return ss_outputfile;
    }

    public PAGeneralConfig ss_outputfile(String ss_outputfile) {
        this.ss_outputfile = ss_outputfile;
        return this;
    }

    public void setSs_outputfile(String ss_outputfile) {
        this.ss_outputfile = ss_outputfile;
    }

    public Integer getSs_saxcodefldindex() {
        return ss_saxcodefldindex;
    }

    public PAGeneralConfig ss_saxcodefldindex(Integer ss_saxcodefldindex) {
        this.ss_saxcodefldindex = ss_saxcodefldindex;
        return this;
    }

    public void setSs_saxcodefldindex(Integer ss_saxcodefldindex) {
        this.ss_saxcodefldindex = ss_saxcodefldindex;
    }

    public Integer getSs_subseqinterval() {
        return ss_subseqinterval;
    }

    public PAGeneralConfig ss_subseqinterval(Integer ss_subseqinterval) {
        this.ss_subseqinterval = ss_subseqinterval;
        return this;
    }

    public void setSs_subseqinterval(Integer ss_subseqinterval) {
        this.ss_subseqinterval = ss_subseqinterval;
    }

    public Integer getSs_subseqintervalthreshhold() {
        return ss_subseqintervalthreshhold;
    }

    public PAGeneralConfig ss_subseqintervalthreshhold(Integer ss_subseqintervalthreshhold) {
        this.ss_subseqintervalthreshhold = ss_subseqintervalthreshhold;
        return this;
    }

    public void setSs_subseqintervalthreshhold(Integer ss_subseqintervalthreshhold) {
        this.ss_subseqintervalthreshhold = ss_subseqintervalthreshhold;
    }

    public String getSs_script() {
        return ss_script;
    }

    public PAGeneralConfig ss_script(String ss_script) {
        this.ss_script = ss_script;
        return this;
    }

    public void setSs_script(String ss_script) {
        this.ss_script = ss_script;
    }

    public String getSs_tempopfile() {
        return ss_tempopfile;
    }

    public PAGeneralConfig ss_tempopfile(String ss_tempopfile) {
        this.ss_tempopfile = ss_tempopfile;
        return this;
    }

    public void setSs_tempopfile(String ss_tempopfile) {
        this.ss_tempopfile = ss_tempopfile;
    }

    public String getSs_inputdirname() {
        return ss_inputdirname;
    }

    public PAGeneralConfig ss_inputdirname(String ss_inputdirname) {
        this.ss_inputdirname = ss_inputdirname;
        return this;
    }

    public void setSs_inputdirname(String ss_inputdirname) {
        this.ss_inputdirname = ss_inputdirname;
    }

    public String getSq_ipaddr() {
        return sq_ipaddr;
    }

    public PAGeneralConfig sq_ipaddr(String sq_ipaddr) {
        this.sq_ipaddr = sq_ipaddr;
        return this;
    }

    public void setSq_ipaddr(String sq_ipaddr) {
        this.sq_ipaddr = sq_ipaddr;
    }

    public String getSq_mysqlpwd() {
        return sq_mysqlpwd;
    }

    public PAGeneralConfig sq_mysqlpwd(String sq_mysqlpwd) {
        this.sq_mysqlpwd = sq_mysqlpwd;
        return this;
    }

    public void setSq_mysqlpwd(String sq_mysqlpwd) {
        this.sq_mysqlpwd = sq_mysqlpwd;
    }

    public String getSq_mysqldb() {
        return sq_mysqldb;
    }

    public PAGeneralConfig sq_mysqldb(String sq_mysqldb) {
        this.sq_mysqldb = sq_mysqldb;
        return this;
    }

    public void setSq_mysqldb(String sq_mysqldb) {
        this.sq_mysqldb = sq_mysqldb;
    }

    public String getSq_loadlocalinfile() {
        return sq_loadlocalinfile;
    }

    public PAGeneralConfig sq_loadlocalinfile(String sq_loadlocalinfile) {
        this.sq_loadlocalinfile = sq_loadlocalinfile;
        return this;
    }

    public void setSq_loadlocalinfile(String sq_loadlocalinfile) {
        this.sq_loadlocalinfile = sq_loadlocalinfile;
    }

    public String getSq_daydumppath() {
        return sq_daydumppath;
    }

    public PAGeneralConfig sq_daydumppath(String sq_daydumppath) {
        this.sq_daydumppath = sq_daydumppath;
        return this;
    }

    public void setSq_daydumppath(String sq_daydumppath) {
        this.sq_daydumppath = sq_daydumppath;
    }

    public String getSq_updquery() {
        return sq_updquery;
    }

    public PAGeneralConfig sq_updquery(String sq_updquery) {
        this.sq_updquery = sq_updquery;
        return this;
    }

    public void setSq_updquery(String sq_updquery) {
        this.sq_updquery = sq_updquery;
    }

    public String getSq_insertquery() {
        return sq_insertquery;
    }

    public PAGeneralConfig sq_insertquery(String sq_insertquery) {
        this.sq_insertquery = sq_insertquery;
        return this;
    }

    public void setSq_insertquery(String sq_insertquery) {
        this.sq_insertquery = sq_insertquery;
    }

    public String getSq_script() {
        return sq_script;
    }

    public PAGeneralConfig sq_script(String sq_script) {
        this.sq_script = sq_script;
        return this;
    }

    public void setSq_script(String sq_script) {
        this.sq_script = sq_script;
    }

    public String getSq_command() {
        return sq_command;
    }

    public PAGeneralConfig sq_command(String sq_command) {
        this.sq_command = sq_command;
        return this;
    }

    public void setSq_command(String sq_command) {
        this.sq_command = sq_command;
    }

    public String getSq_localinfiile() {
        return sq_localinfiile;
    }

    public PAGeneralConfig sq_localinfiile(String sq_localinfiile) {
        this.sq_localinfiile = sq_localinfiile;
        return this;
    }

    public void setSq_localinfiile(String sq_localinfiile) {
        this.sq_localinfiile = sq_localinfiile;
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
            ", dr_script='" + dr_script + "'" +
            ", dr_inseriesformat='" + dr_inseriesformat + "'" +
            ", dr_outseriesformat='" + dr_outseriesformat + "'" +
            ", dr_inputfile='" + dr_inputfile + "'" +
            ", dr_expressionfile='" + dr_expressionfile + "'" +
            ", dr_outputfile='" + dr_outputfile + "'" +
            ", dr_seriesgcolindex='" + dr_seriesgcolindex + "'" +
            ", dr_seriesstart='" + dr_seriesstart + "'" +
            ", dr_seriesend='" + dr_seriesend + "'" +
            ", dr_seriesnxt='" + dr_seriesnxt + "'" +
            ", df_inputfile='" + df_inputfile + "'" +
            ", df_outputfile='" + df_outputfile + "'" +
            ", df_entityfld='" + df_entityfld + "'" +
            ", df_seriesfld='" + df_seriesfld + "'" +
            ", df_inseriesformat='" + df_inseriesformat + "'" +
            ", df_outseriesformat='" + df_outseriesformat + "'" +
            ", df_isheader='" + df_isheader + "'" +
            ", df_script='" + df_script + "'" +
            ", df_skipfldindexes='" + df_skipfldindexes + "'" +
            ", ss_inputfile='" + ss_inputfile + "'" +
            ", ss_outputfile='" + ss_outputfile + "'" +
            ", ss_saxcodefldindex='" + ss_saxcodefldindex + "'" +
            ", ss_subseqinterval='" + ss_subseqinterval + "'" +
            ", ss_subseqintervalthreshhold='" + ss_subseqintervalthreshhold + "'" +
            ", ss_script='" + ss_script + "'" +
            ", ss_tempopfile='" + ss_tempopfile + "'" +
            ", ss_inputdirname='" + ss_inputdirname + "'" +
            ", sq_ipaddr='" + sq_ipaddr + "'" +
            ", sq_mysqlpwd='" + sq_mysqlpwd + "'" +
            ", sq_mysqldb='" + sq_mysqldb + "'" +
            ", sq_loadlocalinfile='" + sq_loadlocalinfile + "'" +
            ", sq_daydumppath='" + sq_daydumppath + "'" +
            ", sq_updquery='" + sq_updquery + "'" +
            ", sq_insertquery='" + sq_insertquery + "'" +
            ", sq_script='" + sq_script + "'" +
            ", sq_command='" + sq_command + "'" +
            ", sq_localinfiile='" + sq_localinfiile + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
