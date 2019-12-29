package com.preproduction.bobrov.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.database.dao.UserDAO;

/**
 * Displays user avatar
 */
public class LocalImageServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(LocalImageServlet.class);

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String extension = name.substring(name.lastIndexOf('.') + 1);
		response.setContentType("image/" + extension);
		OutputStream out = response.getOutputStream();
		String avatarFolder = (String) request.getServletContext().getInitParameter(AttributeKey.IMAGE_FOLDER);
		File avatarFile = new File(avatarFolder + File.separator + name);
		BufferedImage avatar = null;
		try {
			avatar = ImageIO.read(avatarFile);
		} catch (IOException e) {
			LOG.warn("Can't read image", e);
		}

		ImageIO.write(avatar, extension, out);
		out.close();
	}

}
