package com.springboot.schoolassistantapi;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.io.ByteArrayOutputStream;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

public class Dropbox {

	private final String ACCESS_TOKEN = System.getenv("DROPBOX_ACCESS_TOKEN");
	
	public void generateQuestionsAndAnswers(List<String> upcomingSubjects) {
		for (String subject : upcomingSubjects) {
			loopThroughNotes(subject);
        }
		return;
	}
	
	private void loopThroughNotes(String subject) {
		System.out.println(ACCESS_TOKEN);
		
	    String dropboxFolderPath = "/School Assistant Papers/" + subject;
	    String resourcesFolderPath = getClass().getResource("/").getPath() + "static/";

	    DbxRequestConfig config = DbxRequestConfig.newBuilder("school-assistant").build();
	    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

	    try {
	        ListFolderResult result = client.files().listFolder(dropboxFolderPath);

	        for (Metadata metadata : result.getEntries()) {
	            if (metadata instanceof FileMetadata) {
	                String fileName = metadata.getName();
	                String localFilePath = resourcesFolderPath + fileName;

	                try (InputStream in = client.files().download(metadata.getPathLower()).getInputStream()) {
	                    Path localPath = Paths.get(localFilePath);
	                    Files.copy(in, localPath, StandardCopyOption.REPLACE_EXISTING);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }

	                Selenium selenium = new Selenium();
	                String paperContent = selenium.extractTextFromPaper();
	                QAndA qanda = new QAndA();
	                qanda.addQuestionsToJson(paperContent);
	            }
	        }
	    } catch (ListFolderErrorException ex) {
	        System.err.println("Error listing the folder contents: " + ex.getMessage());
	    } catch (DbxException e1) {
	        e1.printStackTrace();
	    }
	}

}
