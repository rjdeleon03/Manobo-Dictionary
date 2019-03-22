package com.rjdeleon.manobodictionary.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.feature.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var mViewModel: SplashViewModel
    private var mTotalEntryCount: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        mViewModel.getTotalEntryCount().observe(this, Observer<Int> {
            System.out.println("Total count: $it")
            mTotalEntryCount = it
        })

        mViewModel.getLiveEntryCount().observe(this, Observer<Int> {
            System.out.println("So far count: $it")
            if (mTotalEntryCount != null && mTotalEntryCount == it) {
                val mainActivityIntent = Intent(this, MainActivity::class.java)
                startActivity(mainActivityIntent)
                finish()
            }
        })

        mViewModel.initializeEntries()
    }
}
