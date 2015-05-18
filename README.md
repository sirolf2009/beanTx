# beanTx

>JavaBeans are classes that encapsulate many objects into a single object (the bean). They are serializable, have a zero-argument constructor, and allow access to properties using getter and setter methods

This library can aid you with adding and removing data from neo4j by translating java beans into nodes. In order for a class to be considered a bean, it has to have a getter and setter for every variable and a no argument constructor.

*It does not have to implement the Serializable interface*

When you upload a bean, it is translated into key/value (kinda like JSon) and pushed to neo4j. If the bean has a variable that is not a primitive, array or string, then that variable gets translated into key/value and becomes a node in the graph. This variable node is then connected with a relationship to it's parent object.

## Example

This is an example pulled from the unit tests. I'd advise you to read the other [unit tests](https://github.com/sirolf2009/beanTx/tree/master/bean-tx/src/test/java/com/sirolf2009/beantx) as well as they are and will remain the most up to date with the features of this project.

```java
GraphDatabaseService graph = new GraphDatabaseFactory().newEmbeddedDatabase("test-db");
BeanTx beanTx = new BeanTx(graph);

TestBean testBean = TestBean.GetDefaultBean();
TestBean pulledBean = (TestBean) beanTx.pullBean(id);
assertEquals(testBean, pulledBean);
assertEquals(id, beanTx.getCache().getIDFromBean(testBean));
assertEquals(id, beanTx.getCache().getIDFromBean(pulledBean));
assertEquals(id, testBean.graphID);
assertEquals(id, pulledBean.graphID);
	
testBean.setEz("Mom says i'm special!");
beanTx.updateBean(testBean);
assertEquals(testBean, beanTx.pullBean(id));
	
beanTx.deleteBean(testBean);
assertEquals(null, beanTx.pullBean(id));
assertEquals(-1, beanTx.getCache().getIDFromBean(testBean));
assertEquals(-1, beanTx.getCache().getIDFromBean(pulledBean));
assertEquals(-1, beanTx.getCache().getIDFromBean(testBean.graphID));
assertEquals(-1, beanTx.getCache().getIDFromBean(pulledBean.graphID));
```

First, a new beanTx instance is created by using a database service, after which a bean is created. This is just a bean with a few primitive variables and their according getters and setters. Then, this bean is pushed to the database and the ID is returned.

```java
long id = beanTx.pushBean(testBean, "This", "is", "a", "test", "bean");
```

The first parameter is the bean itself. The other parameters are the labels the bean will receive. By default, the created node doesn't get any labels at all. BeanTx itself does not need these labels, but they may be useful for integrating other tools or doing manual searches on the graph.

The bean can be retrieved using the id.

```java
TestBean pulledBean = (TestBean) beanTx.pullBean(id);
```

It can also be updated.

```java
testBean.setEz("Mom says i'm special!");
beanTx.updateBean(testBean);
```

Or be deleted

```java
beanTx.deleteBean(testBean);
```

## //TODO

* This library as of now supports CRUD for "simple" beans, but not for "normal" or "complicated" beans. See [beans](https://github.com/sirolf2009/beanTx/tree/master/bean-tx/src/test/java/com/sirolf2009/beantx/beans) for the different type of beans. The "normal" beans do support Create and Read
* JavaDoc
* Build Server
* More cache control
* Interfaces, to define custom components/handlers
* Central Maven Repo
* More annotations for greater control of how beans are added to the graph

## Spring Data Neo4J

This library may seem like a giant ripoff off [spring data neo4j](https://github.com/spring-projects/spring-data-neo4j), but that's basically because it pretty much is. When I started this project, I did not know about the existence of spring data neo4j. However, when I figured out it existed I did steal an idea or two (Like the GraphID annotation). My aim since then, became to develop a much more lightweight version that doesn't need (or supports, depends on how you look at it :) ) any fancy configuration.
