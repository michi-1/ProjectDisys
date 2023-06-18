package StationDataCollectorTest;

import com.project.Sender;
import org.checkerframework.checker.fenum.qual.SwingTextOrientation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StationDataCollectorTest {

    @Test
    public void checkMessage(){
        Sender sender = new Sender();
        sender.setCustomerId("3");
        sender.setkwhsum("343");

       String real =  sender.sendCustomerId();

       String expected = "343;3";

        assertEquals(expected, real);

    }

}
