package main;

public class Reservation {

    private String email;
    private long reservationTime;
    private String question;
    private String status;
    public long currentTime;


    public Reservation(String email, int timeDiff, String question){

        this.currentTime=System.currentTimeMillis()-(4*60*60*1000);
        this.email=email;
        this.reservationTime=this.currentTime-timeDiff;
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

    public String hhmmssFormat(long RT){

        long second = (RT / 1000) % 60;
        long minute = (RT / (1000 * 60)) % 60;
        long hour = (RT / (1000 * 60 * 60)) % 24;

        String time = String.format("%02d:%02d:%02d", hour, minute, second);

        return time;
    }

}