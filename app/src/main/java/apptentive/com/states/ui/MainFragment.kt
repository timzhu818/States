package apptentive.com.states.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apptentive.com.states.R
import apptentive.com.states.activities.MainActivity
import apptentive.com.states.models.StateInfo
import apptentive.com.states.repositories.StatesInfoRepository
import apptentive.com.states.util.LiveDataWrapper
import apptentive.com.states.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.inject

class MainFragment : BaseFragment() {
    override fun getLayoutId() = R.layout.fragment_main
    private val TAG = MainFragment::class.java.simpleName

    private val viewModel by lazy {
        val repo: StatesInfoRepository by inject()
        MainViewModel(Dispatchers.Main, Dispatchers.IO, repo)
    }

    private val statesList: MutableList<StateInfo> = mutableListOf()
    private val stateAdapter by lazy {
        StateAdapter(requireContext(), statesList) { state ->
            showDetail(state)
        }
    }

    private fun showDetail(state: StateInfo) {
        AlertDialog.Builder(context).setTitle(state.name).setMessage(state.toString())
            .setPositiveButton(R.string.ok_text, null).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.response.observe(viewLifecycleOwner, Observer { res ->
            handleResponse(res)
        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = stateAdapter
        }

        viewModel.requestStatesInfo()
    }

    private fun handleResponse(res: LiveDataWrapper<List<StateInfo>>?) {
        when (res?.responseStatus) {
            LiveDataWrapper.RESPONSESTATUS.LOADING -> {
                showHideLoading(true)
            }
            LiveDataWrapper.RESPONSESTATUS.ERROR -> {
                showHideLoading(false)
                showHideError(true, res.error)
            }

            LiveDataWrapper.RESPONSESTATUS.SUCCESS -> {
                showHideLoading(false)
                showHideError(false)
                displayResult(res)
            }
        }
    }

    private fun showHideLoading(show: Boolean) {
        (activity as? MainActivity)?.showHideProgressBar(show)
    }

    private fun showHideError(show: Boolean, throwable: Throwable? = null) {
        error_holder.visibility = if (show) View.VISIBLE else View.GONE
        throwable?.localizedMessage?.let { errorMessage -> Log.e(TAG, errorMessage) }
    }

    private fun displayResult(res: LiveDataWrapper<List<StateInfo>>) {
        res.response?.let {
            statesList.apply {
                clear()
                addAll(it)
            }
            stateAdapter.notifyDataSetChanged()
        }
    }
}
