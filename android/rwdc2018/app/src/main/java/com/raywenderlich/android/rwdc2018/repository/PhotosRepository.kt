/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.raywenderlich.android.rwdc2018.repository

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.ComponentName
import android.content.Context
import android.os.*
import android.util.Log
import androidx.work.*
import com.raywenderlich.android.rwdc2018.app.PhotosUtils
import com.raywenderlich.android.rwdc2018.app.RWDC2018Application
import com.raywenderlich.android.rwdc2018.service.DownloadWorker
import com.raywenderlich.android.rwdc2018.service.LogJobService
import com.raywenderlich.android.rwdc2018.service.PhotosJobService
import java.util.concurrent.TimeUnit


class PhotosRepository : Repository {
  private val photosLiveData = MutableLiveData<List<String>>()
  private val bannerLiveData = MutableLiveData<String>()


  companion object {
    const val DOWNLOAD_WORK_TAG = "DOWNLOAD_WORK_TAG"
  }

  init {
//    scheduleFetchJob()
//    scheduleLogJob()
    schedulePeriodicWorkRequest()
  }
  override fun getPhotos(): LiveData<List<String>> {
//    fetchJsonData()
    FetchPhotosAsyncTask({ photos ->
      photosLiveData.value = photos
    }).execute()
    return photosLiveData
  }

  override fun getBanner(): LiveData<String> {
    val runnable = Runnable {
      val photoString = PhotosUtils.photoJsonString()
      val banner = PhotosUtils.bannerFromJsonString(photoString ?: "")

      if (banner != null) {
        bannerLiveData.postValue(banner)
      }
    }
    val thread = Thread(runnable)
    thread.start()
    return bannerLiveData
  }

  private fun schedulePeriodicWorkRequest() {
    val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .build()

    val workManager = WorkManager.getInstance()

    val request: WorkRequest = PeriodicWorkRequestBuilder<DownloadWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag(DOWNLOAD_WORK_TAG)
            .build()

    workManager.cancelAllWorkByTag(DOWNLOAD_WORK_TAG) // to cancel any previous scheduled task
    workManager.enqueue(request) // then start a new task
  }

//  private fun scheduleFetchJob() {
//    val jobScheduler = RWDC2018Application.getAppContext().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
//    val jobInfo = JobInfo.Builder(1000,
//            ComponentName(RWDC2018Application.getAppContext(), PhotosJobService::class.java))
//            .setPeriodic(900000)
//            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//            .build()
//    jobScheduler.schedule(jobInfo)
//  }
//
//
//  private fun scheduleLogJob() {
//    val jobScheduler = RWDC2018Application.getAppContext().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
//    val jobInfo = JobInfo.Builder(1001,
//            ComponentName(RWDC2018Application.getAppContext(), LogJobService::class.java))
//            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//            .build()
//    jobScheduler.schedule(jobInfo)
//  }
  private fun fetchJsonData() {
//    val handler = object : Handler(Looper.getMainLooper()){
//      override fun handleMessage(msg: Message?) {
//        val bundle = msg?.data
//        val photos = bundle?.getStringArrayList("PHOTOS_KEY")
//        photosLiveData.value = photos
//      }
//    }
    val runnable = Runnable {
      val photosString = PhotosUtils.photoJsonString()
      val photos = PhotosUtils.photoUrlsFromJsonString(photosString ?: "")

      if (photos != null) {
//          val message = Message()
//        val bundle = Bundle()
//        bundle.putStringArrayList("PHOTOS_KEY", photos)
//        message.data = bundle
//        handler.sendMessage(message)
        photosLiveData.postValue(photos)
      }
    }

    val thread = Thread(runnable)
    thread.start()
  }

  private class FetchPhotosAsyncTask(val callback: (List<String>) -> Unit)
    : AsyncTask<Void, Void, List<String>>() {
    override fun doInBackground(vararg params: Void): List<String>? {
      val photosString = PhotosUtils.photoJsonString()
      return PhotosUtils.photoUrlsFromJsonString(photosString ?: "")
    }

    override fun onPostExecute(result: List<String>?) {
      if (result != null) {
        callback(result)
      }
    }
  }
}