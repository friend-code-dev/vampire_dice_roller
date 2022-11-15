package friend.code.vampire_dice_roller.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.stream.Collectors

/** A [List] of [VampireDice]  */
class Pool(private val pool: List<VampireDice>) {
    /**
     * roll each [VampireDice] in the [Pool].
     *
     * @param difficulty the number of successes required achieve [PoolRollResultType.SUCCESS]
     * @return [PoolRollResult]
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun roll(difficulty: Int?): PoolRollResult {
        return PoolRollResult(rollPool(), difficulty ?: 0)
    }

    /**
     * parallely executes [VampireDice.roll] for all [VampireDice] in the [Pool]
     *
     * @return the result of each roll; a [List] of [VampireDiceRollResult]
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun rollPool(): List<VampireDiceRollResult> {
        return pool.parallelStream().map { obj: VampireDice -> obj.roll() }
            .collect(Collectors.toList())
    }
}