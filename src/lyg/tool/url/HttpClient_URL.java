package lyg.tool.url;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class HttpClient_URL {
	//url.openStream()获取输入流  
    public static void main(String[] args) throws IOException {  
        URL url = new URL("http://www.baidu.com");  
        InputStream in = url.openStream();   
          
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        byte[] b = new byte[1024];  
        int len= 0;  
          
        while((len=in.read(b))!=-1){  
            out.write(b,0,len);  
        }  
          
        System.out.println(new String(out.toByteArray())); //打印网页  
    }
}
