package lyg.tool.plugin;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.Manifest;


/**
 * 插件类加载器，在插件目录中搜索jar包，并为发现的资源(jar)构造一个类加载器,将对应的jar添加到classpath中
 */
public class PluginClassLoader extends URLClassLoader {
	
	private AtomicReference<JarURLConnection> cachedJarFiles = new AtomicReference<JarURLConnection>(null);
    public PluginClassLoader() {
        super(new URL[] {}, findParentClassLoader());
    }

    /**
     * 将指定的文件url添加到类加载器的classpath中去，并缓存jar connection，方便以后卸载jar
     * @param 一个可想类加载器的classpath中添加的文件url
     */
    public void addURLFile(URL file) {
        try {
            // 打开并缓存文件url连接
            
            URLConnection uc = file.openConnection();
            if (uc instanceof JarURLConnection) {
                uc.setUseCaches(true);
                printManifest(((JarURLConnection) uc).getManifest());
                cachedJarFiles.set((JarURLConnection)uc);
            }
        } catch (Exception e) {
            System.err.println("Failed to cache plugin JAR file: " + file.toExternalForm());
        }
        addURL(file);
    }
    
    private void printManifest(Manifest manifest) {
    	for(Entry<Object, Object> entry:manifest.getMainAttributes().entrySet())
    		System.out.println(entry.getKey()+" = "+entry.getValue());
	}

	/**
     * 卸载jar包
     */
    public void unloadJarFiles() {
       	if (cachedJarFiles.get() != null) {
            try {
                System.err.println("Unloading plugin JAR file " + cachedJarFiles.get().getJarFile().getName());
                cachedJarFiles.get().getJarFile().close();
                cachedJarFiles.set(null);
            } catch (Exception e) {
                System.err.println("Failed to unload JAR file\n"+e);
            }
        }
    }

    /**
     * 定位基于当前上下文的父类加载器
     * @return 返回可用的父类加载器.
     */
    private static ClassLoader findParentClassLoader() {
        ClassLoader parent = PluginManager.class.getClassLoader();
        if (parent == null) {
            parent = PluginClassLoader.class.getClassLoader();
        }
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }
        return parent;
    }
}