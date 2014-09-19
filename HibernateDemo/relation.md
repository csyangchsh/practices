# Relation Mapping

Hibernate version: 4.3.5.Final

* 1:1
* 1:n
* m:n
* 1:n:1

### Collections holding many side

XML Elment | Java Type | Comments
-----------|-----------|---------
set | java.util.Set | Fast, doesn't need index.
map | java.util.Map | Fast on update, needs index column equivalent to map key.
map | java.util.SortedMap | Same as map. In addition, sorting by a comporator is supported.
list | java.util.List | Fast on update, needs index column.
bag | java.util.List | Slow on update
array | Array of object | Needs index column
primitive-array | Array of String, Integer | Needs index column

*** Use Set in most cases. If need a sort order, use SortedSet. If need some kind of natural order or I have to update single entries of a relation very often, I use List. *** <br />
*** The use of Map is rare. ***

### Uni-directional Relations

Uni-directional is a relation where one side does not know about the relation.

#### Foreign key constraints

When there is a foreign key constraint on the relation, you must specify nullable=false in the JoinColumn annotation or add not-null="true" in the key tag.

### Bi-directional Realtions

Both sides know about the other side. You must always set the relation on both sides.

#### Inverse=true

Defines that a side does not manage a relation.

Foreign key constraints in a bi-directional relation Hibernate can cause exceptions of type foreign key violations. To prevent this you must use inverse="true" on the many side. 

### Cascading

Cascading means that if you insert, update or delete an object, related objects are inserted, updated or deleted as well. If you do not use cascade you would have to save both objects independently. 

JAP 2.0 ￼`@OneToMany (cascade = CascadeType.PERSIST, orphanRemoval = true)`
***
### 1:1

Using FK

1. Prefer Uni-directional
2. Use Join Fetch

### 1:n

#### Uni-directional

Make many-side as relationship owner

#### Bi-directional

Similar with Uni-directional, make many-side as relationship owner. This will lead to less queries.

`@OneToMany(mappedBy="club")` specifies that the relation is managed by the club property of the Member.

In XML, use `inverse="true"` on the collection.

### 1:n:1

TODO

## Query

TODO

## Open-Session-in-View

* Avoid LazyInitializationException
* Inefficiently fetching date during the rendering of the view
* If we get any exception there, the view is already rendered and we can’t even show an error page. 

	- Call session.flush in the application logic to enforce that open statements are inserted. This will probably reduce the number of exceptions but still a commit might fail.


## References

[Java Persistence and Hibernate book](http://www.laliluna.com/hibernate-3-jpa-book-ebook.html)
