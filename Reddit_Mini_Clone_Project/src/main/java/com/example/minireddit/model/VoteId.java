package com.example.minireddit.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VoteId implements Serializable {
    private Long postId;
    private Long userId;

    public VoteId(){}
    public VoteId(Long postId, Long userId){this.postId=postId; this.userId=userId;}

    public Long getPostId(){return postId;}
    public void setPostId(Long postId){this.postId=postId;}
    public Long getUserId(){return userId;}
    public void setUserId(Long userId){this.userId=userId;}

    @Override public boolean equals(Object o){
        if (this==o) return true;
        if (!(o instanceof VoteId v)) return false;
        return Objects.equals(postId,v.postId) && Objects.equals(userId,v.userId);
    }
    @Override public int hashCode(){return Objects.hash(postId,userId);}
}

