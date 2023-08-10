package com.learn.newproject.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.learn.newproject.R
import com.learn.newproject.databinding.ActivityMainBinding
import com.learn.newproject.view.fragment.AccountFragment
import com.learn.newproject.view.fragment.HomeFragment
import com.learn.newproject.view.fragment.SavedNewsFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.parent_fragment_container)
        return if (currentFragment is HomeFragment) {
            currentFragment.onOptionsItemSelected(item)
        } else {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0){
            checkAndBackStack()
            updateBottomAppBarIcon()
        }else
            super.onBackPressed()

    }
    private fun setup() {
        setupBottomAppBar()

    }
    private fun setupBottomAppBar() {
        
        binding.bottomAppBar.setOnItemSelectedListener { menuItems->
            when(menuItems.itemId){
                R.id.home ->handleMenuEvent(HOME,HomeFragment())
                R.id.saved ->handleMenuEvent(SAVED_NEW,SavedNewsFragment())
                R.id.accounts->handleMenuEvent(ACCOUNT,AccountFragment())
            }
            true
        }
    }
    private fun checkAndBackStack(){
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.parent_fragment_container)
        if(currentFragment !=null && (currentFragment is SavedNewsFragment || currentFragment is AccountFragment ))
            supportFragmentManager.popBackStackImmediate(HOME,0)
        else
            supportFragmentManager.popBackStackImmediate()


    }

    private fun handleMenuEvent(backStackEntryName:String,fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.parent_fragment_container,fragment)
            .addToBackStack(backStackEntryName).commit()
    }
    private fun updateBottomAppBarIcon() {
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.parent_fragment_container)
        Log.i(TAG,currentFragment.toString())
        val menuItemId = when (currentFragment) {
            is HomeFragment -> R.id.home
            is SavedNewsFragment -> R.id.saved

            else -> 0
        }
        binding.bottomAppBar.selectedItemId = menuItemId
    }

    companion object{
        const val TAG="MainActivity"
        const val HOME="HOME"
        const val SAVED_NEW="SAVED_NEW"
        const val ACCOUNT="ACCOUNT"
    }
}