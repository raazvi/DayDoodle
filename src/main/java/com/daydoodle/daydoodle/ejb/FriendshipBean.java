package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.FriendshipDto;
import com.daydoodle.daydoodle.entities.Friendship;
import com.daydoodle.daydoodle.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class FriendshipBean {

    private static final Logger log= Logger.getLogger(FriendshipBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds all friendships in the database
     */
    public List<FriendshipDto> findAllFriendships(){
        log.info("\n Entered findAllFriendships method \n");

        try{
            TypedQuery<Friendship> typedQuery=entityManager.createQuery("SELECT fr FROM Friendship fr", Friendship.class);
            List<Friendship> friendships=typedQuery.getResultList();

            log.info("\n Exited findAllFriendships method \n");
            return copyFriendshipToDto(friendships);

        }catch(Exception ex){
            log.info("\n Error in findAllFriendships method! "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }
    }
    private List<FriendshipDto> copyFriendshipToDto(List<Friendship> friendshipList) {

        log.info("\n Entered copyFriendshipToDto method with list size of: "+friendshipList.size()+" \n");
        List<FriendshipDto> listToReturn =new ArrayList<>();

        for(Friendship fr: friendshipList){
            FriendshipDto friendshipDtoTemp=new FriendshipDto(fr.getId(),fr.getDateCreated(),fr.getUser(),fr.getFriend());
            listToReturn.add(friendshipDtoTemp);
        }

        log.info("\n Exited copyFriendshipToDto method. \n");
        return listToReturn;

    }

    /**
     * Finds all friends of a user, identified by their username
     */
    public List<FriendshipDto> findFriendshipsByUser(String username, List<FriendshipDto> allFriendships){

        log.info("\n Entered findFriendshipsByUser method with list size of: "+allFriendships.size()+" and username: " + username + " \n");
        List<FriendshipDto> listToReturn =new ArrayList<>();

        for(FriendshipDto frdto: allFriendships){
            if(frdto.getUser().getUsername().equals(username)){
                listToReturn.add(frdto);
            }
        }

        log.info("\n Exited findFriendshipsByUser method. \n");
        return listToReturn;
    }

    /**
     * Creates a new friendship
     */
    public void createFriendship(String user, String friend){
        log.info("\n Creating friendship between " + user + " and " + friend + " \n");

        Friendship friendship = new Friendship();
        User userEntity =entityManager.find(User.class, user);
        User friendEntity = entityManager.find(User.class, friend);

        friendship.setUser(userEntity);
        friendship.setFriend(friendEntity);
        friendship.setDateCreated(LocalDate.now());
        entityManager.persist(friendship);

        Friendship reverseFriendship=new Friendship();
        reverseFriendship.setUser(friendEntity);
        reverseFriendship.setFriend(userEntity);
        friendship.setDateCreated(LocalDate.now());
        entityManager.persist(reverseFriendship);

        log.info("\n Friendship created successfully \n");
    }

    /**
     * Checks if the two users are friends or not.
     */
    public boolean isUserMyFriend(String username, String friendUsername){
        log.info("\n Entered isUserMyFriend method \n");
        User user=entityManager.find(User.class,username);
        User friend=entityManager.find(User.class,friendUsername);
        List<FriendshipDto> allFriendship = findFriendshipsByUser(username,findAllFriendships());
        for(FriendshipDto friendDto: allFriendship){
            if(friendDto.getFriend().getUsername().equals(friendUsername)){
                log.info("\n Exited isUserMyFriend method ----> is Friend \n");
                return true;
            }
        }

        log.info("\n Exited isUserMyFriend method ----> is NOT friend\n");
        return false;

    }

    /**
     * Returns a list of all the usernames of the user's friends
     */
    public List<String> findUserFriends(String username, List<FriendshipDto> userFriendships) {
        log.info("\n Entered findUserFriends method \n");
        List<String> userFriendsList = new ArrayList<>();
        for(FriendshipDto friendDto: userFriendships){
            userFriendsList.add(friendDto.getFriend().getUsername());
        }
        log.info("\n Exited findUserFriends method. \n");
        return userFriendsList;
    }
}
