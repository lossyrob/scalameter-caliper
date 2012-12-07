scalaVersion := "2.10.0-RC3"

fork := true

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "12.0.1",
  "com.google.code.gson" % "gson" % "2.2.1",
  "com.google.code.java-allocation-instrumenter" % "java-allocation-instrumenter" % "2.0"
)

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
