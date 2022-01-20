package com.danielblandford.workplace.ui.people

import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.danielblandford.workplace.R
import com.danielblandford.workplace.databinding.ItemPeopleBinding
import com.danielblandford.workplace.ui.people.data.Person
import com.danielblandford.workplace.utils.UrlBitmap
import java.util.*


class PeopleAdapter :
    ListAdapter<Person, PeopleFragment.PeopleViewHolder>(object : DiffUtil.ItemCallback<Person>() {

        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleFragment.PeopleViewHolder {
        val binding = ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return PeopleFragment.PeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleFragment.PeopleViewHolder, person: Int) {

        //Download avatar and change its shape from square to circle
        UrlBitmap.urlToBitmapCircle(getItem(person).avatar, holder.avatarIV)

        holder.nameTV.text = holder.nameTV.context.resources.getString(R.string.full_name).format(getItem(person).firstName, getItem(person).lastName)
        holder.jobTitleTV.text = getItem(person).jobTitle
        holder.emailTV.text = holder.emailTV.context.resources.getString(R.string.email).format(getItem(person).email)
        holder.phoneTV.text = holder.phoneTV.context.resources.getString(R.string.phone).format(getItem(person).phone)

        //Convert location latitude and longitude to get country name.
        try {
            val geocoder = Geocoder(holder.avatarIV.context, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(getItem(person).latitude, getItem(person).longitude, 1)
            val location: String = addresses[0].countryName
            holder.locationTV.text = holder.locationTV.context.resources.getString(R.string.location).format(location)
        }catch (e:Exception) {
            holder.locationTV.text = holder.locationTV.context.resources.getString(R.string.location).format(holder.locationTV.context.resources.getString(
                R.string.unknown))
        }

        //If running on a small width display (i.e. a phone in portrait) support expand to detailed view
        if(holder.cardView.isClickable)
        {
            holder.cardView.setOnClickListener {
                val detailVisibility = holder.detailView.visibility

                if(detailVisibility == View.VISIBLE){
                    holder.detailView.visibility = View.GONE
                    holder.expandDetailsImageButton.setImageResource(R.drawable.ic_expand_more)
                }
                else {
                    holder.detailView.visibility = View.VISIBLE
                    holder.expandDetailsImageButton.setImageResource(R.drawable.ic_expand_less)
                }
            }
        }
    }
}