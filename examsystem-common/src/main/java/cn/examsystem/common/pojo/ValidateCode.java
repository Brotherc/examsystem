/**
 * 
 */
package cn.examsystem.common.pojo;

import java.awt.image.BufferedImage;


/**
 * @author brotherChun
 *
 */
public class ValidateCode {
	
	private String code;
	private BufferedImage image;
	
	public ValidateCode(String code, BufferedImage image){
		this.code = code;
		this.image = image;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
