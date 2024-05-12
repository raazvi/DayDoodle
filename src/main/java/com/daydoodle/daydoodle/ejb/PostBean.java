package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.PostDto;
import com.daydoodle.daydoodle.entities.Activity;
import com.daydoodle.daydoodle.entities.CustomActivity;
import com.daydoodle.daydoodle.entities.Post;
import com.daydoodle.daydoodle.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class PostBean {

    private static final Logger log= Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds all posts that are in the database, and returns them in a list.
     */
    public List<PostDto> findAllPosts(){
        log.info("\n Entered findAllPosts method \n");

        try{
            TypedQuery<Post> typedQuery=entityManager.createQuery("SELECT post FROM Post post", Post.class);
            List<Post> posts=typedQuery.getResultList();

            log.info("\n Exited findAllPosts method, successfully found "+ posts.size() +" posts \n");
            return copyPostToDto(posts);

        }catch(Exception ex){
            log.info("\n Error in findAllPosts method! "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }

    }

    private List<PostDto> copyPostToDto(List<Post> posts) {
        log.info("\n Entered copyPostToDto method with list size of: " + posts.size() + " \n");
        List<PostDto> listToReturn = new ArrayList<PostDto>();
        PostDto postDtoTemp;

        for (Post p : posts) {

            if (p.getActivity() == null) {
                postDtoTemp = new PostDto(p.getId(), p.getDatePosted(), p.getCaption(), p.getAuthor(), p.getCustomActivity());
            } else {
                postDtoTemp = new PostDto(p.getId(), p.getDatePosted(), p.getCaption(), p.getAuthor(), p.getActivity());
            }

            listToReturn.add(postDtoTemp);
        }
        listToReturn.sort(Comparator.comparing(PostDto::getDatePosted).reversed());

        log.info("\n Exited copyPostToDto method. \n");
        return listToReturn;
    }

    /**
     * Creates a new post with a predefined activity.
     */
    public void createPostWithActivity(String authorUsername, String caption, Long activityId){
        log.info("\n Entered createPost method for the user: "+authorUsername+" \n");

        Post newPost = new Post();
        newPost.setCaption(caption);
        newPost.setDatePosted(LocalDate.now());

        Activity activity=entityManager.find(Activity.class,activityId);
        newPost.setActivity(activity);

        User author=entityManager.find(User.class,authorUsername);
        author.getPosts().add(newPost);
        newPost.setAuthor(author);

        entityManager.persist(newPost);

        log.info("\n Exited createPostWithCustomActivity method. \n");

    }

    /**
     * Creates a new post with a user-specific activity (custom activity)
     */
    public void createPostWithCustomActivity(String authorUsername, String caption, Long customActivityId){
        Post newPost = new Post();
        newPost.setCaption(caption);
        newPost.setDatePosted(LocalDate.now());

        CustomActivity ca=entityManager.find(CustomActivity.class,customActivityId);
        newPost.setCustomActivity(ca);

        User author=entityManager.find(User.class,authorUsername);
        author.getPosts().add(newPost);
        newPost.setAuthor(author);

        entityManager.persist(newPost);

        log.info("\n Exited createPostWithCustomActivity method. \n");
    }

}