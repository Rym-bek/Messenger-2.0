package com.example.messenger.ui.handlers

import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.messenger.utils.constants.ConstantsPermissions
import com.example.messenger.utils.FileUtils
import com.example.messenger.utils.PermissionsHelper
import com.example.messenger.viewmodels.ViewModelProfilePhoto
import java.io.File
import javax.inject.Inject

class PhotoHandlers @Inject constructor(val fragment: Fragment, viewModelProfilePhoto: ViewModelProfilePhoto){
    private var permissionsHelper = PermissionsHelper(fragment, ConstantsPermissions.PERMISSIONS_STORAGE)

    fun onClickPhotoUpdate(view: View) {
        if(permissionsHelper.hasPermissions())
        {
            pickImage()
        }
    }

    private fun pickImage()
    {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val pickMedia =
        fragment.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val path = FileUtils(fragment.requireContext()).getPath(uri)
                viewModelProfilePhoto.uploadPhoto(File(path))
            }
        }
}