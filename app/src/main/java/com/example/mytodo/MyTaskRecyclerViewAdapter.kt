import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.IMPORTANCE
import com.example.mytodo.R
import com.example.mytodo.Task
import com.example.mytodo.TaskListFragment
import com.example.mytodo.ToDoListListener
import com.example.mytodo.databinding.FragmentTaskItemBinding

// Adapter is responsible for managing the display of the list –binding data with the views
class MyTaskRecyclerViewAdapter(
    private val values: List<Task>,
    private val eventListener: ToDoListListener,
): RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder>()
{
    // onCreateViewHolder creates the ViewHolder objects
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyTaskRecyclerViewAdapter.ViewHolder {
// create the view holders for the recycler view items
// no data is bound to the views yet
        return ViewHolder(
            FragmentTaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // The ViewHolder class is a container for the views in the recycler view item
    class ViewHolder(binding: FragmentTaskItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val imgView: ImageView = binding.itemImg
        val contentView: TextView = binding.content
        val price: TextView = binding.price
        val checked: Switch = binding.checked
        val itemContainer: View = binding.root
        var partType: TextView = binding.PartTypeStr

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    override fun onBindViewHolder(holder: MyTaskRecyclerViewAdapter.ViewHolder, position: Int) {
        val task = values[position]

        val importanceImage = when(task.importance){
            IMPORTANCE.LOW -> R.drawable.circle_drawable_green
            IMPORTANCE.NORMAL -> R.drawable.circle_drawable_orange
            IMPORTANCE.HIGH -> R.drawable.circle_drawable_red
        }
        holder.imgView.setImageResource(importanceImage)
        holder.contentView.text = task.title
        holder.price.text = task.price
        holder.checked.isChecked = task.checked
        holder.partType.text = task.category
        holder.checked.setOnClickListener{
            task.checked = holder.checked.isChecked
            eventListener.setOnBudget()
        }
        holder.itemContainer.setOnClickListener{
            eventListener.onTaskClick(position)
        }
        holder.itemContainer.setOnLongClickListener {
            eventListener.onTaskLongClick(position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }


}