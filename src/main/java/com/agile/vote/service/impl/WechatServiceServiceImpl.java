package com.agile.vote.service.impl;

import com.agile.vote.domain.Vote;
import com.agile.vote.domain.VoteItem;
import com.agile.vote.domain.VoteResult;
import com.agile.vote.repository.VoteItemRepository;
import com.agile.vote.repository.VoteRepository;
import com.agile.vote.repository.VoteResultRepository;
import com.agile.vote.service.WechatServiceService;
import com.agile.vote.service.dto.VoteDTO;
import com.agile.vote.service.mapper.VoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class WechatServiceServiceImpl implements WechatServiceService {

    private final Logger log = LoggerFactory.getLogger(WechatServiceServiceImpl.class);

    private final VoteRepository voteRepository;

    private final VoteItemRepository voteItemRepository;

    private final VoteResultRepository voteResultRepository;

    private final VoteMapper voteMapper;


    public WechatServiceServiceImpl(VoteRepository voteRepository, VoteItemRepository voteItemRepository, VoteResultRepository voteResultRepository, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.voteItemRepository = voteItemRepository;
        this.voteResultRepository = voteResultRepository;
        this.voteMapper = voteMapper;
    }

    /**
     * 获取可用的投票列表
     * @return
     */
    @Override
    public List<VoteDTO> getActiveVoteList(String login){
        List<Vote> voteList = voteRepository.findByStartDateBeforeAndEndDateAfterAndDeleted(ZonedDateTime.now(), ZonedDateTime.now(), false);
        List<VoteDTO> voteDTOS = voteMapper.toDto(voteList);
        for (VoteDTO voteDTO : voteDTOS) {
            List<VoteItem> voteItemList = voteItemRepository.findByVoteId(voteDTO.getId());
            voteDTO.setVoteItemList(voteItemList);
        }
        return voteDTOS;
    }

    /**
     * 获取投票的详细信息
     *
     * @param id
     * @param login
     * @return
     */
    @Override
    public VoteDTO getVote(Long id, String login) {
        Vote vote = voteRepository.findOne(id);
        VoteDTO voteDTO = voteMapper.toDto(vote);
        if (vote != null) {
            List<VoteItem> voteItemList = voteItemRepository.findByVoteId(voteDTO.getId());
            voteDTO.setVoteItemList(voteItemList);
            List<VoteResult> resultList = findCurrentUserVoteHistory(vote.getId(), login);
            voteDTO.setVoted(resultList.isEmpty()? false: true);
        }
        return voteDTO;
    }

    /**
     * 用户投票
     *
     * @param id
     * @param login
     * @return
     */
    @Override
    public VoteResult vote(Long id, String login) throws Exception {
        List<VoteResult> resultList = voteResultRepository.findByVoteItemIdAndOpenid(id, login);
        if (!resultList.isEmpty()) {
            throw new Exception("您已经投过票");
        }
        VoteResult voteResult = new VoteResult();
        voteResult.setOpenid(login);
        voteResult.setVoteTime(ZonedDateTime.now());
        VoteItem voteItem = new VoteItem();
        voteItem.setId(id);
        voteResult.setVoteItem(voteItem);
        VoteResult voteResult1 = voteResultRepository.save(voteResult);
        return voteResult1;
    }

    /**
     * 获取当前用户的投票记录
     * @param voteId
     * @param login
     * @return
     */
    private List<VoteResult> findCurrentUserVoteHistory(Long voteId, String login){
        List<VoteResult> voteResultList = voteResultRepository.findByVoteItemVoteIdAndOpenid(voteId, login);
        return voteResultList;
    }
}
