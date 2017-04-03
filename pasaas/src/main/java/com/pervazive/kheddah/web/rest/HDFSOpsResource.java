package com.pervazive.kheddah.web.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.security.AuthoritiesConstants;
import com.pervazive.kheddah.service.HDFSFileOperationsService;
import com.pervazive.kheddah.service.PAProjectService;
import com.pervazive.kheddah.service.dto.FileStatusDTO;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;


/**
 * REST controller for managing PANotification.
 */
@RestController
@RequestMapping("/api")
public class HDFSOpsResource {
	
	@Inject
	private HDFSFileOperationsService hdfsFileOperationsService;
	
	@Inject
	private PAProjectService paProjectService;

    private final Logger log = LoggerFactory.getLogger(HDFSOpsResource.class);
    
    /**
     * Default File structure to be created
     * hdfsurl 	|
	 *			| -- /user/pervazive/organization/project  	|
	 *		     										| -- /ppa-repo
	 *							    					| -- /ppa-repo/traindata
	 *							    					| -- /ppa-repo/traindatafeed
	 *							    					| -- /ppa-repo/predictdata
	 *							    					| -- /ppa-repo/predictdatafeed
	 *							    					| -- /ppa-repo/conf
	 *							    					| -- /ppa-repo/traindata/temp
	 *							    					| -- /ppa-repo/traindata/temp/trainDW
	 *							    					| -- /ppa-repo/traindata/temp/trainTD (PATTERN RESULT)
	 *							    					| -- /ppa-repo/predictdata/temp
	 *							    					| -- /ppa-repo/predictdata/temp/predictDW
	 *							    					| -- /ppa-repo/predictdata/temp/predictTD (PREDICTION RESULT)
     */
 
    /**
     * TODO all parms to be updated
     * GET  /p-a-notifications : get all the pANotifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pANotifications in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    /*@PostMapping("/mkdir/{newDir}")
    @Timed
    public ResponseEntity<String> createNewDir(@PathVariable String newDir)
        throws URISyntaxException {
       try {
        	hdfsFileOperationsService.mkdir(newDir, hdfsFileOperationsService.init("pervazive"));
		} catch (IOException e) {
			new ResponseEntity<String>("Error :"+e.getMessage() ,HttpStatus.BAD_REQUEST);
		}
       
        return new ResponseEntity<String>("Successfully Created",HttpStatus.OK);
    }*/
    
    /**
     * TODO all parms to be updated
     * GET  /p-a-notifications : get all the pANotifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pANotifications in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    /*@PostMapping("/mkdirstructure/{project}")
    @Timed
    public ResponseEntity<String> createNewDirStructure(@PathVariable String project, HttpServletRequest request)
        throws URISyntaxException {
    	//RUN-ONCE
       try {
    	   List<PAOrganization> paOrganizations = (List<PAOrganization>) request.getSession().getAttribute("organizationsess");
    	   hdfsFileOperationsService.mkdirStructure(project, paOrganizations.get(0).getOrganization());
		} catch (IOException e) {
			new ResponseEntity<String>("Error :"+e.getMessage() ,HttpStatus.BAD_REQUEST);
		}
       
        return new ResponseEntity<String>("Successfully Created",HttpStatus.OK);
    }*/
    
    @PostMapping("/pushfiles")
    @Timed
    public ResponseEntity<String> createNewFile(@RequestPart("file") @Valid List<MultipartFile> file)
        throws URISyntaxException {
        try {
        	hdfsFileOperationsService.addMultipleFiles(file, "/user/pervazive/", hdfsFileOperationsService.init("pervazive"));
		} catch (IOException e) {
			new ResponseEntity<String>("Error :"+e.getMessage() ,HttpStatus.BAD_REQUEST);
		}
       
       return new ResponseEntity<String>("Successfully uploaded",HttpStatus.OK);
    }
    
    @PostMapping("/pushfile/{projectId}")
    @Timed
    public ResponseEntity<String> createNewFile(@RequestPart("file") @Valid MultipartFile file, @PathVariable Long projectId)
        throws URISyntaxException {
    	PAProject paProject = paProjectService.findOne(projectId);
    	
    	try {
    		Configuration configuration = hdfsFileOperationsService.init("pervazive");
    		String dirName = configuration.get("fs.defaultFS")+"/"+paProject.getPaorgpro().getOrganization()+"/"+paProject.getProjectname()+"/ppa-repo/traindata/"+file.getOriginalFilename();
    		
        	hdfsFileOperationsService.addFile(file, dirName, configuration);
		} catch (IOException e) {
			new ResponseEntity<String>("Error :"+e.getMessage() ,HttpStatus.BAD_REQUEST);
		}
       
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    
    @GetMapping("/download/{projectid}/{fileName:.+}")
    @Timed
    public void downloadFile(@PathVariable("projectid") Long projectId, @PathVariable("fileName") String fileName,  HttpServletResponse response) throws IOException {
    	
    	/*String tmp = "SUQsQVVUSE9SLEZJTEVOQU1FLERBVEVFWEVDVVRFRCxPUkRFUkVYRUNVVEVELEVYRUNUWVBFLE1ENVNVTSxERVNDUklQVElPTixDT01NRU5UUyxU"
    			+ "QUcsTElRVUlCQVNFLENPTlRFWFRTLExBQkVMUwowMDAwMDAwMDAwMDAwMSxzdWRhcnNoYW4sY2xhc3NwYXRoOmNvbmZpZy9saXF1aWJhc2UvY2hhbm"
    			+ "dlbG9nLzAwMDAwMDAwMDAwMDAwX2luaXRpYWxfc2NoZW1hLnhtbCwiMjAxNi0wOC0wNSAxMjo1MTo0NyIsMSxFWEVDVVRFRCw3OmQyMjA2MzRkYWU3YWJm"
    			+ "MGQ5YjZjZGI4NmNjNWI3ZDc2LCJjcmVhdGVUYWJsZSwgY3JlYXRlSW5kZXggKHgyKSwgY3JlYXRlVGFibGUgKHgyKSwgYWRkUHJpbWFyeUtleSwgY3JlYXRlVGFibGUsI"
    			+ "GFkZEZvcmVpZ25LZXlDb25zdHJhaW50ICh4MyksIGxvYWREYXRhLCBkcm9wRGVmYXVsdFZhbHVlLCBsb2FkRGF0YSAoeDIpLCBjcmVhdGVUYWJsZS"
    			+ "AoeDIpLCBhZGRQcmltYXJ5S2V5LCBjcmVhdGVJbmRleCAoeDIpLCBhZGRGb3JlaWduS2V5Q29uc3RyYWludCIsLE5VTEwsMy40LjIsTlVMTCxOVUxMCjIwMTYwO"
    			+ "DA1MDYxMTQ2LTEsamhpcHN0ZXIsY2xhc3NwYXRoOmNvbmZpZy9saXF1aWJhc2UvY2hhbmdlbG9nLzIwMTYwODA1MDYxMTQ2X2FkZGVkX2VudGl0eV9Qc3B0b3Bz"
    			+ "dWJzLnhtbCwiMjAxNi0wOC0wNSAxNTozMzo0MSIsMixFWEVDVVRFRCw3OmJkZGIyMDc3ODc5MjBmODMzMWFhODMwYTliZDRhZmUwLGNyZWF0ZVRhYmxlLCxOVUxM"
    			+ "LDMuNC4yLE5VTEwsTlVMTAoyMDE2MDgxMDA1MzQzMC0xLGpoaXBzdGVyLGNsYXNzcGF0aDpjb25maWcvbGlxdWliYXNlL2NoYW5nZWxvZy8yMDE2MDgxMDA1MzQz"
    			+ "MF9hZGRlZF9lbnRpdHlfTG9jYW5hbHlzaXMueG1sLCIyMDE2LTA4LTEwIDExOjI3OjEwIiwzLEVYRUNVVEVELDc6OWFmNjJkZTJkMzMzM2YzZDYyYWM4ODk5Y2I3YmZ"
    			+ "jMjgsY3JlYXRlVGFibGUsLE5VTEwsMy40LjIsTlVMTCxOVUxMCjIwMTYwODEwMTU1MTQ0LTEsamhpcHN0ZXIsY2xhc3NwYXRoOmNvbmZpZy9saXF1aWJhc2UvY2hhbmd"
    			+ "lbG9nLzIwMTYwODEwMTU1MTQ0X2FkZGVkX2VudGl0eV9Mb2NhdGlvbmxvb2t1cC54bWwsIjIwMTYtMDgtMTAgMjE6MzE6NTAiLDQsRVhFQ1VURUQsNzoyYWR"
    			+ "hZWNlNzVlY2JiZDM0MGRhYzIwMmY3OGFmZjg4YSxjcmVhdGVUYWJsZSwsTlVMTCwzLjQuMixOVUxMLE5VTEwK"; */
    	
    		PAProject paProject = paProjectService.findOne(projectId);
    		try {
    			Configuration configuration = hdfsFileOperationsService.init("pervazive");
    			String dirName = configuration.get("fs.defaultFS")+"/"+paProject.getPaorgpro().getOrganization()+"/"+paProject.getProjectname()+"/ppa-repo/traindata/"+fileName;
    		
    			FSDataInputStream in = hdfsFileOperationsService.readFile(dirName, configuration);
    			response.addHeader("Content-disposition", "attachment;filename="+fileName+"");
    			response.setContentType("txt/csv");

    			// Copy the stream to the response's output stream.
    			//IOUtils.copy(in, response.getOutputStream());
    			
    			// following 2 lines added for test only
    			// ****************************************************************************************************
    			InputStream stream = new ByteArrayInputStream(IOUtils.toString(in, "UTF-8").getBytes()); 
    			IOUtils.copy(stream, response.getOutputStream());
    			// ****************************************************************************************************
    			response.flushBuffer();
    		} catch (IOException io){
    			io.printStackTrace();
    		}
    }
    
    @DeleteMapping("/delete/{projectid}/{fileName:.+}")
    @Timed
    @Secured(AuthoritiesConstants.SUPERADMIN)
    public ResponseEntity<Void> deleteFile(@PathVariable("projectid") Long projectId, @PathVariable("fileName") String fileName) {
    	
    	PAProject paProject = paProjectService.findOne(projectId);
		try {
			Configuration configuration = hdfsFileOperationsService.init("pervazive");
			String dirName = configuration.get("fs.defaultFS")+"/"+paProject.getPaorgpro().getOrganization()+"/"+paProject.getProjectname()+"/ppa-repo/traindata/"+fileName;
			hdfsFileOperationsService.deleteFile(dirName, configuration);
		} catch (IOException io){
			io.printStackTrace();
		}
		//return ResponseEntity.ok().body(HeaderUtil.pushMessage(fileName+" deleted"));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("Deleted", fileName)).build();
    }
    
    @GetMapping("/readfilelist/{projectName}")
    @Timed
    public ResponseEntity<List<FileStatusDTO>> getFileList(@PathVariable String projectName)
        throws URISyntaxException {
    	
    	PAProject paProject = paProjectService.findProjectByName(projectName);
    	String dirName = "/user/pervazive/"+paProject.getPaorgpro().getOrganization()+"/"+projectName+"/ppa-repo/traindata";
    	//dirName = dirName.replace(' ', '+');
    	
    	List<FileStatus> fileList = new ArrayList<FileStatus>();
    	FileStatus[] fileStatus = null;
        try {
        	fileStatus =hdfsFileOperationsService.readFileList(dirName, hdfsFileOperationsService.init("pervazive"));
        	for (FileStatus fileStat : fileStatus)  {
				if(fileStat.isFile()) {
					fileList.add(fileStat);
				}
        	}
         } catch (IOException e) {
    	   new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
        

        List<FileStatusDTO> fileStatusDTO = fileList.stream()
                .map(FileStatusDTO::new)
                .collect(Collectors.toList());
       
        return new ResponseEntity<List<FileStatusDTO>>(fileStatusDTO, HttpStatus.OK);
    }

    

}
