package nl.rijksoverheid.mgo.framework.util.base64

class TestBase64Util : Base64Util {
  override fun encode(str: String): String = str

  override fun decode(base64Str: String): ByteArray = base64Str.toByteArray(Charsets.UTF_8)
}
