package com.hfad.kursach.myClasses

import com.hfad.kursach.myClasses.Realtor
import java.io.Serializable

class Agency: Serializable {
	val realtors = mutableListOf<Realtor>()//гибкий массив
	val getRealtor
	get() = realtors

	fun addRealtor(str: String, num: String) {
		realtors.add(Realtor(str, num))
	}
	fun deleteRealtor(str: String) { //удаляем сотрудника с такой фамилией str
		realtors.removeIf { it.getSecondName == str }
	}
	fun getRealtorsSum(): Int {
		var res: Int = 0

		for (realtor in realtors) {
			res += realtor.getTreatiesSum()
		}
		return res
	}
	override fun toString(): String {//info
		var res = ""

		for (realtor in realtors) {
			res += "${realtor.toString()}\n"
		}
		return res
	}
}