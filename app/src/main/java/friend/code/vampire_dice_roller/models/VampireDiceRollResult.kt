package friend.code.vampire_dice_roller.models

class VampireDiceRollResult(
    val vampireDiceType: VampireDiceType, val vampireDiceRollResultType: VampireDiceRollResultType,
) {
    override fun toString(): String {
        return vampireDiceType.toString() + "\n" + vampireDiceRollResultType
    }
}