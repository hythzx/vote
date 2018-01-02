package com.agile.vote.service.impl;

import com.agile.vote.service.VoteResultService;
import com.agile.vote.domain.VoteResult;
import com.agile.vote.repository.VoteResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing VoteResult.
 */
@Service
@Transactional
public class VoteResultServiceImpl implements VoteResultService{

    private final Logger log = LoggerFactory.getLogger(VoteResultServiceImpl.class);

    private final VoteResultRepository voteResultRepository;

    public VoteResultServiceImpl(VoteResultRepository voteResultRepository) {
        this.voteResultRepository = voteResultRepository;
    }

    /**
     * Save a voteResult.
     *
     * @param voteResult the entity to save
     * @return the persisted entity
     */
    @Override
    public VoteResult save(VoteResult voteResult) {
        log.debug("Request to save VoteResult : {}", voteResult);
        return voteResultRepository.save(voteResult);
    }

    /**
     * Get all the voteResults.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VoteResult> findAll(Pageable pageable) {
        log.debug("Request to get all VoteResults");
        return voteResultRepository.findAll(pageable);
    }

    /**
     * Get one voteResult by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VoteResult findOne(Long id) {
        log.debug("Request to get VoteResult : {}", id);
        return voteResultRepository.findOne(id);
    }

    /**
     * Delete the voteResult by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoteResult : {}", id);
        voteResultRepository.delete(id);
    }
}
