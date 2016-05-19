package com.mcnc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public abstract class FileUtil {
	
	private static final String DEFUALT_CHARACTER_ENCODING = "UTF-8";
	
	public static void saveFile(String inputFile, String outputFile){
		FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(inputFile);
            out = new FileOutputStream(outputFile);
            IOUtils.copyLarge(in, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
	}
	
	public static void downloadFile(
			String fileName,
			HttpServletResponse response, 
			HttpServletRequest request){
		
		FileInputStream in = null;
        OutputStream out = null;
        
		try {
			Path path = Paths.get(fileName);
			String mimeType = Files.probeContentType(path);
            response.setContentType(mimeType);
            File file = new File(fileName);
            int fileSize = (int) (file.length());

            if (fileSize > 0) {
                setDisposition(fileName, request, response);
                response.setContentLength(fileSize);
                in = FileUtils.openInputStream(file);
                out = response.getOutputStream();
                IOUtils.copyLarge(in, out);
                out.flush();
            } else {
                System.out.println("fileSize=0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(404);
            } catch (Exception ex) {
                e.printStackTrace();
            }
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
	}
	
	public static String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}

	public static String setDisposition(String filename, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);
		String encodedFilename = null;

		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename,
					DEFUALT_CHARACTER_ENCODING).replaceAll("\\+", "%20");

		} else if (browser.equals("Firefox")) {
			encodedFilename = "\""
					+ new String(filename.getBytes(DEFUALT_CHARACTER_ENCODING),
							"8859_1") + "\"";

		} else if (browser.equals("Opera")) {
			encodedFilename = "\""
					+ new String(filename.getBytes(DEFUALT_CHARACTER_ENCODING),
							"8859_1") + "\"";

		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c,
							DEFUALT_CHARACTER_ENCODING));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();

		} else {
			throw new IOException("Not supported browser");
		}
		response.setHeader("Content-Disposition", "attachment; filename="+ encodedFilename);

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset="
					+ DEFUALT_CHARACTER_ENCODING);
		}
		return encodedFilename;
	}
}
