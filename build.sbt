name := "spreact"

version := "1.0"

scalaVersion := "2.11.8"

//resolvers += "Spring IO Libs" at "https://repo.spring.io/libs-release"
//libraryDependencies += "org.springframework.data" % "spring-data-gemfire" % "1.8.2.RELEASE"

libraryDependencies += "io.reactivex" % "rxjava" % "1.1.3"

libraryDependencies += "org.springframework.boot" % "spring-boot-starter-freemarker" % "1.3.3.RELEASE"
libraryDependencies += "org.springframework.boot" % "spring-boot-starter-security" % "1.3.3.RELEASE"
libraryDependencies += "org.springframework.boot" % "spring-boot-devtools" % "1.3.3.RELEASE"

libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.4.7"
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.4.7"
libraryDependencies += "com.typesafe" % "config" % "1.3.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"

libraryDependencies += "org.springframework.boot" % "spring-boot-starter-test" % "1.3.3.RELEASE" % "test"

libraryDependencies += "org.mockito" % "mockito-core" % "1.10.19" % "test"
libraryDependencies += "org.powermock" % "powermock-api-mockito" % "1.6.5" % "test"
libraryDependencies += "org.powermock" % "powermock-module-junit4" % "1.6.5" % "test"
