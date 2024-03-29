package ie.setu.watch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.watch.databinding.CardWatchBinding
import ie.setu.watch.models.WatchModel

interface WatchListener {
    fun onWatchClick(watch: WatchModel, position : Int)
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
            //If watch sold is true then only show title and that its sold and not allow to click in to update
            if(watch.sold) {
                binding.watchTitle.text = watch.title
                //change back
                Picasso.get().load(watch.image).resize(200,200).into(binding.imageIcon)
                binding.watchDescription.text = watch.description
                binding.watchPrice.text = "\u20AC"+watch.price.toString()
                binding.watchGender.text = watch.gender
                binding.watchSold.isVisible = true
            }
            //If watch is not sold then hide the field and allow updates
            else{
                binding.watchTitle.text = watch.title
                //change back WILL
                Picasso.get().load(watch.image).resize(200,200).into(binding.imageIcon)
                binding.watchDescription.text = watch.description
                binding.watchPrice.text = "\u20AC"+watch.price.toString()
                binding.watchGender.text = watch.gender
                binding.watchSold.isVisible = false
            }
            binding.root.setOnClickListener { listener.onWatchClick(watch, adapterPosition)
            }
        }
    }
}
