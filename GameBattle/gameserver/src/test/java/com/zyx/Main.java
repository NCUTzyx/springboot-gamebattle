package com.zyx;

import java.util.Scanner;

/**
 * @author 张宇森
 * @version 1.0
 */
class Object{
    float v;  // 价值
    float w;  // 重量
    float x;  // v/w
    float index; // 序列
}
public class Main {
    public static Scanner sc;
    static float Knapsack(int n,float M, Object p[])
    {
        int i=0;
        float total=0;
        while(p[i].w<M){
            total=total+p[i].v;
            M=M-p[i].w;
            i++;
        }
        float m=(float)M/p[i].w;
        total=total+p[i].v*m;
        return total;
    }
    //从大到小排列
    private static void Sort(int n, Object p[]) {
        float t;
        for(int i=1;i<n;i++)
            for(int j=n-1;j>=i;j--){
                if(p[j-1].x<p[j].x){
                    t=p[j].index;
                    p[j].index=p[j-1].index;
                    p[j-1].index=t;
                    t=p[j].w;
                    p[j].w=p[j-1].w;
                    p[j-1].w=t;
                    t=p[j].v;
                    p[j].v=p[j-1].v;
                    p[j-1].v=t;
                    t=p[j].x;
                    p[j].x=p[j-1].x;
                    p[j-1].x=t;
                }
            }
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.print("请输入物品个数：");
        int n=sc.nextInt();
        System.out.print("请输入背包最大容量：");
        int M=sc.nextInt();
        Object []p=new Object[n];
        System.out.println("请输入物品的序号、重量、价值：");
        for(int i=0;i<n;i++){
            p[i]=new Object();
            p[i].index=sc.nextInt();
            p[i].w=sc.nextInt();
            p[i].v=sc.nextInt();
            p[i].x=p[i].v/p[i].w;
        }
        Sort(n,p);;
        System.out.println( "背包中才放入物品最大总价值为："+Knapsack(n,M,p));
    }
}

