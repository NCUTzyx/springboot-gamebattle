package com.zyx;


import java.util.Arrays;

class Solution {

    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        int[] ints = twoSum(nums, 9);
        System.out.println(Arrays.toString(ints));
    }


    public static int[] twoSum(int[] nums, int target) {

        int[] res = new int[2]; 

        int k = 0;
        for(int i = 0; i < nums.length; i ++){
            if(nums[i] >= target){
                break;
            }
            k ++;
        }

        for(int i = 0; i < k; i ++)
            for(int j = 0; j < i; j ++)
                if(nums[i] + nums[j] == target){
                    System.out.println(nums[i]);
                    System.out.println(nums[j]);
                    System.out.println(i);
                    System.out.println(j);
                    res[0] = i;
                    res[1] = j;
                    break;
                } 

        return res;
    }
}