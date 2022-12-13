import java.lang.Integer.max
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val br = System.`in`.bufferedReader()

    val pairs = ArrayList<Pair<String,String>>()
    var a = br.readLine()
    var b = ""
    while(a != null) {
        b = br.readLine()
        pairs.add(a to b)

        a = br.readLine()
        if(a != null && a.isEmpty()) a = br.readLine()
    }

    open abstract class Value(val type: Int)
    class IntValue(val value: Int) : Value(1) {
        override fun toString(): String {
            return "$value"
        }
    }
    class List(value: Value?=null) : Value(0) {
        val values = ArrayList<Value>()

        init {
            if(value != null) values.add(value)
        }

        override fun toString(): String {
            return "[${values.joinToString(separator = ",") { it.toString() }}]"
        }
    }

    fun parseList(list: MutableList<Char>): List {
        val ret = List()
        while(list.isNotEmpty()) {
            val token = list.removeFirst()
            when(token) {
                '[' -> {
                    ret.values.add(parseList(list))
                }
                ']' -> {
                    break
                }
                ',' -> continue
                else -> {
                    var a = token - '0'
                    while(list.isNotEmpty() && list.first() in '0'..'9') {
                        a *= 10
                        a += list.removeFirst() - '0'
                    }
                    ret.values.add(IntValue(a))
                }
            }
        }
        return ret
    }

    fun compare(a: List, b: List): Boolean? {
        val N = max(a.values.size, b.values.size)
        for(idx in 0 until N) {
            if(idx == a.values.size && idx < b.values.size) return true
            else if (idx < a.values.size && idx == b.values.size) return false

            val aVal = a.values[idx]
            val bVal = b.values[idx]
            if(aVal.type == 1 && bVal.type == 1) {
                val x = (aVal as IntValue).value
                val y = (bVal as IntValue).value
                if(x < y) return true
                else if(x > y) return false
            } else if(aVal.type == 1 && bVal.type == 0) {
                val res = compare(List(aVal), (bVal as List))
                if(res != null) return res
            } else if(aVal.type == 0 && bVal.type == 1) {
                val res = compare((aVal as List), List(bVal))
                if(res != null) return res
            } else if(aVal.type == 0 && bVal.type == 0) {
                val res = compare((aVal as List), (bVal as List))
                if(res != null) return res
            }
        }
        return null
    }

    var ans = 0
    var idx = 1
    for((fst, snd) in pairs) {
        val flist = parseList(fst.drop(1).dropLast(1).toMutableList())
        val slist = parseList(snd.drop(1).dropLast(1).toMutableList())

        var equal = compare(flist,slist)
        while(equal == null && flist.values.isNotEmpty() && slist.values.isNotEmpty()) equal = compare(flist,slist)

        if(equal != null && equal) ans += idx
        idx++
    }

    println(ans)
}