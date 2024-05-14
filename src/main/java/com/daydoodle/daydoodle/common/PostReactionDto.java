package com.daydoodle.daydoodle.common;

import com.daydoodle.daydoodle.entities.Post;
import com.daydoodle.daydoodle.entities.PostReaction;
import com.daydoodle.daydoodle.entities.User;

public class PostReactionDto {

    Long id;
    PostReaction.ReactionType reactionType;
    User user;
    Post post;

    public PostReactionDto(Long id, PostReaction.ReactionType reactionType, User user, Post post) {
        this.id = id;
        this.reactionType = reactionType;
        this.user = user;
        this.post = post;
    }
    public PostReactionDto(PostReaction.ReactionType reactionType, User user, Post post) {
        this.reactionType = reactionType;
        this.user = user;
        this.post = post;
    }
    public PostReactionDto() {
    }

    public Long getId() {
        return id;
    }
    public PostReaction.ReactionType getReactionType() {
        return reactionType;
    }
    public User getUser() {
        return user;
    }
    public Post getPost() {
        return post;
    }
}
