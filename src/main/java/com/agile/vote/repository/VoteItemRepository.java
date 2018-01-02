package com.agile.vote.repository;

import com.agile.vote.domain.VoteItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VoteItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {

}
