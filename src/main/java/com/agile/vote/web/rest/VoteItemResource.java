package com.agile.vote.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agile.vote.domain.VoteItem;
import com.agile.vote.service.VoteItemService;
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
 * REST controller for managing VoteItem.
 */
@RestController
@RequestMapping("/api")
public class VoteItemResource {

    private final Logger log = LoggerFactory.getLogger(VoteItemResource.class);

    private static final String ENTITY_NAME = "voteItem";

    private final VoteItemService voteItemService;

    public VoteItemResource(VoteItemService voteItemService) {
        this.voteItemService = voteItemService;
    }

    /**
     * POST  /vote-items : Create a new voteItem.
     *
     * @param voteItem the voteItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voteItem, or with status 400 (Bad Request) if the voteItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vote-items")
    @Timed
    public ResponseEntity<VoteItem> createVoteItem(@Valid @RequestBody VoteItem voteItem) throws URISyntaxException {
        log.debug("REST request to save VoteItem : {}", voteItem);
        if (voteItem.getId() != null) {
            throw new BadRequestAlertException("A new voteItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoteItem result = voteItemService.save(voteItem);
        return ResponseEntity.created(new URI("/api/vote-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vote-items : Updates an existing voteItem.
     *
     * @param voteItem the voteItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voteItem,
     * or with status 400 (Bad Request) if the voteItem is not valid,
     * or with status 500 (Internal Server Error) if the voteItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vote-items")
    @Timed
    public ResponseEntity<VoteItem> updateVoteItem(@Valid @RequestBody VoteItem voteItem) throws URISyntaxException {
        log.debug("REST request to update VoteItem : {}", voteItem);
        if (voteItem.getId() == null) {
            return createVoteItem(voteItem);
        }
        VoteItem result = voteItemService.save(voteItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voteItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vote-items : get all the voteItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of voteItems in body
     */
    @GetMapping("/vote-items")
    @Timed
    public ResponseEntity<List<VoteItem>> getAllVoteItems(Pageable pageable) {
        log.debug("REST request to get a page of VoteItems");
        Page<VoteItem> page = voteItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vote-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vote-items/:id : get the "id" voteItem.
     *
     * @param id the id of the voteItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voteItem, or with status 404 (Not Found)
     */
    @GetMapping("/vote-items/{id}")
    @Timed
    public ResponseEntity<VoteItem> getVoteItem(@PathVariable Long id) {
        log.debug("REST request to get VoteItem : {}", id);
        VoteItem voteItem = voteItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(voteItem));
    }

    /**
     * DELETE  /vote-items/:id : delete the "id" voteItem.
     *
     * @param id the id of the voteItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vote-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteVoteItem(@PathVariable Long id) {
        log.debug("REST request to delete VoteItem : {}", id);
        voteItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
