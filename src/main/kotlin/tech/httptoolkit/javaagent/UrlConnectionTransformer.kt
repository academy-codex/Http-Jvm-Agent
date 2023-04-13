package tech.httptoolkit.javaagent

import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.asm.Advice
import net.bytebuddy.description.method.MethodDescription
import net.bytebuddy.dynamic.DynamicType
import net.bytebuddy.matcher.ElementMatchers.*
import tech.httptoolkit.javaagent.advice.OverrideUrlConnectionProxyAdvice

// We override URL.openConnection() so that even if a proxy setting is passed explicitly, it's
// overridden and ignored (for HTTP(S) traffic only) so all such traffic goes to our proxy.
class UrlConnectionTransformer(logger: TransformationLogger): MatchingAgentTransformer(logger) {
    override fun register(builder: AgentBuilder): AgentBuilder {
        return builder
            .type(
                named("java.net.URL")
            ).transform(this)
    }

    override fun transform(builder: DynamicType.Builder<*>, loadAdvice: (String) -> Advice): DynamicType.Builder<*> {
        return builder
            .visit(loadAdvice("tech.httptoolkit.javaagent.advice.OverrideUrlConnectionProxyAdvice")
                .on(
                    hasMethodName("getHost")))
    }
}