package com.jorzet.casmal.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.jorzet.casmal.R
import com.jorzet.casmal.adapters.GraphAdapter
import com.jorzet.casmal.base.BaseFragment
import com.jorzet.casmal.utils.Utils
import com.jorzet.casmal.viewmodels.ScoreViewModel


/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 12/08/19.
 */

class ScoresFragment: BaseFragment() {
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: GraphAdapter

    private lateinit var viewModel: ScoreViewModel

    override fun getLayoutId(): Int {
        return R.layout.scores_fragment
    }

    override fun initView() {
        refreshLayout = rootView.findViewById(R.id.refreshLayout)
        recyclerView = rootView.findViewById(R.id.recyclerView)
    }

    override fun prepareComponents() {
        viewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)

        adapter = GraphAdapter()

        viewModel.getScores().observe(this, Observer {
            adapter.setList(it)
        })

        viewModel.isUpdating().observe(this, Observer {
            refreshLayout.isRefreshing = it
        })

        viewModel.getException().observe(this, Observer {
            if(it != null) {
                Utils.print("Exception: $it")
            }
        })

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        if(user != null) {
            viewModel.load(user.uid)
        }
    }
}