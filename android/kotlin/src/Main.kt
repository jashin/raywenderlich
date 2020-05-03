//fun main() {
//    var myPets = mutableMapOf<String, String>(
//        "coco" to "Rabbit",
//        "erica" to "Girl"
//    )
//
//    for (pet in myPets) {
//        println(pet)
//    }
//
//    myPets.put("testa", "aaaa")
//
//    myPets["testb"] = "bbbb"
//
//    for ((s1, s2) in myPets) {
//        println("my pet $s1 is a $s2")
//    }

//    var things = mutableSetOf<String>("keyboard", "mouse")
//
//    println(things)
//
//    things.add("phone")
//    println(things)
//
//    for(i in 1..10) println(i)
//    for(i in 1 until 10) println(i)
//
//    fun parseCoordinate(coordinate: String) : Pair<Int, Int> {
//        var arrayCoordinate = coordinate.split(",")
//        var lat = arrayCoordinate[0].toInt()
//        var long = arrayCoordinate[1].toInt()
//
//        return Pair(lat, long)
//    }
//
//    println(parseCoordinate("1,1"))

//    fun validatePassword(password: String) = password.length >= 10
//
//    fun validateString(input: String?, validator: (String) -> Boolean) =
//        if (input == null || input.isBlank()) {
//            false
//        } else {
//            validator(input)
//        }
//
//   var a = 1000 + 10050
//    println(a)
//}

// P01E05 Challenge forEach & map - Starter

//// ----------------Starter Code----------------------
//class Student(val name: String, var grade: Int, var pet: String?, var libraryBooks: List<String>) {
//    fun getPassStatus(lowestPass: Int = 50) {
//        grade >= lowestPass
//    }
//
//    fun earnExtraCredit() {
//        grade += 10
//    }
//}
//
//val chris = Student(name = "Chris", grade = 49, pet = "Mango",
//    libraryBooks = listOf("The Book of Atrus", "Living by the Code", "Mastering Git"))
//val sam = Student(name = "Sam", grade = 99, pet = null,
//    libraryBooks = listOf("Mastering Git"))
//val catie = Student(name = "Catie", grade = 75, pet = "Ozma",
//    libraryBooks = listOf("Hogfather", "Good Omens"))
//val andrea = Student(name = "Andrea", grade = 88, pet = "Kitten",
//    libraryBooks = listOf("Dare to Lead", "The Warrior's Apprentice"))
//
//val students = arrayOf(chris, sam, catie, andrea)
////// ----------------Starter Code----------------------
//
//fun main() {
//
///*:
// Challenge 1 - `forEach` & `map`
// There are two `for` loops below.
// Rewrite one of them using `forEach` and the other with `map`
//*/
//
//    students.forEach { println(it.grade) }
//    for (student in students) {
//        student.earnExtraCredit()
//    }
//
//    students.forEach { println(it.grade) }
//
//    students.forEach { it.earnExtraCredit() }
//
//    students.forEach { println(it.grade) }
//    val classLibraryBooks: MutableList<List<String>> = mutableListOf()
//    for (student in students) {
//        classLibraryBooks.add(student.libraryBooks)
//    }
//    println(classLibraryBooks)
//    val books = students.map { it.libraryBooks }
//    println(books)
//
///*:
//  Challenge 2 - mapNotNull
//  Create a list of student pets
//*/
//    println(students.mapNotNull { it.pet  })
//}

data class Grade (val courseName: String, val letter: Char, val credits: Double)

open class Person(val firstName: String, val lastName: String) {
    fun fullName() = "$firstName $lastName"
}

class Student(firstName: String, lastName: String)
    : Person(firstName, lastName) {
    var grades: MutableList<Grade> = mutableListOf<Grade>()
    fun recordGrade(grade: Grade) {
        grades.add(grade)
    }

    constructor(firstName: String, lastName: String, grades: MutableList<Grade>) {
        this.ff
    }
}