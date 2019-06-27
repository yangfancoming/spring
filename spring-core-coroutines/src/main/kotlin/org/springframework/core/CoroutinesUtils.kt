

@file:JvmName("CoroutinesUtils")
package org.springframework.core

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.flow.asPublisher

import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Mono
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import kotlin.reflect.full.callSuspend
import kotlin.reflect.jvm.kotlinFunction

/**
 * Convert a [Deferred] instance to a [Mono] one.
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
internal fun <T: Any> deferredToMono(source: Deferred<T>) =
		GlobalScope.mono(Dispatchers.Unconfined) { source.await() }

/**
 * Convert a [Mono] instance to a [Deferred] one.
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
internal fun <T: Any> monoToDeferred(source: Mono<T>) =
		GlobalScope.async(Dispatchers.Unconfined) { source.awaitFirstOrNull() }

/**
 * Invoke an handler method converting suspending method to [Mono] or
 * [reactor.core.publisher.Flux] if necessary.
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
@Suppress("UNCHECKED_CAST")
@FlowPreview
internal fun invokeHandlerMethod(method: Method, bean: Any, vararg args: Any?): Any? {
	val function = method.kotlinFunction!!
	return if (function.isSuspend) {
		val mono = GlobalScope.mono(Dispatchers.Unconfined) {
			function.callSuspend(bean, *args.sliceArray(0..(args.size-2)))
					.let { if (it == Unit) null else it }
		}.onErrorMap(InvocationTargetException::class.java) { it.targetException }
		if (function.returnType.classifier == Flow::class) {
			mono.flatMapMany { (it as Flow<Any>).asPublisher() }
		}
		else {
			mono
		}
	}
	else {
		function.call(bean, *args)
	}
}
