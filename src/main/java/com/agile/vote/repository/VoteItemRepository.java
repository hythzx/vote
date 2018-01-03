package com.agile.vote.repository;

import com.agile.vote.domain.VoteItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the VoteItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {

    /**
     * 通过投票的ID获取投票项列表
     * @param id
     * @param pageable
     * @return
     */
    Page<VoteItem> findByVoteId(Long id, Pageable pageable);

    /**
     * 通过投票的ID获取投票项列表
     * @param id
     * @return
     */
    List<VoteItem> findByVoteId(Long id);

}
