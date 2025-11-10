package com.example.integernumber

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var edtNumber: EditText
    private lateinit var listView: ListView
    private lateinit var txtMessage: TextView
    private lateinit var radioGroup: RadioGroup



    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        1
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtNumber = findViewById(R.id.edtNumber)
        listView = findViewById(R.id.listView)
        txtMessage = findViewById(R.id.txtMessage)
        radioGroup = findViewById(R.id.radioGroup)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList())
        listView.adapter = adapter

        edtNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateList(adapter)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        radioGroup.setOnCheckedChangeListener { _, _ ->
            updateList(adapter)
        }
    }

    private fun updateList(adapter: ArrayAdapter<String>) {
        val inputText = edtNumber.text.toString()
        adapter.clear()

        if (inputText.isEmpty()) {
            txtMessage.text = "Nhập số nguyên"
            txtMessage.textSize = 16f
            txtMessage.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            txtMessage.visibility = TextView.VISIBLE
            return
        }

        val n = inputText.toIntOrNull() ?: return
        val selectedId = radioGroup.checkedRadioButtonId

        if (selectedId == -1) {
            txtMessage.text = "Chọn loại số"
            txtMessage.textSize = 16f
            txtMessage.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            txtMessage.visibility = TextView.VISIBLE
            return
        }

        val numbers = (1 until n).toList()
        val filtered = when (selectedId) {
            R.id.rbOdd -> numbers.filter { it % 2 != 0 }
            R.id.rbEven -> numbers.filter { it % 2 == 0 }
            R.id.rbPrime -> numbers.filter { isPrime(it) }
            R.id.rbPerfect -> numbers.filter { isPerfect(it) }
            R.id.rbSquare -> numbers.filter { isSquare(it) }
            R.id.rbFibo -> numbers.filter { isFibo(it) }
            else -> emptyList()
        }

        if (filtered.isEmpty()) {
            txtMessage.text = "Không có số nào thỏa mãn"
            txtMessage.textSize = 16f
            txtMessage.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            txtMessage.visibility = TextView.VISIBLE
        } else {
            txtMessage.visibility = TextView.GONE
            adapter.addAll(filtered.map { it.toString() })
        }
    }

    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        for (i in 2..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return false
        }
        return true
    }

    private fun isSquare(n: Int): Boolean {
        val root = sqrt(n.toDouble()).toInt()
        return root * root == n
    }

    private fun isPerfect(n: Int): Boolean {
        if (n < 2) return false
        var sum = 1
        for (i in 2..n / 2) {
            if (n % i == 0) sum += i
        }
        return sum == n
    }

    private fun isFibo(n: Int): Boolean {
        fun isPerfectSquare(x: Int): Boolean {
            val s = sqrt(x.toDouble()).toInt()
            return s * s == x
        }
        return isPerfectSquare(5 * n * n + 4) || isPerfectSquare(5 * n * n - 4)
    }
}
