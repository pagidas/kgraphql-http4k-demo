package me.pagidas.graphql

import org.http4k.client.ApacheClient
import org.http4k.core.Method.GET
import org.http4k.core.Request

fun testApp() {
    val http = ApacheClient()

    http(
        Request(GET, "$APP_URI/graphql")
        .query("query",
            """
            {
              writers { id, name }
            }
            """.trimIndent()
        )
    ).apply {
        println("---------------------")
        println("---GET ALL WRITERS---")
        println("---------------------")
        println(bodyString())
    }

    http(
        Request(GET, "$APP_URI/graphql")
        .query("query",
            """
            {
              writer(id: 2) {
                name
                books {
                  title
                }
              }
            }
            """.trimIndent())
    ).apply {
        println("------------------------------------")
        println("---GET WRITER AND HIS BOOKS BY ID---")
        println("------------------------------------")
        println(bodyString())
    }

    http(
        Request(GET, "$APP_URI/graphql")
        .query("query",
            """
            {
              books(limit: 4) { id, title }
            }
            """.trimIndent())
    ).apply {
        println("-----------------")
        println("---GET 4 BOOKS---")
        println("-----------------")
        println(bodyString())
    }

    http(
        Request(GET, "$APP_URI/graphql")
            .query("query",
            """
            {
              books(limit: 1, writer: "Philip K. Dick") {
                id,
                title
              }
            }
            """.trimIndent())
    ).apply {
        println("-------------------------")
        println("---GET BOOKS BY WRITER---")
        println("-------------------------")
        println(bodyString())
    }
}