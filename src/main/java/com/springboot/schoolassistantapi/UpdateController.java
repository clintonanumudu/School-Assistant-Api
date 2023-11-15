package com.springboot.schoolassistantapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UpdateController {
	@GetMapping("/update")
	public String update() {
		
		// FIRST STEP TO UPDATING THE QUESTIONS JSON WOULD BE TO RETRIEVE THE UPCOMING EXAMS
		
		HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://anotepad.com/notes/a89rrwmj");
        
        List<String> upcomingSubjects = new ArrayList<>();
        
        try {
            HttpResponse response = httpClient.execute(httpGet);

            // Get the response status code
            int statusCode = response.getStatusLine().getStatusCode();

            // Get the response entity (the web page content)
            String html = EntityUtils.toString(response.getEntity());
            
            Document document = Jsoup.parse(html);

            // Use CSS selector to find the first element with class 'plaintext'
            Elements elements = document.select(".plaintext");
            Element firstElementWithClass = elements.first();
            String upcoming = firstElementWithClass.html();
            
            String[] subjectsOriginal = {"Mathe", "Deutsch", "Englisch", "Informatik", "Physik", "Rechnungswesen", "Wirtschaft", "Recht", "Sport", "Englisch"};
            
            String[] words = upcoming.split("\\s+"); // Split the input string into words

            // Create a new array to store the found subjects
            List<String> foundSubjects = new ArrayList<>();

            // Build a regex pattern that matches any subject from subjectsOriginal
            String pattern = "\\b(" + String.join("|", subjectsOriginal) + ")\\b";
            Pattern subjectPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

            Matcher matcher = subjectPattern.matcher(upcoming);

            while (matcher.find()) {
                foundSubjects.add(matcher.group());
            }

            Dictionary dictionary = new Dictionary();
            dictionary.addTranslation("Englisch", "English");
            dictionary.addTranslation("Mathe", "Maths");
            dictionary.addTranslation("Informatik", "Computer Science");
            dictionary.addTranslation("Physik", "Physics");
            dictionary.addTranslation("Rechnungswesen", "Accounting");
            dictionary.addTranslation("Wirtschaft", "Economics");
            dictionary.addTranslation("Sport", "Sport");
            dictionary.addTranslation("Deutsch", "German");
            dictionary.addTranslation("Recht", "Law");
            dictionary.addTranslation("Politik", "Politics");

            for (String word : foundSubjects) {
                upcomingSubjects.add(dictionary.translate(word));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        // THE NEXT STEP IS TO DOWNLOAD EVERY IMAGE OF YOUR NOTES
        // FROM SUBJECTS OF UPCOMING EXAMS FROM DROPBOX
        
        //System.out.println(upcomingSubjects);
        Dropbox dropbox = new Dropbox();
        dropbox.generateQuestionsAndAnswers(upcomingSubjects);
		
		return "The flashcards have been successfully updated.";
	}
}
