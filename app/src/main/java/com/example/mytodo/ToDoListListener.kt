package com.example.mytodo

interface ToDoListListener {
    fun onTaskClick(taskPosition: Int)
    fun onTaskLongClick(taskPosition: Int)
    fun setOnBudget()
}