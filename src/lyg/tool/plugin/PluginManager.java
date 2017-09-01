package lyg.tool.plugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PluginManager {
    static{
        System.out.println(PluginManager.class.getName());
    }
    private Map<String ,PluginClassLoader> pluginMap = new HashMap<String,PluginClassLoader>();
    private static String packagename = "plugin.Plugin1";
    public PluginManager(){

    }
    
    public void doSome(String pluginName){

        try{
            Class<?> forName = Class.forName(packagename, true, getLoader(pluginName));//this.pluginMap.get(pluginName).loadClass(packagename);
            Plugin ins = (Plugin)forName.newInstance();
            ins.doSome();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void addLoader(String pluginName,PluginClassLoader loader){
        this.pluginMap.put(pluginName, loader);
    }
    private PluginClassLoader getLoader(String pluginName){
        return this.pluginMap.get(pluginName);
    }
    public void loadPlugin(String pluginName){
        this.pluginMap.remove(pluginName);
        PluginClassLoader loader = new PluginClassLoader();
        String pluginurl = "jar:file:/C:/Users/luoyaogui/Desktop/"+pluginName+".jar!/";
        System.out.println(pluginurl);
        URL url = null;
        try {
            url = new URL(pluginurl);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        loader.addURLFile(url);
        addLoader(pluginName, loader);
        System.out.println("load " + pluginName + "  success");
    }
    public void unloadPlugin(String pluginName){
        this.pluginMap.get(pluginName).unloadJarFiles();
        this.pluginMap.remove(pluginName);
    }
}