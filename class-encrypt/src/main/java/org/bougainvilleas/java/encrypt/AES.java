package org.bougainvilleas.java.encrypt;

/**
 *
 */
public class AES {

    /**
     * Rijndael S-box Substitution table used for encryption in the subBytes
     * step, as well as the key expansion.
     */
    private static final int[] SBOX = {
            0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
            0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
            0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
            0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
            0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
            0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
            0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
            0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
            0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
            0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
            0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
            0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
            0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
            0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
            0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
            0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
    };


    /**
     * Inverse Rijndael S-box Substitution table used for decryption in the
     * subBytesDec step.
     */
    private static final int[] INVERSE_SBOX = {
            0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB,
            0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB,
            0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E,
            0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25,
            0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92,
            0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84,
            0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06,
            0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
            0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73,
            0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E,
            0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B,
            0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4,
            0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F,
            0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF,
            0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61,
            0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D
    };

    /**
     * 1	2	4	8	16	32	64	128	27	54
     */
    public static byte[] RCON = new byte[]{0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, (byte) 0x80, 0x1b, 0x36};

    /**
     * 循环左移
     */
    public static byte[] cycleLeft(byte[] bytes) {
        byte tmp = bytes[0];
        bytes[0] = bytes[1];
        bytes[1] = bytes[2];
        bytes[2] = bytes[3];
        bytes[3] = tmp;
        return bytes;
    }

    /**
     * 按 SBOX 映射 字节
     */
    public static byte[] subBytes(byte[] ciphertext) {
        byte[] result = new byte[ciphertext.length];
        for (int i = 0; i < ciphertext.length; i++) {
            result[i] = (byte) SBOX[(int) ciphertext[i] & 0xff];
        }
        return result;
    }

    /**
     * 按 INVERSE_SBOX 映射 字节
     */
    public static byte[] subBytesDec(byte[] ciphertext) {
        byte[] result = new byte[ciphertext.length];
        for (int i = 0; i < ciphertext.length; i++) {
            result[i] = (byte) INVERSE_SBOX[(int) ciphertext[i] & 0xff];
        }
        return result;
    }

    /**
     * 根据密钥 获取每轮加密的密钥
     */
    public static byte[] getPWD(byte[] bytes, byte[] RC) {
        byte[] result = new byte[16];
        byte[] w4 = new byte[4];
        for (int i = bytes.length - 1, j = 3; j >= 0; j--, i--) {
            w4[j] = bytes[i];
        }
//        showBytesHex(w4);
        cycleLeft(w4);//循环左移一位
//        showBytesHex(w4);
        byte[] subBytes = subBytes(w4);//映射字节
//        showBytesHex(subBytes);
        //异或
        for (int i = 0; i < 4; i++) {
            w4[i] = (byte) (subBytes[i] ^ bytes[i] ^ RC[i]);
            result[i] = w4[i];
        }
//        showBytesHex(w4);
        byte[] w5 = new byte[4];
        for (int i = 4; i < 8; i++) {
            w5[i - 4] = (byte) (bytes[i] ^ w4[i - 4]);
            result[i] = w5[i - 4];
        }
//        showBytesHex(w5);
        byte[] w6 = new byte[4];
        for (int i = 8; i < 12; i++) {
            w6[i - 8] = (byte) (bytes[i] ^ w5[i - 8]);
            result[i] = w6[i - 8];
        }
//        showBytesHex(w6);
        byte[] w7 = new byte[4];
        for (int i = 12; i < 16; i++) {
            w7[i - 12] = (byte) (bytes[i] ^ w6[i - 12]);
            result[i] = w7[i - 12];
        }
//        showBytesHex(w7);
//        showBytesHex(result);
        return result;
    }

    public static byte[][] getPWDArrays(byte[] passwordBytes) {
        byte[][] RCON = new byte[][]{
                {0x01, 0x00, 0x00, 0x00},
                {0x02, 0x00, 0x00, 0x00},
                {0x04, 0x00, 0x00, 0x00},
                {0x08, 0x00, 0x00, 0x00},
                {0x10, 0x00, 0x00, 0x00},
                {0x20, 0x00, 0x00, 0x00},
                {0x40, 0x00, 0x00, 0x00},
                {(byte) 0x80, 0x00, 0x00, 0x00},
                {0x1b, 0x00, 0x00, 0x00},
                {0x36, 0x00, 0x00, 0x00}
        };

        //获取每轮密钥 共11轮 每轮的密钥长度都是 16 byte
        byte[][] passwordArrays = new byte[11][16];

        passwordArrays[0] = passwordBytes;
//        showBytesHex(passwordArrays[0]);
        for (int i = 1; i < passwordArrays.length; i++) {
            passwordArrays[i] = getPWD(passwordBytes, RCON[i - 1]);
//            showBytesHex(passwordArrays[i]);
        }

        return passwordArrays;
    }


    public static byte[] inverseShiftRows(byte[] bytes) {
        for (int i = bytes.length; i > 4; i--) {
            int offset = i % 4;//偏移量 移动次数
            int tmpIdx = i / 4;//是否需要便宜
            if (offset > 0 && tmpIdx >= offset) {
                for (int j = 1; j <= offset; j++) {
                    int k = i - 4 * j;
                    byte temp = bytes[k];
                    bytes[k] = bytes[i];
                    bytes[i] = temp;
                }
            }
        }
        return bytes;
    }

    /**
     * GF(256) 域 本原多项式
     * 大于 0xFF 的 都需要使用 本原多项式 转化
     * 后续一切转化都基于此 本原多项式 2^8 = 0x1B = 0001 1011 = x^4+x^3+x^1+x^0
     * <p>
     * 2^0=0000 0001=x^0
     * 2^1=0000 0010=x^1
     * 2^2=0000 0100=x^2
     * 2^3=0000 1000=x^3
     * 2^4=0001 0000=x^4
     * 2^5=0010 0000=x^5
     * 2^6=0100 0000=x^6
     * 2^7=1000 0000=x^7
     * 2^8 = 0x1B = 0001 1011 = x^4+x^3+x^1+x^0
     * 2^9=2^8 << 1 = 0011 0110 = x^5+x^4+x^2+x^1
     * 2^10=2^9 << 1 = 0110 1100 = x^6+x^5+x^3+x^2
     * 2^11=2^10 << 1 =1101 1000 = x^7+x^6+x^4+x^3
     * 2^12=2^11 << 1 =2^8 + 1011 0000 =0001 1011 + 1011 0000 = 1010 1011=x^7+x^5+x^3+x^1+x^0
     * 2^13=2^12 << 1 =2^8 + 0101 0110 =0001 1011 + 0101 0110 = 0100 1101=x^6+x^3+x^2+x^0
     * 2^14=2^13 << 1=1001 1010=x^7+x^4+x^3+x
     *
     * @param baseValue 本原多项式
     * @param length    域范围 2^length
     */
    public static int[] GaloisFieldBase(int baseValue, int length) {
        // length=8 域范围是[0,255] 最高位指数是7 会出现的最大指数是 2*7=14 即需要得到 2^14 的替换值。所以数组最大索引是14 数组大小为14+1=15
        int size = 2 * (length - 1) + 1;
        //length=8-->0xff length=4-->0x0f length=16-->0xffff
        int max = (int) (Math.pow(2, length) - 1);
        int[] result = new int[size];
        int temp = 0x01;
        for (int i = 0; i < size; i++) {
            if (0 < i && i < length) {
                temp <<= 1;
            }
            if (length == i) {
                temp = baseValue;
            }
            if (length < i) {
                temp = temp << 1;
                if (temp > max) {
                    int low = temp & max;
                    temp = low ^ baseValue;
                }
            }
            result[i] = temp;
        }
        return result;
    }


    public static int[] GaloisFieldBase8 = GaloisFieldBase(0x1B, 8);


    /**
     * 参考： http://abcdxyzk.github.io/blog/2018/04/16/isal-erase-3/
     * 伽罗华域 算法(有限域算法)
     * <p>
     * primitive ploynomial 本原多项式 域中不可约多项式 是不能够进行因子分解的多项式
     * 是一种特殊的不可约多项式，当一个域上的本原多项式确定了，这个域上的运算也就确定了
     * 本原多项式 一般通过查表可得，同一个域往往有多个本原多项式
     * 通过将域中的元素化为多项式形式，可以将域上的乘法运算转化为普通的多项式乘法再模本原多项式
     * <p>
     * 加法是异或运算
     * 由于异或运算是自身的逆运算
     * 减法也是异或运算
     * <p>
     * 互逆矩阵的元素 乘以[0,255]的结果
     * 两值的伽罗华域乘法的结果是固定的
     * 所以取巧的方式是
     * 直接将两个矩阵内所有元素与 [0，255]进行伽罗华域乘法的结果域一一列出来
     * index是[0,255]
     * value是伽罗华域乘法
     * <p>
     * 计算时直接查询结果域获取伽罗华乘法结果
     */
    public static byte GaloisField(byte b1, byte b2) {
        int result = 0;
        //0x0D * 0x81
        //0000 1101 * 1000 0001
        // 计算过程会超出 byte 范围 所以 使用 int 代换一次
        int I1 = Byte.toUnsignedInt(b1);
        int I2 = Byte.toUnsignedInt(b2);
//        System.err.println(Integer.toBinaryString(I1));
//        System.err.println(Integer.toBinaryString(I2));
        /**
         * 二进制乘法 实质是移位运算
         * 乘法分配律 0000 1101 * 1000 0001
         * = 0000 1000 * 1000 0001 + 0000 0100 * 1000 0001 + 0000 0001 * 1000 0001
         * = 1000 0001 << 3 + 1000 0001 << 2 + 1000 0001 << 0
         * = 100 0000 1000 + 10 0000 0100 + 1000 0001
         * GF 本原多项式替换
         * = (0110 1100 +
         *    0000 1000) +
         *   (0011 0110 +
         *    0000 0100) +
         *   (1000 0001)
         *异或
         * =  1101 0111
         */
        int[] array = new int[8];//用于 保存 分配律 结果
        for (int i = 0; i < array.length; i++) {
            /**
             * 0000 0001
             * 0000 0010
             * 0000 0100
             * 0000 1000
             * 0001 0000
             * 0010 0000
             * 0100 0000
             * 1000 0000
             */
            int tmp1 = 1 << i;
            //当 第i个位置 为 1 时 该项分配律结果需要计算
            if ((tmp1 & I1) == tmp1) {
                // 分配律后的结果 二进制乘法 实质是移位运算
                int shiftVal = I2 << i;
                //大于 255 数据超出 byte长度 8 bit,需要 GF域本原多项式进行转换
                if (shiftVal > 0xff) {
                    // 获取 最右 八位 作为基础
                    int lowVal = shiftVal & 0xff;
                    //从第9位 2^8 开始 逐位 判断是否进行伽罗华域 替换
                    for (int j = 8; j < GaloisFieldBase8.length; j++) {
                        //1 0000 0000
                        int tmp2 = 1 << j;
                        // 需要替换 且 本原多项式 替换值的 索引 为 j
                        if ((shiftVal & tmp2) == tmp2) {
                            //获取伽罗华域值 与最右8位 进行异或
                            lowVal ^= GaloisFieldBase8[j];
                        }
                    }
                    //经过伽罗华域 转换后的 只有 8位的结果
                    array[i] = lowVal;
                } else {
                    //数据不超过 byte 范围 不需要 GF域替换
                    array[i] = shiftVal;
                }
                result ^= array[i];
            }
//            else {
            // 数组默认 0 所以可以不用填充
//                array[i] = 0;
//            }
//            System.err.println(Integer.toBinaryString(array[i]));
            // 不需要 与 0 异或运算，所以 可以往上移
//            result ^= array[i];
        }
//        System.err.println(Integer.toBinaryString(result));
//        System.err.println((byte)result);
        return (byte) result;
    }


    /**
     * 列混淆
     * 矩阵 左乘---在矩阵左边乘以 一个矩阵
     */
    public static byte[] mixCols(byte[] bytes, byte[] a1) {

        byte[] A = new byte[]{
                0x02, 0x01, 0x01, 0x03,
                0x03, 0x02, 0x01, 0x01,
                0x01, 0x03, 0x02, 0x01,
                0x01, 0x01, 0x03, 0x02};

        byte[] result = new byte[16];
        for (int i = 0; i <= bytes.length - 4; i += 4) {
            int j = 0;
            byte a00 = (byte) (GaloisField(A[j], bytes[i]) ^ GaloisField(A[j + 4], bytes[i + 1]) ^ GaloisField(A[j + 2 * 4], bytes[i + 2]) ^ GaloisField(A[j + 3 * 4], bytes[i + 3]));
            byte a01 = (byte) (GaloisField(A[j + 1], bytes[i]) ^ GaloisField(A[j + 4 + 1], bytes[i + 1]) ^ GaloisField(A[j + 2 * 4 + 1], bytes[i + 2]) ^ GaloisField(A[j + 3 * 4 + 1], bytes[i + 3]));
            byte a02 = (byte) (GaloisField(A[j + 2], bytes[i]) ^ GaloisField(A[j + 4 + 2], bytes[i + 1]) ^ GaloisField(A[j + 2 * 4 + 2], bytes[i + 2]) ^ GaloisField(A[j + 3 * 4 + 2], bytes[i + 3]));
            byte a03 = (byte) (GaloisField(A[j + 3], bytes[i]) ^ GaloisField(A[j + 4 + 3], bytes[i + 1]) ^ GaloisField(A[j + 2 * 4 + 3], bytes[i + 2]) ^ GaloisField(A[j + 3 * 4 + 3], bytes[i + 3]));
            result[i] = a00;
            result[i + 1] = a01;
            result[i + 2] = a02;
            result[i + 3] = a03;
        }
        return result;
    }


    /**
     * 列混淆
     * 矩阵 左乘 TODO 逆运算失败
     */
    public static byte[] inverseMixCols(byte[] bytes, byte[] a1) {
        byte[] A = new byte[]{
                0x0E, 0x09, 0x0D, 0x0B,
                0x0B, 0x0E, 0x09, 0x0D,
                0x0D, 0x0B, 0x0E, 0x09,
                0x09, 0x0D, 0x0B, 0x0E};
        byte[] result = new byte[16];
        for (int i = 0; i <= bytes.length - 4; i += 4) {
            int j = 0;
            byte a00 = (byte) (GaloisField(A[j], bytes[i]) ^ GaloisField(A[j + 4], bytes[i + 1]) ^ GaloisField(A[j + 2 * 4], bytes[i + 2]) ^ GaloisField(A[j + 3 * 4], bytes[i + 3]));
            byte a01 = (byte) (GaloisField(A[j + 1], bytes[i]) ^ GaloisField(A[j + 4 + 1], bytes[i + 1]) ^ GaloisField(A[j + 2 * 4 + 1], bytes[i + 2]) ^ GaloisField(A[j + 3 * 4 + 1], bytes[i + 3]));
            byte a02 = (byte) (GaloisField(A[j + 2], bytes[i]) ^ GaloisField(A[j + 4 + 2], bytes[i + 1]) ^ GaloisField(A[j + 2 * 4 + 2], bytes[i + 2]) ^ GaloisField(A[j + 3 * 4 + 2], bytes[i + 3]));
            byte a03 = (byte) (GaloisField(A[j + 3], bytes[i]) ^ GaloisField(A[j + 4 + 3], bytes[i + 1]) ^ GaloisField(A[j + 2 * 4 + 3], bytes[i + 2]) ^ GaloisField(A[j + 3 * 4 + 3], bytes[i + 3]));
            result[i] = a00;
            result[i + 1] = a01;
            result[i + 2] = a02;
            result[i + 3] = a03;
        }
        return result;
    }


}
