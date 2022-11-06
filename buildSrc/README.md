# BuildSrc модуль

## Назначение
Данный модуль предназначен для хранения зависимостей и их версий

## Описание
Сам модуль имеет следующие разделение:
- [const](buildSrc/src/main/kotlin/consts)
  - [CompileVersions](buildSrc/src/main/kotlin/consts/CompileVersions.kt) -
  нужен для выделения версии сборки проекта
  - [Dependecies](buildSrc/src/main/kotlin/consts/Dependecies.kt) -
  содержит все нужные версии и зависимости
- [conventions](buildSrc/src/main/kotlin/conventions)
  - [base](buildSrc/src/main/kotlin/conventions/base) -
  базовые конвенции, которые содержать первоначальные настройки
  - [module](buildSrc/src/main/kotlin/conventions/module) -
  конвенции, которые расширяют базовые конвенции минималлного уровня использования
  - [module_extended](buildSrc/src/main/kotlin/conventions/module_extended) - 
  специфичные конвенции, которые расширяют конвенции из [module](buildSrc/src/main/kotlin/conventions/module)

## Процесс расширения
```
base -------> module -------> module_advanced

                OR
                
base -----------------------> module_advanced
```