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

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SuperAwesomeCardFragment : Fragment() {

    lateinit var textView: TextView

    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = arguments!!.getInt(ARG_POSITION)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_card, container, false)
        textView = rootView.findViewById(R.id.textView)
        ViewCompat.setElevation(rootView, 50f)
        textView.text = "CARD $position"
        return rootView
    }

    companion object {

        private val ARG_POSITION = "position"

        fun newInstance(position: Int): SuperAwesomeCardFragment {
            val f = SuperAwesomeCardFragment()
            val b = Bundle()
            b.putInt(ARG_POSITION, position)
            f.arguments = b
            return f
        }
    }
}