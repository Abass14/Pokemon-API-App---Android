package com.example.pokemonreactivetask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonreactivetask.R
import com.example.pokemonreactivetask.adapter.RecyclerViewAdapter
import com.example.pokemonreactivetask.connectivity.ConnectivityLiveData
import com.example.pokemonreactivetask.model.PokemonData
import com.example.pokemonreactivetask.vm.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_single_pokemon_detail.*

class MainActivity : AppCompatActivity() {
    private lateinit var connectivityLiveData: ConnectivityLiveData
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectivityLiveData = ConnectivityLiveData(application)

        //Setting mutable live data value of limit
        val theLimit : MutableLiveData<String> = MutableLiveData("100")
        var viewModel: MainActivityViewModel = makeAPICall(theLimit.value!!, 0)

        //observing changes to the mutable live data value of limit on every setBtn click
        theLimit.observe(this, {
            setBtn.setOnClickListener {
                val limit = findViewById<EditText>(R.id.limit)
                theLimit.value = limit.text.toString()
                viewModel = makeAPICall(theLimit.value!!, 0)
            }
        })

        viewModel.init(this)
        setUpBinding(viewModel)
    }

    //Function binding RecyclerView to ViewModel
    fun setUpBinding(viewModel: MainActivityViewModel) {
        recyclerView.apply {
            adapter = viewModel.recyclerViewAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    fun makeAPICall(limit: String, offset:Int) : MainActivityViewModel {
        val viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.recyclerListLiveData.observe(this, Observer {
            progressBar_main.visibility = View.GONE
            errorTxt_main.visibility = View.GONE
            if (it != null){
                viewModel.setAdapterData(it.results)
            }else{
                progressBar_main.visibility = View.VISIBLE
                errorTxt_main.visibility = View.VISIBLE
            }
        })

        connectivityLiveData.observe(this, Observer { isAvailable ->
            //2
            when (isAvailable) {
                true -> {
                    //3
                    viewModel.makeAPICall(limit,offset)
                    errorTxt_main.visibility = View.GONE
                }
                false -> {
                    errorTxt_main.visibility = View.VISIBLE
                }
            }
        })
        return viewModel
    }
}