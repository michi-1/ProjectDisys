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
       String expected =  sender.summe(numbers, 2,2,2);
       String real = "2;6.8";
        // Assert
        assertEquals(expected, real);
    }}

