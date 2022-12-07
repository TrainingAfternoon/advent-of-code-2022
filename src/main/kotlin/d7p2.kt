import java.lang.Long.min
import java.util.*

fun main() {
    val br = System.`in`.bufferedReader()

    data class Directory(val name: String, val parent: Directory?) {
        val children = HashMap<String,Directory>()
        var size = 0L
    }

    val lines = LinkedList<String>()
    for(line in br.lines()) lines.addLast(line)
    lines.poll()

    val fileRoot = Directory("/", null)
    fun parse(root: Directory, lines: LinkedList<String>) {
        if(lines.isNotEmpty()) {
            val tokens = lines.poll().split(" ")
            when(tokens[1]) {
                "cd" -> {
                    if(tokens[2] == "..") {
                        parse(root.parent!!, lines)
                    } else if(tokens[2] == "/") {
                        parse(fileRoot, lines)
                    } else {
                        val nextDir = root.children.getOrDefault(tokens[2], Directory(tokens[2], root))
                        parse(nextDir, lines)
                        root.size += nextDir.size
                    }
                }
                "ls" -> {
                    while(lines.isNotEmpty() && lines.peek().first() != '$') {
                        val lsTokens = lines.poll().split(" ")
                        when(lsTokens[0]) {
                            "dir" -> {
                                root.children[lsTokens[1]] = Directory(lsTokens[1], root)
                            }
                            else -> {
                                root.size += lsTokens[0].toLong()
                            }
                        }
                    }
                }
            }
            parse(root, lines)
        }
    }
    parse(fileRoot, lines)

    val deficit = 30000000L - (70000000L - fileRoot.size)

    fun dfs(root: Directory): Long {
        var ret = root.size

        if(ret == deficit) return ret
        else if(ret > deficit) {
            for((name, child) in root.children) {
                ret = min(ret, dfs(child))
            }
        } else {
            ret = Long.MAX_VALUE
        }

        return ret
    }

    if(deficit > 0) println(dfs(fileRoot))
    else println(0)
}