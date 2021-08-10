/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
String getAuthString() {
  def credential = "--db-user ${USERNAME}"
  try {
    credential = credential + " --db-pwd ${PASSWORD} "
  } catch (e) {
  }
  return credential
}

String getAuthRepoString() {
  def credential = "--storage-user ${USERNAME}"
  try {
    credential = credential + " --storage-pwd ${PASSWORD} "
  } catch (e) {
  }
  return credential
}

String getCustomAuth(String userName, String passName) {
  def credential = "${userName} ${USERNAME}"
  try {
    credential = credential + " ${passName} ${PASSWORD} "
  } catch (e) {
  }
  return credential
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
  println("Credential " + id + "  exist? " + result)
  return result
}