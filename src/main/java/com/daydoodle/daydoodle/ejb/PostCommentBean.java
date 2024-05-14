package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.PostCommentDto;
import com.daydoodle.daydoodle.common.PostReactionDto;
import com.daydoodle.daydoodle.entities.Post;
import com.daydoodle.daydoodle.entities.PostComment;
import com.daydoodle.daydoodle.entities.PostReaction;
import com.daydoodle.daydoodle.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class PostCommentBean {

    private static final Logger log = Logger.getLogger(PostReactionBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds all post comments in the database.
     */
    public List<PostCommentDto> findAllComments() {
        log.info("\n Entered findAllComments method \n");

        try{
            TypedQuery<PostComment> typedQuery=entityManager.createQuery("SELECT postComment FROM PostComment postComment ORDER BY postComment.postedAt DESC", PostComment.class);
            List<PostComment> postComments =typedQuery.getResultList();

            log.info("\n Exited findAllComments method, successfully found "+ postComments.size() +" comments \n");
            return copyPostCommentsToDto(postComments);

        }catch(Exception ex){
            log.info("\n Error in findAllComments method! "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }
    }
    private List<PostCommentDto> copyPostCommentsToDto(List<PostComment> postComments) {
        log.info("\n Entered copyPostCommentsToDto method with list size of: " + postComments.size() + " \n");
        List<PostCommentDto> listToReturn = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (PostComment pc : postComments) {
            LocalDateTime formattedDate = null;
            if (pc.getPostedAt() != null) {
                formattedDate = pc.getPostedAt();
            }
            String formattedDateString = "";
            if (formattedDate != null) {
                formattedDateString = formattedDate.format(formatter);
            }

            // Replace the "T" with a space
            formattedDateString = formattedDateString.replace("T", " ");

            // Parse the formatted date string back to LocalDateTime
            LocalDateTime parsedDate = null;
            if (!formattedDateString.isEmpty()) {
                parsedDate = LocalDateTime.parse(formattedDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }
            PostCommentDto pcDto = new PostCommentDto(pc.getId(), pc.getContent(), parsedDate, pc.getUser(), pc.getPost());
            listToReturn.add(pcDto);
        }

        log.info("\n Exited copyPostCommentsToDto method. \n");

        return listToReturn;
    }





    /**
     * Finds all comments for a post identified by its id.
     */
    public List<PostCommentDto> findAllCommentsForPost(Long postId) {
        log.info("\n Entered findAllCommentsForPost method \n");
        List<PostCommentDto> listToReturn =new ArrayList<>();
        List<PostCommentDto> allComments = findAllComments();
        for(PostCommentDto pc: allComments){
            if(pc.getPost().getId().equals(postId)){
                listToReturn.add(pc);
            }
        }
        log.info("\n Exited findAllCommentsForPost method. \n");
        return listToReturn;
    }

    /**
     * Adds a comment to a post identified by its id.
     */
    public void addCommentToPost(Long postId, String authorUsername, String commentContent) {
        log.info("\n Entered addCommentToPost method \n");

        // Find the user who authored the comment
        User user = entityManager.find(User.class, authorUsername);

        // Find the post to which the comment will be added
        Post post = entityManager.find(Post.class, postId);

        PostComment postComment = new PostComment();
        postComment.setContent(commentContent);
        postComment.setPostedAt(LocalDateTime.now());
        postComment.setUser(user);
        postComment.setPost(post);

        // Add the comment to the post's list of comments
        post.getComments().add(postComment);
        entityManager.persist(postComment);

        // Merge the updated post entity to reflect the new comment
        entityManager.merge(post);

        log.info("\n Exited addCommentToPost method. \n");
    }
}
