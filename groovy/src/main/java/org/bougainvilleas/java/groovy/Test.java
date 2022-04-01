package org.bougainvilleas.java.groovy;

import groovy.lang.Script;

/**
 * @author renqiankun
 * 2022-04-01 17:52:01 星期五
 */
public class Test extends Script
{
    public static String getString(String string)
    {
        return string;
    }
    public static int getInt()
    {
        return 1;
    }
    @Override
    public Object run()
    {
        return null;
    }
}
