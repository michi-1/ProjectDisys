package com.project.restapip;



import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class APIControllerTest {
    @InjectMocks
    private APIController apiController;

    @Mock
    private Send sendMock;

    @Test
    void testPost() {
        // Prepare test data
        String id = "123";
        String requestBody = "Test Request Body";

        // Mock the dependencies
        MockitoAnnotations.openMocks(this);
        when(sendMock.sender(id)).thenReturn("Send Mock Result");

        // Execute the method
        String result = apiController.post(requestBody, id);

        // Verify the result
        assertEquals(id, result);
    }

}