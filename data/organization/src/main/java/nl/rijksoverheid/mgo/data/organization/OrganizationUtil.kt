package nl.rijksoverheid.mgo.data.organization

internal fun String.normalizeText(): String =
  this
    .replace(".", "")
    .replace(",", "")
    .replace("\\s+".toRegex(), " ")
    .trim()
    .lowercase()

internal fun String.editDistance1Variants(): List<String> {
  val chars = this.toCharArray().toList()
  val length = chars.size
  val alphabet = ('a'..'z').toList()

  val variants = mutableSetOf<String>()

  // Deletions
  for (deletionIndex in 0 until length) {
    val mutableChars = chars.toMutableList()
    mutableChars.removeAt(deletionIndex)
    variants.add(mutableChars.joinToString(""))
  }

  // Substitutions
  for (substitutionIndex in 0 until length) {
    for (ch in alphabet) {
      if (ch != chars[substitutionIndex]) {
        val mutableChars = chars.toMutableList()
        mutableChars[substitutionIndex] = ch
        variants.add(mutableChars.joinToString(""))
      }
    }
  }

  // Insertions
  for (insertionIndex in 0..length) {
    for (ch in alphabet) {
      val mutableChars = chars.toMutableList()
      mutableChars.add(insertionIndex, ch)
      variants.add(mutableChars.joinToString(""))
    }
  }

  // Transpositions
  for (transpositionIndex in 0 until length - 1) {
    val mutableChars = chars.toMutableList()
    val tmp = mutableChars[transpositionIndex]
    mutableChars[transpositionIndex] = mutableChars[transpositionIndex + 1]
    mutableChars[transpositionIndex + 1] = tmp
    variants.add(mutableChars.joinToString(""))
  }

  return variants.toList()
}

fun String.toFts5Query(): String {
  val normalized = this.normalizeText()
  val tokens = normalized.split(" ")
  val terms = tokens.flatMap { it.editDistance1Variants() + it }.distinct()
  return terms.joinToString(" OR ") { "\"$it*\"" }
}
