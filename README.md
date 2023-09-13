# javaNames

A utility to extract user-defined names from a directory tree of Java code
and examine them for suitability according to my own interpretation of
Clean Coding conventions.

* Names should consist of one or more phrases separated either by underscores (_)
  or by CamelCase conventions.
* Each such "phrase" must be either
    * A word recognized in an English (US) spell check dictionary, or
    * an all-uppercase acronym, or
    * a single letter

For example, when run on it's own source code:

```
java -jar build/libs/*.jar src/main/java
```

The output produced is

```
Found 4 questionable names:
  DECLARER
  args
  attr
  str
```