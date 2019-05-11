package main;

/**
 * The Reservation class represents a reservation made by a student.
 *
 * @author Alex Zhong
 * @author Rohan Gupta
 * @author Siddharth Pandey
 * @author Tanmay Pradeep Singh
 * @author Vivek Adithya Srinivasa Raghavan
 * @version 1.0
 * @since 2019-05-01
 */
public class Reservation {
    private String email;
    private long reservationTime;
    private String question;
    private String status;
    public long currentTime;

    public Reservation(String email, int timeDiff, String question) {
        this.currentTime=System.currentTimeMillis()-(4*60*60*1000);
        this.reservationTime=this.currentTime-timeDiff;
        this.status="Pending";
        this.email=email;
        this.question=question;
    }

    /**
     * Returns the email of student who made the reservation
     * @return Student's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the scheduled time of the reservation
     * @return Reservation time
     */
    public long getTime() {
        return reservationTime;
    }

    /**
     * Returns the question asked of student
     * @return Student's question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the status of the reservation (Pending, Present, Postponed or Banned)
     * @return Reservation's status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the reservations
     * @param status The current status
     */
    public void setStatus(String status) {
        this.status=status;
    }

    /**
     * Converts the time in milli seconds into appropriate format
     * @param timeInMillis The time in milli seconds
     * @return The time in required format
     */
    public String hhmmssFormat(long timeInMillis) {
        long second = (timeInMillis / 1000) % 60;
        long minute = (timeInMillis / (1000 * 60)) % 60;
        long hour = (timeInMillis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}