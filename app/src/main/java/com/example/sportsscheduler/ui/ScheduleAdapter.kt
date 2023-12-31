package com.example.sportsscheduler.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportsscheduler.databinding.ScheduleItemBinding
import com.example.sportsscheduler.model.ScheduleData
import com.example.sportsscheduler.util.Constant

class ScheduleAdapter (private val gameList: ArrayList<ScheduleData>) :
    RecyclerView.Adapter<ScheduleAdapter.GameViewHolder>() {

    class GameViewHolder(private val binding: ScheduleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: ScheduleData) {
            binding.sports = schedule
            val triCode = schedule.GameSection[0].Game[0].Opponent.TriCode.toLowerCase()
            val imageUrlAway =
                Constant.IMAGE_BASE_URL + triCode + Constant.IMAGE_END
            Glide.with(binding.root)
                .load(imageUrlAway)
                .into(binding.ivIconAway)

            val triCodeHome = schedule.Team.TriCode.toLowerCase()
            val imageUrlHome = Constant.IMAGE_BASE_URL + triCodeHome + Constant.IMAGE_END
            Glide.with(binding.root)
                .load(imageUrlHome)
                .into(binding.ivIconHome)

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding =
            ScheduleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(gameList[position])
    }

    fun addUsers(games: List<ScheduleData>) {
        this.gameList.apply {
            clear()
            addAll(games)
        }
    }

}