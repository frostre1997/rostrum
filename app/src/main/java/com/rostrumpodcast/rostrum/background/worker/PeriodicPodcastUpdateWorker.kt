package com.rostrumpodcast.rostrum.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class PeriodicPodcastUpdateWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        // TODO: implement periodic update
        return Result.success()
    }
}
