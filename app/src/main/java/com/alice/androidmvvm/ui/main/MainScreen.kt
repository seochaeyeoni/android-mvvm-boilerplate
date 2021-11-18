package com.alice.androidmvvm.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alice.androidmvvm.model.TodoMainData
import com.alice.androidmvvm.ui.components.CalendarDialog
import com.alice.androidmvvm.ui.theme.Typography

@Composable
fun MainScreen(onItemClick: (Int, Boolean) -> Unit, viewModel: MainViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val todoList = viewModel.todoDataList.collectAsState(initial = listOf())
    var openDialog by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openDialog = true },
                modifier = Modifier.size(44.dp),
                backgroundColor = MaterialTheme.colors.primary,
                content = {
                    Icon(Icons.Outlined.Create, contentDescription = "create")
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colors.background)
        ) {
            if (openDialog) CalendarDialog(
                onDismissClick = { openDialog = false },
                onCheckDateUnique = { select ->
                    if (todoList.value.none { it.date == select }) {
                        onItemClick(select, true)
                        return@CalendarDialog true
                    }
                    return@CalendarDialog false
                },
                context = context
            )
            TodoList(todoList = todoList.value, onItemClick = onItemClick)
        }
    }
}

@Composable
fun TodoList(todoList: List<TodoMainData>, onItemClick: (Int, Boolean) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "TodoList",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            style = Typography.h1,
            textAlign = TextAlign.Center
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(todoList) { todo ->
                TodoItem(todo = todo, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun TodoItem(todo: TodoMainData, onItemClick: (Int, Boolean) -> Unit) {
    val date = "${todo.date.toString().slice(0..3)}/${
    todo.date.toString().slice(4..5)
    }/${todo.date.toString().slice(6..7)}"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(6.dp)
            .shadow(3.dp)
            .background(MaterialTheme.colors.background, MaterialTheme.shapes.medium)
            .clickable { onItemClick(todo.date, false) }
    ) {
        Text(
            text = todo.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, start = 6.dp, end = 6.dp),
            style = Typography.body1,
            textAlign = TextAlign.Center
        )
        Text(
            text = date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            style = Typography.caption,
            textAlign = TextAlign.Center
        )
    }
}
