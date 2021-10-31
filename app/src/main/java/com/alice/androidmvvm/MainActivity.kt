package com.alice.androidmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alice.androidmvvm.AndroidApplication.Companion.mainViewModel
import com.alice.androidmvvm.model.TodoData
import com.alice.androidmvvm.ui.theme.AndroidMvvmTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel = mainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidMvvmTheme {
                TodoListScreen(viewModel = viewModel)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateList.apply {
            if (this.isNotEmpty()) {
                viewModel.updateTodoData(this.values.toList())
            }
        }
    }
}

@Composable
fun TodoListScreen(viewModel: MainViewModel) {
    val data: List<TodoData> by viewModel.todoData.collectAsState(initial = mutableListOf())
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                TodoList(todos = data, viewModel)
            }
            Row() {
                CreateButton(
                    onCreateClicked = {
                        viewModel.insertTodoData(TodoData(System.currentTimeMillis(), false, ""))
                    }
                )
            }
        }
    }
}

@Composable
fun TodoList(todos: List<TodoData>, viewModel: MainViewModel) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ) {
        LazyColumn() {
            items(items = todos) { todo ->
                TodoItem(
                    todo = todo,
                    onDeleteClicked = {
                        viewModel.deleteTodoData(todo)
                    },
                    onDataChanged = fun(): (text: String, check: Boolean) -> Unit = { text, check ->
                        viewModel.setUpdateList(TodoData(todo.dateTime, check, text))
                    }
                )
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: TodoData,
    onDeleteClicked: () -> Unit,
    onDataChanged: () -> (String, Boolean) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(todo.text) }
    var check by rememberSaveable { mutableStateOf(todo.isDone) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.padding(start = 8.dp),
            checked = check,
            onCheckedChange = {
                check = it
                onDataChanged()(text, check)
            }
        )
        TextField(
            modifier = Modifier.padding(start = 8.dp, bottom = 12.dp),
            value = text,
            onValueChange = {
                if (it.length < 20) {
                    text = it
                    onDataChanged()(text, check)
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.secondary
            )
        )
        IconButton(onClick = onDeleteClicked) {
            Icon(
                Icons.Rounded.Delete,
                contentDescription = "delete",
                tint = MaterialTheme.colors.secondary
            )
        }
    }
}

@Composable
fun CreateButton(onCreateClicked: () -> Unit) {
    Button(modifier = Modifier.fillMaxWidth(), onClick = onCreateClicked) {
        Row() {
            Icon(
                Icons.Rounded.Add,
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "Add Todo")
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    AndroidMvvmTheme {
    }
}
