package com.learn.newproject.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.newproject.R
import com.learn.newproject.model.entity.New
import com.learn.newproject.model.local.Dao.NewDao
import com.learn.newproject.model.local.DatabaseHelper
import com.learn.newproject.databinding.FragmentHomeBinding
import com.learn.newproject.databinding.SearchDialogBinding
import com.learn.newproject.model.remote.VolleyHandler
import com.learn.newproject.presenter.LocalNewPresenter
import com.learn.newproject.presenter.MVPNew
import com.learn.newproject.presenter.VolleyNewPresenter
import com.learn.newproject.view.adapter.NewsAdapter

class HomeFragment : Fragment(),MVPNew.NewView {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var listOfNews:List<New>

    private lateinit var localNewPresenter: LocalNewPresenter
    private lateinit var volleyPresenter: VolleyNewPresenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment\
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun setResult(listOfNewsResponse: List<New>) {
        listOfNews=listOfNewsResponse
        setRecyclerView(listOfNews)
    }

    override fun setError(error: String) {
        Log.i("tag","${error}")
    }

    override fun makeToast(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_search -> {
                createSearchDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun setup() {

        initPresenter()
        volleyPresenter.getNews()
        binding

    }
    private fun createSearchDialog() {
        val searchFormBinding = SearchDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireContext()).apply {
            setView(searchFormBinding.root)
            setCancelable(false)
        }
        val dialog =builder.create()
        dialog.setCancelable(true)
        dialog.window?.setGravity(Gravity.CENTER)
        searchFormBinding.btnSearch.setOnClickListener {
            val key= searchFormBinding.etKey.text.toString()
            volleyPresenter.getNewsByString(key)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun initPresenter() {
        volleyPresenter= VolleyNewPresenter(
            VolleyHandler(requireContext()),this)
        localNewPresenter= LocalNewPresenter(
            DatabaseHelper(requireContext()),this)
    }

    private fun setRecyclerView(listOfData:List<New>){
        binding.rvNew.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        newsAdapter= NewsAdapter(listOfData,volleyPresenter,localNewPresenter)
        binding.rvNew.adapter=newsAdapter

        newsAdapter.setOnNewSelectedListener { data, i ->
            openNewWebsite(data.url)
        }

    }
    private fun openNewWebsite(url:String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
    companion object{
        const val TAG="HomeFragment"
    }



}