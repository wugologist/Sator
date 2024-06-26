import java.io.File

fun main() {
    val width = 5
    val height = 5

    val wordFiles = mapOf(
        3 to "src/main/resources/three.txt",
        4 to "src/main/resources/four.txt",
        5 to "src/main/resources/five.txt",
        6 to "src/main/resources/six.txt",
        7 to "src/main/resources/seven.txt"
    )

    val columnWords = Trie().apply {
        File(wordFiles.getOrElse(height) { throw IllegalArgumentException("Invalid height $height")}).useLines {
            it.forEach { word -> this.insert(word) }
        }
    }
    val rowWords = Trie().apply {
        File(wordFiles.getOrElse(width) { throw IllegalArgumentException("Invalid width $width")}).useLines {
            it.forEach { word -> this.insert(word) }
        }
    }

    benchmark(30, width, height, rowWords, columnWords)
//    print(
//        generate(
//            Puzzle(
//                width = width,
//                height = height,
//                rowTrie = rowWords,
//                columnTrie = columnWords
//            )
//        )
//    )
}
fun generate(
    puzzle: Puzzle,
    iterCallback: () -> Unit = {},
    solveCallback: () -> Unit = {}
): Puzzle? =
    if (!puzzle.isValid()) {
        null
    }
    else if (puzzle.isSolved()) {
        solveCallback()
        puzzle
    }
    else puzzle.validNextChars().shuffled().firstNotNullOfOrNull {
        iterCallback()
        generate(puzzle.addChar(it), iterCallback, solveCallback)
    }

fun benchmark(iterations: Int, width: Int, height: Int, rowWords: Trie, columnWords: Trie) {
    val counts = (0..iterations).map {
        var steps = 0
        println(generate(
            Puzzle(
                width = width,
                height = height,
                rowTrie = rowWords,
                columnTrie = columnWords
            ),
            { steps++ },
            { println(steps) }
        ))
        steps
    }
    println(counts)
    println("Average count: ${counts.average()}")
}
