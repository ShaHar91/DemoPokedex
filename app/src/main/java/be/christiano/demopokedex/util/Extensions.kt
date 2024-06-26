package be.christiano.demopokedex.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed


/**
 * Like [then] but the [other] modifier is only applied if the condition is true.
 */
fun Modifier.thenIf(condition: Boolean, other: Modifier): Modifier {
    return this.thenIf(condition) { other }
}

/**
 * Like [thenIf] but with an inverted condition.
 */
fun Modifier.thenUnless(condition: Boolean, other: Modifier): Modifier {
    return this.thenUnless(condition) { other }
}

/**
 * Like the version of [thenIf] which takes in a modifier directly, but it produces that modifier lazily.
 *
 * This is occasionally useful if you have a Modifier that is expensive to create, e.g. it takes some complicated
 * parameters you need to allocate which is a waste if the condition is false.
 */
inline fun Modifier.thenIf(condition: Boolean, lazyProduce: () -> Modifier): Modifier {
    return this.then(if (condition) lazyProduce() else Modifier)
}

/**
 * Like the version of [thenUnless] which takes in a modifier directly, but it produces that modifier lazily.
 *
 * This is occasionally useful if you have a Modifier that is expensive to create, e.g. it takes some complicated
 * parameters you need to allocate which is a waste if the condition is true.
 */
inline fun Modifier.thenUnless(condition: Boolean, lazyProduce: () -> Modifier): Modifier {
    return this.thenIf(!condition, lazyProduce)
}

fun Modifier.conditional(condition: Boolean, modifier: @Composable Modifier.() -> Modifier): Modifier = composed {
    if (condition) {
        this.then(modifier())
    } else {
        this
    }
}
