package cinema

const val SMALL_CINEMA = 60
const val NORMAL_PRICE = 10
const val LOW_PRICE = 8

fun printCinema(dataSeats: Array<Array<Boolean>>) {
    println("\nCinema: ")
    print(' ')

    for (num1 in 1..dataSeats.first().size) {
        print(" $num1")
    }
    println()

    for (num2 in 1..dataSeats.size) {
        print(num2)
        for (el in dataSeats[num2-1]) {
            if (el == true) print(" B") else print(" S")
        }
        println()
    }
}

fun ticketBuy(cinemaData: Array<Array<Boolean>>, rows: Int, rowNums: Int) {
    println("\nEnter a row number:")
    val buyRow = readln().toInt()
    println("Enter a seat number in that row:")
    val buyRowNum = readln().toInt()

    if (buyRow > rows || buyRow < 0 || buyRowNum > rowNums || buyRowNum < 0) {
        throw Exception("\nWrong input!")
    } else if (cinemaData[buyRow - 1][buyRowNum - 1] == true) {
        throw Exception("\nThat ticket has already been purchased!")
    }

    val price: Int = if(cinemaData.size * cinemaData.first().size > SMALL_CINEMA) {
        if(buyRow <= cinemaData.size / 2) NORMAL_PRICE else LOW_PRICE
    } else NORMAL_PRICE

    println("\nTicket price: \$$price")

    cinemaData[buyRow-1][buyRowNum-1] = true
}

fun printStats(cinemaData: Array<Array<Boolean>>, rows: Int, numInRow: Int) {
    var lowPrice = 0
    var normalPrice = 0

    val isBigCinema = if (rows * numInRow > SMALL_CINEMA) true else false

    for (i in 1..rows) {
        for (j in 1..numInRow) {
            if (cinemaData[i - 1][j - 1] == true) {
                if (isBigCinema) {
                    if (i <= cinemaData.size / 2) normalPrice++ else lowPrice++
                } else normalPrice++
            }
        }
    }

    val percentageTickets: Double = (lowPrice + normalPrice)*100.toDouble() / (rows * numInRow)
    val currentIncome = lowPrice * LOW_PRICE + normalPrice * NORMAL_PRICE
    val totalIncome = if (isBigCinema) {
        (rows - rows / 2) * numInRow * LOW_PRICE + rows / 2 * numInRow * NORMAL_PRICE
    } else rows * numInRow * NORMAL_PRICE

    val format = "%.2f"

    println("\nNumber of purchased tickets: ${lowPrice+normalPrice}")
    println("Percentage: ${String.format(format, percentageTickets)}%")
    println("Current income: \$$currentIncome")
    println("Total income: \$$totalIncome")
}


fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsRow = readln().toInt()

    val seatsData = Array<Array<Boolean>>(rows) { Array<Boolean>(seatsRow) { false } }

    while (true) {
        println()
        println(
            """
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
            """.trimIndent()
        )
        var checker: Boolean
        val userIn: Int = readln().toInt()
        do {
            checker = false
            try {
                when (userIn) {
                    1 -> printCinema(seatsData)
                    2 -> ticketBuy(seatsData, rows, seatsRow)
                    3 -> printStats(seatsData, rows, seatsRow)
                    0 -> return
                    else -> continue
                }
            } catch (e: Exception) {
                println(e.message)
                checker = true
            }
        } while(checker)
    }
}
