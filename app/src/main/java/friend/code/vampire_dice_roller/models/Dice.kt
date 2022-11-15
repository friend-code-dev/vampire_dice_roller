package friend.code.vampire_dice_roller.models

import java.util.*

class Dice(private val sides: Int) {
    private val random = Random()
    fun roll(): Int {
        return random.nextInt(sides) + 1
    }
}