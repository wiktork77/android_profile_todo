package com.example.android4

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2

private const val ARG_PARAM1 = "photos"
class PhotoSliderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var photoPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photoPosition = it.getInt("photoPos")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_slider, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vp2: ViewPager2 = view.findViewById(R.id.vpPhotosSlider)
        val btn: Button = view.findViewById(R.id.setPhotoBtn)
        val btn2: Button = view.findViewById(R.id.backBtnSlider)
        val currentList = PhotosRepository.getInstance().getDataList(requireContext())
        vp2.adapter = MyPagerAdapter2(this, currentList)
        vp2.post {
            vp2.currentItem = photoPosition!!
        }
        println(photoPosition)
        btn.setOnClickListener { _ ->
            val sp = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
            val spEdit = sp.edit()
            spEdit.putString("avatarUri", currentList.get(vp2.currentItem).uri.toString())
            spEdit.apply()
            println(sp.getString("avatarUri", " "))
            Navigation.findNavController(view).navigate(R.id.sliderToMain)
        }
        btn2.setOnClickListener { _ ->
            Navigation.findNavController(view).navigate(R.id.sliderToEdit)
        }
    }

    companion object {
        @JvmStatic fun newInstance(param1: String, param2: String) =
                PhotoSliderFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}