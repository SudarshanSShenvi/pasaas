package com.pervazive.kheddah.dbuploader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class DBOperationsHandler {
	
	private final Logger log = LoggerFactory.getLogger(DBOperationsHandler.class);
	
	public String deleteFromSaxcodeTmp = "truncate pa_saxcode_tmp";
	//public String deleteFromSaxcodeTmp = "delete from pa_saxcode_tmp where paorgsct_id = :template_orgId AND paprosct_id = :template_projId";
	
	public String loadDataIntoSaxcodeTmp = "load data local infile \':template_filePath\' into table pa_saxcode_tmp fields terminated by '\t' lines terminated by '\n' "
			+ "(distalarm, saxcode, total, painterval, @paorgsct_id, @paprosct_id, @pastatus)"
		+ " SET paorgsct_id = :template_orgId, paprosct_id = :template_projId, pastatus = 'Active'";
	
	public String updateSaxCodeFromTmp ="UPDATE pa_saxcode a JOIN pa_saxcode_tmp b ON a.distalarm = b.distalarm AND a.saxcode = b.saxcode "
			+ "AND a.paprosc_id = b. paprosct_id AND a.paorgsc_id = b. paorgsct_id AND a.pastatus = 'Active'  SET a.total = a.total + b.total";
	
	
	public String insertSaxcodeFromTmp ="INSERT INTO pa_saxcode (distalarm,saxcode,total, paorgsc_id, paprosc_id, pastatus) "
			+ "SELECT a.distalarm,a.saxcode,a.total,a.paprosct_id,a.paorgsct_id,a.pastatus FROM pa_saxcode_tmp a WHERE (a.distalarm, a.saxcode, a.paprosct_id, a.paorgsct_id)"
			+ "NOT IN (select distalarm, saxcode, paprosc_id, paorgsc_id from pa_saxcode)";
	
	public String insertPredictionsFromSaxcodeAndTmp = "INSERT INTO pa_prediction_score (dist,alarmno,bcount,ccount,bscore,cscore,createdon,pastatus,paorgps_id,paprops_id, severity) "
			+ "SELECT SUBSTRING_INDEX(AA.distalarm,'_',1), SUBSTRING_INDEX(AA.distalarm,'_',-1), AA.b AS bCount, BB.c AS cCount, ROUND(IFNULL((AA.b / (AA.b + BB.c)), 0), 3) AS bScore, "
			+ "ROUND(IFNULL((BB.c / (AA.b + BB.c)), 0), 3) AS cScore, now(), 'Active', org, proj, AA.severity "
			+ "FROM ( SELECT b.distalarm, b.saxcode, IFNULL(a.total,0) AS b, b.paorgsct_id as org, b.paprosct_id as proj ,b.severity as severity  "
			+ "FROM pa_saxcode AS a RIGHT OUTER JOIN pa_saxcode_tmp b ON a.distalarm = b.distalarm AND a.saxcode = CONCAT(b.saxcode, 'b')) AS AA "
			+ "JOIN ( SELECT b.distalarm, b.saxcode, IFNULL(a.total,0) AS c FROM pa_saxcode AS a "
			+ "RIGHT OUTER JOIN pa_saxcode_tmp b ON a.distalarm = b.distalarm AND a.saxcode = CONCAT(b.saxcode, 'c')) AS BB "
			+ "where AA.distalarm = BB.distalarm and AA.saxcode = BB.saxcode";
	
	public String exportCSVPredictions = "SELECT dist, alarmno, bscore, cscore FROM pa_prediction_score "
			+ "WHERE paorgps_id = :template_orgId and paprops_id=:template_projId INTO OUTFILE ':template_filePath' "
			+ "FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n'";
	
	public String exportTSVPredictions = "SELECT dist, alarmno, bscore, cscore FROM pa_prediction_score "
			+ "WHERE paorgps_id = :template_orgId and paprops_id=:template_projId INTO OUTFILE ':template_filePath' "
			+ "FIELDS TERMINATED BY '\t' ENCLOSED BY '\"' LINES TERMINATED BY '\n'";
		

/*	public static void main(String[] args) {
		DBOperationsHandler dbOperationsHandler = new DBOperationsHandler();
		dbOperationsHandler.executeAnyQuery(dbOperationsHandler.insertPredictionsFromSaxcodeAndTmp);
		}*/

	public Connection getConnection(String dbUrl, String dbUser, String dbpwd) throws SQLException {
			return DriverManager.getConnection(dbUrl, dbUser, dbpwd);
	}
	
	public boolean executeAnyQuery(Connection connection, String query, Long orgId, Long projectId, String path) throws SQLException {
			query = query.replace(":template_orgId", ""+orgId);
			query = query.replace(":template_projId", ""+projectId);
			query = query.replace(":template_filePath", path);
			log.debug("QUERY --> "+query);
			Statement stmt = connection.createStatement();
			return stmt.execute(query);
	}

}
