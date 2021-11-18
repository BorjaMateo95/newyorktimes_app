package com.borja.pruebanewyorktimes.ui.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.borja.pruebanewyorktimes.R
import com.borja.pruebanewyorktimes.databinding.OptionsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionsFragment : Fragment() {

    private lateinit var binding: OptionsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.options_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        binding.rbOption1.setOnClickListener {
            binding.liner.visibility = View.GONE
        }

        binding.rbOption2.setOnClickListener {
            binding.liner.visibility = View.VISIBLE
        }

        binding.rbOption3.setOnClickListener {
            binding.liner.visibility = View.GONE
        }

        binding.button.setOnClickListener {
            try {
                goToNewsList()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun goToNewsList() {
        var type: String = ""
        var period: Int = 1
        var mostPopular: String? = null

        when (binding.radioGroup1.checkedRadioButtonId) {
            R.id.rb_option1 -> {
                type = "mostemailed"
            }
            R.id.rb_option2 -> {
                type = "mostshared"
                if (binding.cbFacebook.isChecked) {
                    mostPopular = "facebook"
                }
                if (binding.cbTwitter.isChecked) {
                    if (mostPopular?.isNotEmpty() == true) {
                        mostPopular += ";"
                    } else {
                        mostPopular = ""
                    }

                    mostPopular +="twitter"
                }

                if (mostPopular == null) {
                    throw Exception("MostPopular requerido en esta seleccion")
                }

                mostPopular += "/"

            }
            R.id.rb_option3 -> {
                type = "mostviewed"
            }
        }

        when (binding.radioGroup2.checkedRadioButtonId) {
            R.id.rb_1 -> {
                period = 1
            }
            R.id.rb_7 -> {
                period = 7
            }
            R.id.rb_30 -> {
                period = 30
            }
        }

       if (findNavController().currentDestination?.id == R.id.optionsFragment) {
           val action = OptionsFragmentDirections.actionOptionsFragmentToNewsListFragment(type, period, mostPopular)
           findNavController().navigate(action)
       }

    }

    override fun onResume() {
        super.onResume()
        binding.rbOption1.isChecked = true
        binding.rb1.isChecked = true
    }

}
