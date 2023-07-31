# library-demo-a
Demo of consumable library for SDK and version catalog demo

### How to build
Make sure your version catalog is available either in Maven local or in your corporate repo first of all (just publish it).

```shell
./gradlew build
```

### How to publish
For local development and testing purposes:
```shell
./gradlew publishToMavenLocal
```
or (for production)
```shell
./gradlew publish
```