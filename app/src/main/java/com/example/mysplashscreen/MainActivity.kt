package com.example.mysplashscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import com.example.mysplashscreen.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSplashScreenConfig(splashScreen)

    }

    private fun setSplashScreenConfig(splashScreen: SplashScreen) {

        splashScreen.setKeepOnScreenCondition {
            mainViewModel.isLoading.value == true
        }

        splashScreen.setOnExitAnimationListener{ splashScreenView->
            playZoomingAnimation(splashScreenView)

        }

    }

    private fun playSlideUpAnimation(splashScreenView: SplashScreenViewProvider) {
        val slideUp = ObjectAnimator.ofFloat(
            splashScreenView.view,
            View.TRANSLATION_Y,
            0f,
            -splashScreenView.view.height.toFloat()
        )
        slideUp.interpolator = AnticipateInterpolator()
        slideUp.duration = 200L

        // Call SplashScreenView.remove at the end of your custom animation.
        slideUp.doOnEnd { splashScreenView.remove() }

        // Run your animation.
        slideUp.start()
    }

    private fun playZoomingAnimation(splashScreenView: SplashScreenViewProvider) {
        val animatorSet = AnimatorSet()

        animatorSet.play(
            ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.SCALE_X,
                1f,
                20f,
            )
        ).with(
            ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.SCALE_Y,
                1f,
                20f,
            )
        )
        animatorSet.duration = 800L

        animatorSet.doOnEnd { splashScreenView.remove() }

        animatorSet.start()
    }
}