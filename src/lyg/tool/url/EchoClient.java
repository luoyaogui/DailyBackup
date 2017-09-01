package lyg.tool.url;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ContentHandler;
import java.net.ContentHandlerFactory;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;


//客户端调用协议处理框架  
public class EchoClient {  
    public static void main(String[] args) throws IOException {  
        // 1.设置URLStreamHandler,ContentHandler  
        URL.setURLStreamHandlerFactory(new EchoURLStreamHandlerFactory());  
        URLConnection.setContentHandlerFactory(new EchoContentHandlerFactory());  
          
        URL url = new URL("echo://localhost:8080");  
        EchoURLConnection urlConnection = (EchoURLConnection) url.openConnection();  
          
        urlConnection.setDoOutput(true);  
        PrintWriter writer = new PrintWriter(urlConnection.getOutputStream(),true);  

        for(;;){  
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
            String msg = reader.readLine();  
            writer.println(msg);  
            String content = (String)urlConnection.getContent();  
            System.out.println(content);  
        } 
    	
    	/*URL.setURLStreamHandlerFactory(new EchoURLStreamHandlerFactory());
        URLConnection.setContentHandlerFactory(new EchoContentHandlerFactory());
        URL url=new URL("echo://localhost:8000");
        EchoURLConnection connection=(EchoURLConnection)url.openConnection();
        connection.setDoOutput(true);
        PrintWriter pw=new PrintWriter(connection.getOutputStream(),true);
        while(true){
           BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
           String msg=br.readLine();
           pw.println(msg);  //向服务器发送消息
           String echoMsg=(String)connection.getContent(); //读取服务器返回的消息
           System.out.println(echoMsg);
           if(echoMsg.equals("echo:bye")){
             connection.disConnect();
             break;
           }
        }*/
    }  
    
    static class EchoContentHandlerFactory implements ContentHandlerFactory {  
    	  
    	@Override  
        public ContentHandler createContentHandler(String mimetype) {  
            if(mimetype.equals("text/plain"))  
                return new EchoContentHandler();  
            else  
                return null;  
        }   
    } 
  //用于处理服务器发回来的数据 (2个重载方法)  
    static class EchoContentHandler extends ContentHandler {  
      
        @Override  
        public Object getContent(URLConnection connection) throws IOException {  
            //读取服务器发回的一行数据,把他转换为字符串  
            InputStream in = connection.getInputStream();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));  
            System.out.println(reader.readLine());  
            return reader.readLine();  
        }  
      
        @Override  
        //getContent(URLConnection, Class[])会先试图把输入流读到的内容,转换成class数组的第一个对象,失败在转换成第二个对象  
        public Object getContent(URLConnection connection, Class[] classes)  
                throws IOException {  
            InputStream in = connection.getInputStream();  
            for(Class c:classes){  
                if(c == InputStream.class)  
                    return in;  
                if(String.class == c)  
                    return this.getContent(connection);   
            }  
            return null;  
        }  
    }  
    /** 
    URLconnetcion的getContent()发放流程  
    1) 在自身的缓存HashTable中查找是否已经存在该协议的Contenthandler 
    2) 用private的getContenthandler()返回一个 内容处理器,若用户已经setContentHandler(),则可以成功调用该类的getContent 
    3) 系统属性设置 : java -Djava.content.handler.pkgs=com.EchoURLStreamHandler 
    4) 实例化sun.net.www.content包中的内容处理器,eg:(sun.net.www.content.http.Handler) 
    */ 
    
  //该工厂类根据协议类型创建RLStreamHandler  
    static class EchoURLStreamHandlerFactory implements URLStreamHandlerFactory{  
      
        public URLStreamHandler createURLStreamHandler(String protocol) {  
            if(protocol.equals("echo"))  
                return new EchoURLStreamHandler();  
            return null;  
        }  
          
    } 
    
  //该类, 创建URLConnection类  
    static class EchoURLStreamHandler extends URLStreamHandler{  
      
        @Override  
        protected URLConnection openConnection(URL u) throws IOException {  
            return new EchoURLConnection(u);  
        }  
    }  
    
  //URLConnection负责在框架中连接给出的URL,和协议自身的默认端口  
    static class EchoURLConnection extends URLConnection {  
      
        private Socket soket = null;  
        private final static int port = 8080;    
          
        protected EchoURLConnection(URL url) { //父类URLConnection接收URL  
            super(url);  
        }  
          
        @Override  
        public synchronized void connect() throws IOException {  
            if(!this.connected)  
                this.soket = new Socket(this.url.getHost(),this.port);  
            this.connected = true;  
        }  
      
        @Override  
        public synchronized InputStream getInputStream() throws IOException {  
            if(!this.connected)  
                this.connect();  
            return this.soket.getInputStream();  
        }  
      
        @Override  
        public synchronized OutputStream getOutputStream() throws IOException {  
            if(!this.connected)  
                this.connect();  
            return this.soket.getOutputStream();  
        }  
      
        public synchronized void disConnect() throws IOException{  
            if(this.connected){  
                this.soket.close();  
                this.connected = false;  
            }  
        }  
      
        // 必须有该方法,否则java.net.UnknownServiceException: no content-type  
        @Override  
        public String getContentType() {    
            return ("text/plain");  
        }  
    }  
} 