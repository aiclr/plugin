package org.bougainvilleas.java.groovy;


import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;


/**
 * @author renqiankun
 * 2022-04-01 17:46:54 星期五
 */
public class MyTest2
{
    public static void main(String[] args) {
        CompilerConfiguration cfg=new CompilerConfiguration();
        cfg.setScriptBaseClass(Test.class.getName());
        GroovyShell shell=new GroovyShell(cfg);
        String str= (String) shell.parse("getString(\"Hello world\")").run();
        System.err.println(str);
        int a= (int) shell.parse("getInt()").run();
        System.err.println(a);

            try{
                Object result=shell.evaluate(new File("C:\\Users\\renqiankun\\vscode\\github\\plugin\\groovy\\src\\main\\groovy\\org\\bougainvilleas\\java\\groovy\\GTest.groovy"));
                System.err.println(result);
            }catch (IOException ioe)
            {
                ioe.printStackTrace();
            }

        Binding binding=new Binding();
            binding.setProperty("name","Jack");
        GroovyShell s2=new GroovyShell(binding);
        try{
            Object result=s2.evaluate(new File("C:\\Users\\renqiankun\\vscode\\github\\plugin\\groovy\\src\\main\\groovy\\org\\bougainvilleas\\java\\groovy\\GTest2.groovy"));
            System.err.println(result);
        }catch (IOException ioe)
        {
            ioe.printStackTrace();
        }


    }


}
