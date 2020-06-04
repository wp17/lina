package com.github.wp17.lina.util;

import java.util.List;
import java.util.Random;

/**
 * 提供随机数相关工具函数。
 */
public class RandomUtil {
	// 公共随机数生成器
	public static Random random = new Random();
	
	/**
	 * 根据概率判断是否命中(使用公共随机数生成器)。
	 * @param chance 命中率分子
	 * @param base 命中率的分母
	 * @return 是否命中
	 */
	public static boolean hit(int chance, int base){
		return hit(random, chance, base);
	}
	
	/**
	 * 根据概率判断是否命中(使用指定随机数生成器)
	 * @param rnd 随机数生成器
	 * @param chance 命中率分子
	 * @param base 命中率分母
	 * @return
	 */
    public static boolean hit(Random rnd, int chance, int base){
        int r = rnd.nextInt(base);
        if(r < chance)
            return true;
        return false;
    }
    
    /**
     * 生成一个指定范围内的随机数（使用公共随机数生成器）。
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return
     */
    public static int rand(int min, int max){
    	return rand(random, min, max);
    }

	/**
	 * 生成一个(0, bound]范围内的随机数
	 * @param random 随机函数
	 * @param bound 最大值
	 * @return 随机结果
	 */
	public static int rand(Random random, int bound){
		return random.nextInt(bound) + 1;
	}
    
    /**
     * 生成一个指定范围内的随机数（使用指定随机数生成器）。
     * @param rnd 随机数生成器
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return
     */
    public static int rand(Random rnd, int min, int max){
    	if(min==max)
    		return min;
        return rnd.nextInt(max - min + 1) + min;
    }
    
    /**
     * 根据多个项目的权重，随机选出一个。每个项目的命中率=项目权重/总权重
     * @param values 每个项目的权重
     * @return 选中项目的索引
     */
    public static int chooseFromList(List<Float> values) {
    	float total = 0.0f;
    	for (int i = 0; i < values.size(); i++) {
    		total += values.get(i);
    	}
    	float v = random.nextFloat() * total;
    	for (int i = 0; i < values.size(); i++) {
    		v -= values.get(i);
    		if (v <= 0) {
    			return i;
    		}
    	}
    	return values.size() - 1;
    }
}
