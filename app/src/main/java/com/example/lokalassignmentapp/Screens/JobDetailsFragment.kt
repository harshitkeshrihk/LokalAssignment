package com.example.lokalassignmentapp.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lokalassignmentapp.MainActivity
import com.example.lokalassignmentapp.R
import com.example.lokalassignmentapp.ViewModel.MainViewModel
import com.example.lokalassignmentapp.adapter.JobTagAdapter
import com.example.lokalassignmentapp.databinding.FragmentJobBinding
import com.example.lokalassignmentapp.databinding.FragmentJobDetailsBinding
import com.example.lokalassignmentapp.model.Result
import com.google.android.material.bottomnavigation.BottomNavigationView


class JobDetailsFragment : Fragment() {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var adapter: JobTagAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJobDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpObservers()
        setUpRecyclerView()
    }

    private fun setUpView() {
        val mainActivity = activity as? MainActivity

        // Access a view from MainActivity
        mainActivity?.let {
            val bottomNavigationView = it.findViewById<View>(R.id.bottom_navigation)
           bottomNavigationView.visibility = View.GONE
        }
    }

    private fun setUpRecyclerView() {
        binding.jobTagsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter = JobTagAdapter()
        binding.jobTagsRecyclerview.adapter = adapter
    }

    private fun setUpObservers() {
        viewModel.clickedJob.observe(viewLifecycleOwner){
            setUpData(it)
        }
    }

    private fun setUpData(it: Result){
        binding.jobTitle.text = "Title - " + it.title
        binding.companyName.text = "Company Name - " +it.company_name
        binding.jobLocation.text = "Location - " + it.company_name + it.job_location_slug
        binding.salaryRange.text = "Salary - " +it.salary_min.toString() + " - " + it.salary_max.toString()
        binding.jobType.text ="Job Type - " + it.job_category.toString()
        binding.experience.text = "Experience - " + ((it.experience?.toInt() ?: 0)/12).toString()
        binding.content.text ="Job Role - " + it.job_role
        binding.contactPreference.text ="Contact Timings - " + it.contact_preference?.preferred_call_start_time +" - "+it.contact_preference?.preferred_call_end_time

        it.job_tags?.let { it1 -> adapter.setList(it1) }
    }
}