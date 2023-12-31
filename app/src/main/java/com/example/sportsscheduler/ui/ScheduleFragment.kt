package com.example.sportsscheduler.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsscheduler.R
import com.example.sportsscheduler.databinding.FragmentScheduleBinding
import com.example.sportsscheduler.model.ScheduleData
import com.example.sportsscheduler.util.Status
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private val viewModel: ScheduleViewModel by viewModels()
    private lateinit var binding: FragmentScheduleBinding
    lateinit var scheduleAdapter: ScheduleAdapter
    val gameList: ArrayList<ScheduleData> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScheduleBinding.bind(view)
        setupUI()
        viewModel.getScheduleData()
        Log.e("MyApp", "Success on Fragment Call")
        setupObserver()
    }

    fun setupUI() {
        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        scheduleAdapter = ScheduleAdapter(arrayListOf())
        binding.recyclerview.adapter = scheduleAdapter
    }

    private fun setupObserver() {
        viewModel.sportsData.observe(viewLifecycleOwner) { resource ->
            binding.sports = resource.data
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { schedules ->
                        retrieveGameList(listOf(schedules))
                        }
                    }

                    Status.LOADING -> { //If I had time I would put a PROGRESS BAR here and
                                        //make Progress bar visible while loading data
                    }

                    Status.ERROR -> {
                        Toast.makeText(activity, resource.message, Toast.LENGTH_LONG).show()
                    }
                }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveGameList(games: List<ScheduleData>) {
        gameList.clear()
        gameList.addAll(games)
        scheduleAdapter.addUsers(games)
        scheduleAdapter.notifyDataSetChanged()
    }

}