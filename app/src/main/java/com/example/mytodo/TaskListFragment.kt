package com.example.mytodo

import MyTaskRecyclerViewAdapter
import Tasks
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodo.databinding.FragmentTaskListBinding
import com.google.android.material.snackbar.Snackbar


class TaskListFragment : Fragment(), ToDoListListener {
    // connect the fragment_task_list.xml with TaskListFragment class
    private lateinit var binding: FragmentTaskListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        binding = FragmentTaskListBinding.inflate(inflater, container, false)

// Set the adapter and layout manager for the RecyclerView
// "with" is a Kotlin extension function that allows you to call
// the methods of an object without explicitly calling the object itself
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyTaskRecyclerViewAdapter(
                Tasks.shownList,
                this@TaskListFragment
            ) // adapter is responsible for displaying the data

        }

        Log.i("chuj2", binding.list.adapter.toString())


        binding.BudgedCalcId.addTextChangedListener(tw)


        return binding.root
    }

    var tw: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            Log.i("number", s.toString())
            var num1 = binding.BudgedCalcId.text.toString().toFloat()
            var num2 = 0.0f
            if (binding.editBudget.text.toString().isNotEmpty()){
                var num2 = binding.editBudget.text.toString().toFloat()
            }

            if (num1 < num2){
                binding.BudgedCalcId.setTextColor(Color.RED);
            }else{
                binding.BudgedCalcId.setTextColor(Color.GREEN);
            }
        }
        override fun afterTextChanged(s: Editable) {}
    }

    override fun onResume() {
        super.onResume()
        setOnBudget()
        Tasks.updateShownList()
        Log.i("cuj", "refresg")

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// Set the action for the FAB
        binding.addButton.setOnClickListener {
// Navigate to the AddTaskFragment with action id
            findNavController().navigate(R.id.action_taskListFragment_to_addTaskFragment)

        }
        binding.editBudget.setOnClickListener {
            setOnBudget()
        }

        binding.filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("DetachAndAttachSameFragment")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected item from the spinner
                val selectedItem = parent?.getItemAtPosition(position).toString()
                // Update the filter
                Tasks.updateFilter(selectedItem)
                with(binding.list) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = MyTaskRecyclerViewAdapter(
                        Tasks.shownList,
                        this@TaskListFragment
                    ) // adapter is responsible for displaying the data
                }
                setOnBudget()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing if nothing is selected
            }
        }




    }
    override fun setOnBudget(){
        var num = 0.0
        for (task in Tasks.shownList){
            if(!task.checked){
                num -= task.price.toFloat()
            }
            Log.i("chuj", task.checked.toString())
        }
        if (binding.editBudget.text.toString().isEmpty()){
            binding.BudgedCalcId.setText((0.0f+num).toString())
        }else{
            binding.BudgedCalcId.setText((binding.editBudget.text.toString().toFloat()+num).toString())
        }
        Log.i("chuj3", binding.BudgedCalcId.text.toString())
    }



    override fun onTaskClick(taskPosition: Int) {
// create an action to navigate to the DisplayTaskFragment
//with the selected task at taskPosition

        val actionTaskListFragmentToDisplayTaskFragment =
            TaskListFragmentDirections.actionTaskListFragmentToDisplayTaskFragment(
                Tasks.list[taskPosition])
// use the navigate method to perform the navigation action created above
// we do not use the id of the action in this case
        findNavController().navigate(actionTaskListFragmentToDisplayTaskFragment)
    }

    override fun onTaskLongClick(taskPosition: Int) {
        showDeleteDialog(taskPosition)
    }

    // show a dialog window to delete a task at given position
    private fun showDeleteDialog(taskPosition: Int) {
// get the task to delete from the list
        val taskToDelete = Tasks.list[taskPosition]
// Define a dialog window with the AlertDialog.Builder class
        val dialogBuilder = android.app.AlertDialog.Builder(requireContext())
// set the title for the dialog
        dialogBuilder.setTitle("Delete Task?")
// set the message for the dialog
        dialogBuilder.setMessage(taskToDelete.title)
// set the positive button for the dialog
            .setPositiveButton("Confirm") { _, _ ->
                deleteDialogPositiveClick(taskPosition)
            }
// set the negative button for the dialog
            .setNegativeButton("Cancel") { dialog, _ ->
// if the user cancels the deletion, dismiss the dialog
                dialog.dismiss()
                deleteDialogNegativeClick(taskPosition)
            }
// create the dialog
        val alert = dialogBuilder.create()
// show the dialog
        alert.show()
    }

    private fun deleteDialogPositiveClick(taskPosition: Int) {
// remove the task from the list
        Tasks.list.removeAt(taskPosition)
// show a snackbar message to confirm the deletion
        Snackbar.make(binding.root, "Task deleted", Snackbar.LENGTH_SHORT).show()
// notify the adapter that the data has changed
        binding.list.adapter?.notifyItemRemoved(taskPosition)
    }
    private fun deleteDialogNegativeClick(taskPosition: Int) {
// show a snackbar message to confirm the cancellation.
// The action specified for the snackbar allows to add "REDO" button to
// show the dialog again
        Snackbar.make(binding.root, "Deletion cancelled", Snackbar.LENGTH_SHORT)
            .setAction("Redo") {
// if the user wants to redo the deletion, call the showDeleteDialog method again
                showDeleteDialog(taskPosition)
            }.show()
    }
}