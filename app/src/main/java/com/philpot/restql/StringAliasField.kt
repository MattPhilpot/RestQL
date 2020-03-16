package com.philpot.restql

/**
 * Used to represent a 'field' in a graphQL query that needs an alias
 */
class StringAliasField internal constructor(private val alias: String,
                                            private val name: String) : GraphQLField() {

    override fun indentRender(indent: Int): String {
        return "  ".repeat(indent) + "$alias: $name\n"
    }
}
