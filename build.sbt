name := "zionomicon-exercises"
organization := "zack"
scalaVersion := "2.13.1"

lazy val config = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "1.0.9",
    )
  )
