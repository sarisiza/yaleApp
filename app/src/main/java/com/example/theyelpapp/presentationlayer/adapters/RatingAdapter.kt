package com.example.theyelpapp.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.theyelpapp.R
import com.example.theyelpapp.databinding.RatingsHolderBinding
import com.example.theyelpapp.databinding.UserHolderBinding
import com.example.theyelpapp.datalayer.domain.Rating
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class RatingAdapter(
    private val itemSet: MutableList<ViewType> = mutableListOf()
): RecyclerView.Adapter<ViewHolder>() {

    fun updateRatings(newRatings: List<Rating>){
        itemSet.clear()
        newRatings.forEachIndexed { index, rating ->
            if(index%2 == 0){
                itemSet.add(ViewType.USER(rating))
                itemSet.add(ViewType.RATING(rating))
            } else{
                itemSet.add(ViewType.RATING(rating))
                itemSet.add(ViewType.USER(rating))
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        if(viewType == 0){ //user
            UserViewHolder(
                UserHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{ //rating
            RatingViewHolder(
                RatingsHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }


    override fun getItemCount() = itemSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(val item = itemSet[position]){
            is ViewType.RATING -> {
                (holder as RatingViewHolder).ratingsBind(item.rating)
            }
            is ViewType.USER -> {
                (holder as UserViewHolder).usersBind(item.rating)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when(itemSet[position]){
            is ViewType.RATING -> 1
            is ViewType.USER -> 0
        }


}

class UserViewHolder(
    private val binding: UserHolderBinding
): ViewHolder(binding.root){

    fun usersBind(item: Rating){
        binding.tvUserName.text = item.userName
        Picasso
            .get()
            .load(item.userImg)
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.baseline_person_24)
            .error(R.drawable.baseline_person_24)
            .into(binding.ivUserPic)
    }

}

class RatingViewHolder(
    private val binding: RatingsHolderBinding
): ViewHolder(binding.root){

    fun ratingsBind(item: Rating){
        binding.rbRating.rating = item.rating.toFloat()
        binding.tvTimeCreated.text = item.timeCreated
        binding.tvRatings.text = item.text
    }

}