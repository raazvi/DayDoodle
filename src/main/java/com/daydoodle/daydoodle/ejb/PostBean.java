package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.FriendshipDto;
import com.daydoodle.daydoodle.common.PostDto;
import com.daydoodle.daydoodle.entities.*;
import com.daydoodle.daydoodle.servlets.Post.PostReact;
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
        List<PostDto> listToReturn = new ArrayList<>();
        PostDto postDtoTemp;

        for (Post p : posts) {
            if (p == null) {
                postDtoTemp = new PostDto(p.getId(),p.getDatePosted(),p.getCaption(),p.getAuthor(),p.getActivity(),p.getComments(),p.getReactions(),p.getPicture());
            } else {
                postDtoTemp = new PostDto(p.getId(),p.getDatePosted(),p.getCaption(),p.getAuthor(),p.getCustomActivity(),p.getComments(),p.getReactions(),p.getPicture());
            }

            listToReturn.add(postDtoTemp);
        }

        listToReturn.sort((p1, p2) -> {
            LocalDate date1 = p1.getDatePosted();
            LocalDate date2 = p2.getDatePosted();
            if (date1 == null && date2 == null) {
                return 0;
            } else if (date1 == null) {
                return 1;
            } else if (date2 == null) {
                return -1;
            } else {
                return date2.compareTo(date1);
            }
        });

        log.info("\n Exited copyPostToDto method. \n");
        return listToReturn;
    }

    /**
     * Creates a new post with a predefined activity.
     */
    public void createPostWithActivity(String username, String caption, Long activityId, Picture picture) {
        User user = entityManager.find(User.class, username);
        Activity activity = entityManager.find(Activity.class, activityId);
        Post post = new Post();
        post.setAuthor(user);
        post.setCaption(caption);
        post.setActivity(activity);
        post.setPicture(picture);
        post.setDatePosted(LocalDate.now());

        if (picture != null) {
            entityManager.persist(picture);
        }
        entityManager.persist(post);
    }

    /**
     * Creates a new post with a user-specific activity (custom activity)
     */
    public void createPostWithCustomActivity(String username, String caption, Long customActivityId, Picture picture) {
        User user = entityManager.find(User.class, username);
        CustomActivity customActivity = entityManager.find(CustomActivity.class, customActivityId);
        Post post = new Post();
        post.setAuthor(user);
        post.setCaption(caption);
        post.setCustomActivity(customActivity);
        post.setPicture(picture);
        post.setDatePosted(LocalDate.now());

        if (picture != null) {
            entityManager.persist(picture);
        }
        entityManager.persist(post);
    }

    /**
     * Finds all post of the user's friends.
     */
    public List<PostDto> findFriendsPosts(List<String> userFriends, List<PostDto> allPosts) {
        log.info("\n Entered findFriendsPosts method for the user: "+userFriends.size()+" \n");
        List<PostDto> friendsPosts=new ArrayList<>();
        for(PostDto postDto:allPosts){
            if(userFriends.contains(postDto.getAuthor().getUsername())){
                friendsPosts.add(postDto);
            }
        }
        log.info("\n Exited findFriendsPosts method. \n");
        return friendsPosts;

    }

    /**
     * Finds a certain post by its id.
     */
    public PostDto findPostById(Long postId) {

        List<PostDto> allPosts=findAllPosts();
        for(PostDto postDto:allPosts){
            if(postDto.getId().equals(postId)){
                return postDto;
            }
        }

        return null;
    }

    /**
     * Finds all posts by username.
     */
    public List<PostDto> findPostsByUser(String username) {

        log.info("\n Entered findPostsByUser method for the user: "+username+" \n");
        List<PostDto> posts=new ArrayList<>();
        List<PostDto> allPosts=findAllPosts();
        for(PostDto postDto:allPosts){
            if(postDto.getAuthor().getUsername().equals(username)){
                posts.add(postDto);
            }
        }

        log.info("\n Exited findPostsByUser method. \n");
        return posts;

    }

    /**
     * Deletes a post based on the post id.
     */
    public void removePost(Long postId) {
        log.info("\n Entered removePost method for the user: "+postId+" \n");
        Post post=entityManager.find(Post.class,postId);
        entityManager.remove(post);
        log.info("\n Exited removePost method. \n");
    }

    /**
     * Edit a post with a predefined activity.
     */
    public void editPostWithActivity(Long postId, String newCaption, Long activityId) {
        log.info("\n Entered editPostWithActivity method for the post: "+postId+" \n");
        Post post=entityManager.find(Post.class,postId);
        post.setCaption(newCaption);
        post.setActivity(entityManager.find(Activity.class,activityId));
        entityManager.merge(post);
        log.info("\n Exited editPostWithActivity method. \n");
    }
    /**
     * Edit a post with a custom activity.
     */
    public void editPostWithCustomActivity(Long postId, String newCaption, Long customActivityId) {
        log.info("\n Entered editPostWithCustomActivity method for the post: "+postId+" \n");
        Post post=entityManager.find(Post.class,postId);
        post.setCaption(newCaption);
        post.setCustomActivity(entityManager.find(CustomActivity.class,customActivityId));
        entityManager.merge(post);
        log.info("\n Exited editPostWithCustomActivity method. \n");
    }
}
