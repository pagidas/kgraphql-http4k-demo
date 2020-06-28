package me.pagidas.graphql

typealias Books = List<Book>
typealias Writers = List<Writer>

data class Writer(val id: Int, val name: String, val books: Books)
data class Book(val id: Int, val title: String)

object TestRepo {
    val getWriters: (Int?) -> Writers = { limit ->
        limit?.let {
            TestData.writers.take(limit)
        } ?:
        TestData.writers
    }
    val getWriterById: (Int) -> Writer = { id ->
        TestData.writers.find { it.id == id } ?: throw RuntimeException("Writer not found.")
    }
    val getBooks: (Int?) -> Books = { limit ->
        limit?.let {
            TestData.books.take(limit)
        } ?:
        TestData.books
    }
    val getBooksByWriter: (String, Int?) -> Books = { givenWriter, limit ->
        limit?.let {
            numberOfBooks -> TestData.writers.find { writer -> writer.name == givenWriter }?.books?.take(numberOfBooks)
                ?: emptyList()
        } ?:
        TestData.writers.find { writer -> writer.name == givenWriter }?.books ?: emptyList()
    }
}

object TestData {
    private val asimovBooks: Books = listOf(
        Book(0, "Foundation"),
        Book(1, "The Naked Sun"),
        Book(2, "The End of Eternity")
    )

    private val bradburyBooks: Books = listOf(
        Book(3, "Fahrenheit 451"),
        Book(4, "The Martian Chronicles"),
        Book(5, "Dandelion Wine")
    )

    private val philipDickBooks: Books = listOf(
        Book(6, "Do Androids Dream of Electric Sheep?"),
        Book(7, "The Man in the High Castle"),
        Book(8, "Ubik")
    )

    val ISSAC_ASIMOV = Writer(0, "Issac Asimov", asimovBooks)
    val RAY_BRADBURY = Writer(1, "Ray Bradbury", bradburyBooks)
    val PHILIP_K_DICK = Writer(2, "Philip K. Dick", philipDickBooks)

    val writers: Writers = listOf(
        ISSAC_ASIMOV,
        RAY_BRADBURY,
        PHILIP_K_DICK
    )

    val books: Books = listOf(
        Book(0, "Foundation"),
        Book(1, "The Naked Sun"),
        Book(2, "The End of Eternity"),
        Book(3, "Fahrenheit 451"),
        Book(4, "The Martian Chronicles"),
        Book(5, "Dandelion Wine"),
        Book(6, "Do Androids Dream of Electric Sheep?"),
        Book(7, "The Man in the High Castle"),
        Book(8, "Ubik")
    )
}
