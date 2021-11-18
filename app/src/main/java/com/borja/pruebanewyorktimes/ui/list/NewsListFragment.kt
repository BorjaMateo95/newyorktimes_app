package com.borja.pruebanewyorktimes.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.fragment.navArgs
import com.borja.pruebanewyorktimes.R
import com.borja.pruebanewyorktimes.databinding.NewsListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private val viewModel : NewsListViewModel by viewModels()
    private lateinit var binding: NewsListFragmentBinding

    private val args: NewsListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.news_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        viewModel.getData(args.type, args.period, args.mostPopularBy)

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.uiModel.collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is NewsListViewModel.UiModel.Loading -> {
                            Log.d("PRUEBA", "LOADING")
                        }

                        is NewsListViewModel.UiModel.Failure -> {
                            Log.d("PRUEBA", "FAILURE")

                        }

                        is NewsListViewModel.UiModel.Success -> {
                            Log.d("PRUEBA", "SUCCESS")

                        }

                    }
                }
            }

        }

    }

}