fun main() {
    val br = System.`in`.bufferedReader()

    data class Pos(val x: Int, val y: Int)
    val visible = HashSet<Pos>()

    val lines = ArrayList<String>()
    for(line in br.lines()) lines.add(line)

    //left --> right
    for(rowIdx in lines.indices) {
        var max = lines[rowIdx][0].toInt()
        visible.add(Pos(rowIdx, 0))
        for(colIdx in lines[rowIdx].indices) {
            val p = Pos(colIdx, rowIdx)
            val curr = lines[rowIdx][colIdx].toInt()
            if(curr > max) {
                max = curr
                visible.add(p)
            }
        }
    }

    //up --> down
    for(colIdx in lines[0].indices) {
        var max = lines[0][colIdx].toInt()
        visible.add(Pos(0, colIdx))
        for(rowIdx in lines.indices) {
            val p = Pos(colIdx, rowIdx)
            val curr = lines[rowIdx][colIdx].toInt()
            if(curr > max) {
                max = curr
                visible.add(p)
            }
        }
    }

    //right --> left
    for(rowIdx in lines.indices) {
        var max = lines[rowIdx][lines[rowIdx].lastIndex].toInt()
        visible.add(Pos(rowIdx, lines[rowIdx].lastIndex))
        for(colIdx in lines[rowIdx].indices.reversed()) {
            val p = Pos(colIdx, rowIdx)
            val curr = lines[rowIdx][colIdx].toInt()
            if(curr > max) {
                max = curr
                visible.add(p)
            }
        }
    }

    //Down --> up
    for(colIdx in lines[0].indices) {
        var max = lines[lines.lastIndex][colIdx].toInt()
        visible.add(Pos(lines.lastIndex, colIdx))
        for(rowIdx in lines.indices.reversed()) {
            val p = Pos(colIdx, rowIdx)
            val curr = lines[rowIdx][colIdx].toInt()
            if(curr > max) {
                max = curr
                visible.add(p)
            }
        }
    }

    println(visible.size)
}