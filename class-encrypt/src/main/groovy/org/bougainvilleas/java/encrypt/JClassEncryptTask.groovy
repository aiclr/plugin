package org.bougainvilleas.java.encrypt

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class JClassEncryptTask extends DefaultTask {

    @Input
    List<String> skip;
    @Input
    String packages = 'org.bougainvilleas';
    @Input
    String password = 'Amaterasu';

    @TaskAction
    def encryptClass() {
        print(skip)
        println "密码:" + password + " 包:" + packages
        //将原始class 文件 加密并放到 build/encrypted/classes
        final String classPath = this.getProject().getBuildDir().getAbsolutePath() + "/classes";
        final File file = new File(classPath.replace("\\", "/"));
        encryptClass(file, (byte) 1);
    }

    public void encryptClass(File file, byte key) {
        //加密 class 路径 build/classes ---> build/encrypt/classes
        def newFile = file.getAbsolutePath().replaceAll("classes", "encrypt/classes")
        File output = new File(newFile);
        //创建文件夹
        if (file.isDirectory()) {
            if (!output.exists()) {
                //自动创建父级
                output.mkdirs()
                //不自动创建父级
                output.mkdir()
            }
            File[] files = Optional.ofNullable(file.listFiles()).orElse(new File[0]);
            // 递归 recursion 遍历所有文件
            Arrays.stream(files).forEach(temp -> encryptClass(temp, key));
        } else if (file.isFile() && file.getAbsolutePath().endsWith(".class")) {
            try (
                    InputStream inputStream = new FileInputStream(file);
                    OutputStream outputStream = new FileOutputStream(output)
            ) {
                //AES 加密
                byte[] buffer = new byte[16];
                int read;
                if (skip.contains(file.getName())) {
                    //不加密的文件
                    while ((read = inputStream.read(buffer)) != -1)
                        outputStream.write(buffer, 0, read);
                } else {
                    //加密的文件
                    while ((read = inputStream.read(buffer)) != -1)
                        outputStream.write(encryption(buffer, key), 0, read);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public byte[] encryption(byte[] source, byte key) {
        byte[] result = new byte[source.length];
        for (int i = 0; i < source.length; i++)
            result[i] = (byte) (source[i] ^ key);
        return result;
    }

}


