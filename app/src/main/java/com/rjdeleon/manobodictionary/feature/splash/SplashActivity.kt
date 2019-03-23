package com.rjdeleon.manobodictionary.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.common.observeOnce
import com.rjdeleon.manobodictionary.feature.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var mViewModel: SplashViewModel
    private val mTotalEntryCount = 5630

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        val onInitializationProgress:(Int) -> Unit = {
            System.out.println("$it / $mTotalEntryCount")
        }


        val onInitializationComplete:(Boolean) -> Unit = {
            if (it) {
                startMainActivity()
            } else {
                // TODO: Display error message
            }
        }

        mViewModel.getLiveEntryCount().observeOnce(this, object: Observer<Int> {
            override fun onChanged(it: Int?) {

                System.out.println("$it / $mTotalEntryCount added!")

                if (it == mTotalEntryCount) {
                    onInitializationComplete.invoke(true)
                    mViewModel.getLiveEntryCount().removeObserver(this)
                } else {
                    mViewModel.initializeEntries(onInitializationProgress, onInitializationComplete)
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
