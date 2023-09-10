package com.example.testapp.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.databinding.FragmentMenuSettingsBinding
import com.example.testapp.domain.CardType
import com.example.testapp.presentation.activities.MainActivity
import com.example.testapp.presentation.adapters.EnabledCardsAdapter
import com.example.testapp.presentation.viewmodels.MenuSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MenuSettingsFragment : Fragment() {
    private val viewModel: MenuSettingsViewModel by viewModels()
    private var _binding: FragmentMenuSettingsBinding? = null
    private val binding get() = _binding!!
    private val menu
        get() = (activity as MainActivity).toolbar.menu

    override fun onStart() {
        super.onStart()
        setMenuItems(true)
    }

    private fun setMenuItems(visible: Boolean) {
        val textButton = menu.findItem(R.id.text_button)
        textButton.isVisible = visible
        if (visible) {
            textButton.title = "Готово"
            textButton.setOnMenuItemClickListener {
                viewModel.save()
                findNavController().navigateUp()
                true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var itemTouchHelper : ItemTouchHelper
        val adapter  = EnabledCardsAdapter()
        adapter.onRemove = {
            viewModel.removeCard(it.id)
        }
        adapter.onStartDrag = {
            itemTouchHelper.startDrag(it)
        }


        binding.enabledCardsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(), DividerItemDecoration.VERTICAL
            )
        )
        binding.enabledCardsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { uiState ->

                    adapter.differ.submitList(uiState.list)
                    binding.enabledCardsRecyclerView.adapter = adapter
                }
            }
        }

        binding.addFab.setOnClickListener {
            val types = CardType.values()
            var checkedItem = 0
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setTitle("Какую карточку добавить?")
            dialogBuilder.setSingleChoiceItems(
                types.map { it.text }.toTypedArray(), checkedItem
            ) { _, which -> checkedItem = which }
            dialogBuilder.setPositiveButton(
                "Выбрать"
            ) { _, which ->
                viewModel.createCard(types[checkedItem])
            }
            dialogBuilder.create().show()
        }

        itemTouchHelper =
            ItemTouchHelper(object: ItemTouchHelper.SimpleCallback (
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                0
            ) {
                override fun isLongPressDragEnabled(): Boolean {
                    return false
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    adapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                }

                override fun onMoved(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    fromPos: Int,
                    target: RecyclerView.ViewHolder,
                    toPos: Int,
                    x: Int,
                    y: Int
                ) {
                    super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
                    viewModel.move(viewHolder.adapterPosition, target.adapterPosition)
                }
            })
        itemTouchHelper.attachToRecyclerView(binding.enabledCardsRecyclerView)
    }

    override fun onStop() {
        super.onStop()
        setMenuItems(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}