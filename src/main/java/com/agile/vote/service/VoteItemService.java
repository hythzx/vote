package com.agile.vote.service;

import com.agile.vote.domain.VoteItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VoteItem.
 */
public interface VoteItemService {

    /**
     * Save a voteItem.
     *
     * @param voteItem the entity to save
     * @return the persisted entity
     */
    VoteItem save(VoteItem voteItem);

    /**
     * Get all the voteItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VoteItem> findAll(Pageable pageable, Long voteId);

    /**
     * Get the "id" voteItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VoteItem findOne(Long id);

    /**
     * Delete the "id" voteItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
