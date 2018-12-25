package com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.presenter

import android.content.Context
import android.util.Base64
import com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.api_request.GetLeagueSoccer
import com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.data_controller.LeagueResponse
import com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.data_controller.TeamLeagueData
import com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.ui.SplashView
import com.apwdevs.tugaskade2_footballmatch.api_repo.ApiRepository
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
    val FILTER = "Soccer"
    val fileDir = File(ctx.cacheDir, Base64.encodeToString(FILTER.toByteArray(), Base64.DEFAULT))
    fun getLeagueList() {
        view.onLoadDataStarted()
        doAsync {

            var finalData: List<TeamLeagueData>? = null
            if (!fileDir.exists()) {
                val data = gson.fromJson(
                    apiRepository.doRequest(GetLeagueSoccer.getAllLeague()),
                    LeagueResponse::class.java
                )
                if (data != null) {
                    val arr_data = mutableListOf<TeamLeagueData>()

                    for ((index, value) in data.leagues.withIndex()) {
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
                    view.onDataIsNotLoaded("Cannot get the data from internet!, please make sure you connected the internet!")
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
                    view.onDataIsNotLoaded("IOException: $e. Please make sure you restart the application. If this problem is occur again, try reinstalling the app")
                }

                Thread.sleep(1500)
            }
            uiThread {
                if (finalData != null) {
                    view.onLOadDataFinished()
                    view.showLeagueInSpinner(finalData)
                }
            }
        }
    }
}