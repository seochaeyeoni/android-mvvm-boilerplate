package com.alice.androidmvvm.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.datetime.datePicker

@Composable
fun CalendarDialog(onDismissClick: () -> Unit, onCheckDateUnique: (Int) -> Boolean, context: Context) {
    var date by rememberSaveable { mutableStateOf(0) }
    val errorMsg = "There's already a TodoList for that date."
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        MaterialDialog(context).show {
            datePicker { _, select ->
                date = select.year * 10000 + (select.month + 1) * 100 + select.dayOfMonth
            }
            positiveButton(
                click = {
                    if (date != 0) {
                        if (onCheckDateUnique(date)) dismiss()
                        else Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                    }
                }
            )
            negativeButton(
                click = {
                    dismiss()
                }
            )
            onDismiss {
                onDismissClick()
            }
        }
    }
}
