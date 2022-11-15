package friend.code.vampire_dice_roller.models

/**
 * [VampireDiceType.REGULAR] have 3 faces:
 *
 *  1. [VampireDiceRollResultType.CRITICAL_SUCCESS] (1 face)
 *  2. [VampireDiceRollResultType.SUCCESS] (4 faces)
 *  3. [VampireDiceRollResultType.FAILURE] (5 faces)
 *
 * [VampireDiceType.HUNGER] have 4 faces
 *
 *  1. [VampireDiceRollResultType.SUCCESS_OR_MESSY_CRITICAL] (1 face)
 *  2. [VampireDiceRollResultType.SUCCESS] (4 faces)
 *  3. [VampireDiceRollResultType.FAILURE] (4 faces)
 *  4. [VampireDiceRollResultType.BESTIAL_FAILURE] (1 face)
 *
 * For each level of Hunger affecting a vampire character (up to a maximum 5), they gain one
 * Hunger die. Hunger dice are ten-sided, just like regular dice. When a player
 * builds a dice pool for a vampire character, they exchange regular dice from that pool for Hunger
 * dice on a one-for-one basis.
 *
 * If the dice pool for the roll is lower than the character’s Hunger, simply roll a number of
 * Hunger dice equal to the dice pool.
 *
 * The exception: Characters never include Hunger dice in Checks, Willpower, or Humanity dice
 * pools.
 */
enum class VampireDiceType {
    REGULAR, HUNGER
}