package com.hfad.kursach.myClasses

import java.io.Serializable
import java.util.*

class Treaty(sN: String, private val sum: Int): Serializable {
	private val secondName =
		sN.replaceFirstChar {
			if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
		} // Первую букву делаем заглавной
	val getSecondName
		get() = secondName
	val getSum
		get() = sum
	var next: Treaty? = null //прямо в объекте договора будет храниться ссылка на следующий договор

	fun lastElem(): Treaty { //используется классом Realtor этого же пакета
		if (this.next == null)
			return this
		return this.next!!.lastElem()
	}

	override fun toString(): String {
		return "Фамилия: $secondName\n" +
				"Сумма сделки: $sum"
	}
}