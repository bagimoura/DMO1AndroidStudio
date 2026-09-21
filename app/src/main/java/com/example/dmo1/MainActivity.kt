package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val VALOR_DOLAR = 5.00 //valor fixo
    }

    //componentes da tela
    private lateinit var ivBandeiraOrigem: ImageView
    private lateinit var btnInverter: Button
    private lateinit var ivBandeiraDestino: ImageView
    private lateinit var etValor: EditText
    private lateinit var btnCalcular: Button
    private lateinit var btnLimpar: Button
    private lateinit var tvResultado: TextView

    //variável para saber a direção da conversão
    private var isRealParaDolar = true


    //main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupListeners()
    }

    //mapear os IDs do XML
    private fun setupViews() {
        ivBandeiraOrigem = findViewById(R.id.ivBandeiraOrigem)
        btnInverter = findViewById(R.id.btnInverter)
        ivBandeiraDestino = findViewById(R.id.ivBandeiraDestino)
        etValor = findViewById(R.id.etValor)
        btnCalcular = findViewById(R.id.btnCalcular)
        btnLimpar = findViewById(R.id.btnLimpar)
        tvResultado = findViewById(R.id.tvResultado)
    }

    //configurar os cliques dos botões
    private fun setupListeners() {
        btnCalcular.setOnClickListener {
            converterMoeda()
        }

        btnInverter.setOnClickListener {
            inverterMoedas()
        }

        btnLimpar.setOnClickListener {
            etValor.text.clear()
            tvResultado.text = ""
            etValor.requestFocus()
        }
    }

    //funções utilitárias de conversão e inversão
    private fun converterMoeda() {
        val valorTexto = etValor.text.toString()

        //validação com toast de campo vazio
        if (valorTexto.isEmpty()) {
            Toast.makeText(this, "Insira um valor a ser convertido.", Toast.LENGTH_SHORT).show()
            return
        }

        val valorDigitado: Double? = valorTexto.toDoubleOrNull()

        //validação com toast de campo inválido
        if (valorDigitado == null || valorDigitado <= 0) {
            Toast.makeText(this, "Valor inválido. Insira um número maior que zero.", Toast.LENGTH_SHORT).show()
            return
        }

        //lógica de conversão
        val resultado = if (isRealParaDolar) {
            valorDigitado / VALOR_DOLAR
        } else {
            valorDigitado * VALOR_DOLAR
        }

        //formatação do resultado
        val simbolo = if (isRealParaDolar) "US$" else "R$"
        tvResultado.text = "Resultado = %s %.2f".format(simbolo, resultado)
    }

    private fun inverterMoedas() {
        isRealParaDolar = !isRealParaDolar

        //limpa o resultado anterior
        tvResultado.text = ""
        etValor.text.clear()

        //troca visual das bandeiras
        if (isRealParaDolar) {
            ivBandeiraOrigem.setImageResource(R.drawable.br)
            ivBandeiraDestino.setImageResource(R.drawable.eua)

            etValor.hint = "Valor em R$"
            Toast.makeText(this, "Conversão: Real para Dólar", Toast.LENGTH_SHORT).show()
        } else {
            ivBandeiraOrigem.setImageResource(R.drawable.eua)
            ivBandeiraDestino.setImageResource(R.drawable.br)

            etValor.hint = "Valor em US$"
            Toast.makeText(this, "Conversão: Dólar para Real", Toast.LENGTH_SHORT).show()
        }
    }
}