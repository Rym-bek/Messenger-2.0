package com.example.messenger.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.messenger.R
import com.example.messenger.adapters.MessageAdapter
import com.example.messenger.databinding.UserMessagePageBinding
import com.example.messenger.models.CreateRoom
import com.example.messenger.models.Message
import com.example.messenger.utils.BaseResponse
import com.example.messenger.utils.UserManager
import com.example.messenger.viewmodels.*
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentUserMessagePage : Fragment(){
    private var _binding: UserMessagePageBinding? = null

    private val binding get() = _binding!!

    private lateinit var messageAdapter: MessageAdapter
    private val messages: ArrayList<Message> = ArrayList()
    private lateinit var viewManager: LinearLayoutManager

    private val webSocketViewModel:WebSocketViewModel by activityViewModels()
    private val viewModelCreateRoom: ViewModelCreateRoom by viewModels()
    //private val viewModelCreateRoom = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ViewModelCreateRoom::class.java]
    private val viewModelGetPrivateMessages: ViewModelGetPrivateMessages by activityViewModels()
    private val getUserByUidViewModel: GetUserByUidViewModel by activityViewModels()

    private var myUid: String? =null
    private var anotherUid: String? =null
    private var roomUid: String? =null
    private var firstname: String? =null
    private var photo_url: String? =null
    private var online: Boolean? =null
    private val gson: Gson = Gson()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserMessagePageBinding.inflate(inflater, container, false)
        val view = binding.root

        webSocketViewModel.clear()
        viewModelGetPrivateMessages.clear()

        getIntentData()

        setUserData()

        if(roomUid!=null)
        {
            viewModelGetPrivateMessages.loadMessages(roomUid!!)
        }

        setAdapter()

        listeners()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        observers()

        observeOnlineStatus()
    }

    private fun observeOnlineStatus()
    {
        webSocketViewModel.onlineStatus.observe(viewLifecycleOwner) {
            if(anotherUid.equals(it!!.uid))
            {
                if(it.online)
                {
                    binding.secondText.text=getString(R.string.online)
                }
                else
                {
                    binding.secondText.text=getString(R.string.offline)
                }
            }
        }
    }

    private fun observers() {
        viewModelCreateRoom.roomUid.observe(viewLifecycleOwner)
        {
            when (it) {
                is BaseResponse.Loading -> {

                }
                is BaseResponse.Success -> {
                    roomUid= it.data?.roomUid!!
                    sendMessage()
                }
                is BaseResponse.Error -> {

                }
            }
        }

        webSocketViewModel.message.observe(viewLifecycleOwner) {
            if (it != null && it.roomUid==roomUid) {
                messages.add(it)
                addToRecyclerView()
            }
        }

        viewModelGetPrivateMessages.messages.observe(viewLifecycleOwner)
        {
            when (it) {
                is BaseResponse.Loading -> {

                }
                is BaseResponse.Success -> {
                    messages.addAll(it.data!!)
                    if(messages.size!=0)
                    {
                        messageAdapter.notifyItemRangeInserted(0,messages.size);
                        binding.recyclerView.smoothScrollToPosition(messages.size - 1)
                    }
                }
                is BaseResponse.Error -> {

                }
                else -> {}
            }
        }
    }

    private fun addToRecyclerView()
    {
        messageAdapter.notifyItemInserted(messages.size - 1)
        binding.recyclerView.smoothScrollToPosition(messages.size - 1)
    }

    private fun setAdapter()
    {
        messageAdapter = MessageAdapter(requireContext(), messages, myUid!!)
        viewManager = LinearLayoutManager(requireContext())
        viewManager.stackFromEnd = true
        binding.recyclerView.apply {
            adapter = messageAdapter
            layoutManager = viewManager
        }
    }

    private fun setUserData() {
        if(photo_url!=null)
        {
            Glide.with(this)
                .load(photo_url)
                .signature(ObjectKey(photo_url.hashCode()))
                .into(binding.image)

            binding.mainText.text=firstname

            if(!online!!)
            {
                binding.secondText.text=getString(R.string.offline)
            }
        }
    }

    private fun initToolbar()
    {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        binding.toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }

    private fun listeners()
    {
        binding.textInputLayout.setEndIconOnClickListener {
            if(roomUid==null)
            {
                getUserByUidViewModel.getUserByUid(anotherUid!!)
                viewModelCreateRoom.createRoom(CreateRoom(listOf(myUid, anotherUid)))
            }
            else
            {
                sendMessage()
            }
        }
    }

    private fun sendMessage()
    {
        val message = binding.textInputEditText.text.toString().trim()
        if (!TextUtils.isEmpty(message)) {
            val jsonData = gson.toJson(Message(senderUid=myUid?: "", roomUid = roomUid?: "",message = message))
            webSocketViewModel.sendMessage(jsonData)
            /*messages.add(IncomingMessage(myUid!!,message,System.currentTimeMillis()))
            addToRecyclerView()*/
            binding.textInputEditText.text?.clear()
        }
    }

    private fun getIntentData()
    {
        val bundle = this.arguments
        myUid = UserManager.user?.uid
        anotherUid= bundle?.getString("anotherUid")
        roomUid=bundle?.getString("roomUid")
        firstname=bundle?.getString("firstname")
        photo_url=bundle?.getString("photo_url")
        online=bundle?.getBoolean("online")

        Log.d("suka_blya_2", roomUid.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}