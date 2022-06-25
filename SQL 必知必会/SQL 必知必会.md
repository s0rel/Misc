<!-- GFM-TOC -->
* [一、了解 SQL](#一了解 SQL)
* [二、检索数据](#二检索数据)
* [三、排序检索数据](#三排序检索数据)
* [四、过滤数据](#四过滤数据)
* [五、高级数据过滤](#五高级数据过滤)
* [六、用通配符进行过滤](#六用通配符进行过滤)
* [七、创建计算字段](#七创建计算字段])
* [八、使用函数处理数据](#八使用函数处理数据)
* [九、汇总数据](#九汇总数据)
* [十、分组数据](#十分组数据)
* [十一、使用子查询](#十一使用子查询)
* [十二、联结表](#十二联结表)
* [十三、创建高级联结](#十三创建高级联结)
* [十四、组合查询](#十四组合查询)
* [十五、插入数据](#十五插入数据])
* [十六、更新和删除数据](#十六更新和删除数据)
* [十七、创建和操纵表](#十七创建和操纵表)
* [十八、使用视图](#十八使用视图)
* [十九、使用存储过程](#十九使用存储过程)
* [二十、管理事务处理](#二十管理事务处理)
* [二十一、使用游标](#二十一使用游标)
* [二十二、高级 SQL 特性](#二十二高级 SQL 特性)
* [参考资料](#参考资料)
<!-- GFM-TOC -->


# 一、了解 SQL

模式定义了数据在表中如何存储、包括存储什么样的数据、数据如何分解，各部分信息如何命名等信息，数据库和表都有模式。

虽然并不总是需要主键，但应该总是定义主键，以便于以后的数据操作和管理。

表中的任何列都可以作为主键，只要它满足以下条件：

- 任意两行都不具有相同的主键值；

- 每一行都必须具有一个主键值（主键值不允许为 NULL）;

- 主键列中的值不允许修改或更新；

- 主键值不允许复用（如果某行从表中删除，它的主键不能赋给以后的新行）。

主键通常定义在表的一列上，但并不是必须这么做，也可以一起使用多个列作为主键。在使用多个列作为主键时，上述条件必须应用到所有列，所有列值的组合必须是唯一的，但其中单个列的值可以不唯一。

# 二、检索数据

SQL 语句不区分大小写，但是表名、列名和值是否区分依赖于具体的 DBMS 以及配置。

一般而言，除非确实需要表中的每一列，否则最好不要使用 * 通配符。虽然使用通配符省事，可以不用明确列出所需列，但检索不需要的列通常会降低检索速度和应用程序的性能。

**DISTINCT** 指示数据库只返回不同的值，并且 DISTINCT 关键字作用于所有的列，不仅仅是跟在其后的那一列。

```sql
SELECT DISTINCT vend_id, prod_price
FROM Products;

SELECT vend_id, prod_price
FROM Products;
```

**LIMIT** 限制返回的行数。如果需要指定开始的位置可以用 OFFSET。

```sql
-- 返回前 5 行数据
SELECT prod_name
FROM Products
LIMIT 5;

-- 返回从第 5 行开始的 5 行数据
SELECT prod_name
FROM Products
LIMIT 5 OFFSET 5;
```

MySQL、MariaDB 和 SQLite 可以把 LIMIT 4 OFFSET 3 语句简化为 LIMIT 3, 4。使用这个语法，逗号之前的值对应 OFFSET，逗号之后的值对应 LIMIT。

# 三、排序检索数据

在指定一条 **ORDER BY** 子句时，应该保证它是 SELECT 语句中最后一条子句。如果它不是最后的子句，将会出错。

**DESC** 关键字只应用到直接位于其前面的列名，如果想在多个列上进行降序排序，必须对每一列指定 DESC 关键字.

```sql
SELECT prod_id, prod_price, prod_name
FROM Products
ORDER BY prod_price DESC, prod_name ASC;
```

# 四、过滤数据

使用 **WHERE** 子句可以对数据进行过滤。下表显示了 WHERE 子句可用的操作符

| 操作符        | 说明         |
| :---:         | :---:        |
| =             | 等于         |
| &lt;          | 小于         |
| &gt;          | 大于         |
| &lt;&gt;、!=  | 不等于       |
| &lt;=、!&gt;  | 小于等于     |
| &gt;=、!&lt;  | 大于等于     |
| BETWEEN       | 在两个值之间 |
| IS NULL       | 为 NULL 值   |

应该注意到，NULL 与 0、空字符串、仅仅包含空格都不同。

要检查某个范围的值，可以使用 **BETWEEN** 操作符。在使用 BETWEEN 时，必须指定两个值 - 所需范围的低端值和高端值。这两个值必须用 AND 关键字分隔，BETWEEN 匹配范围中的所有值，包括指定的开始值和结束值。

# 五、高级数据过滤

SQL 在处理 **OR** 操作符之前，优先处理 **AND** 操作符，因此任何使用具有 AND 和 OR 操作符的 WHERE 子句，都应该使用圆括号明确地分组操作符。

```sql
-- 筛选出由供应商 BRS01 制造的价格为 10 美元以上的所有产品，以及由供应商 DLL01 制造的所有产品
SELECT prod_name, prod_price
FROM Products
WHERE vend_id = "DLL01"
   OR vend_id = "BRS01" AND prod_price >= 10;

-- 筛选出由供应商 BRS01 或 DLL01 制造的且价格在 10 美元以上的所有产品
SELECT prod_name, prod_price
FROM Products
WHERE (vend_id = "DLL01"
    OR vend_id = "BRS01")
  AND prod_price >= 10;
```

**NOT** 操作符只有一个功能，那就是否定其后跟的任何条件。

```sql
SELECT prod_name
FROM Products
WHERE NOT vend_id = "DLL01"
ORDER BY prod_name;
```

# 六、用通配符进行过滤

通配符 % 看起来像是可以匹配任何东西，但有个例外，那就是 NULL。子句 WHERE prod_name LIKE "%" 不会匹配产品名称为 NULL 的行。

使用通配符要记住的技巧

- 不要过度使用通配符，如果其他操作符能达到目的，应该使用其他操作符。

- 在确实需要使用通配符时，也尽量不要把它们用在搜索模式的开始处。把通配符置于开始处，搜索起来是很慢的。

- 仔细注意通配符的位置，如果放错地方，可能不会返回想要的数据。

# 七、创建计算字段

在 SQL 语句内可完成的许多转换和格式化工作都可以直接在客户端应用程序内完成。但一般来说，在数据库服务器上完成这些操作比在客户端完成要快得多。

**CONCAT()**  用于连接两个字段。许多数据库会使用空格把一个值填充为列宽，因此连接的结果会出现一些不必要的空格，使用 **TRIM()** 可以去除首尾空格。

```sql
SELECT CONCAT(TRIM(vend_name), "(", TRIM(vend_country), ")")
FROM Vendors
ORDER BY vend_name;
```

# 八、使用函数处理数据

与 SQL 语句不一样，SQL 函数不是可移植的。这意味着为特定 SQL 实现编写的代码在其他实现中可能不能用。

常用的文本处理函数

| 函数        | 说明                 |
| :---:       | :---:                |
| LEFT()      | 返回字符串左边的字符 |
| RIGHT()     | 返回字符串右边的字符 |
| LOWER()     | 将字符串转换为小写   |
| UPPER()     | 将字符串转换为大写   |
| LTRIM()     | 去除字符串左边的空格 |
| RTRIM()     | 去除字符串右边的空格 |
| LENGTH()    | 返回字符串的长度     |
| SUBSTRING() | 提取字符串的组成部分 |

常用的数值处理函数

| 函数   | 说明   |
| :---:  | :---:  |
| SIN()  | 正弦   |
| COS()  | 余弦   |
| TAN()  | 正切   |
| ABS()  | 绝对值 |
| SQRT() | 平方根 |
| MOD()  | 余数   |
| EXP()  | 指数   |
| PI()   | 圆周率 |
| RAND() | 随机数 |

# 九、汇总数据

SQL 聚集函数

| 函数    | 说明             |
| :---:   | :---:            |
| AVG()   | 返回某列的平均值 |
| COUNT() | 返回某列的行数   |
| MAX()   | 返回某列的最大值 |
| MIN()   | 返回某列的最小值 |
| SUM()   | 返回某列值之和   |

AVG() 函数、MAX() 函数、MIN() 函数和 SUM() 函数会忽略值为 NULL 的行。

COUNT() 函数有两种使用方式：

- 使用 COUNT(*) 对表中行的数目进行计数，不管表列中包含的是 NULL 还是非空值。

- 使用 COUNT(column) 对特定列中具有值的行进行计数，忽略 NULL 值。

# 十、分组数据

关于 **GROUP BY** 子句使用的一些重要规定

- GROUP BY 子句可以包含任意数目的列，因而可以对分组进行嵌套，更细致地进行数据分组。

- 如果在 GROUP BY 子句中嵌套了分组，在建立分组时，指定的所有列都一起计算。

- GROUP BY 子句中列出的每一列都必须是检索列或有效的表达式（但不能是聚集函数）。如果在 SELECT 中使用表达式，则必须在 GROUP BY 子句中指定相同的表达式，不能使用别名。

- 大多数 SQL 实现不允许 GROUP BY 列带有长度可变的数据类型，如文本或备注型字段。

- 如果分组列在包含具有 NULL 值的行，则 NULL 将作为一个分组返回。如果列中有多行 NULL 值，它们将分为一组。

- GROUP BY 子句必须出现在 WHERE 子句之后，ORDER BY 子句之前。

WHERE 用于标准的行级过滤，而 **HAVING** 应该结合 GROUP BY 子句用于分组过滤，并且 WHERE 在数据分组前过滤，WHERE 排除的行不包括在分组中，HAVING 在数据分组后过滤。

```sql
SELECT vend_id, COUNT(*) AS num_prods
FROM Products
WHERE prod_price >= 4
GROUP BY vend_id
HAVING COUNT(*) >= 2;
```

一般在使用 GROUP BY 子句时，应该也给出 ORDER BY 子句。这是保证数据正确排序的唯一方法，千万不要依赖于 GROUP BY 排序数据。

# 十一、使用子查询

子查询中只能返回一个字段的数据。

# 十二、联结表

联结用于连接多个表，使用 JOIN 关键字，并且条件语句使用 ON。

```sql
-- 內联结
SELECT vend_name, prod_name, prod_price
FROM Vendors
         INNER JOIN Products on Vendors.vend_id = Products.vend_id;
```

联结可以替换子查询，并且比子查询的效率一般会更快。

# 十三、创建高级联结

可以用 AS 给列名、计算字段和表名取别名，给表名取别名是为了缩短 SQL 语句以及在一条 SELECT 语句中多次使用相同的表。

```sql
-- 自联结
SELECT c1.cust_id, c1.cust_name, c1.cust_contact
FROM Customers AS c1,
     Customers AS c2
WHERE c1.cust_name = c2.cust_name
  AND c2.cust_contact = "Jim Jones";
```

自然联结要求只能选择那些唯一的列，一般通过对一个表使用通配符，而对其他表的列使用明确的子集来完成。

```sql
-- 自然联结
SELECT C.*, O.order_num, O.order_date, OI.prod_id, OI.quantity, OI.item_price
FROM Customers AS C,
     Orders AS O,
     OrderItems AS OI
WHERE C.cust_id = O.cust_id
  AND OI.order_num = O.order_num
  AND prod_id = "RGAN01";
```

外联结包含了那些在相关表中没有关联行的行，分为左外联结和右外联结两种。

```sql
-- 左外联结，包含左边表的所有行
SELECT Customers.cust_id, Orders.order_num
FROM Customers
         LEFT JOIN Orders ON Customers.cust_id = Orders.cust_id;
```

# 十四、组合查询

主要有两种情况需要使用组合查询

- 在一个查询中从不同的表返回结构数据。

- 对一个表执行多个查询，按一个查询返回数据。

可用 **UNION** 操作符来组合数条 SQL 查询，利用 UNION，可给出多条 SELECT 语句，将它们的结果组合成一个结果集。

```sql
SELECT cust_name, cust_contact, cust_email
FROM Customers
WHERE cust_state IN ("IL", "IN", "MI")
UNION
SELECT cust_name, cust_contact, cust_email
FROM Customers
WHERE cust_name = "FUN4All";
```

UNION 的使用规则

- UNION 必须由两条或两条以上的 SELECT 语句组成，语句之间用关键字 UNION 分隔。

- UNION 中的每个查询必须包含相同的列、表达式或聚集函数，不过，各个列不需要以相同的次序出现。

- 列数据类型必须兼容，类型不必完全相同，但必须是 DBMS 可以隐含转换的类型。

如果结合 UNION 使用的 SELECT 语句遇到不同的列名。它会返回第一个名字，这种行为带来一个有意思的副作用，由于只使用第一个名字，那么想要排序也只能用这个名字。

使用 UNION 组合两个查询，如果第一个查询返回 M 行，第二个查询返回 N 行，那么组合查询的结果一般为 M+N 行。UNION 默认会自动移除重复行，如果想返回所有的匹配行，可以使用 **UNION ALL**。


# 十五、插入数据

INSERT 通常只插入一行，要插入多行，必须执行多个 INSERT 语句。**INSERT SELECT** 是个例外，它可以用一条 INSERT 插入多行，不管 SELECT 语句返回多少行都将被 INSERT 插入。

```sql
INSERT INTO Customers(cust_id, cust_contact, cust_email, cust_name, cust_address, cust_city, cust_state, cust_zip, cust_country)
SELECT cust_id, cust_contact, cust_email, cust_name, cust_address, cust_city, cust_state, cust_zip, cust_country
FROM CustNew;
``` 

有一种数据插入不使用 INSERT 语句。要将一个表的内容复制到一个全新的表（运行中创建的表），可以使用 **CREATE SELECT** 语句。与 INSERT SELECT 将数据添加到一个已经存在的表不同，CREATE SELECT 将数据复制到一个新表（有的 DBMS 可以覆盖已经存在的表，这依赖于所使用的具体 DBMS）。

```sql
CREATE TABLE CustCopy AS SELECT * FROM Customers;
```

在使用 **SELECT INTO** 时，需要知道一些事情：

- 任何 SELECT 选项和子句都可以使用，包括 WHERE 和 GROUP BY；

- 可利用联结从多个表插入数据；

- 不管从多少个表中检索数据，数据都只能插入到一个表中。

SELECT INTO 是试验新 SQL 语句前进行表复制的很好工具。先进行复制，可在复制的数据上测试 SQL 代码，而不会影响实际的数据。

# 十六、更新和删除数据

DELETE 语句从表中删除行，甚至是删除表中所有行。但是 DELETE 不删除表本身。如果想从表中删除所有行，不要使用 DELETE。可使用 **TRUNCATE TABLE** 语句，它完成相同的工作，而速度更快（因为不记录数据的变动）。

# 十七、创建和操纵表

只有不允许 NULL 值的列可作为主键，允许 NULL 值的列不能作为唯一标识。

# 十八、使用视图

视图是虚拟的表，与包含数据的表不一样，视图只包含使用时动态检索数据的查询。

使用视图的原因：

- 重用 SQL 语句；

- 简化复杂的 SQL 操作，在编写查询后，可以方便地重用它而不必知道其基本查询细节；

- 使用表的一部分而不是整个表；

- 保护数据，可以授予用户访问表的特定部分的权限，而不是整个表的访问权限；

- 更改数据格式和表示，视图可返回与底层表的表示和格式不同的数据。

视图本身不包含数据，因此返回的数据是从其他表中检索出来的。在添加或更改这些表中的数据时，视图将返回改变过的数据。因为视图不包含数据，所以每次使用视图时，都必须处理查询执行时需要的所有检索，性能较差。在部署使用了大量视图的应用前，应该进行测试。

视图创建和使用的规则和限制：

- 与表一样，视图必须唯一命名；

- 对于可以创建的视图数目没有限制；

- 视图可以嵌套，即可以利用其他视图中检索数据的查询来构造视图；

- 许多 DBMS 禁止在视图查询中使用 ORDER BY 子句；

- 有些 DBMS 要求对返回的所有列进行命名，如果列是计算字段，则需要使用别名；

- 视图不能索引，也不能有关联的触发器或默认值；

- 有些 DBMS 把视图作为只读的查询，这表示可以从视图检索数据，但不能将数据写回底层表；

- 有些 DBMS 允许创建这样的视图，它不能进行导致行不再属于视图的插入或更新。例如有一个视图，只检索带有电子邮件地址的顾客。如果删除某个顾客的电子邮件地址，将使该顾客不再属于视图。这是默认行为，而且是允许的，但有的 DBMS 可能会防止这种情况发生。

视图用 **CREATE VIEW** 语句来创建，删除视图，可以使用 DROP 语句。覆盖或更新视图，必须先删除它，然后再重新创建。

```sql
CREATE VIEW ProductCustomers AS
SELECT cust_name, cust_contact, prod_id
FROM Customers, Orders, OrderItems
WHERE Customers.cust_id = Orders.cust_id
AND OrderItems.order_num = Orders.order_num;
```

# 十九、使用存储过程

存储过程就是为了以后使用而保存的一条或多条 SQL 语句。可将其视为批文件，虽然它们的作用不限于批处理。

使用存储过程的原因：

- 通过把处理封装成一个易用的单元中，可以简化复杂的操作；

- 由于不要求反复建立一系列处理步骤，因而保证了数据的一致性。如果所有开发人员和应用程序都使用同一存储过程，则所使用的代码都相同；

- 防止错误，需要执行的步骤越多，出错的可能性就越大，防止错误保证了数据的一致性；

- 简化对变动的管理，如果表名、列名或业务逻辑（或别的内容）有变化，那么只需要更改存储过程的代码，使用它的人员甚至不需要知道这些变化；

- 安全性，通过存储过程限制对基础数据的访问，减少了数据讹误的机会；

- 因为存储过程通常以编译过的形式存储，所以 DBMS 处理命令所需的工作量少，提高了性能；

- 存在一些只能用在单个请求中的 SQL 元素和特性，存储过程可以使用它们来编写功能更强更灵活的代码。

```sql
EXECUTE AddNewProduct("JTS01",
"Stuffed Eiffel Tower",
6.49,
"Plush stuffed toy with the text La Tour Eiffel in red white and blue");
```

```sql
CREATE PROCEDURE MailingListCount (
    ListCount OUT iNTEGER
)
IS v_rows iNTEGER;
BEGIN
    SELECT COUNT(*) INTO v_rows
    FROM Customers
    WHERE NOT cust_email IS NULL;
    ListCount := v_rows;
END;
```

# 二十、管理事务处理

事务处理用来管理 INSERT、UPDATE 和 DELETE 语句，不能回退 SELECT 语句，也不能回退 CREATE 或 DROP 操作。

使用 **ROLLBACK** 和 **COMMIT** 语句，就可以写入或撤销整个事务。但是，只对简单的事务才能这样做，复杂的事务可能需要部分提交或回退。要支持回退部分事务，必须在事务处理块中的合适位置放置占位符。这样如果需要回退，可以回退到某个占位符。

可以在 SQL 代码中设置任意多的保留点，越多越好。因为保留点越多，就越能灵活地进行回退。

# 二十一、使用游标

游标是一个存储在 DBMS 服务器上的数据库查询，它不是一条 SELECT 语句，而是被该语句检索出来的结果集。在存储了游标后，应用程序可以根据需要滚动或浏览其中的数据。

游标常见的一些选项和特性如下：

- 能够标记游标为只读，使数据能读取，但不能更新和删除；

- 能控制可以执行的定向操作，向前、向后、第一、最后、绝对位置和相对位置等；

- 能标记某些列为可编辑的，某些列为不可编辑的；

- 规定范围，使游标对创建它的特定请求（如存储过程）或对所有请求可访问；

- 指示 DBMS 对检索出的数据（而不是指出表中活动数据）进行复制，使数据在游标打开和访问期间不变化。

使用游标涉及几个明确步骤：

- 在使用游标前，必须声明它。这个过程实际上没有检索数据，它只是定义要使用的 SELECT 语句好游标选项；

- 一旦声明，就必须打开游标以供使用。这个过程用前面定义的 SELECT 语句把数据实际检索出来；

- 对于填有数据的游标，根据需要取出各行；

- 在结束游标使用时，必须关闭游标，可能的话，释放游标。

```sql
-- 创建游标
DECLARE CustCursor CURSOR
FOR 
SELECT * FROM Customers
WHERE cust_email IS NULL;
```

```sql
-- 打开游标
OPEN CURSOR CustCursor;

-- 关闭游标
CLOSE CustCursor;
```

一旦游标关闭，如果不再次打开，将不能使用。第二次使用它时不需要再声明，只需用 OPEN 打开它即可。

# 二十二、高级 SQL 特性

DBMS 通过在数据库表上施加约束来实施引用完整性。

主键是一种特殊的约束，用来保证一列或一组列中的值是唯一的，而且永不改动。

外键是表中的一列，其值必须列在另一表的主键中。外键是保证引用完整性极其重要的部分。在定义外键后，DBMS 不允许删除在另一表中具有关联行的行，因而利用外键可以防止意外删除数据。

```sql
-- 定义外键的两种方法
CREATE TABLE Orders (
    order_num iNTEGER NOT NULL PRIMARY KEY,
    order_date DATETIME NOT NULL,
    cust_id CHAR(10) NOT NULL REFERENCES Customers(cust_id)
);

ALTER TABLE Orders
ADD CONSTRAINT
FOREIGN KEY (cust_id) REFERENCES Customers(cust_id); 
```

唯一约束用来保证一列或一组列中的数据是唯一的。它们类似于主键，但存在以下重要区别。

- 表可包含多个唯一约束，但每个表只允许一个主键；

- 唯一约束列可包含 NULL 值；

- 唯一约束列可修改或更新；

- 唯一约束列的值可重复使用；

- 与主键不一样，唯一约束不能用来定义外键。

检查约束用来保证一列或一组列中的数据满足一组指定的条件。检查约束的常见用途有以下几点：

- 检查最小或最大值；

- 指定范围；

- 只允许特定的值。

```sql
-- 引入检查约束的两种方法
CREATE TABLE OrderItems (
    order_num iNTEGER NOT NULL,
    order_item iNTEGER NOT NULL,
    prod_id CHAR(10) NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 10),
    item_price MONEY NOT NULL
);

ADD CONSTRAINT CHECK (gender LIKE '[MF]');
```

触发器是特殊的存储过程，它在特殊的数据库活动发生时自动执行。触发器可以与特定表上的 INSERT、UPDATE 和 DELETE 操作或组合相关联。与存储过程不一样（存储过程只是简单地存储 SQL 语句），触发器与单个表相关联。

触发器的一些常见用途：

- 保证数据一致，例如，在 INSERT 或 UPDATE 操作中将所有州名转换为大写；

- 基于某个表的变动在其他表上执行活动。例如，每当更新或删除一行时将审计跟踪记录写入某个日志表；

- 进行额外的验证并根据需要回退数据。例如，保证某个顾客的可用资金不超限定，如果已经超出，则阻塞插入；

- 计算机算列的值或更新时间戳。

一般来说，约束的处理比触发器快，因此在可能的时候，应该尽量使用约束。

# 参考资料

- Ben·Forta. SQL 必知必会 [M]. 人民邮电出版社, 2020.
