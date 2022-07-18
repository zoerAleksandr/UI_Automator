package com.geekbrains.tests.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.geekbrains.tests.view.details.DetailsActivity
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class DetailsScreenTest {
    companion object {
        private const val TIMEOUT = 5000L
        private const val COUNT = 50
    }

    private val uiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = DetailsActivity.getIntent(context, COUNT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_DetailsScreenIsStarted() {
        val buttonDec = uiDevice.findObject(By.res(packageName, "decrementButton"))
        assertNotNull(buttonDec)
    }

    @Test
    fun test_decrementButtonIsWorking_Positive() {
        val totalText = uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        val buttonDec = uiDevice.findObject(By.res(packageName, "decrementButton"))

        buttonDec.click()
        Assert.assertEquals(totalText.text.toString(), "Number of results: 49")
    }

    @Test
    fun test_incrementButtonIsWorking_Positive() {
        val totalText = uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        val buttonInc = uiDevice.findObject(By.res(packageName, "incrementButton"))

        buttonInc.click()
        Assert.assertEquals(totalText.text.toString(), "Number of results: 51")
    }
}