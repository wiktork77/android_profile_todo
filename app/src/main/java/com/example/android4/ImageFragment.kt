package com.example.android4

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import java.io.Serializable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ImageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: MutableList<PhotoItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getSerializable(ARG_PARAM2) as MutableList<PhotoItem>?
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
        val currentItem = param2?.get(param1!!)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            img.setImageBitmap(requireContext().contentResolver.loadThumbnail(
                currentItem?.uri!!,
                Size(250, 250),
                null
            ))
        }
//        val sp = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
//        val spEdit = sp.edit()
//        spEdit.putInt("avatar", imageArr[param1!!])
//        spEdit.apply()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Serializable) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putSerializable(ARG_PARAM2, param2)
                }
            }
    }
}