package org.bougainvilleas.java.plugins;


import org.bougainvilleas.java.pluggable.IPlugin;

/**
 * @author renqiankun
 * 2022-02-18 11:01:08 星期五
 */
public class BPlugin implements IPlugin
{
    @Override
    public void service()
    {
        System.err.println("我是B插件");
    }
}
