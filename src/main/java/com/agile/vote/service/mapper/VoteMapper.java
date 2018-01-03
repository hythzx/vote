package com.agile.vote.service.mapper;

import com.agile.vote.domain.Vote;
import com.agile.vote.service.dto.VoteDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = { })
public interface VoteMapper extends EntityMapper<VoteDTO, Vote>{

}
