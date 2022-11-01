package com.application.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadUtility {
	public File uploadImage(MultipartFile multipartFile, String folder) {
		File file = new File(folder);
		if (!file.exists()) {
			file.mkdirs();
		}
		if(multipartFile.isEmpty()) {
			return null;
		}
		if(!multipartFile.getOriginalFilename().matches( ".*\\.(png|jpg)")) {
			return null;
		}
		
		String subFile = UUID.randomUUID().toString() + ""
				+ new SimpleDateFormat("dd-MM-yyyy-HH-mm").format(Calendar.getInstance().getTime()) + "-"
				+ multipartFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.]", "");
		File fileSave = new File(file, subFile);
		try {
			multipartFile.transferTo(fileSave);
			return fileSave;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void deleteImage(String path) {
		File file = new File(path);
		if(file.exists()) {
			System.err.println("Exists");
			file.delete();
		}
	}

}
