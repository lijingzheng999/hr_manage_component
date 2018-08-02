package com.hr.manage.web.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	
	public static String decrypt(String content, String password, String ivStr) {  
        try {  
        	byte[] raw = password.getBytes("utf-8");
    		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
    		byte[] iv = ivStr.getBytes("utf-8");
    		IvParameterSpec _iv = new IvParameterSpec(iv);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, _iv);
    		byte[] byteContent = parseHexStr2Byte(content);
    		byte[] result = cipher.doFinal(byteContent);
            return  new String(result);
        }catch (UnsupportedEncodingException e) {
				e.printStackTrace();
		}catch (NoSuchAlgorithmException e) {  
                e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
                e.printStackTrace();  
        } catch (InvalidKeyException e) {  
                e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
                e.printStackTrace();  
        } catch (BadPaddingException e) {  
                e.printStackTrace();  
        } catch (InvalidAlgorithmParameterException e) {
    		e.printStackTrace();
        } 
        return null;  
	} 

    /** 
     * 加密 
     *  
     * @param content 需要加密的内容 
     * @param password  加密密码 
     * @return 
     */  
    public static String encrypt(String content, String password, String ivStr) {  
    	 try {             
         	byte[] raw = password.getBytes("utf-8");
     		//System.out.println("长度：" +raw.length);
     		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
     		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
     		byte[] iv = ivStr.getBytes("utf-8");
     		IvParameterSpec _iv = new IvParameterSpec(iv);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec, _iv);
     		byte[] byteContent = content.getBytes("utf-8");
     		byte[] result = cipher.doFinal(byteContent);
             return  parseByte2HexStr(result); // 加密  
         } catch (NoSuchAlgorithmException e) {  
                 e.printStackTrace();  
         } catch (NoSuchPaddingException e) {  
                 e.printStackTrace();  
         } catch (InvalidKeyException e) {  
                 e.printStackTrace();  
         } catch (UnsupportedEncodingException e) {  
                 e.printStackTrace();  
         } catch (IllegalBlockSizeException e) {  
                 e.printStackTrace();  
         } catch (BadPaddingException e) {  
                 e.printStackTrace();  
         } catch (InvalidAlgorithmParameterException e) {
         		e.printStackTrace();
		 }
         return null; 
    }  
	
	
    /**将二进制转换成16进制 
     * @param buf 
     * @return 
     */  
    private static String parseByte2HexStr(byte buf[]) {  
    	StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();
    }
    
    
    /**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    private static byte[] parseHexStr2Byte(String hexStr) {  
    	if (hexStr.length() < 1) return null;  
    	byte[] result = new byte[hexStr.length()/2];  
    	for (int i = 0;i< hexStr.length()/2; i++) {  
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
            result[i] = (byte) (high * 16 + low);  
    	}  
    	return result;
    }
	
}
