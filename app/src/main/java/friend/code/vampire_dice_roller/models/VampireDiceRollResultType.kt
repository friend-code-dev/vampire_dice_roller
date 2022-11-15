package friend.code.vampire_dice_roller.models

enum class VampireDiceRollResultType {
    CRITICAL_SUCCESS, SUCCESS, FAILURE, BESTIAL_FAILURE,

    /**
     * Schroedinger's [VampireDiceRollResultType].
     *
     * Value is determined when [Pool.roll] is called
     */
    SUCCESS_OR_MESSY_CRITICAL;

    companion object {
        /**
         * Translates the [Integer] faceValue of a 10-sided die roll into a [VampireDiceRollResultType]
         * @param vampireDiceType
         * @param faceValue
         * @return [VampireDiceRollResultType]
         */
        @Throws(IllegalStateException::class)
        fun from(vampireDiceType: VampireDiceType, faceValue: Int): VampireDiceRollResultType {
            return when (vampireDiceType) {
                VampireDiceType.REGULAR -> {
                    when (faceValue) {
                        1, 2, 3, 4, 5 -> FAILURE
                        6, 7, 8, 9    -> SUCCESS
                        10            -> CRITICAL_SUCCESS
                        else          -> throw IllegalStateException("Unexpected value: $faceValue")
                    }
                }
                VampireDiceType.HUNGER  -> {
                    when (faceValue) {
                        1          -> BESTIAL_FAILURE
                        2, 3, 4, 5 -> FAILURE
                        6, 7, 8, 9 -> SUCCESS
                        10         -> SUCCESS_OR_MESSY_CRITICAL
                        else       -> throw IllegalStateException("Unexpected value: $faceValue")
                    }
                }
            }
        }
    }
}