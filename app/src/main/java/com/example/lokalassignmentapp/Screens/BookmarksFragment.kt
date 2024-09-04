package com.example.lokalassignmentapp.Screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lokalassignmentapp.MainActivity
import com.example.lokalassignmentapp.R
import com.example.lokalassignmentapp.ViewModel.MainViewModel
import com.example.lokalassignmentapp.adapter.JobAdapter
import com.example.lokalassignmentapp.databinding.FragmentBookmarksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var adapter: JobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarksBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpObservers()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        binding.bookMarkRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = JobAdapter()
        binding.bookMarkRecyclerView.adapter = adapter
        adapter.onBookmarkClick = {
            viewModel.deleteJob(it)
            Toast.makeText(requireContext(),"UnBookmarked", Toast.LENGTH_SHORT).show()
        }
        adapter.onItemClick = {
            viewModel.clickJob(it)
            moveToDetailsFragment(JobDetailsFragment())
        }
        adapter.onPhoneNoClick = {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:$it")
            startActivity(dialIntent)
        }
    }

    private fun setUpObservers() {
        viewModel.mbookMarkedData.observe(viewLifecycleOwner){
            if (it != null) {
                adapter.setList(it)
            }
        }
    }

    private fun setUpView() {
        val mainActivity = activity as? MainActivity

        // Access a view from MainActivity
        mainActivity?.let {
            val bottomNavigationView = it.findViewById<View>(R.id.bottom_navigation)
            bottomNavigationView.visibility = View.VISIBLE
        }
    }

    private fun moveToDetailsFragment(fragment: JobDetailsFragment){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.rootLayout, fragment)
        fragmentTransaction.addToBackStack(null) // This adds the transaction to the back stack
        fragmentTransaction.commit()
    }

}