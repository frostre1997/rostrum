package com.rostrumpodcast.rostrum.api.db.model

import androidx.room.Embedded

data class ListWithContains(
    @Embedded val list: ListModel,   // Tell Room to map all ListModel fields from the query
    val contains: Boolean
)

