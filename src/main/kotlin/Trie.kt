class Trie {

    data class Node(var word: String? = null, val childNodes: MutableMap<Char, Node> = mutableMapOf())

    private val root = Node()

    fun insert(word: String) {
        var currentNode = root
        word.forEach { char ->
            if (currentNode.childNodes[char] == null) {
                currentNode.childNodes[char] = Node()
            }
            currentNode = currentNode.childNodes[char]!!
        }
        currentNode.word = word
    }

    fun search(word: String): Boolean {
        var currentNode = root
        word.forEach { char ->
            if (currentNode.childNodes[char] == null) {
                return false
            }
            currentNode = currentNode.childNodes[char]!!
        }
        return currentNode.word != null
    }

    fun startsWith(word: String): Boolean {
        var currentNode = root
        word.forEach { char ->
            if (currentNode.childNodes[char] == null) {
                return false
            }
            currentNode = currentNode.childNodes[char]!!
        }
        return currentNode.word == null
    }

    fun children(word: String): Set<Char> {
        var currentNode = root
        word.forEach { char ->
            if (currentNode.childNodes[char] == null) {
                return emptySet()
            }
            currentNode = currentNode.childNodes[char]!!
        }
        return currentNode.childNodes.keys
    }
}