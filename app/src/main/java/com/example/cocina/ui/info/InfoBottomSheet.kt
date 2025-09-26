package com.example.cocina.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.cocina.databinding.BottomsheetInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoBottomSheet : BottomSheetDialogFragment() {

    private var _b: BottomsheetInfoBinding? = null
    private val b get() = _b!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = BottomsheetInfoBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title = requireArguments().getString("title").orEmpty()
        val message = requireArguments().getString("message").orEmpty()
        val imageName = requireArguments().getString("imageName")
        b.tvTitle.text = title
        b.tvMessage.text = message

        val resId = imageName?.let {
            resources.getIdentifier(it, "drawable", requireContext().packageName)
        } ?: 0
        if (resId != 0) b.ivCover.load(resId)

    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }

    companion object {
        fun newInstance(title: String, message: String, imageName: String?) =
            InfoBottomSheet().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("message", message)
                    putString("imageName", imageName)

                }
            }
    }
}
