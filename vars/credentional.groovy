String getAuthString() {
  def credentional = "--db-user ${USERNAME}"
  try {
    credentional = credentional + " --db-pwd ${PASSWORD} "
  } catch (e) {
  }
  return credentional
}

String getAuthRepoString() {
  def credentional = "--storage-user ${USERNAME}"
  try {
    credentional = credentional + " --storage-pwd ${PASSWORD} "
  } catch (e) {
  }
  return credentional
}

String getCustomAuth(String userName, String passName) {
  def credentional = "${userName} ${USERNAME}"
  try {
    credentional = credentional + " ${passName} ${PASSWORD} "
  } catch (e) {
  }
  return credentional
}


boolean exist(String id) {
  boolean result = false
  try {
    withCredentials([usernamePassword(credentialsId: id, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      result = true;
    }
  } catch (e) {
    println(e.getMessage())
    result = false
  }
  println("Credentional " + id + "  exist? " + result)
  return result
}