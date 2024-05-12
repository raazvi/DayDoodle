package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.ActivityDto;
import com.daydoodle.daydoodle.entities.Activity;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class ActivityBean {

    private static final Logger log= Logger.getLogger(ActivityBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Returns a list of all the activities in the database.
     */
    public List<ActivityDto> findAllActivities(){
        log.info("\n Entered findAllActivities method \n");

        try{
            TypedQuery<Activity> typedQuery=entityManager.createQuery("SELECT activity FROM Activity activity", Activity.class);
            List<Activity> activities=typedQuery.getResultList();

            log.info("\n Exited findAllActivities method, successfully found "+ activities.size() +" activities \n");
            return copyActivitiesToDto(activities);

        }catch(Exception ex){
            log.info("\n Error in findAllActivities method! "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }

    }

    private List<ActivityDto> copyActivitiesToDto(List<Activity> activities) {
        log.info("\n Entered copyActivitiesToDto method with list size of: "+activities.size()+" \n");
        List<ActivityDto> listToReturn =new ArrayList<>();

        for(Activity a: activities){

            ActivityDto activityDtoTemp=new ActivityDto(a.getId(),a.getName(),a.getDescription(),a.getActivityPicture());
            listToReturn.add(activityDtoTemp);
        }

        log.info("\n Exited copyActivitiesToDto method. \n");

        return listToReturn;
    }

    /**
     * Finds the activity by the given id.
     */
    public ActivityDto findActivityById(Long id, List<ActivityDto> allActivities){

        log.info("\n Entered findActivityById method for the id: "+ id +" \n");

        ActivityDto activityToReturn=new ActivityDto();

        for(ActivityDto a: allActivities){
            if(a.getId().equals(id)){
                activityToReturn=a;
            }
        }

        log.info("\n Exited findActivityById method \n");
        return activityToReturn;
    }

}
