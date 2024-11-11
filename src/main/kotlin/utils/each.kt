package utils

/**
 * Iterates over each element in the iterable and applies the given scope function to it.
 * The original iterable is returned.
 *
 * @param scope A function that takes an element of type T and performs operations on it.
 * @return The original iterable with the applied operations.
 */
inline fun <T> Iterable<T>.each(scope: T.() -> Unit): Iterable<T> = this.onEach { scope(it) }