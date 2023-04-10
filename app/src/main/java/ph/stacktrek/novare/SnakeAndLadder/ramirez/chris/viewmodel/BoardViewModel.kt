package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.viewmodel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model.Player
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model.PlayerColor
import kotlin.random.Random

class BoardViewModel(context: Context?, attrs: AttributeSet?): View(context,attrs) {



    private final var cellSide=130f
    private final var originY=0f
    private final var originX=0f
    private val scaleFactor = 1.0f
    private val paint = Paint()
    private  val darkGreen = Color.rgb(76,175,80)
    private val lightGreen = Color.rgb(204,255,51)
    private var players = mutableListOf<Player>()
    private var isGameOver = false




    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val smaller = Integer.min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(smaller, smaller)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?:return
        val snakeAndLadderBoardSide= Integer.min(width, height) *scaleFactor
        cellSide= snakeAndLadderBoardSide / 10f
        originX=(width-snakeAndLadderBoardSide)/2f
        originY=(height-snakeAndLadderBoardSide)/2
        if (players != null) {
            drawSnakeAndLadderBoard(canvas,players)
        }
    }

    fun drawSnakeAndLadderBoard(canvas: Canvas?, players: MutableList<Player>){
        for(row in 9 downTo 0) {
            for(col in 0 until  10) {
                val squareNumber = if(row %2 == 1){
                    row * 10 +(10-col)
                }else{
                    row * 10 + col + 1
                }
                drawSquareAt(canvas, row, col, ((row + col)%2 == 0))

                for (player in players) {
                    if (player.currentPosition == squareNumber) {
                        drawPlayerAt(canvas, squareNumber, player.color)
                        println("your position is at ($row, $col): number = $squareNumber ")
                    }
                }
                drawTextAt(canvas,row,col,squareNumber)


                print("Square at ($row, $col): number = $squareNumber ")
            }
            println()
        }
        println()
    }
    fun drawSquareAt(canvas: Canvas?, row:Int, col:Int, isDark:Boolean){
        paint.color=if(isDark) darkGreen else lightGreen
        canvas?.drawRect(
            originX + col * cellSide,
            originY +(9- row) * cellSide,
            originX + (col + 1)* cellSide,
            originY + (10 - row ) * cellSide,
            paint
        )
    }

    fun drawTextAt(canvas: Canvas?, row: Int, col: Int, squareNumber: Int){
        paint.color= Color.BLACK
        paint.textSize=cellSide/3f
        paint.textAlign= Paint.Align.CENTER

        val x= originX+(col+0.5f)*cellSide
        val y= originY+(9-row+0.5f)*cellSide+paint.textSize/2f

        canvas?.drawText(squareNumber.toString(),x,y,paint)
    }

    fun drawPlayerAt(canvas: Canvas?, squareNumber: Int, color: PlayerColor) {
        // Find the row and column indices of the square
        val row = (squareNumber - 1) / 10
        val col = if (row % 2 == 0) (squareNumber - 1) % 10 else 9 - (squareNumber - 1) % 10

        paint.color = color.colorInt
        canvas?.drawCircle(
            originX + (col + 0.5f) * cellSide,
            originY + (9 - row + 0.5f) * cellSide,
            cellSide / 4f,
            paint
        )
    }

    fun updatePlayerList(playersList:MutableList<Player>) {
        players=playersList


    }


    fun reset(){

    }



    fun winner(player: Player){
        println("${player.name} congratulation you win")
    }
    fun roll():Int{
        return Random.nextInt(1,7)
    }


    fun gameOver(){
        isGameOver=true
        println("Game over ")
    }


}