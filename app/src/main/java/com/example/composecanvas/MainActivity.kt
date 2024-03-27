package com.example.composecanvas

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.composecanvas.ui.theme.ComposeCanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCanvasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//
                    ChessBoard()
                }
            }
        }
    }
}

@Composable
fun DrawCircle(size:Dp) {
    Canvas(modifier = Modifier
        .size(size)
        .padding(12.dp), ){
        val canvasSize = size
        drawCircle(
            color = Color.Blue,
            radius = canvasSize.value
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun CanvasPreview() {
//
////    ChessBoard()
//    Column(modifier = Modifier
//        .size(400.dp))
//    {
//
//        val squareSize = Size(120f,120f)
//        for(i in 1..8){
//            Column {
//                for (j in 1..8){
//                    val color = if((i+j)%2==0) Color.Blue else Color.Black
//
//
//                    Square(color = color, size = squareSize, topLeft = Offset((i*110).toFloat(),((j*110).toFloat())) )
//
//
//                }
//            }
//        }
//
//
//    }


//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CanvasPreview() {

    ChessBoard()
}

@Composable
fun ChessBoard() {
    val squareSize = 40.dp
    val chessboardSize = 8 * squareSize
    var selectedPiece = remember {
        mutableIntStateOf(-1)
    }
    val onClick = {piece:Int-> selectedPiece.value = piece}

    val listOfPieces = listOf<List<Int>>(
        List(8) { R.drawable.wpawn },
        listOf(
            R.drawable.wrook, R.drawable.wknight, R.drawable.wbishop, R.drawable.wqueen,
            R.drawable.wking, R.drawable.wbishop, R.drawable.wknight, R.drawable.wrook
        ),
        List(8) { R.drawable.bpawn },
        listOf(
            R.drawable.brook, R.drawable.bknight, R.drawable.bbishop, R.drawable.bqueen,
            R.drawable.bking, R.drawable.bbishop, R.drawable.bknight, R.drawable.brook
        )
    )
    Column(modifier = Modifier
        .size(400.dp)
        .padding(10.dp)
    ,verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        repeat(8) { row ->
            Row {
                repeat(8) { column ->
                    val color = if ((row + column) % 2 == 0) Color.White else Color.Gray
                    val whitePieces = if(row == 0) 1 else 0
                    val blackPieces = if(row == 6) 2 else 3
                    val rowIndex = if (row == 0 || row == 1) whitePieces else blackPieces
                    val pieceIcon =
                        if (row == 0 || row == 1 || row == 6 || row == 7 )
                            listOfPieces[rowIndex][column]
                        else
                            null


//                    val placedPiece = if(selectedPiece.value!=-1) null else  pieceIcon
                    Square(color, squareSize, pieceIcon,onClick,selectedPiece.value)
                }
            }
        }
    }
}



@Composable
fun Square(color: Color, size: Dp, pieceIcon: Int? = null,onClick:(piece:Int)->Unit,selectedPiece:Int = -1) {
    var movedPiece by remember {
        mutableStateOf(-1)
    }
    var placedIcon by remember {
        mutableStateOf(-2)
    }
    placedIcon = pieceIcon?:-1
    Box(
        modifier = Modifier
            .size(size)
            .background(color)
            .clickable {
                if (pieceIcon != null) {
                    onClick(pieceIcon ?: -1)
                    placedIcon = -1

                } else {
                    movedPiece = selectedPiece
//                    placedIcon = movedPiece


                }
            },
        contentAlignment = Alignment.Center
    ) {

        if (placedIcon>0){
                Image(
                    painterResource(id = placedIcon),
                    contentDescription = null,

                    )

        }
        if(pieceIcon==null){
            if (movedPiece != -1){
                Image(
                    painterResource(id = movedPiece),
                    contentDescription = null,

                    )
            }
        }

    }
}