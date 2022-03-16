package org.bougainvilleas.java.pluggable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author renqiankun
 * 2022-02-18 09:36:05 星期五
 */
@XmlRootElement(name = "plugin")
@XmlAccessorType(XmlAccessType.FIELD)
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
