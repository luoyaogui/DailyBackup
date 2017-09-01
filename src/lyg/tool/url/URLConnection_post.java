package lyg.tool.url;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class URLConnection_post {
	public String doPost(String bookName) throws IOException{  
        URL url = new URL("http://www.javathinker.org/aboutBook.jsp"); //初始化一个与协议相关的URLStreamHandler  
        URLConnection urlConnection = url.openConnection();  
        urlConnection.setDoOutput(true);  
          
        OutputStream out = urlConnection.getOutputStream();  
        PrintWriter writer = new PrintWriter(out);  
        writer.print("title");  //print()方法把一切数据转换何成string,用write()写出  
        writer.print("=");  
        writer.print(URLEncoder.encode(bookName,"GB2312"));  
          
        InputStream in = urlConnection.getInputStream();  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        byte[] b = new byte[1024];  
        int len = 0;  
        while((len=in.read(b))!=0){  
            bos.write(b,0,len);  
        }  
          
        return new String(bos.toByteArray());  
    }  
      
    public static void main(String[] args) throws IOException {  
        String result = new URLConnection_post().doPost("JavaScriptで画像を振動（ブルブル）させる");  
        System.out.println(result);  
    } 
}
