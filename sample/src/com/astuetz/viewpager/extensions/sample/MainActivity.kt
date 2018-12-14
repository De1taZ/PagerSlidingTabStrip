/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.astuetz.viewpager.extensions.sample

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import com.astuetz.PagerSlidingTabStrip
import com.readystatesoftware.systembartint.SystemBarTintManager

class MainActivity : AppCompatActivity() {

    internal var toolbar: Toolbar? = null


    lateinit var tabs: PagerSlidingTabStrip
    lateinit var pager: ViewPager

    lateinit var adapter: MyPagerAdapter
    var oldBackground: Drawable? = null
    private var currentColor: Int = 0
    lateinit var mTintManager: SystemBarTintManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabs = findViewById(R.id.tabs)
        pager = findViewById(R.id.pager)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        // create our manager instance after the content view is set
        mTintManager = SystemBarTintManager(this)
        // enable status bar tint
        mTintManager.isStatusBarTintEnabled = true
        adapter = MyPagerAdapter(supportFragmentManager)
        pager.adapter = adapter
        tabs.setViewPager(pager)
        val pageMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, resources
                .displayMetrics).toInt()
        pager.pageMargin = pageMargin
        pager.currentItem = 1
        changeColor(ContextCompat.getColor(baseContext, R.color.green))

        tabs.setOnTabReselectedListener { position -> Toast.makeText(this@MainActivity, "Tab reselected: $position", Toast.LENGTH_SHORT).show() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_contact -> {
                QuickContactFragment.newInstance().show(supportFragmentManager, "QuickContactFragment")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeColor(newColor: Int) {
        tabs.setBackgroundColor(newColor)
        mTintManager.setTintColor(newColor)
        // change ActionBar color just if an ActionBar is available
        val colorDrawable = ColorDrawable(newColor)
        val bottomDrawable = ColorDrawable(ContextCompat.getColor(baseContext, android.R.color.transparent))
        val ld = LayerDrawable(arrayOf<Drawable>(colorDrawable, bottomDrawable))
        if (oldBackground == null) {
            supportActionBar?.setBackgroundDrawable(ld)
        } else {
            val td = TransitionDrawable(arrayOf(oldBackground, ld))
            supportActionBar?.setBackgroundDrawable(td)
            td.startTransition(200)
        }

        oldBackground = ld
        currentColor = newColor
    }

    fun onColorClicked(v: View) {
        val color = Color.parseColor(v.tag.toString())
        changeColor(color)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentColor", currentColor)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentColor = savedInstanceState.getInt("currentColor")
        changeColor(currentColor)
    }

    inner class MyPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val TITLES = arrayOf("Categories", "Home", "Top Paid", "Top Free", "Top Grossing", "Top New Paid", "Top New Free", "Trending")

        override fun getPageTitle(position: Int): CharSequence? {
            return TITLES[position]
        }

        override fun getCount(): Int {
            return TITLES.size
        }

        override fun getItem(position: Int): Fragment {
            return SuperAwesomeCardFragment.newInstance(position)
        }
    }
}