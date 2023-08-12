package com.example.messenger.ui.dialogs

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.messenger.R
import com.example.messenger.ui.HomePage
import com.example.messenger.viewmodels.TokenViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

class DialogEditUserProfileExit : DialogFragment() {
    private val tokenViewModel: TokenViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(R.string.alarm)
        builder.setMessage(R.string.you_sure_to_exit)
        builder.setPositiveButton(R.string.yes) { dialogInterface, i ->
            tokenViewModel.deleteToken()
            startActivity(Intent(context, HomePage::class.java))
        }
        builder.setNegativeButton(R.string.no) {
                dialogInterface, i -> dialogInterface.dismiss()
        }
        return builder.create()
    }
}

