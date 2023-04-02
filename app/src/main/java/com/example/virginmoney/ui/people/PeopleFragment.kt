package com.example.virginmoney.ui.people

import android.os.Bundle
import android.provider.Contacts
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virginmoney.R
import com.example.virginmoney.data.model.errorhandling.ResultOf
import com.example.virginmoney.data.model.people.PeopleModel
import com.example.virginmoney.databinding.FragmentPeopleBinding
import com.example.virginmoney.ui.errorhandling.ErrorHandling.doIfFailure
import com.example.virginmoney.ui.errorhandling.ErrorHandling.doIfSuccess
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    private var _binding: FragmentPeopleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<PeopleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPeopleBinding.inflate(inflater, container, false)

        viewModel.people.observe(viewLifecycleOwner, Observer { result ->
            result.doIfSuccess {items ->
                setupUI(items)
            }

            result.doIfFailure {message, throwable ->
                showErrorPopup(message ?: "Unknown error message")
            }
        })


        viewModel.getPeople()
        return binding.root
    }

    fun setupUI(people: PeopleModel) {
        val peopleAdapter= PeopleAdapter(people)
        binding.rvPeople.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = peopleAdapter
        }
        peopleAdapter.onItemClick = {
            val bundle=Bundle().apply{
                putSerializable("PeopleItem",it)
            }
            findNavController().navigate(
                R.id.action_navigation_people_to_peopleDetailsFragment,bundle
            )
        }
        //define setonquerytextlinstener is method will be called whenever i type something in text bar
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(p0: String?): Boolean {
               //TODO("Not yet implemented")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                // to filter the data start with lastname given in a search bar
                val filteredList  = people.filter {
                    it.lastName?.startsWith(newText, ignoreCase = true)?:false
                }
                //changing from list of people item model to arraylist of people item model
                val filteredPeople = PeopleModel()
                for (p in filteredList)
                {
                    filteredPeople.add(p)
                }

                // filteredPeople.setPeople
                peopleAdapter.updateData(filteredPeople)
                return true
            }
        })
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