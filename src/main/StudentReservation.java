package main;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentReservation {
    public static Queue<Reservation> reservationQueue = new LinkedList<>();
    public static  ArrayList<String> question = new ArrayList<>();
    public static ArrayList<String> email = new ArrayList<>();
    public static   ArrayList<Integer> time = new ArrayList<>();
    public static  ArrayList<String> allQuestions = new ArrayList<>();
    public static  JFrame mainFrame;


    public static void main(String[] args){
        Random rt = new Random();

        mainFrame = new JFrame("TA Office Hours Reservation System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        int t = rt.nextInt(5);
        performTaTask(t);

        mainFrame.setSize(640,400);
        mainFrame.setLayout(null);
        mainFrame.getContentPane().setBackground(Color.WHITE);
        mainFrame.setVisible(true);
    }

    public static void performTaTask(int t) {
        if(t > 0) {
            int pos = 50;
            int qSize = allQuestions.size();
            question = addQuestion(t, qSize, allQuestions);
            populateReservationQueue(email, time, question, t);

            Reservation first = reservationQueue.poll();

            String content = "\n Student's Email: "+ first.getEmail() + "\n Question: " + first.getQuestion()+"\n Reservation Time: "+first.hhmmssFormat(first.getTime());

            JTextArea textArea = new JTextArea(content);
            textArea.setBounds(50,pos, 400,80);
            textArea.setEditable(false);
            textArea.setBackground(Color.LIGHT_GRAY);
            mainFrame.add(textArea);

            String content1 = "Current Time:\n" + first.hhmmssFormat(first.currentTime);
            JTextArea textArea1 = new JTextArea(content1);
            textArea1.setBounds(500,pos, 100,60);
            textArea1.setEditable(false);
            //textArea1.setBackground(Color.LIGHT_GRAY);
            mainFrame.add(textArea1);

            JButton presentButton=new JButton("Mark Present");
            presentButton.setBounds(50,250,150,40);
            presentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    first.setStatus("Present");
                    mainFrame.setVisible(false);
                    showQueue();
                }
            });
            presentButton.setBackground(Color.LIGHT_GRAY);
            presentButton.setOpaque(true);
            //presentButton.setBorderPainted(false);
            //presentButton.setBorder(BorderFactory.createEtchedBorder());
            mainFrame.add(presentButton);

            JButton absentButton = new JButton("Mark Absent");
            absentButton.setBounds(300,250,150,40);
            absentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    first.setStatus("Pending (Postponed)");
                    mainFrame.setVisible(false);
                    if((System.currentTimeMillis()-(4*60*60*1000))-first.getTime()<10*60*1000){
                        reservationQueue.add(first);
                    } else {
                        SimpleDateFormat formatter= new SimpleDateFormat("MM-dd-yyyy 'at' HH:mm:ss z");
                        Date date = new Date(System.currentTimeMillis());
                        JOptionPane.showMessageDialog(mainFrame, "Student " + first.getEmail() + " banned for the next 11 days. Ban Date : " + formatter.format(date), "Absent",JOptionPane.WARNING_MESSAGE);
                    }
                    showQueue();
                }
            });
            absentButton.setBackground(Color.LIGHT_GRAY);
            absentButton.setOpaque(true);

            mainFrame.add(absentButton);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "There Are No Reservation At This Time!", "Warning",JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }

    public static ArrayList<String> addQuestion(int t, int qSize, ArrayList<String> tmp_question) {
        ArrayList<String> questionList = new ArrayList<>();
        if (t == 1) {
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(tmp_question.get(temp));
        } else if (t == 2) {
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(tmp_question.get(temp));
            questionList.add("");
        } else if (t==3){
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(tmp_question.get(temp));
            questionList.add("");
            questionList.add("");
        } else {
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(tmp_question.get(temp));
            allQuestions.remove(temp);
            temp = Math.max((int) (Math.random()*qSize - 2), 0);
            questionList.add(tmp_question.get(temp));
            questionList.add("");
            questionList.add("");
        }
        return questionList;
    }

    public static int populateReservationQueue(ArrayList<String> email, ArrayList<Integer> time, ArrayList<String> question, int t) {
        for(int i = 0; i < t; i++) {
            Random rt = new Random();
            Integer tempEmail = rt.nextInt(email.size());
            Reservation reservation = new Reservation(email.get(tempEmail), time.get(rt.nextInt(2)), question.get(i));
            email.remove(email.get(tempEmail));
            reservationQueue.add(reservation);
        }
        return reservationQueue.size();
    }

    public static void showQueue() {
        JFrame queueFrame = new JFrame("Reservation Queue");
        queueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int pos = 20;
        String startText = "          ======== Start of Queue ========";
        JTextArea startArea = new JTextArea(startText);
        startArea.setBounds(50,pos, 400,20);
        startArea.setEditable(false);
        queueFrame.add(startArea);
        pos += 40;

        int num = 1;
        for(Reservation reservation : reservationQueue) {
            String content = num++ + ".\nEmail: "+ reservation.getEmail() + "\nQuestion: " + reservation.getQuestion()+
                    "\nResetvation Time: " + reservation.hhmmssFormat(reservation.getTime())+"\nReservation Status: "+reservation.getStatus();
            JTextArea textArea = new JTextArea(content);
            textArea.setBounds(50,pos, 400,100);
            textArea.setEditable(false);
            queueFrame.add(textArea);
            pos += 120;
        }

        String endText = "          ======== End of Queue ========";
        JTextArea endArea = new JTextArea(endText);
        endArea.setBounds(50, pos, 400,20);
        endArea.setEditable(false);
        queueFrame.add(endArea);
        pos += 60;

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
        queueFrame.setVisible(true);
    }
}














