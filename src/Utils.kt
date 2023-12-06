import kotlinx.coroutines.*
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.max
import kotlin.math.min


/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("inputs/$name.txt").readLines()

/**
 * Reads lines from the given input txt file.
 */
fun readExampleInput(name: String) = Path("inputs/sample/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
fun <E> MutableList<E>.push(c: E) {
    this.add(0, c)
}

fun <E> MutableList<E>.pop(): E {
    return this.removeAt(0)
}

fun <E> MutableList<E>.peek(): E {
    return this.first()
}

data class MutablePair<T, O>(var first: T, var second: O)

fun <A,B> Pair<A,B>.toMutablePair() = MutablePair(first, second)

fun <B> MutablePair<Int,B>.incFirst(amount: Int = 1): Unit {
    first += amount
}

fun String.toIntRange(delim: String = ".."): IntRange {
    val split = this.split(delim)
    return split.first().toInt()..split.last().toInt()
}

operator fun <R> (() -> R).times(paddingSize: Int): List<R> {
    val value = this()
    return MutableList(paddingSize) { value }
}

operator fun IntRange.contains(intRange: IntRange): Boolean {
    return first <= intRange.first && intRange.last <= last
}

operator fun <E> List<MutableList<E>>.set(pair: Pair<Int, Int>, value: E) {
    this[pair.first][pair.second] = value
}

operator fun <K, V> MutableMap<K, MutableMap<K, V>>.get(pair: Pair<K, K>): V? {
    return this[pair.first]?.get(pair.second)
}

operator fun <K, V> MutableMap<K, MutableMap<K, V>>.set(pair: Pair<K, K>, value: V) {
    this.getOrPut(pair.first) {
        ConcurrentHashMap()
    }[pair.second] = value
}

operator fun <K, V> ConcurrentHashMap<K, ConcurrentHashMap<K, V>>.get(pair: Pair<K, K>): V? {
    return this[pair.first]?.get(pair.second)
}

operator fun <K, V> ConcurrentHashMap<K, ConcurrentHashMap<K, V>>.set(pair: Pair<K, K>, value: V) {
    this.getOrPut(pair.first) {
        ConcurrentHashMap()
    }[pair.second] = value
}

operator fun <E> List<List<E>>.get(pair: Pair<Int, Int>): E {
    return this[pair.first][pair.second]
}

fun <E> List<List<E>>.getOrNull(pair: Pair<Int, Int>): E? {
    return this.getOrNull(pair.first)?.getOrNull(pair.second)
}

open class Node<E>(val value: E) {
    var visited = false
    var neighbors = listOf<Node<E>>()
}


fun <E> List<E>.toPair(): Pair<E, E> {
    return first() to last()
}

val <A, B> Pair<A, B>.reversed: Pair<B, A>
    get() = second to first

suspend fun <K, V> MutableMap<K, V>.parallelForEach(block: suspend (Map.Entry<K, V>) -> Unit) {

    val jobsToScopes = this.map {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch { block(it) } to scope
    }
    val jobs: List<Job> = jobsToScopes.map { it.first }
    val scopes = jobsToScopes.map { it.second }
    jobs.joinAll()
    scopes.forEach(CoroutineScope::cancel)
}

suspend fun <K, V, R> Map<K, V>.parallelMap(block: suspend (Map.Entry<K, V>) -> R): List<R> {
    println("starting ${this.size} jobs")
    val totalJobs = this.size
    var jobId = 1
    val jobsToScopes = this.map {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.async {
            val myId = jobId++
            block(it).also { println(myId) }
        } to scope

    }
    val jobs = jobsToScopes.map { it.first }
    val results = jobs.awaitAll()
    println("jobs complete")
    jobsToScopes.forEach { it.second.cancel() }
    return results
}

fun Char.toNumericValue(): Int {
    if (!isDigit()) error("can't convert non-digits")
    return minus('0')
}

val englishDigits = mapOf(
    "zero" to 0,
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

fun String.toDigit(): String? {
    return if (length == 1) {
        this
    } else {
        englishDigits[this]?.toString()
    }
}

operator fun LongRange.plus(value: Long): LongRange {
    return (first + value)..(last + value)
}

operator fun LongRange.contains(other: LongRange): Boolean {
    return first <= other.first && last >= other.last
}

fun LongRange.intersect(other: LongRange): LongRange {
    return if (other.first > last || first > other.last) {
        LongRange.EMPTY
    } else {
        max(first, other.first)..min(last, other.last)
    }
}

operator fun LongRange.minus(other: LongRange): List<LongRange> {
    if (this == other) return emptyList()
    if (isEmpty()) return emptyList()
    if (other.isEmpty()) return listOf(this)
    return listOf(
        first..<other.first,
        other.endExclusive..last
    ).filter(LongRange::isNotEmpty)
}

fun LongRange.isNotEmpty(): Boolean {
    return !isEmpty()
}