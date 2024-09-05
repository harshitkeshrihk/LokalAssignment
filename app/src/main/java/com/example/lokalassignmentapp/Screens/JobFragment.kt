package com.example.lokalassignmentapp.Screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignmentapp.MainActivity
import com.example.lokalassignmentapp.R
import com.example.lokalassignmentapp.Utils.NetworkResult
import com.example.lokalassignmentapp.ViewModel.MainViewModel
import com.example.lokalassignmentapp.adapter.JobAdapter
import com.example.lokalassignmentapp.databinding.FragmentJobBinding
import com.example.lokalassignmentapp.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobFragment : Fragment() {

    private var _binding: FragmentJobBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var adapter: JobAdapter

    private var isLoading = false

    private lateinit var lists : MutableList<Result>


    val TAG = "JOBFRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lists = viewModel?.mdata?.value?.data ?: mutableListOf()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJobBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpObservers()
        setUpRecyclerView()
    }



    private fun setUpRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        adapter = JobAdapter()
        binding.recyclerView.adapter = adapter
        updateRecyclerView()
        adapter.onBookmarkClick = {
            viewModel.insertJob(it)
            Toast.makeText(requireContext(),"Bookmarked",Toast.LENGTH_SHORT).show()
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

        //pagination
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !viewModel.isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        viewModel.loadNextPage()
                    }
                }
            }
        })
    }

    private fun updateRecyclerView() {
        adapter.setList(lists)
    }

    private fun setUpObservers() {
        viewModel.mdata.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Success -> {
                    it.data?.let { it1 -> lists.addAll(it1.toList()) }
                    updateRecyclerView()
                }
                is NetworkResult.Error ->{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Error..",Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading = it
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