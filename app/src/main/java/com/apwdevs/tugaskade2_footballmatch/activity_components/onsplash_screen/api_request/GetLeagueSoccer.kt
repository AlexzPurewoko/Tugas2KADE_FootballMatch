package com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.api_request

object GetLeagueSoccer {
    fun getAllLeague(): String {
        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("all_leagues.php")
            .build()
            .toString()*/
        return "https://www.thesportsdb.com/api/v1/json/1/all_leagues.php"
    }
}