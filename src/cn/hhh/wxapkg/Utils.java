package cn.hhh.wxapkg;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class Utils {
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray(); 
	
	//byte字节转为int
	static int byte2int(byte[] data) {
		int i = 0;
		i += ((data[0] & 0xff) << 24);
		i += ((data[1] & 0xff) << 16);
		i += ((data[2] & 0xff) << 8);
		i += ((data[3] & 0xff));
		return i;
	}
	
	//小端字节序反转
	static byte[] reverseBytes(byte[] data) {
		byte[] reverse = new byte[data.length];
		for(int i=0; i<data.length; i++) {
			reverse[i] = data[data.length - i - 1];
		}
		return data; //reverse
	}
	
	//复制一段数据
	static byte[] copyByte(byte[] data, int offset, int len) {
		byte[] copy = new byte[len];
		for(int i=0; i<len; i++) {
			copy[i] = data[i+offset];
		}
		return copy;
	}
	
	//byte数组转hex
	public static String bytesToHex(byte[] bytes) {  
	    char[] hexChars = new char[bytes.length * 2];  
	    for ( int j = 0; j < bytes.length; j++ ) {  
	        int v = bytes[j] & 0xFF;  
	        hexChars[j * 2] = hexArray[v >>> 4];  
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];  
	    }  
	    return new String(hexChars);  
	}
	
	public static String byteToHex(byte b) {  
	    char[] hexChars = new char[2];  
	    int v = b & 0xFF;  
	    hexChars[0] = hexArray[v >>> 4];  
	    hexChars[1] = hexArray[v & 0x0F];  
	    return new String(hexChars);  
	}
	
	//保存文件
	static String isSaveFile(String filepath, byte[] data) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			file = new File(filepath);
			String parent = file.getParent();
			File dir = new File(parent);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			file = new File(filepath);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(data);
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filepath;
	}
	
	//打开文件
	static byte[] getContent(String filepath) throws IOException{
		File file = new File(filepath);
		long filesize = file.length();
		if(filesize > Integer.MAX_VALUE) {
			System.out.println("file too big...");    
            return null; 
		}
		FileInputStream fi = new FileInputStream(file);    
        byte[] buffer = new byte[(int) filesize];    
        int offset = 0;    
        int numRead = 0;    
        while (offset < buffer.length    
        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {    
            offset += numRead;    
        }    

        if (offset != buffer.length) {    
        	System.out.println("Could not completely read file " + file.getName());
        	buffer = null;
        }    
        fi.close();    
        return buffer; 
	}

}

















