package friend.code.vampire_dice_roller.ui.main

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import friend.code.vampire_dice_roller.utilities.MathUtils

private const val ACCELERATION_RECORD_SIZE: Int = 5
private const val SHAKE_ACCELERATION_THRESHOLD: Float = 20f

class ShakeEventListener(
    private val sensorManager: SensorManager,
    private val mainFragment: MainFragment,
) : SensorEventListener {

    private val TAG: String = this.javaClass.simpleName
    private var accelerationRecord: FloatArray = FloatArray(ACCELERATION_RECORD_SIZE)
    private var i: Int = 0

    fun onResume() {
        sensorManager.registerListener(this,
                                       sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                                       SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun onPause() {
        sensorManager.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.values != null && isShake(event.values)) {
            mainFragment.roll()
        }
    }

    private fun isShake(values: FloatArray): Boolean {
        accelerationRecord[i] = MathUtils.magnitude(values.toTypedArray()).toFloat()

        if (i >= ACCELERATION_RECORD_SIZE - 1) {
            i = 0
        } else {
            i += 1
        }

        return if (getMaxValue(accelerationRecord) >= SHAKE_ACCELERATION_THRESHOLD) {
            accelerationRecord = FloatArray(ACCELERATION_RECORD_SIZE)
            true
        } else {
            false
        }
    }

    private fun getMaxValue(values: FloatArray): Float {
        var maxValue: Float = values[0]
        for (value in values) {
            if (maxValue > value) {
                maxValue = value
            }
        }
        return maxValue
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
}