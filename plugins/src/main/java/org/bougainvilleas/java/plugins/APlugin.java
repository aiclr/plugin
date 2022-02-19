package org.bougainvilleas.java.plugins;


import org.bougainvilleas.java.pluggable.IPlugin;

/**
 * @author renqiankun
 * 2022-02-18 09:55:02 星期五
 */
public class APlugin implements IPlugin
{
    @Override
    public void service()
    {
        System.err.println("我是A插件");
    }
}
