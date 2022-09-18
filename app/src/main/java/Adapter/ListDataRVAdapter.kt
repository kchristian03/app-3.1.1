package Adapter

import Interface.cardListener
import Model.Film
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vintech.movielistapplication.R
import com.vintech.movielistapplication.databinding.CardDataBinding

class ListDataRVAdapter(val ListFilm: ArrayList<Film>, val cardListener: cardListener) :
    RecyclerView.Adapter<ListDataRVAdapter.viewHolder>() {

    class viewHolder(val itemView: View, val cardListener1: cardListener) :
        RecyclerView.ViewHolder(itemView) {
        val binding = CardDataBinding.bind(itemView)

        fun setData(data: Film) {
            binding.textViewNama.text = data.title
            binding.textViewJenis.text = data.rating
            binding.textViewUsia.text = data.genre.toString()
            if (data.imageUri.isNotEmpty()) {
                binding.imageView.setImageURI(Uri.parse(data.imageUri))
            }

            itemView.setOnClickListener {
                cardListener1.onCardClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_data, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(ListFilm[position])
    }

    override fun getItemCount(): Int {
        return ListFilm.size
    }
}