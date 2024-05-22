package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.FriendshipRequestDto;
import com.daydoodle.daydoodle.entities.FriendshipRequest;
import com.daydoodle.daydoodle.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@Stateless
public class FriendshipRequestBean {

    private static final Logger log = Logger.getLogger(FriendshipBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Executes a SELECT query on the database in order to fetch all the FriendshipRequest objects in the table.
     */
    public List<FriendshipRequestDto> findAllFriendshipRequests() {
        log.info("\n Entered findAllFriendshipRequests method \n");

        try {
            TypedQuery<FriendshipRequest> typedQuery = entityManager.createQuery("SELECT frq FROM FriendshipRequest frq", FriendshipRequest.class);
            List<FriendshipRequest> friendshipRequests = typedQuery.getResultList();

            log.info("\n** Exited findAllFriendshipRequests method **\n");
            return copyFriendshipRequestsToDto(friendshipRequests);

        } catch (Exception ex) {
            log.info("\n Error in findAllFriendshipRequests method! " + ex.getMessage() + " \n");
            throw new EJBException(ex);
        }
    }

    /**
     * Converts a list of FriendshipRequest objects into a list of FriendshipRequestDto objects, sorts the list from the most recent friend request.
     */
    private List<FriendshipRequestDto> copyFriendshipRequestsToDto(List<FriendshipRequest> friendshipRequestList) {

        log.info("\n Entered copyFriendshipRequestsToDto method with list size of: " + friendshipRequestList.size() + " \n");
        List<FriendshipRequestDto> listToReturn = new ArrayList<>();

        for (FriendshipRequest frq : friendshipRequestList) {
            FriendshipRequestDto friendshipRequestDtoTemp = new FriendshipRequestDto(frq.getId(), frq.getRequester(), frq.getReceiver(), frq.getStatus().name(), frq.getDateSent());
            listToReturn.add(friendshipRequestDtoTemp);
        }

        Collections.sort(listToReturn, new Comparator<FriendshipRequestDto>() {
            @Override
            public int compare(FriendshipRequestDto o1, FriendshipRequestDto o2) {
                return o1.getDateSent().compareTo(o2.getDateSent());
            }
        });

        log.info("\n Exited copyFriendshipRequestsToDto method. \n");
        return listToReturn;


    }

    /**
     * Creates a new list of FriendshipRequestDto objects that contains only the friend requests sent by a certain user.
     * @return The list of friend requests sent by the user.
     */
    public List<FriendshipRequestDto> getAllSentRequestsByUser (String username, List<FriendshipRequestDto> allFriendshipRequests){
        log.info("\n** Entered getAllSentRequestsByUser method with the username: "+username+" **\n");
        List<FriendshipRequestDto> listToReturn=new ArrayList<>();

        for(FriendshipRequestDto frq : allFriendshipRequests){
            if(frq.getRequester().getUsername().equals(username)){
                listToReturn.add(frq);
            }
        }

        log.info("\n** Exited getAllSentRequestsByUser method. **\n");
        return listToReturn;
    }

    /**
     * Creates a new list of FriendshipRequestDto objects that contains only the friend requests received by a certain user.
     * @return The list of friend requests received by the user.
     */
    public List<FriendshipRequestDto> getAllReceivedRequestsByUser (String username, List<FriendshipRequestDto> allFriendshipRequests){
        log.info("\n Entered getAllReceivedRequestsByUser method with the username: "+username+" \n");
        List<FriendshipRequestDto> listToReturn=new ArrayList<>();

        for(FriendshipRequestDto frq : allFriendshipRequests){
            if(frq.getReceiver().getUsername().equals(username) && frq.getStatus()=="PENDING"){
                listToReturn.add(frq);
                log.info("\n Added request with id: "+frq.getId()+"\n");
            }
        }

        log.info("\n Exited getAllReceivedRequestsByUser method. \n");
        return listToReturn;
    }

    /**
     * Finds a specific Friendship Request based on its id.
     */
    public FriendshipRequestDto findFriendshipRequestById(Long id, List<FriendshipRequestDto> allFriendshipRequests){
        FriendshipRequestDto friendshipRequestToReturn=new FriendshipRequestDto();

        for(FriendshipRequestDto freq: allFriendshipRequests){
            if(freq.getId().equals(id)){
                friendshipRequestToReturn=freq;
                break;
            }
        }

        return friendshipRequestToReturn;
    }

    /**
     * Updates a certain Friendship Request.
     */
    public void updateFriendshipRequestStatus(FriendshipRequestDto newFriendshipRequest, String status){
        log.info("\n** Entered updateFriendshipRequestStatus method for the username: "+ newFriendshipRequest.getId() +" **\n");

        FriendshipRequest friendshipRequest=entityManager.find(FriendshipRequest.class,newFriendshipRequest.getId());
        if(Objects.equals(status, "accepted")){
            friendshipRequest.setStatus(FriendshipRequest.RequestStatus.ACCEPTED);
        }else{
            friendshipRequest.setStatus(FriendshipRequest.RequestStatus.REJECTED);
        }

        log.info("\n** Exited updateFriendshipRequestStatus method. **\n");
    }

    /**
     * Creates a new friendship request with the given usernames
     */
    public void createFriendshipRequest(String requesterUsername, String receiverUsername){

        log.info("\n** Entered createFriendshipRequest method. **\n");

        FriendshipRequest freq = new FriendshipRequest();
        freq.setRequester(entityManager.find(User.class,requesterUsername));
        freq.setReceiver(entityManager.find(User.class,receiverUsername));
        freq.setDateSent(LocalDate.now());
        freq.setStatus(FriendshipRequest.RequestStatus.PENDING);
        entityManager.persist(freq);

        log.info("\n** Exited createFriendshipRequest method. **\n");
    }

    public boolean isFriendshipRequestPending(String requesterUsername, String receiverUsername){
        log.info("\n** Entered isFriendshipRequestPending method. **\n");

        List<FriendshipRequestDto> allFriendshipRequests = findAllFriendshipRequests();
        for(FriendshipRequestDto frq : allFriendshipRequests){
            if(frq.getRequester().getUsername().equals(requesterUsername) && frq.getReceiver().getUsername().equals(receiverUsername)&&frq.getStatus()=="PENDING"){
                log.info("\n Exited isFriendshipRequestPending method ----> friendship request PENDING \n");
                return true;
            }
        }
        log.info("\n Exited isFriendshipRequestPending method ----> friendship request NOT PENDING \n");
        return false;

    }

    public boolean userSentMeAFriendshipRequest(String requesterUsername, String receiverUsername){
        log.info("\n** Entered userSentMeAFriendshipRequest method. **\n");
        List<FriendshipRequestDto> allFriendshipRequests = findAllFriendshipRequests();
        for(FriendshipRequestDto frq : allFriendshipRequests){
            if(frq.getReceiver().getUsername().equals(receiverUsername) && frq.getRequester().getUsername().equals(requesterUsername)){
                return true;
            }
        }
        log.info("\n Exited userSentMeAFriendshipRequest method. **\n");
        return false;
    }

    /**
     * Sets friendship request status to: ACCEPTED
     */
    public void setFriendshipStatusAccepted(Long reqId) {
        log.info("\n** Entered setFriendshipStatusAccepted method. \n");

        FriendshipRequest friendshipRequest=entityManager.find(FriendshipRequest.class,reqId);
        friendshipRequest.setStatus(FriendshipRequest.RequestStatus.ACCEPTED);
        entityManager.merge(friendshipRequest);
        log.info("\n** Exited setFriendshipStatusAccepted method. \n");
    }

    /**
     * Sets friendship request status to: DECLINED
     */
    public void setFriendshipStatusDeclined(Long reqId) {
        log.info("\n** Entered setFriendshipStatusDeclined method. **\n");

        FriendshipRequest friendshipRequest=entityManager.find(FriendshipRequest.class,reqId);
        entityManager.remove(friendshipRequest);
        log.info("\n** Exited setFriendshipStatusDeclined method. **\n");
    }

    /**
     * Finds a friendship request sent from user 1 to user 2
     */
    public FriendshipRequestDto findFriendshipRequestFromTo(String fromUsername, String toUsername){
        log.info("\n** Entered findFriendshipRequestFromTo method. **\n");
        List<FriendshipRequestDto> allFriendshipRequests = findAllFriendshipRequests();
        for(FriendshipRequestDto frq : allFriendshipRequests){
            if(frq.getReceiver().getUsername().equals(toUsername) && frq.getRequester().getUsername().equals(fromUsername)){
                return frq;
            }
        }

        return null;
    }

    /**
     * Removes a friendship request
     */
    public void removeFriendshipRequest(FriendshipRequestDto frq) {
        log.info("\n** Entered removeFriendshipRequest method. **\n");
        FriendshipRequest friendshipRequest=entityManager.find(FriendshipRequest.class,frq.getId());
        entityManager.remove(friendshipRequest);
        log.info("\n** Exited removeFriendshipRequest method. **\n");
    }
}