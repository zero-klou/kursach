package com.hfad.kursach

import com.hfad.kursach.myClasses.Agency

fun main() {
	//проверка агенства
	val agency = Agency()

	println("Adding realtor Glukhov and Badaev in Agency...")
	agency.addRealtor("Glukhov", "+79959416229")
	agency.addRealtor("Badaev", "+79127532435")
	println(agency)

	println("Deleting realtor badaev out of Agency")
	agency.deleteRealtor("Badaev")
	println(agency)

	println("Trying to delete Badaev again...")
	agency.deleteRealtor("Badaev")
	println(agency)
	println("\n")
	//Проверка Класса Realtor и Treaty
	agency.addRealtor("Badaev", "+79127532435")
	println(agency)

	println("Adding treaties to relators...")
	agency.getRealtor[0].addTreaty("Garaev", 30000)
	agency.getRealtor[0].addTreaty("Mazov", 25000)
	agency.getRealtor[1].addTreaty("Vasilyeva", 20000)
	agency.getRealtor[1].addTreaty("Gerasymov", 35000)
	println("Finding treaties")
	println(agency.getRealtor[0].findTreaty("Garaev"))
	println(agency.getRealtor[0].findTreaty("Mazov"))
	println(agency.getRealtor[1].findTreaty("Vasilyeva"))
	println(agency.getRealtor[1].findTreaty("Gerasymov"))
	println(agency.getRealtor[0].findTreaty("Abrakadabra"))

	println("\nGetting treaty...")
	println(agency.getRealtor[0].getTreaty())
	println("Checking for it in treaties...")
	println(agency.getRealtor[0].findTreaty("Garaev"))

	println("\nCalculating treaties sum...")
	println("Glukhov - ${agency.getRealtor[0].getTreatiesSum()}")
	println("Badaev - ${agency.getRealtor[1].getTreatiesSum()}")

	println("\nDeleting treaty...")
	println(agency.getRealtor[1].deleteTreaty("Gerasymov"))
	println(agency.getRealtor[1].findTreaty("Gerasymov"))
	println(agency.getRealtor[1].deleteTreaty("Abrakadabra"))

	println(agency.getRealtor[0])
	println(agency.getRealtor[1])
}