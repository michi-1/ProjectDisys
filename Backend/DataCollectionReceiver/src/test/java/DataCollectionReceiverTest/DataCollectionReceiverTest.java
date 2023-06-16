package DataCollectionReceiverTest;

import org.example.Sender;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataCollectionReceiverTest {

//tests
@Test
    public void checkKWHSum(){

        Sender sender = new Sender();

        List <Double> numbers = new ArrayList<>();
        numbers.add(2.3);
        numbers.add(4.5);

       String expected =  sender.summe(numbers, 2);
       String real = "0;6.8"; //wir haben jz einfach customnerid 0 angenommen.
        // Assert
        assertEquals(expected, real);
    }

    @Test
    public void checkList() {
        //check function list
//speichert einzelne kwh summen aus der id
        Sender sender = new Sender();
        String message1 = "361;2;";
        String message2 = "222;2;";
        String message3 = "531;2;";

        List <Double> expected = new ArrayList<>();
            expected.add(361.0);
            expected.add(222.0);
            expected.add(531.0);

        //besteht aus kwh anzahl der einzelnen stationen
        //count = wie viele nachrichten
        //cnt = anzahl der datenbanken

        List<Double> real = sender.list(message1, 1, 3);
                     real = sender.list(message2, 2, 3);
                     real = sender.list(message3, 3, 3);
        //einzelne kwhs in eine list tun
        //messages split ergibt: customerid, counter


        assertEquals(expected, real);


    }
}

