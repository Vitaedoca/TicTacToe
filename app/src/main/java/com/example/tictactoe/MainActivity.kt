package com.example.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var restartButton: Button
    private lateinit var buttons: Array<Button>
    private var currentPlayer = 1 // 1 for X, 2 for O
    private var board = Array(3) { IntArray(3) } // 0 = empty, 1 = X, 2 = O

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        restartButton = findViewById(R.id.restartButton)
        buttons = arrayOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9)
        )

        for (i in buttons.indices) {
            buttons[i].setOnClickListener { onCellClick(i) }
        }

        restartButton.setOnClickListener { restartGame() }
    }

    private fun onCellClick(index: Int) {
        val row = index / 3
        val col = index % 3

        if (board[row][col] == 0) { // Se a célula está vazia
            board[row][col] = currentPlayer
            buttons[index].text = if (currentPlayer == 1) "X" else "O"
            buttons[index].setTextColor(if (currentPlayer == 1) Color.RED else Color.BLUE) // Cores diferentes para X e O
            buttons[index].isEnabled = false // Desabilita o botão após a jogada

            if (checkWinner()) {
                statusTextView.text = "Jogador $currentPlayer venceu!"
                highlightWinningLine()
                disableButtons()
            } else if (isBoardFull()) {
                statusTextView.text = "Empate!"
            } else {
                currentPlayer = 3 - currentPlayer // Alterna entre 1 e 2
                statusTextView.text = "Jogador $currentPlayer na vez"
            }
        }
    }

    private fun checkWinner(): Boolean {
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { cell -> cell != 0 } }
    }

    private fun disableButtons() {
        for (button in buttons) {
            button.isEnabled = false
        }
    }

    private fun highlightWinningLine() {
        // Lógica para destacar a linha vencedora
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                buttons[i * 3].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                buttons[i * 3 + 1].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                buttons[i * 3 + 2].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                return
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                buttons[0 + i].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                buttons[3 + i].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                buttons[6 + i].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                return
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            buttons[0].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            buttons[4].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            buttons[8].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            buttons[2].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            buttons[4].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            buttons[6].setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }
    }

    private fun restartGame() {
        board = Array(3) { IntArray(3) }
        currentPlayer = 1
        statusTextView.text = "Jogador 1 (X) na vez"
        buttons.forEach { button ->
            button.text = ""
            button.isEnabled = true
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }
    }
}
