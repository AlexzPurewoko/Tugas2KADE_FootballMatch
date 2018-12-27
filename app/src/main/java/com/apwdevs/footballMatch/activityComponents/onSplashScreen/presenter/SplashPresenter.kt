package com.apwdevs.footballMatch.activityComponents.onSplashScreen.presenter

import android.content.Context
import android.util.Base64
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.apiRequest.GetLeagueSoccer
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController.LeagueResponse
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController.TeamLeagueData
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.ui.SplashView
import com.apwdevs.footballMatch.api.ApiRepository
import com.apwdevs.footballMatch.utility.CekKoneksi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.*

class SplashPresenter(
    private val ctx: Context,
    private val view: SplashView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    private val FILTER = "Soccer"
    private val fileDir = File(ctx.cacheDir, Base64.encodeToString(FILTER.toByteArray(), Base64.DEFAULT))
    fun getLeagueList() {
        view.onLoadDataStarted()
        doAsync {
            var msg: String? = null
            var finalData: List<TeamLeagueData>? = null
            if (!fileDir.exists()) {
                if (CekKoneksi.isConnected(ctx)) {
                    val data = gson.fromJson(
                        apiRepository.doRequest(GetLeagueSoccer.getAllLeague()),
                        LeagueResponse::class.java
                    )
                    if (data != null) {
                        val arr_data = mutableListOf<TeamLeagueData>()

                        for ((_, value) in data.leagues.withIndex()) {
                            if (!value.strSport.equals(FILTER)) continue
                            arr_data.add(value)
                        }
                        val fos = FileOutputStream(fileDir)
                        val oos = ObjectOutputStream(fos)
                        oos.writeObject(arr_data.toList())
                        oos.flush()
                        fos.flush()
                        oos.close()
                        fos.close()
                        finalData = arr_data.toList()

                    } else {
                        msg = "Cannot get the data from internet!, please make sure you connected the internet!"
                    }
                } else {
                    msg = "Cannot get the data from internet!, please make sure you connected the internet!"
                }
            } else {
                try {
                    val fis = FileInputStream(fileDir)
                    val ois = ObjectInputStream(fis)
                    finalData = ois.readObject() as List<TeamLeagueData>
                    ois.close()
                    fis.close()
                } catch (e: IOException) {
                    fileDir.delete()
                    msg =
                            "IOException: $e. Please make sure you restart the application. If this problem is occur again, try reinstalling the app"
                }

                Thread.sleep(1500)
            }
            uiThread {
                if (finalData != null) {
                    view.onLOadDataFinished()
                    view.showLeagueInSpinner(finalData)
                } else {
                    if (msg != null) view.onDataIsNotLoaded(msg)
                }
            }
        }
    }
}