package DataCollectionDispatcherTest;

import org.example.Database;
import org.example.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class DataCollectionDispatcherTest {
    //Test DatabaseConnection -- ist das richtig ?

    /* @Test
    public void returnNumberofStations() throws SQLException, IOException, TimeoutException {
        //test numbers of stations

        Database db = mock(Database.class);
        DatabaseConnection dbConnection = mock(DatabaseConnection.class);

        when(dbConnection.getData()).thenReturn(
                List.of( //urls
                        "localhost:29011",
                        "localhost:34311",
                        "localhost:20231",
                        "localhost:20643"

                )
        );

        List<String> data = dbConnection.getData();

        int size = data.size();

        //Assert
        assertEquals(4, size);
    }
*/

}
