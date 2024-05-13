package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.CalendarEventDto;
import com.daydoodle.daydoodle.entities.*;
import jakarta.ejb.EJBException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CalendarEventBean {
    private static final Logger log= Logger.getLogger(CalendarEventBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Performs a query on the database in order to select all the calendar events.
     */
    public List<CalendarEventDto> findAllCalendarEvents(){
        log.info("\n Entered findAllCalendarEvents method \n");
        try{
            TypedQuery<CalendarEvent> typedQuery=entityManager.createQuery("SELECT ce FROM CalendarEvent ce", CalendarEvent.class);
            List<CalendarEvent> calendarEvents=typedQuery.getResultList();

            log.info("\n Exited findAllCalendarEvents method \n");
            return copyCalendarEventsToDto(calendarEvents);

        }catch(Exception ex){
            log.info("\n Error in findAllCalendarEvents method! "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }
    }
    private List<CalendarEventDto> copyCalendarEventsToDto(List<CalendarEvent> calendarEvents) {

        log.info("\n Entered copyCalendarEventsToDto method with list size: "+ calendarEvents.size() +" \n");
        List<CalendarEventDto> listToReturn = new ArrayList<CalendarEventDto>();

        for(CalendarEvent calendarEvent:calendarEvents){
            CalendarEventDto calendarEventDtoTemp=new CalendarEventDto(calendarEvent.getId(),calendarEvent.getTitle(),calendarEvent.getStart(),calendarEvent.getEnd(),calendarEvent.getDescription(),calendarEvent.getLocation(),calendarEvent.getUser(),calendarEvent.getActivity(),calendarEvent.getUserActivity(),calendarEvent.getCalendar());
            listToReturn.add(calendarEventDtoTemp);
        }

        log.info("\n Exited copyCalendarEventsToDto method. \n");
        return listToReturn;

    }

    /**
     * Adds an event in the calendar with a predefined activity.
     */
    public void addEventWithActivity (String title, LocalDateTime startDate, LocalDateTime endDate, String description, String location, String username, Long activityId, Long calendarId){
        log.info("\n Entered addEventWithActivity method. \n");

        CalendarEvent newEvent=new CalendarEvent();
        newEvent.setTitle(title);
        newEvent.setStart(startDate);
        newEvent.setEnd(endDate);
        newEvent.setDescription(description);
        newEvent.setLocation(location);
        User user=entityManager.find(User.class,username);
        newEvent.setUser(user);
        Activity activity=entityManager.find(Activity.class,activityId);
        newEvent.setActivity(activity);
        Calendar calendar=entityManager.find(Calendar.class,calendarId);
        calendar.getEvents().add(newEvent);
        newEvent.setCalendar(calendar);

        log.info("\n Exited addEventWithActivity method. \n");
        entityManager.persist(newEvent);
    }

    /**
     * Adds an event in the calendar with a custom activity.
     */
    public void addEventWithUserActivity (String title, LocalDateTime startDate, LocalDateTime endDate, String description,String location,String username,Long userActivityId,Long calendarId){
        log.info("\n Entered addEventWithUserActivity method. \n");

        CalendarEvent newEvent=new CalendarEvent();
        newEvent.setTitle(title);
        newEvent.setStart(startDate);
        newEvent.setEnd(endDate);
        newEvent.setDescription(description);
        newEvent.setLocation(location);
        User user=entityManager.find(User.class,username);
        newEvent.setUser(user);
        CustomActivity userActivity=entityManager.find(CustomActivity.class,userActivityId);
        newEvent.setUserActivity(userActivity);
        Calendar calendar=entityManager.find(Calendar.class,calendarId);
        calendar.getEvents().add(newEvent);
        newEvent.setCalendar(calendar);

        log.info("\n Exited addEventWithUserActivity method. \n");
        entityManager.persist(newEvent);
    }
}
