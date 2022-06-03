package com.hfad.kursach.myClasses

import java.io.Serializable
import java.util.*

class Realtor(sN: String, private val number: String): Serializable {
	private val secondName =
		sN.replaceFirstChar {
			if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
		}
	val getSecondName
		get() = secondName
	val getNumber
		get() = number
	private var treatiesRoot: Treaty? = null//первый договор
	val getTreatiesRoot
		get() = treatiesRoot

	fun addTreaty(str: String, sum: Int) {//добавляем договор в конец
		if (treatiesRoot == null) {
			treatiesRoot = Treaty(str, sum)
			return
		}
		treatiesRoot?.lastElem()?.let {
			it.next = Treaty(str, sum)
		}
	}

	fun getTreaty(): Treaty? { //получает следующий элемент в порядке очереди и удаляет его из списка
		val res: Treaty? = treatiesRoot//получаем первый элемент
		treatiesRoot = treatiesRoot?.next//сдвигаем очередь на один элемент
		return res//возвращаем элемент
	}

	fun findTreaty(secondName: String): Treaty? {//возвращает договор с конкретной фамилией
		var currentTreaty: Treaty? = treatiesRoot

		while (currentTreaty != null) {
			if (currentTreaty.getSecondName == secondName)
				return currentTreaty
			currentTreaty = currentTreaty.next
		}
		return currentTreaty
	}

	fun getTreatiesSum(): Int {//sum
		var res: Int = 0
		var currentTreaty: Treaty? = treatiesRoot

		while (currentTreaty != null) {
			res += currentTreaty.getSum
			currentTreaty = currentTreaty.next
		}
		return res
	}

	fun deleteTreaty(secondName: String): Treaty? {//delete
		var currentTreaty: Treaty? = treatiesRoot
		var prevTreaty: Treaty? = treatiesRoot

		while (currentTreaty != null) {
			if (currentTreaty.getSecondName == secondName) {
				break
			}
			prevTreaty = currentTreaty
			currentTreaty = currentTreaty.next
		}
		if (prevTreaty == currentTreaty)
			treatiesRoot = null
		prevTreaty?.next = currentTreaty?.next
		return currentTreaty
	}

	override fun toString(): String {//info
		var res = ""

		res += "Фамилия: $secondName\n" +
				"Номер телефона: $number\n" +
				"Сумма сделок: ${getTreatiesSum()}\n"
		return res
	}
}