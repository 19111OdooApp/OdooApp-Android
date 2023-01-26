# DI module in the core layer

## Назначение
 - Providing the necessary interfaces, extension method, classes for the correct
operation of **Dagger 2** throughout the project

## Description
- The DI module is divided into 2 parts - **api** and **impl**:
  - **api** - a module containing the **Api** interface, which is needed to combine all the apis into
one **Map** (see **ApiResolver**)
  - **impl** - a module that encapsulates extensions and classes to get
the necessary implementation of the **Api**

## How do I connect a new module to a common DI graph?
- Your module should be divided into **api** and **impl**
- In both modules we make a folder **di**
- Then consider each module:
  - **api**
    - Contains interfaces of what it will hold (usually stored in the root).
For example, **SomeOdooFeature**
    - The **di** folder will store **SomeOdooFeatureApi**, which **
must** be the heir of **Api**, so that later it can be accessed via **getApi()**
  - **impl**
    - Usually, the implementation of the **SomeOdooFeature** interface will be stored in the root as
**SomeOdooFeatureImpl**
    - 3 classes/interfaces will be stored in the **di** folder:
      - **SomeOdooFeatureApiProviderModule** 
        - **Dagger** a module that provides our implementation of **SomeOdooFeatureApi** in the general DI graph. 
        - Do not forget to add annotations in *@Provides* methods *@IntoMap* to add to the general
**Map** at **ApiResolver** and *@ApiKey(SomeStarFeatureApi::class)* to provide the necessary key under which
the implementation in **Map** will be stored.
        - **BE SURE TO** add **SomeOdooFeatureApiProviderModule** to **OdooAppComponent** in the *modules* section,
otherwise the new **SomeOdooFeatureApi** will not be added to the general **Map**
      - **SomeOdooFeatureComponent**
        - **Dagger** a component that implements the **SomeOdooFeatureApi interface**
        - As a regular **Dagger** component, it can contain *modules* (**SomeOdooFeatureModule**, for example),
which conducts the necessary implementations of **SomeOdooFeatureImpl** and *dependencies* from which envy. Most often these
will be **Api**, which can be easily accessed via **getApi()**
        - **SomeOdooFeatureModule** - **Dagger** module that provides **SomeOdooFeatureImpl**