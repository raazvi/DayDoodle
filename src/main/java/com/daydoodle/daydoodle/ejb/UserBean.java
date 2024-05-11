package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.UserDto;
import com.daydoodle.daydoodle.entities.User;
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
public class UserBean {
    private static final Logger log= Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds all the users existing in the database
     */
    public List<UserDto> findAllUsers(){
        log.info("\n Entered findAllUsers method \n");
        try{
            TypedQuery<User> typedQuery=entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users=typedQuery.getResultList();

            log.info("\n Exited findAllUsers method \n");
            return copyUsersToDto(users);

        }catch(Exception ex){
            log.info("\n Error in findAllUsers method: "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }
    }

    /**
     * Copies the list of User objects into a list of UserDto objects.
     */
    public List<UserDto> copyUsersToDto(List<User> users) {
        log.info("\n Entered copyUsersToDto method with list size: "+ users.size() +" \n");
        List<UserDto> listToReturn = new ArrayList<UserDto>();

        for(User currentUser:users){
            UserDto userDtoTemp=new UserDto(currentUser.getUsername(),currentUser.getEmail(),currentUser.getPassword(),currentUser.getDateJoined(),currentUser.isFirstLogin());
            listToReturn.add(userDtoTemp);
        }

        log.info("\n Exited copyUsersToDto method. \n");
        return listToReturn;
    }

    public UserDto findUserByUsername(String username, List<UserDto> userList){
        log.info("\n Entered findUserByUsername method with the username: "+ username +" \n");
        UserDto foundUser=null;

        for(UserDto u: userList){
            if(Objects.equals(u.getUsername(), username)){
                foundUser=u;
            }
        }

        log.info("\n Exited findUserByUsername method. \n");
        return foundUser;

    }
//
//    public void updateUser(UserDto newUser){
//        log.info("\n** Entered updateUser method with the new values for username: "+ newUser.getUsername() +" email: "+ newUser.getEmail() + " password: "+ newUser.getPassword() +"**\n");
//
//        User user=entityManager.find(User.class, newUser.getUsername());
//        user.setUsername(newUser.getUsername());
//        user.setEmail(newUser.getEmail());
//        user.setPassword(newUser.getPassword());
//
//        log.info("\n** Exited updateUser method. **\n");
//    }
//
//    public void deleteUser(String username){
//
//        log.info("\n** Entered deleteUser method with the username "+username+" **\n");
//
//        User user=entityManager.find(User.class,username);
//        entityManager.remove(user);
//        UserDetails userDetails=entityManager.find(UserDetails.class,username);
//        entityManager.remove(userDetails);
//
//        log.info("\n** Exited deleteUser method **\n");
//
//    }
//
//    public List<UserDto> getSearchResultByKeyword (String keyword, List<UserDto> allUsers){
//
//        log.info("\n** Entered getSearchResultByKeyword method with keyword: " + keyword + " **\n");
//
//        List<UserDto> searchResults = new ArrayList<>();
//
//        for (UserDto user : allUsers) {
//            if (user.getUsername().toLowerCase().contains(keyword.toLowerCase())) {
//                // If the username contains the keyword (case-insensitive), add it to the search results
//                searchResults.add(user);
//            }
//        }
//
//        log.info("\n** Exited getSearchResultByKeyword method. **\n");
//        return searchResults;
//
//    }
//
    /**
     * Finds all the usernames already existing in the table.
     * @return A list of all the usernames of the users already registered.
     */
    public List<String> getExistingUsernames() {

        List<UserDto> allUsers = findAllUsers();
        List<String> allUsernames = new ArrayList<String>();

        for(UserDto u: allUsers){
            allUsernames.add(u.getUsername());
        }

        return allUsernames;

    }

    /**
     * Create a new user in the database.
     */
    public void createUser(String username, String email, String hashedPassword) {
        User user=new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        user.setDateJoined(LocalDate.now());
        user.setFirstLogin(true);
//        user.setCalendars(new ArrayList<Calendar>());
//        user.setPosts(new ArrayList<Post>());
//        user.setCalendarEvents(new ArrayList<CalendarEvent>());
//        user.setDiaryPages(new ArrayList<Diary>());
        entityManager.persist(user);

        //Create userDetails for this user...
        UserDetails ud=new UserDetails();
        ud.setUser(user);
        entityManager.persist(ud);

    }

    /**
     * Sets the first login property to false
     */
    public void setFirstLoginFalse(String username) {
        log.info("\n Entered setFirstLoginFalse, user:" + username+ " \n");
        User user=entityManager.find(User.class, username);
        user.setFirstLogin(false);
        log.info("\n Exited setFirstLoginFalse method. \n");
    }
}