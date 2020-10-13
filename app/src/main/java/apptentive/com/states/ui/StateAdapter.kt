package apptentive.com.states.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apptentive.com.states.R
import apptentive.com.states.models.StateInfo

class StateAdapter(private val context: Context,
                   private val states: List<StateInfo>,
                   private val listener: (StateInfo) -> Unit): RecyclerView.Adapter<StateAdapter.StateHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateHolder {
        return StateHolder(LayoutInflater.from(context).inflate(R.layout.state_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return states.size
    }

    override fun onBindViewHolder(holder: StateHolder, position: Int) {
        holder.bind(states[position], listener)
    }

    class StateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textState = itemView.findViewById<TextView>(R.id.text_state_name)

        fun bind(stateInfo: StateInfo, listener: (StateInfo) -> Unit) {
            textState.text = stateInfo.name
            itemView.setOnClickListener {
                listener(stateInfo)
            }
        }
    }
}
