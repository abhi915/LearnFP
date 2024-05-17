import org.apache.spark.sql.SparkSession

/*
*  In aggreagtion, we specify key or grouping and then we specify the aggregation function to transform one or more columnn.
* Aggregation function produces one result for group of columns.
*
*
* 1.groupBy
* 2.window
* 3. grouping set
* 4. roll up
* 5. cube
*
*
*  Grouping set:
*
*
In Apache Spark, the GROUPING SETS clause is used in SQL queries to generate multiple grouping sets
* within a single query. It allows you to perform aggregation across different combinations of grouping
* columns, providing more flexibility in summarizing data compared to traditional GROUP BY statements.
*
*
* example:
*
* | product_id | category   | subcategory | amount_sold |
|------------|------------|-------------|-------------|
| 1          | Electronics| TV          | 1000        |
| 2          | Electronics| Phone       | 800         |
| 3          | Clothing   | Shirts      | 500         |
| 4          | Clothing   | Pants       | 700         |
*
*
* SELECT category,subcategory,SUM(amount_sold) AS total_amount_sold FROM sales GROUP BY GROUPING SETS (
        (category, subcategory),
        (category),
        ());
*
*| category   | subcategory | total_amount_sold |
|------------|-------------|-------------------|
| Electronics| TV          | 1000              |
| Electronics| Phone       | 800               |
| Clothing   | Shirts      | 500               |
| Clothing   | Pants       | 700               |
| Electronics| NULL        | 1800              |
| Clothing   | NULL        | 1200              |
| NULL       | NULL        | 3000              |
*
*
*
*
* Rollup:
*
* | year | month | category   | subcategory | amount_sold |
|------|-------|------------|-------------|-------------|
| 2023 | Jan   | Electronics| TV          | 1000        |
| 2023 | Jan   | Electronics| Phone       | 800         |
| 2023 | Jan   | Clothing   | Shirts      | 500         |
| 2023 | Jan   | Clothing   | Pants       | 700         |

*
*
* SELECT
    year,
    month,
    category,
    subcategory,
    SUM(amount_sold) AS total_amount_sold
FROM
    sales
GROUP BY
    ROLLUP(year, month, category, subcategory);

*
*| year | month | category   | subcategory | total_amount_sold |
|------|-------|------------|-------------|-------------------|
| 2023 | Jan   | Electronics| TV          | 1000              |
| 2023 | Jan   | Electronics| Phone       | 800               |
| 2023 | Jan   | Clothing   | Shirts      | 500               |
| 2023 | Jan   | Clothing   | Pants       | 700               |
| 2023 | Jan   | Electronics| NULL        | 1800              |
| 2023 | Jan   | Clothing   | NULL        | 1200              |
| 2023 | Jan   | NULL       | NULL        | 3000              |
| 2023 | NULL  | NULL       | NULL        | 3000              |

*
*
* 3. cube
*
*
* | year | month | category   | subcategory | amount_sold |
|------|-------|------------|-------------|-------------|
| 2023 | Jan   | Electronics| TV          | 1000        |
| 2023 | Jan   | Electronics| Phone       | 800         |
| 2023 | Jan   | Clothing   | Shirts      | 500         |
| 2023 | Jan   | Clothing   | Pants       | 700         |

*
* SELECT
    year,
    month,
    category,
    subcategory,
    SUM(amount_sold) AS total_amount_sold
FROM
    sales
GROUP BY
    CUBE(year, month, category, subcategory);

*
* | year | month | category   | subcategory | total_amount_sold |
|------|-------|------------|-------------|-------------------|
| 2023 | Jan   | Electronics| TV          | 1000              |
| 2023 | Jan   | Electronics| Phone       | 800               |
| 2023 | Jan   | Clothing   | Shirts      | 500               |
| 2023 | Jan   | Clothing   | Pants       | 700               |
| 2023 | Jan   | Electronics| NULL        | 1800              |
| 2023 | Jan   | Clothing   | NULL        | 1200              |
| 2023 | Jan   | NULL       | NULL        | 3000              |
| 2023 | NULL  | NULL       | NULL        | 3000              |
| NULL | Jan   | NULL       | NULL        | 3000              |
| NULL | NULL  | NULL       | NULL        | 3000              |

* */



val spark = SparkSession
  .builder()
  .appName("leanrn aggreagation")
  .master("local")
  .getOrCreate()

import spark.implicits._


val seqData = Seq(("Java", 20000), ("Python", 100000), ("Scala", 3000))

val df = seqData.toDF("language", "users_count")