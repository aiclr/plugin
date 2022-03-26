package org.bougainvilleas.java.groovy;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

public class EncryptTask extends DefaultTask {

    public byte key;

    @TaskAction
    public void encrypt()
    {
        final String classPath=this.getProject().getBuildDir().getAbsolutePath()+"/classes";
        final File file=new File(classPath.replace("\\","/"));
        encryptClass(file,key);
    }

    public void encryptClass(File file,byte key)
    {
        if (file.isDirectory()) {
            File[] files = Optional.ofNullable(file.listFiles()).orElse(new File[0]);
            Arrays.stream(files).forEach(temp->encryptClass(temp, key));
        } else if (file.isFile() && file.getAbsolutePath().endsWith(".class")) {
            //加密 class 文件
            File output = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 6));
            try(
                    InputStream inputStream = new FileInputStream(file);
                    OutputStream outputStream = new FileOutputStream(output)
            )
            {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) != -1)
                    outputStream.write(encryption(buffer, key), 0, read);
            }catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
            if(!file.delete())
                System.out.println("清除"+file.getName()+"失败");
        }
    }

    public byte[] encryption(byte[] source,byte key)
    {
        byte[] result = new byte[source.length];
        for (int i = 0; i < source.length; i++)
            result[i] = (byte) (source[i] ^ key);
        return result;
    }
}
