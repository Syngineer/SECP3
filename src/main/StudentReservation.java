package main;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class StudentReservation {
    public static Queue<Reservation> reservationQueue = new LinkedList<>();
    public static  ArrayList<String> question = new ArrayList<>();
    public static ArrayList<String> email = new ArrayList<>();
    public static   ArrayList<Integer> time = new ArrayList<>();
    public static  ArrayList<String> tmp_question = new ArrayList<>();
    public static  JFrame f;


    public static void main(String[] args){

        Random rt = new Random();

        f=new JFrame("Welcome To Office Hour Reservation System");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        email.add("abc@buffalo.edu");
        email.add("efg@buffalo.edu");
        email.add("csdc12@buffalo.edu");
        email.add("abc234@buffalo.edu");

        time.add(5*60*1000);
        time.add(11*60*1000);
        time.add(5*60*1000);
        time.add(11*60*1000);

        tmp_question.add("How to study J Swing?");
        tmp_question.add("How to become a SDE?");

        Collections.shuffle(email);
        Collections.shuffle(time);
        Collections.shuffle(tmp_question);

        int t = rt.nextInt(5);

        performTaTask(t);
        f.setSize(800,600);
        f.setLayout(null);
        f.setVisible(true);


    }

    public static void performTaTask(int t) {

        if(t>0) {
            int pos = 50;
            int qSize = tmp_question.size();
            question = addQuestion(t,qSize,tmp_question);
            int reserveQueue  = populateReservationQueue(email,time,question,t);
            Reservation first=reservationQueue.poll();
            String content = "Email: "+ first.getEmail() + "\nQuestion: " + first.getQuestion();
            JTextArea textArea = new JTextArea(content);
            textArea.setBounds(50,pos, 300,60);
            textArea.setEditable(false);
            f.add(textArea);
            // System.out.println(reservationQueue);
            JButton b=new JButton("Mark Present");
            b.setBounds(50,250,150,30);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    first.setStatus("Present");
                    f.setVisible(false);
                    showQueue();
                }
            });
            f.add(b);

            JButton b2=new JButton("Mark Absent");
            b2.setBounds(300,250,150,30);
            b2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    first.setStatus("Absent");
                    f.setVisible(false);
                    if(System.currentTimeMillis()-first.getTime()<10*60*1000){
                        reservationQueue.add(first);
                    }
                    else{
                        JOptionPane.showMessageDialog(f, "Student Banned", "Absent",JOptionPane.WARNING_MESSAGE);
                        System.exit(0);
                    }
                    showQueue();
                }
            });
            f.add(b2);
        }else {
            JOptionPane.showMessageDialog(f, "There Are No Reservation At This Time!", "Warning",JOptionPane.WARNING_MESSAGE);
            System.exit(0);

        }
    }

    public static ArrayList<String> addQuestion(int t, int qSize, ArrayList<String> tmp_question) {

        ArrayList questionList = new ArrayList();
        if(t == 1) {
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(tmp_question.get(temp));

        }else if(t == 2) {
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(tmp_question.get(temp));
            questionList.add("");
        }else if(t==3){
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(tmp_question.get(temp));
            questionList.add("");
            questionList.add("");
        }else {
            int temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(tmp_question.get(temp));
            temp = Math.max((int) (Math.random()*qSize - 1), 0);
            questionList.add(tmp_question.get(temp));
            questionList.add("");
            questionList.add("");
        }

        return questionList;
    }

    public static int populateReservationQueue(ArrayList<String> email, ArrayList<Integer> time, ArrayList<String> question, int t) {

        for(int i = 0; i < t; i++) {
            Reservation reservation1=new Reservation(email.get(i),time.get(i),question.get(i));
            reservationQueue.add(reservation1);
        }

        return reservationQueue.size();

    }

    public static void showQueue() {
        JFrame f2=new JFrame("Reservation Queue");
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        int pos=50;

        for(int i = 0; i < reservationQueue.size(); i++) {
            Reservation reservation1=reservationQueue.poll();
            String content = "Email: "+ reservation1.getEmail() + "\nQuestion: " + reservation1.getQuestion();
            JTextArea textArea = new JTextArea(content);
            textArea.setBounds(50,pos, 300,60);
            textArea.setEditable(false);
            f2.add(textArea);
            pos += 100;

        }

        f2.setSize(800,600);
        f2.setLayout(null);
        f2.setVisible(true);

    }


}














