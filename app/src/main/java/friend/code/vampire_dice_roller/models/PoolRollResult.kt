package friend.code.vampire_dice_roller.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

/**
 * When you roll a dice pool, every individual die result of 6 or higher is a success, including
 * a result of 10 (represented as 0 on most d10s). If you roll a number of successes equal to or
 * exceeding the Difficulty number, the rules call that a win.
 *
 *
 * **criticals** result of 10 on two regular dice (00) is a *critical
 * success*. A critical success counts as two additional successes above the two 10s (four
 * total successes). A winning roll containing at least one critical success is called a
 * *critical win*. Each pair of 10s count as their own critical success, so three 10s (000)
 * would add up to five successes, whereas four 10s (0000) would count as eight.
 *
 *
 * Hunger dice function as regular dice in tests and contests, scoring successes on a 6 or
 * higher. Hunger dice can not be re-rolled by using Willpower, however. In addition, rolling a
 * 0 (10) or 1 on a Hunger die carries additional consequences: messy criticals and bestial
 * failures.
 *
 *
 * **Messy Critical**A critical win in which one or more 10s appears on a Hunger die is a
 * messy critical. You do not roll Hunger dice on a Willpower roll or on a Humanity roll, so you
 * cannot get a messy critical on these rolls.
 *
 *
 * **Bestial Failure** A failed roll (not enough successes to reach the Difficulty or to
 * beat an opposing contestant’s number of successes) in which one or more Hunger dice come up a
 * 1 is a bestial failure.
 */
class PoolRollResult @RequiresApi(api = Build.VERSION_CODES.N) constructor(
    vampireDiceRollResults: List<VampireDiceRollResult>,
    difficulty: Int,
) {
    private val margin: Int
    private var poolRollResultType: PoolRollResultType? = null

    init {
        val vampireDiceRollResultCounts = countVampireDiceRollResultTypes(vampireDiceRollResults)
        val totalSuccesses = computeTotalSuccesses(vampireDiceRollResultCounts)
        margin = totalSuccesses - difficulty
        poolRollResultType = if (margin >= 0) {
            determineSuccessType(vampireDiceRollResultCounts)
        } else {
            determineFailureType(vampireDiceRollResultCounts)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun countVampireDiceRollResultTypes(
        vampireDiceRollResults: List<VampireDiceRollResult>,
    ): Map<VampireDiceRollResultType, Int> {
        val vampireDiceRollResultCounts: MutableMap<VampireDiceRollResultType, Int> = EnumMap(
            VampireDiceRollResultType::class.java)
        Arrays.stream(VampireDiceRollResultType.values())
            .parallel()
            .forEach { vampireDiceRollResultType: VampireDiceRollResultType ->
                vampireDiceRollResultCounts[vampireDiceRollResultType] =
                    countVampireDiceRollResultsWithGivenVampireDiceRollResultType(
                        vampireDiceRollResults,
                        vampireDiceRollResultType).toInt()
            }
        return vampireDiceRollResultCounts
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun countVampireDiceRollResultsWithGivenVampireDiceRollResultType(
        vampireDiceRollResults: List<VampireDiceRollResult>,
        vampireDiceRollResultType: VampireDiceRollResultType,
    ): Long {
        return if (anyVampireDiceRollResultsHaveGivenVampireDiceRollResultType(
                vampireDiceRollResults, vampireDiceRollResultType)
        ) {
            vampireDiceRollResults.parallelStream()
                .filter { vampireDiceRollResult: VampireDiceRollResult ->
                    vampireDiceRollResultType ==
                            vampireDiceRollResult.vampireDiceRollResultType
                }
                .count()
        } else {
            0
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun anyVampireDiceRollResultsHaveGivenVampireDiceRollResultType(
        vampireDiceRollResults: List<VampireDiceRollResult>,
        vampireDiceRollResultType: VampireDiceRollResultType,
    ): Boolean {
        return vampireDiceRollResults.parallelStream()
            .anyMatch { vampireDiceRollResult: VampireDiceRollResult ->
                vampireDiceRollResultType ==
                        vampireDiceRollResult.vampireDiceRollResultType
            }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun computeTotalSuccesses(
        vampireDiceRollResultCounts: Map<VampireDiceRollResultType, Int>,
    ): Int {
        val totalCrits =
            (vampireDiceRollResultCounts[VampireDiceRollResultType.SUCCESS_OR_MESSY_CRITICAL]!!
             + vampireDiceRollResultCounts[VampireDiceRollResultType.CRITICAL_SUCCESS]!!)
        return (vampireDiceRollResultCounts[VampireDiceRollResultType.SUCCESS]!!
                + Math.floorDiv(totalCrits, 2)
                + totalCrits % 2)
    }

    private fun determineSuccessType(
        vampireDiceRollResultCounts: Map<VampireDiceRollResultType, Int>,
    ): PoolRollResultType {
        val successOrMessyCriticalCount =
            vampireDiceRollResultCounts[VampireDiceRollResultType.SUCCESS_OR_MESSY_CRITICAL]!!
        val criticalSuccessCount =
            vampireDiceRollResultCounts[VampireDiceRollResultType.CRITICAL_SUCCESS]!!
        return if (successOrMessyCriticalCount >= 2
                   || successOrMessyCriticalCount >= 1 && criticalSuccessCount >= 1
        ) {
            PoolRollResultType.MESSY_CRITICAL
        } else if (criticalSuccessCount >= 2) {
            PoolRollResultType.CRITICAL_WIN
        } else {
            PoolRollResultType.SUCCESS
        }
    }

    private fun determineFailureType(
        vampireDiceRollResultCounts: Map<VampireDiceRollResultType, Int>,
    ): PoolRollResultType {
        return if (vampireDiceRollResultCounts[VampireDiceRollResultType.BESTIAL_FAILURE]!! >= 1) {
            PoolRollResultType.BESTIAL_FAILURE
        } else {
            PoolRollResultType.FAILURE
        }
    }

    override fun toString(): String {
        return """
            $poolRollResultType
            margin: $margin
        """.trimIndent()
    }
}