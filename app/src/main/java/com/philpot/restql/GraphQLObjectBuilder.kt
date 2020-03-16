package com.philpot.restql

object GraphQLObjectBuilder {

    fun builder(name: String) = GraphQLObjectConstructor(name)

    class GraphQLObjectConstructor internal constructor(private val name: String,
                                                        private val possibleVariables: List<String> = emptyList()) {

        private val variables = mutableListOf<String>()
        private val fields = mutableListOf<GraphQLField>()
        private var alias: String = ""

        /**
         * Tells the object to use an alias name for returning the data
         */
        fun withAlias(alias: String): GraphQLObjectConstructor {
            this.alias = alias
            return this
        }

        /**
         * Tells the object to define a variable it needs to return the object properly
         */
        fun withVariable(variable: String): GraphQLObjectConstructor {
            assert(possibleVariables.contains(variable))
            variables.add(variable)
            return this
        }

        /**
         * Defines a field to be returned by the graph ql service
         */
        fun withField(name: String): GraphQLObjectConstructor {
            fields.add(StringField(name))
            return this
        }

        infix fun GraphQLObjectConstructor.with(name: String): GraphQLObjectConstructor {
            fields.add(StringField(name))
            return this
        }

        /**
         * Defines a field to be returned by the graph ql service (with an aliased name)
         *
         * note: why tf you'd need this, I don't know. It seems pretty stupid to me
         */
        fun withAliasField(alias: String, fieldName: String): GraphQLObjectConstructor {
            fields.add(StringAliasField(alias, fieldName))
            return this
        }

        /**
         * Defines an sub-object to be returned that's nested within the current object
         */
        fun withObject(name: String, code: (GraphQLObjectConstructor) -> GraphQLObjectConstructor): GraphQLObjectConstructor {
            fields.add(code(GraphQLObjectConstructor(name, possibleVariables)).build())
            return this
        }

        /**
         * Returns a GraphQLObject that is capable of building a GraphQL query
         */
        internal fun build(): GraphQLObject {
            return GraphQLObject(name, variables, fields, alias)
        }
    }
}
