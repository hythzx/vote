package com.agile.vote.service.impl;

import com.agile.vote.service.VoteItemService;
import com.agile.vote.domain.VoteItem;
import com.agile.vote.repository.VoteItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing VoteItem.
 */
@Service
@Transactional
public class VoteItemServiceImpl implements VoteItemService{

    private final Logger log = LoggerFactory.getLogger(VoteItemServiceImpl.class);

    private final VoteItemRepository voteItemRepository;

    public VoteItemServiceImpl(VoteItemRepository voteItemRepository) {
        this.voteItemRepository = voteItemRepository;
    }

    /**
     * Save a voteItem.
     *
     * @param voteItem the entity to save
     * @return the persisted entity
     */
    @Override
    public VoteItem save(VoteItem voteItem) {
        log.debug("Request to save VoteItem : {}", voteItem);
        return voteItemRepository.save(voteItem);
    }

    /**
     * Get all the voteItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VoteItem> findAll(Pageable pageable, Long voteId) {
        log.debug("Request to get all VoteItems");
        if (voteId == null) {
            return voteItemRepository.findAll(pageable);
        }else {
            return voteItemRepository.findByVoteId(voteId, pageable);
        }
    }

    /**
     * Get one voteItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VoteItem findOne(Long id) {
        log.debug("Request to get VoteItem : {}", id);
        return voteItemRepository.findOne(id);
    }

    /**
     * Delete the voteItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoteItem : {}", id);
        voteItemRepository.delete(id);
    }
}
