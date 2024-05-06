import android.util.Log
import com.example.mytodo.Task
import kotlin.math.log

object Tasks {
    // This is the list that will be used to store the tasks
    val list: MutableList<Task> = ArrayList()
    val shownList: MutableList<Task> = ArrayList()
    var catFilter: String = "Wszystkie"
    // This is the number of placeholder tasks that will be added to the list
    private val COUNT = 10
    // This is the init block that will be used to add the placeholder tasks to the list
    init {
//        for (i in 1..COUNT) {
////            addTask(createPlaceholderTask(i))
////        }
    }
    // This function is used to add a task to the list
    fun addTask(task: Task) {
        list.add(task)
        updateShownList()

    }

    fun updateFilter(filt: String){
        catFilter = filt
        updateShownList()
    }
    fun updateShownList(){
        Log.i("chujujujuj", catFilter)
        shownList.clear()
        for (task in list){
            if (catFilter == "Wszystkie"){
                shownList.add(task)
            }else{
                if (task.category == catFilter){
                    shownList.add(task)
                }
            }

        }
    }
    // This function is used to create a placeholder task so the initial list is not empty
//    private fun createPlaceholderTask(position: Int): Task {
//        return Task(position.toString(), "Task $position", makeDetails(position))
//    }
    // This function is used to create a placeholder description for the tasks
// it takes in an integer position and returns a string created with the StringBuilder
    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Task: ").append(position)
        for (i in 0..<position) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
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
        updateShownList()
    }
}