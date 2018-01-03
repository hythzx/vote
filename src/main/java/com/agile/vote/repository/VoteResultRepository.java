package com.agile.vote.repository;

import com.agile.vote.domain.VoteResult;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the VoteResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {

    /**
     * 获取当前用户对某个投票的记录
     * @param id
     * @param login
     * @return
     */
    List<VoteResult> findByVoteItemVoteIdAndOpenid(Long id, String login);

    /**
     * 通过投票项和用户名获取用户是否已经投过票
     * @param id
     * @param login
     * @return
     */
    List<VoteResult> findByVoteItemIdAndOpenid(Long id, String login);
}
