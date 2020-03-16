package com.philpot.restql

/**
 * Central entry point for building a new GraphQL query
 */
object GraphQLBuilder {

    /**
     * Main entry point to starting a new GraphQL query
     */
    fun builder(): GraphQLOperation = GraphQLOperation()

    /**
     * First part of the GraphQL query process
     *
     * From here, you define a query operation
     */
    class GraphQLOperation internal constructor()  {
        fun operation(operationName: String): GraphQLVariables = GraphQLVariables(operationName)
    }

    /**
     * Second part of the GraphQL query process
     */
    class GraphQLVariables internal constructor(private val operationName: String) {
        private val variables = mutableMapOf<String, Any>()

        fun with(variableName: String, value: Any): GraphQLVariables {
            variables[variableName] = value
            return this
        }

        fun buildQuery(): GraphQLQuery {
            return GraphQLQuery(operationName, variables)
        }
    }

    /**
     * Main part of the graph ql query process
     *
     * From here, you can define objects, fields, etc
     */
    class GraphQLQuery internal constructor(private val operationName: String,
                                            private val variables: Map<String, Any>) {

        private val objects = mutableListOf<GraphQLObject>()

        fun withObject(objectName: String,
                       code: (GraphQLObjectBuilder.GraphQLObjectConstructor) -> GraphQLObjectBuilder.GraphQLObjectConstructor): GraphQLQuery {
            objects.add(code(GraphQLObjectBuilder.GraphQLObjectConstructor(objectName, variables.keys.toList())).build())
            return this
        }

        fun buildQuery(): String {
            val builder = StringBuilder()

            builder.append("query ${operationName}(")
            var first = true
            variables.forEach {
                //TODO fix this to account for other data types
                if (first) {
                    first = false
                } else {
                    builder.append(", ")
                }
                builder.append("$${it.key}: String!")
            }
            builder.append(") {\n")
            objects.firstOrNull()?.let {
                builder.append(it.indentRender(1))
            }
            builder.append("}\n")
            return builder.toString()
        }
    }
}

