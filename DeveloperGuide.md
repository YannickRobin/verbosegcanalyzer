# Start with Eclipse #

  * Select `com.ebizance.verbosegcanalyzer.VerboseGCAnalyzer`
    * `Arguments` :  `<path to GC log file>`
    * `Environment`
      * log4j.configuration=src/main/conf/log4j.properties
      * verbosegcanalyzer.root\_dir=src/main

# New release #

  * Commit all your changes
  * `mvn -Dusername=[svnusername] -Dpassword=[svnpassword] release:prepare`
  * `mvn release:perform`
  * Upload `VerboseGCAnalyzer/target/checkout/target`