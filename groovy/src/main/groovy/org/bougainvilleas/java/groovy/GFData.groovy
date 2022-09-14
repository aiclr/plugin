package org.bougainvilleas.java.groovy

System.err.println("c/encrypt/a".replaceAll("/encrypt/","/"))

/**
 * 不可约多项式
 * GF(2^2)
 * GF(2^3)
 */
int n=8
int length = (int) Math.pow(2,n)
int[] GF=new int[length]
int[] BGF=new int[length >> 1]
int j=0
for(i in 0..(length-1))
{
    GF[i]=i
    if(1 == (i & 1))
    {
        BGF[j]=length+i
        j++
    }
}
println(GF)
println(BGF)

/**
 * GF(2^n)多项式乘法
 * @param a
 * @param b
 * @return
 */
int GF_MUL(int n,int a,int b){
    int tmp=1
    int result=0
    0.upto(n-1){
        if(tmp == (a & tmp))
        {
            result ^= (b * tmp)
        }
        tmp <<= 1
    }
    return result
}

println GF_MUL(3,3,7)
println GF_MUL(3,3,5)


static int GF_MUL_PRO(int n, int a, int b){
    int result=0
    int tmp=1
    0.upto(n-1){
        tmp <<= it
        if(tmp==(tmp&a)){
            result^=b<<it
        }
    }
    result
}

println GF_MUL_PRO(2,3,3)
println GF_MUL_PRO(2,2,2)
println GF_MUL_PRO(2,0,0)

/**
 * 每两个基础多项式相乘 结果
 * 4+3+2+1=n*(n+1)/2
 *
 */

int[] ALL_GF=new int[(length-2)*(length-1)/2]
int k=0
2.upto(length-1){it1->
    it1.upto(length-1){it2->
        ALL_GF[k]=GF_MUL_PRO(n,GF[it1],GF[it2])
        k++
    }
}
// 任意两个基础多项式相乘 结果集 不够全
println ALL_GF

for(i1 in BGF){

    boolean is=true

    for(i2 in ALL_GF)
    {
        if(i1==i2)
        {
            is=false
            break
        }
    }
    if(is)
    {
        print i1 +" "
        println Integer.toString(i1,2)
    }


}



