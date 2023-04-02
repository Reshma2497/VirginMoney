package com.example.virginmoney.ui.people.details


import android.graphics.Color
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.virginmoney.R
import com.example.virginmoney.data.model.people.PeopleItemModel
import com.example.virginmoney.databinding.FragmentPeopleDetailsBinding

class PeopleDetailsFragment : Fragment() {

    private var _binding: FragmentPeopleDetailsBinding? = null
    private val binding get() = _binding!!
    private var peopleItem: PeopleItemModel?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPeopleDetailsBinding.inflate(inflater, container, false)
        peopleItem = arguments?.getSerializable("PeopleItem") as PeopleItemModel
        val view=binding.root
        view?.let {
            Glide.with(it).load(peopleItem?.avatar).placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.ivImage)
        }
        binding.tvName.text= peopleItem?.firstName + peopleItem?.lastName
        binding.tvJobTitle.text="Job Title - " +peopleItem?.jobtitle
        binding.tvMailId.text="Mail ID - "+peopleItem?.email
        binding.tvFavColor.text="Favourite Color - "+peopleItem?.favouriteColor
        binding.llPeopleDetails.setBackgroundColor(getColorByName(colorName= peopleItem?.favouriteColor.toString())?:0)
        return view

    }

    private fun getColorByName( colorName: String): Int? {
        var color=colorName.replace(" ","_")
        return when (color) {
            "pink" -> context?.let { ContextCompat.getColor(it, R.color.pink) }
            "sky_blue" -> context?.let { ContextCompat.getColor(it, R.color.sky_blue) }
            "cyan" -> context?.let { ContextCompat.getColor(it, R.color.cyan) }
            "indigo" -> context?.let { ContextCompat.getColor(it, R.color.indigo) }
            "orchid" -> context?.let { ContextCompat.getColor(it, R.color.orchid) }
            "lavender" -> context?.let { ContextCompat.getColor(it, R.color.lavender) }
            "blue" -> context?.let { ContextCompat.getColor(it, R.color.blue) }
            "olive" -> context?.let { ContextCompat.getColor(it, R.color.olive) }
            "red" -> context?.let { ContextCompat.getColor(it, R.color.red) }
            "plum" -> context?.let { ContextCompat.getColor(it, R.color.plum) }
            "violet" -> context?.let { ContextCompat.getColor(it, R.color.violet) }
            "fuchsia" -> context?.let { ContextCompat.getColor(it, R.color.fuchsia) }
            "azure" -> context?.let { ContextCompat.getColor(it, R.color.azure) }
            "teal" -> context?.let { ContextCompat.getColor(it, R.color.teal) }
            "silver" -> context?.let { ContextCompat.getColor(it, R.color.silver) }
            "black" -> context?.let { ContextCompat.getColor(it, R.color.black) }
            "magenta" -> context?.let { ContextCompat.getColor(it, R.color.magenta) }
            "tan" -> context?.let { ContextCompat.getColor(it, R.color.tan) }
            "salmon" -> context?.let { ContextCompat.getColor(it, R.color.salmon) }
            "grey" -> context?.let { ContextCompat.getColor(it, R.color.grey) }
            "green" -> context?.let { ContextCompat.getColor(it, R.color.green) }
            "turquoise" -> context?.let { ContextCompat.getColor(it, R.color.turquoise) }
            "mint_green" -> context?.let { ContextCompat.getColor(it, R.color.mint_green) }
            "orange" -> context?.let { ContextCompat.getColor(it, R.color.orange) }
            "white" -> context?.let { ContextCompat.getColor(it, R.color.white) }
            "lime" -> context?.let { ContextCompat.getColor(it, R.color.lime) }
            "gold" -> context?.let { ContextCompat.getColor(it, R.color.gold) }
            "ivory" -> context?.let { ContextCompat.getColor(it, R.color.ivory) }
            else -> throw IllegalArgumentException("Unknown color name: $colorName")
        }
    }

}