rootProject.name = "network-time"

include("library")
file("samples").apply {
  list { file, _ -> file.isDirectory }!!
    .forEach {
      include(":$it")
      project(":$it").projectDir = toPath().resolve(it).toFile()
    }
}
