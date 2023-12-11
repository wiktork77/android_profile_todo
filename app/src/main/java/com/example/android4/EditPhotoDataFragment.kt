package com.example.android4

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditPhotoDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditPhotoDataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_photo_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val avatars: TableLayout = view.findViewById(R.id.tlAvatars)
        val resources = mapOf<Int, Int>(
            0 to R.drawable.image_ape,
            1 to R.drawable.image_castle,
            2 to R.drawable.image_flower,
            3 to R.drawable.image_orang,
            4 to R.drawable.image_rails,
            5 to R.drawable.image_rock,
            6 to R.drawable.image_sun,
            7 to R.drawable.image_wood,
            8 to R.drawable.image_cat
        )
        for (i in 0..<avatars.childCount) {
            val child = avatars.getChildAt(i) as TableRow
            for (j in 0..<child.childCount) {
                val element = child.getChildAt(j) as ImageView
                element.setOnClickListener { _ ->
                    val sp = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
                    val spEdit = sp.edit()
                    val res: Int = resources[(i*3) + j]!!
                    spEdit.putInt("avatar", res)
                    spEdit.apply()
                    val navView = requireActivity().findViewById<NavigationView>(R.id.nvMainNavView)
                    Utilities.setupNavHeader(navView, sp)
                    Navigation.findNavController(view).navigate(R.id.editToMain)
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditPhotoDataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditPhotoDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}