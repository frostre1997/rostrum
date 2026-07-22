package com.rostrumpodcast.rostrum.manager

import com.rostrumpodcast.rostrum.api.db.AppDatabase

class SubscriptionManager(
    val db: AppDatabase
) {

    suspend fun subscribe(origin: String) {
        db.podcastubscriptions()
            .subscribe(origin)

        db.syncActions()
            .addSubscribe(origin)
    }

    suspend fun unsubscribe(origin: String) {
        db.podcastubscriptions()
            .unsubscribe(origin)

        db.syncActions()
            .addUnsubscribe(origin)
    }

}