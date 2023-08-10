package com.learn.newproject.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.learn.newproject.R
import com.learn.newproject.model.entity.New
import com.learn.newproject.model.local.Dao.NewDao
import com.learn.newproject.databinding.ViewHolderNewBinding
import com.learn.newproject.presenter.LocalNewPresenter
import com.learn.newproject.presenter.VolleyNewPresenter


class NewsAdapter  (val listOfNew: List<New>,
                    val volleyPresenter: VolleyNewPresenter,
                    val localNewPresenter: LocalNewPresenter
) :
    RecyclerView.Adapter<NewsAdapter.NewViewHolder>() {
    private lateinit var binding: ViewHolderNewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ViewHolderNewBinding.inflate(layoutInflater, parent, false)
        val viewHolder = NewViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        holder.bind(listOfNew[position])
    }

    override fun getItemCount(): Int {
        return listOfNew.size
    }



    private lateinit var newSelected :(New, Int) ->Unit
    fun setOnNewSelectedListener(listener: (New, Int) -> Unit) {
        newSelected = listener
    }
    inner class NewViewHolder (val binding: ViewHolderNewBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    newSelected(listOfNew[position], position)
                }
            }
        }
        fun bind(new: New){
            binding.apply {
                tvTitle.text=new.title
                tvDate.text=new.published.split(" ")[0]
                tvDescription.text=new.description
                val imgLoader=volleyPresenter.fetchImg(new.image,
                    imgThumbnail,
                    R.drawable.baseline_image_24,
                    R.drawable.baseline_error_24)
                imgThumbnail.setImageUrl(new.image,imgLoader)

                btnViewHolder.setOnClickListener{
                    localNewPresenter.addNews(new)
                }

            }
        }


    }
}