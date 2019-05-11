package main;

import java.util.ArrayList;
import java.util.Queue;

/**
 * The AttendanceHandler class handles the attendance of the student and changes the status of the reservation queue
 * accordingly.
 *
 * @author Alex Zhong
 * @author Rohan Gupta
 * @author Siddharth Pandey
 * @author Tanmay Pradeep Singh
 * @author Vivek Adithya Srinivasa Raghavan
 * @version 1.0
 * @since 2019-05-05
 */
public class AttendanceHandler {

    /**
     * The markStudentPresent method handles the case when the student is present for the appointment.
     *
     * @param appointmentQueue The queue of reservations
     * @param firstReservation The first reservation in the queue
     * @return The updated reservation queue
     */
    public static Queue<Reservation> markStudentPresent(Queue<Reservation> appointmentQueue, Reservation firstReservation) {
        firstReservation.setStatus("Present");
        return appointmentQueue;
    }

    /**
     * The markStudentAbsent method handles the case when the student is absent for the appointment.
     *
     * @param appointmentQueue The queue of reservations
     * @param firstReservation The first reservation in the queue
     * @param bannedStudents The list of currently banned students
     * @return The updated reservation queue
     */
    public static Queue<Reservation> markStudentAbsent(Queue<Reservation> appointmentQueue, Reservation firstReservation, ArrayList<String> bannedStudents) {
        if((System.currentTimeMillis()-(4*60*60*1000)) - firstReservation.getTime()<10*60*1000){
            appointmentQueue.add(firstReservation);
            firstReservation.setStatus("Postponed");
        } else {
            firstReservation.setStatus("Banned");
            bannedStudents.add(firstReservation.getEmail());
        }
        return appointmentQueue;
    }
}