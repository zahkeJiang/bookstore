package com.amazon.util;

import java.util.Random;
import java.util.UUID;

/**
 * In server->com.dayukeji.common.utils
 * <p>
 * Create in 16:33 2017/12/10
 *
 * @author canfuu
 * @version v1.0:say explain
 */
public class CodeUtil {
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-","");
	}

	public static String getRandom(int number){
	    int max = ((Double) Math.pow(10, (double) number)).intValue() -1;
	    int min = ((Double) Math.pow(10, (double) number-1)).intValue() ;
        Random r = new Random();
        int s = r.nextInt(max-min)+min;
	    return s+"";
    }
}
