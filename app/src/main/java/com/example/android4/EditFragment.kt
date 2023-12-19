package com.example.android4

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.doOnPreDraw
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var rv: RecyclerView
    private var lastFile: File? = null

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
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv = view.findViewById(R.id.rvPhotos)

        val header: TextView = view.findViewById(R.id.tvPhotosListHeader)
        val mode = PhotosRepository.getInstance().getMode(requireContext())
        val btnAdd: Button = view.findViewById(R.id.btnAddPhoto)

        val photoTakeLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                Toast.makeText(requireContext(), "Photo TAKEN!!!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Photo fail!", Toast.LENGTH_LONG).show()
                lastFile?.delete()
            }
        }

        header.text = "${mode} photos".uppercase()
        setupRv()
        btnAdd.setOnClickListener { _ ->
            try {
                val fileUri = getNewFileUri()
                if (fileUri != null) {
                    println(fileUri)
                    photoTakeLauncher.launch(fileUri)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshRv()
    }

    private fun setupRv() {
        val repo = PhotosRepository.getInstance()
        val mode = repo.getMode(requireContext())
        val list = repo.getDataList(requireContext())
        println(list.size)
        rv.adapter = PhotoRVAdapter(list, requireContext())
        val spanCount = 2
        val manager =
            GridLayoutManager(requireContext(), spanCount, GridLayoutManager.VERTICAL, false)
        rv.layoutManager = manager
        rv.addItemDecoration(GridSpacingItemDecoration(spanCount, 50, false))
    }

    private fun refreshRv() {
        val repo = PhotosRepository.getInstance()
        val list = repo.getDataList(requireContext())
        val currentAdapter = rv.adapter as PhotoRVAdapter
        currentAdapter.changeData(list)
        currentAdapter.notifyDataSetChanged()
    }

    private fun getNewFileUri(): Uri? {
        try {
            val tStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val dir = when (PhotosRepository.getInstance().getMode(requireContext())) {
                "app" -> requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                "shared" -> Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } as File
            val tmpFile = File.createTempFile("Photo_" + "${tStamp}", ".jpg", dir)
            lastFile = tmpFile
            return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}