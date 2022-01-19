package com.danielblandford.workplace.ui.people

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Path
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielblandford.workplace.R
import com.danielblandford.workplace.databinding.FragmentPeopleBinding
import com.danielblandford.workplace.databinding.ItemPeopleBinding
import com.danielblandford.workplace.ui.people.data.Person
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URL
import kotlin.math.min
import android.location.Geocoder
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*


class PeopleFragment : Fragment() {

    private lateinit var peopleViewModel: PeopleViewModel
    private var _binding: FragmentPeopleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        peopleViewModel = ViewModelProvider(this).get(PeopleViewModel::class.java)
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerviewPeople
        val adapter = PeopleAdapter()
        recyclerView.adapter = adapter

        //People observer
        peopleViewModel.peopleResponseList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            for (person in it) {
                Log.d("Person avatar", person.avatar)
                Log.d("Person createdAt", person.createdAt)
                Log.d("Person email", ""+person.email)
                Log.d("Person favouriteColor", ""+person.favouriteColor)
                Log.d("Person firstName", person.firstName)
                Log.d("Person id", person.id)
                Log.d("Person jobTitle", person.jobTitle)
                Log.d("Person lastName", person.lastName)
                Log.d("Person latitude", person.latitude.toString())
                Log.d("Person longitude", person.longitude.toString())
                Log.d("Person phone", person.phone)
            }
        })
        peopleViewModel.getPeople()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class PeopleAdapter :
        ListAdapter<Person, PeopleViewHolder>(object : DiffUtil.ItemCallback<Person>() {

            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean =
                oldItem == newItem
        }) {


        // extension function to crop specified size circular area from bitmap
        fun Bitmap.cropCircularArea(
            diameter:Int = min(width,height)
        ):Bitmap?{
            val bitmap = Bitmap.createBitmap(
                width, // width in pixels
                height, // height in pixels
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bitmap)

            // create a circular path
            val path = Path()
            val length = min(diameter,min(width,height))
            val radius = length / 2F // in pixels
            path.apply {
                addCircle(
                    width/2f,
                    height/2f,
                    radius,
                    Path.Direction.CCW
                )
            }

            // draw circular bitmap on canvas
            canvas.clipPath(path)
            canvas.drawBitmap(this,0f,0f,null)

            val x = (width - length)/2
            val y = (height - length)/2

            // return cropped circular bitmap
            return Bitmap.createBitmap(
                bitmap, // source bitmap
                x, // x coordinate of the first pixel in source
                y, // y coordinate of the first pixel in source
                length, // width
                length // height
            )
        }


        // extension function to get bitmap from assets
        fun Context.assetsToBitmap(fileName: String): Bitmap?{
            return try {
                with(assets.open(fileName)){
                    BitmapFactory.decodeStream(this)
                }
            } catch (e: IOException) { null }
        }

        private fun URL.toBitmap(): Bitmap?{
            return try {
                BitmapFactory.decodeStream(openStream())
            }catch (e: IOException){
                null
            }
        }

        private fun updateAvatar(url: String, imageView: ImageView) {

            // url of image
            val urlImage = URL(url)

            // async task to get bitmap from url
            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main) {
                // show bitmap on image view when available
                val bitmap: Bitmap? = result.await()
                // crop specified size circular area from bitmap
                bitmap?.cropCircularArea(1000)?.apply {
                    imageView.setImageBitmap(this)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
            val binding = ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent,false)
            return PeopleViewHolder(binding)
        }

        override fun onBindViewHolder(holder: PeopleViewHolder, person: Int) {

            //Download avatar and change its shape from square to circle
            updateAvatar(getItem(person).avatar, holder.avatarIV)

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
                holder.locationTV.text = holder.locationTV.context.resources.getString(R.string.location).format(holder.locationTV.context.resources.getString(R.string.unknown))
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

    class PeopleViewHolder(binding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val cardView: CardView = binding.itemPeopleLayoutParent
        val detailView: ConstraintLayout = binding.itemPeopleLayoutDetail
        val expandDetailsImageButton: ImageView = binding.itemPeopleExpandDetail
        val avatarIV: ImageView = binding.imageViewItemPeopleAvatar
        val nameTV: TextView = binding.textViewItemPeopleName
        val jobTitleTV: TextView = binding.textViewItemPeopleJobTitle
        val emailTV: TextView = binding.textViewItemPeopleEmail
        val phoneTV: TextView = binding.textViewItemPeoplePhone
        val locationTV: TextView = binding.textViewItemPeopleLocation
    }
}