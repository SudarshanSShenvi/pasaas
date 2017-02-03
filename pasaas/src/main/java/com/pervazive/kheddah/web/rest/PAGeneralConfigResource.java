package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAGeneralConfig;
import com.pervazive.kheddah.service.PAGeneralConfigService;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PAGeneralConfig.
 */
@RestController
@RequestMapping("/api")
public class PAGeneralConfigResource {

    private final Logger log = LoggerFactory.getLogger(PAGeneralConfigResource.class);
        
    @Inject
    private PAGeneralConfigService pAGeneralConfigService;

    /**
     * POST  /p-a-general-configs : Create a new pAGeneralConfig.
     *
     * @param pAGeneralConfig the pAGeneralConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAGeneralConfig, or with status 400 (Bad Request) if the pAGeneralConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-general-configs")
    @Timed
    public ResponseEntity<PAGeneralConfig> createPAGeneralConfig(@RequestBody PAGeneralConfig pAGeneralConfig) throws URISyntaxException {
        log.debug("REST request to save PAGeneralConfig : {}", pAGeneralConfig);
        if (pAGeneralConfig.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAGeneralConfig", "idexists", "A new pAGeneralConfig cannot already have an ID")).body(null);
        }
        PAGeneralConfig result = pAGeneralConfigService.save(pAGeneralConfig);
        return ResponseEntity.created(new URI("/api/p-a-general-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAGeneralConfig", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-general-configs : Updates an existing pAGeneralConfig.
     *
     * @param pAGeneralConfig the pAGeneralConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAGeneralConfig,
     * or with status 400 (Bad Request) if the pAGeneralConfig is not valid,
     * or with status 500 (Internal Server Error) if the pAGeneralConfig couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-general-configs")
    @Timed
    public ResponseEntity<PAGeneralConfig> updatePAGeneralConfig(@RequestBody PAGeneralConfig pAGeneralConfig) throws URISyntaxException {
        log.debug("REST request to update PAGeneralConfig : {}", pAGeneralConfig);
        if (pAGeneralConfig.getId() == null) {
            return createPAGeneralConfig(pAGeneralConfig);
        }
        PAGeneralConfig result = pAGeneralConfigService.save(pAGeneralConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAGeneralConfig", pAGeneralConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-general-configs : get all the pAGeneralConfigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAGeneralConfigs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-general-configs")
    @Timed
    public ResponseEntity<List<PAGeneralConfig>> getAllPAGeneralConfigs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAGeneralConfigs");
        Page<PAGeneralConfig> page = pAGeneralConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-general-configs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-general-configs/:id : get the "id" pAGeneralConfig.
     *
     * @param id the id of the pAGeneralConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAGeneralConfig, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-general-configs/{id}")
    @Timed
    public ResponseEntity<PAGeneralConfig> getPAGeneralConfig(@PathVariable Long id) {
        log.debug("REST request to get PAGeneralConfig : {}", id);
        PAGeneralConfig pAGeneralConfig = pAGeneralConfigService.findOne(id);
        return Optional.ofNullable(pAGeneralConfig)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-general-configs/:id : delete the "id" pAGeneralConfig.
     *
     * @param id the id of the pAGeneralConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-general-configs/{id}")
    @Timed
    public ResponseEntity<Void> deletePAGeneralConfig(@PathVariable Long id) {
        log.debug("REST request to delete PAGeneralConfig : {}", id);
        pAGeneralConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAGeneralConfig", id.toString())).build();
    }

}
