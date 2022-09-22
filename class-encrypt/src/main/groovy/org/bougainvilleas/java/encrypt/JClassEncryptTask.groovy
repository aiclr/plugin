package org.bougainvilleas.java.encrypt

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import java.nio.channels.FileChannel

class JClassEncryptTask extends DefaultTask {

    @Input
    String packages = 'org.bougainvilleas';
    @Input
    String password = 'Amaterasu';

    @TaskAction
    def encryptClass() {
        println "密码:" + password + " 包:" + packages
        //加密 class 位置 build/encrypt/classes
        final String classPath = this.getProject().getBuildDir().getAbsolutePath() + "/encrypt/classes";
        final File file = new File(classPath.replace("\\", "/"));
        println classPath
        encryptClass(file, (byte)1);
    }

    public void encryptClass(File file, byte key) {
        def newFile = file.getAbsolutePath().replaceAll("\\\\encrypt\\\\", "/")
//        boolean isApplication=newFile.endsWith("Application.class")
        System.err.println("name=="+newFile)
        File output=new File(newFile);
        if (file.isDirectory()) {
            //创建文件夹
            if(!output.exists())
            {
                System.err.println(output.getAbsolutePath());
                output.mkdir()
            }
            File[] files = Optional.ofNullable(file.listFiles()).orElse(new File[0]);
            Arrays.stream(files).forEach(temp -> encryptClass(temp, key));
        } else if (file.isFile() && file.getAbsolutePath().endsWith(".class")) {
            System.err.println("文件： "+output.getAbsolutePath());
            try (
                    InputStream inputStream = new FileInputStream(file);
                    OutputStream outputStream = new FileOutputStream(output)
            ) {
                byte[] buffer = new byte[16];
                int read;
                while ((read = inputStream.read(buffer)) != -1)
                    outputStream.write(encryption(buffer, key), 0, read);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            System.out.println("清除" + file.getName() + "失败");
        }
    }

    public byte[] encryption(byte[] source, byte key) {
        byte[] result = new byte[source.length];
        for (int i = 0; i < source.length; i++)
            result[i] = (byte) (source[i] ^ key);
        return result;
    }

    public static void copeFile(File from,File to)
    {
        try(FileChannel inputChannel=new FileInputStream(from).getChannel();
            FileChannel outputChannel=new FileOutputStream(to).getChannel();)
        {
            outputChannel.transferFrom(inputChannel,0,inputChannel.size())
        }catch(Exception e) {
            e.printStackTrace()
        }
    }


    /**
     * 先备份原始文件
     */
    public void cow() {
        println "密码:" + password + " 包:" + packages
        //原始 class 位置 build/classes
        String sourceClassPath = this.getProject().getBuildDir().getAbsolutePath() + "/classes";
        File fromFile = new File(sourceClassPath.replace("\\", "/"));
        File toFile=new File(sourceClassPath+"_bak");
        if(fromFile.exists())
            encryptClassPro(fromFile,toFile);
    }


    public static void encryptClassPro(File fromFile,File toFile){
        String fromPath=fromFile.getAbsolutePath();
        System.err.println("from: "+fromPath);
        if(fromFile.isDirectory())
        {//目录
            Arrays.stream(fromFile.listFiles()).forEach(temp -> encryptClassPro(temp));
            File toDir=new File(fromPath+"_bak");
            //创建备份目录
            if(!toDir.exists()){
                //创建 父级目录
                toDir.mkdirs();
                //不创建 父级目录
//                toDir.mkdir();
            }
            System.err.println("to: "+toDir.getAbsolutePath());
        }else if(fromPath.endsWith(".class"))
        {//字节码文件
//            File toFile=new File(fromPath)


        }else {
            //do sth
        }
    }
}


