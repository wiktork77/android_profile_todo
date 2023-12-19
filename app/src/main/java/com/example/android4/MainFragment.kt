package com.example.android4

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val avatar: ImageView = view.findViewById(R.id.ivMainAvatar)
        val rg: RadioGroup = view.findViewById(R.id.rgMainStorage)
        val sp = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        if (sp.contains("avatarUri")) {
            val uri = sp.getString("avatarUri", " ")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                avatar.setImageBitmap(requireContext().contentResolver.loadThumbnail(
                    Uri.parse(uri),
                    Size(500, 500),
                    null
                ))
            }
        }
        if (!sp.contains("mode")) {
            val spEdit = sp.edit()
            spEdit.putString("mode", "app")
            spEdit.apply()
        }
        when (sp.getString("mode", " ")) {
            "app" -> rg.findViewById<RadioButton>(R.id.rbOptApp).isChecked = true
            "shared" -> rg.findViewById<RadioButton>(R.id.rbOptShared).isChecked = true
        }

        rg.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                val spEdit = sp.edit()
                when (p1) {
                    R.id.rbOptApp ->  spEdit.putString("mode", "app")
                    R.id.rbOptShared -> spEdit.putString("mode", "shared")
                }
                spEdit.apply()
            }

        })
        println(sp.getString("mode", " "))


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}