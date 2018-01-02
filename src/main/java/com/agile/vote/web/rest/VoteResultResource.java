package com.agile.vote.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agile.vote.domain.VoteResult;
import com.agile.vote.service.VoteResultService;
import com.agile.vote.web.rest.errors.BadRequestAlertException;
import com.agile.vote.web.rest.util.HeaderUtil;
import com.agile.vote.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VoteResult.
 */
@RestController
@RequestMapping("/api")
public class VoteResultResource {

    private final Logger log = LoggerFactory.getLogger(VoteResultResource.class);

    private static final String ENTITY_NAME = "voteResult";

    private final VoteResultService voteResultService;

    public VoteResultResource(VoteResultService voteResultService) {
        this.voteResultService = voteResultService;
    }

    /**
     * POST  /vote-results : Create a new voteResult.
     *
     * @param voteResult the voteResult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voteResult, or with status 400 (Bad Request) if the voteResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vote-results")
    @Timed
    public ResponseEntity<VoteResult> createVoteResult(@Valid @RequestBody VoteResult voteResult) throws URISyntaxException {
        log.debug("REST request to save VoteResult : {}", voteResult);
        if (voteResult.getId() != null) {
            throw new BadRequestAlertException("A new voteResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoteResult result = voteResultService.save(voteResult);
        return ResponseEntity.created(new URI("/api/vote-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vote-results : Updates an existing voteResult.
     *
     * @param voteResult the voteResult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voteResult,
     * or with status 400 (Bad Request) if the voteResult is not valid,
     * or with status 500 (Internal Server Error) if the voteResult couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vote-results")
    @Timed
    public ResponseEntity<VoteResult> updateVoteResult(@Valid @RequestBody VoteResult voteResult) throws URISyntaxException {
        log.debug("REST request to update VoteResult : {}", voteResult);
        if (voteResult.getId() == null) {
            return createVoteResult(voteResult);
        }
        VoteResult result = voteResultService.save(voteResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voteResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vote-results : get all the voteResults.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of voteResults in body
     */
    @GetMapping("/vote-results")
    @Timed
    public ResponseEntity<List<VoteResult>> getAllVoteResults(Pageable pageable) {
        log.debug("REST request to get a page of VoteResults");
        Page<VoteResult> page = voteResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vote-results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vote-results/:id : get the "id" voteResult.
     *
     * @param id the id of the voteResult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voteResult, or with status 404 (Not Found)
     */
    @GetMapping("/vote-results/{id}")
    @Timed
    public ResponseEntity<VoteResult> getVoteResult(@PathVariable Long id) {
        log.debug("REST request to get VoteResult : {}", id);
        VoteResult voteResult = voteResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(voteResult));
    }

    /**
     * DELETE  /vote-results/:id : delete the "id" voteResult.
     *
     * @param id the id of the voteResult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vote-results/{id}")
    @Timed
    public ResponseEntity<Void> deleteVoteResult(@PathVariable Long id) {
        log.debug("REST request to delete VoteResult : {}", id);
        voteResultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
