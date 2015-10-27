package usee.com.utils;
import java.io.UnsupportedEncodingException;

/**
 * 转换字符串的编码
 */
public class ConverStr {
     
    /** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
    public static final String US_ASCII = "US-ASCII";
    /** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
    public static final String ISO_8859_1 = "ISO-8859-1";
    /** 8 位 UCS 转换格式 */
    public static final String UTF_8 = "UTF-8";
    /** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
    public static final String UTF_16BE = "UTF-16BE";
    /** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
    public static final String UTF_16LE = "UTF-16LE";
    /** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
    public static final String UTF_16 = "UTF-16";
    /** 中文超大字符集 */
    public static final String GBK = "GBK";
    public static final String GB2312 = "GB2312";
    
    /**
     * 判断字符串编码格式
     * @param str
     * @throws UnsupportedEncodingException
     */
    public static void judgeCharster(String str) throws UnsupportedEncodingException {
        ConverStr test = new ConverStr();
        System.out.println("str: " + str);
        String gbk = test.changeCharset(str, GBK);
        System.out.println("转换成GBK码: " + gbk);
        String ascii = test.changeCharset(str, US_ASCII);
        System.out.println("转换成US-ASCII码: " + ascii);
        String iso88591 = test.changeCharset(str, ISO_8859_1);
        System.out.println("转换成ISO-8859-1码: " + iso88591);
        String utf8 = test.changeCharset(str, UTF_8);
        System.out.println("转换成UTF-8码: " + utf8);
        String utf16be = test.changeCharset(str, UTF_16BE);
        System.out.println("转换成UTF-16BE码:" + utf16be);
        String utf16le = test.changeCharset(str, UTF_16LE);
        System.out.println("转换成UTF-16LE码:" + utf16le);
        String utf16 = test.changeCharset(str, UTF_16);
        System.out.println("转换成UTF-16码:" + utf16);
    }

    /**
     * 字符串编码转换的实现方法
     * 
     * @param str
     *            待转换编码的字符串
     * @param newCharset
     *            目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * 字符串编码转换的实现方法
     * 
     * @param str
     *            待转换编码的字符串
     * @param oldCharset
     *            原编码
     * @param newCharset
     *            目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用旧的字符编码解码字符串。解码可能会出现异常。
            byte[] bs = str.getBytes(oldCharset);
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }
    public static void main(String[] args) {
		String str="你好";
		try {
			ConverStr.judgeCharster(str);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
