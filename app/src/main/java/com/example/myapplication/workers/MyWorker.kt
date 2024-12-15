package com.example.myapplication.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import timber.log.Timber

class MyWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        try {
            Timber.e("Do some work")
            return Result.success()
        } catch (th: Throwable) {
            return Result.failure()
        }
    }
}