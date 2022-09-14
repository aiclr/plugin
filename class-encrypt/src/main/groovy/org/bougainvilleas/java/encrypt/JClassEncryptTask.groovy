package org.bougainvilleas.java.encrypt

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

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
            //加密 class 文件
//            File output = new File(fileName);
            try (
                    InputStream inputStream = new FileInputStream(file);
                    OutputStream outputStream = new FileOutputStream(output)
            ) {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) != -1)
                    outputStream.write(encryption(buffer, key), 0, read);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
//            if (!file.delete())
                System.out.println("清除" + file.getName() + "失败");
        }
    }

    public byte[] encryption(byte[] source, byte key) {
        byte[] result = new byte[source.length];
        for (int i = 0; i < source.length; i++)
            result[i] = (byte) (source[i] ^ key);
        return result;
    }
}


