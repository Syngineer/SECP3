package test;


import main.AttendanceHandler;
import main.Reservation;
import main.StudentReservation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.*;


public class TestMain {
    ArrayList<String> email = new ArrayList<>();
    ArrayList<Integer> time = new ArrayList<>();

    ArrayList<Integer> timeUnderEleven = new ArrayList<>();
    ArrayList<Integer> timeOverEleven = new ArrayList<>();
    ArrayList<String> tmp_question = new ArrayList<>();
    Random random=new Random();
    StudentReservation studentReservation = new StudentReservation();


    /**
     * The following test case will consider the scenario that the student is delayed but is present within 10 minutes
     * of appointment time, then the test case will test that the status of the student is changed to postponed.
     * and the student is added to the bottom of the appointment queue.
     */
    @Test
    public void testAppointmentPostponed(){

        setup();
        int min = 1;
        int max = 4;
        int t = random.nextInt((max - min) + 1) + min;
        ArrayList<String> bannedStudentLsit=new ArrayList<>();
        ArrayList<String> question = new ArrayList<>();
        question = studentReservation.addQuestion(t, tmp_question);
        Queue<Reservation> entriesInReservationQueue = new LinkedList<>();
        entriesInReservationQueue = studentReservation.populateAppointmentQueue(entriesInReservationQueue, email,
                timeUnderEleven, question, t);
        Reservation first = entriesInReservationQueue.poll();
        Reservation lastElementInQueueAfterAbsent = new Reservation();
        int count=0;
        Queue<Reservation> queueAfterAbsentMarked = AttendanceHandler.markStudentAbsent(entriesInReservationQueue
                , first, bannedStudentLsit);

        for(Reservation s: queueAfterAbsentMarked) {
            if (s.getEmail().equals(first.getEmail())) {

                lastElementInQueueAfterAbsent = s;
                count++;
            } else {
                count++;
            }
        }

        // if the count is equal to queuAfterAbsentMarked Size it implies that the element found is a the end of the
        // queue, which is nothing but the element that was marked absent by the TA
        Assert.assertTrue(count==queueAfterAbsentMarked.size());
        Assert.assertTrue(lastElementInQueueAfterAbsent.getStatus().equals("Postponed"));



    }


    /**
     *
     * The following test case will test that if the student is late by more than 10 min and is marked absent by the
     * TA then the student status is changed to banned and is addded to the banned list.
     * The test case will also check that the banned student has been eliminated  from the appointment queue.
     */
    @Test
    public void testStudentBanned(){

        setup();
        int min = 1;
        int max = 4;
        int t = random.nextInt((max - min) + 1) + min;
        ArrayList<String> bannedStudentLsit=new ArrayList<>();
        //System.out.println("T in method" + t);
        ArrayList<String> question = new ArrayList<>();
        question = studentReservation.addQuestion(t, tmp_question);
        Queue<Reservation> entriesInReservationQueue = new LinkedList<>();
        entriesInReservationQueue = studentReservation.populateAppointmentQueue(entriesInReservationQueue,email,
                timeOverEleven, question, t);
        Reservation first = entriesInReservationQueue.poll();
        int count=0;
        Queue<Reservation> queueAfterAbsentMarked = AttendanceHandler.markStudentAbsent(entriesInReservationQueue,
                first,bannedStudentLsit);

        List<String> listOfStudentEmailAfterBanning = new ArrayList<>();
        for(Reservation reservation:queueAfterAbsentMarked){
            listOfStudentEmailAfterBanning.add(reservation.getEmail());
        }

        Assert.assertTrue(first.getEmail().equals(bannedStudentLsit.get(0)));
        Assert.assertFalse(listOfStudentEmailAfterBanning.contains(first.getEmail()));


    }


    /**
     *The following test case will test that when the student is marked present by the TA then the status of the student
     * is set to present and the reservation queue only contains the pending reservations.
     */
    @Test
    public void testAttendanceHandlingWhenStudentPresent() {

        setup();
        int min = 2;
        int max = 4;
        int t = random.nextInt((max - min) + 1) + min;

        //System.out.println("T in method" + t);
        ArrayList<String> question = new ArrayList<>();
        question = studentReservation.addQuestion(t, tmp_question);
        Queue<Reservation> entriesInReservationQueue = new LinkedList<>();

        entriesInReservationQueue = studentReservation.populateAppointmentQueue(entriesInReservationQueue,email,
                time, question, t);
        Reservation first = entriesInReservationQueue.poll();
        Queue<Reservation> queueAfterPresentMarked = AttendanceHandler.markStudentPresent(entriesInReservationQueue,
                first);

        List<String> listOfStudentEmailAfterAttendance = new ArrayList<>();
        for(Reservation reservation:entriesInReservationQueue){
            listOfStudentEmailAfterAttendance.add(reservation.getEmail());
        }

        Assert.assertTrue(queueAfterPresentMarked.size() == t - 1);
        Assert.assertTrue(first.getStatus().equals("Present"));
        Assert.assertFalse(listOfStudentEmailAfterAttendance.contains(first.getEmail()));

    }




    /**
     * As per the requirement's document the following test case will test that the question list is of the same size as the appointment queue and
     * always half of the randomly selected questions in appointments are empty.
     */

    @Test
    public void testQuestionCountForAppointments(){
        setup();
        int min= 4;
        int max = Integer.MAX_VALUE;
        int t= random.nextInt((max - min) + 1) + min;
        ArrayList<String> question = studentReservation.addQuestion(t, tmp_question);
        Assert.assertEquals(4,question.size());
        int emptyQuestionsCount = getEmptyQuestions(question);
        Assert.assertEquals(2,emptyQuestionsCount);

    }


    /**
     * The Assert statement in the following test case will test that the appointment queue is populated randomly
     * (0-4 appointments) and contains a minimum of 0 and a maximum of 4 reservations.
     */

    @Test
    public void testPopulateAppointmentQueue(){
        setup();
        int min= 0;
        int max = 4;
        int t= random.nextInt((max - min) + 1) + min;
        ArrayList<String> question = studentReservation.addQuestion(t, tmp_question);
        Queue<Reservation> entriesInReservationQueue=new LinkedList<>();
        entriesInReservationQueue = studentReservation.populateAppointmentQueue(entriesInReservationQueue,email,time,
                question,t);
        Assert.assertEquals(t,entriesInReservationQueue.size());

    }

    /**
     * The following test case will test that at any given time slot no student will have duplicate appointments.
     */

    @Test
    public void testNoDuplicateAppointments() {


        setup();
        int min = 0;
        int max = 4;
        ArrayList<String> emails = new ArrayList<>();
        int t = random.nextInt((max - min) + 1) + min;
        t=4;
        ArrayList<String> question = studentReservation.addQuestion(t, tmp_question);
        Queue<Reservation> entriesInReservationQueue = new LinkedList<>();

        entriesInReservationQueue = studentReservation.populateAppointmentQueue(entriesInReservationQueue,email, time,
                question, t);
        Set<String> set = new HashSet<>();
        int emailCountInReservationQueue = 0;

        for (Reservation reservations : entriesInReservationQueue) {
            set.add(reservations.getEmail());
            emailCountInReservationQueue++;

        }
        Assert.assertEquals(emailCountInReservationQueue,set.size());

    }

    /**
     * The setup method is used to populate dummy data for running JUnits
     */
    void setup() {

        email.add("khaleesi@buffalo.edu");
        email.add("johnwick@buffalo.edu");
        email.add("tonystark@buffalo.edu");
        email.add("thanos@buffalo.edu");
        email.add("thor@buffalo.edu");
        email.add("hulk@buffalo.edu");
        email.add("loki@buffalo.edu");
        email.add("brucewayne@buffalo.edu");
        email.add("harleyquinn@buffalo.edu");
        email.add("peterparker@buffalo.edu");

        time.add(11*60*1000);
        time.add(5*60*1000);
        timeUnderEleven.add(5*60*1000);
        timeUnderEleven.add(5*60*1000);
        timeOverEleven.add(11*60*1000);
        timeOverEleven.add(11*60*1000);
        //time.add(11*60*1000);
        //time.add(11*60*1000);

        tmp_question.add("How to study J Swing?");
        tmp_question.add("How to become a SDE?");
        tmp_question.add("What is SDLC or Software Development Life Cycle?");
        tmp_question.add("What are the different types of models available in SDLC?");
        tmp_question.add("What is the role of a Software Project Manager?");
        tmp_question.add("What are Software Metrics?");
        tmp_question.add("What is Cohesion?");
        tmp_question.add("What is coupling?");
        tmp_question.add("What is black-box testing?");
        tmp_question.add("What is the purpose of design documentation?");
        Collections.shuffle(email);
        Collections.shuffle(time);
        Collections.shuffle(tmp_question);
    }

    private int getEmptyQuestions(ArrayList<String> question) {
        int count = 0;
        for (String s : question) {
            if (s.equals("")) {
                count++;
            }
        }
        return count;
    }
}
