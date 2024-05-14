package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.entities.Post;
import com.daydoodle.daydoodle.entities.PostReaction;
import com.daydoodle.daydoodle.entities.User;
import com.daydoodle.daydoodle.servlets.Post.PostReact;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.logging.Logger;

@Stateless
public class PostReactionBean {

    private static final Logger log = Logger.getLogger(PostReactionBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public void addReactionToPost(Long postId, String usernameReacted, PostReaction.ReactionType reactionType) {

        log.info("\n Entered addReactionToPost \n");
        User user = entityManager.find(User.class, usernameReacted);
        Post post = entityManager.find(Post.class, postId);

        PostReaction postReaction=null;
        try {
            postReaction = entityManager.createQuery("SELECT pr FROM PostReaction pr WHERE pr.user.username = :usernameReacted AND pr.reactionType <> :reactionType", PostReaction.class)
                    .setParameter("usernameReacted", usernameReacted)
                    .setParameter("reactionType", reactionType)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info("No PostReaction found for username " + usernameReacted+" and type " + reactionType);
        }
        if(postReaction != null){
            post.getReactions().remove(postReaction);
            entityManager.remove(postReaction);
        }

        postReaction= new PostReaction();
        postReaction.setPost(post);
        postReaction.setType(reactionType);
        postReaction.setUser(user);
        entityManager.persist(postReaction);

        post.getReactions().add(postReaction);
        entityManager.merge(post);
        log.info("\n Exited addReactionToPost \n");

    }
}
