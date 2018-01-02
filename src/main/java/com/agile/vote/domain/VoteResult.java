package com.agile.vote.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * 投票结果
 */
@ApiModel(description = "投票结果")
@Entity
@Table(name = "vote_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VoteResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 投票时间
     */
    @NotNull
    @ApiModelProperty(value = "投票时间", required = true)
    @Column(name = "vote_time", nullable = false)
    private ZonedDateTime voteTime;

    /**
     * 投票人
     */
    @NotNull
    @ApiModelProperty(value = "投票人", required = true)
    @Column(name = "openid", nullable = false)
    private String openid;

    @ManyToOne
    private VoteItem voteItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getVoteTime() {
        return voteTime;
    }

    public VoteResult voteTime(ZonedDateTime voteTime) {
        this.voteTime = voteTime;
        return this;
    }

    public void setVoteTime(ZonedDateTime voteTime) {
        this.voteTime = voteTime;
    }

    public String getOpenid() {
        return openid;
    }

    public VoteResult openid(String openid) {
        this.openid = openid;
        return this;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public VoteItem getVoteItem() {
        return voteItem;
    }

    public VoteResult voteItem(VoteItem voteItem) {
        this.voteItem = voteItem;
        return this;
    }

    public void setVoteItem(VoteItem voteItem) {
        this.voteItem = voteItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VoteResult voteResult = (VoteResult) o;
        if (voteResult.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voteResult.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoteResult{" +
            "id=" + getId() +
            ", voteTime='" + getVoteTime() + "'" +
            ", openid='" + getOpenid() + "'" +
            "}";
    }
}
