/*
 * Copyright 2016 电子计算技术研究所
 * All Right Reserved
 * Author：zbb
 * Create Date：2016-7-28
 */
package com.rails.common.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
	
	public  static List array2List(Object[] array){
		List result = new ArrayList();
		for(int i=0;i<array.length;i++){
			result.add(array[i]);
		}
		return result;
	}
}
