package org.mendora.util.generate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 *  生成id
 * Created by wang007 on 2017/8/8.
 */
public class IDUtils {

    public static final DateTimeFormatter Formatter4GenerateID = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS") ;

    /**
     * yyyyMMddHHmmssSSS模式的id
     * @return
     */
    public static String idWithTime() {
        return LocalDateTime.now().format(Formatter4GenerateID) ;
    }

    /**
     * yyyyMMddHHmmssSSS+randomNumCount个随机数 模式的id
     * @param randomNumCount randomNumCount >0 || randomNumCount < 18
     * @return
     */
    public static String idWithTimeRandom(int randomNumCount) {
        if(randomNumCount < 0 || randomNumCount > 18) throw new IllegalArgumentException("randomNum > 0 || < 18 is required") ;
        StringBuilder sb = new StringBuilder(randomNumCount+1).append("1") ;
        for(;randomNumCount-->0;sb.append("0")) ;
        return idWithTime()+(long)(Math.random() * Long.parseLong(sb.toString())) ;
    }

    /**
     * yyyyMMddHHmmssSSS+3个随机数 模式的id
     * @return
     */
    public static String idWithTimeRandom() {
        return idWithTimeRandom(3) ;
    }

    public static String idWithTimestamp(){
        return System.nanoTime()+"";
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "") ;
    }


}
