package ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.viewmodel

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.R
import ph.stacktrek.novare.SnakeAndLadder.ramirez.chris.model.*
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
    val ladders= listOf(
        Ladder(3, 20),
        Ladder(6, 14),
        Ladder(11, 28),
        Ladder(15, 34),
        Ladder(17, 74),
        Ladder(22, 37),
        Ladder(38, 59),
        Ladder(49, 67),
        Ladder(57, 76),
        Ladder(61, 78),
        Ladder(73, 86),
        Ladder(81, 98),
        Ladder(88, 91)

    )
    val snakes= listOf(
        Snake(8, 4),
        Snake(18, 1),
        Snake(26, 10),
        Snake(39, 5),
        Snake(51, 6),
        Snake(54, 36),
        Snake(56, 1),
        Snake(60, 23),
        Snake(75, 29),
        Snake(85, 59),
        Snake(90, 48),
        Snake(92, 25),
        Snake(97, 87),
        Snake(99, 63),
    )




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
        originY=(height-snakeAndLadderBoardSide)/2f
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
//                        println("your position is at ($row, $col): number = $squareNumber ")
                    }
                }

                drawTextAt(canvas,row,col,squareNumber)
                drawLadderBottomAt(canvas,squareNumber,ladders)
                drawSnakeAt(canvas,squareNumber,snakes)
//                print("Square at ($row, $col): number = $squareNumber ")

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
        paint.typeface= Typeface.DEFAULT_BOLD
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

    fun drawLadderBottomAt(canvas: Canvas?, squareNumber: Int, ladders: List<Ladder>) {
        val ladder = ladders.find { it.bottom == squareNumber }
        if (ladder != null) {
            val row = (squareNumber - 1) / 10
            val col = if (row % 2 == 0) (squareNumber - 1) % 10 else 9 - (squareNumber - 1) % 10

            val ladder = ContextCompat.getDrawable(context, R.drawable.ladder)
            val canvasWidth = canvas?.width ?: 0
            val canvasHeight = canvas?.height ?: 0
            val imageSize = (cellSide / 0.8f).coerceAtMost(cellSide * 0.8f)
            val left = originX + (col + 0.5f) * cellSide - imageSize / 2
            val top = originY + (9 - row + 0.5f) * cellSide - imageSize / 2
            val right = left + imageSize
            val bottom = top + imageSize
            val rect = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
            ladder?.bounds = rect
            if (canvas != null) {
                ladder?.draw(canvas)
            }
        }
    }
    fun drawLadderTopAt(canvas: Canvas?, squareNumber: Int, ladders: List<Ladder>) {
        val ladder = ladders.find { it.top == squareNumber }
        if (ladder != null) {
            val row = (squareNumber - 1) / 10
            val col = if (row % 2 == 0) (squareNumber - 1) % 10 else 9 - (squareNumber - 1) % 10

            val ladder = ContextCompat.getDrawable(context, R.drawable.ladder)
            val canvasWidth = canvas?.width ?: 0
            val canvasHeight = canvas?.height ?: 0
            val imageSize = (cellSide / 0.8f).coerceAtMost(cellSide * 0.8f)
            val left = originX + (col + 0.5f) * cellSide - imageSize / 2
            val top = originY + (9 - row + 0.5f) * cellSide - imageSize / 2
            val right = left + imageSize
            val bottom = top + imageSize
            val rect = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
            ladder?.bounds = rect
            if (canvas != null) {
                ladder?.draw(canvas)
            }
        }
    }

    fun drawSnakeAt(canvas: Canvas?,squareNumber: Int,snake:List<Snake>){
        val snakes=snake.find { it.head == squareNumber }
        if(snakes !=null){
            val row = (squareNumber - 1) / 10
            val col = if (row % 2 == 0) (squareNumber - 1) % 10 else 9 - (squareNumber - 1) % 10

            val ahas = ContextCompat.getDrawable(context, R.drawable.ahas)
            val canvasWidth = canvas?.width ?: 0
            val canvasHeight = canvas?.height ?: 0
            val imageSize = (cellSide / 0.8f).coerceAtMost(cellSide * 0.8f)
            val left = originX + (col + 0.5f) * cellSide - imageSize / 2
            val top = originY + (9 - row + 0.5f) * cellSide - imageSize / 2
            val right = left + imageSize
            val bottom = top + imageSize
            val rect = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
            ahas?.bounds = rect
            if (canvas != null) {
                ahas?.draw(canvas)
            }
        }
    }

    fun movePlayerIfOnLadder( playerPosition:Int,ladders: List<Ladder>,context: Context?,player: Player):Int{
        val ladder = ladders.find { it.bottom == playerPosition }
        if (ladder != null){
            Toast.makeText(context, "Yehheey yes!... ${player.name} you step on ladder", Toast.LENGTH_SHORT).show()
            return  ladder.top
        }
        return  playerPosition
    }
    fun movePlayerIfOnSnake(playerPosition: Int,snakes:List<Snake>,context: Context?,player: Player):Int{
        val snake = snakes.find { it.head == playerPosition}
        if(snake !=null){
            Toast.makeText(context, "Oh no!... ${player.name} you step on snake", Toast.LENGTH_SHORT).show()
            return  snake.tail
        }
        return  playerPosition
    }



    fun updatePlayerList(playersList:MutableList<Player>) {
        players=playersList


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