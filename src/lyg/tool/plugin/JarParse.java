package lyg.tool.plugin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import sun.misc.ClassLoaderUtil; 

public class JarParse {

	public static void parseJarName(String jarFile) throws Exception { 
		try{ 
			//通过将给定路径名字符串转换为抽象路径名来创建一个新File实例 
			File f = new File(jarFile); 
			URL url1 = f.toURI().toURL(); 
			URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url1},Thread.currentThread().getContextClassLoader()); 

			//通过jarFile和JarEntry得到所有的类 
			JarFile jar = new JarFile(jarFile); 
			//返回zip文件条目的枚举 
			Enumeration<JarEntry> enumFiles = jar.entries(); 
			JarEntry entry; 

			//测试此枚举是否包含更多的元素 
			while(enumFiles.hasMoreElements()){ 
				entry = (JarEntry)enumFiles.nextElement(); 
				if(entry.getName().indexOf("META-INF")<0){ 
					String classFullName = entry.getName(); 
					if(!classFullName.endsWith(".class")){ 
						classFullName = classFullName.substring(0,classFullName.length()-1); 
					} else{ 
						//去掉后缀.class 
						String className = classFullName.substring(0,classFullName.length()-6).replace("/", "."); 
						Class<?> myclass = myClassLoader.loadClass(className); 
						//打印类名 
						System.out.println("*****************************");
						System.out.println("全类名:" + className); 

						//得到类中包含的属性 
						Method[] methods = myclass.getMethods();
						for (Method method : methods) {
							String methodName = method.getName();
							System.out.println("方法名称:" + methodName);
							Class<?>[] parameterTypes = method.getParameterTypes();
							for (Class<?> clas : parameterTypes) {
								// String parameterName = clas.getName();
								String parameterName = clas.getSimpleName();
								System.out.println("参数类型:" + parameterName);
							}
							System.out.println("==========================");                                                      
						} 
					} 
				} 
			}  
			ClassLoaderUtil.releaseLoader(myClassLoader);  
			Thread.sleep(1000);  
			System.out.println("卸载后："+myClassLoader.loadClass("org.osgi.service.url.URLStreamHandlerService").getName());
			System.out.println("run");  
		} catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 


	static final String filePath = "C:\\Users\\luoyaogui\\Desktop\\felix\\bin\\felix.jar";
	public static void main(String[] args) throws Exception {
		parseJarName(filePath); 
	}

	public static void loadClass() {  
		try {  
			//第一种  配置成文件格式  
			File file = new File("D:\\jarload\\test.txt");  
			BufferedReader in = new BufferedReader(new FileReader(file));  
			String s = new String();  
			while ((s = in.readLine()) != null) {  

				URL url = new URL(s);  
				s = null;  

				URLClassLoader myClassLoader = new URLClassLoader(new URL[] { url }, Thread.currentThread()  
						.getContextClassLoader());  
				Class<?> myClass = (Class<?>) myClassLoader.loadClass("com.java.jarloader.TestAction");  
				InterfaceAction action = (InterfaceAction) myClass.newInstance();  
				String str = action.action();  
				System.out.println(str);  

				//第二种  
				URL url1 = new URL(filePath);  
				URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 }, Thread.currentThread()  
						.getContextClassLoader());  
				Class<?> myClass1 = myClassLoader1.loadClass("com.java.jarloader.TestAction");  
				InterfaceAction action1 = (InterfaceAction) myClass1.newInstance();  
				String str1 = action1.action();  
				System.out.println(str1);  
				ClassLoaderUtil.releaseLoader(myClassLoader1);  //释放
				while(true)  
				{  
					Thread.sleep(1000);  
					System.out.println("run");  
				}  
			}  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
	}  

	interface InterfaceAction {  
		public String action();  
	}  
}
