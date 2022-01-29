package com.vb.breastfeedingnotes.views.weightScreen

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.breastfeedingnotes.database.weight.Weight
import com.vb.breastfeedingnotes.database.weight.WeightViewModel
import com.vb.breastfeedingnotes.ui.WeightDatePicker
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.time.temporal.ChronoUnit
import kotlin.time.ExperimentalTime

@ExperimentalTime
@InternalCoroutinesApi
@Composable
fun WeightScreenView() {
    val weightViewModel = hiltViewModel<WeightViewModel>()
    var text by rememberSaveable { mutableStateOf("") }
    val allWeights = weightViewModel.getAllWeights().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth(),  verticalArrangement = Arrangement.Center,  horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.fillMaxWidth().padding(5.dp))
        Text(
            text = "Baby's weight log",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,

        )
        WeightChart(records = allWeights.value, modifier = Modifier.fillMaxWidth().height(200.dp))
        WeightDatePicker()
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            label = { Text("Weight in grams") },
            keyboardOptions = KeyboardOptions(KeyboardCapitalization.Characters, true, KeyboardType.Number, ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (text != "") {
                    scope.launch {
                        weightViewModel.insertWeight(Weight(0, weightViewModel.weightSelectedDate.value, text.toFloat()))
                        text = ""
                    }
                }
            }),
            placeholder = { Text(text = "i.e.3800") }
        )

        OutlinedButton(onClick = {
            if (text != "") {
                scope.launch {
                    weightViewModel.insertWeight(Weight(0, weightViewModel.weightSelectedDate.value, text.toFloat()))
                    text = ""
                }
            }
        }) {
            Text("Insert Weight")
        }

        LazyColumn(Modifier.fillMaxWidth()) {
            items(allWeights.value) { item ->
                Row {
                    Text("${item.weighDate}", modifier = Modifier.padding(5.dp))
                    Text("${item.weight}", modifier = Modifier.padding(5.dp))
                    OutlinedButton(onClick = {
                        scope.launch {
                            weightViewModel.deleteWeight(item)
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, "delete_icon")
                    }
                }

            }
        }
    }
}

@Composable
fun WeightChart(records: List<Weight>, modifier: Modifier) {
    if (records.isEmpty()) return
    Card(
        modifier = Modifier.fillMaxWidth()
            .height(350.dp)
            .padding(16.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
                .wrapContentSize(align = Alignment.BottomStart)
        ) {
            Canvas(modifier = modifier) {
                val totalRecords = records.size

                // distance between dots
                val distance = size.width / (totalRecords + 1)

                //max weight (the last entry in the list)
                //  val maxValue = records.get(totalRecords-1).weight
                var maxValue = 0f

                val maxWeight = records.maxByOrNull { it.weight }
                if (maxWeight != null) { maxValue = maxWeight.weight }
                var currentX = records[0].weighDate
                var x = 10F

                val points = mutableListOf<Points>()
                val dots = mutableListOf<Offset>()

                val paint = Paint()
                paint.textAlign = Paint.Align.CENTER
                paint.textSize = 24f
                paint.color = 0xFF000000.toInt()


                /*the graph goes like this: y goes down, x to the right, therefore
                 must use maxvalue - current value * relative screen size (overallSize/maxValue)
                  */

                for (j in 0 until records.size-1) {
                    val y = (maxValue - records[j].weight) * (size.height / maxValue)
                    points.add(j, Points(x, y))
                    x += ChronoUnit.DAYS.between(currentX, records[j].weighDate).toFloat() + distance
                    currentX = records[j].weighDate
                    dots.add(Offset(points[j].x, points[j].y))

                }


                for (i in 0 until points.size - 1) {
                    drawLine(
                        start = Offset(points[i].x, points[i].y),
                        end = Offset(points[i + 1].x, points[i + 1].y),
                        color = Color.Black,
                        strokeWidth = 4f
                    )
                }
                /*
                DRAW months on X axis
                 */
                for (i in 0 until points.size) {
                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            "${toMonthName(records[i].weighDate.monthValue)}-" + "${records[i].weighDate.dayOfMonth}",
                            points[i].x,
                            size.height + 4.dp.toPx(),
                            paint
                        )
                    }
                }

                /*
                DRAW weight on Y axis
                 */

                for (i in 0 until points.size) {
                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            "${records[i].weight}",
                            4.dp.toPx(),
                            points[i].y,
                            paint
                        )
                    }
                }

                drawPoints(
                    points = dots,
                    pointMode = PointMode.Points,
                    strokeWidth = 8f,
                    color = Color.Red
                )
            }
        }
    }
}

data class Points(
    val x: Float,
    val y: Float
)

fun toMonthName(month: Int): String {
   return when (month) {
       1  -> "Jan"
       2 -> "Feb"
       3 -> "Mar"
       4 -> "Apr"
       5 -> "May"
       6  -> "Jun"
       7 -> "Jul"
       8 -> "Aug"
       9 -> "Sep"
       10 -> "Oct"
       11 -> "Nov"
       12 -> "Dec"
       else -> "error"
   }
}

