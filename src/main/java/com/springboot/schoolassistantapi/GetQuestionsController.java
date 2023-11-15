package com.springboot.schoolassistantapi;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class GetQuestionsController {

    @GetMapping("/getquestions")
    public String getQuestions() {
        try {
            // Load the JSON file from the resources folder
            ClassPathResource resource = new ClassPathResource("static/q_and_a.json");
            InputStream inputStream = resource.getInputStream();

            // Read the contents of the file into a String
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String jsonContent = new String(bdata, StandardCharsets.UTF_8);

            // Return the JSON content as the response
            return jsonContent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
