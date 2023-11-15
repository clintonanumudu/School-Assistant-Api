package com.springboot.schoolassistantapi;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class QAndA {
	
	public void addQuestionsToJson(String paperContent) {
		// Remove the note title
		int indexOfNotes = paperContent.indexOf("notes ");
		paperContent = paperContent.substring(indexOfNotes + 6);
		
		// Divide the note into lines
		String[] lines = paperContent.split("\n");
		for (String line : lines) {
			line = line.trim();
			if (line.contains("?")) {
				// Seperate the question from the answer in the sentence
				String question = separateQuestionFromAnswer(line)[0];
				String answer = separateQuestionFromAnswer(line)[1];
				
				// Add the question and answer pair to the JSON file for storage.
				storeQuestionAndAnswer(question, answer);
				
	            System.out.println(line);
			}
		}
	}

	private static String[] separateQuestionFromAnswer(String line) {
	    // Extract the question and answer based on the position of the question mark
		String[] result = new String[2];
	    int questionMarkIndex = line.indexOf('?');
        String question = line.substring(0, questionMarkIndex + 1).trim();
        String answer = line.substring(questionMarkIndex + 1).trim();
        result[0] = question;
        result[1] = answer;
	    return result;
	}
	
	public static void storeQuestionAndAnswer(String question, String answer) {
	    try {
	        // Read existing JSON file
	    	String FILE_PATH = "src/main/resources/static/q_and_a.json";
        	ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(new File(FILE_PATH));

	        // Get the "flashcards" array
	        ArrayNode flashcardsArray = (ArrayNode) rootNode.get("flashcards");

	        boolean questionExists = false;

	        // Check if the question already exists in the JSON
	        for (JsonNode node : flashcardsArray) {
	            if (node.get("question").asText().equalsIgnoreCase(question)) {
	                questionExists = true;
	                break;
	            }
	        }

	        if (!questionExists) {
	            // Create a new JSON object for the question and answer
	            ObjectNode newFlashcard = objectMapper.createObjectNode();
	            newFlashcard.put("question", question);
	            newFlashcard.put("answer", answer);

	            // Add the new question and answer to the "flashcards" array
	            flashcardsArray.add(newFlashcard);

	            // Write the updated JSON back to the file
	            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	            objectMapper.writeValue(new File(FILE_PATH), rootNode);

	            System.out.println("Question and answer added to the JSON file.");
	        } else {
	            System.out.println("Question already exists in the JSON file.");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
