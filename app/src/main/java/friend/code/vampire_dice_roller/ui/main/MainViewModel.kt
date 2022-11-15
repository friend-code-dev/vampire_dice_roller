package friend.code.vampire_dice_roller.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import friend.code.vampire_dice_roller.models.Pool
import friend.code.vampire_dice_roller.models.VampireDice
import friend.code.vampire_dice_roller.models.VampireDiceType

class MainViewModel : ViewModel() {
    var pool: Pool = Pool(emptyList())

    @RequiresApi(Build.VERSION_CODES.N)
    fun roll(difficulty: Int?) {
        pool.roll(difficulty ?: 0)
    }

    fun updatePool(regularDiceCount: Int?, hungerDiceCount: Int?) {
        pool = Pool(createVampireDiceList(regularDiceCount ?: 0, hungerDiceCount ?: 0))
    }

    private fun createVampireDiceList(
        regularDiceCount: Int,
        hungerDiceCount: Int,
    ): List<VampireDice> {
        val vampireDiceList: MutableList<VampireDice> = mutableListOf()
        for (vampireDiceType in VampireDiceType.values()) {
            if (vampireDiceType == VampireDiceType.REGULAR) {
                if (regularDiceCount > 0) {
                    addDiceToVampireDiceList(vampireDiceList,
                                             VampireDiceType.REGULAR,
                                             regularDiceCount)
                }
            } else {
                if (hungerDiceCount > 0) {
                    addDiceToVampireDiceList(vampireDiceList,
                                             VampireDiceType.HUNGER,
                                             hungerDiceCount)
                }
            }
        }
        return vampireDiceList
    }

    private fun addDiceToVampireDiceList(
        vampireDiceList: MutableList<VampireDice>,
        vampireDiceType: VampireDiceType,
        diceCount: Int,
    ) {
        for (i in 1..diceCount) {
            vampireDiceList.add(VampireDice(vampireDiceType))
        }
    }
}