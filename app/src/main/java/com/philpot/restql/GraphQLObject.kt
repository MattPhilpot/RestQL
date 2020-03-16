package com.philpot.restql

/**
 * Extention of GraphQLField responsible for rendering complex objects into a graphQL query
 */
class GraphQLObject internal constructor(private val name: String,
                                         private val variables: List<String>,
                                         private val fields: List<GraphQLField>,
                                         private val alias: String = "") : GraphQLField() {

    override fun indentRender(indent: Int): String {
        val builder = StringBuilder()
        printIndent(builder, indent)

        //set alias if needed
        if (alias.isNotBlank()) {
            builder.append("$alias: ")
        }

        builder.append(name)

        // Render params
        if (variables.isNotEmpty()) {
            builder.append("(")
            var first = true
            variables.forEach { each ->
                if (first) {
                    first = false
                } else {
                    builder.append(" ")
                }
                builder.append(each)
                builder.append(": $$each")
            }
            builder.append(")")
        }

        builder.append(" {\n")

        // Render children
        for (field in fields) {
            builder.append(field.indentRender(indent + 1))
        }
        //this is just an APOLLO thing apparently
        //builder.append(StringField("__typename").indentRender(indent + 1))
        printIndent(builder, indent)
        builder.append("}\n")

        return builder.toString()
    }

    private fun printIndent(builder: StringBuilder, indent: Int) {
        for (i in 0 until indent) {
            builder.append("  ")
        }
    }
}