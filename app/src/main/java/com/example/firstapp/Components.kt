package com.example.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun TodoApp() {

    var todoItems by remember { mutableStateOf(listOf<String>()) }

    Column {
        Header()
        InputTodoItem(
            todoItems = todoItems,
            setTodoItems = { todoItems = it }
        )
        DisplayTodoItems(
            todoItems = todoItems,
            removeItems = { todoItems = it }
        )
    }
}

@Composable
fun Header() {
    Column (
        modifier = Modifier
            .padding(top = 70.dp, bottom = 20.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.light_green)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Image(
                painterResource(id = R.drawable.note_and_pen),
                contentDescription = "icon",
                modifier = Modifier
                    .width(45.dp)
                    .padding(10.dp)
            )
            Column {
                Text(
                    text = "TODO LIST",
                    fontSize = 24.sp
                )
                Text(
                    text = "Create your list",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTodoItem(
    todoItems: List<String>,
    setTodoItems: (List<String>) -> Unit,
) {
    var inputItem by remember{ mutableStateOf("") }
    OutlinedTextField(
        value = inputItem,
        onValueChange = { inputItem = it },
        placeholder = { Text("Enter your task") },
        maxLines = 1,
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.assignment),
                contentDescription = "icon",
                colorFilter = ColorFilter.tint(Color.Black),
                modifier = Modifier
                    .clickable {
                        if (inputItem.isNotEmpty()) {
                            setTodoItems(todoItems + inputItem)
                            inputItem = ""
                        }
                    }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black
        )
    )
}

@Composable
fun TodoItem(
    task: String,
    removeTask: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = colorResource(id = R.color.white)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = R.drawable.add_task),
            contentDescription = "icon",
            modifier = Modifier
                .width(50.dp)
                .padding(10.dp)
                .weight(1f),
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Text(
            text = task,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(6f)
                .padding(start = 10.dp)
        )
        Image(
            painterResource(id = R.drawable.delete),
            contentDescription = "icon",
            modifier = Modifier
                .width(50.dp)
                .padding(10.dp)
                .weight(1f)
                .clickable {
                    removeTask(task)
                },
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}

@Composable
fun DisplayTodoItems(
    todoItems: List<String>,
    removeItems: (List<String>) -> Unit
) {
    Column (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color = colorResource(id = R.color.light_grey))
            .wrapContentHeight()
    ) {
        todoItems.forEach {
            TodoItem(task = it, removeTask = { task ->
                removeItems(todoItems - task)
            })
        }
    }
}
