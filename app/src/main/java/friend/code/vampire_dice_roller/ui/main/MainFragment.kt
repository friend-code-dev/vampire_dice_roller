package friend.code.vampire_dice_roller.ui.main

import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import friend.code.vampire_dice_roller.databinding.MainFragmentBinding
import friend.code.vampire_dice_roller.models.PoolRollResult

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _gestureDetector: GestureDetector? = null
    private val detector get() = _gestureDetector!!
    private var _mainFragmentBinding: MainFragmentBinding? = null
    private val mainFragmentBinding get() = _mainFragmentBinding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _gestureDetector = GestureDetector(this.context, SimpleOnGestureListener())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _mainFragmentBinding = MainFragmentBinding.inflate(inflater, container, false)

        mainFragmentBinding.rollButton.setOnClickListener {
            roll()
        }

        return mainFragmentBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun roll() {
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