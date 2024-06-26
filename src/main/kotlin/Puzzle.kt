data class Puzzle(
    val width: Int,
    val height: Int,
    val rowTrie: Trie,
    val columnTrie: Trie,
    val letters: List<Char> = emptyList()
) {
    fun isSolved() = letters.size == width * height
            && columns().all { isWord(it, columnTrie) }
            && rows().all { isWord(it, rowTrie) }

    fun isValid() = columns().all { columnTrie.startsWith(it.trim()) || columnTrie.search(it) }
            && rows().all { rowTrie.startsWith(it.trim()) || rowTrie.search(it) }
            && (completeRows() + completeColumns()).let { it.distinct() == it }

    fun addChar(c: Char) = this.copy(letters = letters.plus(c))

    fun validNextChars(): Set<Char> = rowTrie.children(rows().getOrElse(letters.size / width) { "" })
        .intersect(columnTrie.children(columns().getOrElse(letters.size % width) { "" }))

    private fun rows(): List<String> = letters
        .chunked(width)
        .map { it.joinToString(separator = "") }

    private fun columns(): List<String> = letters
        .withIndex()
        .groupBy(keySelector = { it.index % width }, valueTransform = { it.value })
        .map { it.value.joinToString(separator = "") }

    private fun completeRows() = rows().filter { it.length == width }

    private fun completeColumns() = columns().filter { it.length == height }

    private fun isWord(word: String, trie: Trie): Boolean = trie.search(word)

    override fun toString() = letters
        .chunked(width)
        .joinToString(separator = "\n") {
            it.joinToString(separator = "").uppercase()
        }
}
