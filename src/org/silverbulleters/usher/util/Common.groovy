package org.silverbulleters.usher.util

import com.cloudbees.groovy.cps.NonCPS

class Common {

  @NonCPS
  static String getLibraryVersion() {
    return getClass().getPackage().getImplementationVersion()
  }

}
