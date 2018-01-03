package com.agile.vote.service;

import com.agile.vote.domain.VoteResult;
import com.agile.vote.service.dto.VoteDTO;

import java.util.List;

public interface WechatServiceService {

    /**
     * 获取当前可以投票的列表
     * @param login
     * @return
     */
    List<VoteDTO> getActiveVoteList(String login);

    /**
     * 获取投票的详细信息
     * @param id
     * @param login
     * @return
     */
    VoteDTO getVote(Long id, String login);

    /**
     * 用户投票
     * @param id
     * @param login
     * @return
     */
    VoteResult vote(Long id, String login) throws Exception;
}
