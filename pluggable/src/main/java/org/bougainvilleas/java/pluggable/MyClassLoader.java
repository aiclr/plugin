package org.bougainvilleas.java.pluggable;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 自定义类加载器 解密插件字节码
 * 
 * @author renqiankun
 *         2022-02-18 13:55:01 星期五
 */
public class MyClassLoader extends ClassLoader 
{
    Set<Plugin> plugins;
    public MyClassLoader(Set<Plugin> plugins) 
    {
        this.plugins = plugins;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException 
    {
        try 
        {
            byte[] bytes = getClass(name);
            if (bytes == null)
                throw new FileNotFoundException();
            else
                return defineClass(name, bytes, 0, bytes.length);
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        throw new ClassNotFoundException();
    }

    /**
     * 类似从本地获取class文件字节流
     * 将源码加密，此处可以加入解密逻辑
     *
     * @param name com.ctn.commons.plugins.MyPlugin
     * @return .class文件字节流
     * @throws FileNotFoundException
     */
    private byte[] getClass(String name) throws FileNotFoundException
     {
        for (Plugin plugin : plugins) 
        {
            if (name.equalsIgnoreCase(plugin.getClassName()))
            {
                final String jarPath = plugin.getJar();
                try (JarFile jarFile = new JarFile(jarPath)) 
                {
                    final String classPath = name.replace(".", "/");
                    final byte[] buffer = new byte[1024];
                    final Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) 
                    {
                        final JarEntry jarEntry = entries.nextElement();
                        if (jarEntry.getName().equalsIgnoreCase(classPath)) 
                        {
                            try (InputStream inputStream = jarFile.getInputStream(jarEntry);
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) 
                            {
                                int read;
                                while ((read = inputStream.read(buffer)) != -1) 
                                {
                                    byte key=Byte.parseByte(Pluggable.key);
                                    byte[] decrypt = encrypt(buffer,key);
                                    byteArrayOutputStream.write(decrypt, 0, read);
                                }
                                return byteArrayOutputStream.toByteArray();
                            } 
                            catch (Exception e) 
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] encrypt(byte[] source, byte key) 
    {
        byte[] result = new byte[source.length];
        for (int i = 0; i < source.length; i++)
            result[i] = (byte) (source[i] ^ key);
        return result;
    }
}