package org.bougainvilleas.java.plugins;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TTClassFileTransformer implements ClassFileTransformer
{
    @Override
    public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException
    {
        if(s.startsWith("org/bougainvilleas/java/"))
        {
            byte[] decode=new byte[bytes.length];
            System.err.println(s);

            for (int i = 0; i < bytes.length; i++)
            {
                System.err.print(Integer.toString(Byte.toUnsignedInt(bytes[i]),16));
                decode[i]= (byte) (bytes[i]^1);
                System.err.print(Integer.toString(Byte.toUnsignedInt(decode[i]),16));
            }
            return decode;
        }else
        {
            return null;
        }
    }
}
