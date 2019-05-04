package main;

public class Reservation {

    private String email;
    private long reservationTime;
    private String question;
    private String status;


    public Reservation(String email, int timeDiff, String question){

        this.email=email;
        this.reservationTime=System.currentTimeMillis()-timeDiff;
        this.question=question;
        this.status="Pending";
    }

    public String getEmail(){
        return email;
    }

    public long getTime(){
        return reservationTime;
    }

    public String getQuestion(){
        return question;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }

}
