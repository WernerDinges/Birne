package utils

inline fun <T> Iterable<T>.each(scope: T.() -> Unit): Iterable<T> = this.onEach { scope(it) }