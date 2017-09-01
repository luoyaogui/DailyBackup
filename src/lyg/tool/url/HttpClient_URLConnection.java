package lyg.tool.url;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class HttpClient_URLConnection {
	public static void main(String[] args) throws IOException {  
        URL url = new URL("http://www.baidu.com");  
        URLConnection urlConnection = url.openConnection(); //利用URLStreamHandler的openConnection()  
        System.out.println("响应正文长度:"+urlConnection.getContentLength());  
        System.out.println("相应正文类型:"+urlConnection.getContentType());  
          
        InputStream in = urlConnection.getInputStream();  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        byte[] b = new byte[1024];  
          
        int len = 0;  
        while((len=in.read(b))!=-1){  
            out.write(b, 0, len);  
        }  
        System.out.println(new String(out.toByteArray())); 
        
        String str = URLEncoder.encode("白色");  
        System.out.println(str); // %E7%99%BD%E8%89%B2  
        
        String contentType = URLConnection.guessContentTypeFromName("123.png");  
        System.out.println(contentType); //image/png 
    }  
}
