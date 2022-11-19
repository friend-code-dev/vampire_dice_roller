package friend.code.vampire_dice_roller

import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import friend.code.vampire_dice_roller.ui.main.MainFragment
import friend.code.vampire_dice_roller.ui.main.ShakeEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var shakeEventListener: ShakeEventListener
    private lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        mainFragment = MainFragment.newInstance()
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        shakeEventListener = ShakeEventListener(sensorManager, mainFragment)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()
        }
    }

    override fun onResume() {
        super.onResume()
        shakeEventListener.onResume()
    }

    override fun onPause() {
        super.onPause()
        shakeEventListener.onPause()
    }
}