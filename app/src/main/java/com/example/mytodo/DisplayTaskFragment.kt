import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.IMPORTANCE
import com.example.mytodo.R
import com.example.mytodo.databinding.FragmentDisplayTaskBinding

class DisplayTaskFragment : Fragment() {
    val args: DisplayTaskFragmentArgs by navArgs()
    private lateinit var binding: FragmentDisplayTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        binding = FragmentDisplayTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// get the task from the arguments and display the task details
        val task = args.task
        binding.displayTitle.text = task.title
        binding.displayDescription.text = task.description
// select the drawable resource for the image view based on the importance of the task
        val importanceDrawable = when(task.importance){
            IMPORTANCE.LOW -> R.drawable.circle_drawable_green
            IMPORTANCE.NORMAL -> R.drawable.circle_drawable_orange
            IMPORTANCE.HIGH -> R.drawable.circle_drawable_red
        }
        binding.displayImportance.setImageResource(importanceDrawable)

        binding.displayEdit.setOnClickListener {
// create an action to navigate to the AddTaskFragment with the displayed task
            val actionEditTask =
                DisplayTaskFragmentDirections.actionDisplayTaskFragmentToAddTaskFragment()
// set the task to edit and the edit flag to true in the action
            with(actionEditTask) {
                taskToEdit = task
                edit = true
            }
// use the navigate method to perform the navigation action created above
            findNavController().navigate(actionEditTask)
        }
    }


}