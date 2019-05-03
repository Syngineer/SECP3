package test;

import main.StudentReservation;
//import org.junit.jupiter.api.Test;

public class TestMain {
    /**
     *
     * This test case is a sample reference for creating other test cases
     */

    //@Test
    public void testSum(){
        StudentReservation studentReservation = new StudentReservation();
        assert (studentReservation.sum(3,4)==7);

    }

    //@Test
    public void testSumNeg(){
        StudentReservation studentReservation = new StudentReservation();
        assert (studentReservation.sum(3,4)==7);

    }




}
