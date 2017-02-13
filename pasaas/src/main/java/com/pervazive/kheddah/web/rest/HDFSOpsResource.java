package com.pervazive.kheddah.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FsStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.service.HDFSFileOperationsService;
import com.pervazive.kheddah.service.dto.FileStatusDTO;
import com.pervazive.kheddah.service.dto.PAOrganizationDTO;

/**
 * REST controller for managing PANotification.
 */
@RestController
@RequestMapping("/api")
public class HDFSOpsResource {
	
	@Inject
	private HDFSFileOperationsService hdfsFileOperationsService;

    private final Logger log = LoggerFactory.getLogger(HDFSOpsResource.class);
 
    /**
     * TODO all parms to be updated
     * GET  /p-a-notifications : get all the pANotifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pANotifications in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @PostMapping("/mkdir/{newDir}")
    @Timed
    public ResponseEntity<String> createNewDir(@PathVariable String newDir)
        throws URISyntaxException {
       try {
        	hdfsFileOperationsService.mkdir(newDir, hdfsFileOperationsService.init(null, null, ""));
		} catch (IOException e) {
			new ResponseEntity<String>("Error :"+e.getMessage() ,HttpStatus.BAD_REQUEST);
		}
       
        return new ResponseEntity<String>("Successfully Created",HttpStatus.OK);
    }
    
    @PostMapping("/pushfiles")
    @Timed
    public ResponseEntity<String> createNewFile(@RequestPart("file") @Valid MultipartFile[] file)
        throws URISyntaxException {
        try {
        	hdfsFileOperationsService.addMultipleFiles(file, "/user/pervazive/newfromapi/", hdfsFileOperationsService.init(null, null, ""));
		} catch (IOException e) {
			new ResponseEntity<String>("Error :"+e.getMessage() ,HttpStatus.BAD_REQUEST);
		}
       
       return new ResponseEntity<String>("Successfully uploaded",HttpStatus.OK);
    }
    
    @PostMapping("/pushfile")
    @Timed
    public ResponseEntity<String> createNewFile(@RequestPart("file") @Valid MultipartFile file)
        throws URISyntaxException {
        try {
        	hdfsFileOperationsService.addFile(file, "/user/pervazive/newfromapi/", hdfsFileOperationsService.init(null, null, ""));
		} catch (IOException e) {
			new ResponseEntity<String>("Error :"+e.getMessage() ,HttpStatus.BAD_REQUEST);
		}
       
        return new ResponseEntity<String>("Successfully uploaded",HttpStatus.OK);
    }
    
    @GetMapping("/readfilelist/{queryDir}")
    @Timed
    public ResponseEntity<List<FileStatusDTO>> getFileList(@PathVariable String queryDir)
        throws URISyntaxException {
    	
    	List<FileStatus> fileList = new ArrayList<FileStatus>();
    	FileStatus[] fileStatus = null;
        try {
        	fileStatus =hdfsFileOperationsService.readFileList(queryDir, hdfsFileOperationsService.init(null, null, ""));
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
