package com.giac.restauranttrends.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.giac.restauranttrends.R

abstract class AbstractBaseFragment : Fragment() {

    lateinit var progressbar : View
        private set
    lateinit var errorMessage : TextView
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.abstract_base_fragment, container, false)
        progressbar = view.findViewById(R.id.progressbar)
        errorMessage = view.findViewById(R.id.errorMessage)
        val content = view.findViewById<ViewGroup>(R.id.container)
        content.addView(createContentFragmentLayout(inflater, container, savedInstanceState))
        return view
    }

    abstract fun createContentFragmentLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View?

}