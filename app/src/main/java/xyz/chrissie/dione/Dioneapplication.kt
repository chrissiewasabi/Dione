package xyz.chrissie.dione

import android.app.Application
import android.content.Context

class Dioneapplication : Application() {
    internal var context: Context
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    init {
        context = this
    }

    private fun getAppcontext(): Context = context
}