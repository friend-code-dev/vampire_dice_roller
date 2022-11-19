package friend.code.vampire_dice_roller.utilities

import kotlin.math.pow
import kotlin.math.sqrt

class MathUtils {

    companion object {
        fun <T : Number> magnitude(vector: Array<T>): Double {
            return sqrt(sumSqr(vector))
        }

        private fun <T : Number> sumSqr(values: Array<T>): Double {
            return values.sumOf { value -> value.toDouble().pow(2.0) }
        }
    }
}