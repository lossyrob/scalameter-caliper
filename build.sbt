scalaVersion := "2.10.0-RC3"

fork in run := true

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "12.0.1",
  "com.google.code.gson" % "gson" % "2.2.1",
  "com.google.code.java-allocation-instrumenter" % "java-allocation-instrumenter" % "2.0",
  "com.google.code.findbugs" % "jsr305" % "1.3.9",
  "joda-time" % "joda-time" % "2.1",
  "jfree" % "jfreechart" % "1.0.12",
  "org.apache.commons" % "commons-math3" % "3.0",
  "org.scala-tools.testing" % "test-interface" % "0.5"
)

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
