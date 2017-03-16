package com.pervazive.kheddah.service.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pervazive.kheddah.domain.PABusinessPlan;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.domain.enumeration.PAStatus;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PAProjectRepository;
import com.pervazive.kheddah.repository.UserRepository;
import com.pervazive.kheddah.service.PAOrganizationService;

/**
 * Service Implementation for managing PAOrganization.
 */
@Service
@Transactional
public class PAOrganizationServiceImpl implements PAOrganizationService{

    private final Logger log = LoggerFactory.getLogger(PAOrganizationServiceImpl.class);
    
    @Inject
    private PAOrganizationRepository pAOrganizationRepository;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private PAOrganizationRepository paOrganizationRepository;

    @Inject
    private PAProjectRepository pAProjectRepository;
    /**
     * Save a pAOrganization.
     *
     * @param pAOrganization the entity to save
     * @return the persisted entity
     */
    public PAOrganization save(PAOrganization pAOrganization) {
        log.debug("Request to save PAOrganization : {}", pAOrganization);
        PAOrganization result = pAOrganizationRepository.save(pAOrganization);
        return result;
    }

    /**
     *  Get all the pAOrganizations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAOrganization> findAll(Pageable pageable) {
        log.debug("Request to get all PAOrganizations");
        //Page<PAOrganization> result = pAOrganizationRepository.findAll(pageable);
        Page<PAOrganization> result = pAOrganizationRepository.findAllPAUsers(pageable);
        return result;
    }
    
    @Transactional(readOnly = true) 
    public Page<PAOrganization> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PAOrganizations");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        log.debug("PAOrganizations: SIZE ======================= "+organizationames.size());
        //Page<PAOrganization> result = pAOrganizationRepository.findAll(pageable);
        Page<PAOrganization> result = new PageImpl<PAOrganization>(organizationames);
        return result;
    }

    @Transactional(readOnly = true) 
    public Page<PAOrganization> findAll(Pageable pageable, List<PAOrganization> organizationames) {
        log.debug("PAOrganizations: SIZE ======================= "+organizationames.size());
        //Page<PAOrganization> result = pAOrganizationRepository.findAll(pageable);
        Page<PAOrganization> result = new PageImpl<PAOrganization>(organizationames);
        return result;
    }
    
    @Transactional(readOnly = true) 
    public PAOrganization findOrganizationByName(String paOrganization) {
        PAOrganization result = pAOrganizationRepository.findByOrganization(paOrganization);
        return result;
    }
    
    /**
     *  Get one pAOrganization by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAOrganization findOne(Long id) {
        log.debug("Request to get PAOrganization : {}", id);
        PAOrganization pAOrganization = pAOrganizationRepository.findOne(id);
        return pAOrganization;
    }

    /**
     *  Delete the  pAOrganization by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAOrganization : {}", id);
        pAOrganizationRepository.delete(id);
    }
    
    
    public void updateOrganizationwithUsers(Long id, String organizationname, ZonedDateTime validfrom, ZonedDateTime validto,
			PAStatus pastatus, PABusinessPlan pabporg, Set<String> pausers) {

            Optional.of(pAOrganizationRepository
                .findOne(id))
                .ifPresent(organization -> {
                	organization.setOrganization(organizationname);
                	organization.setValidfrom(validfrom);
                	organization.setValidto(validto);
                	organization.setPastatus(pastatus);
                	organization.setPabporg(pabporg);
                	
                	 Set<User> managedUsers = organization.getPAUsers();
                	 managedUsers.clear();
                	 if(pausers != null ){
                	 pausers.forEach(
                		pauser -> managedUsers.add(userRepository.findOneByLoginName(pauser))
                     );
                	 }
                	 
                	 /*Set<PAProject> managedProjects = organization.getPaproorgs();
                	 managedProjects.clear();
                	 if(paprojects != null ){
                     paprojects.forEach(
                			 paproject -> managedProjects.add(pAProjectRepository.findByProjectName(paproject))
                     );
                	 }*/
                	
                	log.debug("Changed Information for Organization: {}", organization);
                });
    }
    
    @Transactional(readOnly = true)
    public PAOrganization getOrganizationWithUser(Long id) {
    	PAOrganization pAOrganization = pAOrganizationRepository.findOne(id);
    	pAOrganization.getPAUsers().size(); // eagerly load the association
    	return pAOrganization;
    }
    
}
