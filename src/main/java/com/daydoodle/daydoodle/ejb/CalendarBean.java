package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.CalendarDto;
import com.daydoodle.daydoodle.entities.Calendar;
import com.daydoodle.daydoodle.entities.CalendarEvent;
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
public class CalendarBean {

    private static final Logger log= Logger.getLogger(CalendarBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Performs a query on the database in order to select all the calendars.
     */
    public List<CalendarDto> findAllCalendars(){
        log.info("\n Entered findAllCalendars method \n");
        try{
            TypedQuery<Calendar> typedQuery=entityManager.createQuery("SELECT c FROM Calendar c", Calendar.class);
            List<Calendar> calendars=typedQuery.getResultList();

            log.info("\n Exited findAllCalendars method \n");
            return copyCalendarsToDto(calendars);

        }catch(Exception ex){
            log.info("\n Error in findAllCalendars method! "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }
    }
    private List<CalendarDto> copyCalendarsToDto(List<Calendar> calendars) {

        log.info("\n Entered copyCalendarsToDto method with list size: " + calendars.size() + " \n");
        List<CalendarDto> listToReturn = new ArrayList<CalendarDto>();

        for (Calendar calendar : calendars) {
            CalendarDto calendarDtoTemp = new CalendarDto(calendar.getId(), calendar.getName(), calendar.getDescription(), calendar.getUsers(), calendar.getEvents());
            listToReturn.add(calendarDtoTemp);
        }

        log.info("\n Exited copyCalendarsToDto method. \n");
        return listToReturn;
    }

    /**
     * Returns the calendar that has the given id.
     */
    public CalendarDto findCalendarById(Long id, List<CalendarDto> allCalendars){

        log.info("\n Entered findCalendarById method \n");
        CalendarDto calendarToReturn = new CalendarDto();

        for(CalendarDto c: allCalendars){
            if(c.getId().equals(id)){
                calendarToReturn=c;
                break;
            }
        }
        log.info("\n Exited findCalendarById method. \n");
        return calendarToReturn;
    }

    /**
     * Creates a new calendar.
     */
    public void createCalendar(String username, String title, String description) {

        log.info("\n Entered createCalendar method. \n");

        Calendar newCalendar=new Calendar();
        newCalendar.setName(title);
        newCalendar.setDescription(description);
        newCalendar.setCreatedBy(username);

        User user=entityManager.find(User.class,username);
        user.getCalendars().add(newCalendar);
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        newCalendar.setUsers(userList);

        entityManager.persist(newCalendar);

        log.info("\n Exited createCalendar method. **\n");

    }

    /**
     * Finds all calendars for a specific user.
     */
    public List<CalendarDto> findAllCalendarsByUser(String username){
        log.info("\n Entered findAllCalendarsByUser method \n");
        List<CalendarDto> allCalendars=findAllCalendars();
        List<CalendarDto> userCalendars=new ArrayList<>();
        User user=entityManager.find(User.class,username);
        for(CalendarDto c: allCalendars){
            if(c.getUsers().contains(user)){
                userCalendars.add(c);
            }
        }
        log.info("\n Exited findAllCalendarsByUser method. \n");
        return userCalendars;
    }

    public void deleteEventFromCalendar(Long calendarIdLong, Long eventIdLong) {
        log.info("\n Entered deleteEventFromCalendar method \n");
        Calendar calendar=entityManager.find(Calendar.class,calendarIdLong);
        CalendarEvent calendarEvent=entityManager.find(CalendarEvent.class,eventIdLong);
        calendar.getEvents().remove(calendarEvent);
        entityManager.merge(calendar);
        log.info("\n Exited deleteEventFromCalendar method. \n");
    }
}
