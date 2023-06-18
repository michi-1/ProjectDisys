package com.project.restapip;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import org.springframework.http.HttpStatus;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
public class APIController {
Send s1 = new Send();

    @PostMapping("/post/invoices/{id}")
    public String post(@RequestBody String id2, @PathVariable String id) {

        s1.sender(id);
        return  id  ;
    }
    @GetMapping(value = "/get/invoices/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> get(@PathVariable String id) throws FileNotFoundException {
        System.out.println(id);
        File pdfFile = new File("Files\\" + id + ".pdf");
        if (!pdfFile.exists()) {
            return ResponseEntity.notFound().build(); // Return a 404 Not Found response
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=1.pdf");
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(pdfFile));

            // Get the creation date of the PDF file
            long creationDate = pdfFile.lastModified();
            headers.add("X-Creation-Date", String.valueOf(creationDate));

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(inputStreamResource);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return a 500 Internal Server Error response if there's an error reading the file
        }
    }


}
