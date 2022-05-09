package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloader extends Thread {
	private PersonCardController controller;
	private String path;
	
	public ImageDownloader(PersonCardController controller, String path) {
		this.controller = controller;
		this.path = path;
	}

	@Override
	public void run() {
		try {
			downloadImage("https://thispersondoesnotexist.com/image", path);
			controller.setImage(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void downloadImage(String search, String path) throws IOException {
	    InputStream inputStream = null;
	    OutputStream outputStream = null;

	    try {
			URL url = new URL(search);
			String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
			URLConnection con = url.openConnection();
			con.setRequestProperty("User-Agent", USER_AGENT);
			inputStream = con.getInputStream();
			outputStream = new FileOutputStream(path);
			byte[] buffer = new byte[2048];
			int length; 
			
			while ((length = inputStream.read(buffer)) != -1) {
			    outputStream.write(buffer, 0, length);
			}
			
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    outputStream.close();
	    inputStream.close();
	}
}
