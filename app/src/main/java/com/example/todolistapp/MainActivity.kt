package com.example.todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.todolistapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViewPager()
    }

    private fun initViewPager() {
        val tabTextList = listOf("Todo", "Bookmark")

        //ViewPager2 Adapter 셋팅
        viewPagerAdapter.addFragment(TodoFragment())
        viewPagerAdapter.addFragment(BookmarkFragment())

        //Adapter 연결
        binding.mainViewPager.apply {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {})
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.mainNavigationView, binding.mainViewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }
}