package apptentive.com.states.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import apptentive.com.states.R
import apptentive.com.states.ui.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: 1. load list of states from https://raw.githubusercontent.com/apptentive/states/master/states.json
        // TODO: 2. display a simple list of state names in a RecyclerView. Should look like this:
        //          - Alabama
        //          - Arizona
        //            ...
        //          - Wyoming
        // TODO: 3. show details for a selected state (when user clicks a row in the state list)
        // TODO: 4. add unit/UI tests

        openFragment(MainFragment())
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).
            addToBackStack(fragment.javaClass.simpleName).commit()
    }

    fun showHideProgressBar(show: Boolean) {
        progress_circular.visibility = if (show) View.VISIBLE else View.GONE
    }
}
