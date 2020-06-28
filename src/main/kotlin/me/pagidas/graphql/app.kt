package me.pagidas.graphql

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.http4k.lens.Query
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

const val PORT = 8000
const val HOST = "localhost"
val APP_URI = Uri.of("http://$HOST:$PORT")

val route = {
    fun handler(): HttpHandler = { req: Request ->
        val queryParamLens = Query.string().required("query")
        val query = queryParamLens(req)

        /**
         * The GraphQL schema is declared in [graphqlSchema] under file 'schema'.
         * This is where the resolvers are implemented to handle queries.
         */
        val resp = graphqlSchema.runCatching { executeBlocking(query) }
            .fold(
                onSuccess = { it },
                onFailure = {
                    """
                    {
                      "errors": ${it.message}
                    }
                    """.trimIndent()
                }
            )

        Response(OK).body(resp)
    }

    routes("/graphql" bind GET to handler())
}

val app = route().asServer(Jetty(PORT))

fun main() {
    app.start()
    testApp()
}