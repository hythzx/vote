package com.agile.vote.web.rest;

import com.agile.vote.domain.VoteItem;
import com.agile.vote.domain.VoteResult;
import com.agile.vote.service.VoteItemService;
import com.agile.vote.service.VoteResultService;
import com.agile.vote.service.WechatServiceService;
import com.agile.vote.service.dto.VoteDTO;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/wechat")
public class WechatResource {

    private final WechatServiceService wechatServiceService;

    private final VoteResultService voteResultService;

    public WechatResource(WechatServiceService wechatServiceService, VoteResultService voteResultService) {
        this.wechatServiceService = wechatServiceService;
        this.voteResultService = voteResultService;
    }

    @GetMapping("/votes")
    @Timed
    public ResponseEntity<List<VoteDTO>> getActiveVotes(String login){
        List<VoteDTO> activeVoteList = wechatServiceService.getActiveVoteList(login);
        return ResponseEntity.ok(activeVoteList);
    }

    @GetMapping("/votes/{id}")
    @Timed
    public ResponseEntity<VoteDTO> getActiveVotes(@PathVariable Long id, String login){
        VoteDTO activeVote = wechatServiceService.getVote(id, login);
        return ResponseEntity.ok(activeVote);
    }

    @PostMapping("/votes")
    @Timed
    @ApiOperation(value = "微信用户投票")
    public ResponseEntity<VoteResult> vote(String login, Long voteItemId) throws Exception {
        VoteResult voteResult = wechatServiceService.vote(voteItemId, login);
        return ResponseEntity.ok(voteResult);
    }
}
