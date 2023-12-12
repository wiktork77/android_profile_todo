package com.example.android4

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

private const val ARG_PARAM1 = "param1"
class ImageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private val imageArr: Array<Int> = Utilities.getAvailableImages()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val img: ImageView = view.findViewById(R.id.ivAvatar)
        img.setImageResource(imageArr[param1!!])
        println(param1!!)
//        val sp = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
//        val spEdit = sp.edit()
//        spEdit.putInt("avatar", imageArr[param1!!])
//        spEdit.apply()
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}