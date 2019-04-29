package SEC;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.*;    
public class SEC {  
public static void main(String[] args) {  
    JFrame f=new JFrame("Welcome To Office Hour Reservation System"); 
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    JTextArea t1,t2,t3,t4; 
    ArrayList<String> email = new ArrayList<>();
    email.add("abc@buffalo.edu");
    email.add("efg@buffalo.edu");
    email.add("csdc12@buffalo.edu");
    email.add("abc234@buffalo.edu");
    ArrayList<String> time = new ArrayList<>();
    time.add("-5");
    time.add("-11");
    time.add("-30");
    time.add("-50");
    ArrayList<String> question = new ArrayList<>();
    ArrayList<String> tmp_question = new ArrayList<>();
    tmp_question.add("How to study J Swing?");
    tmp_question.add("How to become a SDE?");
    Collections.shuffle(email);
    Collections.shuffle(time);
    Collections.shuffle(tmp_question);
    Random rt = new Random();
    int t = rt.nextInt(5);
    if(t>0) {
    	int pos = 50;
    	if(t == 1) {
    	    question.add("How to study J Swing?");
    	    question.add("How to become a SDE?");
    	    question.add("");
    	}else if(t == 2) {
    	    question.add(tmp_question.get(0));
    	    question.add("");
    	    
    	}
    	else{
    	    question.add("How to study J Swing?");
    	    question.add("How to become a SDE?");
    	    question.add("");
    	    question.add("");
    	}
    	Collections.shuffle(question);
    	for(int i = 0; i < t; i++) {
    		String content = "Email: "+ email.get(i) + "\nQuestion: " + question.get(i) +  "\nReservation Time: " + time.get(i);
    		JTextArea textArea = new JTextArea(content);
    		textArea.setBounds(50,pos, 300,60); 
    		textArea.setEditable(false); 
		    f.add(textArea); 
		    pos += 100;
    		
    	}
	    JButton b=new JButton("Take Reservation");  
	    b.setBounds(50,450,150,30); 
	    f.add(b); 
    }else {
    	JOptionPane.showMessageDialog(f, "There Are No Reservation At This Time!", "Warning",JOptionPane.WARNING_MESSAGE);
    	System.exit(0);

    }
   
    f.setSize(800,600);  
    f.setLayout(null);  
    f.setVisible(true);   
}  
}  
