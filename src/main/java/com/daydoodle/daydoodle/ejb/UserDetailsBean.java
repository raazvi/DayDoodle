package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.UserDetailsDto;
import com.daydoodle.daydoodle.entities.UserDetails;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserDetailsBean {

    private static final Logger log= Logger.getLogger(UserDetailsBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds all the user details stored in the database
     */
    public List<UserDetailsDto> findAllUserDetails(){
        log.info("\n Entered findAllUserDetails method \n");

        try{
            TypedQuery<UserDetails> typedQuery=entityManager.createQuery("SELECT ud FROM UserDetails ud", UserDetails.class);
            List<UserDetails> users=typedQuery.getResultList();

            log.info("\n Exited findAllUsers method \n");
            return copyUserDetailsToDto(users);

        }catch(Exception ex){
            log.info("\n Error in findAllUserDetails method: "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }
    }

    /**
     * Copies the list of UserDetails and converts it into UserDetailsDto for security reasons.
     */
    public List<UserDetailsDto> copyUserDetailsToDto(List<UserDetails> userDetailsList) {
        log.info("\n Entered copyUserDetailsToDto method with list size of: "+userDetailsList.size()+" \n");
        List<UserDetailsDto> listToReturn =new ArrayList<>();

        for(UserDetails ud: userDetailsList){
            UserDetailsDto userDetailsDtoTemp=new UserDetailsDto(ud.getUsername(),ud.getFirstName(),ud.getLastName(),ud.getNickname(),ud.getDescription(),ud.getPronouns(),ud.getLocation(),ud.getBirthDate(),ud.getProfilePicture(),ud.getZodiacSign());
            listToReturn.add(userDetailsDtoTemp);
        }

        log.info("\n Exited copyUserDetailsToDto method. \n");
        return listToReturn;
    }

}
