package com.pervazive.kheddah.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.fs.FileStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.repository.UserRepository;
import com.pervazive.kheddah.security.AuthoritiesConstants;
import com.pervazive.kheddah.security.SecurityUtils;
import com.pervazive.kheddah.service.HDFSFileOperationsService;
import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.service.PAProjectService;
import com.pervazive.kheddah.service.dto.FileStatusDTO;
import com.pervazive.kheddah.service.dto.PAProjectDTO;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing PAProject.
 */
@RestController
@RequestMapping("/api")
public class PAProjectResource {

    private final Logger log = LoggerFactory.getLogger(PAProjectResource.class);
        
    @Inject
    private PAProjectService pAProjectService;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private PAOrganizationService paOrganizationService;
    
	@Inject
	private HDFSFileOperationsService hdfsFileOperationsService;


    /**
     * POST  /p-a-projects : Create a new pAProject.
     *
     * @param pAProject the pAProject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAProject, or with status 400 (Bad Request) if the pAProject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws IOException 
     */
    @PostMapping("/p-a-projects")
    @Timed
    public ResponseEntity<PAProject> createPAProject(@RequestBody PAProject pAProject) throws URISyntaxException, IOException {
        log.debug("REST request to save PAProject : {}", pAProject);
        if (pAProject.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAProject", "idexists", "A new pAProject cannot already have an ID")).body(null);
        }
        if (pAProject.getProjectname() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAProject", "NotNull", "Field Project Name cannot be empty!")).body(null);
        }
        if (pAProject.getPaorgpro() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAProject", "NotNull", "Field Organization cannot be empty!")).body(null);
        }
        
        Set<User> pausers = new HashSet<User>();
        User currentUser = userRepository.findOneByLoginName(SecurityUtils.getCurrentUserLogin());
        pausers.add(currentUser);
        pAProject.setPausers(pausers);
        PAProject result = pAProjectService.save(pAProject);
        //On successful project Creation - create directory structure in hadoop
        hdfsFileOperationsService.mkdirProjectStructure(result.getProjectname(), result.getPaorgpro().getOrganization());
        
        return ResponseEntity.created(new URI("/api/p-a-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAProject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-projects : Updates an existing pAProject.
     *
     * @param pAProject the pAProject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAProject,
     * or with status 400 (Bad Request) if the pAProject is not valid,
     * or with status 500 (Internal Server Error) if the pAProject couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-projects")
    @Timed
    public ResponseEntity<PAProjectDTO> updatePAProject(@RequestBody PAProjectDTO pAProjectDto) throws URISyntaxException {
        log.debug("REST request to update PAProject : {}", pAProjectDto);
        /*if (pAProject.getId() == null) {
            return createPAProject(pAProject);
        }*/
        PAProject paProject = new PAProject();
        paProject.setPastatus(pAProjectDto.getPastatus());
        paProject.setProjectname(pAProjectDto.getProjectname());
        paProject.setPaorgpro(pAProjectDto.getPaorgpro());
        paProject.setDescription(pAProjectDto.getDescription());
        paProject.setId(pAProjectDto.getId());
        
        paProject.setCreatedBy(pAProjectDto.getCreatedBy());
        paProject.setCreatedDate(pAProjectDto.getCreatedDate());
        paProject.setLastModifiedBy(pAProjectDto.getLastModifiedBy());
        paProject.setLastModifiedDate(pAProjectDto.getLastModifiedDate());
        
        paProject.setObjectentity(pAProjectDto.getObjectentity());
        paProject.setTimeseriesentity(pAProjectDto.getTimeseriesentity());
        paProject.setFeedfirstlineheader(pAProjectDto.isFeedfirstlineheader());
        paProject.setFeedindateformat(pAProjectDto.getFeedindateformat());
        paProject.setFeedoutdateformat(pAProjectDto.getFeedoutdateformat());
        paProject.setFeedskipindexes(pAProjectDto.getFeedskipindexes());
        paProject.setRollindateformat(pAProjectDto.getRollindateformat());
        paProject.setRolloutdateformat(pAProjectDto.getRolloutdateformat());
        paProject.setRollseriesend(pAProjectDto.getRollseriesend());
        paProject.setRollseriesgroupindex(pAProjectDto.getRollseriesgroupindex());
        paProject.setRollseriesnxt(pAProjectDto.getRollseriesnxt());
        paProject.setRollseriesstart(pAProjectDto.getRollseriesstart());
        paProject.setPatternfldindex(pAProjectDto.getPatternfldindex());
        paProject.setPatternintervalthreshhold(pAProjectDto.getPatternintervalthreshhold());
        paProject.setPatternpredictinterval(pAProjectDto.getPatternpredictinterval());
        paProject.setPatterntraininterval(pAProjectDto.getPatterntraininterval());
       
        Set<String> userList = pAProjectDto.getPausers();
        Set<User> userObj = new HashSet<User>();
        Iterator<String> iterator = userList.iterator(); 
        // check values
        while (iterator.hasNext()){
    	   Optional<User> optUser = userRepository.findOneByLogin(iterator.next());
    	   if (optUser.isPresent()) userObj.add(optUser.get()); 
        }
        paProject.setPausers(userObj);
        
        PAProject result = pAProjectService.save(paProject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAProject", pAProjectDto.getId().toString()))
            .body(new PAProjectDTO(result));
        
        /*pAProjectService.updateProjectwithUsers(pAProjectDto.getId(), pAProjectDto.getProjectname(), pAProjectDto.getDescription(), 
        		pAProjectDto.getPaorgpro(), pAProjectDto.getPausers());
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("pAProject.updated", pAProjectDto.getProjectname()))
            .body(new PAProjectDTO(pAProjectService.getProjectWithUser(pAProjectDto.getId())));*/

    }

    /**
     * GET  /p-a-projects : get all the pAProjects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAProjects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-projects")
    @Timed
    public ResponseEntity<List<PAProjectDTO>> getAllPAProjects(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAProjects");
        
        if((PAOrganization) request.getSession().getAttribute("s_organization") == null) 
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAProject", "Organization missing", "Create one to proceed")).body(null);
        
        Page<PAProject> page = pAProjectService.findAll(pageable, (PAOrganization) request.getSession().getAttribute("s_organization") );
        List<PAProjectDTO> paProjectDTO = page.getContent().stream()
            .map(PAProjectDTO::new)
            .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-projects");
        return new ResponseEntity<>(paProjectDTO, headers, HttpStatus.OK);
        
        //ORIGINAL IMPLEMENTATION
        /*Page<PAProject> page = pAProjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);*/
    }
    
    /**
     * GET  /p-a-projects : get all the pAProjects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAProjects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-projects/suops")
    @Timed
    @Secured(AuthoritiesConstants.SUPERADMIN)
    public ResponseEntity<List<PAProjectDTO>> getAllPAProjectsSU(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAProjects");
        
        Page<PAProject> page = pAProjectService.findAllSU(pageable);
        List<PAProjectDTO> paProjectDTO = page.getContent().stream()
            .map(PAProjectDTO::new)
            .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-projects");
        return new ResponseEntity<>(paProjectDTO, headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-projects/:id : get the "id" pAProject.
     *
     * @param id the id of the pAProject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAProject, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-projects/{id}")
    @Timed
    public ResponseEntity<PAProjectDTO> getPAProject(@PathVariable Long id) {
        log.debug("REST request to get PAProject : {}", id);
        PAProject pAProject = pAProjectService.findOne(id);
        
        String dirNameT = "/user/pervazive/"+pAProject.getPaorgpro().getOrganization()+"/"+pAProject.getProjectname()+"/ppa-repo/traindata";
        
        String dirNameP = "/user/pervazive/"+pAProject.getPaorgpro().getOrganization()+"/"+pAProject.getProjectname()+"/ppa-repo/predictdata";     
        List<FileStatus> fileListT = new ArrayList<FileStatus>();
    	FileStatus[] fileStatusT = null;
        try {
        	fileStatusT =hdfsFileOperationsService.readFileList(dirNameT, hdfsFileOperationsService.init("pervazive"));
        	for (FileStatus fileStat : fileStatusT)  {
				if(fileStat.isFile()) {
					fileListT.add(fileStat);
				}
        	}
         } catch (IOException e) {
    	   new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
        
        List<FileStatusDTO> fileStatusDTOT = fileListT.stream()
                .map(FileStatusDTO::new)
                .collect(Collectors.toList());
        
        List<FileStatus> fileListP = new ArrayList<FileStatus>();
    	FileStatus[] fileStatusP = null;
        try {
        	fileStatusP =hdfsFileOperationsService.readFileList(dirNameP, hdfsFileOperationsService.init("pervazive"));
        	for (FileStatus fileStat : fileStatusP)  {
				if(fileStat.isFile()) {
					fileListP.add(fileStat);
				}
        	}
         } catch (IOException e) {
    	   new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
        
        List<FileStatusDTO> fileStatusDTOP = fileListP.stream()
                .map(FileStatusDTO::new)
                .collect(Collectors.toList());
        
        PAProjectDTO paProjectDTO = new PAProjectDTO(pAProject);
        paProjectDTO.setFileStatusDTOT(fileStatusDTOT);
        paProjectDTO.setFileStatusDTOP(fileStatusDTOP);
        
        
        return Optional.ofNullable(paProjectDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
   /* @GetMapping("/p-a-projects/{opts}/{id}")
    @Timed
    public ResponseEntity<PAProjectDTO> getPAProject(@PathVariable Long id, @PathVariable String opts) {
        log.debug("REST request to get PAProject : {}", id);
        PAProject pAProject = pAProjectService.findOne(id);
        
        String dirName = "/user/pervazive/"+pAProject.getPaorgpro().getOrganization()+"/"+pAProject.getProjectname()+"/ppa-repo/traindata";
        
        if(opts.equals("P"))
        	dirName = "/user/pervazive/"+pAProject.getPaorgpro().getOrganization()+"/"+pAProject.getProjectname()+"/ppa-repo/predictdata/";        
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
        
        PAProjectDTO paProjectDTO = new PAProjectDTO(pAProject);
        paProjectDTO.setFileStatusDTO(fileStatusDTO);
        return Optional.ofNullable(paProjectDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    /**
     * DELETE  /p-a-projects/:id : delete the "id" pAProject.
     *
     * @param id the id of the pAProject to delete
     * @return the ResponseEntity with status 200 (OK)
     * @throws IOException 
     */
    @DeleteMapping("/p-a-projects/{id}")
    @Timed
    public ResponseEntity<Void> deletePAProject(@PathVariable Long id) throws IOException {
        log.debug("REST request to delete PAProject : {}", id);
        //On successful project Deletion - delete directory structure in hadoop
        hdfsFileOperationsService.removeProjectStructure(pAProjectService.getProjectWithUser(id).getProjectname(), pAProjectService.getProjectWithUser(id).getPaorgpro().getOrganization());
       
        pAProjectService.delete(id);
        
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAProject", id.toString())).build();
    }

}
