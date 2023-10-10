package com.example.a21cartas
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity2 : AppCompatActivity() {
    private val baralho = mutableListOf(
        "Ás", "Dois", "Três", "Quatro", "Cinco", "Seis", "Sete", "Oito", "Nove", "Dez", "Valete", "Dama", "Rei"
    )
    private val naipes = listOf("Espadas", "Paus", "Copas", "Ouros")
    private val random = Random()
    private val maoJogador1 = mutableListOf<String>()
    private val maoJogador2 = mutableListOf<String>()
    private var totalPontosJogador1 = 0
    private var totalPontosJogador2 = 0
    private lateinit var textViewMao: TextView
    private lateinit var textViewTotalPontos: TextView
    private lateinit var resultadoTextView: TextView
    private lateinit var jogadorAtualTextView: TextView
    private lateinit var btnComprar: Button
    private lateinit var btnParar: Button
    private var jogadorAtual = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        resultadoTextView = findViewById(R.id.resultadoTextView)
        textViewMao = findViewById(R.id.textViewMao)
        textViewTotalPontos = findViewById(R.id.textViewTotalPontos)
        jogadorAtualTextView = findViewById(R.id.jogadorAtualTextView)
        btnComprar = findViewById(R.id.btnComprar)
        btnParar = findViewById(R.id.btnParar)

        "Jogador Atual: Jogador 1".also { jogadorAtualTextView.text = it }

        btnComprar.setOnClickListener {
            comprarCarta()
        }

        btnParar.setOnClickListener {
            verificarResultado()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun comprarCarta() {
        val carta = baralho[random.nextInt(baralho.size)]
        val naipe = naipes[random.nextInt(naipes.size)]
        val cartaComNaipe = "$carta de $naipe"

        if (jogadorAtual == 1) {
            maoJogador1.add(cartaComNaipe)
            totalPontosJogador1 += valorCarta(carta)
        } else {
            maoJogador2.add(cartaComNaipe)
            totalPontosJogador2 += valorCarta(carta)
        }

        textViewMao.text = "Mão do Jogador 1: ${maoJogador1.joinToString(", ")}\nMão do Jogador 2: ${maoJogador2.joinToString(", ")}"
        textViewTotalPontos.text = "Pontos do Jogador 1: $totalPontosJogador1\nPontos do Jogador 2: $totalPontosJogador2"

        when {
            totalPontosJogador1 == 21 || totalPontosJogador2 == 21 -> {
                exibirResultado("Jogador ${if (totalPontosJogador1 == 21) 1 else 2} fez 21! Jogador ${if (totalPontosJogador1 == 21) 1 else 2} ganhou!")
                val intent = Intent(this, ProximaAtividade::class.java)
                startActivity(intent)
            }
            totalPontosJogador1 > 21 || totalPontosJogador2 > 21 -> {
                exibirResultado("Jogador ${if (totalPontosJogador1 > 21) 1 else 2} estourou! Jogador ${if (totalPontosJogador1 > 21) 1 else 2} perdeu!")
            }
            else -> {
                jogadorAtual = when (jogadorAtual) {
                    1 -> 2
                    else -> 1
                }
                jogadorAtualTextView.text = "Jogador Atual: Jogador $jogadorAtual"
            }
        }
    }

    private fun valorCarta(carta: String): Int = when (carta) {
        "Ás" -> 1
        in listOf("Dois", "Três", "Quatro", "Cinco", "Seis", "Sete", "Oito", "Nove", "Dez") -> carta.toInt()
        in listOf("Valete", "Dama", "Rei") -> 10
        else -> 0
    }

    private fun exibirResultado(mensagem: String) {
        resultadoTextView.text = mensagem
        btnComprar.isEnabled = false
        btnParar.isEnabled = false
    }

    private fun verificarResultado() {
        val vencedor = when {
            totalPontosJogador1 == totalPontosJogador2 -> "Empate! Pontuação dos jogadores 1 e 2 é igual."
            totalPontosJogador1 > 21 && totalPontosJogador2 > 21 -> "Nenhum jogador venceu. Ambos estouraram!"
            totalPontosJogador1 <= 21 && totalPontosJogador1 > totalPontosJogador2 -> "Jogador 1 venceu com $totalPontosJogador1 pontos!"
            else -> "Jogador 2 venceu com $totalPontosJogador2 pontos!"
        }

        exibirResultado(vencedor)
    }
}
