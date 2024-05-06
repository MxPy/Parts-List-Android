package com.example.mytodo

import Tasks.list
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodo.databinding.FragmentAddTaskBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTaskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    val args: AddTaskFragmentArgs by navArgs()
    private lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Initialize the binding variable
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
// Set the title and description EditText fields with the task to edit
// (only if it's not null)
        binding.titleInput.setText(args.taskToEdit?.title)
        binding.descriptionInput.setText(args.taskToEdit?.description)
// Set the importance radio button with the task to edit (only if it's not null)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener { saveTask() }
    }

    fun updateTask(oldTask: Task?, newTask: Task) {
//perform the update operation only if the old task is not null
        oldTask?.let { old ->
//find the index of the old task in the list
// indexOf will be -1 if the old task is not in the list
            val indexOfOld = list.indexOf(old)
            if (indexOfOld != -1) { // check if the old task is in the list
//replace the old task with the new task
                list[indexOfOld] = newTask
            }
        }
    }
    private fun saveTask() {
// Get the values from data fields on the screen
        var title: String = binding.titleInput.text.toString()
        var price = binding.editPrice.text.toString()
        var description: String = binding.descriptionInput.text.toString()
        var checked = false;
        var category: String = binding.PartType.selectedItem.toString()

// Handle missing EditText input
        if(title.isEmpty())
            title = getString(R.string.default_task_title) + "${Tasks.list.size + 1}"
        if(description.isEmpty())
            description = getString(R.string.no_desc_msg)
        if(price.isEmpty())
            price = "0";
// Create a new Task item based on input values
        val taskItem = Task(
            {title + description}.hashCode().toString(),
            title,
            description,
            price,
            checked,
            category
        )

        if(!args.edit){
            Tasks.addTask(taskItem)
        }
        else {
            Tasks.updateTask(oldTask = args.taskToEdit, newTask = taskItem)
        }
// Hide the software keyboard with InputMethodManager
        val inputMethodManager: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken,0)

        // Navigate back to the TaskListFragment, the inclusive parameter is set to false
        findNavController().popBackStack(R.id.taskListFragment, false)
    }


}