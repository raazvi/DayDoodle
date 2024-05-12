package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.CustomActivityDto;
import com.daydoodle.daydoodle.entities.CustomActivity;
import com.daydoodle.daydoodle.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CustomActivityBean {

    private static final Logger log= Logger.getLogger(CustomActivityBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Returns a list of all the custom activities in the database.
     */
    public List<CustomActivityDto> findAllCustomActivities(){
        log.info("\n Entered findAllCustomActivities method \n");

        try{
            TypedQuery<CustomActivity> typedQuery=entityManager.createQuery("SELECT customActivity FROM CustomActivity customActivity", CustomActivity.class);
            List<CustomActivity> customActivities=typedQuery.getResultList();

            log.info("\n Exited findAllCustomActivities method, successfully found "+ customActivities.size() +" activities \n");
            return copyCustomActivitiesToDto(customActivities);

        }catch(Exception ex){
            log.info("\nError in findAllCustomActivities method! "+ex.getMessage()+"\n");
            throw new EJBException(ex);
        }

    }

    private List<CustomActivityDto> copyCustomActivitiesToDto(List<CustomActivity> userActivities) {
        log.info("\n Entered copyCustomActivitiesToDto method with list size of: "+userActivities.size()+" \n");
        List<CustomActivityDto> listToReturn =new ArrayList<>();

        for(CustomActivity ca: userActivities){

            CustomActivityDto userActivityDtoTemp=new CustomActivityDto(ca.getId(),ca.getName(),ca.getDescription(),ca.getUser(),ca.getActivityPicture());
            listToReturn.add(userActivityDtoTemp);
        }

        log.info("\n Exited copyCustomActivitiesToDto method. \n");

        return listToReturn;
    }

    /**
     * Finds a certain custom activity by its id
     */
    public CustomActivityDto findCustomActivityById(Long id, List<CustomActivityDto> allUserActivities){

        log.info("\n Entered findCustomActivityById method for the id: "+ id +" \n");

        CustomActivityDto activityToReturn=new CustomActivityDto();

        for(CustomActivityDto ca: allUserActivities){
            if(ca.getId().equals(id)){
                activityToReturn=ca;
            }
        }

        log.info("\n Exited findCustomActivityById method \n");
        return activityToReturn;
    }

    /**
     * Updates an activity identified by the activityId.
     */
    public void updateCustomActivity(Long activityId, String activityName, String activityDescription) {

        log.info("\n Entered updateCustomActivity method for the id: "+ activityId + " \n");
        CustomActivity ca=entityManager.find(CustomActivity.class,activityId);
        ca.setName(activityName);
        ca.setDescription(activityDescription);
        log.info("\n Exited updateCustomActivity method for the id: "+ activityId + " \n");

    }

    /**
     * Deletes an activity identified by the activityId.
     */
    public void deleteCustomActivity(long activityId) {

        log.info("\n Entered deleteCustomActivity method, activity id: "+activityId+" \n");

        CustomActivity ua=entityManager.find(CustomActivity.class,activityId);
        entityManager.remove(ua);

        log.info("\n Exited deleteCustomActivity method \n");

    }

    /**
     * Finds all activities for a specific user.
     */
    public List<CustomActivityDto> findAllCustomActivitiesByUsername(String username, List<CustomActivityDto> allUserActivities){

        log.info("\n Entered findAllCustomActivitiesByUsername method for the username: "+ username +" \n");

        List<CustomActivityDto> listToReturn=new ArrayList<>();

        for(CustomActivityDto ca: allUserActivities){
            if(ca.getUser().getUsername().equals(username)){
                listToReturn.add(ca);
            }
        }

        log.info("\n Exited findAllCustomActivitiesByUsername method, found "+ listToReturn.size() +"custom activities \n");
        return listToReturn;
    }

    /**
     * Adds a new custom activity for the user identified by its username.
     */
    public void addCustomActivity(String username, String activityName, String activityDescription) {

        log.info("\n Entered addCustomActivity method for the username and activity name: "+ username + " "+  activityName  +" \n");

        CustomActivity ua=new CustomActivity();
        ua.setName(activityName);
        ua.setDescription(activityDescription);

        User user=entityManager.find(User.class,username);
        ua.setUser(user);
        entityManager.persist(ua);

        log.info("\n Exited addCustomActivity method \n");

    }


}
