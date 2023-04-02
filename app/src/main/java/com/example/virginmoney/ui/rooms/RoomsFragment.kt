package com.example.virginmoney.ui.rooms


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virginmoney.R
import com.example.virginmoney.data.model.people.PeopleModel
import com.example.virginmoney.data.model.rooms.RoomsModel
import com.example.virginmoney.databinding.FragmentRoomsBinding
import com.example.virginmoney.ui.errorhandling.ErrorHandling.doIfFailure
import com.example.virginmoney.ui.errorhandling.ErrorHandling.doIfSuccess
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomsFragment : Fragment() {

    private var _binding: FragmentRoomsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<RoomsViewModel>()

    private val items = listOf("AllRooms", "Occupied", "Unoccupied")




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        viewModel.rooms.observe(viewLifecycleOwner, Observer { result ->
            result.doIfSuccess {items ->
                setupUI(items)
            }

            result.doIfFailure {message, throwable ->
                showErrorPopup(message ?: "Unknown error message")
            }
        })
        viewModel.getRooms()
        //availablity dropdown
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.rooms_availability,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.snrAvailablity.adapter = adapter
        }


        return binding.root
    }

    fun setupUI(rooms: RoomsModel) {
        val roomsAdapter= RoomsAdapter(rooms)
        binding.rvRooms.apply {
            val gridlayoutManager = GridLayoutManager(context, 2)
            layoutManager =gridlayoutManager
            adapter = roomsAdapter
        }
        roomsAdapter.onItemClick = {
            val bundle=Bundle().apply{
                putSerializable("RoomsId",it.id)
            }

        }
        binding.snrAvailablity.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View?,
                                        position: Int,
                                        id: Long) {
                val selectedItem= parent.getItemAtPosition(position)
                if(selectedItem=="Available Rooms")
                {
                    val filteredList  = rooms.filter {
                        !(it.isOccupied)!!

                    }
                    val filteredRooms = RoomsModel()
                    for (p in filteredList)
                    {
                        filteredRooms.add(p)
                    }
                    roomsAdapter.updateData(filteredRooms)
                }
                else if(selectedItem=="Occupied Rooms"){
                    val filteredList= rooms.filter {
                        (it.isOccupied == true)
                    }
                    val filteredRooms = RoomsModel()
                    for (p in filteredList)
                    {
                        filteredRooms.add(p)
                    }
                    roomsAdapter.updateData(filteredRooms)

                }
                else
                {
                    roomsAdapter.updateData(rooms)
                }


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
    private fun showErrorPopup(message: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Error")
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}