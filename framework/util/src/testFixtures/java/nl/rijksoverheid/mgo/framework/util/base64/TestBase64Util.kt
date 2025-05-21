package nl.rijksoverheid.mgo.framework.util.base64

class TestBase64Util : Base64Util {
  override fun encode(str: String): String {
    return str
  }

  override fun decode(base64Str: String): String {
    return base64Str
  }
}
