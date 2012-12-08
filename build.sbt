scalaVersion := "2.10.0-RC3"

libraryDependencies ++= Seq(
                    "com.github.axel22" %% "scalameter" % "0.2",
                    "com.google.guava" % "guava" % "12.0.1",
                    "com.google.code.gson" % "gson" % "2.2.1",
                    "com.google.code.java-allocation-instrumenter" % "java-allocation-instrumenter" % "2.0"
)

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/snapshots"

testFrameworks += new TestFramework(
    "org.scalameter.ScalaMeterFramework")
