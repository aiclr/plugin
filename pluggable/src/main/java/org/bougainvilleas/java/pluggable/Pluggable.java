package org.bougainvilleas.java.pluggable;

import java.net.MalformedURLException;
import java.util.Set;

/**
 * @author renqiankun
 * 2022-02-18 09:33:39 星期五
 */
public class Pluggable
{
    public static String key;

    public static void main(String[] args)
    {
        try
        {
            init(args);

            final Set<Plugin> plugins = XMLParser.getPlugins();
            final PluginManager pluginManager = new PluginManager(plugins);
            for(Plugin plugin:plugins)
            {
                final IPlugin instance = pluginManager.getInstance(plugin.getClassName());
                System.err.println("开始执行["+plugin.getName()+"]插件...");
                instance.service();
                System.err.println("["+plugin.getName()+"]插件执行结束...");
            }
        }
        catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private static void init(String[] args)
    {
        //MARK 命令行参数传入解密 byte
        key=args.length>0?args[0]:"1";
    }

}
