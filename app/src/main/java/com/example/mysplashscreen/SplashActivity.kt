package com.example.mysplashscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setSplashScreenConfig(splashScreen)

        openMainActivity()
    }

    // use it if you dont want to show it on devices < 12, call from onCreate()
    private fun handleDoubleSplashScreen(){
        val content = findViewById<View>(android.R.id.content)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            content.viewTreeObserver.addOnDrawListener { false }
        }
    }
    private fun setSplashScreenConfig(splashScreen: SplashScreen) {

        splashScreen.setKeepOnScreenCondition {
            mainViewModel.isLoading.value == true
        }

        splashScreen.setOnExitAnimationListener{ splashScreenView->
            playFadeAnimation(splashScreenView)
        }

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

    private fun playFadeAnimation(splashScreenView: SplashScreenViewProvider) {
//        no movement, uncomment it in order to add a movement, then call animatorSet.with(move)
//        val move: ObjectAnimator = ObjectAnimator.ofFloat(splashScreenView.view, "translationY", 100f)
//        move.setDuration(3000)

        val alpha1 = ObjectAnimator.ofFloat(splashScreenView.view, "alpha", 0.5f)
        alpha1.duration = 1000

        val alpha2: ObjectAnimator = ObjectAnimator.ofFloat(splashScreenView.view, "alpha", 0f)
        alpha2.duration = 2000

        val animatorSet = AnimatorSet()
        animatorSet.play(alpha2).before(alpha1)
        animatorSet.start()

        animatorSet.duration = 800L

        animatorSet.doOnEnd { splashScreenView.remove() }

        animatorSet.start()
    }

    private fun openMainActivity() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}