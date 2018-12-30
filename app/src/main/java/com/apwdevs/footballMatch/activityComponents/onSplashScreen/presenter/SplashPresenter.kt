package com.apwdevs.footballMatch.activityComponents.onSplashScreen.presenter

import android.content.Context
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.apiRequest.GetLeagueSoccer
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController.LeagueResponse
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.dataController.TeamLeagueData
import com.apwdevs.footballMatch.activityComponents.onSplashScreen.ui.SplashView
import com.apwdevs.footballMatch.api.ApiRepository
import com.apwdevs.footballMatch.utility.CekKoneksi
import com.apwdevs.footballMatch.utility.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.*

class SplashPresenter(
    private val ctx: Context,
    private val view: SplashView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val isTesting: Boolean = false,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {
    private val FILTER = "Soccer"
    private val fileDir = File(ctx.cacheDir, FILTER)//Base64.encodeToString(FILTER.toByteArray(), Base64.DEFAULT))
    fun getLeagueList() {
        view.onLoadDataStarted()
        GlobalScope.launch(contextPool.main) {

            var msg: String? = null
            var finalData: List<TeamLeagueData>? = null
            if (!fileDir.exists()) {
                var connected = true
                if (!isTesting) {
                    connected = CekKoneksi.isConnected(ctx).await()
                }
                if (connected) {
                    val data = gson.fromJson(
                        apiRepository.doRequest(GetLeagueSoccer.getAllLeague()).await(),
                        LeagueResponse::class.java
                    )
                    if (data != null) {
                        val arrTeamLeagueData = mutableListOf<TeamLeagueData>()

                        for ((_, value) in data.leagues.withIndex()) {
                            if (!value.strSport.equals(FILTER)) continue
                            arrTeamLeagueData.add(value)
                        }
                        val fos = FileOutputStream(fileDir)
                        val oos = ObjectOutputStream(fos)
                        oos.writeObject(arrTeamLeagueData.toList())
                        oos.flush()
                        fos.flush()
                        oos.close()
                        fos.close()
                        finalData = arrTeamLeagueData.toList()

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

                if (!isTesting) {
                    delay(1500)
                } else {
                    Thread.sleep(1500)
                }
            }
            ///uiThread {
                if (finalData != null) {
                    view.onLOadDataFinished()
                    view.showLeagueInSpinner(finalData)
                } else {
                    if (msg != null) view.onDataIsNotLoaded(msg)
                }
            //}
        }
    }
}