package com.apwdevs.tugaskade2_footballmatch.api_repo

import java.net.URL

class ApiRepository {
    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}