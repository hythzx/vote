package com.agile.vote.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * 投票项
 */
@ApiModel(description = "投票项")
@Entity
@Table(name = "vote_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VoteItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    @NotNull
    @Size(max = 512)
    @ApiModelProperty(value = "标题", required = true)
    @Column(name = "title", length = 512, nullable = false)
    private String title;

    /**
     * 投票数
     */
    @ApiModelProperty(value = "投票数")
    @Column(name = "vote_count")
    private Integer voteCount;

    @ManyToOne
    private Vote vote;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public VoteItem title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public VoteItem voteCount(Integer voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Vote getVote() {
        return vote;
    }

    public VoteItem vote(Vote vote) {
        this.vote = vote;
        return this;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
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
        VoteItem voteItem = (VoteItem) o;
        if (voteItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voteItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoteItem{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", voteCount=" + getVoteCount() +
            "}";
    }
}
