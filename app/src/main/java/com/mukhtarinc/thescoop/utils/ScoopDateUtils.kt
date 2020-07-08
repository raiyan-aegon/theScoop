
package com.mukhtarinc.thescoop.utils

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter


/**
 * Created by Raiyan Mukhtar on 6/26/2020.
 */
open class ScoopDateUtils {


    companion object {
        @JvmStatic
        fun newsTimeDifference(time: String): String? {

            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)


            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val position = ParsePosition(0)


            val date : Date? = formatter.parse(time, position)

            val then : Long? = date?.time

            val now :Long? = Date().time
            val seconds:Long = (now!!.minus(then!!)).div(1000)
            val minutes:Long? = seconds.div(60)
            val hours = minutes?.div(60)
            val days = hours?.div(24)
            var friendly: String? = null
            var num: Long = 0
            if (days != null) {
                when {
                    days > 0 -> {
                        num = days
                        friendly = days.toString() + "day"
                    }
                    hours > 0 -> {
                        num = hours
                        friendly = hours.toString() + "h"
                    }
                    minutes > 0 -> {
                        num = minutes
                        friendly = minutes.toString() + "min"
                    }
                    seconds > 0 -> {
                        num = seconds
                        friendly = seconds.toString() + "second"
                    }
                }
            }

            //        if(num>1){
    //            friendly +="s";
    //
    //        }


            // return friendly+" ago";
            return friendly
        }
    }


}