package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.ActivityDto;
import com.daydoodle.daydoodle.common.PostReactionDto;
import com.daydoodle.daydoodle.entities.Activity;
import com.daydoodle.daydoodle.entities.Post;
import com.daydoodle.daydoodle.entities.PostReaction;
import com.daydoodle.daydoodle.entities.User;
import com.daydoodle.daydoodle.servlets.Post.PostReact;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class PostReactionBean {

    private static final Logger log = Logger.getLogger(PostReactionBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds all post reactions in the database
     */
    public List<PostReactionDto> findAllPostReactions() {
        log.info("\n Entered findAllPostReactions method \n");

        try{
            TypedQuery<PostReaction> typedQuery=entityManager.createQuery("SELECT postReaction FROM PostReaction postReaction", PostReaction.class);
            List<PostReaction> postReactions=typedQuery.getResultList();

            log.info("\n Exited findAllPostReactions method, successfully found "+ postReactions.size() +" reactions \n");
            return copyPostReactionsToDto(postReactions);

        }catch(Exception ex){
            log.info("\n Error in findAllPostReactions method! "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }
    }
    private List<PostReactionDto> copyPostReactionsToDto(List<PostReaction> postReactions) {
        log.info("\n Entered copyPostReactionsToDto method with list size of: "+postReactions.size()+" \n");
        List<PostReactionDto> listToReturn =new ArrayList<PostReactionDto>();

        for(PostReaction pr: postReactions){
            PostReactionDto prDto = new PostReactionDto(pr.getId(),pr.getReactionType(),pr.getUser(),pr.getPost());
            listToReturn.add(prDto);
        }

        log.info("\n Exited copyPostReactionsToDto method. \n");

        return listToReturn;
    }

    /**
     * Adds a new reaction to a post identified by its id and the username who reacted
     */
    public void addReactionToPost(Long postId, String usernameReacted, PostReaction.ReactionType reactionType) {

        log.info("\n Entered addReactionToPost method \n");
        Post post = entityManager.find(Post.class, postId);
        User user = entityManager.find(User.class, usernameReacted);
        PostReaction pr=new PostReaction();
        pr.setPost(post);
        pr.setUser(user);
        pr.setType(reactionType);
        post.getReactions().add(pr);
        entityManager.persist(pr);
        entityManager.merge(post);
        log.info("\n Exited addReactionToPost method. \n");
    }

    /**
     * Finds all reactions for a specific post identified by its id.
     */
    public List<PostReactionDto> findAllReactionsForPost(Long postId) {
        log.info("\n Entered findAllReactionsForPost method \n");
        List<PostReactionDto> listToReturn =new ArrayList<>();
        List<PostReactionDto> allPostReactions = findAllPostReactions();
        for(PostReactionDto pr: allPostReactions){
            if(pr.getPost().getId().equals(postId)){
                listToReturn.add(pr);
            }
        }
        log.info("\n Exited findAllReactionsForPost method. \n");
        return listToReturn;


    }

    /**
     * Removes all other reactions from a specific user to a specific post.
     */
    public void removeOtherReactionsFromUser(Long postId, String username) {
        log.info("\n Entered removeOtherReactionsFromUser method \n");

        try {
            // Find the post by its ID
            Post post = entityManager.find(Post.class, postId);

            if (post != null) {
                // Get the reactions associated with the post
                List<PostReaction> reactions = post.getReactions();

                // Iterate over the reactions list to remove other reactions from the user
                Iterator<PostReaction> iterator = reactions.iterator();
                while (iterator.hasNext()) {
                    PostReaction reaction = iterator.next();
                    if (reaction.getUser().getUsername().equals(username)) {
                        // Remove the reaction if it belongs to the specified user
                        iterator.remove();
                        entityManager.remove(reaction); // Remove the reaction from the database
                    }
                }

                // Update the post with modified reactions
                entityManager.merge(post);

                log.info("\n Successfully removed other reactions from user: " + username + " for post: " + postId + "\n");
            } else {
                log.warning("\n Post with ID: " + postId + " not found \n");
            }
        } catch (Exception ex) {
            log.severe("\n Error in removeOtherReactionsFromUser method: " + ex.getMessage() + "\n");
            throw new EJBException(ex);
        }
    }


}
