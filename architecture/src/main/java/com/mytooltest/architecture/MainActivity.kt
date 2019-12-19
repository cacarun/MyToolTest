package com.mytooltest.architecture

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.mytooltest.architecture.mvc.MVCFragment
import com.mytooltest.architecture.mvp.MVPFragment
import com.mytooltest.architecture.mvvm.MVVMFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        changeFragment(NormalFragment.newInstance())
        normalText.setOnClickListener {
            changeFragment(NormalFragment.newInstance())
        }
        mvcText.setOnClickListener {
            changeFragment(MVCFragment.newInstance())
        }
        mvpText.setOnClickListener {
            changeFragment(MVPFragment.newInstance())
        }
        mvvmText.setOnClickListener {
            changeFragment(MVVMFragment.newInstance())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragment).commit()
    }
}
