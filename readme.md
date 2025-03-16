# Custom Content API

The Custom content api allows you to more easily create custom content.

## Installation

### Maven:

- Run this command. ([Explanation](https://maven.apache.org/general.html#I_have_a_jar_that_I_want_to_put_into_my_local_repository._How_can_I_copy_it_in.3F))<p>
`mvn install:install-file -Dfile=<path-to-file> -DgroupId=carslo.mods.custom_content_api -DartifactId=custom_content_api -Dversion=<version> -Dpackaging=jar -DgeneratePom=true`

- Then add the following to your pom.xml
```
<dependency>
  <groupId>carslo.mods.custom_content_api</groupId>
  <artifactId>custom_content_api</artifactId>
  <version>0.1.0-ALPHA</version>
</dependency>
```