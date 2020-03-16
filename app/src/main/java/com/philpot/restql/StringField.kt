package com.philpot.restql

/**
 * Used to represent a 'field' in a graphQL query
 */
class StringField internal constructor(val name: String) : GraphQLField() {

    override fun indentRender(indent: Int): String {
        return "  ".repeat(indent) + name + "\n"
    }
}
