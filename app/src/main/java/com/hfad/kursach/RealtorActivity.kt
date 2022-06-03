package com.hfad.kursach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.hfad.kursach.myClasses.*
import java.io.Serializable
import java.lang.IllegalStateException
import java.lang.NullPointerException
import java.lang.NumberFormatException
import kotlin.properties.Delegates

class RealtorActivity : AppCompatActivity() {
    private lateinit var agency: Agency
    private lateinit var secondName: TextView
    private lateinit var number: TextView
    private lateinit var sum: TextView
    private lateinit var secondNameView: EditText
    private lateinit var sumView: EditText
    private lateinit var listView: ListView
    private val treaties: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtor)

        val intent: Intent = intent
        id = intent.getIntExtra("Id", 0)
        agency = intent.getSerializableExtra("Agency") as Agency

        secondName = findViewById(R.id.textView)
        number = findViewById(R.id.textView2)
        sum = findViewById(R.id.textView3)
        secondNameView = findViewById(R.id.secondName2)
        sumView = findViewById(R.id.sum)
        listView = findViewById(R.id.treaties)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, treaties)
        listView.adapter = adapter

        secondName.text = agency.realtors[id - 1].getSecondName
        number.text = agency.realtors[id - 1].getNumber
        sum.text = agency.realtors[id - 1].getTreatiesSum().toString()

        showTreaties(agency.realtors[id - 1])
    }

    private fun showTreaties(realtor: Realtor) {
        var treatiesRoot: Treaty? = realtor.getTreatiesRoot ?: return

        val toast = Toast.makeText(applicationContext, "Договоры риелтора загружены", Toast.LENGTH_SHORT)
        toast.show()
        while (treatiesRoot != null) {
            treaties.add(treatiesRoot.toString())
            treatiesRoot = treatiesRoot.next
        }
        adapter.notifyDataSetChanged()
    }

    fun run(view: View) {
        val checkView: CheckBox = findViewById(R.id.checkBox2)
        val check = checkView.isChecked

        if (!check)
            add()
        else
            delete()
    }

    private fun add() {
        val secondNameVar = secondNameView.text.toString()
        val sumVar = sumView.text.toString()

        try {
            sumVar.toInt()
        } catch (e: NumberFormatException) {
            val toast = Toast.makeText(applicationContext, "Некорректные данные", Toast.LENGTH_SHORT)
            toast.show()
            return
        }
        if (secondNameVar.length <= 2 || sumVar.isEmpty()) {
            val toast = Toast.makeText(applicationContext, "Некорректные данные", Toast.LENGTH_SHORT)
            toast.show()
            return
        }
        agency.realtors[id - 1].addTreaty(secondNameVar, sumVar.toInt())

        treaties.add(agency.realtors[id - 1].findTreaty(secondNameVar).toString())
        adapter.notifyDataSetChanged()
        sum.text = agency.realtors[id - 1].getTreatiesSum().toString()
    }
    private fun delete() {
        val secondNameVar = secondNameView.text.toString()

        if (secondNameVar.length <= 2) {
            val toast = Toast.makeText(applicationContext, "Некорректные данные", Toast.LENGTH_SHORT)
            toast.show()
            return
        }
        treaties.remove(agency.realtors[id - 1].findTreaty(secondNameVar).toString())
        agency.realtors[id - 1].deleteTreaty(secondNameVar)

        adapter.notifyDataSetChanged()
        sum.text = agency.realtors[id - 1].getTreatiesSum().toString()
    }

    fun save(view: View) {
        val answerIntent = Intent().putExtra("Agency", agency as Serializable)

        setResult(1, answerIntent)
        finish()
    }
}