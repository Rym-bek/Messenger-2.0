package com.example.messenger.ui
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.messenger.databinding.HomeContainerBinding
import com.example.messenger.repositories.WebSocketRepository
import com.example.messenger.viewmodels.WebSocketViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomePage : AppCompatActivity() {
    private lateinit var binding: HomeContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
