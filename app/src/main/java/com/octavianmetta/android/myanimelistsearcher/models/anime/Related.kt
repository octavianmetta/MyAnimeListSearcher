package com.octavianmetta.android.myanimelistsearcher.models.anime



data class Related (

	val adaptation : List<Adaptation>,
	val prequel : List<Prequel>,
	val sequel : List<Sequel>
)