package com.dazzi.goldenticket.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dazzi.goldenticket.R
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        ibtn_search_pre.onClick {
            finish()
        }

        ibtn_search_submit.onClick {
            startActivity<SearchResultActivity>("search_text" to et_search_searchbar.text.toString())
        }

        tv_search_tag1.onClick {
            startActivity<SearchActivity>("search_tag" to tv_search_tag1.text)
        }
        tv_search_tag2.onClick {
            startActivity<SearchActivity>("search_tag" to tv_search_tag2.text)
        }
        tv_search_tag3.onClick {
            startActivity<SearchActivity>("search_tag" to tv_search_tag3.text)
        }
        tv_search_tag4.onClick {
            startActivity<SearchActivity>("search_tag" to tv_search_tag4.text)
        }
        tv_search_tag5.onClick {
            startActivity<SearchActivity>("search_tag" to tv_search_tag5.text)
        }
        tv_search_tag6.onClick {
            startActivity<SearchActivity>("search_tag" to tv_search_tag6.text)
        }
        tv_search_tag7.onClick {
            startActivity<SearchActivity>("search_tag" to tv_search_tag7.text)
        }
        tv_search_tag8.onClick {
            startActivity<SearchActivity>("search_tag" to tv_search_tag8.text)
        }
        tv_search_tag9.onClick {
            startActivity<SearchActivity>("search_tag" to tv_search_tag9.text)
        }
        tv_search_tag0.onClick {
            startActivity<SearchActivity>("search_tag" to tv_search_tag0.text)
        }
    }
}