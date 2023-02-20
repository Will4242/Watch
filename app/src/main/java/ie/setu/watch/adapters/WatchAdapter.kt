package ie.setu.watch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.watch.databinding.CardWatchBinding
import ie.setu.watch.models.WatchModel

interface WatchListener {
    fun onWatchClick(watch: WatchModel)
}

class WatchAdapter constructor(private var watchs: List<WatchModel>,
                                   private val listener: WatchListener) :
    RecyclerView.Adapter<WatchAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWatchBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val watch = watchs[holder.adapterPosition]
        holder.bind(watch, listener)
    }

    override fun getItemCount(): Int = watchs.size

    class MainHolder(private val binding : CardWatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(watch: WatchModel, listener: WatchListener) {
            binding.watchTitle.text = watch.title
            binding.watchDescription.text = watch.description
            binding.watchPrice.text = watch.price.toString()
            binding.watchGender.text = watch.gender
            binding.watchAvailable.text = watch.available.toString()
            binding.root.setOnClickListener { listener.onWatchClick(watch) }
        }
    }
}
