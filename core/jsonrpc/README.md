# Json Rpc

## Description
This module is a Retrofit implementation of a similar add-on on Okhttp. It consists of 4 submodules:
- [base](core/jsonrpc/base) - contains protocols, base interfaces for implementation and errors
- [core](core/jsonrpc/core) - special module, for external consumers, so **if you want to use this library, just include it**
- [engine](core/jsonrpc/engine) - main module of library, which contain all implementations of [base](core/jsonrpc/base)

## Details about creating request
So, in the basic scenario, you will need to create the following request body:
```
{
  'jsonrpc': '2.0',
  'id': 1,
  'method': 'your_method',
  'params': {
    'model': 'your_model',
    'method': 'your_model_method',
    'args': [
      [('filter', '=', 'your_filter')],
      ['your_args']
    ],
    'kwargs': {
      'your_kwargs_key' : 'your_kwargs_value'
    }
  }
}
```

Let's go by step:
- `jsonrpc` - will always (99%) `2.0`, that is why this value is default
- `id` - this value will generate engine, therefore you do not need to set it
- `method` - method of execution (default value is `call`)
- `params` - parameters by which the request will be executed:
  - `model` - the model you are referring to (for example, `res.partner`)
  - `method` - method **IN MODEL** (for example, `search_read`)
  - `args` - additional arguments for filtering out your request. For example, if you want to have a results only with filed `active=true` 
  and view only `id`, your args should be:
  ```
  'args': [
      [('active', '=', 'true')],
      ['id']
    ],
  ```
  - `kwargs` - addition keyword arguments. For example, if you want to see only first 5 records:
  ```
  'kwargs': {
      'limit' : 5
  }
  ```
  
## Example
Let's try to create such response body with a help of library:
```
{
  'jsonrpc': '2.0',
  'id': 1,
  'method': 'call',
  'params': {
    'model': 'ir.ui.menu',
    'method': 'search_read',
    'args': [
      [('active', '=', 'true')],
      ['id'],
    ],
    'kwargs': {},
  }
}
```

Firstly, we need to create *IMenusRepository* by url `https://odoo.miem.tv/web/dataset/`:
```kotlin
interface IMenusRepository {
    
    @JsonRpc("call")
    fun getMenus(
      @JsonRpcPath path: String = "web/dataset",
      @JsonRpcArgument("model") model: String = "ir.ui.menu",
      @JsonRpcArgument("method") modelMethod: String = "search_read",
      @JsonRpcArgument("args") args: List<Any> = listOf(
        listOf<String>("active", "=", "true"),
        listOf<String>("id")
      ),
      @JsonRpcArgument("kwargs") kwargs: Map<String, Any> = emptyMap()
    )
}
```

Secondly, just create instance of this interface, using [JsonRpcClient](core/jsonrpc/core/src/main/kotlin/odoo/miem/android/core/jsonrpc/core/JsonRpcClient.kt):
```kotlin
val jsonRpcClient = JsonRpcClient.Builder()
            .baseUrl("https://odoo.miem.tv/")
            .build()

val menuRepository = jsonRpcClient.create(IMenusRepository::class.java)
// or it is also possible like that: val menuInterface by jsonRpcClient.create<IMenusRepository::class.java>()
```

And finally, call method (it is network call, so you asynchronous tools, like coroutines or rxjava):
```kotlin
val menus = menuRepository.getMenus()
```

## Alertness
Without any doubt this type of json body can change to suit your needs as you like