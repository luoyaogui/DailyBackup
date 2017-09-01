package lyg.tool.properties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.Properties;


/**
* 初始化仲裁管理
*/
public final class InitArbitrate {
	private static final String SYSTEM_CONF = "cs.conf";
	private static final String CS_CACHECLOUD_WEB_HOST = "web.host";
	private static final String CS_CACHECLOUD_WEB_PORT = "web.port";
	private static final String CS_CACHECLOUD_WEB_KEY = "web.key";
	private static final String CS_PROXY_UUID = "uuid";
	
	private PropertiesParser cfg;

	public void initConf() throws Exception {
		if(cfg != null){
			throw new Exception("## cfg not null!");
		}
		//否则按照系统参数目录、当前目录./、根目录/、资源目录进行查找，没有则失败
		String requestedFile = System.getProperty(SYSTEM_CONF);
		String propFileName = requestedFile != null ? requestedFile : "cs.properties";
		File propFile = new File(propFileName);

		Properties props = new Properties();

		InputStream in = null;

		try {
			if (propFile.exists()) {
				try {
					in = new BufferedInputStream(new FileInputStream(propFileName));
					props.load(in);

				} catch (IOException e) {
					throw new Exception("## "+propFile.getAbsolutePath()+" load failed :\n{}", e.fillInStackTrace());
				}
			} else if (requestedFile != null) {
				in = Thread.currentThread().getContextClassLoader().getResourceAsStream(requestedFile);

				if(in == null) {
					throw new Exception("## "+requestedFile+" load failed :\n{}");
				}

				in = new BufferedInputStream(in);
				try {
					props.load(in);
				} catch (IOException e) {
					throw new Exception("## "+requestedFile+" read failed :\n{}", e.fillInStackTrace());
				}

			} else {
				ClassLoader cl = getClass().getClassLoader();
				if(cl == null)
					cl = findClassloader();
				if(cl == null){
					throw new Exception("## call findClassloader() failed :\n{}");
				}
				
				//加载属性配置文件
				in = cl.getResourceAsStream("cs.properties");

				if (in == null) {
					in = cl.getResourceAsStream("/cs.properties");
				}
				
				if (in == null) {
					throw new Exception("## all path can't fond cs.properties :\n{}");
				}
				try {
					props.load(in);
				} catch (IOException e) {
					throw new Exception("## last , cs.properties read failed :\n{}", e.fillInStackTrace());
				}
			}
		} finally {
			if(in != null) {
				try { in.close(); } catch(IOException ignore) { /* ignore */ }
			}
		}
		overrideWithSysProps(props);
		//设置配置文件解析
		this.cfg = new PropertiesParser(props);
	}

	/**
	 * 覆盖系统属性
	 */
	private Properties overrideWithSysProps(Properties props) {
		Properties sysProps = null;
		try {
			sysProps = System.getProperties();
		} catch (AccessControlException e) {
		}

		if (sysProps != null) {
			props.putAll(sysProps);
		}
		
		return props;
	}
	
	private ClassLoader findClassloader() {
		if(Thread.currentThread().getContextClassLoader() == null && getClass().getClassLoader() != null) {
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		}
		return Thread.currentThread().getContextClassLoader();
	}

	public String getWebHost() {
		return cfg.getStringProperty(CS_CACHECLOUD_WEB_HOST);
	}

	public int getWebPort() {
		return cfg.getIntProperty(CS_CACHECLOUD_WEB_PORT);
	}
	public String getWebKey() {
		return cfg.getStringProperty(CS_CACHECLOUD_WEB_KEY);
	}
	
	public long getProxyUuid() {
		return cfg.getLongProperty(CS_PROXY_UUID);
	}
	public String getCheckCode(){
		return null;
	}
	
	public String getProperty(String key,String defaultValue){
		
		return cfg.getStringProperty(key, defaultValue);
	}
	
	public Properties getProperties(){
		
		return this.cfg.getUnderlyingProperties();
	}
}
