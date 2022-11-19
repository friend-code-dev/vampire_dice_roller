package friend.code.vampire_dice_roller.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import friend.code.vampire_dice_roller.databinding.MainFragmentBinding
import friend.code.vampire_dice_roller.models.PoolRollResult

class MainFragment() : Fragment() {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private val TAG: String = this.javaClass.simpleName

    private lateinit var viewModel: MainViewModel
    private var _mainFragmentBinding: MainFragmentBinding? = null
    private val mainFragmentBinding get() = _mainFragmentBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _mainFragmentBinding = MainFragmentBinding.inflate(inflater, container, false)
        mainFragmentBinding.rollButton.setOnClickListener { roll() }
        return mainFragmentBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun roll() {
        viewModel.updatePool(mainFragmentBinding.regularDiceCountEditText.text.toString()
                                 .toIntOrNull(),
                             mainFragmentBinding.hungerDiceCountEditText.text.toString()
                                 .toIntOrNull())
        toast(viewModel.pool.roll(mainFragmentBinding.difficultyLevelEditText.text.toString()
                                      .toIntOrNull()))
    }

    private fun toast(roll: PoolRollResult) {
        Toast.makeText(requireContext(), roll.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mainFragmentBinding = null
    }
}