package com.learn.newproject.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.newproject.model.entity.New
import com.learn.newproject.model.local.Dao.NewDao
import com.learn.newproject.model.local.DatabaseHelper
import com.learn.newproject.databinding.FragmentSavedNewsBinding
import com.learn.newproject.model.remote.VolleyHandler
import com.learn.newproject.presenter.LocalNewPresenter
import com.learn.newproject.presenter.MVPNew
import com.learn.newproject.presenter.VolleyNewPresenter
import com.learn.newproject.view.adapter.SavedNewsAdapter

class SavedNewsFragment : Fragment() , MVPNew.NewView{
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var savedNewsAdapter: SavedNewsAdapter
    private lateinit var listOfNews:List<New>
    private  lateinit var volleyPresenter: VolleyNewPresenter
    private  lateinit var localPresenter: LocalNewPresenter
    private lateinit var newDao: NewDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSavedNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun setResult(listOfNewsResponse: List<New>) {
        listOfNews=listOfNewsResponse
        setRecyclerView(listOfNews)
    }

    override fun setError(error: String) {
        makeToast(error)
    }

    override fun makeToast(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }


    private fun setup() {
        initPresenter()
        localPresenter.getNews()

    }

    private fun setRecyclerView(listOfNews:List<New>){
        binding.rvNew.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        savedNewsAdapter= SavedNewsAdapter(listOfNews, volleyPresenter,localPresenter )
        binding.rvNew.adapter=savedNewsAdapter

        savedNewsAdapter.setOnNewSelectedListener { data, i ->
            openNewWebsite(data.url)
        }

    }

    private fun initPresenter() {
        volleyPresenter= VolleyNewPresenter(
            VolleyHandler(requireContext()),this)
        localPresenter=LocalNewPresenter(
            DatabaseHelper(requireContext()),this)
    }
    private fun openNewWebsite(url:String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
    companion object {

    }


}