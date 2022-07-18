package com.geekbrains.tests.automator

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class OpenOtherAppsTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    @Test
    fun test_OpenSettings() {
        uiDevice.pressHome()
        //Открываем экран со списком установленных приложений.
        //Обратите внимание, что на устройстве, для которого писался этот тест (Android_Emulator - Pixel_2_Q_10_api),
        //список приложений открывается свайпом снизу вверх на главном экране.
        //Метод swipe принимает координаты начальной и конечной точки свайпа.
        //В нашем случае это примерно снизу экрана строго вверх. Steps указывает, в
        //какое количество шагов мы хотим осуществить смахивание: чем выше число,
        //тем медленнее будет осуществляться свайп
        uiDevice.swipe(500, 1500, 500, 0, 5)

        //Для других устройств список установленных приложений может открываться по другому.
        //Часто это иконка на главном экране под названием Apps.
        //Для этого достаточно свернуть все приложения через uiDevice.pressHome() и
        //и найти Apps на главном экране
        //val allAppsButton: UiObject = uiDevice.findObject(UiSelector().description("Apps"))
        //allAppsButton.clickAndWaitForNewWindow()
        //Вполне возможно (встречается на старых устройствах), что приложения находятся на вкладке Apps (будет еще вкладка Widgets).
        //Тогда еще найдем вкладку и выберем ее
        //val appsTab: UiObject = uiDevice.findObject(UiSelector().text("Apps"))
        //appsTab.click()

        //Приложений, обычно, установлено столько, что кнопка может быть за границей экрана
        //Тогда корневым контейнером будет Scrollable.
        //Если же все приложения умещаются на одном экране, то достаточно установить scrollable(false)
        val appViews = UiScrollable(UiSelector().scrollable(false))
        //Если прокрутка горизонтальная (встречается на старых устройствах), нужно установить
        // горизонтальную прокрутку (по умолчанию она вертикальная)
        //appViews.setAsHorizontalList()

        //Находим в контейнере настройки по названию иконки
        val settingsApp = appViews
            .getChildByText(
                UiSelector()
                    .className(TextView::class.java.name),
                "Settings"
            )
        //Открываем
        settingsApp.clickAndWaitForNewWindow()

        //Убеждаемся, что Настройки открыты
        val settingsValidation =
            uiDevice.findObject(UiSelector().packageName("com.android.settings"))
        Assert.assertTrue(settingsValidation.exists())
    }
}
