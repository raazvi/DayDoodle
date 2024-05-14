package com.daydoodle.daydoodle.entities;

import jakarta.persistence.*;

@Entity
public class PostReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public enum ReactionType {
        LIKE, STAR
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ReactionType getReactionType() {
        return reactionType;
    }
    public void setType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
}
