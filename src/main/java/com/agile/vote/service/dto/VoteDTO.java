package com.agile.vote.service.dto;

import com.agile.vote.domain.VoteItem;

import java.time.ZonedDateTime;
import java.util.List;

public class VoteDTO {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 投票开始时间
     */
    private ZonedDateTime startDate;

    /**
     * 投票截止时间
     */
    private ZonedDateTime endDate;

    private String remark;

    /**
     * 该用户是够已经投过票
     */
    private Boolean isVoted;

    List<VoteItem> voteItemList;

    /**
     * 投票的选项
     */
    private Long votedItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<VoteItem> getVoteItemList() {
        return voteItemList;
    }

    public void setVoteItemList(List<VoteItem> voteItemList) {
        this.voteItemList = voteItemList;
    }

    public Boolean getVoted() {
        return isVoted;
    }

    public void setVoted(Boolean voted) {
        isVoted = voted;
    }

    public Long getVotedItemId() {
        return votedItemId;
    }

    public void setVotedItemId(Long votedItemId) {
        this.votedItemId = votedItemId;
    }

    @Override
    public String toString() {
        return "VoteDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", remark='" + remark + '\'' +
            '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteDTO)) return false;

        VoteDTO voteDTO = (VoteDTO) o;

        if (getId() != null ? !getId().equals(voteDTO.getId()) : voteDTO.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(voteDTO.getTitle()) : voteDTO.getTitle() != null) return false;
        if (getStartDate() != null ? !getStartDate().equals(voteDTO.getStartDate()) : voteDTO.getStartDate() != null)
            return false;
        if (getEndDate() != null ? !getEndDate().equals(voteDTO.getEndDate()) : voteDTO.getEndDate() != null)
            return false;
        return getRemark() != null ? getRemark().equals(voteDTO.getRemark()) : voteDTO.getRemark() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        result = 31 * result + (getRemark() != null ? getRemark().hashCode() : 0);
        return result;
    }
}
