package org.bougainvilleas.java.pluggable;

import java.net.MalformedURLException;
import java.util.Set;

/**
 * @author renqiankun
 * 2022-02-18 09:44:09 星期五
 */
public class PluginManager
{

    private MyClassLoader classLoader;

    public PluginManager(Set<Plugin> plugins) throws MalformedURLException
    {
        init(plugins);
    }

    private void init(Set<Plugin> plugins)
    {
        classLoader=new MyClassLoader(plugins);
    }

    public IPlugin getInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Class<?> clazz =Class.forName(className, true, classLoader);
        Object instance=clazz.newInstance();
        return (IPlugin) instance;
    }
}
