package com.astuetz.viewpager.extensions.sample

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView

import com.astuetz.PagerSlidingTabStrip

import com.astuetz.PagerSlidingTabStrip.CustomTabProvider

class QuickContactFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (dialog != null) {
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        }

        val root = inflater.inflate(R.layout.fragment_quick_contact, container, false)
        val tabs = root.findViewById<PagerSlidingTabStrip>(R.id.tabs)
        val pager = root.findViewById<ViewPager>(R.id.pager)
        val adapter = ContactPagerAdapter(activity!!.applicationContext)
        pager.adapter = adapter
        tabs.setViewPager(pager)
        return root
    }

    override fun onStart() {
        super.onStart()

        // change dialog width
        if (dialog != null) {
            val fullWidth: Int
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                val display = activity!!.windowManager.defaultDisplay
                val size = Point()
                display.getSize(size)
                fullWidth = size.x
            } else {
                val display = activity!!.windowManager.defaultDisplay
                fullWidth = display.width
            }

            val padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, resources
                    .displayMetrics).toInt()
            val w = fullWidth - padding
            val h = dialog!!.window!!.attributes.height
            dialog!!.window!!.setLayout(w, h)
        }
    }

    class ContactPagerAdapter(private val mContext: Context) : PagerAdapter(), CustomTabProvider {

        private val ICONS = intArrayOf(R.drawable.ic_launcher_gplus, R.drawable.ic_launcher_gmail, R.drawable.ic_launcher_gmaps, R.drawable.ic_launcher_chrome)
        private val TITLES = arrayOf("GPlus", "GMail", "GMaps", "GChrome")

        override fun getCount(): Int {
            return ICONS.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return TITLES[position]
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val textview = LayoutInflater.from(mContext).inflate(R.layout.fragment_quickcontact, container, false) as TextView
            textview.text = "PAGE $position"
            container.addView(textview)
            return textview
        }

        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View)
        }

        override fun isViewFromObject(v: View, o: Any): Boolean {
            return v === o
        }

        override fun getCustomTabView(parent: ViewGroup, position: Int): View {
            val tab = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, parent, false)
            (tab.findViewById<View>(R.id.image) as ImageView).setImageResource(ICONS[position])
            return tab
        }

        override fun tabSelected(tab: View) {
            //Callback with the tab on his selected state. It is up to the developer to change anything on it.
        }

        override fun tabUnselected(tab: View) {
            //Callback with the tab on his unselected state. It is up to the developer to change anything on it.
        }
    }

    companion object {

        fun newInstance(): QuickContactFragment {
            return QuickContactFragment()
        }
    }
}
