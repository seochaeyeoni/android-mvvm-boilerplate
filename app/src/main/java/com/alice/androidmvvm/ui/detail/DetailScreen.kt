package com.alice.androidmvvm.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alice.androidmvvm.model.Check
import com.alice.androidmvvm.model.TodoData
import com.alice.androidmvvm.ui.theme.AndroidMvvmTheme
import com.alice.androidmvvm.ui.theme.Typography

@Composable
fun DetailScreen(date: Int, isNew: Boolean, viewModel: DetailViewModel = hiltViewModel()) {
    val todoData = viewModel.todoData.observeAsState()
    var title by rememberSaveable { mutableStateOf("") }
    var checkList by rememberSaveable { mutableStateOf(listOf<Check>()) }
    LaunchedEffect(key1 = Unit, block = { viewModel.getTodoData(date) })
    if (todoData.value != null) {
        title = todoData.value!!.title
        checkList = todoData.value!!.checkList
    }
    DisposableEffect(key1 = viewModel) {
        onDispose {
            if (isNew) viewModel.insertTodoData(TodoData(date, title, checkList))
            else viewModel.updateTodoData(TodoData(date, title, checkList))
        }
    }

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TodoTitle(title = title, onTitleChanged = { if (it.length < 20) title = it })
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                CheckList(
                    todos = checkList,
                    onDeleteClicked = { idx ->
                        checkList = checkList.toMutableList().also {
                            it.removeAt(idx)
                        }
                    },
                    onDataChanged = { idx, after ->
                        checkList = checkList.toMutableList().also {
                            it[idx] = after
                        }
                    }
                )
            }
            CreateButton(
                onCreateClicked = {
                    checkList = checkList + listOf(Check())
                }
            )
        }
    }
}

@Composable
fun TodoTitle(title: String, onTitleChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        value = title,
        onValueChange = {
            onTitleChanged(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary
        ),
        textStyle = Typography.h1,
        maxLines = 1
    )
}

@Composable
fun CheckList(
    todos: List<Check>,
    onDeleteClicked: (Int) -> Unit,
    onDataChanged: (Int, Check) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 10.dp
    ) {
        LazyColumn() {
            itemsIndexed(items = todos) { pos, todo ->
                CheckItem(
                    todo = todo,
                    position = pos,
                    onDeleteClicked = onDeleteClicked,
                    onDataChanged = onDataChanged
                )
            }
        }
    }
}

@Composable
fun CheckItem(
    todo: Check,
    position: Int,
    onDeleteClicked: (Int) -> Unit,
    onDataChanged: (Int, Check) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(todo.text) }
    var check by rememberSaveable { mutableStateOf(todo.isChecked) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.padding(start = 8.dp),
            checked = check,
            onCheckedChange = {
                check = it
                onDataChanged(position, Check(text, check))
            }
        )
        TextField(
            modifier = Modifier.padding(start = 8.dp, bottom = 12.dp),
            value = text,
            onValueChange = {
                if (it.length < 20) {
                    text = it
                    onDataChanged(position, Check(text, check))
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.secondary
            )
        )
        IconButton(
            onClick = {
                text = ""
                check = false
                onDeleteClicked(position)
            }
        ) {
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
