package com.agile.vote.service;

import com.agile.vote.domain.VoteResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VoteResult.
 */
public interface VoteResultService {

    /**
     * Save a voteResult.
     *
     * @param voteResult the entity to save
     * @return the persisted entity
     */
    VoteResult save(VoteResult voteResult);

    /**
     * Get all the voteResults.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VoteResult> findAll(Pageable pageable);

    /**
     * Get the "id" voteResult.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VoteResult findOne(Long id);

    /**
     * Delete the "id" voteResult.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
