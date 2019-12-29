package com.preproduction.bobrov.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * Provides operations for avatar
 */
public class AvatarService {

	private static final String AVATAR = "avatar";
	
	private String avatarFolderPath;
	 

	public AvatarService(String avatarFolderPath) {
		this.avatarFolderPath = avatarFolderPath;
	}
 
	/**
	 * Saves avatar on disk
	 * @param request
	 * @param avatarname
	 * @throws IOException
	 * @throws ServletException
	 */
	public void loadAvatar(HttpServletRequest request, String avatarname) throws IOException, ServletException {
		Part part = request.getPart(AVATAR);
		if (avatarname != null) {
			part.write(avatarFolderPath + File.separator + avatarname);
		}
	}

	/**
	 * Generates avatar name
	 * @param fileName
	 * @return avatar name
	 */
	public String generateAvatarName(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		UUID newName = UUID.randomUUID();
		return newName.toString() + fileName.substring(dotIndex);
	}
}
