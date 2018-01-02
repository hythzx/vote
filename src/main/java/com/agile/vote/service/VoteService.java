package com.agile.vote.service;

import com.agile.vote.domain.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Vote.
 */
public interface VoteService {

    /**
     * Save a vote.
     *
     * @param vote the entity to save
     * @return the persisted entity
     */
    Vote save(Vote vote);

    /**
     * Get all the votes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Vote> findAll(Pageable pageable);

    /**
     * Get the "id" vote.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Vote findOne(Long id);

    /**
     * Delete the "id" vote.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
