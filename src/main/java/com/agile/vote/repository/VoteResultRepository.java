package com.agile.vote.repository;

import com.agile.vote.domain.VoteResult;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VoteResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {

}
