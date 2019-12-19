package com.mytooltest.architecture.mvvm

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mytooltest.architecture.R
import com.mytooltest.architecture.databinding.ArchitectureBindingBinding


class MVVMFragment : Fragment() {
    companion object {
        fun newInstance(): Fragment {
            return MVVMFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var binding: ArchitectureBindingBinding = DataBindingUtil.inflate(inflater, R.layout.architecture_binding, container, false)
        binding.lifecycleOwner = this
        val viewModel = ViewModel()
        viewModel.setModel(HandleModel())
        binding.viewmodel = viewModel
        return binding.root
    }
}