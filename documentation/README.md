# Developer documentation


## Description
> Odoo mobile client application for Android platform

## Общая структура проекта
- [buildSrc](buildSrc/) - a module designed to store all gradle dependencies for further connection in all other modules
- [common](common/) - a module that contains functional pieces of code that can be reused in the future
- [.ci](.ci/) - encapsulation of configs for ci/cd
- [core](core/) - a module of components that are used in other modules of the project
- [.detekt](.detekt/) - configs for `detekt` on ci
- [entry](entry/) - module for input points to the application
- [feature](feature/) - a module that will describe the logic of features (for example, authorization)

## Git flow
There are several main branches

- prod - stable release branch
- develop - active development branch, checkout from prod
- Various branches of features/bugfixes (respectively by name `feature/some_description` and `bugfix/some_description`)

There will be a release at the end of each sprint:
- Topping up all the improvements in prod
- Updating github

## Agreements
- We write comments in **english**
- We divide , if possible , into **api** **impl**, to separate the methods outwards and their implementation respectively
- Naming modules via **camelCase**. For example - `buildSrc`
- Naming different implementations of `impl` via *snake_case* so that a feature
of a particular implementation can be clearly distinguished. Example:
```
    login/
        api/
        impl/
        impl_hse/
        impl_google/
```
- Documentation in api modules is very welcome, as is logging
- Package naming should be as follows: 
`odoo.miem.android.{feature/common/core}.{name of module}.{api/impl}`  
  - api/impl - optional, if present
  - Does not apply to `entry/*` modules, as it contains the base path `odoo.miem.android`
  - For example: `odoo.miem.android.core.di.impl`

## How do I add a new cradle dependency? What is the build Src module and why is it needed?
In the module [buildSrc](buildSrc/) in the file [Dependencies](../buildSrc/src/main/kotlin/consts/Dependencies.kt),
all gradle dependencies are registered, if necessary, add/update dependencies there.
More information about the module [buildSrc](buildSrc/) can be found in the corresponding [README.md ](../buildSrc/README.md)

## How to connect a module to a common DI graph?
You can read more about this in the corresponding [README](../core/di/README.md)

## What is the Json Rpc library and how to use it?
You can read more about this in the corresponding [README](../core/jsonrpc/README.md)