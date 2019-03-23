package com.rjdeleon.manobodictionary.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.common.observeOnce
import com.rjdeleon.manobodictionary.feature.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private lateinit var mViewModel: SplashViewModel
    private val mTotalEntryCount = 5630

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        /* Initialize progress views */
        splashProgressBar.max = mTotalEntryCount
        splashProgressText.text = String.format(getString(R.string.splash_progress), 0, mTotalEntryCount)

        /* Create initialization action */
        val onInitializationComplete:(Boolean) -> Unit = {
            if (it) {
                startMainActivity()
            } else {
                // TODO: Display error message
            }
        }

        /* Observe initialization progress */
        mViewModel.getInitializationProgress().observe(this, Observer {
            System.out.println("$it / $mTotalEntryCount")
            if (it == 0) {
                splashProgressLayout.visibility = View.VISIBLE
            }
            splashProgressBar.progress = it
            splashProgressText.text =  String.format(getString(R.string.splash_progress), it, mTotalEntryCount)
        })

        /* Observe live entry count */
        mViewModel.getLiveEntryCount().observeOnce(this, object: Observer<Int> {
            override fun onChanged(it: Int?) {
                if (it == mTotalEntryCount) {
                    onInitializationComplete.invoke(true)
                    mViewModel.getLiveEntryCount().removeObserver(this)
                } else {
                    mViewModel.initializeEntries(onInitializationComplete)
                }
            }
        })
    }

    private fun startMainActivity() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }
}
