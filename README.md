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
 
 
# AES

## 不可约多项式

> 不可约多项式，不能写成两个次数较低的多项式之乘积的多项式\
> 8次 不可约多项式 y(x) 范围 = 2^8 ~ (2^9-1) \
> 子项 z(x) 范围 = 2 ~ (2^8-1)
> y(x) != z1(x) * z2(x)

### 8次不可约多项式

| BIN         | OCT | DEC | HEX   |
|:------------|:----|:----|:------|
| 1 0001 1011 | 433 | 283 | 1 1 B |
| 1 0001 1101 | 435 | 285 | 1 1 D |
| 1 0010 1011 | 453 | 299 | 1 2 B |