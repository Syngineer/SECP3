package main;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * The StudentReservation program implements the TA Hours Reservation system.
 *
 * @author Alex Zhong
 * @author Rohan Gupta
 * @author Siddharth Pandey
 * @author Tanmay Pradeep Singh
 * @author Vivek Adithya Srinivasa Raghavan
 * @version 1.0
 * @since 2019-05-01
 */
public class StudentReservation {
    // Data structures for maintaining dummy data
    public static ArrayList<String> email = new ArrayList<>();
    public static ArrayList<Integer> time = new ArrayList<>();
    public static ArrayList<String> allQuestions = new ArrayList<>();

    // List for maintaining the banned students
    public static ArrayList<String> bannedStudents = new ArrayList<>();

    // Declaring a JFrame
    public static JFrame mainFrame;

    /**
     * The main method is the entry point of the program.
     * @param args Unused
     */
    public static void main(String[] args) {
        createDummyData();
        performTaTask(new Random().nextInt(5));
    }

    /**
     * The createDummyData method creates dummy data for populating the reservation queue.
     */
    public static void createDummyData() {
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

        time.add(5*60*1000);
        time.add(11*60*1000);

        allQuestions.add("How to study JSwings?");
        allQuestions.add("How to become a Software Development Engineer?");
        allQuestions.add("What is Software Development Life Cycle?");
        allQuestions.add("What are the different types of models in SDLC?");
        allQuestions.add("What is the role of a Software Project Manager?");
        allQuestions.add("What are Software Metrics?");
        allQuestions.add("What is Cohesion?");
        allQuestions.add("What is coupling?");
        allQuestions.add("What is black-box testing?");
        allQuestions.add("What is the purpose of design documentation?");

        Collections.shuffle(email);
        Collections.shuffle(time);
        Collections.shuffle(allQuestions);
    }

    /**
     * The performTaTask method creates the reservation queue, removes the first reservation from the queue if required
     * and displays the needed information to the user. It also allows the user to report the student's attendance.
     * @param numStudents The number of students in the queue
     */
    public static void performTaTask(int numStudents) {
        // Queue for maintaining the reservations
        Queue<Reservation> appointmentQueue = new LinkedList<>();

        mainFrame = new JFrame("TA Office Hours Reservation System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if(numStudents > 0) {
            // If there are students in the reservation queue (1-4)

            int pos = 50;
            List<String> question = addQuestion(numStudents, allQuestions);

            // Populating the reservation queue with dummy data according to the requirements
            populateAppointmentQueue(appointmentQueue, email, time, question, numStudents);

            // Polling the first reservation from the reservation queue
            Reservation firstReservation = appointmentQueue.poll();

            // Displaying the details of the first reservation
            String content = "\n Student's EmailId: "+ firstReservation.getEmail() + "\n Question: " + firstReservation.getQuestion()
                    + "\n Reservation Time: " + firstReservation.hhmmssFormat(firstReservation.getTime()) + " EST";
            JTextArea textArea = new JTextArea(content);
            textArea.setBounds(50,pos, 400,80);
            textArea.setEditable(false);
            textArea.setForeground(Color.WHITE);
            textArea.setBackground(Color.DARK_GRAY);
            mainFrame.add(textArea);

            // Creating text area to show current time
            JTextArea timeArea = new JTextArea("Current Time:");
            timeArea.setBounds(500,pos, 100,20);
            timeArea.setEditable(false);
            timeArea.setBackground(Color.LIGHT_GRAY);
            mainFrame.add(timeArea);

            JLabel timeLabel = new JLabel();
            timeLabel.setBounds(500,pos+20, 100,20);
            mainFrame.add(timeLabel);

            final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss 'EST'");
            ActionListener timerListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Date date = new Date();
                    String time = timeFormat.format(date);
                    timeLabel.setText(time);
                }
            };
            Timer timer = new Timer(1000, timerListener);
            timer.setInitialDelay(0);
            timer.start();

            // Creating button to mark the student present
            JButton presentButton = new JButton("Mark Present");
            presentButton.setBounds(50,250,150,40);
            presentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.setVisible(false);
                    AttendanceHandler.markStudentPresent(appointmentQueue, firstReservation);
                    showQueue(appointmentQueue);
                }
            });
            mainFrame.add(presentButton);

            // Creating button to mark the student absent
            JButton absentButton = new JButton("Mark Absent");
            absentButton.setBounds(300,250,150,40);
            absentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.setVisible(false);
                    AttendanceHandler.markStudentAbsent(appointmentQueue, firstReservation, bannedStudents);
                    if((System.currentTimeMillis()-(4*60*60*1000)) - firstReservation.getTime() >= 10*60*1000){
                        SimpleDateFormat formatter= new SimpleDateFormat("MM-dd-yyyy 'at' HH:mm:ss 'EST'");
                        Date date = new Date(System.currentTimeMillis());
                        JOptionPane.showMessageDialog(mainFrame, "Student " + firstReservation.getEmail() + " banned for the next 11 days. Ban Date : " + formatter.format(date), "Absent",JOptionPane.WARNING_MESSAGE);
                    }
                    showQueue(appointmentQueue);
                }
            });
            mainFrame.add(absentButton);

            mainFrame.setSize(640,400);
            mainFrame.setLayout(null);
            mainFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
            mainFrame.setVisible(true);
        } else {
            // Showing a popup when there are no reservation in the queue
            JOptionPane.showMessageDialog(mainFrame, "There are no appointments in the queue at this time.",
                    "Queue Empty", JOptionPane.WARNING_MESSAGE);
            mainFrame.setVisible(false);
            showQueue(appointmentQueue);
        }
    }

    /**
     * The addQuestion method populates the list of required questions from the whole question bank.
     * @param numStudents The number of students in the queue
     * @return The populated question list
     */
    public static ArrayList<String> addQuestion(int numStudents, List<String> allQuestions) {
        int qSize = allQuestions.size();
        ArrayList<String> questionList = new ArrayList<>();
        if (numStudents == 1) {
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(allQuestions.get(temp));
        } else if (numStudents == 2) {
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(allQuestions.get(temp));
            questionList.add("");
        } else if (numStudents == 3){
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(allQuestions.get(temp));
            questionList.add("");
            questionList.add("");
        } else if (numStudents > 3) {
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(allQuestions.get(temp));
            allQuestions.remove(temp);
            temp = Math.max((int) (Math.random()*qSize - 2), 0);
            questionList.add(allQuestions.get(temp));
            questionList.add("");
            questionList.add("");
        }
        return questionList;
    }

    /**
     * The populateAppointmentQueue populates the reservation queue with dummy data according to the number of students.
     * @param appointmentQueue The queue of reservations
     * @param email The list of student email ids
     * @param time The list of reservation times
     * @param question The list of questions
     * @param numStudents The number of students in the queue
     * @return The populated reservation queue
     */
    public static Queue<Reservation> populateAppointmentQueue(Queue<Reservation> appointmentQueue, List<String> email
            , List<Integer> time, List<String> question, int numStudents) {
        Random random = new Random();
        for(int i = 0; i < numStudents; i++) {
            Integer tempEmail = random.nextInt(email.size());
            Reservation reservation = new Reservation(email.get(tempEmail), time.get(random.nextInt(2)), question.get(i));
            email.remove(email.get(tempEmail));
            appointmentQueue.add(reservation);
        }
        return appointmentQueue;
    }

    /**
     * The showQueue method displays the current status of the reservation queue
     * @param appointmentQueue The queue of reservations
     */
    public static void showQueue(Queue<Reservation> appointmentQueue) {
        JFrame queueFrame = new JFrame("Reservation Queue");
        queueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int pos = 20;
        String startText = "=============== Start of Queue ===============";
        JTextArea startArea = new JTextArea(startText);
        startArea.setBounds(50,pos, 400,20);
        startArea.setEditable(false);
        startArea.setBackground(Color.LIGHT_GRAY);
        queueFrame.add(startArea);
        pos += 40;

        if (appointmentQueue.size() > 0) {
            int num = 1;
            for (Reservation reservation : appointmentQueue) {
                String content = num++ + ".\nStudent's EmailId: " + reservation.getEmail() + "\nQuestion: " + reservation.getQuestion() +
                        "\nReservation Time: " + reservation.hhmmssFormat(reservation.getTime()) + " EST" + "\nReservation Status: " + reservation.getStatus();
                JTextArea textArea = new JTextArea(content);
                textArea.setBounds(50, pos, 400, 100);
                textArea.setEditable(false);
                textArea.setForeground(Color.WHITE);
                textArea.setBackground(Color.DARK_GRAY);
                queueFrame.add(textArea);
                pos += 120;
            }
        } else {
            String content = "Queue is Empty!";
            JTextArea textArea = new JTextArea(content);
            textArea.setBounds(50, pos, 400, 20);
            textArea.setEditable(false);
            textArea.setBackground(Color.LIGHT_GRAY);
            queueFrame.add(textArea);
            pos += 40;
        }

        String endText = "=============== End of Queue ===============";
        JTextArea endArea = new JTextArea(endText);
        endArea.setBounds(50, pos, 400,20);
        endArea.setEditable(false);
        endArea.setBackground(Color.LIGHT_GRAY);
        queueFrame.add(endArea);

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(50, 720,150,40);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        queueFrame.add(closeButton);

        queueFrame.setSize(800, 800);
        queueFrame.setLayout(null);
        queueFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
        queueFrame.setVisible(true);
    }
}














