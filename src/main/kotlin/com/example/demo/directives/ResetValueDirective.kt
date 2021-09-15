package com.example.demo.directives

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsRuntimeWiring
import graphql.schema.GraphQLInputObjectField
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaDirectiveWiring
import graphql.schema.idl.SchemaDirectiveWiringEnvironment
import java.time.LocalDateTime

@DgsComponent
class ResetValueDirective : SchemaDirectiveWiring {
    companion object {
        private const val NAME = "resetOnValue"
        private const val ARGUMENT_NAME = "value"
    }

    @DgsRuntimeWiring
    fun runtimeWiring(builder: RuntimeWiring.Builder): RuntimeWiring.Builder {
        return builder.directiveWiring(ResetValueDirective())
    }

    override fun onInputObjectField(environment: SchemaDirectiveWiringEnvironment<GraphQLInputObjectField>): GraphQLInputObjectField {
        val field = environment.element
        if (field.getDirective(NAME) == null) {
            return field
        }
        val parentType = environment.fieldsContainer

        // add necessary transformation logic for the input field here

        // (!) The lambda is called on start only
        return field.transform { builder: GraphQLInputObjectField.Builder ->
            builder
                .defaultValue("Guest-${LocalDateTime.now()}")
                .build()
        }
    }
}
