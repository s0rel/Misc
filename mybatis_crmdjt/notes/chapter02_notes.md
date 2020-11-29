resultMap 标签用于配置 Java 对象的属性和查询结果列的对应关系，通过 resultMap 中配置的 column 和 property 可以将查询列的值映射到 type 对象的属性上。

resultMap 包含的所有属性如下：
* id，必填并且唯一，在 SELECT 标签中，resultMap 指定的值即为此处 id 所设置的值。
* type，必填，用于配置查询列所映射到的 Java 对象类型。
* extends，选填，可以配置当前的 resultMap 继承自其他的 resultMap，属性值为继承 resultMap 的 id。
* autoMapping，选填，可选值为 true 或 false，用于配置是否启用非映射字段（没有在 resultMap 中配置的字段）的自动映射功能，该功能可以覆盖全局的 autoMappingBehavior 配置。

resultMap 包含的所有标签如下：
* constructor，通过构造方法注入属性的结果，包含以下两个标签：
  * idArg，id 参数，标记结果作为 id（唯一值），可以帮助提高整体性能。
  * arg，注入到构造方法的一个普通结果。
* id，一个 id 结果，标记结果作为 id（唯一值），可以帮助提高整体性能。
* result，注入到 Java 对象属性的普通结果。
* association，一个复杂的类型关联，许多结果将包成这种类型。
* collection，复杂的类型集合。
* discriminator，根据结果值来决定使用哪个结果映射。
* case，基于某些值的结果映射。