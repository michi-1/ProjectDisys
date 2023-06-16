package DataCollectionDispatcherTest;

import org.example.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataCollectionDispatcherTest {
    //Test DatabaseConnection -- ist das richtig ?
    @Test
    public void returnNumberofStations() throws SQLException, IOException, TimeoutException {
        //test numbers of stations

        DatabaseConnection db = new DatabaseConnection();
        List<String> data = db.getData();

        int size = data.size();

        //Assert
        assertEquals(3, size);
    }

    //Test something ughhh


}
