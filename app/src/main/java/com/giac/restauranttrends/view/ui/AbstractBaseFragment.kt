package com.giac.restauranttrends.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.giac.restauranttrends.R

abstract class AbstractBaseFragment : Fragment() {

    private lateinit var toolbar : Toolbar
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
        toolbar = view.findViewById(R.id.toolbar)
        val content = view.findViewById<ViewGroup>(R.id.container)
        content.addView(createContentFragmentLayout(inflater, container, savedInstanceState))
        return view
    }

    abstract fun createContentFragmentLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View?

    fun setTitle(title : String) {
        toolbar.title = title
    }

    fun setTitle(titleResId : Int) {
        toolbar.title = requireContext().getString(titleResId)
    }

    fun hideToolbar() {
        toolbar.visibility = View.GONE
    }
}