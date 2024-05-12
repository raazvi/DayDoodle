package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.UserDetailsDto;
import com.daydoodle.daydoodle.entities.Picture;
import com.daydoodle.daydoodle.entities.UserDetails;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    /**
     * Returns an object of type UserDetailsDto that contains the details of the user with the given username.
     * @param username - The username we are looking for
     * @param allUserDetails - A list of all the userDetails entries in the database
     */
    public UserDetailsDto getUserDetailsByUsername(String username, List<UserDetailsDto> allUserDetails) {

        log.info("\n Entered getUserDetailsByUsername method for the username: "+username+" \n");

        UserDetailsDto userToReturn=new UserDetailsDto();

        for(UserDetailsDto udd: allUserDetails){
            if(Objects.equals(udd.getUsername(), username)){
                userToReturn=udd;
            }
        }

        log.info("\n Exited getUserDetailsByUsername method. \n");
        return userToReturn;

    }

    /**
     * Updates the first name of a user based on their username.
     */
    public void updateFirstName(String username, String firstName) {
        log.info("\n Entered updateFirstName method for the username: "+username+" \n");

        UserDetails ud=entityManager.find(UserDetails.class,username);
        ud.setFirstName(firstName);

        log.info("\n Exited updateFirstName method. \n");
    }

    /**
     * Updates the last name of a user based on their username.
     */
    public void updateLastName(String username, String lastName) {
        log.info("\n Entered updateLastName method for the username: "+username+" \n");
        UserDetails ud=entityManager.find(UserDetails.class,username);
        ud.setLastName(lastName);
        log.info("\n Exited updateLastName method. \n");
    }

    /**
     * Updates the nickname of a user based on their username.
     */
    public void updateNickname(String username, String nickname) {
        log.info("\n Entered updateNickname method for the username: "+username+" \n");
        UserDetails ud=entityManager.find(UserDetails.class,username);
        ud.setNickname(nickname);
        log.info("\n Exited updateNickname method. \n");
    }

    /**
     * Updates the location of a user based on their username.
     */
    public void updateLocation(String username, String location) {
        log.info("\n Entered updateLocation method for the username: "+username+" \n");
        UserDetails ud=entityManager.find(UserDetails.class,username);
        ud.setLocation(location);
        log.info("\n Exited updateLocation method. \n");
    }

    /**
     * Updates the birthdate of a user based on their username.
     */
    public void updateBirthDate(String username, LocalDate birthDate) {
        log.info("\n Entered updateBirthDate method for the username: "+username+" \n");
        UserDetails ud=entityManager.find(UserDetails.class,username);
        ud.setBirthDate(birthDate);
        log.info("\n Exited updateBirthDate method. \n");
    }

    /**
     * Updates the zodiac sign of a user based on their username.
     */
    public void updateZodiacSign(String username, String zodiacSign) {
        log.info("\n Entered updateZodiacSign method for the username: " + username + " \n");
        UserDetails ud = entityManager.find(UserDetails.class, username);

        if (ud != null) {
            UserDetails.ZodiacSign sign;
            switch (zodiacSign.toLowerCase()) {
                case "aries":
                    sign = UserDetails.ZodiacSign.Aries;
                    break;
                case "taurus":
                    sign = UserDetails.ZodiacSign.Taurus;
                    break;
                case "gemini":
                    sign = UserDetails.ZodiacSign.Gemini;
                    break;
                case "cancer":
                    sign = UserDetails.ZodiacSign.Cancer;
                    break;
                case "leo":
                    sign = UserDetails.ZodiacSign.Leo;
                    break;
                case "virgo":
                    sign = UserDetails.ZodiacSign.Virgo;
                    break;
                case "libra":
                    sign = UserDetails.ZodiacSign.Libra;
                    break;
                case "scorpio":
                    sign = UserDetails.ZodiacSign.Scorpio;
                    break;
                case "sagittarius":
                    sign = UserDetails.ZodiacSign.Sagittarius;
                    break;
                case "capricorn":
                    sign = UserDetails.ZodiacSign.Capricorn;
                    break;
                case "aquarius":
                    sign = UserDetails.ZodiacSign.Aquarius;
                    break;
                case "pisces":
                    sign = UserDetails.ZodiacSign.Pisces;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid zodiac sign: " + zodiacSign);
            }
            ud.setZodiacSign(sign);
        } else {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
    }

    /**
     * Updates the pronouns of a user based on their username.
     */
    public void updatePronouns(String username, String pronouns) {
        log.info("\n Entered updatePronouns method for the username: "+username+" \n");
        UserDetails ud = entityManager.find(UserDetails.class, username);
        ud.setPronouns(pronouns);
        log.info("\n Exited updatePronouns method. \n");
    }

    /**
     * Updates the description of a user based on their username.
     */
    public void updateDescription(String username, String description) {
        log.info("\n Entered updateDescription method for the username: "+username+" \n");
        UserDetails ud = entityManager.find(UserDetails.class, username);
        ud.setDescription(description);
        log.info("\n Exited updateDescription method. \n");
    }

    /**
     * Updates the profile picture of a user based on their username.
     */
    public void updateProfilePicture(String username, byte[] imageData, String imageFormat) {
        log.info("\n Entered updateProfilePicture method for the username: " + username + " \n");
        UserDetails ud = entityManager.find(UserDetails.class, username);
        if (ud != null) {
            Picture picture = new Picture();
            picture.setImageData(imageData);
            picture.setImageFormat(imageFormat);
            ud.setProfilePicture(picture);
        } else {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
    }
}
