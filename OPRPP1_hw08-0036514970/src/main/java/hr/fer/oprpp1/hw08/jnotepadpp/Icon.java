package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

public class Icon {
	
	public  ImageIcon getRedIcon() {
		 return getIcon("red_save_icon.png");
	}
	
	public ImageIcon getGreenIcon() {
		return getIcon("green_save_icon.png");
	}
	private  ImageIcon getIcon(String name) {
		try(InputStream is = getClass().getResourceAsStream(name)) {
			return new ImageIcon(new ImageIcon(is.readAllBytes()).getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
			
		} catch (IOException e) {
			throw new IllegalArgumentException("nije moguće otvoriti ilkonu");
		}
		
	}
	
}
