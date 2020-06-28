package me.pagidas.graphql

import com.apurebase.kgraphql.KGraphQL

val graphqlSchema = KGraphQL.schema {
    configure { useDefaultPrettyPrinter = true }

    query("writer") {
        resolver { id: Int ->
            TestRepo.getWriterById(id)
        }
    }
    query("writers") {
        resolver { limit: Int? ->
            TestRepo.getWriters(limit)
        }
    }
    query("books") {
        resolver { limit: Int?, writer: String? ->
            writer?.let {
                TestRepo.getBooksByWriter(writer, limit)
            } ?: TestRepo.getBooks(limit)
        }
    }

    type<Writer>()
    type<Book>()
}
