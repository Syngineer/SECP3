package test;


import main.StudentReservation;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.junit.jupiter.api.Test;


public class TestMain {
    ArrayList<String> email = new ArrayList<>();
    ArrayList<Integer> time = new ArrayList<>();
    ArrayList<String> tmp_question = new ArrayList<>();
    Random random=new Random();
    StudentReservation studentReservation = new StudentReservation();


    @Test
    public void testAddQuestion(){
        setup();
        int min= 1;
        int max = 3;
        int t= random.nextInt((max - min) + 1) + min;
        //ArrayList<String> question = studentReservation.addQuestion(t,tmp_question.size(),tmp_question);
        //Assert.assertEquals(t,question.size());
    }


    @Test
    public void testAddQuestionWithZero(){
        setup();
        int t= 0;
        //ArrayList<String> question = studentReservation.addQuestion(t,tmp_question.size(),tmp_question);
        //Assert.assertEquals(4,question.size());
    }


    @Test
    public void testAddQuestionWithValueGreaterThanThree(){
        setup();
        int min= 4;
        int max = Integer.MAX_VALUE;
        int t= random.nextInt((max - min) + 1) + min;
        //ArrayList<String> question = studentReservation.addQuestion(t,tmp_question.size(),tmp_question);
        //Assert.assertEquals(4,question.size());
    }


    @Test
    public void testPoplateReservatioQueueForStudentData(){
        setup();
        int min= 1;
        int max = 3;
        int t= random.nextInt((max - min) + 1) + min;
        //ArrayList<String> question = studentReservation.addQuestion(t,tmp_question.size(),tmp_question);
        //int a = studentReservation.populateReservationQueue(email,time,question,t).size();
        //Assert.assertEquals(t,a);

    }




    void setup() {

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

    }



}
