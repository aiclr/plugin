package org.bougainvilleas.java.pluggable;

/**
 * @author renqiankun
 * 2022-02-18 09:36:05 星期五
 */
public class Plugin
{
    private String name;
    private String jar;
    private String className;

    public Plugin()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getJar()
    {
        return jar;
    }

    public void setJar(String jar)
    {
        this.jar = jar;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }
}
