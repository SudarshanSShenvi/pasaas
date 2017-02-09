package com.pervazive.kheddah.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PAProjectRepository;
import com.pervazive.kheddah.repository.UserRepository;
import com.pervazive.kheddah.service.PAProjectService;

/**
 * Service Implementation for managing PAProject.
 */
@Service
@Transactional
public class PAProjectServiceImpl implements PAProjectService{

    private final Logger log = LoggerFactory.getLogger(PAProjectServiceImpl.class);
    
    @Inject
    private PAProjectRepository pAProjectRepository;

    @Inject
    private UserRepository userRepository;
    
    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    
    /**
     * Save a pAProject.
     *
     * @param pAProject the entity to save
     * @return the persisted entity
     */
    public PAProject save(PAProject pAProject) {
        log.debug("Request to save PAProject : {}", pAProject);
        PAProject result = pAProjectRepository.save(pAProject);
        return result;
    }

    /**
     *  Get all the pAProjects.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAProject> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PAProjects");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        //Page<PAProject> result = pAProjectRepository.findAllPAUsers(pageable);
        Page<PAProject> result = pAProjectRepository.findByPaorgproIn(organizationames, pageable);
        return result;
    }
    
    /**
     *  Get all the pAProjects.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAProject> findAll(Pageable pageable, List<PAOrganization> organizationames) {
        log.debug("Request to get all PAProjects");
        Page<PAProject> result = pAProjectRepository.findByPaorgproIn(organizationames, pageable);
        return result;
    }

    /**
     *  Get one pAProject by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAProject findOne(Long id) {
        log.debug("Request to get PAProject : {}", id);
        PAProject pAProject = pAProjectRepository.findOne(id);
        return pAProject;
    }

    /**
     *  Delete the  pAProject by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAProject : {}", id);
        pAProjectRepository.delete(id);
    }
    

	@Override
	public void updateProjectwithUsers(Long id, String projectname, String description,
			PAOrganization paorgpro, Set<String> pausers) {
		Optional.of(pAProjectRepository
                .findOne(id))
                .ifPresent(project -> {
                	project.setId(id);
                	project.setDescription(description);
                	project.setProjectname(projectname);
                	project.setPaorgpro(paorgpro);
                	
                	 Set<User> managedUsers = project.getPausers();
                	 managedUsers.clear();
                	 if(pausers != null ){
                	 pausers.forEach(
                		pauser -> managedUsers.add(userRepository.findOneByLoginName(pauser))
                     );
                	 }
                	 log.debug("Changed Information for Organization: {}", project);
                });
		
	}
	
	 @Transactional(readOnly = true)
	    public PAProject getProjectWithUser(Long id) {
		 PAProject pAProject = pAProjectRepository.findOne(id);
		 pAProject.getPausers().size(); // eagerly load the association
	    return pAProject;
	    }
}
