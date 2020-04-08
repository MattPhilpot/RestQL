# RestQL
GraphQL query builder designed to allow you to break away from options like Apollo and instead use a regular OkHttp/Retrofit configuration with GraphQL endpoints

TODO - I'd like to change the builder style to use infix functions to make it more readable, breaking away from standard builder notation.

An example of how this would be used:

```kotlin
private fun buildGraphQLQuery(queryId: String): CourseService.QueryDTO {
  val builder = GraphQLBuilder.builder()
    .operation("doTheOperation") //operation name
    .with("queryId", queryId) //add parameters here
    .buildQuery()
    .withObject("courseOverview") {
      it.withVariable("queryId") //choose from variables you added earlier here
        .withField("id")
        .withField("title")
        .withObject("metadata") { metadata ->
          metadata.withField("metadataId") //embedded objects use lambda expressions to fill out
            .withField("metadataTitle")
            .withField("updated")
        }
        .withObject("extraDetail") { extra ->
          extra.withField("id")
            .withField("title")
            .withObject("extraExtraDetail") { detail -> //embed objects in objects
              detail.withAlias("detail") //give the embedded object an alias
                .withField("id")
                .withAliasField("detailId", "id") //use an alias for a normal field name
                .withField("title")
            }
        }
    }

  return CourseService.QueryDTO(builder.operationName, CourseService.QueryVariables(courseId), builder.buildQuery())
}
```
