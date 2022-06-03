package com.hfad.kursach

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.hfad.kursach.myClasses.*
import java.io.*


class MainActivity : AppCompatActivity() {
    private lateinit var agency: Agency
    private lateinit var secondNameView: EditText
    private lateinit var phoneView: EditText
    private lateinit var realtors: ListView
    private val secondNames: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>

    private lateinit var realtorClickListener: OnItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        agency = try {
            val fis: FileInputStream = this.openFileInput("Agency")
            val ois: ObjectInputStream = ObjectInputStream(fis)

            ois.readObject() as Agency
        } catch (e: FileNotFoundException) {
            Agency()
        }

        secondNameView = findViewById(R.id.secondName)
        phoneView = findViewById(R.id.phone)
        realtors = findViewById(R.id.realtors)
        secondNames.add("Риелторы:")
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, secondNames)
        realtors.adapter = adapter

        realtorClickListener = OnItemClickListener {
                _, _, position, _ ->
                if (position == 0)
                    return@OnItemClickListener
                val intent = Intent(this, RealtorActivity::class.java)
                intent.putExtra("Id", position)
                intent.putExtra("Agency", agency as Serializable)
                startActivityForResult(intent, 1)
        }
        realtors.onItemClickListener = realtorClickListener

        showRealtors()
    }

    private fun showRealtors() {
        val realtors = agency.realtors

        val toast = Toast.makeText(applicationContext, "Риелторы загружены", Toast.LENGTH_SHORT)
        toast.show()
        realtors.forEach {
            secondNames.add(it.getSecondName)
        }
        adapter.notifyDataSetChanged()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (data != null) {
                agency = data.getSerializableExtra("Agency") as Agency
            }
        }
    }

    fun run(view: View) {
        val checkView: CheckBox = findViewById(R.id.checkBox)
        val check = checkView.isChecked

        if (!check)
            add()
        else
            delete()
    }

    private fun add() {
        val secondName = secondNameView.text.toString()
        val phone = phoneView.text.toString()

        if (secondName.length <= 2 || phone.length != 11) {
            val toast = Toast.makeText(applicationContext, "Некорректные данные", Toast.LENGTH_SHORT)
            toast.show()
            return
        }
        agency.addRealtor(secondName, phone)

        secondNames.add(secondName)
        adapter.notifyDataSetChanged()
    }
    private fun delete() {
        val secondName = secondNameView.text.toString()

        if (secondName.length <= 2) {
            val toast = Toast.makeText(applicationContext, "Некорректные данные", Toast.LENGTH_SHORT)
            toast.show()
            return
        }
        agency.deleteRealtor(secondName)

        secondNames.remove(secondName)
        adapter.notifyDataSetChanged()
    }

    fun save(view: View) {
        val fos: FileOutputStream = this.openFileOutput("Agency", Context.MODE_PRIVATE)
        val os: ObjectOutputStream = ObjectOutputStream(fos)

        os.writeObject(agency)
        os.close()
        fos.close()
    }
}