package com.philpot.restql

/**
 * Base graph ql field object
 */
abstract class GraphQLField internal constructor() {
    internal abstract fun indentRender(indent: Int): String
}
