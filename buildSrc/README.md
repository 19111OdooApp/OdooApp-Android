# BuildSrc модуль

## Appointment
This module is designed to store dependencies and their versions

## Описание
The module itself has the following divisions:
- [const](buildSrc/src/main/kotlin/consts)
  - [CompileVersions](buildSrc/src/main/kotlin/consts/CompileVersions.kt) -
  needed to highlight the build version of the project
  - [Dependecies](buildSrc/src/main/kotlin/consts/Dependecies.kt) -
  contains all the necessary versions and dependencies
- [conventions](buildSrc/src/main/kotlin/conventions)
  - [base](buildSrc/src/main/kotlin/conventions/base) -
  basic conventions that contain the initial settings
  - [module](buildSrc/src/main/kotlin/conventions/module) -
  conventions that extend the basic conventions of the minimum level of use
  - [module_extended](buildSrc/src/main/kotlin/conventions/module_extended) -
    specific conventions that extend conventions from [module](buildSrc/src/main/kotlin/conventions/module)

## The expansion process
```
base -------> module -------> module_advanced

                OR
                
base -----------------------> module_advanced
```