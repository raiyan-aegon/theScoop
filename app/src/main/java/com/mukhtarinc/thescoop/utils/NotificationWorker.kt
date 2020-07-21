package com.mukhtarinc.thescoop.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mukhtarinc.thescoop.R
import com.mukhtarinc.thescoop.data.network.NewsAPIService
import com.mukhtarinc.thescoop.model.Article
import com.mukhtarinc.thescoop.ui.activities.TheScoopDetailsActivity
import javax.inject.Inject


/**
 * Created by Raiyan Mukhtar on 7/20/2020.
 */
class NotificationWorker @Inject constructor(appContext : Context, parameters: WorkerParameters ) : Worker(appContext,parameters){

    @Inject lateinit var apiService: NewsAPIService

    init {

        com.mukhtarinc.thescoop.di.application.Provider.appComponent?.inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun doWork(): Result {

        Log.d(TAG, "Api Service : $apiService")

        val article: Article = getArticle()

        val notifyManager : NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val taskStackBuilder =  TaskStackBuilder.create(applicationContext)


        val intent  = Intent(applicationContext,TheScoopDetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("article", article)
        bundle.putParcelable("source", article.getSource)
        intent.putExtras(bundle)
        taskStackBuilder.addNextIntentWithParentStack(intent)




        val pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            val notificationChannel = NotificationChannel("1001", "scoopNews", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)


            val notificationCompat = NotificationCompat.Builder(applicationContext,"1001")
                    .setContentTitle(article.getSource.name)
                    .setContentText(article.title)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(article.title))
                    .setSmallIcon(R.drawable.ic_stat_library_books)
                    .setContentIntent(pendingIntent)
                    .setChannelId("1001")
            notifyManager.createNotificationChannel(notificationChannel)
            notifyManager.notify((System.currentTimeMillis().toInt()),notificationCompat.build())



        }else{
            val notification : Notification = NotificationCompat.Builder(applicationContext)
                    .setContentTitle(article.getSource.name)
                    .setContentText(article.title)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(article.title))
                    .setSmallIcon(R.drawable.ic_stat_library_books)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setVibrate(longArrayOf(100,200,300,400,500,400,300,200,400))
                    .build()

            notifyManager.notify((System.currentTimeMillis().toInt()),notification)
        }


        Log.d(TAG, "doWork: ")

        return Result.success()
    }



    private fun getArticle() : Article{
         var article: Article? = null
        val random = (0..5).shuffled().random()

        Log.d(TAG, "Number : $random")
        apiService.getNotificationArticle("us","en",Constants.apiKey,2)
                .take(1)
                .subscribe {

                    article = it.articles[random]

                }

        return article!!


    }


    companion object{

        private const val TAG = "NotificationWorker"
    }
}