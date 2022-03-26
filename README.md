# JAVA 插件开发样例

## pluggable 使用插件方

> 需要提供插件接口
> 
> 自定义 classloader 解密插件字节码
>> 使用命令行参数 传入解密byte `main(String[] args)` 默认解密byte=1

## plugins 插件

> 需要依赖 `pluggable` 提供的插件接口
> 
> 需要依赖 `groovy` 提供的加密 `encrypt` gradle TASK
> 
> 使用命令行参数 传入加密byte `gradle build -Pkey=2` 默认key=1
 
## groovy 插件

> gradle 插件加密 字节码  默认加密byte=1