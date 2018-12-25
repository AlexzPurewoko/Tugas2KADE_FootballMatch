package com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.data_controller

import com.google.gson.annotations.SerializedName

data class DetailMatchDataClass(
    // statistics

    @SerializedName("dateEvent")
    val dateEvent: String?,
    //// id team
    @SerializedName("idHomeTeam")
    val idHomeTeam: String?,
    @SerializedName("idAwayTeam")
    val idAwayTeam: String?,
    //// goals
    @SerializedName("intHomeScore")
    val intHomeScore: String?,
    @SerializedName("intAwayScore")
    val intAwayScore: String?,
    //// shots
    @SerializedName("intHomeShots")
    val intHomeShots: String?,
    @SerializedName("intAwayShots")
    val intAwayShots: String?,
    //// team_name
    @SerializedName("strHomeTeam")
    val strHomeTeam: String?,
    @SerializedName("strAwayTeam")
    val strAwayTeam: String?,
    //// Goal Details
    @SerializedName("strHomeGoalDetails")
    val strHomeGoalDetails: String?,
    @SerializedName("strAwayGoalDetails")
    val strAwayGoalDetails: String?,
    //// Red Cards
    @SerializedName("strHomeRedCards")
    val strHomeRedCards: String?,
    @SerializedName("strAwayRedCards")
    val strAwayRedCards: String?,
    //// Yellow Cards
    @SerializedName("strHomeYellowCards")
    val strHomeYellowCards: String?,
    @SerializedName("strAwayYellowCards")
    val strAwayYellowCards: String?,

    /////////////
    // lineups
    //// Goalkeeper
    @SerializedName("strHomeLineupGoalkeeper")
    val strHomeLineupGoalkeeper: String?,
    @SerializedName("strAwayLineupGoalkeeper")
    val strAwayLineupGoalkeeper: String?,
    //// Defense
    @SerializedName("strHomeLineupDefense")
    val strHomeLineupDefense: String?,
    @SerializedName("strAwayLineupDefense")
    val strAwayLineupDefense: String?,
    //// Mildfield
    @SerializedName("strHomeLineupMidfield")
    val strHomeLineupMidfield: String?,
    @SerializedName("strAwayLineupMidfield")
    val strAwayLineupMidfield: String?,
    //// Forward
    @SerializedName("strHomeLineupForward")
    val strHomeLineupForward: String?,
    @SerializedName("strAwayLineupForward")
    val strAwayLineupForward: String?,
    //// Subtitues
    @SerializedName("strHomeLineupSubstitutes")
    val strHomeLineupSubstitutes: String?,
    @SerializedName("strAwayLineupSubstitutes")
    val strAwayLineupSubstitutes: String?

)
