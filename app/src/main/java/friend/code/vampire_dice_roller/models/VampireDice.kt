package friend.code.vampire_dice_roller.models

/**
 * [VampireDice] are 10-sided dice with special faces. There are 2 [VampireDiceType]s:
 *
 *
 *  1. [VampireDiceType.REGULAR]
 *  2. [VampireDiceType.HUNGER]
 *
 */
class VampireDice(val vampireDiceType: VampireDiceType) {
    private val dice = Dice(10)
    fun roll(): VampireDiceRollResult {
        return VampireDiceRollResult(
            vampireDiceType, VampireDiceRollResultType.from(vampireDiceType, dice.roll()))
    }
}